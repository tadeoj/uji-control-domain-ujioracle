/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
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
import org.osgi.service.prefs.Preferences;
import org.osgi.service.prefs.PreferencesService;

import es.uji.control.domain.spi.IControlConnectionFactorySPI;
import es.uji.control.domain.ujioracle.UjiOracleConnectionFactoryConfig;

@Component(name="factory.ujioracle", immediate=true)
public class UjiOracleComponent implements UjiOracleConnectionFactoryConfig {
	
	static final String CONNECTION_FACTORY_KEY_VALUE = "UJIORA";
	static final String CONNECTION_FACTORY_DESCRIPTION_VALUE = "Backend UJI/Oracle via JDBC";

	private BundleContext bundlecontext;
	private PreferencesService preferencesService;
	private Preferences preferences;

	private ServiceRegistration<IControlConnectionFactorySPI> registration;
	private ConnectionFactoryImpl impl;
	
	@Activate
	public void activate(ComponentContext componentContext) throws Exception {
		// Se anota el contexto del Bundle para posteriores operacones
		this.bundlecontext = componentContext.getBundleContext();
		// Se registra el factori con la configuracion por defecto
		registerConnectionFactory();
	}

	@Deactivate
	public void deactivate(ComponentContext componentContext) {
		unregisterConnectionFactory();
	}

	@Reference(policy=ReferencePolicy.STATIC, cardinality=ReferenceCardinality.MANDATORY, name="preferences")
	public void bindPreferences(PreferencesService preferencesService) {
		// Se anota el servicio de registro
		this.preferencesService = preferencesService;
		preferences = preferencesService.getUserPreferences("es.uji.control.domain.ujioracle.preferences.prefs");		
	}
	
	public void unbindPreferences(PreferencesService preferencesService) {
		// Se resetea el servicio de registro
		this.preferencesService = null;
	}

	private void writeConfig(ConnectionConfig config) {
		// TODO:
	}
	
	private ConnectionConfig readConfig() {
		return null; // TODO:
	}
	
	@Override
	public void setURL(String url, String user, String password, String schema, String sid) {
		
		// Se desregistra el servicio
		unregisterConnectionFactory();
		
		// Se almazena la nueva configuracion
		writeConfig(new ConnectionConfig(url, user, password, schema, sid));
		
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
