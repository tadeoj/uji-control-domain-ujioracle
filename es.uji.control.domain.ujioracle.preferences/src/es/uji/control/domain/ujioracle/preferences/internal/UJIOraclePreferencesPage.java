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

public class UJIOraclePreferencesPage extends FieldEditorPreferencePage {

	public UJIOraclePreferencesPage() {
		super(GRID);
	}
	
	@Override
	protected void createFieldEditors() {
		
		setDescription("Parametros de configuración de la conexión JDBC contra la B.D. de la Universitat Jaume I.");
		
		addField(new StringFieldEditor("url", "URL : ", getFieldEditorParent()));
		addField(new StringFieldEditor("user", "Usuario : ", getFieldEditorParent()));
		StringFieldEditor fieldEditor = new StringFieldEditor("password", "Password : ", getFieldEditorParent());
		addField(fieldEditor);
		addField(new StringFieldEditor("schema", "Esquema : ", getFieldEditorParent()));
		addField(new StringFieldEditor("sid", "SID : ", getFieldEditorParent()));
		
		fieldEditor.getTextControl(getFieldEditorParent()).setEchoChar('*');
		
	}
	
	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		UJIOraclePreferencesComponent.getConfig().checkPreferences();
		return result;
	}
	

}
