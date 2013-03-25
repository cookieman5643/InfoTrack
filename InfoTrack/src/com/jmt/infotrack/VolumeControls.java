package com.jmt.infotrack;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

public class VolumeControls {

		
		
		AudioManager mAudioManager;
		public final Context mCtx;
		public String TAG = new String("VolumeClass");
		
		
		int sSystem = AudioManager.STREAM_SYSTEM;
		int sAlarm = AudioManager.STREAM_ALARM;
		int sMusic = AudioManager.STREAM_MUSIC;
		int sNotification = AudioManager.STREAM_NOTIFICATION;
		int sRing = AudioManager.STREAM_RING;
		int sCall = AudioManager.STREAM_VOICE_CALL;
		int sDTMF = AudioManager.STREAM_DTMF;
		public int[] mAllSounds= {sSystem,sAlarm,sMusic,
				sNotification,sRing,sCall,sDTMF};
		
		
		/*
		 * Call an instance of this class
		 * @param ctx - context of the application calling it
		 */
		public VolumeControls(Context ctx){
			this.mCtx = ctx;
		}
		
		/*
		 * Call an instance of this class
		 * @param ctx - for the context
		 * @param appName - the app from which it came for
		 * 		logging purposes
		 */
		public VolumeControls(Context ctx, String appName){
			this(ctx);
			this.TAG = appName + " " + TAG;
		}
		
		/*
		 * Open instance of this class, assign the audio 
		 * manager (if available) 
		 */
		public void open(){
			
			if(mCtx.getSystemService(Context.AUDIO_SERVICE)!=null){
				mAudioManager = (AudioManager) 
						mCtx.getSystemService(Context.AUDIO_SERVICE);
				Log.i(TAG,"**AudioManager Assigned.**");
			}
			else{
				this.close();
			}
		}

		/*
		 * Close instance, basically it frees an unassigns 
		 * the audio manager
		 */
		public void close(){
			mAudioManager = null;
		}
		
		/*
		 * Sets all audio streams to level 
		 * @param - level is the audio value each gets
		 */
		public void setAllVolumesTo(int level){
			
			for(int i=0; i<mAllSounds.length; i++){
				mAudioManager.setStreamVolume(mAllSounds[i],level,0);
			}
			Log.i(TAG,"**Set all Volumes to 'level'. ");
		}
		
		/*
		 * Set all streams Mute / unMute
		 * @param - value of setting: true = mute, false = unmute
		 */
		public void setAllVolumesMute(boolean val){
			for(int i=0; i<mAllSounds.length; i++){
				mAudioManager.setStreamMute(mAllSounds[i],val);
			}
			Log.i(TAG,"**Set Volume mute to: "+ val +".");
		}
		

		
		//TODO: return a string of current / max
		
}// end class
	
