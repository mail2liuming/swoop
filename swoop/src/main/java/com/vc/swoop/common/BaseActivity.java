package com.vc.swoop.common;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;

import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import com.vc.swoop.*;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@NoTitle
@EActivity
public class BaseActivity extends Activity {
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
    boolean threadLoadLocation = true;
    public LocationClient mLocationClient = null;

    @Click(R.id.btnBaseLeft)
    public void goBack() {
        this.finish();
    }

    @Click(R.id.btnBaseRight)
    public void rightClick() {

    }

    public void hideGoBack() {
        btnBaseLeft.setVisibility(View.INVISIBLE);
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

    @Click(R.id.tvBaseRight)
    public void rightTextClick() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
        //Tracker t = this.getTracker( );
//
//		// Enable Display Features.
//		t.enableAdvertisingIdCollection(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
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
    @AfterViews
    public void InitBase() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ApplicationMamager.getInstance().addActivity(this);
        mContext = this;

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

//	public void initLocation(BDLocationListener myListener) {
//		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
//		mLocationClient.registerLocationListener(myListener);
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
//		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ������ϵ
//		int span = 1000;
//		option.setScanSpan(0);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
//		// option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
//		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
//		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
//		// option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯��������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ���
//		// option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI��������BDLocation.getPoiList��õ�
//		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶�����̣������Ƿ���stop��ʱ��ɱ�������̣�Ĭ��ɱ��
//		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
//		option.setEnableSimulateGps(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps������Ĭ����Ҫ
//		mLocationClient.setLocOption(option);
//		mLocationClient.start();
//	}

    public void initLocation(LocationListener locationListener) {

        //��ȡ��LocationManager����
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //������õ�Criteria���󣬻�ȡ���ϴ˱�׼��provider����
        String currentProvider =
                locationManager.getProvider(LocationManager.GPS_PROVIDER).getName();

        Location currentLocation =
                locationManager.getLastKnownLocation(currentProvider);
        if(currentLocation != null){
            Log.d("Location",currentLocation.toString());
            double longitude = currentLocation.getLongitude();
            double latitude = currentLocation.getLatitude();
            GetApp().setLatitude(latitude);
            GetApp().setLongitude(longitude);
            //���λ����ϢΪnull�����������λ����Ϣ

        }else{
            locationManager.requestLocationUpdates(currentProvider, 5, 0,
                    locationListener);
        }
        // //����GPS״̬������
        // locationManager.addGpsStatusListener(gpsListener);

        //ֱ��������һ��λ����ϢΪֹ�����δ������һ��λ����Ϣ������ʾĬ�Ͼ�γ��
        //ÿ��10���ȡһ��λ����Ϣ
        // while(true){
//        currentLocation = locationManager.getLastKnownLocation(currentProvider);
        // if(currentLocation != null){
        // locationListener.onLocationChanged(currentLocation);
        //// Log.d("Location", "Latitude: " + currentLocation.getLatitude());
        //// Log.d("Location", "location: " + currentLocation.getLongitude());
        // break;
        // }else{
        //
        // }
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        //
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

    public void setRightText(String text) {
        tvBaseRight.setText(text);
        tvBaseRight.setVisibility(View.VISIBLE);
        btnBaseRight.setVisibility(View.GONE);
    }

    public String getSessionId() {
        if (GetApp().getUser() == null)
            return "";
        return GetApp().getUser().getSession_id();
    }

    public void onResume() {
        threadLoadLocation = true;
        super.onResume();
//		JPushInterface.onResume(this);
        // MobclickAgent.onPageStart(getRunningActivityName()); //ͳ��ҳ��
        // MobclickAgent.onResume(this); //ͳ��ʱ��
    }

    public void onPause() {
        threadLoadLocation = false;
        DialogHelper.CloseLoadingDialog();
        super.onPause();
//		JPushInterface.onPause(this);
        // MobclickAgent.onPageEnd(getRunningActivityName()); // ��֤ onPageEnd
        // ��onPause ֮ǰ����,��Ϊ onPause �лᱣ����Ϣ
        // MobclickAgent.onPause(this);
    }

    // public void goLogin() {
    // if (GetApp().getUser() == null) {
    // Intent intent = LoginActivity_.intent(mContext).get();
    // startActivity(intent);
    // }
    // }

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