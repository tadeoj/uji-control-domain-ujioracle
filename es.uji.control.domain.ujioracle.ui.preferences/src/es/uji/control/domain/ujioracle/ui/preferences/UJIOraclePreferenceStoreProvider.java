/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.IPreferenceStoreProvider;
import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class UJIOraclePreferenceStoreProvider implements IPreferenceStoreProvider {

	private IPreferenceStore store;
	
	@Override
	public IPreferenceStore getPreferenceStore() {
		store  = new ScopedPreferenceStore(InstanceScope.INSTANCE, "es.uji.control.domain.ujioracle.ui.preferences");
		return store;
	}
	
}
