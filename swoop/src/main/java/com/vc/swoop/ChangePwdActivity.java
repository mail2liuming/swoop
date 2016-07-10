package com.vc.swoop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;
 
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
import com.vc.swoop.common.BaseActivity; 
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.common.NetUtil;
import com.vc.swoop.db.AppConfigDAL;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.UserWS;
@EActivity(R.layout.activity_change_pass)
public class ChangePwdActivity extends BaseActivity{
	@ViewById 
	EditText password_old ,password_new,password_again; 
	@ViewById
	Button btn_finish; 
	@AfterViews
	void initView() {
		setTitleText("Change Password");
	}


	@Click(R.id.btn_finish)
	void btn_finish() {
		if (!NetUtil.isOpenNetwork(mContext)) {
			DialogHelper.showTost(mContext,getString(R.string.No_Networks_Found));
			return;
		}
		
		if (GetApp().getUser() == null) {
			DialogHelper.showTost(mContext,getString(R.string.user_fail_login)); 
		}
		
		String old_pwd = password_old.getText().toString();
		String new_pwd = password_new.getText().toString();
		String again_pwd = password_again.getText().toString();
		if (!new_pwd.equals(again_pwd)) {
			DialogHelper.showTost(mContext,getString(R.string.not_match_pwd)); 
			return;
		}
		
		if (old_pwd.length() < 4 || new_pwd.length() < 4) {
			DialogHelper.showTost(mContext,getString(R.string.pwd_4));  
			return;
		}
		 new changePwdTask().execute("");
		
	}
	class changePwdTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if(!TextUtils.isEmpty(result))
			{
				DialogHelper.showTost(mContext, result);
			}else
			{
				ChangePwdActivity.this.finish();
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
			String old_pwd = password_old.getText().toString();
			String new_pwd = password_new.getText().toString(); 
			UserWS ws=new UserWS(mContext);
			 
			JSONObject jsonObject =ws.modiy_pwd(GetApp().getUser().getSession_id(), old_pwd, new_pwd);
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
