package com.vc.swoop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NoTitle;

import com.vc.swoop.common.App;
import com.vc.swoop.common.ApplicationMamager;
import com.vc.swoop.webservice.UserWS;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

@EActivity(R.layout.activity_main_tab)
@NoTitle
public class MainActivity extends TabActivity {

	private static final String BOOKMARK_ID = "TAB_BOOKMARK";
	public static final int BOOKMARK_TAB = 3;
	protected static final int CHECKING = 0;
	protected static final int DO_APP_FINISH_LAUNCH = 5;
	protected static final int DO_VERSION_CHECK = 3;
	protected static final int END_APP_FINISH_LAUNCH = 7;
	protected static final int END_VERSION_CHECK = 4;
	protected static final int FINISH = 1;
	public static final String HOME_ID = "TAB_HOME";
	public static final int HOME_TAB = 0;
	private static final String MORE_ID = "TAB_MORE";
	public static final int MORE_TAB = 4;
	private static final String MYACCOUNT_ID = "TAB_MYACCOUNT";
	public static final int MY_ACCOUNT_TAB = 2;
	static final String POSTAD_ID = "TAB_POSTAD";
	public static final int POST_AD_TAB = 1;
	private static final int STANBY_RQ = 5;
	protected static final int START_APP_FINISH_LAUNCH = 6;
	protected static final int START_VERSION_CHECK = 2;
	private static int mSelectedTab;
	public static int rootTab = 0;
	private FrameLayout flTabBookmark;
	private ImageView ivBookmark;
	private TextView tvBookmark;
	private FrameLayout flTabMyAccount;
	private ImageView ivMyAccount;
	private TextView tvMyAccount;
	private FrameLayout flTabPostAd;
	private ImageView ivPostAd;
	private TextView tvPostAd;
	private FrameLayout flTabPublish;
	private ImageView ivPublish;
	private TextView tvPublish;
	private boolean isFirstLaunch = true;
	private boolean isResumeFromInternalActivity = false;
	@Extra("ad_id")
	String ad_id;
	TabHost.OnTabChangeListener mOnTabChangedListner = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String paramString) {
			if (paramString.equals("TAB_POSTAD"))
				MainActivity.this.switchTab(0);
			if (paramString == "TAB_MYACCOUNT")
				MainActivity.this.switchTab(1);
			if (paramString == "TAB_BOOKMARK")
				MainActivity.this.switchTab(2);
			if (paramString == "TAB_PUBLISH")
				MainActivity.this.switchTab(3);
		}
	};

	public static TabHost tabHost;
	static {
		mSelectedTab = 0;
	}

	private void addTab(String paramString1, String paramString2, int paramInt,
			Intent paramIntent) {
		tabHost = getTabHost();
		TabHost.TabSpec localTabSpec = tabHost.newTabSpec(paramString1)
				.setContent(paramIntent);
		localTabSpec.setIndicator(paramString2,
				getResources().getDrawable(paramInt));
		tabHost.addTab(localTabSpec);
		tabHost.setOnTabChangedListener(mOnTabChangedListner);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		exit();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 需要监听的事件
			exit();
			return false;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();

		}

		return false;

	}

	Dialog dialog;

	private void exit() {

		if (dialog == null) {
			dialog = new Dialog(this, R.style.activity_translucent);
			dialog.setContentView(R.layout.tip_out);
		}
		Button btn_confirm, btn_cancel;
		final TextView tv_message = (TextView) dialog.getWindow().findViewById(
				R.id.tv_message);
		tv_message.setText("Are you sure you want to exit\n\n");
		btn_confirm = (Button) dialog.getWindow()
				.findViewById(R.id.btn_confirm);
		btn_cancel = (Button) dialog.getWindow().findViewById(R.id.btn_cancel);

		btn_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				// logouClientId();
				ApplicationMamager.getInstance().exit();
				
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
	}

	class LogoutTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected String doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			UserWS ws=new UserWS(MainActivity.this);
			ws.logout_clientid(App.getApp(MainActivity.this).getUser().getSession_id());
			return null;
		}

	}

	private void logouClientId() {

		if (App.getApp(this).getUser() == null) {
			return;
		}
		new LogoutTask().execute("");
	}

	private void setupTabHost() {
		flTabPostAd = ((FrameLayout) findViewById(R.id.flTabPostAd));
		ivPostAd = ((ImageView) findViewById(R.id.ivPostAd));
		tvPostAd = ((TextView) findViewById(R.id.tvPostAd));
		flTabPostAd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				MainActivity.rootTab = 0;
				MainActivity.this.switchTab(0);
			}
		});
		flTabMyAccount = ((FrameLayout) findViewById(R.id.flTabMyAccount));
		ivMyAccount = ((ImageView) findViewById(R.id.ivMyAccount));
		tvMyAccount = ((TextView) findViewById(R.id.tvMyAccount));
		flTabMyAccount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				MainActivity.rootTab = 1;
				MainActivity.this.switchTab(1);
			}
		});
		flTabBookmark = ((FrameLayout) findViewById(R.id.flTabBookmark));
		ivBookmark = ((ImageView) findViewById(R.id.ivBookmark));
		tvBookmark = ((TextView) findViewById(R.id.tvBookmark));
		flTabBookmark.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				MainActivity.rootTab = 2;
				MainActivity.this.switchTab(2);
			}
		});
		flTabPublish = ((FrameLayout) findViewById(R.id.flTabPublish));
		ivPublish = ((ImageView) findViewById(R.id.ivPublish));
		tvPublish = ((TextView) findViewById(R.id.tvPublish));
		flTabPublish.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				MainActivity.rootTab = 3;
				MainActivity.this.switchTab(3);
			}
		});
	}
	@AfterViews
	void init() {
		setupTabHost();
//		Intent intent = new Intent(this, NearbyListActivity.class);
//		Bundle mBundle = new Bundle();
//		mBundle.putInt("type", 0);
//		intent.putExtras(mBundle);
		addTab("TAB_POSTAD", "TAB_POSTAD", 2130837557, NearbyListActivity_.intent(this).get());
		addTab("TAB_MYACCOUNT", "TAB_MYACCOUNT", 2130837556,SellingListActivity_.intent(this).get());
		addTab("TAB_BOOKMARK", "TAB_BOOKMARK", 2130837544, WatchingListActivity_.intent(this).get());
		addTab("TAB_PUBLISH", "TAB_PUBLISH", 2130837555, SettingActivity_.intent(this).get());
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			int currentTab = bundle.getInt("currentTab");
			switchTab(currentTab);
		} else {
			switchTab(0);
		}
		if(TextUtils.isEmpty(ad_id))
		{
			 
		}else {
			Intent i = ADDetailActivity_.intent(this).get();
			i.putExtra("ad_id", ad_id);
			i.putExtra("who", 0);
			startActivity(i);
			 
		}
	}

	public void setResumeFromInternalActivity(boolean paramBoolean) {
		isResumeFromInternalActivity = paramBoolean;
	}

	public void switchTab(int paramInt) {
		getTabHost().setCurrentTab(paramInt);
		mSelectedTab = paramInt;
		switch (paramInt) {
		case 0:
			flTabPostAd
					.setBackgroundResource(R.drawable.active_navigation_menu);
			flTabMyAccount.setBackgroundResource(android.R.color.transparent);
			flTabBookmark.setBackgroundResource(android.R.color.transparent);
			flTabPublish.setBackgroundResource(android.R.color.transparent);

			ivPostAd.setImageResource(R.drawable.nearby_icon_pressed);
			ivMyAccount.setImageResource(R.drawable.selling_icon);
			ivBookmark.setImageResource(R.drawable.watchlist_icon);
			ivPublish.setImageResource(R.drawable.settings_icon);

			tvPostAd.setTextColor(getResources().getColor(R.color.white));
			tvMyAccount.setTextColor(getResources().getColor(R.color.black));
			tvBookmark.setTextColor(getResources().getColor(R.color.black));
			tvPublish.setTextColor(getResources().getColor(R.color.black));
			break;
		case 1:
			flTabPostAd.setBackgroundResource(R.drawable.transparent);
			flTabMyAccount
					.setBackgroundResource(R.drawable.active_navigation_menu);
			flTabBookmark.setBackgroundResource(android.R.color.transparent);
			flTabPublish.setBackgroundResource(android.R.color.transparent);

			ivPostAd.setImageResource(R.drawable.nearby_icon);
			ivMyAccount.setImageResource(R.drawable.selling_icon_pressed);
			ivBookmark.setImageResource(R.drawable.watchlist_icon);
			ivPublish.setImageResource(R.drawable.settings_icon);

			tvPostAd.setTextColor(getResources().getColor(R.color.black));
			tvMyAccount.setTextColor(getResources().getColor(R.color.white));
			tvBookmark.setTextColor(getResources().getColor(R.color.black));
			tvPublish.setTextColor(getResources().getColor(R.color.black));
			break;
		case 2:
			flTabPostAd.setBackgroundResource(R.drawable.transparent);
			flTabMyAccount.setBackgroundResource(android.R.color.transparent);
			flTabBookmark
					.setBackgroundResource(R.drawable.active_navigation_menu);
			flTabPublish.setBackgroundResource(android.R.color.transparent);

			ivPostAd.setImageResource(R.drawable.nearby_icon);
			ivMyAccount.setImageResource(R.drawable.selling_icon);
			ivBookmark.setImageResource(R.drawable.watchlist_icon_pressed);
			ivPublish.setImageResource(R.drawable.settings_icon);

			tvPostAd.setTextColor(getResources().getColor(R.color.black));
			tvMyAccount.setTextColor(getResources().getColor(R.color.black));
			tvBookmark.setTextColor(getResources().getColor(R.color.white));
			tvPublish.setTextColor(getResources().getColor(R.color.black));
			break;
		case 3:
			flTabPostAd.setBackgroundResource(R.drawable.transparent);
			flTabMyAccount.setBackgroundResource(android.R.color.transparent);
			flTabBookmark.setBackgroundResource(android.R.color.transparent);
			flTabPublish
					.setBackgroundResource(R.drawable.active_navigation_menu);

			ivPostAd.setImageResource(R.drawable.nearby_icon);
			ivMyAccount.setImageResource(R.drawable.selling_icon);
			ivBookmark.setImageResource(R.drawable.watchlist_icon);
			ivPublish.setImageResource(R.drawable.settings_icon_pressed);

			tvPostAd.setTextColor(getResources().getColor(R.color.black));
			tvMyAccount.setTextColor(getResources().getColor(R.color.black));
			tvBookmark.setTextColor(getResources().getColor(R.color.black));
			tvPublish.setTextColor(getResources().getColor(R.color.white));
			break;
		}
	}
}