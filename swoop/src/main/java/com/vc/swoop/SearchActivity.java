package com.vc.swoop;

import java.util.LinkedList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import com.vc.swoop.NearbyListActivity.GetDataTask;
import com.vc.swoop.adapter.NearbyListAdapter;
import com.vc.swoop.common.App;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.model.AD;
import com.vc.swoop.webservice.AdWS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

@NoTitle
@EActivity(R.layout.activity_search)
public class SearchActivity extends Activity {
	@ViewById
	LinearLayout llEmpty;
	@ViewById
	SwipeRefreshLayout swipeRefreshLayout;
	@ViewById
	ListView listview;
	@ViewById
	TextView tvMsg;
	int pageIndex = 1;

	List<AD> ads;
	NearbyListAdapter adapter;
	Context mContext;
	@ViewById
	TextView et_search;

	@AfterViews
	void init() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this;
		et_search.setOnKeyListener(onKey);
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
				new GetDataTask().execute("");

			}
		});

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
				AD ad = ads.get(position);
				intent.putExtra("ad", JUtil.toJson(ad));
				intent.putExtra("ad_id",ad.getAd_id()+"");
				intent.putExtra("who", 0);
				startActivity(intent);
				// TODO Auto-generated method stub

			}
		});
		// DialogHelper.ShowLoadingDialog(mContext, "loading location data");

		// new GetDataTask().execute("");
	}

	OnKeyListener onKey = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {

			// TODO Auto-generated method stub

			if (keyCode == KeyEvent.KEYCODE_ENTER) {

				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {

					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							0);

				}
				new GetDataTask().execute("");
				return true;

			}
			return false;
		}

	};

	@Click(R.id.btnBaseLeft)
	void goback() {
		this.finish();
	}

	@Click(R.id.iv_cancel)
	void clearText() {
		et_search.setText("");
		ads.clear();
		adapter.notifyDataSetChanged();
	}

	class GetDataTask extends AsyncTask<String, Integer, List<AD>> {

		@Override
		protected List<AD> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			AdWS ws = new AdWS(mContext);
			String searchString = et_search.getText().toString();
			String sessionId="";
			if(App.getApp(mContext).getUser()!=null)
			{
				sessionId=App.getApp(mContext).getUser().getSession_id();
			}
			JSONObject jsonData = ws.search_near_ad(sessionId, searchString, pageIndex + "");
			if (JUtil.checkStaus(jsonData)) {
				return JUtil.parseArray(JUtil.getData(jsonData, "ads"),
						AD.class);
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<AD> result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			swipeRefreshLayout.setRefreshing(false);
			if (result == null) {
				if (ads.size() == 0) {
//					llEmpty.setVisibility(View.VISIBLE);
					DialogHelper.showTost(mContext,
							getString(R.string.No_Networks_Found)); 
					return;
				} else { 
					llEmpty.setVisibility(View.GONE);
					DialogHelper.showTost(mContext,
							getString(R.string.No_Networks_Found)); 
					return;
				}
			}
			if (result.size() == 0) {
				if (ads.size() == 0) {
					DialogHelper.showTost(mContext,
							getString(R.string.search_no_result));
					return;
				} else {
//					if (pageIndex == 1) {
						 
						DialogHelper.showTost(mContext,
								getString(R.string.search_no_result));
//					}

					return;
				}
				// DialogHelper.showTost(mContext,
				// getString(R.string.search_no_result));

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

			llEmpty.setVisibility(View.GONE);
			DialogHelper.ShowLoadingDialog(mContext, "loading data");
			super.onPreExecute();
		}
	}
}
