package com.vc.swoop.common;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
 

public class ApplicationMamager {
	private List<Activity> mList = new LinkedList<Activity>();
	private static ApplicationMamager instance;

	private ApplicationMamager() {
	}

	public synchronized static ApplicationMamager getInstance() {
		if (null == instance) {
			instance = new ApplicationMamager();
		}
		return instance;
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
		// Log.e("add Activity", activity.getLocalClassName());
	}

	

	public void exit() {//Context c

		try {
			if (mList != null) {
				for (Activity activity : mList) {

					if (activity != null)
						activity.finish();
				}
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}

		} catch (Exception e) {
			//Log.e("�˳�", e.getMessage());
		}
	}
	public void goMainActivity(Context mContext)
	{
		//Intent intent = MainActivity_.intent(mContext).get();
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//mContext.startActivity(intent);
	}
	/**
	 * ���������棬�����������Ľ���
	 * 
	 */
	public void clearActivity() {

		for (int  i=0;i<mList.size() ; i++) {
			String a=mList.get(i).getLocalClassName().toLowerCase();
			if (mList.get(i) != null
					&& (!mList.get(i).getLocalClassName().toLowerCase()
							.equals("mainactivity_"))) {
				mList.get(i).finish();
			//	mList.remove(i);
				// Log.e("destory Activity", activity.getLocalClassName());
			}
		}
		
		System.gc();
	}

	public void quit() {
		int ActivitySize=mList.size();
		for (int  i=ActivitySize-1;i>0 ; i--) {
			if (mList.get(i) != null)
			{
				mList.get(i).finish();
				mList.remove(i);
			}
		}
	}
}
