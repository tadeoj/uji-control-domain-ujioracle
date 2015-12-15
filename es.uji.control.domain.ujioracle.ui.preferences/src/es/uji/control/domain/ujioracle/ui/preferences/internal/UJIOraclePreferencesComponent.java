package es.uji.control.domain.ujioracle.ui.preferences.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import es.uji.control.domain.ujioracle.UjiOracleConnectionFactoryConfig;

@Component(name = "ujioracle.preferences", immediate = true)
public class UJIOraclePreferencesComponent {

	private static UjiOracleConnectionFactoryConfig config;
	private BundleContext bundlecontext;

	@Activate
	public void activate(ComponentContext componentContext) throws Exception {
		this.bundlecontext = componentContext.getBundleContext();
	}

	@Deactivate
	public void deactivate(ComponentContext componentContext) {
		this.bundlecontext = null;
	}

	public BundleContext getContext() {
		return this.bundlecontext;
	}

	@Reference(policy = ReferencePolicy.STATIC, cardinality = ReferenceCardinality.MANDATORY, name = "ujioracleConnectionFactoryConfig")
	public void bindUJIOracleConnectionFactoryConfig(UjiOracleConnectionFactoryConfig config) {
		this.config = config;
	}

	public void unbindUJIOracleConnectionFactoryConfig(UjiOracleConnectionFactoryConfig config) {
		this.config = null;
	}
	
	public static UjiOracleConnectionFactoryConfig getConfig() {
		return config;
	}
	
}
