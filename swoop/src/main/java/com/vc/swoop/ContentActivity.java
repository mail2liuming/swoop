package com.vc.swoop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.webkit.WebView;

import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.webservice.AdWS;
import com.vc.swoop.webservice.UserWS;

@EActivity(R.layout.activity_content)
public class ContentActivity extends BaseActivity {
	@Extra("itype")
	String itype;
	@ViewById
	WebView webView1;

	@AfterViews
	void init() {
		new getAppInfoTask().execute("");
	}

	class getAppInfoTask extends AsyncTask<Object, Integer, JSONObject> {

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			try {
				if (itype.equals("about")) {
					setTitleText("About");

				} else if (itype.equals("terms")) {
					setTitleText("Terms & Conditions");
				} else if (itype.equals("privacy")) {
					setTitleText("Privacy");
				}

				webView1.loadData(result.getString(itype), "text/html", "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			DialogHelper.ShowLoadingDialog(mContext, "loading data");
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			UserWS ws = new UserWS(mContext);
			JSONObject jsonObject = ws.app_info();
			return jsonObject;

		}

	}
}
