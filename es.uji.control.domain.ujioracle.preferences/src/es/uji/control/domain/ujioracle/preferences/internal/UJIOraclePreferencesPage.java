/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.preferences.internal;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
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
public class UJIOraclePreferencesPage extends FieldEditorPreferencePage {

	private UjiOracleConnectionFactoryConfig config;
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

	public UJIOraclePreferencesPage() {
		super(GRID);
	}

	@Reference(policy = ReferencePolicy.STATIC, cardinality = ReferenceCardinality.MANDATORY, name = "ujioracleConnectionFactoryConfig")
	public void bindUJIOracleConnectionFactoryConfig(UjiOracleConnectionFactoryConfig config) {
		this.config = config;
	}

	public void unbindUJIOracleConnectionFactoryConfig(UjiOracleConnectionFactoryConfig config) {
		this.config = null;
	}
	
	@Override
	protected void createFieldEditors() {
		
		setDescription("Parametros de configuración de la conexión JDBC contra la B.D. de la Universitat Jaume I.");
		
		addField(new StringFieldEditor("url", "URL : ", getFieldEditorParent()));
		addField(new StringFieldEditor("user", "Usuario : ", getFieldEditorParent()));
		addField(new StringFieldEditor("password", "Password : ", getFieldEditorParent()));
		addField(new StringFieldEditor("schema", "Esquema : ", getFieldEditorParent()));
		addField(new StringFieldEditor("sid", "SID : ", getFieldEditorParent()));
		
	}

}
