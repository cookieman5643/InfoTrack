package com.jmt.infotrack;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

//Class Vars.
	Context gCtx;
	String TAG = new String ("InfoTrackApp");
	
//Declaring Layout Objects
	ImageButton ib_input,ib_table,ib_settings,ib_graph;
	
	Button gBtnStart,gBtnStop,gBtn1,gBtn2,gBtn3;
	
	AlarmManager gAlarmMan;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity_layout);
		
//Instantiating Class Vars.		
		gCtx = getApplicationContext();
//Instantiating Layout Objects
		ib_input = (ImageButton)findViewById(R.id.ma_input_btn);
			ib_input.setOnClickListener(ibListener);
		ib_table = (ImageButton)findViewById(R.id.ma_table_btn);
			ib_table.setOnClickListener(ibListener);
		ib_settings = (ImageButton)findViewById(R.id.ma_settings_btn);
			ib_settings.setOnClickListener(ibListener);
		ib_graph = (ImageButton)findViewById(R.id.ma_graph_btn);
			ib_graph.setOnClickListener(ibListener);
	
		gBtnStart = (Button) findViewById(R.id.ma_btn_startservice);
			gBtnStart.setOnClickListener(btnListener);
		gBtnStop = (Button) findViewById(R.id.ma_btn_stopservice);
			gBtnStop.setOnClickListener(btnListener);
		gBtn1 = (Button) findViewById(R.id.ma_btn_btn1);
			gBtn1.setOnClickListener(btnListener);
		gBtn2 = (Button) findViewById(R.id.ma_btn_btn2);
			gBtn2.setOnClickListener(btnListener);
		gBtn3 = (Button) findViewById(R.id.ma_btn_btn3);
			gBtn3.setOnClickListener(btnListener);
			
		gAlarmMan = (AlarmManager) getSystemService(gCtx.ALARM_SERVICE);
		
//TODO: make statistics page and alarm and volume pages			
			
//		Intent filter = new Intent("android.intent.action.SCREEN_ON");
//		BroadcastReceiver bR = new BroadcastReceiver();
//		registerReceiver(, filter);
		
		
		
	}//end onCreate()
	
	
	/*
	This method controls buttons click behavior, particularly the service ones
	*/
	OnClickListener btnListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.ma_btn_startservice:
				Intent ssintent = new Intent(getApplicationContext(),InfoTrackService.class);
				gCtx.startService(ssintent);
				return;
			case R.id.ma_btn_stopservice:
				Intent stsintent = new Intent(getApplicationContext(),InfoTrackService.class);
				gCtx.stopService(stsintent);
				return;
			case R.id.ma_btn_btn1:
				//gAlarmMan.
				return;
			case R.id.ma_btn_btn2:
				return;
			case R.id.ma_btn_btn3:
				return;
			default:
				return;
			}
			
		}
	
	};//end btnListener
	
	
	
	OnClickListener ibListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.ma_input_btn:
				Log.i(TAG,"input btn clicked");
				Toast.makeText(gCtx, "Input Button Clicked.", Toast.LENGTH_SHORT).show();
				Intent go = new Intent(gCtx, InputActivity.class);
				startActivity(go);
				return;
			case R.id.ma_table_btn:
				Log.i(TAG,"table btn clicked");
				Toast.makeText(gCtx, "Table Button Clicked.", Toast.LENGTH_SHORT).show();
				Intent goTable = new Intent(gCtx, TableActivity.class);
				startActivity(goTable);
				return;
			case R.id.ma_settings_btn:
				Log.i(TAG,"setting btn clicked");
				Toast.makeText(gCtx, "Settings Button Clicked.", Toast.LENGTH_SHORT).show();
				Intent goPref = new Intent(gCtx, Preferences.class);
				startActivity(goPref);
				return;
			case R.id.ma_graph_btn:
				Log.i(TAG,"graph btn clicked");
				//Toast.makeText(gCtx, "Graph Button Clicked.", Toast.LENGTH_SHORT).show();
				Intent goListData = new Intent(gCtx, ListDataActivity.class);
				startActivity(goListData);
				return;
			default:
				return;
			}//end switch
		}//end onClick
	};//end ibListener
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
