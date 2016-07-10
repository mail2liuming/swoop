package com.vc.swoop;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.jpush.android.api.JPushInterface;

import com.vc.swoop.common.App;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.db.AppConfigDAL;
import com.vc.swoop.model.AD;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.UserWS;
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity{
	@ViewById
	EditText et_username_login,et_pwd_login;
	
	@ViewById
	Button btn_ok_login,to_register;
	
	@Click(R.id.btn_ok_login)
	void login()
	{
		String phone=et_username_login.getText().toString();
		String password=et_pwd_login.getText().toString();
		if(phone.equals("")|| password.equals(""))
		{
			DialogHelper.showTost(mContext, "Please make sure you have entered correct login information.");
			return;
		}
		new LoginTask().execute("");
	}
	@Click(R.id.to_register)
	void register()
	{
		Intent intent=RegisterStep1Activity_.intent(mContext).get();
		startActivity(intent);
	}
	@Click(R.id.forgot_pwd)
	void forgot_pwd()
	{
		Intent intent=ForgetPwdActivity_.intent(mContext).get();
		startActivity(intent);
	}
	@AfterViews
	void init()
	{
		setTitleText("Login");
		et_username_login.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
//				if(et_username_login.getText().toString().equals("0"))
//				{
//					et_username_login.setText("");
//				}
			}

		});
	}
	class LoginTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if(!TextUtils.isEmpty(result))
			{
				DialogHelper.showTost(mContext, result);
			}else {
				LoginActivity.this.finish();
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
			UserWS ws=new UserWS(LoginActivity.this);
			String phone=et_username_login.getText().toString();
			if(phone.substring(0,1).equals("0"))
			{
				phone=phone.substring(1);
			}
			String password=et_pwd_login.getText().toString();
			JSONObject jsonObject =ws.login(GetApp().getImei(), "0", phone, password, "1");
			if(JUtil.checkStaus(jsonObject))
			{
				User user=JUtil.parseObject(JUtil.getData(jsonObject, "user"), User.class);
				
				new AppConfigDAL(mContext).insert(AppConfigDAL.user_key, JUtil.toJson(user));
				GetApp().setUser(user);
				if(user!=null)
				{
					JPushInterface.setAliasAndTags(mContext, "", null);
					JPushInterface.setAliasAndTags(mContext, user.getUser_id()+"", null);
				}
				return "";
			}
			else
			{
				return JUtil.getError(jsonObject);
			}
			 
		}

	}
}
