package com.vc.swoop;

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
import com.vc.swoop.common.MD5Helper;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.UserWS;

@EActivity(R.layout.activity_register_step3)
public class RegisterStep3Activity extends BaseActivity {
	@ViewById
	EditText et_nickname,password,password_again;
	 
	 
	@Extra("phonecode")
	String phone;
	@Extra("code")
	String code;  
	@AfterViews
	void init() {
		setTitleText("Registration");
	}
	
	 
	@Click(R.id.btn_ok_account)
	void onSubmit() {
		 
		String clientId = "";
//		if (clientId == null) {
//			T.ShortToast("initialization failure");
//			return ;
//		}
		String imei =GetApp().getImei();
		
		
		
		String pwd = password.getText().toString();
		String pwdAgain = password_again.getText().toString();
		String name = et_nickname.getText().toString();
		
		if (!pwd.equals(pwdAgain)) {
			DialogHelper.showTost(mContext,RegisterStep3Activity.this.getString(R.string.pwd_no_yy));
			return;
		} 
		
		if (pwd.equals("") || pwdAgain.equals("")) {
			DialogHelper.showTost(mContext,RegisterStep3Activity.this.getString(R.string.pwd_no_null));
			return;
		} 
		
		if (name.equals("")) {
			DialogHelper.showTost(mContext,RegisterStep3Activity.this.getString(R.string.user_no_null));
			return;
		}
		
		new regTask().execute("");
	}
	
	class regTask extends AsyncTask<Object, Integer, String> {

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
				 
				RegisterStep3Activity.this.finish();
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
			String name=et_nickname.getText().toString(); 
			String pwd = password.getText().toString();
			pwd=MD5Helper.GetMD5(pwd);
			JSONObject jsonObject =ws.register(GetApp().getImei(), "0", phone, pwd, "0", "0", name, code);
			if(JUtil.checkStaus(jsonObject))
			{
				User user=JUtil.parseObject(JUtil.getData(jsonObject, "user"), User.class);
				GetApp().setUser(user);
				return "";
			}
			else
			{
				return JUtil.getError(jsonObject);
			}
			 
		}

	}
}
