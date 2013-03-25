package com.jmt.infotrack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityListData2 extends Activity {

	// Class Constants
	String TAG = new String("ActivityListData2");
	boolean D = true;

	// Android Constants
	Context gCtx;

	// Database Objects
	private UniverseDbAdapter gDbAdapter;
	String Fill_Path = new String("root");
	String Last_Path;

	// Layout Objects
	ListView gListView;
	TextView gTV_h1, gTV_h2, gTV_h3, gTV_h4, gTV_h5;

	/*
	 * ##########################################################
	 * ######################################### LIFECYLCE ######
	 * ##########################################################
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "+ onCreate()");

		// Set Layout
		setContentView(R.layout.act_listdata2_layout);

		// Instantiate Android Objects
		gCtx = getApplicationContext();

		// Instantiate Database Objects
		gDbAdapter = new UniverseDbAdapter(gCtx);

		// Instantiate Layout Objects
		gListView = (ListView) findViewById(R.id.ald2_lv);

		gTV_h1 = (TextView) findViewById(R.id.ald2_tv1);
		gTV_h2 = (TextView) findViewById(R.id.ald2_tv2);
		gTV_h3 = (TextView) findViewById(R.id.ald2_tv3);
		gTV_h4 = (TextView) findViewById(R.id.ald2_tv4);
		gTV_h5 = (TextView) findViewById(R.id.ald2_tv5);

	}// end onCreate()

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "+ onResume()");

		// Open Database
		if (gDbAdapter != null) {
			gDbAdapter.open();
		}// end if

		// Create some dummy data if first start
		checkFirstStart();

		// Fill Data from a path
		fillData(Fill_Path);
	}// end onResume()

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "+ onPause()");
		// Close Database()
		gDbAdapter.close();
	}// end onPause()

	/*
	 * ##########################################################
	 * ####################################### APPLICATION ######
	 * ##########################################################
	 */

	/*
	 * Create some dummy data for my database
	 */
	public void dummyData() {
		gDbAdapter.fillInitialData();
		DataObj dO = new DataObj();
		dO.setAllDesignations("root", "folders", "folder");
		dO.setData("Sleep Data", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setData("Book Data", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setData("E-Media Data", "", "", "", "");
		gDbAdapter.createEntry(dO);

		
		dO.setAllDesignations("Sleep Data", "_base", "_base");
		dO.setData("", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setAllDesignations("Sleep Data", "_base", "_colNames");
		dO.setData("Action", "Date", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setAllDesignations("Sleep Data", "_base", "_colShow");
		dO.setData("1", "1", "0", "0", "0");
		gDbAdapter.createEntry(dO);
		dO.setAllDesignations("Sleep Data", "data", "data");
		dO.setData("Going to Sleep", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setData("Woke Up", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setData("Get Up", "", "", "", "");
		gDbAdapter.createEntry(dO);
	}// end dummyData

	/*
	 * Check if preference "ActivityListDataFirstStart is true / false if true,
	 * the activity has been run before, and has dummy data if false, it creates
	 * the dummy data
	 */
	public void checkFirstStart() {
		// if(D)Log.i(TAG,"-- Check First Start --");
		if (!getBooleanPreference("ActivityListDataFirstStart")) {
			// if(D)Log.i(TAG,"--Application First Start--");
			setBooleanPreference("ActivityListDataFirstStart", true);

			dummyData();
		}// end if
	}// end checkFirstStart()

	/*
	 * Handles Filling the list view based on Fill_path
	 */
	private void fillData(String fillpath) {
		SimpleCursorAdapter cursorAdapt;
		Cursor uniCursor;

		int method = 1; // For applying different layouts

		switch (method) {
		case 1:
			// Set the Activity title to path name
			setTitle(fillpath);
			// Set the appropraite table headings
			manageTableHeadings(fillpath);
			// Set the listview
			uniCursor = gDbAdapter.fetchAllEntryFromContainer(fillpath, "folders");
			startManagingCursor(uniCursor);
			String[] from4 = new String[] { UniversalDbAdapter.KEY_DATA1 };
			int[] to4 = new int[] { R.id.ldr_data1 };
			cursorAdapt = new SimpleCursorAdapter(gCtx, R.layout.listdata_row,
					uniCursor, from4, to4);
			gListView.setAdapter(cursorAdapt);
			gListView.setOnItemClickListener(lv_listener);
			stopManagingCursor(uniCursor);
			break;
		case 2:
			/*This is going to be the complicated way to list containers, then the entries
			 * under each container, and then each click refills list.
			*/
		default:
			break;
		}// end switch

	}// end fillData()

	/*
	 * Manages the headings for the data output
	 */
	public void manageTableHeadings(String fillpath) {
		// TODO: I could make this a horizontal list of TV returned in a CURSOR
		// = better

		/*
		 * Gets a cursor of the row where path="fillpath, container = "_base",
		 * type = "_colShow"
		 */
		Cursor cursColShow, cursColNames;
		cursColShow = gDbAdapter.fetchAllDesignationEntry(fillpath, "_base",
				"_colShow");
		cursColNames = gDbAdapter.fetchAllDesignationEntry(fillpath, "_base",
				"_colNames");
		if (cursColShow.moveToFirst()) {// if the cursor isn't empty
			if (cursColNames.moveToFirst()) {// if naming row exists
				int id = cursColShow.getInt(0); // row id defining show
				int id2 = cursColNames.getInt(0); // row id defining colNames
				if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA1)
						.equals("1")) {
					gTV_h1.setText(gDbAdapter.getEntryText(id2,
							UniversalDbAdapter.KEY_DATA1));
				} else{
					gTV_h1.setVisibility(View.GONE);}
				if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA2)
						.equals("1")) {
					
					gTV_h2.setText(gDbAdapter.getEntryText(id2,
							UniversalDbAdapter.KEY_DATA2));
				} else{
					gTV_h2.setVisibility(View.GONE);}
				if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA3)
						.equals("1")) {
					gTV_h3.setText(gDbAdapter.getEntryText(id2,
							UniversalDbAdapter.KEY_DATA3));
				} else
					gTV_h3.setVisibility(View.GONE);
				if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA4)
						.equals("1")) {
					gTV_h4.setText(gDbAdapter.getEntryText(id2,
							UniversalDbAdapter.KEY_DATA4));
				} else
					gTV_h4.setVisibility(View.GONE);
				if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA5)
						.equals("1")) {
					gTV_h5.setText(gDbAdapter.getEntryText(id2,
							UniversalDbAdapter.KEY_DATA5));
				} else
					gTV_h5.setVisibility(View.GONE);

				
				setTitle(""+gTV_h1.getVisibility()+gTV_h2.getVisibility());
			}// end if (colNames)
		}// end if (colShow)
		else {
			// Hides all headings if there is no row describing how to have
			// headings (i.e. bad programming by me)
			gTV_h1.setVisibility(View.GONE);
			gTV_h2.setVisibility(View.GONE);
			gTV_h3.setVisibility(View.GONE);
			gTV_h4.setVisibility(View.GONE);
			gTV_h5.setVisibility(View.GONE);
		}
	}// end manageTableHeadnigs()
	
	/*
	 * ########################################################
	 * ######################################## LISTENERS #####
	 * ########################################################
	 */
	/*
	 * Handles List view clicks
	 */
	OnItemClickListener lv_listener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapter, View view, int pos,
				long id) {

			int task = 1;
			/*
			 * Manage different click actions
			 */
			switch (task) {
			case 1:
				Last_Path = Fill_Path;
				Fill_Path = gDbAdapter.getEntryText(id,
						UniversalDbAdapter.KEY_DATA1);
				fillData(Fill_Path);
				return;
			default:
				return;
			}// end switch

		}
	};// end OnItemClickListener()

	/*
	 * ################################################################
	 * ######################################## MENU METHODS ##########
	 * ################################################################
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_listdata2_layout, menu);
		return true;
	}

	/*
	 * Handles actions for each menu item's click
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_ald2_add:// brings up adding new field.
			Toast.makeText(gCtx, "Add", 500).show();
			//Dialog dia = onCreateDialog(item);
			//dia.show();
			break;
		case R.id.menu_ald2_settings: // start the settings activity
									// (can clear data in database
			startActivity(new Intent(gCtx, Preferences.class));
			break;
		case R.id.menu_ald2_clear:
			Toast.makeText(gCtx, "Reset Database", 500).show();
			gDbAdapter.deleteAll();
			setBooleanPreference("ActivityListDataFirstStart", false);
			checkFirstStart();
			fillData(Fill_Path);
			break;
		default:
			break;
		}// end switch

		return super.onMenuItemSelected(featureId, item);
	}
	
	
	/*
	 * ####################################################################
	 * ################################### GENERAL_PREFERENCE_METHODS #####
	 * ####################################################################
	 */
	// TODO: revise these copied from Quick Alarm. Possible set in helper file.
	public SharedPreferences getSharedPrefs() {
		return PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

	public boolean getBooleanPreference(String name) {
		SharedPreferences prefs = getSharedPrefs();
		return prefs.getBoolean(name, false);
	}

	public int getIntPreference(String name) {
		SharedPreferences prefs = getSharedPrefs();
		return prefs.getInt(name, -1);
	}

	public String getStringPreference(String name) {
		SharedPreferences prefs = getSharedPrefs();
		return prefs.getString(name, "Default Preference");
	}

	public void setStringPreference(String title, String body) {
		SharedPreferences prefs = getSharedPrefs();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(title, body);
		editor.commit();
	}

	public void setIntPreference(String title, int body) {
		SharedPreferences prefs = getSharedPrefs();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(title, body);
		editor.commit();
	}

	public void setBooleanPreference(String title, boolean body) {
		SharedPreferences pref = getSharedPrefs();
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(title, body);
		editor.commit();
	}

}// end class
