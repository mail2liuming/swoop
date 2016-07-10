package com.vc.swoop;
 
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.common.MD5Helper;
import com.vc.swoop.common.NetUtil;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.UserWS;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView; 
@EActivity(R.layout.activity_forget_pwd)
public class ForgetPwdStep3Activity extends BaseActivity {
	@ViewById
	EditText password_new,password_again;
	@Extra("phonecode")
	String phone;
	@Extra("code")
	String code;  
	@AfterViews
	void init() {
		setTitleText("New Password");
	}


	@Click(R.id.btn_finish)
	void btn_finish() {
		if (!NetUtil.isOpenNetwork(mContext)) { 
			DialogHelper.showTost(mContext,ForgetPwdStep3Activity.this.getString(R.string.No_Networks_Found));
			return;
		}
		
		String new_pwd = password_new.getText().toString();
		String again_pwd = password_again.getText().toString();
		if (!new_pwd.equals(again_pwd)) {
			DialogHelper.showTost(mContext,ForgetPwdStep3Activity.this.getString(R.string.pwd_no_yy)); 
			return;
		}
		
		if ( new_pwd.length() < 4) {
			DialogHelper.showTost(mContext,ForgetPwdStep3Activity.this.getString(R.string.pwd_4)); 
			return;
		}
		 new forgetPwdTask().execute("");
	}
	class forgetPwdTask extends AsyncTask<Object, Integer, String> {

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
				 
				ForgetPwdStep3Activity.this.finish();
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
			String new_pwd = password_new.getText().toString();
			String again_pwd = password_again.getText().toString();
			JSONObject jsonObject =ws.forget_pwd(phone, new_pwd, code);
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


