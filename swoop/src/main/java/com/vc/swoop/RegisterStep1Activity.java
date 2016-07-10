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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView; 
 
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.db.CountryDB;
import com.vc.swoop.model.Country;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.UserWS;
import com.vc.swoop.widgets.ComfrimDialog.OnConfirmListener;

@EActivity(R.layout.activity_register_step1)
public class RegisterStep1Activity extends BaseActivity {

	@ViewById
	TextView tvCountry,tvNum;
	
	@ViewById
	EditText et_mobile;
	
	
	
 
	List<Country> countries;

	private String[] countrys = { "New Zealand" };
	@Click(R.id.tv_terms)
	void onTearm(View v) {
		Intent intent=ContentActivity_.intent(mContext).get();
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
	@Click(R.id.btn_ok_account)
	void account()
	{
		if (et_mobile.getText().toString().equals("")) {
			DialogHelper.showTost(this,RegisterStep1Activity.this.getString(R.string.mobile_blank));
		} else {
			alertConfirm();
		}
	}
	
	@AfterViews
	void init() { 
		setTitleText("Registration");
		countries = new CountryDB(this).select();
		countrys = new String[countries.size()];
		for (int i = 0; i < countries.size(); i++) {
			countrys[i]=countries.get(i).getName();
		}
		et_mobile.addTextChangedListener(new TextWatcher() {
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
				if(et_mobile.getText().toString().equals("0"))
				{
					et_mobile.setText("");
				}
			}

		});

	}

	public void onRegister(View v) {
		
	}

	 

	private void alertConfirm() {
		DialogHelper.showComfrimDialog(
				mContext,
				"Confirm Mobile Number",
				"We are to send a verification code \n to "
						+ tvNum.getText().toString()+ et_mobile.getText().toString(),
				null, new OnConfirmListener() {

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
				Intent intent =RegisterStep2Activity_.intent(mContext).get();
				intent.putExtra("phonecode", tvNum.getText().toString());
				intent.putExtra("phone", et_mobile.getText().toString());
				startActivity(intent);
				RegisterStep1Activity.this.finish();
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
			phone=tvNum.getText().toString()+phone;
			JSONObject jsonObject =ws.get_code(phone, "0");
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
