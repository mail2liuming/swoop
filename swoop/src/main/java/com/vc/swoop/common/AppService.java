package com.vc.swoop.common;

import java.util.List;

import org.json.JSONObject;
 

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;

public class AppService extends Service {
	public static String CLIENT_ID = "calder";
	private static final String ACTION_START = CLIENT_ID + ".START";
	private static final String ACTION_STOP = CLIENT_ID + ".STOP";
	public static final String TAG = "AppService";
	private boolean isDo = true;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void actionStart(Context ctx) {
		Intent i = new Intent(ctx, AppService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}

	// Static method to stop the service
	public static void actionStop(Context ctx) {
		Intent i = new Intent(ctx, AppService.class);
		i.setAction(ACTION_STOP);
		ctx.startService(i);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		log("Creating service");

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		log("Service started with intent=" + intent);

		// Do an appropriate action based on the intent.
		if (intent == null) {
			log("onStart() intent is null");
			// start();
			return;
		}
		log("onStart() intent is null" + intent.getAction());
		if (intent.getAction().equals(ACTION_STOP) == true) {
			stop();
			stopSelf();
		} else if (intent.getAction().equals(ACTION_START) == true) {
			start();
		}
	}

	private void log(String message) {
		log(message, null);
	}

	private void log(String message, Throwable e) {
		if (e != null) {
			Log.e(TAG, message, e);

		} else {
			Log.i(TAG, message);
		}

	}

	private synchronized void start() {
		log("Starting service...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isDo) {

					try {
						// ���ϳ����������磬�������˱������͵��ӿڹص�����
						

					} catch (Exception e) {

					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private synchronized void stop() {
		// Do nothing, if the service is not running.
		isDo = false;

	}
}