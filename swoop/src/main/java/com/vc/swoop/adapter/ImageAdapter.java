package com.vc.swoop.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vc.swoop.AddOrEditAdActivity;
import com.vc.swoop.AddOrEditAdActivity_;
import com.vc.swoop.R;
 
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class ImageAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
//	List<Bitmap> imgs = new ArrayList<Bitmap>();
//	List<String> urls = new ArrayList<String>();
	List<Map<String,Object>> imgs = new ArrayList<Map<String,Object>>();
	int mGalleryItemBackground;
	DisplayImageOptions options; 
	
	public ImageAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.img).build();//
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		if (isEdit) {
//			if (urls != null && urls.size() < 5)
//				return urls.size() + 1;
//			else 
//				return urls.size();
//		} else {
		//	if (imgs != null && imgs.size() < 5)
		//		return imgs.size() + 1;
		//	else 
				return imgs.size();
//		}
	}
	
	public void setImgs(List<Map<String,Object>> imgs ) {
		this.imgs = imgs;
		notifyDataSetChanged();
	}

//	boolean isEdit  = false;
//	public void setUrls(List<String> urls) {
//		this.urls = urls;
//		isEdit = true;
//		notifyDataSetChanged();
//		isEdit = false;
//	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.item_add_pic, null);
		ImageView pic = (ImageView) view.findViewById(R.id.grllery_pic);
		ImageButton del_pic = (ImageButton) view.findViewById(R.id.del_pic);
		LinearLayout add = (LinearLayout) view.findViewById(R.id.grllery_add_pic);
		
		if (imgs.size() < 5 && position == imgs.size()) {
			add.setVisibility(View.VISIBLE);
			pic.setVisibility(View.GONE);
			del_pic.setVisibility(View.GONE);
			pic.setScaleType(ScaleType.CENTER_CROP);
			add.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					((AddOrEditAdActivity_)context).pickupImageItem();
				}
			});
			view.setVisibility(View.GONE);
		} else {
			add.setVisibility(View.GONE);
			pic.setVisibility(View.VISIBLE);
			pic.setScaleType(ScaleType.CENTER_CROP);
			if (imgs.get(position) != null) {
				if (!(Boolean)imgs.get(position).get("isEdit")) {
					pic.setImageBitmap((Bitmap)imgs.get(position).get("bmp"));
				} else {
					ImageLoader.getInstance().displayImage((String)imgs.get(position).get("url"), pic,options); 
					
				}
			}
				
		}
		
		del_pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!(Boolean)imgs.get(position).get("isEdit")) {
					Bitmap bmp = (Bitmap)imgs.get(position).get("bmp");
					imgs.remove(position);
					if (bmp != null && !bmp.isRecycled()) {
						bmp.recycle();   
						bmp = null;  
					}
					notifyDataSetChanged();
				} else {
					imgs.remove(position);
					notifyDataSetChanged();
				}
				
			}
		});
		return view;
		
	}
	

}
