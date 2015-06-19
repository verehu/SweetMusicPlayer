package com.huwei.sweetmusicplayer.services;

//import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.ui.listeners.ShakeListener;
 

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class ShakeService extends Service {
	private static final String TAG="ShakeService";
	private ShakeListener mShakeListener;
	private Vibrator vibrator;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		vibrator=(Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
		mShakeListener=new ShakeListener(getBaseContext());
		mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
			
			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				 mShakeListener.stop();
				 startVibrator();
				 //vibrator.cancel();
				 mShakeListener.start();
			}
		} );
	}


	void startVibrator(){
		Log.i(TAG,"shake");
		
		vibrator.vibrate(500);
		
		
//		MusicManager.getInstance().random_a_song();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		mShakeListener.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mShakeListener.stop();
	}

	
}
