package com.vc.swoop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
 
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;

import com.vc.swoop.common.App;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.db.AppConfigDAL;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.UserWS;

@EActivity(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

	User user = null;
	@ViewById
	LinearLayout ll_login, ll_unlogin;
	@ViewById
	TextView tv_username, tv_mobile;
	boolean isLogin;
	@AfterViews
	void init() {
		//setTitleText("Settings");
		hideGoBack();
		try {
			user = GetApp().getUser();
			if (user != null && !user.getSession_id().equals("")) {
				isLogin = true;
			} else {
				isLogin = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			showIsLogin();
		}
	}
	private void showIsLogin() {
		if (isLogin) {
			tv_username.setText(user.getName());
			tv_mobile.setText(user.getPhone());
			ll_login.setVisibility(View.VISIBLE);
			ll_unlogin.setVisibility(View.GONE);
		} else {
			ll_login.setVisibility(View.GONE);
			ll_unlogin.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		init();
		super.onResume();
	}
	@Click(R.id.ll_register)
	void register() {
		Intent intent=RegisterStep1Activity_.intent(mContext).get();
		startActivity(intent);
	}

	@Click(R.id.ll_tolog)
	void tolog() {
		Intent intent=LoginActivity_.intent(mContext).get();
		startActivity(intent);
	}

	@Click(R.id.ll_about2)
	void about2() {
		Intent intent=ContentActivity_.intent(mContext).get();
		intent.putExtra("itype", "about");
		startActivity(intent);
	}

	@Click(R.id.ll_terms2)
	void terms2() {
		Intent intent=ContentActivity_.intent(mContext).get();
		intent.putExtra("itype", "terms");
		startActivity(intent);
	}

	@Click(R.id.ll_privacy1)
	void privacy1() {
		Intent intent=ContentActivity_.intent(mContext).get();
		intent.putExtra("itype", "privacy");
		startActivity(intent);
	}

	@Click(R.id.ll_about1)
	void about1() {
		Intent intent=ContentActivity_.intent(mContext).get();
		intent.putExtra("itype", "privacy");
		startActivity(intent);
	}
	@Click(R.id.ll_feedback2)
	void feedback2()
	{
		Intent intent=FeedbackActivity_.intent(mContext).get(); 
		startActivity(intent);
	}
	@Click(R.id.ll_feedback1)
	void feedback1()
	{
		Intent intent=FeedbackActivity_.intent(mContext).get(); 
		startActivity(intent);
	}
	@Click(R.id.ll_terms1)
	void terms1() {
		Intent intent=ContentActivity_.intent(mContext).get();
		intent.putExtra("itype", "terms");
		startActivity(intent);
	}

	@Click(R.id.ll_privacy2)
	void privacy2() {
		Intent intent=ContentActivity_.intent(mContext).get();
		intent.putExtra("itype", "privacy");
		startActivity(intent);
	}

	@Click(R.id.ll_change_pass)
	void change_pass() {
		Intent intent=ChangePwdActivity_.intent(mContext).get(); 
		startActivity(intent);
	}

	@Click(R.id.ll_change_mobilenumber)
	void change_mobilenumber() {
		Intent intent=ChangeMobileActivity_.intent(mContext).get(); 
		startActivity(intent);
	}

	@Click(R.id.ll_logout)
	void logout() {
		alertConfirm();
	}

	Dialog dialog;

	private void alertConfirm() {
		if (dialog == null) {
			dialog = new Dialog(this, R.style.activity_translucent);
			dialog.setContentView(R.layout.tip_confirm);
		}
		Button btn_confirm, btn_cancel;
		final TextView tv_title = (TextView) dialog.getWindow().findViewById(
				R.id.tv_title);
		tv_title.setText("Confirm Logout");
		final TextView tv_message = (TextView) dialog.getWindow().findViewById(
				R.id.tv_message);
		// tv_message.setText("Are you confirm to logout system?");
		tv_message.setText("");
		btn_confirm = (Button) dialog.getWindow()
				.findViewById(R.id.btn_confirm);
		btn_cancel = (Button) dialog.getWindow().findViewById(R.id.btn_cancel);

		btn_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new LogoutTask().execute("");
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
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if(!TextUtils.isEmpty(result))
			{
				DialogHelper.showTost(mContext, result);
			}else {
				GetApp().setUser(null);
				user = null;
				
				new AppConfigDAL(mContext).insert(AppConfigDAL.user_key,"");
				JPushInterface.setAliasAndTags(mContext, "", null);
				dialog.dismiss();
				init();
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
			UserWS ws = new UserWS(mContext);
			ws.logout_clientid(App.getApp(mContext).getUser()
					.getSession_id());
			return null;
		}

	}
}
