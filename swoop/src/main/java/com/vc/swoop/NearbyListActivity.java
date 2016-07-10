package com.vc.swoop;

import java.util.LinkedList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vc.swoop.adapter.NearbyListAdapter;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.model.AD;
import com.vc.swoop.webservice.AdWS;
import com.vc.swoop.widgets.ComfrimDialog.OnConfirmListener;
import com.vc.swoop.widgets.Dialog_Categories;
import com.vc.swoop.widgets.Dialog_Categories.SelectFilterListener;

@EActivity(R.layout.activity_nearby)
public class NearbyListActivity extends BaseActivity {

    @ViewById
    LinearLayout llEmpty;
    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById
    ListView listview;
    @ViewById
    TextView tvMsg;
    int pageIndex = 1;
    boolean isLoad = false;
    Dialog dialog;
    Dialog_Categories dialog_Categories;
    private String orderbyString = "nearest";
    private int category = 0;
    List<AD> ads;
    NearbyListAdapter adapter;
    private GpsStatus gpsstatus;
    //	public BDLocationListener myListener = new MyLocationListener();
    boolean isLocationOK = false;
    boolean isShowError = false;

    @AfterViews
    void init() {

        swipeRefreshLayout.setColorScheme(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                pageIndex = 1;
                isLocationOK = false;
                reDelayedLoad();

            }
        });

        checkGPSInfo();
        ads = new LinkedList<AD>();
        adapter = new NearbyListAdapter(mContext, ads);
        listview.setAdapter(adapter);
        listview.setOnScrollListener(new OnScrollListener() {
            private int lastItemIndex;// ��ǰListView�����һ��Item������

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                Log.i("TAG",
                        "onScrollStateChanged-lastItemIndex   " + lastItemIndex
                                + "    " + view.getLastVisiblePosition());
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                        && view.getLastVisiblePosition() == adapter.getCount() - 1) {
                    // Log.i(TAG, "onScrollStateChanged");
                    // ������ݴ��룬�˴�ʡ����
                    // loadNextPage();
                    pageIndex++;
                    new GetDataTask().execute("");
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

                lastItemIndex = firstVisibleItem + visibleItemCount - 1 - 1;
                Log.i("TAG", "onScroll-lastItemIndex   " + lastItemIndex);
            }
        });
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = ADDetailActivity_.intent(mContext).get();
                if (ads.size() > position) {
                    AD ad = ads.get(position);
                    intent.putExtra("ad", JUtil.toJson(ad));
                    intent.putExtra("ad_id", ad.getAd_id() + "");
                    intent.putExtra("who", 0);
                    startActivity(intent);
                }

                // TODO Auto-generated method stub

            }
        });
        DialogHelper.ShowLoadingDialog(mContext, "loading location data");
        reDelayedLoad();

        getLeftButton().setBackgroundResource(R.drawable.btn_cate);
        getLeftButton().setVisibility(View.VISIBLE);
        getRightButton().setVisibility(View.VISIBLE);
        getRightButton().setBackgroundResource(R.drawable.search);
        // new GetDataTask().execute("");
    }

    public void reDelayedLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (!isLocationOK) {
                    if (!isShowError) {
                        DialogHelper.showTost(mContext,
                                getString(R.string.location_failure));
                        isShowError = true;
                    }

                    isLocationOK = true;
                    if (mLocationClient != null)
                        mLocationClient.stop();
                    DialogHelper.ShowLoadingDialog(mContext, "");
                    new GetDataTask().execute("");

                    // tvMsg.setText(getString(R.string.location_failure));
                    // llEmpty.setVisibility(View.VISIBLE);
                    // DialogHelper.CloseLoadingDialog();
                }
            }
        }, 5000);
    }

    @Override
    public void rightClick() {
        // TODO Auto-generated method stub
        Intent intent = SearchActivity_.intent(mContext).get();
        startActivity(intent);
    }

    @Override
    public void goBack() {
        // TODO Auto-generated method stub
        // super.goBack();
        showCate();
    }

    private void showCate() {
        if (dialog_Categories == null)
            dialog_Categories = new Dialog_Categories(this,
                    selectFilterListener);
        dialog_Categories.showDialog();
    }

    private SelectFilterListener selectFilterListener = new SelectFilterListener() {

        @Override
        public void select(String orderby, int categoriesid) {
            // TODO Auto-generated method stub
            orderbyString = orderby;
            category = categoriesid;
            pageIndex = 1;

            DialogHelper.ShowLoadingDialog(mContext, "loading data");
            new GetDataTask().execute("");
            dialog_Categories.dismiss();
        }
    };

    private void openGPSSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            this.startActivity(intent);

        } catch (ActivityNotFoundException ex) {

            // The Android SDK doc says that the location settings activity
            // may not be found. In that case show the general settings.

            // General settings activity
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                this.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    private void checkGPSInfo() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        boolean result = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!result) {

            pgsAlert();

        }

    }

    private void pgsAlert() {
        DialogHelper
                .showComfrimDialog(
                        mContext,
                        null,
                        "Swoop requires to access your location without which you will not be able you use most features in Swoop.Please enable location services in Settings.",
                        "Settings", new OnConfirmListener() {

                            @Override
                            public void confirm() {
                                // TODO Auto-generated method stub
                                openGPSSetting();
                            }
                        }, null, null);

    }

    class GetDataTask extends AsyncTask<String, Integer, List<AD>> {

        @Override
        protected List<AD> doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            AdWS ws = new AdWS(mContext);
            JSONObject jsonData = ws.near_ad(getSessionId(), orderbyString,
                    category + "", GetApp().getLongitude() + "", GetApp()
                            .getLatitude() + "", pageIndex + "");
            Log.e("GetDataTask", "doInBackground--getSessionId"
                    + getSessionId() + "  orderbyString==" + orderbyString
                    + "  category==" + category + "  getLongitude=="
                    + GetApp().getLongitude() + "  getLatitude==" + GetApp()
                    .getLatitude() + "  pageIndex==" + pageIndex);
            if (JUtil.checkStaus(jsonData)) {
                return JUtil.parseArray(JUtil.getData(jsonData, "ads"),
                        AD.class);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<AD> result) {
            // TODO Auto-generated method stub
            if (result != null && result.size() > 3) {
                Log.e("GetDataTask", "GetDataTask" + result.get(3).getTitle());
            }
            // DialogHelper.showTost(mContext, result.get(3).getTitle());
            DialogHelper.CloseLoadingDialog();
            swipeRefreshLayout.setRefreshing(false);
            if (result == null) {
                // if (ads.size() == 0) {
                // llEmpty.setVisibility(View.VISIBLE);
                // }
                DialogHelper.showTost(mContext,
                        getString(R.string.No_Networks_Found));
                if (ads.size() == 0) {
                    llEmpty.setVisibility(View.VISIBLE);
                    tvMsg.setText(getString(R.string.No_Networks_Found));

                }
                return;
            }
            if (pageIndex == 1) {
                ads.clear();
            }

            ads.addAll(result);
            adapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            isLoad = true;
            llEmpty.setVisibility(View.GONE);
            // DialogHelper.ShowLoadingDialog(mContext, "loading data");
            super.onPreExecute();
        }
    }

//	public class MyLocationListener implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			double lo = location.getLongitude();
//			double la = location.getLatitude();
//			GetApp().setLongitude(location.getLongitude());
//			GetApp().setLatitude(location.getLatitude());
//			if (mLocationClient != null)
//				mLocationClient.stop();
//			// if (!isLoad) {
//			if (!isLocationOK)
//				new GetDataTask().execute("");
//			isLocationOK = true;
//			// }
//		}
//	}

    private LocationListener locationListener = new LocationListener() {
        // λ�÷���ı�ʱ����
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            GetApp().setLatitude(latitude);
            GetApp().setLongitude(longitude);
            if (!isLoad) {
                new GetDataTask().execute("");
            }
            Log.d("Location", "onLocationChanged");
        }

        // providerʧЧʱ����
        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Location", "onProviderDisabled");
        }

        // provider����ʱ����
        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Location", "onProviderEnabled");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Location", "onStatusChanged");
        }
    };

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        if (mLocationClient != null) {
            if (isLocationOK) {
                pageIndex = 1;
                DialogHelper.ShowLoadingDialog(mContext, "loading data");
                if (adapter != null)
                    adapter.notifyDataSetChanged();
                new GetDataTask().execute("");
            } else {

                mLocationClient.start();
            }

        } else {
            initLocation(locationListener);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        if (mLocationClient != null)
            mLocationClient.stop();
        isLoad = false;
        System.gc();
        ImageLoader.getInstance().clearMemoryCache();
        super.onPause();
    }

}
