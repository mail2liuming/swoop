package com.vc.swoop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;

import cn.jpush.android.api.JPushInterface;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;

@EActivity(R.layout.activity_splash)
@NoTitle
public class SplashActivity extends Activity {

	private static final String MASTERSECRET = "4cpWIvWU3GAPv5Ao8LgCZ1";

	private String appkey = "";
	private String appsecret = "";
	private String appid = "";
	@Extra("ad_id")
	String ad_id;
	@AfterViews
	void init() {
		if(TextUtils.isEmpty(ad_id))
		{
			new Handler().postDelayed(new Runnable() {
				public void run() {
					RedirectMainActivity();
				}
			}, 2000);
		}else {
			Intent i = MainActivity_.intent(this).get();
			i.putExtra("ad_id", ad_id);
			startActivity(i);
			SplashActivity.this.finish();
		}
		
	}

	private void RedirectMainActivity() {
		Intent i = MainActivity_.intent(this).get();
		startActivity(i);
		SplashActivity.this.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
}
