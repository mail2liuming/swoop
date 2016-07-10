package com.vc.swoop.common;

import java.math.BigDecimal;

public class LBSUtil {

	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth
	
	/**
	 * 计算两个经纬度坐标点之间的距�?
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static double GetShortDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧�?
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度�?
		dew = ew1 - ew2;
		// 若跨东经和西�?80 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}
	
	public static String distanceStr(double lon1, double lat1, double lon2,
			double lat2) {
		double distance = GetShortDistance(lon1, lat1, lon2, lat2);
		if (distance <=100) {
			
			return  "100 m";
		}  
		else if(distance>100&&distance<1000)
		{
			BigDecimal kDis = new BigDecimal(distance / 1000).setScale(1, BigDecimal.ROUND_HALF_UP);
			return kDis + " km";
		}
		else {
			BigDecimal kDis = new BigDecimal(distance / 1000).setScale(1, BigDecimal.ROUND_HALF_UP);
			return kDis.intValue()  + " km";
		}
		
	}	
}
