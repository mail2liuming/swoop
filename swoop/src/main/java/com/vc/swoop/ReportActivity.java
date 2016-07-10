package com.vc.swoop;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vc.swoop.FeedbackActivity.addFeedbackTask;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.model.ReportMDL;
import com.vc.swoop.webservice.UserWS;

@EActivity(R.layout.activity_report)
public class ReportActivity extends BaseActivity {
	@ViewById
	EditText ad_content;
	private String[] reporttypes = {};
	@ViewById
	LinearLayout llReport;
	@ViewById
	TextView tvReport;
	List<ReportMDL> reports;
	String typeString="";
	@Extra("ad_id")
	String ad_id;
	@AfterViews
	void init() {
		setTitleText("Report");
		reports = ReportMDL.GetDatas();
		reporttypes = new String[reports.size()];
		for (int i = 0; i < reports.size(); i++) {
			reporttypes[i]=reports.get(i).getName();
		}
	}

	@Click(R.id.llReport)
	void llReport() {
		new AlertDialog.Builder(this).setTitle("select report type")
				.setItems(reporttypes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						tvReport.setText(reports.get(which).getName()); 
						typeString=reports.get(which).getId()+"";
					}
				}).setNegativeButton("Cancel", null).show();
	}

	@Click(R.id.ok)
	void ok() {
		String content = ad_content.getText().toString();
		if(TextUtils.isEmpty(typeString))
		{
			DialogHelper.showTost(mContext,"Please select report type" );
		}
		if (!content.trim().equals("")) {
			new addReporyTask().execute("");
		}else
		{
			DialogHelper.showTost(mContext, "Please enter the content briefly");
		}
	}

	class addReporyTask extends AsyncTask<Object, Integer, JSONObject> {

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if (JUtil.checkStaus(result)) {
				DialogHelper.showTost(mContext, "Thank you, we will work on it ASAP.");
				ReportActivity.this.finish();
				 
			} else {
				DialogHelper.showTost(mContext, JUtil.getError(result));
			 
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
			String content = ad_content.getText().toString();
			UserWS ws = new UserWS(mContext);

			JSONObject jsonObject = ws.addAdReports(getSessionId(), ad_id,content, typeString );
			return jsonObject;

		}

	}
}