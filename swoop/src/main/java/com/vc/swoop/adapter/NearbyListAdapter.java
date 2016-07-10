package com.vc.swoop.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.vc.swoop.R;
import com.vc.swoop.common.App;
import com.vc.swoop.common.DateUtil;
import com.vc.swoop.common.ImageLoadHelper;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearbyListAdapter extends BaseAdapter {

	Context context;
	LayoutInflater mInflater;
	DisplayImageOptions options;
	List<AD> ads = new ArrayList<AD>();

	ADCommentCountDAL dal;

	public NearbyListAdapter(Context act, List<AD> ads) {
		this.context = act;
		this.ads = ads;
		 
		mInflater = LayoutInflater.from(context);
		dal = new ADCommentCountDAL(context);
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(
				R.drawable.img).cacheInMemory(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.NONE)
				.bitmapConfig(Bitmap.Config.RGB_565)//����ΪRGB565����Ĭ�ϵ�ARGB_8888Ҫ��ʡ�������ڴ�
				.delayBeforeLoading(100)//����ͼƬǰ������ʱ����������廬����������
				.build();
	}

	public List<AD> getAdList() {
		return ads;
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
			convertView = mInflater.inflate(R.layout.item_nearby_list, null);
			mHolder.imgComments = (ImageView) convertView
					.findViewById(R.id.imgComments);
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

			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		double lng = App.getApp(context).getLongitude();
		double lat = App.getApp(context).getLatitude();
		final AD ad = ads.get(position);
		mHolder.title.setText(ad.getTitle());
		mHolder.time.setText("Q&A(" + ad.getCommentcount() + ")");
		// String timeStr = DateUtil.intervalTime(ad.getCurrt_time(),
		// ad.getRealease_time());
		//
		// mHolder.time.setText(timeStr + " ago");
		String distance = LBSUtil.distanceStr(ad.getLng(), ad.getLat(), lng,
				lat);
		mHolder.distance.setText(distance);
		// double price = 0;
		// if (ad.getPrice() != 0) {
		// price = ad.getPrice();
		// }
		// mHolder.price.setText("$" + price);
		double price = 0;
		if (ad.getPrice() != 0) {
			price = ad.getPrice();
		}
		int oldcommentcount = ObjectHelper.Convert2Int(dal.select(ad.getAd_id()
				+ ""));
		if (oldcommentcount != ad.getCommentcount()) {
			dal.insert(ad.getAd_id() + "", ad.getCommentcount() + "");
			mHolder.imgComments.setImageResource(R.drawable.comment_2);
		} else {
			mHolder.imgComments.setImageResource(R.drawable.comment_1);
		}

		if (ad.getPricetype() == 0) {
			if (price == 0) {
				mHolder.price.setText("Free");
			} else {
				mHolder.price.setText("$" + TextU.ShowPrice(price));
			}

			// distanprice+="   |   $"+ ad.getPrice();
		} else if (ad.getPricetype() == 1) {
			mHolder.price.setText("Swap");
			// distanprice+="   |   swap";
		} else if (ad.getPricetype() == 2) {
			mHolder.price.setText("Offer");
			// distanprice+="   |   offer";
		}

		mHolder.content.setText(ad.getDescribe());

		// if (ad.isIn_watchlist()) {
		// mHolder.iv_logo_item_watch.setVisibility(View.VISIBLE);
		// } else {
		// mHolder.iv_logo_item_watch.setVisibility(View.INVISIBLE);
		// }
		ImageAware imageAware = new ImageViewAware(mHolder.image, false);
		ImageLoader.getInstance().displayImage(ad.getPic1(), imageAware,
				options);
		
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView content;
		TextView price;
		TextView distance;
		LinearLayout watch;
		TextView time;
		ImageView image;
		ImageView imgComments;
		ImageView iv_logo_item_watch;
	}

}
