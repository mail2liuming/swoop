package com.vc.swoop.common;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;


import android.util.Log;
 
public class JUtil {
	
	public static String toJson(Object obj) {
		
		String jsonString = "";
		try {
			jsonString =JSON.toJSONString(obj, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	public static <T> List<T> parseArray(String json, Class<T> classOfT) {

		return JSON.parseArray(json, classOfT);
	}
	public static <T> List<T> parseArray(JSONObject json, Class<T> classOfT) {

		 
		return parseArray(json.toString(), classOfT);
	}
	public static <T> T parseObject(String json, Class<T> classOfT) {

		return JSON.parseObject(json, classOfT);
	}
	public static <T> T parseObject(JSONObject json, Class<T> classOfT) {

		return parseObject(json.toString(), classOfT);
	}
	public static String getError(JSONObject json )
	{
		try {
			return json.getString("error_msg");
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	public static String getData(JSONObject json )
	{
		return getData(json,"msg");
	}
	public static String getData(JSONObject json ,String keys)
	{
		try {
			return json.getString(keys);
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	public static boolean checkStaus(JSONObject json )
	{
		try {
			return json.getString("ret").equals("1");
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	 
}
