package com.vc.swoop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.db.AppConfigDAL;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.UserWS;
@EActivity(R.layout.activity_feedback)
public class FeedbackActivity extends BaseActivity{
	@ViewById
	EditText ad_content;
	
	@AfterViews
	void init()
	{
		setTitleText("feedback");
	}
	
	@Click(R.id.ok)
	void ok()
	{
		String content=ad_content.getText().toString();
		if(!content.equals(""))
		{
			new addFeedbackTask().execute("");
		}
		else {
			DialogHelper.showTost(mContext, "Please enter the content briefly");
		}
	}
	class addFeedbackTask extends AsyncTask<Object, Integer, JSONObject> {

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if(JUtil.checkStaus(result))
			{
				 
				DialogHelper.showTost(mContext, JUtil.getData(result));
				FeedbackActivity.this.finish();
			}
			else
			{
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
			String content=ad_content.getText().toString();
			UserWS ws=new UserWS(mContext);
			 
			JSONObject jsonObject =ws.addAdFeedbacks(getSessionId(), content);
			return jsonObject;
			 
		}

	}
}
