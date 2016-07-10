package com.vc.swoop.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.vc.swoop.R;
import com.vc.swoop.adapter.SellingListAdapter.ViewHolder;
import com.vc.swoop.common.App;
import com.vc.swoop.common.DateUtil;
import com.vc.swoop.common.LBSUtil;
import com.vc.swoop.common.TextU;
import com.vc.swoop.db.ADCommentCountDAL;
import com.vc.swoop.model.AD;
import com.vc.util.ObjectHelper;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WatchListAdapter extends BaseAdapter {

	Context context;
	List<AD> ads = new ArrayList<AD>();
	// private CCBitmap fb;
	private double lat;
	private double lng;
	private float x, ux; 
	public static final int SELLIND_ACTIVITY = 1;
	public static final int WATCHLIST_ACTIVITY = 2;
	public int currentType = 1;
	public int screenWidth;
	LayoutInflater mInflater;
	DisplayImageOptions options;
	boolean isDel=false;
	ADCommentCountDAL dal;
	public WatchListAdapter(Context act, List<AD> ads ) {
		this.context =   act;
		this.ads=ads;
		mInflater = LayoutInflater.from(context); 
		dal=new ADCommentCountDAL(context);
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(
				R.drawable.img).cacheInMemory(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.NONE)
				.bitmapConfig(Bitmap.Config.RGB_565)//����ΪRGB565����Ĭ�ϵ�ARGB_8888Ҫ��ʡ�������ڴ�
				.delayBeforeLoading(100)//����ͼƬǰ������ʱ����������廬����������
				.build();
	}
	public void setDelMode(boolean bool) {
		isDel = bool;
		notifyDataSetChanged();
	}
	public void setType(int type) {
		currentType = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ads.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return ads.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	LinearLayout ll;

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder mHolder = null;

		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_selling_list, null);
			mHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
			mHolder.distance = (TextView) convertView
					.findViewById(R.id.tv_distance);
			mHolder.price = (TextView) convertView.findViewById(R.id.tv_price);
			mHolder.time = (TextView) convertView.findViewById(R.id.tv_time);

			mHolder.content = (TextView) convertView
					.findViewById(R.id.tv_content);
			mHolder.image = (ImageView) convertView.findViewById(R.id.ivImg);
			mHolder.watch = (LinearLayout) convertView
					.findViewById(R.id.ll_watch);
			mHolder.frame = (LinearLayout) convertView
					.findViewById(R.id.rest_item_frame);
			mHolder.ll_content = (LinearLayout) convertView
					.findViewById(R.id.content);
			mHolder.selling = (LinearLayout) convertView
					.findViewById(R.id.ll_selling);
			mHolder.selling2 = (LinearLayout) convertView
					.findViewById(R.id.ll_selling2);
			mHolder.selling_relist = (TextView) convertView
					.findViewById(R.id.selling_relist);
			mHolder.selling_withdrawn = (TextView) convertView
					.findViewById(R.id.selling_withdrawn);
			mHolder.selling_edit = (LinearLayout) convertView
					.findViewById(R.id.selling_edit);
			mHolder.watch_remove = (TextView) convertView
					.findViewById(R.id.watch_remove);
			mHolder.delete = (ImageView) convertView
					.findViewById(R.id.btn_delete);
			mHolder.imgComments=(ImageView)convertView.findViewById(R.id.imgComments);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		lng=App.getApp(context).getLongitude();
		lat=App.getApp(context).getLatitude();
		final AD ad = ads.get(position);


		mHolder.title.setText(ad.getTitle());
		String distance = LBSUtil.distanceStr(ad.getLng(), ad.getLat(), lng,
				lat);
		mHolder.distance.setText(distance);
		mHolder.time.setText("Q&A("+ad.getCommentcount()+")");
		int oldcommentcount= ObjectHelper.Convert2Int(dal.select(ad.getAd_id()+""));
		if(oldcommentcount!=ad.getCommentcount())
		{
			dal.insert(ad.getAd_id()+"", ad.getCommentcount()+"");
			mHolder.imgComments.setImageResource(R.drawable.comment_2);
		}else {
			mHolder.imgComments.setImageResource(R.drawable.comment_1);
		}
//		if (currentType == SELLIND_ACTIVITY) {
//			mHolder.distance.setVisibility(View.GONE);
//
//			String d = DateUtil.parseDate(ad.getEnd_time());
//			String withdrawn_time = DateUtil.parseDate(ad.getWithdrawn_time());
//			if (ad.getState() == 1) {
//				String timeStr = DateUtil.intervalTime(ad.getEnd_time(),
//						ad.getCurrt_time());
//				String timeStr1 = DateUtil.intervalTime(
//						ad.getCurrt_time() + 600000, ad.getCurrt_time());
//				mHolder.time.setText("Ends in:" + timeStr);
//			} else if (ad.getState() == 2)
//				mHolder.time.setText(Html
//						.fromHtml("<td><font color=\"#E55291\">Expired:" + d
//								+ "</td>"));
//			else if (ad.getState() == 3) {
//				mHolder.time.setText(Html
//						.fromHtml("<td><font color=\"#E55291\">Withdrawn:"
//								+ withdrawn_time + "</td>"));
//				LinearLayout.LayoutParams lpLayoutParams = (LinearLayout.LayoutParams) mHolder.time
//						.getLayoutParams();
//				lpLayoutParams.weight = 2;
//				mHolder.time.setLayoutParams(lpLayoutParams);
//				mHolder.distance.setVisibility(View.GONE);
//			}
//		} else if ((currentType == WATCHLIST_ACTIVITY)) {
//			String timeStr = DateUtil.intervalTime(ad.getCurrt_time(),
//					ad.getRealease_time());
//			mHolder.time.setText(timeStr + " ago");
//			String distance = LBSUtil.distanceStr(ad.getLng(), ad.getLat(),
//					lng, lat);
//			mHolder.distance.setVisibility(View.VISIBLE);
//			mHolder.distance.setText(distance);
//		}
		double price = 0;
		if (ad.getPrice() != 0) {
			price = ad.getPrice();
		}
		if (ad.getPricetype() == 0) {
			if (price == 0) {
				mHolder.price.setText("Free");
			} else {
				mHolder.price.setText("$" + TextU.ShowPrice(price));
			}
			// distanprice+="   |   $"+ ad.getPrice();
		} else if (ad.getPricetype() == 1) {
			mHolder.price.setText("swap");
			// distanprice+="   |   swap";
		} else if (ad.getPricetype() == 2) {
			mHolder.price.setText("offer");
			// distanprice+="   |   offer";
		}
		if (isDel) {
			mHolder.delete.setVisibility(View.VISIBLE);
		} else {
			mHolder.delete.setVisibility(View.GONE);
		}
		if(ad.isCheck())
		{
			mHolder.delete.setImageResource(R.drawable.blue_indicator);
		}
		else{
			mHolder.delete.setImageResource(R.drawable.grey_indicator);
		}
		mHolder.selling.setVisibility(View.GONE);
		ImageAware imageAware = new ImageViewAware(mHolder.image, false);
		ImageLoader.getInstance().displayImage(ad.getPic1(), imageAware,
				options);
		

		 
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView content;
		TextView selling_relist;
		TextView selling_withdrawn;
		LinearLayout selling_edit;
		TextView watch_remove;
		TextView price;
		ImageView chat;
		TextView distance;
		LinearLayout watch;
		LinearLayout selling;
		LinearLayout selling2;
		LinearLayout frame;
		LinearLayout ll_content;
		TextView time;
		ImageView image;
		ImageView imgComments;
		ImageView delete;
	}

}
