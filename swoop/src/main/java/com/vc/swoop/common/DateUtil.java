package com.vc.swoop.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	/*
	 * 广告发布时间与现状的间隔计算
	 */
	public static String intervalTime(long currentTime,long realeaseTime) {
		long interval = currentTime - realeaseTime;
		long minute = interval / 60;
		if (minute >= 60) {
			int hour = (int)(minute / 60); 
			if (hour >= 24) {
				int day = hour / 24;
				if (day >= 30 ) {
					int month = day / 30;
					if (month >= 12) {
						int year = month / 12;
						if (year > 1) 
							return year + " years";
						else 
							return year + " year";
					} else {
						if (month > 1) 
							return month + " months";
						else 
							return month + " month";
					}
				} else {
					if (day > 1) 
						return day + " days";
					else 
						return day + " day";
				}
				
			} else {
				if (hour > 1) 
					return hour + " hours";
				else 
					return hour + " hour";
			}
		} else {
			if (minute > 1) 
				return minute + " minutes";
			else 
				return minute + " minute";
		}
		
	}
	public static String intervalTimeByCommtent(long currentTime,long realeaseTime) {
		long interval = currentTime - realeaseTime;
		long minute = interval / 60;
		if (minute >= 60) {
			int hour = (int)(minute / 60); 
			if (hour >= 24) {
				Date d = new Date(realeaseTime*1000);
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-dd-MM", Locale.ENGLISH);
				return sdf1.format(d);
				
			} else {
				if (hour > 1) 
					return hour + " hours ago";
				else 
					return hour + " hour ago";
			}
		} else {
			if (minute > 1) 
				return minute + " minutes ago";
			else 
				return minute + " minute ago";
		}
		
	}
	
	public static String parseDate(long time) {
		Date d = new Date(time*1000);
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM, HH:mm", Locale.ENGLISH);
		return sdf1.format(d);
	}
	
	
	public static String dataFormat(long time) {
		Date d = new Date(time*1000);
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd  HH:mm");
		return sdf1.format(d);
	}
	
}
