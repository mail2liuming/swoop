package com.vc.swoop.widgets;

import com.vc.swoop.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ComfrimDialog extends Dialog {

	TextView tv_message;
	TextView tv_title;
	Button btn_cancel, btn_confirm;
	private OnConfirmListener onConfirmListener;
	private OnCancelListener onCancelListener;

	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	public void setOnCancelListener(OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	public ComfrimDialog(Context context) {
		super(context, R.style.activity_translucent);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.tip_confirm);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setVisibility(View.GONE);
		tv_message = (TextView) findViewById(R.id.tv_message);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (onConfirmListener != null)
					onConfirmListener.confirm();
				ComfrimDialog.this.dismiss();
			}
		});
		btn_cancel.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (onCancelListener != null)
					onCancelListener.cancel();
				ComfrimDialog.this.dismiss();
			}
		});
	}

	public void setConfirmButtonText(String text) {
		btn_confirm.setText(text);
	}
	public void setCanceButtonText(String text) {
		btn_cancel.setText(text);
	}
	public void setMessageText(String text) {
		tv_message.setText(text);
	}
	public void setTitle(String title)
	{
		tv_title.setText(title);
		tv_title.setVisibility(View.VISIBLE);
	}
	public interface OnConfirmListener {
		void confirm();
	}

	public interface OnCancelListener {
		void cancel();
	}
}
