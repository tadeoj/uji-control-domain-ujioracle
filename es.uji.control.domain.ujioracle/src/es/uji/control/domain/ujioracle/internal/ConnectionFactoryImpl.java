/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.service.connectionfactory.ControlNotImplementedException;
import es.uji.control.domain.provider.service.connectionfactory.IControlConnection;
import es.uji.control.domain.provider.spi.IControlConnectionFactorySPI;
import es.uji.control.domain.provider.subsystem.authorizations.IAuthorizationsService;
import es.uji.control.domain.provider.subsystem.people.IPersonService;
import es.uji.control.domain.ujioracle.internal.authorizations.AuthorizationsImpl;
import es.uji.control.domain.ujioracle.internal.people.PersonImpl;
import oracle.jdbc.driver.OracleDriver;

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
		
		private Driver driver;
		private Connection connection;
		private PersonImpl personImpl;
		private AuthorizationsImpl authorizationsImpl;

		public ControlConnectionImpl(ConnectionConfig config) throws ControlConnectionException {
			
			//Se instancia el driver 
			try {
				driver = new OracleDriver();
				DriverManager.registerDriver(driver);
			} catch (SQLException e) {
				throw new ControlConnectionException("No se ha podido cargar el driver JDBC");
			}
			
			// Se abre la conexion
			this.connection = openConnection();
			// Se crea la implementacion de los subservicios
			this.personImpl = new PersonImpl(connection);
			//this.authorizationsImpl = new AuthorizationsImpl(connection);
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

		private Connection openConnection() throws ControlConnectionException {

			String URL = String.format("jdbc:oracle:thin:@%s:1521:%s", config.getURL(), config.getSid());
			
			Properties connectionProps = new Properties();
			connectionProps.put("user", config.getUser());
			connectionProps.put("password", config.getPassword());
			
			try {
				return DriverManager.getConnection(URL, connectionProps);
			} catch (SQLException e) {
				throw new ControlConnectionException("No se ha podido establecer una conexión con la base de datos.");
			}	

		}

		private void closeConnection() {
			personImpl.reset();		
			
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
			}
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