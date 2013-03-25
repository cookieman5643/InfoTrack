package com.jmt.infotrack;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;

public class TableActivity extends Activity {

//Class Vars.
	Context mCtx;
	String TAG = new String ("InfoTrackApp");	
//Database Vars
	private InfoDbAdapter mDbHelper;
//Layout Variables
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG,"Table Activity about to set layout clicked");
		setContentView(R.layout.tableactivity_layout);
			
//Instantiate Class Vars.
	mCtx = getApplicationContext();
//Instantiate Layout Views
	listView = (ListView)findViewById(R.id.ta_listview);
//Instantiate Database Vars.
	mDbHelper = new InfoDbAdapter(mCtx);
//OpenDatabase
	mDbHelper.open();
//Fill the List
	fillData();
//CloseDatabase
	mDbHelper.close();
    		
	
	
	}//end onCreate()
	
	private void fillData(){
		Log.i(TAG,"++TableActivity fillData()");
        SimpleCursorAdapter cursorAdapt;
		Cursor sleepCursor = mDbHelper.fetchAllSleep();
		startManagingCursor(sleepCursor);
	
		String[] from = new String[] { InfoDbAdapter.KEY_DATA1,InfoDbAdapter.KEY_DATA2};
    	int[] to = new int[] {R.id.date,R.id.data};
    	Log.i(TAG,"++Table Act before cursoradapt()");
        
    	cursorAdapt = new SimpleCursorAdapter(getApplicationContext(),
    			R.layout.table_row, sleepCursor, from, to);
    	Log.i(TAG,"++Table Act before setting()");
        
		listView.setAdapter(cursorAdapt);
	}//end fillData
	
	
	
	
	
}//end class TableActivity
