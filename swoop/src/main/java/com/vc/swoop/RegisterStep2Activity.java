package com.vc.swoop;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;
 
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView; 
 
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.webservice.UserWS;

@EActivity(R.layout.activity_register_step2)
public class RegisterStep2Activity extends BaseActivity {

	@Extra("phonecode")
	String phonecode;
	@Extra("phone")
	String phone;
	
	@ViewById
	TextView tv_phone;
	@ViewById
	EditText etCode; 
	
	
	
	@Click(R.id.btn_ok_account)
	void onSubmit()
	{
		final String cd = etCode.getText().toString();
		if(!TextUtils.isEmpty(cd))
		{
			new checkSMSCodeTask().execute("");
		}
		
	} 
	@AfterViews
	void init() {
		tv_phone.setText(phonecode + phone);
		setTitleText("Enter Code");
	}
	
	class checkSMSCodeTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if(!TextUtils.isEmpty(result))
			{
				DialogHelper.showTost(mContext, result);
			}
			else
			{
				Intent intent =RegisterStep3Activity_.intent(mContext).get();
				intent.putExtra("phonecode", phonecode+phone);
				intent.putExtra("code", etCode.getText().toString());
				startActivity(intent);
				RegisterStep2Activity.this.finish();
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
		protected String doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			UserWS ws=new UserWS(mContext);
			String code=etCode.getText().toString(); 
			JSONObject jsonObject =ws.check_code(phonecode+phone, code);
			if(JUtil.checkStaus(jsonObject))
			{
				
				return "";
			}
			else
			{
				return JUtil.getError(jsonObject);
			}
			 
		}

	}
	
	
	
	
}
