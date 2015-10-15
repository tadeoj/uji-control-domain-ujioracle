package es.uji.control.domain.jdbc.internal;

import java.sql.Connection;
import java.util.ArrayList;

import es.uji.control.domain.IControlConnection;
import es.uji.control.domain.ControlConnectionException;
import es.uji.control.domain.ControlNotImplementedException;
import es.uji.control.domain.authorizations.IAuthorizationsService;
import es.uji.control.domain.jdbc.IJdbcPeopleDomainConnectionGetter;
import es.uji.control.domain.jdbc.internal.authorizations.AuthorizationsImpl;
import es.uji.control.domain.jdbc.internal.people.PersonImpl;
import es.uji.control.domain.people.IPersonService;
import es.uji.control.domain.spi.IConnectionFactorySPI;

public class JdbcConnectionFactory implements IConnectionFactorySPI {

	private IJdbcPeopleDomainConnectionGetter globalConfig;
	private GetterImpl factoryConfig;
	
	private ArrayList<PeopleDomainConnection> connections = new ArrayList<>();
	
	public JdbcConnectionFactory(IJdbcPeopleDomainConnectionGetter config) {
		this.globalConfig = config;
		this.factoryConfig = new GetterImpl(globalConfig);
	}

	@Override
	public IControlConnection createConnection() throws ControlConnectionException {
		synchronized (this) {
			PeopleDomainConnection newConnection = new PeopleDomainConnection();
			connections.add(newConnection);
			return newConnection;
		}
	}

	@Override
	public boolean hasPropertiesChanged() {
		synchronized (this) {
			GetterImpl localConfig = new GetterImpl(globalConfig);
			return !localConfig.equals(factoryConfig);
		}
	}

	@Override
	public void reset() {
		synchronized (this) {
			// Se cierran las conexiones activas
			connections.forEach(c->c.close());
			// Se refrescan las propiedades de la conexion.
			this.factoryConfig = new GetterImpl(globalConfig);
		}
	}
	
	private class PeopleDomainConnection implements IControlConnection {
		
		PersonImpl personImpl;
		AuthorizationsImpl authorizationsImpl;
		
		public PeopleDomainConnection() throws ControlConnectionException {
			
			// TODO: Se establece la conexiom..
			Connection connection = null;
			
			// TODO: Se crean las implementaciones de los subservicios
			personImpl = new PersonImpl(connection); // TODO: Hay que proporcionar al constructor los recursos de la conexion...
			authorizationsImpl = new AuthorizationsImpl();  // TODO: Hay que proporcionar al constructor los recursos de la conexion...
			
		}

		@Override
		public IPersonService getPersonService() throws ControlNotImplementedException {
			if (personImpl == null) {
				throw new ControlNotImplementedException("No implementado en este conector.");
			} else {
				return personImpl;
			}
		}

		@Override
		public IAuthorizationsService getAuthorizationsService() throws ControlNotImplementedException {
			if (authorizationsImpl == null) {
				throw new ControlNotImplementedException("No implementado en este conector.");
			} else {
				return authorizationsImpl;
			}
		}

		@Override
		public void close() {
			
			// TODO: Se cierra la conexion...
			
			synchronized (JdbcConnectionFactory.this) {
				connections.remove(this);
			}
			
		}
		
	}

}
