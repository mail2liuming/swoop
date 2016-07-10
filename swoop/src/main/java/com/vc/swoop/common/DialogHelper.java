package com.vc.swoop.common;
 
import com.vc.swoop.R; 
import com.vc.swoop.widgets.ComfrimDialog;
import com.vc.swoop.widgets.ComfrimDialog.OnCancelListener;
import com.vc.swoop.widgets.ComfrimDialog.OnConfirmListener;
import com.vc.swoop.widgets.LoadingDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DialogHelper {

	static LoadingDialog dialog;
	static ComfrimDialog comfrimDialog;

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static void ShowLoadingDialog(Context context, String msg) {
		if (dialog == null) {
			dialog = LoadingDialog.createDialog(context);
			dialog.setCancelable(false);

		} else {
			dialog.setTitle(msg);
		}
		dialog.show();

	}

	public static void CloseLoadingDialog() {
		if (dialog != null&& dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}

	}

	public static void showTost(Context context, String mess) {
		try {
			Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

//	public static void showComfrimDialog(Context c, String title, String msg,
//			String okTitle, DialogInterface.OnClickListener okClickListener,
//			String cancleTitle,
//			DialogInterface.OnClickListener cancleClickListener) {
//		Builder dialog = new AlertDialog.Builder(c).setTitle(title)
//				.setMessage(msg).setPositiveButton(okTitle, okClickListener);
//		if (cancleClickListener != null)
//			dialog.setNegativeButton(cancleTitle, cancleClickListener);
//
//		dialog.show();
//	}

	public static void showComfrimDialog(Context c,String title,  String msg,String okTitle,
			OnConfirmListener okClickListener,String cancleTitle,
			OnCancelListener cancleClickListener) {
		comfrimDialog=new ComfrimDialog(c);
		comfrimDialog.setMessageText(msg);
		if(!TextUtils.isEmpty(okTitle))
			comfrimDialog.setConfirmButtonText(okTitle);
		if(!TextUtils.isEmpty(cancleTitle))
			comfrimDialog.setCanceButtonText(cancleTitle);
		if(!TextUtils.isEmpty(title))
			comfrimDialog.setTitle(title);
		comfrimDialog.setOnCancelListener(cancleClickListener);
		comfrimDialog.setOnConfirmListener(okClickListener);
		comfrimDialog.show();
	}
	
}
