package com.jmt.infotrack;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Preferences extends PreferenceActivity {
	
	PreferenceManager gPM;
	Preference gClearData,gFirstStart;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Preference clearData = new Preference(getApplicationContext());
		clearData.setOnPreferenceClickListener(listener);
		
		addPreferencesFromResource(R.xml.preferences);
		
		gPM = getPreferenceManager();
		gClearData = (Preference) gPM.findPreference("pref_cleardata");
		gClearData.setOnPreferenceClickListener(listener);
		
	}//end onCreate()
	
	OnPreferenceClickListener listener = new OnPreferenceClickListener(){

		@Override
		public boolean onPreferenceClick(Preference preference) {
			Toast.makeText(getApplicationContext(), "Hey",500).show();
			
			UniversalDbAdapter a = new UniversalDbAdapter(getApplicationContext());
			a.open();
			a.deleteAll();
			a.close();
			
			
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean("ListActivityFirstStart", false);
			editor.commit();

			
			return false;
		}
		
	};
	
	
}
