package com.vc.swoop;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.vc.swoop.adapter.WatchListAdapter;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.model.AD;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.AdWS;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@EActivity(R.layout.activity_watching)
public class WatchingListActivity extends BaseActivity {
    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById
    ListView listview;
    int pageIndex = 1;
    WatchListAdapter adapter;
    boolean isDel = false;
    private User user;
    private List<AD> ads = new ArrayList<AD>();

    @AfterViews
    void init() {
        //checkUser();
        hideGoBack();
//		if(GetApp().getUser()==null)
//		{
//			Intent intent=LoginActivity_.intent(mContext).get();
//			startActivity(intent);
//		}
//		else {
//			
//		}
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
                if (!isDel) {
                    pageIndex = 1;
                    getData();
                }

            }
        });
        adapter = new WatchListAdapter(mContext, ads);
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
                    getData();
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
                if (isDel) {
                    AD ad = ads.get(arg2);
                    ad.setCheck(!ad.isCheck());
                    adapter.notifyDataSetChanged();
                } else {
                    Intent intent = ADDetailActivity_.intent(mContext).get();
                    AD ad = ads.get(arg2);
                    intent.putExtra("ad", JUtil.toJson(ad));
                    intent.putExtra("ad_id", ad.getAd_id() + "");
                    intent.putExtra("who", 2);
                    startActivity(intent);
                }

            }
        });

        getRightButton().setVisibility(View.VISIBLE);
        getRightButton().setBackgroundResource(R.drawable.gd_action_bar_trashcan);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

        getData();
        super.onResume();
    }

    @Override
    public void goBack() {
        // TODO Auto-generated method stub
        if (isDel) {
            isDel = false;
            getRightButton().setBackgroundResource(R.drawable.gd_action_bar_trashcan);
            getLeftButton().setVisibility(View.INVISIBLE);
            for (int i = 0; i < ads.size(); i++) {
                ads.get(i).setCheck(false);
            }
            adapter.setDelMode(false);
            adapter.notifyDataSetChanged();
        } else {
            super.goBack();
        }

    }

    @Override
    public void rightClick() {
        // TODO Auto-generated method stub
        if (isDel) {
            isDel = false;
            String ad_ids = "";
            for (int i = 0; i < ads.size(); i++) {
                if (ads.get(i).isCheck()) {
                    ad_ids += ads.get(i).getAd_id() + ",";
                }
            }
            if (!ad_ids.equals("")) {
                ad_ids = ad_ids.substring(0, ad_ids.length() - 1);
                new delDataTask().execute(ad_ids);
            }

            Log.d("xxx", ad_ids);
        } else {
            isDel = true;
            adapter.setDelMode(true);
            getRightButton().setBackgroundResource(R.drawable.icon_delete_sure);
            getLeftButton().setVisibility(View.VISIBLE);
            getLeftButton().setBackgroundResource(R.drawable.icon_delete_cancel);
        }
        super.rightClick();
    }

    private void getData() {
        if (!isDel) {
            if (GetApp().getUser() != null) {
                DialogHelper.ShowLoadingDialog(mContext, "loading data");
                new GetDataTask().execute("");
            } else {
                if (adapter != null) {
                    ads.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    class delDataTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            AdWS ws = new AdWS(mContext);
            String ad_ids = arg0[0];

            String ids[] = ad_ids.split(",");

            JSONObject jsonData = null;

            for (String id : ids) {
                jsonData = ws.del_watchlist(getSessionId(),
                        id + "");
            }


            if (JUtil.checkStaus(jsonData)) {
                return "";
            } else {
                return JUtil.getError(jsonData);
            }

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            DialogHelper.CloseLoadingDialog();
            swipeRefreshLayout.setRefreshing(false);
            if (!result.equals("")) {
                DialogHelper.showTost(mContext, result);

            } else {
                pageIndex = 1;
                getData();
            }

            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub


            DialogHelper.ShowLoadingDialog(mContext, "loading data");
            super.onPreExecute();
        }
    }

    class GetDataTask extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            AdWS ws = new AdWS(mContext);
            JSONObject jsonData = ws.get_my_watchlist(getSessionId(),
                    pageIndex + "");
            return jsonData;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            DialogHelper.CloseLoadingDialog();
            List<AD> thisData = new LinkedList<AD>();
            if (result == null) {
//				if (ads.size() == 0) {
//					llEmpty.setVisibility(View.VISIBLE);
//				}
                DialogHelper.showTost(mContext, getString(R.string.No_Networks_Found));
                return;
            }
            if (JUtil.checkStaus(result)) {
                thisData = JUtil.parseArray(JUtil.getData(result, "ads"),
                        AD.class);
            } else {
                DialogHelper.showTost(mContext, JUtil.getError(result));
                return;
                //return JUtil.getError(jsonData);
            }
            swipeRefreshLayout.setRefreshing(false);


            if (pageIndex == 1) {
                ads.clear();

            }
            if (pageIndex == 1) {
                ads.clear();
            }
            ads.addAll(thisData);
            isDel = false;
            getRightButton().setBackgroundResource(R.drawable.gd_action_bar_trashcan);
            getLeftButton().setVisibility(View.INVISIBLE);
            for (int i = 0; i < ads.size(); i++) {
                ads.get(i).setCheck(false);
            }
            adapter.setDelMode(false);
            adapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub


//			DialogHelper.ShowLoadingDialog(mContext, "loading data");
            super.onPreExecute();
        }
    }
}
