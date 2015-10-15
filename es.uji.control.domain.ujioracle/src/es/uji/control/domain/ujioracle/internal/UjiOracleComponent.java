package es.uji.control.domain.ujioracle.internal;

import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.prefs.PreferencesService;

import es.uji.control.domain.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.service.connectionfactory.IControlConnection;
import es.uji.control.domain.spi.IControlConnectionFactorySPI;
import es.uji.control.domain.ujioracle.UjiOracleConnectionFactoryConfig;

@Component(name="factory.ujioracle", immediate=true)
public class UjiOracleComponent implements UjiOracleConnectionFactoryConfig {
	
	static final String CONNECTION_FACTORY_KEY_VALUE = "UJIORA";
	static final String CONNECTION_FACTORY_DESCRIPTION_VALUE = "Backend UJI/Oracle via JDBC";

	private BundleContext bundlecontext;
	private PreferencesService preferencesService;

	@Activate
	public void activate(ComponentContext componentContext) throws Exception {
		this.bundlecontext = componentContext.getBundleContext();
	}

	@Deactivate
	public void deactivate(ComponentContext componentContext) {
		synchronized (this) {
		}
	}

	@Reference(policy=ReferencePolicy.STATIC, cardinality=ReferenceCardinality.MANDATORY, name="preferences")
	public void bindPreferences(PreferencesService preferencesService) {
		// Se anota el servicio de registro
		this.preferencesService = preferencesService;
	}
	
	public void unbindPreferences(PreferencesService preferencesService) {
		// Se resetea el servicio de registro
		this.preferencesService = null;
	}

	
	@Override
	public void setURL(String url) {
	}

	@Override
	public String getURL() {
		return null;
	}
	
	private ServiceRegistration<IControlConnectionFactorySPI> registration;
	private ConnectionFactorySPIImpl impl;
	
	private void registerConnectionFactory() {
		if (registration != null) {
			throw new IllegalStateException("El servicio ya esta registrado.");
		} else {
			Hashtable<String, Object> properties = new Hashtable<>();
			properties.put(IControlConnectionFactorySPI.CONNECTION_FACTORY_KEY, CONNECTION_FACTORY_KEY_VALUE);
			properties.put(IControlConnectionFactorySPI.CONNECTION_FACTORY_DESCRIPTION, CONNECTION_FACTORY_DESCRIPTION_VALUE);
			impl = new ConnectionFactorySPIImpl();
			registration = bundlecontext.registerService(IControlConnectionFactorySPI.class, impl, properties);
		}
		
	}
	
	private void unregisterConnectionFactory() {
		if (registration != null) {
			registration.unregister();
			registration = null;
			impl.reset();
			impl = null;
		}
	}
	
	private class ConnectionFactorySPIImpl implements IControlConnectionFactorySPI {

		@Override
		public IControlConnection createConnection() throws ControlConnectionException {
			// TODO Auto-generated method stub
			return null;
		}
		
		public void reset() {
			
		}
		
	}

}
