package com.vc.image;
 
import android.net.Uri;


/*
 * */
public interface IImageView {
	
	
	/*
	 * */
	abstract void dispose(boolean isClearCache);
	
	/*
	 * */
	abstract void setImageUrl(String url);
	
	/*
	 * @param url url
	 * */
	abstract void setImageUrlNotCache(String url);
	
	/*
	 * */
	abstract void setImageUrl(String url,int faileResid);
	
	/*
	 * */
	abstract void loadRes(int res);
	
	/*
	 * @param uri uri
	 * */
	abstract void loadUri(Uri uri);
	
	/*
	 * */
	abstract void loadUri(String fileName);
	
	/*
	 * @param isScale
	 * */
	abstract void setScaleEnable(boolean isScale);
	
	
	/*
	 * @param isScale
	 * */
	abstract void toggleFillScreen();
	
	
	/*
	 * */
	abstract void cancelLoadingTask();
	
	
	/*
	 * */
	abstract void clientRender(int res);

	/**
	* @Title: setImageUrlMemoryCache
	* @Description: TODO
	* @param @param url    
	* @return void    
	* @throws
	*/
	void setImageUrlMemoryCache(String url);
	
}
