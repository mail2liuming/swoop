package com.vc.swoop.common;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;

import cn.jpush.android.api.JPushInterface;

import com.google.analytics.tracking.android.EasyTracker;
import com.vc.swoop.*;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@NoTitle
@EActivity
public class BaseFragmentActivity extends FragmentActivity {
	public Context mContext;
	@ViewById
	public Button btnBaseLeft, btnBaseRight;
	// @ViewById
	// public ImageView imgBaseRight;
	@ViewById
	public TextView tvBaseTitle, tvBaseRight;
	LocationManager locationManager;
	@ViewById
	public ImageView imgBaseLogo;

	@Click(R.id.btnBaseLeft)
	public void goBack() {
		this.finish();
	}

	@Click(R.id.btnBaseRight)
	public void rightClick() {

	}

	@Click(R.id.tvBaseRight)
	public void rightTextClick() {
		
	}

	public TextView getBaseRight() {
		return tvBaseRight;
	}

	public boolean checkLatAndLng() {
		return GetApp().getLatitude() == 0 || GetApp().getLongitude() == 0;
	}

	public Button getLeftButton() {
		return btnBaseLeft;
	}

	public Button getRightButton() {
		return btnBaseRight;
	}

	public void setRightText(String text) {
		tvBaseRight.setText(text);
		tvBaseRight.setVisibility(View.VISIBLE);
		btnBaseRight.setVisibility(View.GONE);
	}

	public void showRightButton(String tx) {
		// tvBaseRight.setText(tx);
		//
		// tvBaseRight.setVisibility(View.VISIBLE);
	}

	// public TextView getRightButton()
	// {
	// return tvBaseRight;
	// }
	// public void setRightImageSrc(int id)
	// {
	// imgBaseRight.setVisibility(View.VISIBLE);
	// imgBaseRight.setImageResource(id);
	// }
	@Override
	protected void onStart() {
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
	    super.onStop();
	    EasyTracker.getInstance(this).activityStop(this);
	}
	@AfterViews
	public void InitBase() {
		ApplicationMamager.getInstance().addActivity(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this;
		if(tvBaseRight!=null)
		{
			
			tvBaseRight.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //�»���
			tvBaseRight.getPaint().setAntiAlias(true);//�����
		}
	}

	public void removeLocation(LocationListener locationListener) {
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
			locationManager = null;
			locationListener = null;
		}
	}

	public boolean netCheck() {
		if (!NetUtil.isOpenNetwork(this)) {
			return false;
		}
		return true;
	}

	public void initLocation(LocationListener locationListener) {
		// ��ȡ��LocationManager����
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		// ������õ�Criteria���󣬻�ȡ���ϴ˱�׼��provider����
		String currentProvider = locationManager.getProvider(
				LocationManager.GPS_PROVIDER).getName();

		// ��ݵ�ǰprovider�����ȡ���һ��λ����Ϣ
		Location currentLocation = locationManager
				.getLastKnownLocation(currentProvider);
		// ���λ����ϢΪnull�����������λ����Ϣ
		// if(currentLocation == null){
		locationManager.requestLocationUpdates(currentProvider, 500, 0,
				locationListener);
		// }

		// //����GPS״̬������
		// locationManager.addGpsStatusListener(gpsListener);

		// ֱ��������һ��λ����ϢΪֹ�����δ������һ��λ����Ϣ������ʾĬ�Ͼ�γ��
		// ÿ��10���ȡһ��λ����Ϣ
		// while(true){
		// currentLocation =
		// locationManager.getLastKnownLocation(currentProvider);
		// if(currentLocation != null){
		// Log.d("Location", "Latitude: " + currentLocation.getLatitude());
		// Log.d("Location", "location: " + currentLocation.getLongitude());
		// break;
		// }else{
		// Log.d("Location", "Latitude: " + 0);
		// Log.d("Location", "location: " + 0);
		// }
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// Log.e("Location", e.getMessage());
		// }
		// }
	}

	public void setTitleText(String str) {
		imgBaseLogo.setVisibility(View.GONE);
		tvBaseTitle.setText(str);
		tvBaseTitle.setVisibility(View.VISIBLE);
	}

	public App GetApp() {
		return App.getApp(this);
	}

	private String getRunningActivityName() {
		try {
			String contextString = mContext.toString();
			return contextString.substring(contextString.lastIndexOf(".") + 1,
					contextString.indexOf("@"));
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}

	}

	public String getSessionId() {
		if (GetApp().getUser() == null)
			return "";
		return GetApp().getUser().getSession_id();
	}

	public void onResume() {
		super.onResume();
//		JPushInterface.onResume(this);
		
		// MobclickAgent.onPageStart(getRunningActivityName()); //ͳ��ҳ��
		// MobclickAgent.onResume(this); //ͳ��ʱ��
	}

	public void onPause() {
		DialogHelper.CloseLoadingDialog();
		super.onPause();
//		JPushInterface.onPause(this);
		// MobclickAgent.onPageEnd(getRunningActivityName()); // ��֤ onPageEnd
		// ��onPause ֮ǰ����,��Ϊ onPause �лᱣ����Ϣ
		// MobclickAgent.onPause(this);
	}

//	public void goLogin() {
//		if (GetApp().getUser() == null) {
//			Intent intent = LoginActivity_.intent(mContext).get();
//			startActivity(intent);
//		}
//	}

	public void checkUser() {
		// if(GetApp().getUser()==null)
		// {
		// Intent intent=LoginActivity_.intent(mContext).get();
		// startActivity(intent);
		// }
	}
	// interface BaseLocationListener
	// {
	// void onChangeLocation
	// }

}