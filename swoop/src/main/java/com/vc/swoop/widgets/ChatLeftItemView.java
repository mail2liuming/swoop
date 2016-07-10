package com.vc.swoop.widgets;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import com.vc.swoop.R;
import com.vc.swoop.common.DateUtil;
import com.vc.swoop.model.CommentMDL;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
@EViewGroup(R.layout.item_comment_left)
public class ChatLeftItemView extends LinearLayout{
	Context mContext;
	@ViewById
	TextView tvTime,tvName,tvContent;
	public ChatLeftItemView(Context context) {
		super(context);
		mContext=context;
		// TODO Auto-generated constructor stub
	}
	
	public void setVal(CommentMDL mdl)
	{
		if(mdl!=null)
		{
			tvName.setText(mdl.getUser_name());
			tvContent.setText(mdl.getContent());
			String timeStr = DateUtil.intervalTimeByCommtent(mdl.getCurrt_time(), mdl.getIntime());
			tvTime.setText(timeStr);
		}
	}
}
