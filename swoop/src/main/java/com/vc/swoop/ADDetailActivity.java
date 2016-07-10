package com.vc.swoop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import com.baidu.location.BDLocationListener;
import com.vc.swoop.NearbyListActivity.GetDataTask;
import com.vc.swoop.adapter.PicFragmentAdapter;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.BaseFragmentActivity;
import com.vc.swoop.common.DateUtil;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.common.LBSUtil;
import com.vc.swoop.common.SoftInputUtil;
import com.vc.swoop.common.TextU;
import com.vc.swoop.model.AD;
import com.vc.swoop.model.CommentMDL;
import com.vc.swoop.model.User;
import com.vc.swoop.webservice.AdWS;
import com.vc.swoop.widgets.*;
import com.vc.swoop.widgets.ComfrimDialog.OnConfirmListener;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.activity_addetail)
public class ADDetailActivity extends BaseFragmentActivity {

	PicFragmentAdapter mAdapter;
	// ViewPager mPager;
	InsideViewPager mPager;
	PageIndicator mIndicator;
	// String[] imgs = new String[5];
	AD ad;
	List<String> imgs = new ArrayList<String>();

	public static final int NEARBY_LIST = 0;
	public static final int SELLING_LIST = 1;
	public static final int WATCHLIST = 2;
	@ViewById
	EditText editContent;
	@Extra("who")
	int inActivity = 0;
	String session_id = "";
	// @ViewById
	// SwipeRefreshLayout swipeRefreshLayout;
	@ViewById
	ImageView iv_no_ad_default_home, iv_logo_item_watch;
	@ViewById
	RelativeLayout rel_ad_main;
	@ViewById
	LinearLayout ll_images, ll_content_price, ll_relist, ll_edit, ll_comment,
			ll_fav, llEditPinglun, llCommentPanel, ll_buttons;

	@ViewById
	TextView tv_content_price, ad_title, ago_and_id, ad_content, tv_comment,
			tv_distance, tv_price, tv_phone, tvFav, tv_report, tvComment;
	List<CommentMDL> comments;
	@Extra("ad_id")
	String ad_id;
	int pageIndex = 1;

	@AfterViews
	void init() {
		// TODO Auto-generated method stub
		tv_report.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // �»���
		tv_report.getPaint().setAntiAlias(true);// �����
		tvComment.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // �»���
		tvComment.getPaint().setAntiAlias(true);// �����

		setTitleText("Details");
		// swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
		//
		// @Override
		// public void onRefresh() {
		// // TODO Auto-generated method stub
		// pageIndex = 1;
		// new getCommentsTask().execute("");
		// }
		// });
		setRightText("");
		comments = new LinkedList<CommentMDL>();
		session_id = getSessionId();
		initData();

		editContent.addTextChangedListener(new TextWatcher() {
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
				if (editContent.getText().toString().length() == 1) {
					char c = editContent.getText().toString().charAt(0);
					if (Character.isLowerCase(c)) {
						editContent.setText(editContent.getText().toString()
								.toUpperCase());
						Editable etext = editContent.getText();

						Selection.setSelection(etext, etext.length());

					}

				}
			}

		});
	}

	@Override
	public void rightTextClick() {
		// TODO Auto-generated method stub
		if (inActivity == SELLING_LIST && ad.getState() == 1) {
			alertWithDrawConfirm();
		} else if (inActivity == NEARBY_LIST || inActivity == WATCHLIST) {
			ad_remove();
		}
		super.rightTextClick();
	}

	@Click(R.id.ivCall)
	void call() {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ ad.getPhone()));
		startActivity(intent);

	}

	@Click(R.id.tv_report)
	void tv_report() {
		if (GetApp().getUser() == null) {
			Intent intent = LoginActivity_.intent(mContext).get();
			startActivity(intent);
			return;
		}
		Intent intent = ReportActivity_.intent(mContext).get();
		intent.putExtra("ad_id", ad.getAd_id() + "");
		startActivity(intent);
	}

	@Click(R.id.tvclose)
	void closePingLun() {
		hideKeyBoard();
		editContent.setText("");
	}

	@Click(R.id.tvCancel)
	void CancelPingLun() {
		hideKeyBoard();
		editContent.setText("");
	}

	@Click(R.id.tvSend)
	void sendPingLun() {
		String contentString = editContent.getText().toString();
		if (!contentString.equals("")) {
			new sendCommentsTask().execute("");
		}
	}

	void openInput() {
		ll_buttons.setVisibility(View.GONE);
		llEditPinglun.setVisibility(View.VISIBLE);
		editContent.requestFocus();
		SoftInputUtil.openSoftInput(mContext);
	}

	void hideKeyBoard() {
		ll_buttons.setVisibility(View.VISIBLE);
		llEditPinglun.setVisibility(View.GONE);
		SoftInputUtil.closeSoftInput(mContext, editContent);
	}

	@Click(R.id.ivMessage)
	void message() {
		Uri uri = Uri.parse("smsto:" + ad.getPhone());
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", "");
		startActivity(it);
	}

	Dialog dialog;

	private void alertWithDrawConfirm() {
		DialogHelper.showComfrimDialog(mContext, "Withdraw confirmation",
				"Are you sure ? ", null, new OnConfirmListener() {

					@Override
					public void confirm() {
						// TODO Auto-generated method stub
						new withDrawAdTask().execute("");
					}
				}, null, null);

	}

	private void update_view() {

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		new getAdTask().execute("");
		super.onResume();
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (requestCode != 1 && resultCode != 2)
			return;
		// initData();
		boolean isEdit = data.getBooleanExtra("isEdit", false);
		if (isEdit == true) {

			AD ad2 = (AD) data.getSerializableExtra("ad");

			ad.setIsFlow(ad2.getIsFlow());
			ad.setName(ad2.getName());
			ad.setTitle(ad2.getTitle());
			ad.setDescribe(ad2.getDescribe());
			ad.setPrice(ad2.getPrice());
			ad.setPic1(ad2.getPic1());
			ad.setPic2(ad2.getPic2());
			ad.setPic3(ad2.getPic3());
			ad.setPic4(ad2.getPic4());
			ad.setPic5(ad2.getPic5());

			imgs.clear();
			addPic(ad.getPic1());
			addPic(ad.getPic2());
			addPic(ad.getPic3());
			addPic(ad.getPic4());
			addPic(ad.getPic5());
			update_view();
			initView();
			differentPageSetting();
		}
	}

	public void gotoLogin() {
		Intent intent = new Intent(mContext, LoginActivity.class);
		this.startActivity(intent);
	}

	Dialog relistdialog;
	Dialog unwatchdialog;

	private void alertRelistConfirm() {
		DialogHelper.showComfrimDialog(mContext,
				"Your listing will be relisted", "Are you sure? ", null,
				new OnConfirmListener() {

					@Override
					public void confirm() {
						// TODO Auto-generated method stub
						new relistAdTask().execute("");
					}
				}, null, null);

	}

	private void alertUnWatchConfirm() {
		DialogHelper.showComfrimDialog(mContext, "Withdraw confirmation",
				"Are you sure?", null, new OnConfirmListener() {

					@Override
					public void confirm() {
						// TODO Auto-generated method stub
						new delWatchlistAdTask().execute("");
					}
				}, null, null);

	}

	@Click(R.id.ll_relist)
	void btn_relist() {
		alertRelistConfirm();
	}

	@Click(R.id.ll_fav)
	void ad_remove() {
		if (GetApp().getUser() == null) {
			Intent intent = LoginActivity_.intent(mContext).get();
			startActivity(intent);
			return;
		}
		if (ad.isIn_watchlist()) {
			alertUnWatchConfirm();
		} else {
			new addWatchlistAdTask().execute("");
		}
	}

	@Click(R.id.tvComment)
	void ll_comment() {
		if (GetApp().getUser() == null) {
			Intent intent = LoginActivity_.intent(mContext).get();
			startActivity(intent);
			return;
		}
		openInput();

	}

	@Click(R.id.btn_comment)
	void btn_comment() {
		if (GetApp().getUser() == null) {
			Intent intent = LoginActivity_.intent(mContext).get();
			startActivity(intent);
			return;
		}
		openInput();

	}

	@Click(R.id.ll_edit)
	void ad_edit() {
		if (GetApp().getUser() == null) {
			Intent intent = LoginActivity_.intent(mContext).get();
			startActivity(intent);
			return;
		}
		Intent intent = AddOrEditAdActivity_.intent(mContext).get();
		intent.putExtra("ad_json", JUtil.toJson(ad));
		intent.putExtra("isEdit", true);
		startActivity(intent);
	}

	private User user;

	private void initData() {
		// ad = JUtil.parseObject(ad_json, AD.class);
		//
		// addPic(ad.getPic1());
		// addPic(ad.getPic2());
		// addPic(ad.getPic3());
		// addPic(ad.getPic4());
		// addPic(ad.getPic5());

		user = GetApp().getUser();

	}

	private void addPic(String imgUrl) {
		if (imgUrl != null && !imgUrl.equals("")) {
			imgs.add(imgUrl);
		}
	}

	// private void setRightText("Withdraw");
	private void updateWatch() {
		if (inActivity == NEARBY_LIST || inActivity == WATCHLIST) {
			if (ad.isIn_watchlist()) {
				setRightText("Remove from favourites");
				// tvFav.setText("Remove to favourites");
			} else {
				setRightText("Add to favourites");
				// tvFav.setText("Add to favourites");
			}
		} else {

		}
		if (ad.isIn_watchlist()) {
			iv_logo_item_watch.setVisibility(View.VISIBLE);
		} else {
			iv_logo_item_watch.setVisibility(View.GONE);
		}
	}

	private void differentPageSetting() {
		ll_comment.setVisibility(View.GONE);
		switch (inActivity) {
		case NEARBY_LIST:
			// ll_fav.setVisibility(View.VISIBLE);
			ll_fav.setVisibility(View.GONE);
			// getBaseRight().setVisibility(View.GONE);

			break;
		case WATCHLIST:

			// ll_fav.setVisibility(View.VISIBLE);
			// getBaseRight().setVisibility(View.GONE);
			break;
		case SELLING_LIST:
			// ll_buttons.setVisibility(View.INVISIBLE);
			if (ad.getState() == 1) {
				ll_edit.setVisibility(View.VISIBLE);
				setRightText("Withdraw");
			} else if (ad.getState() == 2 || ad.getState() == 3) {
				ll_relist.setVisibility(View.VISIBLE);

			}
			getBaseRight().setVisibility(View.VISIBLE);
			break;
		}
	}

	boolean hasChat = false;

	private int currentItem = 0;

	private void initView() {
		mAdapter = new PicFragmentAdapter(getSupportFragmentManager());
		mPager = (InsideViewPager) findViewById(R.id.vp_main);
		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator_mainv);
		if (imgs != null && imgs.size() > 0) {
			rel_ad_main.setVisibility(View.VISIBLE);
			iv_no_ad_default_home.setVisibility(View.GONE);
			mAdapter.setImgs(imgs);
		} else {
			rel_ad_main.setVisibility(View.GONE);
			iv_no_ad_default_home.setVisibility(View.VISIBLE);
			((View) mIndicator).setVisibility(View.GONE);

		}

		mPager.setAdapter(mAdapter);
		mPager.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		mIndicator.setViewPager(mPager);
		mIndicator
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// Toast.makeText(AdDetail.this, "Changed to page " +
						// position, Toast.LENGTH_SHORT).show();
						currentItem = position;
					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}
				});

		// distance_and_price.setText(LBSUtil.distanceStr(MyApp.getLng(),
		// MyApp.getLat(), ad.getLng(), ad.getLat())
		// + "   |   $"
		// + ad.getPrice());
		String distanprice = "";
		distanprice += "Watched:" + ad.getWatchlistcount() + "  |  Views:"
				+ ad.getView_count();
		// distanprice+=LBSUtil.distanceStr(MyApp.getLng(),MyApp.getLat(),
		// ad.getLng(), ad.getLat());
		if (ad.getPricetype() == 0) {
			if (ad.getPrice() == 0) {
				distanprice += "  |  Free";
				tv_price.setText("Free");
			} else {
				distanprice += "  |  $" + TextU.ShowPrice(ad.getPrice());
				tv_price.setText("$" + TextU.ShowPrice(ad.getPrice()));
			}

		} else if (ad.getPricetype() == 1) {
			distanprice += "  |  swap";
			tv_price.setText("swap");
		} else if (ad.getPricetype() == 2) {
			distanprice += "  |  offer";
			tv_price.setText("offer");
		}
		if(GetApp().getUser()!=null)
		{
			if(GetApp().getUser().getUser_id()==ad.getUser_id())
			{
				tvComment.setText("Enter your response");
			}
		}
		// tv_price.setText("$" + TextU.ShowPrice(ad.getPrice()));
		tv_comment.setText("Q&A(" + ad.getCommentcount() + ")");
		String distance = LBSUtil.distanceStr(ad.getLng(), ad.getLat(),
				GetApp().getLongitude(), GetApp().getLatitude());
		tv_distance.setText(distance);
		if (imgs != null && imgs.size() > 0) {
			ll_images.setVisibility(View.VISIBLE);
			ll_content_price.setVisibility(View.GONE);
		} else {
			ll_images.setVisibility(View.GONE);
			ll_content_price.setVisibility(View.VISIBLE);
			tv_content_price.setText(distanprice);
		}
		ad_title.setText(ad.getTitle());

		String state = "";
		if (ad.getState() != 1) {
			state = "<td><font color=\"#E55291\">"
					+ ((ad.getState() != 1 && ad.getState() == 2) ? "Expired"
							: "Withdrawn") + "</td>";
		}
		if (state.equals("")) {
			ago_and_id.setText(Html.fromHtml(DateUtil.intervalTime(
					ad.getCurrt_time(), ad.getRealease_time())
					+ " ago | #" + ad.getAd_id()));
		} else {
			ago_and_id.setText(Html.fromHtml(DateUtil.intervalTime(
					ad.getCurrt_time(), ad.getRealease_time())
					+ " ago | #" + ad.getAd_id() + " | " + state));
		}
		tv_phone.setText(ad.getPhone());
		ad_content.setText(Html.fromHtml(ad.getDescribe()));
		if (ad.isIn_watchlist()) {
			iv_logo_item_watch.setVisibility(View.VISIBLE);
		}
		updateWatch();
	}

	class withDrawAdTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if (!TextUtils.isEmpty(result)) {
				DialogHelper.showTost(mContext, result);
			} else {
				DialogHelper.showTost(mContext,
						getString(R.string.Withdraw_success));

				ADDetailActivity.this.finish();
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
			AdWS ws = new AdWS(mContext);
			JSONObject jsonObject = ws.ad_withdrawn(session_id, ad.getAd_id()
					+ "");
			if (JUtil.checkStaus(jsonObject)) {
				return "";
			} else {
				return JUtil.getError(jsonObject);
			}

		}

	}

	class addWatchlistAdTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if (!TextUtils.isEmpty(result)) {
				DialogHelper.showTost(mContext, result);
			} else {
				ad.setIn_watchlist(true);
				updateWatch();
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
			AdWS ws = new AdWS(mContext);
			JSONObject jsonObject = ws.add_to_watchlist(session_id,
					ad.getAd_id() + "");
			if (JUtil.checkStaus(jsonObject)) {
				return "";
			} else {
				return JUtil.getError(jsonObject);
			}

		}

	}

	class delWatchlistAdTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if (!TextUtils.isEmpty(result)) {
				DialogHelper.showTost(mContext, result);
			} else {
				ad.setIn_watchlist(false);
				updateWatch();
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
			AdWS ws = new AdWS(mContext);
			JSONObject jsonObject = ws.del_watchlist(session_id, ad.getAd_id()
					+ "");
			if (JUtil.checkStaus(jsonObject)) {
				return "";
			} else {
				return JUtil.getError(jsonObject);
			}

		}

	}

	class relistAdTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if (!TextUtils.isEmpty(result)) {
				DialogHelper.showTost(mContext, result);
			} else {
				DialogHelper.showTost(mContext,
						getString(R.string.relist_success));
				ADDetailActivity.this.finish();
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
			AdWS ws = new AdWS(mContext);
			JSONObject jsonObject = ws.relist_ad(session_id, JUtil.toJson(ad)
					+ "");
			if (JUtil.checkStaus(jsonObject)) {
				return "";
			} else {
				return JUtil.getError(jsonObject);
			}

		}

	}

	class getCommentsTask extends AsyncTask<Object, Integer, List<CommentMDL>> {

		@Override
		protected void onPostExecute(List<CommentMDL> result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			// swipeRefreshLayout.setRefreshing(false);
			if (result != null) {
				if (pageIndex == 1) {
					llCommentPanel.removeAllViews();
				}
				for (CommentMDL commentMDL : result) {
					if (commentMDL.getUser_id() == ad.getUser_id()) {
						ChatLeftItemView view = ChatLeftItemView_
								.build(mContext);
						view.setVal(commentMDL);
						llCommentPanel.addView(view);
					} else {
						ChatRightItemView view = ChatRightItemView_
								.build(mContext);
						view.setVal(commentMDL);
						llCommentPanel.addView(view);
					}
				}
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
		protected List<CommentMDL> doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			AdWS ws = new AdWS(mContext);
			JSONObject jsonObject = ws.getAdComments(pageIndex + "",
					ad.getAd_id() + "");
			if (JUtil.checkStaus(jsonObject)) {
				return JUtil.parseArray(JUtil.getData(jsonObject, "ads"),
						CommentMDL.class);
			} else {
				return null;
			}

		}

	}

	class sendCommentsTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if (!TextUtils.isEmpty(result)) {
				DialogHelper.showTost(mContext, result);
			} else {
				new getCommentsTask().execute("");
				closePingLun();
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
			AdWS ws = new AdWS(mContext);
			String contentString = editContent.getText().toString();
			JSONObject jsonObject = ws.addAdComments(getSessionId(),
					ad.getAd_id() + "", contentString);
			if (JUtil.checkStaus(jsonObject)) {
				return "";
			} else {
				return JUtil.getError(jsonObject);
			}

		}

	}

	class getAdTask extends AsyncTask<Object, Integer, AD> {

		@Override
		protected void onPostExecute(AD result) {
			// TODO Auto-generated method stub
			DialogHelper.CloseLoadingDialog();
			if (result != null) {
				ad = result;
				imgs.clear();
				addPic(ad.getPic1());
				addPic(ad.getPic2());
				addPic(ad.getPic3());
				addPic(ad.getPic4());
				addPic(ad.getPic5());
				update_view();
				initView();
				differentPageSetting();
				new getCommentsTask().execute("");
			}
			else
			{
				DialogHelper.showTost(mContext, "error data");
				ADDetailActivity.this.finish();
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
		protected AD doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			AdWS ws = new AdWS(mContext);

			JSONObject jsonObject = ws.getAd(getSessionId(),ad_id + "");
			if (JUtil.checkStaus(jsonObject)) {
				List<AD> ads = JUtil.parseArray(
						JUtil.getData(jsonObject, "ads"), AD.class);
				if (ads.size() > 0) {
					return ads.get(0);
				} else {
					return null;
				}

			} else {
				return null;
			}

		}

	}
}
