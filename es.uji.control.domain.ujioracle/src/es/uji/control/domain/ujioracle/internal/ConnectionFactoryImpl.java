/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;

import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.service.connectionfactory.ControlNotImplementedException;
import es.uji.control.domain.provider.service.connectionfactory.IControlConnection;
import es.uji.control.domain.provider.spi.IControlConnectionFactorySPI;
import es.uji.control.domain.provider.subsystem.authorizations.IAuthorizationsService;
import es.uji.control.domain.provider.subsystem.people.IPersonService;
import es.uji.control.domain.ujioracle.internal.authorizations.AuthorizationsImpl;
import es.uji.control.domain.ujioracle.internal.people.PersonImpl;
import es.uji.control.domain.ujioracle.internal.people.entities.UJICard;

class ConnectionFactoryImpl implements IControlConnectionFactorySPI {

	final private AtomicBoolean cleaned = new AtomicBoolean(false);
	private ConnectionConfig config;
	final private List<ControlConnectionImpl> connections = new ArrayList<>();

	public ConnectionFactoryImpl(ConnectionConfig config) {
		this.config = config;
	}

	@Override
	public IControlConnection createConnection() throws ControlConnectionException {
		synchronized (this) {
			if (cleaned.get()) {
				throw new ControlConnectionException("Este factory ya no se puede utilizar porque ha sido cerrado",
						null);
			} else {
				try {
					// Se intenta abrir la conexion
					return new ControlConnectionImpl(config);
				} catch (Throwable ex) {
					// No ha sido posible
					throw new ControlConnectionException("No ha sido posible crear la nueva conexion.", ex);
				}
			}
		}
	}

	public void reset() {
		synchronized (this) {
			if (!cleaned.get()) {
				// Ya no se podra volver a resetear
				cleaned.set(true);
				// Se limpian las conexiones
				while (connections.size() > 0) {
					connections.get(0).close();
				}
			}
		}
	}

	private class ControlConnectionImpl implements IControlConnection {

		private EntityManager entityManager;
		private EntityManagerFactory entityManagerFactory;
		private PersonImpl personImpl;
		private AuthorizationsImpl authorizationsImpl;

		public ControlConnectionImpl(ConnectionConfig config) throws ControlConnectionException {
			// Se abre la conexion
			this.entityManager = openConnection();
			// Se crea la implementacion de los subservicios
			this.personImpl = new PersonImpl(entityManager);
			this.authorizationsImpl = new AuthorizationsImpl(entityManager);
			// Si todo ha ido bien se registra la conexion.
			ConnectionFactoryImpl.this.connections.add(this);
		}

		@Override
		public void close() {
			synchronized (ConnectionFactoryImpl.this) {
				// Se cierra la conexion
				closeConnection();
				// Se des-registra
				ConnectionFactoryImpl.this.connections.remove(this);
			}
		}

		private EntityManager openConnection() throws ControlConnectionException {
			HashMap<String, Object> properties = new HashMap<String, Object>();
			properties.put(PersistenceUnitProperties.CLASSLOADER, UJICard.class.getClassLoader());
			properties.put("eclipselink.logging.level", "FINE");
			properties.put("eclipselink.logging.timestamp", "false");
			properties.put("eclipselink.logging.session", "false");
			properties.put("eclipselink.logging.thread", "false");
			properties.put("javax.persistence.jdbc.url", "jdbc:oracle:thin:@" + config.getURL() + ":1521:" + config.getSid());
			properties.put("javax.persistence.jdbc.user", config.getUser());
			properties.put("javax.persistence.jdbc.password", config.getPassword());
			properties.put("eclipselink.jdbc.read-connections.min", "3");
			properties.put("eclipselink.jdbc.write-connections.min", "3");
			properties.put("javax.persistence.jdbc.driver", "oracle.jdbc.driver.OracleDriver");
			
			try {
				this.entityManagerFactory = new PersistenceProvider().createEntityManagerFactory("sip", properties);
				return entityManagerFactory.createEntityManager();
			} catch (Exception e) {
				throw new ControlConnectionException("No se ha podido establecer la conexión con la base de datos.");
			}
		}

		private void closeConnection() {
			entityManager.clear();
			entityManager.close();
			entityManagerFactory.close();
		}

		@Override
		public IPersonService getPersonService() throws ControlNotImplementedException {
			return personImpl;
		}

		@Override
		public IAuthorizationsService getAuthorizationsService() throws ControlNotImplementedException {
			return authorizationsImpl;
		}

	}

}