package com.vc.swoop.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {
	private static String[] HexCode = { "0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HexCode[d1] + HexCode[d2];
	}

	private static String byteArrayToHexString(byte[] b) {
		String resultString = "";
		for (int i = 0; i < b.length; i++) {
			resultString = resultString + byteToHexString(b[i]);
		}
		return resultString;
	}

	public static String GetMD5(String s) {
		String ss = "";
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(s.getBytes());
			ss = byteArrayToHexString(md5.digest());
			return ss;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	public static String GetSha1(String datas) {
		String reString = "";

		try {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-1");
				digest.update(datas.getBytes());
				byte messageDigest[] = digest.digest();
				return byteArrayToHexString(messageDigest);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return reString;
	}
}
