package com.vc.swoop.common;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.vc.swoop.R;
import com.vc.util.DensityHelper;

public class ImageLoadHelper {
	private static DisplayImageOptions options = null;
	private static ImageLoadHelper mInstance = null;

	public static ImageLoadHelper getInstance() {
		if (null != mInstance) {
			return mInstance;
		} else {
			mInstance = new ImageLoadHelper();
			return mInstance;
		}
	}

	// 加载图片的options配置 有圆形配置的 使用了 RoundedBitmapDisplayer 更消耗内存
	public static DisplayImageOptions getoptions(float roundvalue, Context ct) {

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.img)
				.cacheInMemory(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(
						new RoundedBitmapDisplayer(DensityHelper.dip2px(ct,
								roundvalue))).build();

		return options;
	}

	// 带载入动画，或显示居中部分
	public static DisplayImageOptions getCenterOptions(float roundvalue,
			Context ct) {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.img).cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.resetViewBeforeLoading(true)
				// 设置图片在下载前是否重置，复位
				.displayer(
						new RoundedBitmapDisplayer(DensityHelper.dip2px(ct,
								roundvalue)))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成
		return options;
	}

	public static DisplayImageOptions getoptions(Context ct) {
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.img)
				.cacheInMemory(true)
				.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}

	// 高质量
	public static DisplayImageOptions getHqOptions(Context ct) {
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.img).cacheInMemory(true)
				.bitmapConfig(Bitmap.Config.ARGB_8888).cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}

	// 初始化图片控件 在application里面初始化
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		// config.writeDebugLogs();
		ImageLoader.getInstance().init(config.build());
	}

}
