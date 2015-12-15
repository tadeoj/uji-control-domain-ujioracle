/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal;

import java.util.Hashtable;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.prefs.Preferences;

import es.uji.control.domain.provider.spi.IControlConnectionFactorySPI;
import es.uji.control.domain.ujioracle.UjiOracleConnectionFactoryConfig;

@Component(name="factory.ujioracle", immediate=true)
public class UjiOracleComponent implements UjiOracleConnectionFactoryConfig {
	
	static final String CONNECTION_FACTORY_KEY_VALUE = "UJIORA";
	static final String CONNECTION_FACTORY_DESCRIPTION_VALUE = "Backend UJI/Oracle via JDBC";

	private BundleContext bundlecontext;
	private Preferences preferences;

	private ServiceRegistration<IControlConnectionFactorySPI> registration;
	private ConnectionFactoryImpl impl;
	
	@Activate
	public void activate(ComponentContext componentContext) throws Exception {
		// Se anota el contexto del Bundle para posteriores operacones
		this.bundlecontext = componentContext.getBundleContext();
		// Se obtienen las preferencias, si no existe el fichero se crea..
		preferences = InstanceScope.INSTANCE.getNode("es.uji.control.domain.ujioracle.ui.preferences");
		// Se registra el factori con la configuracion por defecto
		registerConnectionFactory();
	}

	@Deactivate
	public void deactivate(ComponentContext componentContext) {
		unregisterConnectionFactory();
	}
	
	private ConnectionConfig readConfig() {
		String url = preferences.get("url", "");
		String user = preferences.get("user", "");
		String password = preferences.get("password", "");
		String schema = preferences.get("schema", "");
		String sid = preferences.get("sid", "");
		
		return new ConnectionConfig(url, user, password, schema, sid, true);
	}
	
	@Override
	public void checkPreferences() {
		
		// Se desregistra el servicio
		unregisterConnectionFactory();
		
		// Se registra de nuevo el servicio (ahora utilizara la nueva configuracion)
		registerConnectionFactory();
		
	}
	
	@Override
	public String getURL() {
		return readConfig().getURL();
	}
	
	@Override
	public String getUser() {
		return readConfig().getUser();
	}
	
	@Override
	public String getPassword() {
		return readConfig().getPassword();
	}
	
	@Override
	public String getSchema() {
		return readConfig().getSchema();
	}
	
	@Override
	public String getSid() {
		return readConfig().getSid();
	}
	
	private void registerConnectionFactory() {
		if (registration != null) {
			
			throw new IllegalStateException("El servicio ya esta registrado.");
			
		} else {
			
			// Se lee la configuracion
			ConnectionConfig config = readConfig();
			
			if (config.isValid()) {
			
				// Se preparan los atributos para la publicacion del servicio
				Hashtable<String, Object> properties = new Hashtable<>();
				properties.put(IControlConnectionFactorySPI.CONNECTION_FACTORY_KEY, CONNECTION_FACTORY_KEY_VALUE);
				properties.put(IControlConnectionFactorySPI.CONNECTION_FACTORY_DESCRIPTION, CONNECTION_FACTORY_DESCRIPTION_VALUE);
				
				// Se instancia el servicio
				impl = new ConnectionFactoryImpl(config);
				
				// Se registra el servicio
				registration = bundlecontext.registerService(IControlConnectionFactorySPI.class, impl, properties);
				
			}
			
		}
		
	}
	
	private void unregisterConnectionFactory() {
		if (registration != null) {
			
			// Se desregistra el servicio
			registration.unregister();

			// Se resetea el factory (limpia todos sus recursos)
			impl.reset();
			
			// Por simetria se liberan ambas referencias
			registration = null;
			impl = null;
			
		}
	}

}
