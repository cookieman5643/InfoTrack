package com.jmt.infotrack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ActivityViewData extends Activity {
	
	// Class Constants
		String TAG = new String("ActivityViewData");
		public static final boolean D = true; // used for debugging & logs
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			if(D)Log.i(TAG,"+ onCreate()");
			super.onCreate(savedInstanceState);
		
		
		}
		
		

	

}//end class
