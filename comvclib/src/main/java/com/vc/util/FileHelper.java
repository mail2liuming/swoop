package com.vc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class FileHelper {
	/**
	 * <p>
	 * ���ļ�ת��base64 �ַ�
	 * </p>
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) {
		Bitmap bitmap = null;

		try {
			File file = new File(path);
			FileInputStream stream = new FileInputStream(file);

			byte[] buffer = new byte[(int) file.length() + 100];
			int length = stream.read(buffer);
			return Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		} finally {

		}
	}
	private static Bitmap compressImage(Bitmap image) {

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
	private static Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��
		
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//���������ֻ�Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = 800f;//�������ø߶�Ϊ800f
		float ww = 480f;//�������ÿ��Ϊ480f
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
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);//ѹ���ñ����С���ٽ�������ѹ��
	}
	/**
	 * <p>
	 * ���ļ�ת��base64 �ַ�
	 * </p>
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64FileScale(String path) {
		
		try {
			return bitmapToBase64(getimage(path));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		} finally {

		}
	}
	/**
	 * bitmapתΪbase64
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static byte[] InputStreamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

	/**
	 * <p>
	 * ���ļ�ת��base64 �ַ�
	 * </p>
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeByteFile(String path) {
		Bitmap bitmap = null;

		try {
			File file = new File(path);
			FileInputStream stream = new FileInputStream(file);

			byte[] buffer = new byte[(int) file.length() + 100];
			int length = stream.read(buffer);
			stream.close();
			return buffer;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		} finally {

		}
	}

	public static String BytetoStreamString(byte[] bytes) {
		try {
			// byte[] buffer = new byte[(int)file.length()+100];
			// int length = stream.read(bytes);
			return Base64
					.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

}
