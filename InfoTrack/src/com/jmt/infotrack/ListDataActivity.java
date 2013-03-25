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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListDataActivity extends Activity {

	// Class Constants
	String TAG = new String("ListDataActivity");
	public static final boolean D = true; // used for debugging & logs

	// Database Objects
	private UniversalDbAdapter gDbAdapter;
	String Fill_Path = new String("root");
	String Last_Path;

	// Android Objects
	Context gCtx;

	// Layout Objects
	ListView gListView;
	TextView gTV_title;
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
		setContentView(R.layout.act_listdata_layout);

		// Instantiate Android Objects
		gCtx = getApplicationContext();

		// Instantiate Database Objects
		gDbAdapter = new UniversalDbAdapter(gCtx);
		// Instantiate Layout Objects
		gListView = (ListView) findViewById(R.id.lda_lv);
		gTV_title = (TextView) findViewById(R.id.lda_tv_title);

		gTV_h1 = (TextView) findViewById(R.id.lda_tv1);
		gTV_h2 = (TextView) findViewById(R.id.lda_tv2);
		gTV_h3 = (TextView) findViewById(R.id.lda_tv3);
		gTV_h4 = (TextView) findViewById(R.id.lda_tv4);
		gTV_h5 = (TextView) findViewById(R.id.lda_tv5);

	}// end onCreate()

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "+ onResume()");

		// Open Database
		if (gDbAdapter != null) {
			gDbAdapter.open();
			if (D)
				Log.i(TAG, "gDbAdapter opened successfully.");
		}

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
	 * ################################################################
	 * ######################################## MENU METHODS ##########
	 * ################################################################
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_listdata_layout, menu);
		return true;
	}

	/*
	 * Handles actions for each menu item's click
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_ld_add:// brings up adding new field.
			Toast.makeText(gCtx, "Add", 500).show();
			Dialog dia = onCreateDialog(item);
			dia.show();
			break;
		case R.id.menu_ld_settings: // start the settings activity
									// (can clear data in database
			startActivity(new Intent(gCtx, Preferences.class));
			break;
		default:
			break;
		}// end switch

		return super.onMenuItemSelected(featureId, item);
	}

	/*
	 * #################################################################
	 * ######################################## FILLING VIEWS ##########
	 * #################################################################
	 */
	/*
	 * This method handles filling in the list view to its intended data as well
	 * as the headers
	 */
	private void fillData(String fillpath) {
		Log.i(TAG, "fillData");
		SimpleCursorAdapter cursorAdapt;
		Cursor UniCursor;

		int method = 4;
		/*
		 * Offers a way to Alternate the way list views work. Increment in order
		 * made.
		 */
		switch (method) {
		case 1:
			UniCursor = gDbAdapter.fetchAllEntry();
			startManagingCursor(UniCursor);
			String[] from = new String[] { UniversalDbAdapter.KEY_DATECREATED,
					UniversalDbAdapter.KEY_DATA1 };
			int[] to = new int[] { R.id.ldr_date, R.id.ldr_data1 };
			cursorAdapt = new SimpleCursorAdapter(gCtx, R.layout.listdata_row,
					UniCursor, from, to);
			gListView.setAdapter(cursorAdapt);
			gListView.setOnItemClickListener(lv_listener);
			stopManagingCursor(UniCursor);

			break;
		case 2:
			UniCursor = gDbAdapter.fetchAllCategoryEntry(fillpath, "_base");
			startManagingCursor(UniCursor);
			String[] from2 = new String[] { UniversalDbAdapter.KEY_CATEGORY,
					UniversalDbAdapter.KEY_DATA1 };
			int[] to2 = new int[] { R.id.ldr_data1, R.id.ldr_data2 };
			cursorAdapt = new SimpleCursorAdapter(gCtx, R.layout.listdata_row,
					UniCursor, from2, to2);
			gListView.setAdapter(cursorAdapt);
			gListView.setOnItemClickListener(lv_listener);
			stopManagingCursor(UniCursor);
			break;
		case 3:
			/*
			 * Currently, changes title to fillpath, addresses table headings,
			 * fills data with just data1
			 */
			gTV_title.setText(fillpath);
			manageTableHeadings(fillpath);
			UniCursor = gDbAdapter.fetchAllCategoryEntry(fillpath, "_base");
			startManagingCursor(UniCursor);
			String[] from3 = new String[] { UniversalDbAdapter.KEY_DATA1 };
			int[] to3 = new int[] { R.id.ldr_data1 };
			cursorAdapt = new SimpleCursorAdapter(gCtx, R.layout.listdata_row,
					UniCursor, from3, to3);
			gListView.setAdapter(cursorAdapt);
			gListView.setOnItemClickListener(lv_listener);
			stopManagingCursor(UniCursor);
			break;
		case 4:
			/*
			 * Makes title the activity title, gets rid of title TV
			 */
			setTitle(fillpath); //sets title of act. page
			gTV_title.setVisibility(View.GONE); //gets rid of TV
			manageTableHeadings(fillpath);
			UniCursor = gDbAdapter.fetchAllCategoryEntry(fillpath, "_base");
			startManagingCursor(UniCursor);
			String[] from4 = new String[] { UniversalDbAdapter.KEY_DATA1 };
			int[] to4 = new int[] { R.id.ldr_data1 };
			cursorAdapt = new SimpleCursorAdapter(gCtx, R.layout.listdata_row,
					UniCursor, from4, to4);
			gListView.setAdapter(cursorAdapt);
			gListView.setOnItemClickListener(lv_listener);
			stopManagingCursor(UniCursor);
			break;
			
		default:
			break;
		}// end switch
		Log.i(TAG, "Fill Data Successful.");
	}// end fillData()

	/*
	 * Manages the headings for the data output
	 */
	public void manageTableHeadings(String fillpath) {
		// TODO: I could make this a horizontal list of TV returned in a CURSOR
		// = better

		// Gets a cursor of the row where cat=fillpath, subCat = "heading"
		Cursor curs;
		curs = gDbAdapter.fetchAllCategoryEntry(fillpath, "heading");
		if (curs.moveToFirst()) {// if the cursor isn't empty
			int id = curs.getInt(0);

			// This changes all 5 data headings to their respective headings
			// designated by
			// cat=fillpath subCat = "heading"
			gTV_h1.setText(gDbAdapter.getEntryText(id,
					UniversalDbAdapter.KEY_DATA1));
			gTV_h2.setText(gDbAdapter.getEntryText(id,
					UniversalDbAdapter.KEY_DATA2));
			gTV_h3.setText(gDbAdapter.getEntryText(id,
					UniversalDbAdapter.KEY_DATA3));
			gTV_h4.setText(gDbAdapter.getEntryText(id,
					UniversalDbAdapter.KEY_DATA4));
			gTV_h5.setText(gDbAdapter.getEntryText(id,
					UniversalDbAdapter.KEY_DATA5));

			// Hides the TV if heading text is nothing. Layout automatically
			// equally splits width
			if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA2)
					.equals("")) {
				gTV_h2.setVisibility(View.GONE);
			}
			if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA3)
					.equals("")) {
				gTV_h3.setVisibility(View.GONE);
			}
			if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA4)
					.equals("")) {
				gTV_h4.setVisibility(View.GONE);
			}
			if (gDbAdapter.getEntryText(id, UniversalDbAdapter.KEY_DATA5)
					.equals("")) {
				gTV_h5.setVisibility(View.GONE);
			}
		}// end if
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
	 * ####################################################################
	 * ######################################## ACTIVITY METHODS ##########
	 * ####################################################################
	 */
	/*
	 * Create some dummy data for my database
	 */
	public void dummyData() {
		DataObj dO = new DataObj();
		dO.setData("Sleep Tracking", "", "", "", "");
		dO.setAllCategories("root", "_base");
		gDbAdapter.createEntry(dO);
		dO.setData("Books", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setData("VirtualShows", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setAllCategories("Sleep Tracking", "_base");
		dO.setData("Going to Bed", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setData("Woke Up", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setData("Getting Up", "", "", "", "");
		gDbAdapter.createEntry(dO);
		dO.setAllCategories("root", "heading");
		dO.setData("Topics", "", "", "", "");
		gDbAdapter.createEntry(dO);
	}// end dummyData

	/*
	 * ########################################################
	 * ######################################## LISTENERS #####
	 * ########################################################
	 */
	/*
	 * Handles the click events on my list view Initial: clicking on a row
	 * (folder) will open a new query as a sub folder
	 */
	OnItemClickListener lv_listener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapter, View view, int pos,
				long id) {

			int task = 2;
			/*
			 * Manage different click actions
			 */
			switch (task) {
			case 1:
				Toast.makeText(
						getApplicationContext(),
						"Item id=" + id + ". D1="
								+ gDbAdapter.fetchEntry(id).getString(6), 500)
						.show();
				gTV_title.setText(gDbAdapter.getEntryText(id,
						UniversalDbAdapter.KEY_DATA1));
				return;
			case 2:
				Last_Path = Fill_Path;
				Fill_Path = gDbAdapter.getEntryText(id,
						UniversalDbAdapter.KEY_DATA1);
				fillData(Fill_Path);
				gTV_title.setText(Fill_Path);
				return;
			default:
				return;
			}// end switch

		}
	};// end OnItemClickListener()

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

	/*
	 * Check if preference "ListActivityFirstStart is true / false if true, the
	 * activity has been run before, and has dummy data if false, it creates the
	 * dummy data
	 */
	public void checkFirstStart() {
		// if(D)Log.i(TAG,"-- Check First Start --");
		if (!getBooleanPreference("ListActivityFirstStart")) {
			// if(D)Log.i(TAG,"--Application First Start--");
			setBooleanPreference("ListActivityFirstStart", true);

			dummyData();
		}// end if
	}// end checkFirstStart()

	/*
	 * #############################################################
	 * ######################################### DIALOG MANAGE #####
	 * #############################################################
	 */
	protected Dialog onCreateDialog(MenuItem m) {
		final Dialog dialog;
		dialog = new Dialog(this);

		switch (m.getItemId()) {
		case R.id.menu_ld_add:
			dialog.setContentView(R.layout.dialog_ld_add2_layout);
			dialog.setTitle(R.string.ld_dialog_title);
			break;
		default:
			break;
		}// end switch
		return dialog;
	}// end onCreateDialog()

	/*
	 * protected Dialog onCreateDialog(View v){ //, View v
	 * if(L)Log.i(TAG,"--CreatingDialog--"); final Dialog dialog; dialog = new
	 * Dialog(this); //find which button int here = -1; for(int i=0;
	 * i<PRESET_NUMBER; i++){ if(v.getId()==preset[i].getId()) here=i; }
	 * if(here!=-1){ //it found which button final String str = new
	 * String("button"+(here+1));
	 * 
	 * dialog.setContentView(R.layout.custom_dialog);
	 * dialog.setTitle("Set Values for " + str);
	 * 
	 * final EditText nameValue = (EditText)
	 * dialog.findViewById(R.id.dialog_et_name);
	 * nameValue.setText(getStringPreference(str+"Text")); final EditText
	 * timeValue = (EditText) dialog.findViewById(R.id.dialog_et_time);
	 * timeValue.setText(""+getIntPreference(str)); Button setValue = (Button)
	 * dialog.findViewById(R.id.btn_setValues); setValue.setOnClickListener(new
	 * OnClickListener(){
	 * 
	 * @Override public void onClick(View v) { if(nameValue.getText()!=null){
	 * setStringPreference(str+"Text",nameValue.getText().toString()); }
	 * if(timeValue.getText()!=null){ setIntPreference(str,
	 * Integer.parseInt(timeValue.getText().toString())); } dialog.cancel();
	 * //refresh to update text! onResume(); }//end onClick() });//end
	 * onClickListner Button cancel = (Button) dialog.findViewById(R.id.cancel);
	 * cancel.setOnClickListener(new OnClickListener(){
	 * 
	 * @Override public void onClick(View v) { dialog.cancel(); }//end onClick()
	 * });//end onClickListener
	 * 
	 * 
	 * }//end if return dialog; }//end onCreateDialog
	 */

}// end class ListDataActivity
