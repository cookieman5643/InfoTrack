package com.jmt.infotrack;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class InfoTrackService extends Service {

	// Class Constants
	String TAG = new String("InfoTrackApp");

	// Android Objects
	BroadcastReceiver gScreenR; // See screenReceiver().

	NotificationManager gNotifManager;
	static final int NOTIFICATION_ID = 1856; // See servNotify().

	// ##########

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		// Instantiate Android Objects
		gNotifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		servNotify(true);// Persistent notification while service runs.
		screenReceiver(true);// Sets up a receiver for screen state.

		super.onCreate();
	}// end onCreate()

	@Override
	public void onDestroy() {
		servNotify(false);// dismiss the persistent notification
		screenReceiver(false);// unregister the receiver
		super.onDestroy();
	}// end onDestroy()

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}// end onStartCommand()

	// ##########
	// ##########

	/*
	 * This method sets a receiver to listen for when the screen turns on Also,
	 * if false - it unregisters the receiver. I just localized the code even if
	 * it's repetitive.
	 */
	private void screenReceiver(boolean b) {
		if (b) {
			gScreenR = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					// Log.i(TAG, "Screen turned on");
					Toast.makeText(getApplicationContext(), "SCREEN turned on",
							500).show();
				}
			};
			registerReceiver(gScreenR,
					new IntentFilter(Intent.ACTION_SCREEN_ON));
		}// end if
		if (!b) {
			unregisterReceiver(gScreenR);
		}// end if
	}// end screenReceiver()

	/*
	 * This method controls the persistent notification for the service.
	 */
	@SuppressLint("NewApi")
	private void servNotify(boolean b) {
		if (b) {
			Notification.Builder not = new Notification.Builder(this)
					.setContentTitle("InfoTrack Service Running.")
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentText("currently going at it.");
			Notification noti = not.build();
			noti.flags = Notification.FLAG_ONGOING_EVENT;
			gNotifManager.notify(NOTIFICATION_ID, noti);
			Toast.makeText(getApplicationContext(), "servstarted", 500).show();
		}// end if
		if (!b) {
			gNotifManager.cancel(NOTIFICATION_ID);
			Toast.makeText(getApplicationContext(),
					"InfoTrack Service Stopped.", 500).show();
		}// end if
	}

}
