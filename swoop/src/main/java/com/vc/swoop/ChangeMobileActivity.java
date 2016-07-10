package com.vc.swoop;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.vc.swoop.db.CountryDB;
import com.vc.swoop.model.Country;
import com.vc.swoop.webservice.UserWS;
import com.vc.swoop.widgets.ComfrimDialog.OnConfirmListener;

@EActivity(R.layout.activity_change_mobile)
public class ChangeMobileActivity extends BaseActivity {

	@ViewById
	EditText et_mobile;
	@ViewById
	TextView tvCountry,tvNum;
	// @ViewIoc(id = R.id.country)
	// Spinner spin;
	@ViewById
	Button btn_ok_account;
	private String[] countrys = { "New Zealand" };
	List<Country> countries;
	@Click(R.id.tv_terms)
	void tv_terms() {
		Intent intent = ContentActivity_.intent(mContext).get();
		intent.putExtra("itype", "terms");
		startActivity(intent);
	}
 
	@Click(R.id.tvCountry)
	void onSelectCountry() {
		new AlertDialog.Builder(this)
		.setTitle("select country")
		.setItems(countrys, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				tvCountry.setText(countries.get(which).getName());
				tvNum.setText("+"+countries.get(which).getNum());
			}
		})  
		.setNegativeButton("Cancel", null)
		.show();
	}
	@Click(R.id.imgJian)
	void imgJian()
	{
		onSelectCountry();
	}
	@AfterViews
	void initView() { 
		setTitleText("Change Mobile Number");
		countries = new CountryDB(this).select();
		countrys = new String[countries.size()];
		for (int i = 0; i < countries.size(); i++) {
			countrys[i]=countries.get(i).getName();
		}
	}
	@Click(R.id.btn_ok_account)
	void onRegister() {
		if (et_mobile.getText().toString().equals("")) {
			DialogHelper.showTost(mContext, getString(R.string.mobile_blank)); 
		} else {
			alertConfirm();
		}
	}
 

	private void alertConfirm() {
		DialogHelper.showComfrimDialog(mContext, "Confirm Mobile Number", "We are to send a verification code \n to "+tvNum.getText().toString()+ et_mobile.getText().toString(), null, new OnConfirmListener() {
			
			@Override
			public void confirm() {
				// TODO Auto-generated method stub
				new getSMSCodeTask().execute("");
			}
		}, null, null);
		
	}
	class getSMSCodeTask extends AsyncTask<Object, Integer, String> {

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
				Intent intent =ChangeMobileStep2Activity_.intent(mContext).get(); 
				intent.putExtra("phone", tvNum.getText().toString()+et_mobile.getText().toString());
				startActivity(intent);
				ChangeMobileActivity.this.finish();
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
			String phone=et_mobile.getText().toString(); 
			JSONObject jsonObject =ws.get_code(tvNum.getText().toString()+phone, "0");
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
