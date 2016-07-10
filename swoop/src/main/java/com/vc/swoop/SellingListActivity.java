package com.vc.swoop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.vc.swoop.NearbyListActivity.GetDataTask;
import com.vc.swoop.adapter.SellingListAdapter;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.model.AD;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.AdWS;

@EActivity(R.layout.activity_selling)
public class SellingListActivity extends BaseActivity {
	private User user;
	private List<AD> ads = new ArrayList<AD>();
	private List<AD> sellingCurrent = new ArrayList<AD>();
	private List<AD> sellingExpired = new ArrayList<AD>();
	private List<AD> sellingWithdrawn = new ArrayList<AD>();
	public static final int CURRENT = 1;
	public static final int EXPIRED = 2;
	public static final int WITHDRAWN = 3;

	private boolean isInitCurrt = false;
	private boolean isInitExpired = false;
	private boolean isInitWithdrawn = false;

	// private boolean isLoadMoreOp = false;

	// Ĭ�Ϸ���״̬
	private int selectState = CURRENT;
	@ViewById
	SwipeRefreshLayout swipeRefreshLayout;
	@ViewById
	ListView listview;
	@ViewById
	LinearLayout llEmpty, ll_segment;
	@ViewById
	Button iv_current, iv_expired, iv_withdrawn;
	int pageIndex = 1;
	SellingListAdapter adapter;
	@ViewById
	FrameLayout not_empty;
	@AfterViews
	void init() {
		// checkUser();
		hideGoBack();
		llEmpty.setVisibility(View.GONE);
		not_empty.setVisibility(View.VISIBLE);
		user = GetApp().getUser();
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
		adapter = new SellingListAdapter(mContext, ads);
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = ADDetailActivity_.intent(mContext).get();
				AD ad = ads.get(arg2);
				intent.putExtra("ad", JUtil.toJson(ad));
				intent.putExtra("ad_id",ad.getAd_id()+"");
				intent.putExtra("who", 1);
				startActivity(intent);
			}
		});
//		getData();
	}

	@Click(R.id.iv_add)
	void iv_add() {
		if (GetApp().getUser() == null) {
			Intent intent = LoginActivity_.intent(mContext).get();
			startActivity(intent);
			return;
		}
		Intent intent = AddOrEditAdActivity_.intent(mContext).get();
		startActivity(intent);
	}

	@Click(R.id.llEmpty)
	void llEmpty() {
		if (GetApp().getUser() == null) {
			Intent intent = LoginActivity_.intent(mContext).get();
			startActivity(intent);
			return;
		}
		Intent intent = AddOrEditAdActivity_.intent(mContext).get();
		startActivity(intent);
	}

	@Click(R.id.iv_current)
	void current() {
		onSelect(iv_current);
	}

	@Click(R.id.iv_expired)
	void expired() {
		onSelect(iv_expired);
	}

	@Click(R.id.iv_withdrawn)
	void withdrawn() {
		onSelect(iv_withdrawn);
	}

	public void onSelect(View v) {
		int id = v.getId();
		iv_current.setTextColor(0xFF459ECC);
		iv_expired.setTextColor(0xFF459ECC);
		iv_withdrawn.setTextColor(0xFF459ECC);
		// sellingCurrent = new ArrayList<AD>();
		// sellingExpired = new ArrayList<AD>();
		// sellingWithdrawn = new ArrayList<AD>();
		switch (id) {
		case R.id.iv_current:
			iv_current.setTextColor(0xFFFFFFFF);
			selectState = CURRENT;
			// adapter.setAdList(sellingCurrent);
			ll_segment.setBackgroundResource(R.drawable.option_menu_bg);
			pageIndex = 1;
			getData();
			break;
		case R.id.iv_expired:
			iv_expired.setTextColor(0xFFFFFFFF);
			selectState = EXPIRED;
			// adapter.setAdList(sellingExpired);
			ll_segment.setBackgroundResource(R.drawable.option_menu_bg2);
			// if(exFirst) {
			pageIndex = 1;
			getData();
			// exFirst = false;
			// }
			break;
		case R.id.iv_withdrawn:
			iv_withdrawn.setTextColor(0xFFFFFFFF);
			selectState = WITHDRAWN;
			// adapter.setAdList(sellingWithdrawn);
			ll_segment.setBackgroundResource(R.drawable.option_menu_bg3);
			// if(withFirst) {
			pageIndex = 1;
			getData();
			// withFirst = false;
			// }
			break;
		}
		adapter.notifyDataSetChanged();

	}

	public void getData() {
		if (GetApp().getUser() != null) {
			DialogHelper.ShowLoadingDialog(mContext, "loading data");
			new GetDataTask().execute("");
		} else {
			ads.clear();
			adapter.notifyDataSetChanged();
			not_empty.setVisibility(View.GONE);
			llEmpty.setVisibility(View.VISIBLE);
		}
	}

	class GetDataTask extends AsyncTask<String, Integer, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			AdWS ws = new AdWS(mContext);
			JSONObject jsonData = ws.my_ads(getSessionId(), selectState + "",
					pageIndex + "");
			return jsonData;

		}

		@Override
		protected void onPostExecute(JSONObject result) {

			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			List<AD> thisData = new LinkedList<AD>();
			if (result == null) {
				// if (ads.size() == 0) {
				// llEmpty.setVisibility(View.VISIBLE);
				// }
				DialogHelper.showTost(mContext,getString(R.string.No_Networks_Found));
				return;
			}
			if (JUtil.checkStaus(result)) {
				thisData = JUtil.parseArray(JUtil.getData(result, "ads"),
						AD.class);
			} else {
				DialogHelper.showTost(mContext, JUtil.getError(result));
				return;
				// return JUtil.getError(jsonData);
			}
			swipeRefreshLayout.setRefreshing(false);
			
			if (pageIndex == 1 && selectState == CURRENT
					&& thisData.size() == 0) {
				ads.clear();
				llEmpty.setVisibility(View.VISIBLE);
				not_empty.setVisibility(View.GONE);
			}
			if (pageIndex == 1) {
				ads.clear();
			}
			ads.addAll(thisData);
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			llEmpty.setVisibility(View.GONE);
			not_empty.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		getData();
		super.onResume();
	}

}
