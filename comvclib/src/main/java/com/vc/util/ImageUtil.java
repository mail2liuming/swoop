/**  
 * @Title: ImageUtil.java
 * @Package com.uroad.util
 * @Description: TODO(��һ�仰�������ļ���ʲô)
 * @author oupy 
 * @date 2013-8-9 ����2:45:55
 * @version V1.0  
 */
package com.vc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;

/**
 * @author Administrator
 * 
 */
public class ImageUtil {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int TOP = 3;
	public static final int BOTTOM = 4;

	public static boolean isSDCardExist() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	public static Bitmap compBitmap(Bitmap image) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if( baos.toByteArray().length / 1024>1024) {//�ж����ͼƬ����1M,����ѹ�����������ͼƬ��BitmapFactory.decodeStream��ʱ���	
			baos.reset();//����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ�������ݴ�ŵ�baos��
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//���������ֻ�Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = 400f;//�������ø߶�Ϊ800f
		float ww = 400f;//�������ÿ��Ϊ480f
		//���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ����ݽ��м��㼴��
		int be = 1;//be=1��ʾ������
		if (w > h && w > ww) {//����ȴ�Ļ���ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//���߶ȸߵĻ���ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//�������ű���
		//���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);//ѹ���ñ����С���ٽ�������ѹ��
	}
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {	//ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��		
			baos.reset();//����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ�������ݴ�ŵ�baos��
			options -= 10;//ÿ�ζ�����10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ��������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream������ͼƬ
		return bitmap;
	}
	public static void writeFile(byte[] data, String fileName) {
		File f = new File(fileName);
		FileOutputStream fout = null;
		try {
			if (!f.exists())
				f.createNewFile();
			fout = new FileOutputStream(f);
			fout.write(data);
			fout.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Bitmap decodeFile(File f) {
		Bitmap b = null;
		int IMAGE_MAX_SIZE = 1000;
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			FileInputStream fis = new FileInputStream(f);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();

			int scale = 1;
			if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
				scale = (int) Math.pow(
						2,
						(int) Math.round(Math.log(IMAGE_MAX_SIZE
								/ (double) Math.max(o.outHeight, o.outWidth))
								/ Math.log(0.5)));
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o2);
			fis.close();
		} catch (IOException e) {
			return null;
		}
		return b;
	}
	public static Bitmap decodeFile(File f,int scale) {
		Bitmap b = null;
		int IMAGE_MAX_SIZE = 1000;
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			FileInputStream fis = new FileInputStream(f);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();


			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o2);
			fis.close();
		} catch (IOException e) {
			return null;
		}
		return b;
	}
	public static enum ScalingLogic {
		CROP, FIT
	}

	public static int calculateSampleSize(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.FIT) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;

			if (srcAspect > dstAspect) {
				return srcWidth / dstWidth;
			} else {
				return srcHeight / dstHeight;
			}
		} else {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;

			if (srcAspect > dstAspect) {
				return srcHeight / dstHeight;
			} else {
				return srcWidth / dstWidth;
			}
		}
	}

	public static Bitmap decodeSampledBitmapFromByte(byte[] res, int reqWidth,
			int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(res, 0, res.length, options);

		if (reqHeight == -1) {
			options.inSampleSize = calculateSampleSize(options.outWidth,
					options.outHeight, options.outWidth, options.outHeight,
					ScalingLogic.CROP);
		} else {
			options.inSampleSize = calculateSampleSize(options.outWidth,
					options.outHeight, reqWidth, reqHeight, ScalingLogic.CROP);

		}
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return getUnErrorBitmap(res, options);
	}

	private static Bitmap getUnErrorBitmap(byte[] res,
			BitmapFactory.Options options) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeByteArray(res, 0, res.length, options);
		} catch (OutOfMemoryError e) {
			options.inSampleSize += 1;
			return getUnErrorBitmap(res, options);
		}
		return bitmap;
	}

	public static String getBitmapWH(Bitmap bitmap) {// ��ȡandroid��ǰ�����ڴ��С
		byte[] datas = Bitmap2Bytes(bitmap);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
		return options.outWidth + "X" + options.outHeight;
	}

	/*
	 * ѹ��ͼƬ��������Ϊ���
	 */
	public static Bitmap CompressBitmapByQuality(Bitmap bitmap, int quality) {
		InputStream is = Bitmap2InputStream(bitmap, quality);
		return BitmapFactory.decodeStream(is);
	}

	/*
	 * ѹ��ͼƬ���Դ�СΪ��� quality Ϊ2����ԭ����1/2 4����1/4
	 */
	public static Bitmap CompressBitmapBySize(Bitmap bitmap, int quality) {
		InputStream is = Bitmap2InputStream(bitmap);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = quality;
		return BitmapFactory.decodeStream(is, null, options);
	}

	// ��Bitmapת����InputStream
	public static InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// ��Bitmapת����InputStream
	public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// ��InputStreamת����Bitmap
	public static Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * ��BitmapתByte
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static String getBitmapWH(Context context, int res) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), res, options);
		return options.outWidth + "X" + options.outHeight;
	}

	public static String getBitmapSize(Context context, int res) {// ��ȡandroid��ǰ�����ڴ��С

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), res, options);
		int size = 0;
		if (options.inPreferredConfig == Bitmap.Config.ARGB_8888) {
			size = options.outHeight * options.outWidth * 4;
		} else if (options.inPreferredConfig == Bitmap.Config.ARGB_4444) {
			size = options.outHeight * options.outWidth * 2;
		} else if (options.inPreferredConfig == Bitmap.Config.ALPHA_8) {
			size = options.outHeight * options.outWidth;
		}
		return Formatter.formatFileSize(context, size);// ����ȡ���ڴ��С���
	}

	public static String getBitmapSize(Context context, Bitmap bitmap) {// ��ȡandroid��ǰ�����ڴ��С

		long size = bitmap.getRowBytes() * bitmap.getHeight();

		return Formatter.formatFileSize(context, size);// ����ȡ���ڴ��С���
	}

	/**
	 * �Ŵ���СͼƬ
	 * 
	 * @param bitmap
	 * @param w
	 *            ���
	 * @param h
	 *            �߶�
	 * @return �����˵�ͼƬ
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

	/**
	 * ��Drawableת��ΪBitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmapת����Drawable
	 * 
	 * @param bmp
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bmp) {
		return new BitmapDrawable(bmp);
	}

	/**
	 * ���Բ��ͼƬ�ķ���
	 * 
	 * @param bitmap
	 *            ԭͼ
	 * @param roundPx
	 *            �Ƕ�
	 * @return �����ͼƬ
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * ��ô�Ӱ��ͼƬ����
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * byte����ת����Bitmap
	 * 
	 * @param bmp
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bmp) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * ����ת����Bitmap
	 * 
	 * @param buffer
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] buffer) {
		
		return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
	}
	/**
	 * ����ת����Bitmap
	 * 
	 * @param buffer
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] buffer,int inSampleSize ) {
		BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inSampleSize=inSampleSize;
		return BitmapFactory.decodeByteArray(buffer, 0, buffer.length,options);
	}
	/**
	 * ͼƬЧ�����
	 * 
	 * @param bmp
	 *            �����˳ߴ��С��Bitmap
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap overlay(Bitmap bmp, int overRef, Resources res) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		// �Ա߿�ͼƬ��������
		Bitmap overlay = BitmapFactory.decodeResource(res, overRef);
		int w = overlay.getWidth();
		int h = overlay.getHeight();
		float scaleX = width * 1F / w;
		float scaleY = height * 1F / h;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);

		Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix,
				true);

		int pixColor = 0;
		int layColor = 0;

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int pixA = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;
		int newA = 0;

		int layR = 0;
		int layG = 0;
		int layB = 0;
		int layA = 0;

		final float alpha = 0.5F;

		int[] srcPixels = new int[width * height];
		int[] layPixels = new int[width * height];
		bmp.getPixels(srcPixels, 0, width, 0, 0, width, height);
		overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);

		int pos = 0;
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pos = i * width + k;
				pixColor = srcPixels[pos];
				layColor = layPixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				pixA = Color.alpha(pixColor);

				layR = Color.red(layColor);
				layG = Color.green(layColor);
				layB = Color.blue(layColor);
				layA = Color.alpha(layColor);

				newR = (int) (pixR * alpha + layR * (1 - alpha));
				newG = (int) (pixG * alpha + layG * (1 - alpha));
				newB = (int) (pixB * alpha + layB * (1 - alpha));
				layA = (int) (pixA * alpha + layA * (1 - alpha));

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));
				newA = Math.min(255, Math.max(0, layA));

				srcPixels[pos] = Color.argb(newA, newR, newG, newB);
			}
		}

		bitmap.setPixels(srcPixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * ���ͿѻͼƬ��ԴͼƬ
	 * 
	 * @param src
	 *            ԴͼƬ
	 * @param watermark
	 *            ͿѻͼƬ
	 * @return
	 */
	public static Bitmap doodle(Bitmap src, Bitmap watermark) {
		// ���ⴴ��һ��ͼƬ
		Bitmap newb = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ���λͼ
		Canvas canvas = new Canvas(newb);
		canvas.drawBitmap(src, 0, 0, null);// �� 0��0��꿪ʼ����ԭͼƬsrc
		canvas.drawBitmap(watermark,
				(src.getWidth() - watermark.getWidth()) / 2,
				(src.getHeight() - watermark.getHeight()) / 2, null); // ͿѻͼƬ����ԭͼƬ�м�λ��
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		watermark.recycle();
		watermark = null;

		return newb;
	}

	/**
	 * ����Ч��(���֮ǰ�����Ż���һ��)
	 * 
	 * @param bmp
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap oldRemeber(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		int pixColor = 0;
		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pixColor = pixels[width * i + k];
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
				newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
				newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
				int newColor = Color.argb(255, newR > 255 ? 255 : newR,
						newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
				pixels[width * i + k] = newColor;
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		long end = System.currentTimeMillis();
		return bitmap;
	}

	/**
	 * ģ��Ч��
	 * 
	 * @param bmp
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap blurImage(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int newColor = 0;

		int[][] colors = new int[9][3];
		for (int i = 1, length = width - 1; i < length; i++) {
			for (int k = 1, len = height - 1; k < len; k++) {
				for (int m = 0; m < 9; m++) {
					int s = 0;
					int p = 0;
					switch (m) {
					case 0:
						s = i - 1;
						p = k - 1;
						break;
					case 1:
						s = i;
						p = k - 1;
						break;
					case 2:
						s = i + 1;
						p = k - 1;
						break;
					case 3:
						s = i + 1;
						p = k;
						break;
					case 4:
						s = i + 1;
						p = k + 1;
						break;
					case 5:
						s = i;
						p = k + 1;
						break;
					case 6:
						s = i - 1;
						p = k + 1;
						break;
					case 7:
						s = i - 1;
						p = k;
						break;
					case 8:
						s = i;
						p = k;
					}
					pixColor = bmp.getPixel(s, p);
					colors[m][0] = Color.red(pixColor);
					colors[m][1] = Color.green(pixColor);
					colors[m][2] = Color.blue(pixColor);
				}

				for (int m = 0; m < 9; m++) {
					newR += colors[m][0];
					newG += colors[m][1];
					newB += colors[m][2];
				}

				newR = (int) (newR / 9F);
				newG = (int) (newG / 9F);
				newB = (int) (newB / 9F);

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				newColor = Color.argb(255, newR, newG, newB);
				bitmap.setPixel(i, k, newColor);

				newR = 0;
				newG = 0;
				newB = 0;
			}
		}

		return bitmap;
	}

	/**
	 * �ữЧ��(��˹ģ��)(�Ż�����������)
	 * 
	 * @param bmp
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap blurImageAmeliorate(Bitmap bmp) {
		// ��˹����
		int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int delta = 16; // ֵԽСͼƬ��Խ����Խ����Խ��

		int idx = 0;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				idx = 0;
				for (int m = -1; m <= 1; m++) {
					for (int n = -1; n <= 1; n++) {
						pixColor = pixels[(i + m) * width + k + n];
						pixR = Color.red(pixColor);
						pixG = Color.green(pixColor);
						pixB = Color.blue(pixColor);

						newR = newR + (int) (pixR * gauss[idx]);
						newG = newG + (int) (pixG * gauss[idx]);
						newB = newB + (int) (pixB * gauss[idx]);
						idx++;
					}
				}

				newR /= delta;
				newG /= delta;
				newB /= delta;

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[i * width + k] = Color.argb(255, newR, newG, newB);

				newR = 0;
				newG = 0;
				newB = 0;
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		long end = System.currentTimeMillis();
		return bitmap;
	}

	/**
	 * ͼƬ�񻯣�������˹�任��
	 * 
	 * @param bmp
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap sharpenImageAmeliorate(Bitmap bmp) {
		// ������˹����
		int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int idx = 0;
		float alpha = 0.3F;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				idx = 0;
				for (int m = -1; m <= 1; m++) {
					for (int n = -1; n <= 1; n++) {
						pixColor = pixels[(i + n) * width + k + m];
						pixR = Color.red(pixColor);
						pixG = Color.green(pixColor);
						pixB = Color.blue(pixColor);

						newR = newR + (int) (pixR * laplacian[idx] * alpha);
						newG = newG + (int) (pixG * laplacian[idx] * alpha);
						newB = newB + (int) (pixB * laplacian[idx] * alpha);
						idx++;
					}
				}

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[i * width + k] = Color.argb(255, newR, newG, newB);
				newR = 0;
				newG = 0;
				newB = 0;
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		long end = System.currentTimeMillis();
		return bitmap;
	}

	/**
	 * ����Ч��
	 * 
	 * @param bmp
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap emboss(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int pos = 0;
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				pos = i * width + k;
				pixColor = pixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);

				pixColor = pixels[pos + 1];
				newR = Color.red(pixColor) - pixR + 127;
				newG = Color.green(pixColor) - pixG + 127;
				newB = Color.blue(pixColor) - pixB + 127;

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[pos] = Color.argb(255, newR, newG, newB);
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * ��ƬЧ��
	 * 
	 * @param bmp
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap film(Bitmap bmp) {
		// RGBA�����ֵ
		final int MAX_VALUE = 255;
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int pos = 0;
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				pos = i * width + k;
				pixColor = pixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);

				newR = MAX_VALUE - pixR;
				newG = MAX_VALUE - pixG;
				newB = MAX_VALUE - pixB;

				newR = Math.min(MAX_VALUE, Math.max(0, newR));
				newG = Math.min(MAX_VALUE, Math.max(0, newG));
				newB = Math.min(MAX_VALUE, Math.max(0, newB));

				pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB);
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * ����Ч��
	 * 
	 * @param bmp
	 * @return
	 */
	public static Bitmap sunshine(Bitmap bmp) {
		final int width = bmp.getWidth();
		final int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int centerX = width / 2;
		int centerY = height / 2;
		int radius = Math.min(centerX, centerY);

		final float strength = 150F; // ����ǿ�� 100~150
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int pos = 0;
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				pos = i * width + k;
				pixColor = pixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);

				newR = pixR;
				newG = pixG;
				newB = pixB;

				// ���㵱ǰ�㵽�������ĵľ��룬ƽ�����ϵ��������֮��ľ���
				int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(
						centerX - k, 2));
				if (distance < radius * radius) {
					// ���վ����С�������ӵĹ���ֵ
					int result = (int) (strength * (1.0 - Math.sqrt(distance)
							/ radius));
					newR = pixR + result;
					newG = pixG + result;
					newB = pixB + result;
				}

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[pos] = Color.argb(255, newR, newG, newB);
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * ɫ�ʶȵ���
	 * 
	 * @param drawable
	 * @param sature
	 *            Ϊ0��ʾͼƬ��ʾΪû��ɫ�ʣ�����Խ��ͼƬ��ʾ��ɫ�ʾ�ԽŨ
	 * @return
	 */
	public static Drawable chroma(Drawable drawable, int sature) {
		drawable.mutate();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(sature);
		ColorMatrixColorFilter cmf = new ColorMatrixColorFilter(cm);
		drawable.setColorFilter(cmf);

		return drawable;
	}

	/**
	 * ��ͼƬ���浽�豸��
	 * 
	 * @param photoPath
	 *            --ԭͼ·��
	 * @param aFile
	 *            --������ͼ
	 * @param newWidth
	 *            --��ͼ���
	 * @param newHeight
	 *            --��ͼ�߶�
	 */

	public static boolean bitmapToFile(String photoPath, File aFile,
			int newWidth, int newHeight) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		// ��ȡ���ͼƬ�Ŀ�͸�
		Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
		options.inJustDecodeBounds = false;

		// �������ű�
		options.inSampleSize = reckonThumbnail(options.outWidth,
				options.outHeight, newWidth, newHeight);
		bitmap = BitmapFactory.decodeFile(photoPath, options);

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] photoBytes = baos.toByteArray();

			if (aFile.exists()) {
				aFile.delete();
			}

			aFile.createNewFile();

			FileOutputStream fos = new FileOutputStream(aFile);
			fos.write(photoBytes);
			fos.flush();
			fos.close();

			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			if (aFile.exists()) {
				aFile.delete();
			}
			return false;
		}
	}

	/**
	 * ����ͼƬΪPNG
	 * 
	 * @param bitmap
	 * @param name
	 */
	public static void savePNG_After(Bitmap bitmap, String name) {
		File file = new File(name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ͼƬΪJPEG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void saveJPGE_After(Bitmap bitmap, String path,String filename) {
		
		try {
			File dir = new File(path);    
            if (!dir.exists()) {    
                dir.mkdirs();    
            }   
            File file = new File(path+filename);
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������ű�
	 * 
	 * @param oldWidth
	 * @param oldHeight
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static int reckonThumbnail(int oldWidth, int oldHeight,
			int newWidth, int newHeight) {

		if ((oldHeight > newHeight && oldWidth > newWidth)
				|| (oldHeight <= newHeight && oldWidth > newWidth)) {
			int be = (int) (oldWidth / (float) newWidth);
			if (be <= 1) {
				be = 1;
			}

			return be;
		} else if (oldHeight > newHeight && oldWidth <= newWidth) {

			int be = (int) (oldHeight / (float) newHeight);
			if (be <= 1) {
				be = 1;
			}

			return be;
		}
		return 1;
	}

	/**
	 * ˮӡ
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createBitmapForWatermark(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}

		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ���λͼ
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �� 0��0��꿪ʼ����src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// ��src�����½ǻ���ˮӡ
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		// store
		cv.restore();// �洢
		return newb;
	}

	/**
	 * ��ͼView������Bitmap
	 * 
	 * @param v
	 * @return
	 */
	public static Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

	/**
	 * ͼƬ�ϳ�
	 * 
	 * @return
	 */
	public static Bitmap potoMix(int direction, Bitmap... bitmaps) {
		if (bitmaps.length <= 0) {
			return null;
		}
		if (bitmaps.length == 1) {
			return bitmaps[0];
		}
		Bitmap newBitmap = bitmaps[0];
		// newBitmap = createBitmapForFotoMix(bitmaps[0],bitmaps[1],direction);
		for (int i = 1; i < bitmaps.length; i++) {
			newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
		}
		return newBitmap;
	}

	private static Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second,
			int direction) {
		if (first == null) {
			return null;
		}
		if (second == null) {
			return first;
		}
		int fw = first.getWidth();
		int fh = first.getHeight();
		int sw = second.getWidth();
		int sh = second.getHeight();
		Bitmap newBitmap = null;
		if (direction == LEFT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, sw, 0, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == RIGHT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, fw, 0, null);
		} else if (direction == TOP) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, sh, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == BOTTOM) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, 0, fh, null);
		}
		return newBitmap;
	}

	public static Bitmap decodeBitmap(String path, int displayWidth,
			int displayHeight) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, op); // ��ȡ�ߴ���Ϣ
		// ��ȡ�����С
		int wRatio = (int) Math.ceil(op.outWidth / (float) displayWidth);
		int hRatio = (int) Math.ceil(op.outHeight / (float) displayHeight);
		// ����ָ����С������С��Ӧ�ı���
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeFile(path, op);
		return Bitmap
				.createScaledBitmap(bmp, displayWidth, displayHeight, true);
	}

	/**
	 * ���ø��Ӽ�������������
	 * 
	 * @param path
	 * @param maxImageSize
	 * @return
	 */
	public static Bitmap decodeBitmap(String path, int maxImageSize) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, op); // ��ȡ�ߴ���Ϣ
		int scale = 1;
		if (op.outWidth > maxImageSize || op.outHeight > maxImageSize) {
			scale = (int) Math.pow(
					2,
					(int) Math.round(Math.log(maxImageSize
							/ (double) Math.max(op.outWidth, op.outHeight))
							/ Math.log(0.5)));
		}
		op.inJustDecodeBounds = false;
		op.inSampleSize = scale;
		bmp = BitmapFactory.decodeFile(path, op);
		return bmp;
	}

	public static Cursor queryThumbnails(Activity context) {
		String[] columns = new String[] { MediaStore.Images.Thumbnails.DATA,
				MediaStore.Images.Thumbnails._ID,
				MediaStore.Images.Thumbnails.IMAGE_ID };
		return context.managedQuery(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, columns,
				null, null, MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);
	}

	public static Cursor queryThumbnails(Activity context, String selection,
			String[] selectionArgs) {
		String[] columns = new String[] { MediaStore.Images.Thumbnails.DATA,
				MediaStore.Images.Thumbnails._ID,
				MediaStore.Images.Thumbnails.IMAGE_ID };
		return context.managedQuery(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, columns,
				selection, selectionArgs,
				MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);
	}

	public static Bitmap queryThumbnailById(Activity context, int thumbId) {
		String selection = MediaStore.Images.Thumbnails._ID + " = ?";
		String[] selectionArgs = new String[] { thumbId + "" };
		Cursor cursor = ImageUtil.queryThumbnails(context, selection,
				selectionArgs);

		if (cursor.moveToFirst()) {
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
			cursor.close();
			return ImageUtil.decodeBitmap(path, 100, 100);
		} else {
			cursor.close();
			return null;
		}
	}

	public static Bitmap[] queryThumbnailsByIds(Activity context,
			Integer[] thumbIds) {
		Bitmap[] bitmaps = new Bitmap[thumbIds.length];
		for (int i = 0; i < bitmaps.length; i++) {
			bitmaps[i] = ImageUtil.queryThumbnailById(context, thumbIds[i]);
		}

		return bitmaps;
	}

	/**
	 * ��ȡȫ��
	 * 
	 * @param context
	 * @return
	 */
	public static List<Bitmap> queryThumbnailList(Activity context) {
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();
		Cursor cursor = ImageUtil.queryThumbnails(context);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
			Bitmap b = ImageUtil.decodeBitmap(path, 100, 100);
			bitmaps.add(b);
		}
		cursor.close();
		return bitmaps;
	}

	public static List<Bitmap> queryThumbnailListByIds(Activity context,
			int[] thumbIds) {
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();
		for (int i = 0; i < thumbIds.length; i++) {
			Bitmap b = ImageUtil.queryThumbnailById(context, thumbIds[i]);
			bitmaps.add(b);
		}

		return bitmaps;
	}

	public static Cursor queryImages(Activity context) {
		String[] columns = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME };
		return context.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
	}

	public static Cursor queryImages(Activity context, String selection,
			String[] selectionArgs) {
		String[] columns = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME };
		return context.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
				selection, selectionArgs,
				MediaStore.Images.Media.DEFAULT_SORT_ORDER);
	}

	public static Bitmap queryImageById(Activity context, int imageId) {
		String selection = MediaStore.Images.Media._ID + "=?";
		String[] selectionArgs = new String[] { imageId + "" };
		Cursor cursor = ImageUtil
				.queryImages(context, selection, selectionArgs);
		if (cursor.moveToFirst()) {
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
			cursor.close();
			// return ImageUtil.decodeBitmap(path, 260, 260);
			return ImageUtil.decodeBitmap(path, 220); // �������������ַ�ʽ�Ĳ��,���ˣ����
		} else {
			cursor.close();
			return null;
		}
	}
	public static Bitmap queryImageById(Context context, String name) {
		ApplicationInfo appInfo = context.getApplicationInfo();

		int resID = context.getResources().getIdentifier(name, "drawable", appInfo.packageName);

		return BitmapFactory.decodeResource(context.getResources(), resID);
	}
	/**
	 * �������ͼ��Id��ȡ��Ӧ�Ĵ�ͼ cursor = BitmapUtils.queryThumbnails(this);
	 * if(cursor.moveToFirst()){ List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	 * thumbIds = new int[cursor.getCount()]; for(int i=0;
	 * i<cursor.getCount();i++){ cursor.moveToPosition(i); String currPath =
	 * cursor
	 * .getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails
	 * .DATA)); thumbIds[i] =
	 * cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore
	 * .Images.Thumbnails._ID)); Bitmap b =
	 * BitmapUtils.decodeBitmap(currPath,100,100); bitmaps.add(b); }
	 * 
	 * @param context
	 * @param thumbId
	 * @return
	 */
	public static Bitmap queryImageByThumbnailId(Activity context,
			Integer thumbId) {

		String selection = MediaStore.Images.Thumbnails._ID + " = ?";
		String[] selectionArgs = new String[] { thumbId + "" };
		Cursor cursor = ImageUtil.queryThumbnails(context, selection,
				selectionArgs);

		if (cursor.moveToFirst()) {
			int imageId = cursor
					.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
			cursor.close();
			return ImageUtil.queryImageById(context, imageId);
		} else {
			cursor.close();
			return null;
		}
	}

	/**
	 * �÷��������ǽ���ͼ�����ɫ�����ȣ��ԱȶȵȽ������� ��Ҫ�õ�ColorMatrix�ࡣColorMatrix����һ���������еľ���
	 * ÿһ��Ӱ����[R,G,B,A]�е�һ�� ------------------------- a1 b1 c1 d1 e1 a2 b2 c2 d2
	 * e2 a3 b3 c3 d3 e3 a4 b4 c4 d4 e4 ------------------------- Rnew =>
	 * a1*R+b1*G+c1*B+d1*A+e1 Gnew => a2*R+b2*G+c2*B+d2*A+e2 Bnew =>
	 * a3*R+b3*G+c3*B+d3*A+e3 Gnew => a4*R+b4*G+c4*B+d4*A+e4 ����R��G��B��ֵ��128��A��ֵ��0
	 * 
	 * �����ɫ���޸ģ�ͨ��Paint.setColorFilterӦ�õ�Paint�����С�
	 * ��Ҫ����ColorMatrix,��Ҫ�����װ��ColorMatrixColorFilter�����ٴ���Paint����
	 * 
	 * ͬ��ģ�ColorMatrix�ṩ��������Ӧ�ķ�����setSaturation()�Ϳ�������һ�����Ͷ�
	 */
	public static Bitmap ajustImage(Bitmap bitemap) {
		ColorMatrix cMatrix = new ColorMatrix();
		// int brightIndex = -25;
		// int doubleColor = 2;
		// cMatrix.set(new float[]{
		// doubleColor,0,0,0,brightIndex, //���ｫ1��Ϊ2��������Red��ֵΪԭ��������
		// 0,doubleColor,0,0,brightIndex,//�ı����һ�е�ֵ�����ǿ��Բ��ı�RGBͬ����ɫ�Ļ��ϣ��ı�����
		// 0,0,doubleColor,0,brightIndex,
		// 0,0,0,doubleColor,0
		// });
		// cMatrix.setSaturation(2.0f);//���ñ��Ͷ�
		cMatrix.setScale(2.0f, 2.0f, 2.0f, 2.0f);// ������ɫͬ��ɫ������
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
		Bitmap bmp = null;
		// �������Bitmap�д����ĺ���Ϳ��Դ���һ���յ�Bitmap
		// ���ص���һ�����Ըı��Bitmap�����������Ǻ���Ϳ��Զ�����б任����ɫ����Ȳ�����
		bmp = Bitmap.createBitmap(bitemap.getWidth(), bitemap.getHeight(),
				bitemap.getConfig());
		// ����Canvas����
		Canvas canvas = new Canvas(bmp);

		// ��Canvas�ϻ���һ���Ѿ����ڵ�Bitmap������dstBitmap�ͺ�srcBitmapһ��һ����

		canvas.drawBitmap(bitemap, 0, 0, paint);
		return bmp;
	}

	/**
	 * ��View���Ƶ�Bitmap�� http://yunfeng.sinaapp.com/?p=228
	 * 
	 * @param view
	 *            ��Ҫ���Ƶ�View
	 * @param width
	 *            ��View�Ŀ��
	 * @param height
	 *            ��View�ĸ߶�
	 * @return ����Bitmap����
	 */
	public static Bitmap getBitmapFromView(View view, int width, int height) {
		int widthSpec = View.MeasureSpec.makeMeasureSpec(width,
				View.MeasureSpec.EXACTLY);
		int heightSpec = View.MeasureSpec.makeMeasureSpec(height,
				View.MeasureSpec.EXACTLY);
		view.measure(widthSpec, heightSpec);
		view.layout(0, 0, width, height);
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);

		return bitmap;
	}

	/**
	 * 
	 * @param context
	 * @param resId
	 * @return Bitmap
	 */
	public Bitmap readBitmap(Context context, int resId) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Config.RGB_565;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opts);
	}

	/**
	 * ͨ���ļ�����ͼƬ
	 * **/
	public static Bitmap GetBitmapByFileName(Context context, String fileName) {

		try {
			String packetName = context.getPackageName();
			// ��fileName ת����id
			int resId = context.getResources().getIdentifier(fileName,
					"drawable", packetName);
			Bitmap bitmap = null;
			// InputStream im = context.getResources().openRawResource(resId);
			// bitmap = BitmapFactory.decodeStream(im);
			//
			// im.close();
			// im = null;
			bitmap = BitmapFactory
					.decodeResource(context.getResources(), resId);
			return bitmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * ��һ��viewת��Ϊbitmap
	 * **/
	public static Bitmap getBitmapFromView(View view) {
		Bitmap bitmap = null;
		try {
			view.destroyDrawingCache();
			view.measure(View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
					.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
			view.setDrawingCacheEnabled(true);
			bitmap = view.getDrawingCache(true);
		} catch (Exception e) {
		}
		return bitmap;
	}

}
