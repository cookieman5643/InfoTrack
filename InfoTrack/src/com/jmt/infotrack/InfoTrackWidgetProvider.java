package com.jmt.infotrack;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class InfoTrackWidgetProvider extends AppWidgetProvider {

//Class Constants
	public static String SLEEP_ON = "com.jmt.infotrack.SLEEP_ON";
	public static String SLEEP_OFF = "com.jmt.infotrack.SLEEP_OFF";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {

	    AppWidgetManager Mng = AppWidgetManager.getInstance(context);
		
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
	
		Log.i("InfoTrackApp","sending something from infotrack widget");
		
		final int N = appWidgetIds.length;
		
		 // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            
//TODO: check that service isn't already on so that double clicks don't add another entry            
            Intent intent = new Intent(SLEEP_ON);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0 , intent, 0);
            
            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.infotrackwidget);
            views.setOnClickPendingIntent(R.id.infotrackwidget_btn, pendingIntent);
            
            

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
      
        }//end for
		
        super.onUpdate(context, appWidgetManager, appWidgetIds);
		
        
	}//end onUpdate()

	
	
	
	
}//end InfoTrackWidgetProvider
