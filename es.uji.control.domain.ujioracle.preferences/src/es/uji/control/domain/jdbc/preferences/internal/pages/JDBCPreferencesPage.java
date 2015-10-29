/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.jdbc.preferences.internal.pages;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

public class JDBCPreferencesPage extends FieldEditorPreferencePage {

	public JDBCPreferencesPage() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {

		addField(new ComboFieldEditor("prefCombo", "A combo field", new String[][] { { "display1", "value1" }, { "display2", "value2" } }, getFieldEditorParent()));
		addField(new ColorFieldEditor("prefColor", "Color for table items : ", getFieldEditorParent()));
		addField(new BooleanFieldEditor("prefBoolean", "A boolean : ", getFieldEditorParent()));
		addField(new StringFieldEditor("prefString", "A string : ", getFieldEditorParent()));
		
	}

}
