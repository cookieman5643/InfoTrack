package com.jmt.infotrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class InfoTrackReceiver extends BroadcastReceiver {

//Class constants
	static String TAG = new String("InfoTrackReceiver");
	Context mCtx;
//Volume Objects
	VolumeControls volControl;
//Intent Calls	
	public static String SOUND_ON = "com.jmt.infotrack.SOUND_ON_INTENT";
	public static String SOUND_OFF = "com.jmt.infotrack.SOUND_OFF_INTENT";
	
//Intent Calls for Sleep
	public static String SLEEP_ON = "com.jmt.infotrack.SLEEP_ON";
	public static String SLEEP_OFF = "com.jmt.infotrack.SLEEP_OFF";
	
	
//screen obj
	public boolean gScreenOff = true;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i(TAG,"InfoTrackReceiver - onReceive() ");
		
//Instantiate Class constants
		mCtx = context;

//"Switching" the different incoming intents to their respective operations.		
		if(intent.getAction().equals(SOUND_ON)){
			volumeControls("on");
		}
		if(intent.getAction().equals(SOUND_OFF)){
			volumeControls("off");
		}
		if(intent.getAction().equals(SLEEP_ON)){
			sleepMode("on");
		}
		if(intent.getAction().equals(SLEEP_OFF)){
			sleepMode("off");
		}
				
		
	}//end onReceive()
	
//Operations Dealing with Volume Control
	public void volumeControls(String str){
	//Values
		String text = new String();
		int volumeLevel;
	//Instantiate Volume constants
		volControl = new VolumeControls(mCtx,TAG);
		volControl.open();
	//Varying Controls Before Operation		
		if(str.equals("on")){
			text = "Turning all volumes ON. ";
			volumeLevel = 4;
		}
		else if(str.equals("off")){
			text = "Turning all volumes OFF. ";
			volumeLevel = 0;
		}
		else{
			text = "Invalid Volume Operation. ";
			volumeLevel = 0;
		}
	//Actual Work of Changing Volume and Reporting		
		Log.i(TAG,"InfoTrackReceiver, " + text);
		Toast.makeText(mCtx, text, Toast.LENGTH_SHORT).show();
		volControl.setAllVolumesTo(volumeLevel);
		volControl.close();
	}//end volumeControls()
	
	public void sleepMode(String str){
	//Varying Controls Before Operation		
		if(str.equals("on")){

			Toast.makeText(mCtx, "lets turn on sleep mode", Toast.LENGTH_SHORT).show();
		}
		else if(str.equals("off")){
		
		}
		else{
			
		}
	}// end sleepMode()
	
	
}//end class
