package es.uji.control.domain.ujioracle.internal;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.prefs.PreferencesService;

import es.uji.control.domain.ujioracle.UjiOracleConnectionFactoryConfig;

@Component(name="factory.ujioracle", immediate=true)
public class UjiOracleComponent implements UjiOracleConnectionFactoryConfig {

	private PreferencesService preferencesService;

	@Activate
	public void activate(ComponentContext componentContext) throws Exception {
		synchronized (this) {
		}
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

}
