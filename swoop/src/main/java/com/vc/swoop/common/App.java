package com.vc.swoop.common;
 

import java.io.File;
 
import cn.jpush.android.api.JPushInterface;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.vc.swoop.db.AppConfigDAL;
import com.vc.swoop.model.User;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class App extends Application{
	public static Object threadDBLock="";
	private User user;
	double longitude = 0;
	double latitude = 0;
	String imei;
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public static App getApp(Context mContext) {
		return (App) mContext.getApplicationContext();
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
//		JPushInterface.setDebugMode(true); 
//		JPushInterface.init(this);
//		String ss=JPushInterface.getRegistrationID(this);
//		boolean r=JPushInterface.getConnectionState(this);
//		JPushInterface.setAliasAndTags(getApplicationContext(), "vicky", null);
		CrashReport.initCrashReport(this, "900002233", false);
		String userString=new AppConfigDAL(this).select(AppConfigDAL.user_key);
		if(!TextUtils.isEmpty(userString))
		{
			User user=JUtil.parseObject(userString, User.class);
			if(user!=null)
			{
//				 DialogHelper.showTost(this, "EXTRA_REGISTRATION_ID="+regId);
//				JPushInterface.setAliasAndTags(getApplicationContext(), user.getUser_id()+"", null);
			}
			setUser(user);
			
		}
		TelephonyManager telephonyManager= (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		String imeistring=telephonyManager.getDeviceId();
		setImei(imeistring);
		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageapi/Cache");  
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height���������ÿ�������ļ�����󳤿�
				.threadPoolSize(3)
				// �̳߳��ڼ��ص�����
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/�����ͨ���Լ����ڴ滺��ʵ��
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// �������ʱ���URI�����MD5 ����
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// ������ļ�����
				.discCache(new UnlimitedDiscCache(cacheDir))
				// �Զ��建��·��
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
																			// (5
																			// s),
																			// readTimeout
																			// (30
																			// s)��ʱʱ��
				.writeDebugLogs() // Remove for release app
				.build();// ��ʼ����
		ImageLoader.getInstance().init(config);
		super.onCreate();
	}
}
