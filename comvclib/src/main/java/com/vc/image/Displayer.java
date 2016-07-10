package com.vc.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface Displayer {

	/**
	 * @param imageView
	 * @param bitmap
	 * @param config
	 */
	public void loadCompletedisplay(ImageView imageView,Bitmap bitmap,BitmapDisplayConfig config);
	
	public void loadCompletedisplay(ImageView imageView,Bitmap bitmap,BitmapDisplayConfig config,boolean loadAnim);
	
	/**
	 * @param imageView
	 * @param bitmap
	 */
	public void loadFailDisplay(ImageView imageView,Bitmap bitmap);
	
}
