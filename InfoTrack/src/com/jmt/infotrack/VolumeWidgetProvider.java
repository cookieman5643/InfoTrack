package com.jmt.infotrack;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class VolumeWidgetProvider extends AppWidgetProvider {

	
	public static String SOUND_OFF = "com.jmt.infotrack.SOUND_OFF_INTENT";
	public static String SOUND_ON = "com.jmt.infotrack.SOUND_ON_INTENT";
	

	    @Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	    	
	    AppWidgetManager Mng = AppWidgetManager.getInstance(context);
//	    //if(intent.getAction().equals(SOUND_OFF)){
//	    	//Log.i("VolumeAPP","Oin receixke part");
//			
//			
//	    	Toast.makeText(context, "Volume Widget Provider Hit!", 500);
//	    }
	    	
	    	
	    	
		super.onReceive(context, intent);
	}

		public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	        final int N = appWidgetIds.length;

	        // Perform this loop procedure for each App Widget that belongs to this provider
	        for (int i=0; i<N; i++) {
	            int appWidgetId = appWidgetIds[i];

	            // Create an Intent to launch ExampleActivity
	            //Intent intent = new Intent(context, VolumeActivity.class);
	            Intent intent3 = new Intent(SOUND_OFF);
	            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent3, 0);

	            // Get the layout for the App Widget and attach an on-click listener
	            // to the button
	            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.volumewidget);
	            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

	            Intent intent2 = new Intent(SOUND_ON);
	            PendingIntent pi2 = PendingIntent.getBroadcast(context, 0, intent2,0);
	            views.setOnClickPendingIntent(R.id.widget_button2, pi2);
	            Log.i("VolumeAPP","send something");
	    		
	    		
	            
	            
	            // Tell the AppWidgetManager to perform an update on the current app widget
	            appWidgetManager.updateAppWidget(appWidgetId, views);
	        }
	    }

	
}
