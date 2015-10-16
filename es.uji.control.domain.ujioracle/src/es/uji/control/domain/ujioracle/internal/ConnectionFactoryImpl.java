package es.uji.control.domain.ujioracle.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uji.control.domain.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.service.connectionfactory.ControlNotImplementedException;
import es.uji.control.domain.service.connectionfactory.IControlConnection;
import es.uji.control.domain.spi.IControlConnectionFactorySPI;
import es.uji.control.domain.subsystem.authorizations.IAuthorizationsService;
import es.uji.control.domain.subsystem.people.IPersonService;
import es.uji.control.domain.ujioracle.internal.authorizations.AuthorizationsImpl;
import es.uji.control.domain.ujioracle.internal.people.PersonImpl;

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
				throw new ControlConnectionException("Este factory ya no se puede utilizar porquer ha sido cerrado", null);
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
				// Se cierrar la conexion
				closeConnection();
				// Se des-registra
				ConnectionFactoryImpl.this.connections.remove(this);
			}
		}

		private EntityManager openConnection() throws ControlConnectionException {
			// TODO: Abre conexion (crear el EntinyManager) y SI dispara excepcion si hay problemas...
			return entityManager;
		}
		
		private void closeConnection() {
			// TODO: Se cierra la conexion y no tratamos excepciones. 
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