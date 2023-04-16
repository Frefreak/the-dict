package nz.carso.thedict

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import nz.carso.thedict.preferences.TriggerText

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        for (pair in Config.enabledDict) {
            val testPref = findPreference<TriggerText>(pair.prefTestKey)
            testPref?.bindOnClickHandler(pair.klass::testAPI)
        }
    }
}