package es.uji.control.domain.jdbc.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.IPreferenceStoreProvider;
import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class JDBCPStoreProvider implements IPreferenceStoreProvider {

	public JDBCPStoreProvider() {
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, "es.uji.control.domain.jdbc.preferences");
	}

}
