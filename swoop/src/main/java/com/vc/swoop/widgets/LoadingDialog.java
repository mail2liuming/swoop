package com.vc.swoop.widgets;
 
import com.vc.swoop.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 正在加载进度条 
 */
public class LoadingDialog extends Dialog{
	  
	  private static LoadingDialog customProgressDialog = null;
	  private Context context = null;
	  
	  public LoadingDialog(Context context)  {
		  super(context);
		  this.context = context;
	  }
	  
	  public LoadingDialog(Context context, int theme) {
		  super(context, theme);
	  }
	  
	  public static LoadingDialog createDialog(Context context)  {
		  customProgressDialog = new LoadingDialog(context, R.style.MyDialog);
		  customProgressDialog.setContentView(R.layout.dialog_loading);
		  customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		  return customProgressDialog;
	  }
	  
	  public void onWindowFocusChanged(boolean paramBoolean)  {
		  if (customProgressDialog != null)
	    		((AnimationDrawable)((ImageView)customProgressDialog.findViewById(R.id.loadingImageView)).getBackground()).start();
	  }
	  
	  public LoadingDialog setMessage(String paramString)  {
		  TextView localTextView = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
		  if (localTextView != null)
			  localTextView.setText(paramString);
		  return customProgressDialog;
	  }
	  
	  public LoadingDialog setTitle(String paramString)  {
		  return customProgressDialog;
	  }
	  
}