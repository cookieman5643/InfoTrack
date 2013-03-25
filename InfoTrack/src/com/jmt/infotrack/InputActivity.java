package com.jmt.infotrack;

import java.util.Date;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class InputActivity extends Activity {

//Class Vars.
	Context mCtx;
	String TAG = new String ("InfoTrackApp");
	
//DataBase Objects	
	private InfoDbAdapter mDbHelper;
	
//Declare Layout Objects
	Button btn_sleep,btn_wake,btn_up;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG,"Input Activity about to set layout clicked");
		setContentView(R.layout.inputactivity_layout);
		

//Instantiating Class Vars.		
		mCtx = getApplicationContext();
		
//Instantiate Layout Objects
		btn_sleep = (Button)findViewById(R.id.ia_sleep_btn);
		btn_sleep.setOnClickListener(btnListener);
		btn_wake = (Button)findViewById(R.id.ia_wake_btn);
		btn_wake.setOnClickListener(btnListener);
		btn_up = (Button)findViewById(R.id.ia_up_btn);
		btn_up.setOnClickListener(btnListener);
		
//DataBase Object Instantiate / open
		mDbHelper = new InfoDbAdapter(mCtx);
    	
    	
    	
		
	}//end onCreate()
	
	
	
	
	@Override
	protected void onPause() {
//Close Database		
	//	mDbHelper.close();
		super.onPause();
	}




	OnClickListener btnListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
//Open Database
			mDbHelper.open();
			Date date = new Date();
				
	
			String datecreated = new String();			
				datecreated = "" + date.getTime();
			String dateeditted;
				dateeditted = "" + date.getTime();
			String dateviewed;
				dateviewed = ""+ date.getTime();
			String category;
				category = "sleephabits";
			String data1;
				data1 = "" + date.toLocaleString();
			String data2;
			
			switch(v.getId()){
			case R.id.ia_sleep_btn:
				Toast.makeText(mCtx, "Sleep Clicked.", Toast.LENGTH_SHORT).show();
				data2 = "sleep";
				mDbHelper.createSleep(datecreated, dateeditted, dateviewed, category, data1, data2);
				mDbHelper.close();	
				return;
			case R.id.ia_wake_btn:
				Toast.makeText(mCtx, "Wake Clicked.", Toast.LENGTH_SHORT).show();
				data2 = "woke";
				mDbHelper.createSleep(datecreated, dateeditted, dateviewed, category, data1, data2);
				mDbHelper.close();	
				return;
			case R.id.ia_up_btn:
				Toast.makeText(mCtx, "Up Clicked.", Toast.LENGTH_SHORT).show();
				data2 = "up";
				mDbHelper.createSleep(datecreated, dateeditted, dateviewed, category, data1, data2);
				mDbHelper.close();	
				return;
			default:
				return;
			}//end switch
			
		
		}//end onClick
	};//end ibListener
	
}
