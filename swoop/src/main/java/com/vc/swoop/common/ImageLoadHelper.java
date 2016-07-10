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

	// ����ͼƬ��options���� ��Բ�����õ� ʹ���� RoundedBitmapDisplayer �������ڴ�
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

	// �����붯��������ʾ���в���
	public static DisplayImageOptions getCenterOptions(float roundvalue,
			Context ct) {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.img).cacheInMemory(true)// �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.considerExifParams(true) // �Ƿ���JPEGͼ��EXIF��������ת����ת��
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)// ����ͼƬ����εı��뷽ʽ��ʾ
				.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������//
				.resetViewBeforeLoading(true)
				// ����ͼƬ������ǰ�Ƿ����ã���λ
				.displayer(
						new RoundedBitmapDisplayer(DensityHelper.dip2px(ct,
								roundvalue)))// �Ƿ�����ΪԲ�ǣ�����Ϊ����
				.displayer(new FadeInBitmapDisplayer(100))// �Ƿ�ͼƬ���غú���Ķ���ʱ��
				.build();// �������
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

	// ������
	public static DisplayImageOptions getHqOptions(Context ct) {
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.img).cacheInMemory(true)
				.bitmapConfig(Bitmap.Config.ARGB_8888).cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}

	// ��ʼ��ͼƬ�ؼ� ��application�����ʼ��
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
