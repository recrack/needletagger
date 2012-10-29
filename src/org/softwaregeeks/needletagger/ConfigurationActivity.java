package org.softwaregeeks.needletagger;

import org.softwaregeeks.needletagger.common.ConfigurationManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class ConfigurationActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener,OnPreferenceClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.configuration);
		Preference preference = (Preference) findPreference("reportingDialog"); 
		preference.setOnPreferenceClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		
		if( ConfigurationManager.CONFIGURATION_ENABLE_SYSTEM_FONT.equals(key) )
		{
			boolean value = sharedPreferences.getBoolean(ConfigurationManager.CONFIGURATION_ENABLE_SYSTEM_FONT,false);
			ConfigurationManager.setEnableSystemFont(value);
		}
		else if( ConfigurationManager.CONFIGURATION_PLAYER_LINK.equals(key) )
		{
			boolean value = sharedPreferences.getBoolean(ConfigurationManager.CONFIGURATION_PLAYER_LINK,false);
			ConfigurationManager.setPlayerLink(value);
		}
		else if( ConfigurationManager.CONFIGURATION_REPORTING.equals(key) )
		{
			boolean value = sharedPreferences.getBoolean(ConfigurationManager.CONFIGURATION_REPORTING,false);
			ConfigurationManager.setReporting(value);
		}
	}

	@Override
	public boolean onPreferenceClick(Preference preferences) {
		Intent intent = new Intent(getApplicationContext(), ReportDialog.class);
		startActivity(intent);
		return true;
	}
}
