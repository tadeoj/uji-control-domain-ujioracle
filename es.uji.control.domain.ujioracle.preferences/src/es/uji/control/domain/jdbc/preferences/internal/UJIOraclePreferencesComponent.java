package es.uji.control.domain.jdbc.preferences.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

@Component(name="ujioracle.preferences", immediate=true)
public class UJIOraclePreferencesComponent {

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
	
}
