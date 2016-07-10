package com.vc.swoop.widgets;

import java.util.LinkedList;
import java.util.List;

import com.vc.swoop.R;
import com.vc.swoop.model.CategoryMDL;
 
import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dialog_Categories extends Dialog{

	Context mContext;
	LinearLayout llCate,llLatest,llNearest;
	ImageView imgLatest,imgNearest;
	TextView tvNearest,tvLatest;
	List<View_Categories_Item> Views;
	int selectIndex=0;
	int selectTextColor = 0xff459ecc;
	int unselectTextColor = 0xff959595;
	public SelectFilterListener selectFilterListener;
	String orderByField="latest";
	List<CategoryMDL> categoryMDLs = CategoryMDL.GetDatas();
	public Dialog_Categories(Context context,SelectFilterListener l) {
		super(context,R.style.FullDialog);
		  setOwnerActivity((Activity)context);
		mContext=context;
		selectFilterListener=l;
		// TODO Auto-generated constructor stub
	}
	public Dialog_Categories(Context context, int theme){
        super(context, theme);
        this.mContext = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Views=new LinkedList<View_Categories_Item>();
        this.setContentView(R.layout.view_categories);
        ((LinearLayout)findViewById(R.id.llHide)).setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog_Categories.this.dismiss();
			}
		});
        llCate=(LinearLayout)findViewById(R.id.llCate);
        llLatest=(LinearLayout)findViewById(R.id.llLatest);
        llNearest=(LinearLayout)findViewById(R.id.llNearest);
        imgLatest=(ImageView)findViewById(R.id.imgLatest);
        imgNearest=(ImageView)findViewById(R.id.imgNearest);
        tvNearest=(TextView)findViewById(R.id.tvNearest);
        tvLatest=(TextView)findViewById(R.id.tvLatest);
        llLatest.setOnClickListener(onClickListener);
        llNearest.setOnClickListener(onClickListener);
        
        categoryMDLs.add(0, new CategoryMDL(0, "All", ""));
        for (int i=0;i<categoryMDLs.size();i++) {
			View_Categories_Item item=new View_Categories_Item(mContext,categoryMDLs.get(i));
			item.setTag(i);
			item.setOnClickListener(onItemClickListener);
			if(i==0)
				item.setSelect(true);
			else
				item.setSelect(false);
			llCate.addView(item);
			Views.add(item);
			//item.setVal(categoryMDL.get, des)
		}
        imgLatest.setImageResource(R.drawable.latest_1);
		tvLatest.setTextColor(unselectTextColor);
		imgNearest.setImageResource(R.drawable.nearest_2);
		tvNearest.setTextColor(selectTextColor);
		orderByField="nearest";
    }
    private android.view.View.OnClickListener onItemClickListener= new android.view.View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Views.get(selectIndex).setSelect(false);
			selectIndex=(Integer)v.getTag();
			Views.get(selectIndex).setSelect(true);
			loadDataListener();
		}
	};
	public void loadDataListener()
	{
		if(selectFilterListener!=null)
		{
			selectFilterListener.select(orderByField, categoryMDLs.get(selectIndex).getId());
  		}
	}
	private android.view.View.OnClickListener onClickListener= new android.view.View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId()==R.id.llNearest)
			{
				imgLatest.setImageResource(R.drawable.latest_1);
				tvLatest.setTextColor(unselectTextColor);
				imgNearest.setImageResource(R.drawable.nearest_2);
				tvNearest.setTextColor(selectTextColor);
				orderByField="nearest";
			}
			else if (v.getId()==R.id.llLatest) {
				imgLatest.setImageResource(R.drawable.latest_2);
				tvLatest.setTextColor(selectTextColor);
				imgNearest.setImageResource(R.drawable.nearest_1);
				tvNearest.setTextColor(unselectTextColor);
				orderByField="latest";
			}
			loadDataListener();
		}
	};
    public void showDialog()
    {
    	this.show();
    	
    	WindowManager windowManager = ((Activity)mContext).getWindowManager();
    	 //window.setWindowAnimations(R.style.); // 
    	 Display display = windowManager.getDefaultDisplay();
    	 WindowManager.LayoutParams lp = this.getWindow().getAttributes();
    	 lp.width = (int)(display.getWidth()); // 
    	 lp.height=(int)(display.getHeight());
    	this.getWindow().setAttributes(lp); 
    	this.getWindow().setWindowAnimations(R.style.cate_dialog_animation);
    }
    public interface SelectFilterListener
    {
    	void select(String orderby,int categoriesid);
    }
}
