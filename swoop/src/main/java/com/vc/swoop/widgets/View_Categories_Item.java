package com.vc.swoop.widgets;
 
import com.vc.swoop.R;
import com.vc.swoop.model.CategoryMDL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class View_Categories_Item extends LinearLayout{
	private Context mContext;
	private TextView tvName,tvDes;
	CategoryMDL categoryMDL;
	int p_l=0,p_r=0,p_t=0,p_b=0;
	public View_Categories_Item(Context context, CategoryMDL categoryMDL) {
		super(context);
		mContext=context;
		this. categoryMDL= categoryMDL;
		init();
		setVal(categoryMDL.getName(),categoryMDL.getDes());
		// TODO Auto-generated constructor stub
	}
	public View_Categories_Item(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		init();
		// TODO Auto-generated constructor stub
	}
	private void init()
	{
		LayoutInflater.from(mContext).inflate(R.layout.view_item_categories, this);
		tvName=(TextView)findViewById(R.id.tvName);
		tvDes=(TextView)findViewById(R.id.tvDes);
		p_l=tvName.getPaddingLeft();
		p_b=tvName.getPaddingBottom();
		p_r=tvName.getPaddingRight();
		p_t=tvName.getPaddingTop();
	}
	@SuppressLint("NewApi")
	public void setSelect(boolean flag)
	{
		if(flag)
		{
			tvName.setBackgroundColor(Color.parseColor("#dfdddd"));
			tvName.setPadding(p_l, p_t, p_r, p_b);
			//tvName.setTextColor(Color.parseColor("#dfdddd"));
			TextPaint tp = tvName.getPaint(); 
			tp.setFakeBoldText(true); 
		}
		else
		{
			tvName.setBackground(null);
			tvName.setPadding(p_l, p_t, p_r, p_b);
			//tvName.setTextColor(Color.BLACK);
			TextPaint tp = tvName.getPaint(); 
			tp.setFakeBoldText(false); 
		}
	}
	public void setVal(String name,String des)
	{
//		tvName.setText("го"+name);
		tvName.setText(name);
		if(TextUtils.isEmpty(des))
		{
			tvDes.setVisibility(View.GONE);
		}
		else {
			tvDes.setVisibility(View.VISIBLE);
			tvDes.setText(des);
		}
	}
}
