package com.vc.swoop.webservice;

import org.json.JSONObject;

import com.vc.net.RequestParams;
import com.vc.net.SyncHttpClient;
import com.vc.util.LogUtils;

import android.content.Context;

public class UserWS extends BaseWS{

	public UserWS(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public JSONObject app_info( )
	{
		try {
			String url = GetMethodURL("app_info");
			RequestParams params = getParams(); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:"+e);
			return null;
		}
	}
	
	public JSONObject get_code(String phone,String is_reset)
	{
		try {
			String url = GetMethodURL("user/get_code");
			RequestParams params = getParams();
			params.put("is_reset", is_reset); 
			params.put("phone", phone); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:"+e);
			return null;
		}
	}
	public JSONObject register(String device_id,String device_type,String phone,String password,String lat,String lng,String name,String code)
	{
		try {
			String url = GetMethodURL("user/register");
			RequestParams params = getParams();
			params.put("device_id", device_id); 
			params.put("device_type", device_type); 
			params.put("phone", phone); 
			params.put("password", password); 
			params.put("lat", lat); 
			params.put("lng", lng); 
			params.put("name", name); 
			params.put("code", code); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:"+e);
			return null;
		}
	}
	public JSONObject check_code(String phone,String code)
	{
		try {
			String url = GetMethodURL("user/check_code");
			RequestParams params = getParams();
			params.put("code", code); 
			params.put("phone", phone); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:"+e);
			return null;
		}
	}
	public JSONObject logout_clientid(String sessionId)
	{
		try {
			String url = GetMethodURL("user/logout_clientid");
			RequestParams params = getParams();
			params.put("session_id", sessionId); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:"+e);
			return null;
		}
	}
	public JSONObject login(String device_id,String device_type,String phone,String password,String force_login)
	{
		try {
			String url = GetMethodURL("user/login");
			RequestParams params = getParams();
			params.put("device_id", device_id); 
			params.put("device_type", device_type);
			params.put("phone", phone);
			params.put("password", password);
			params.put("force_login", "1");
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:"+e);
			return null;
		}
	}
	public JSONObject modiy_pwd(String session_id,String old_pwd,String new_pwd)
	{
		try {
			String url = GetMethodURL("user/modiy_pwd");
			RequestParams params = getParams();
			params.put("session_id", session_id); 
			params.put("old_pwd", old_pwd);
			params.put("new_pwd", new_pwd); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("modiy_pwd:"+e);
			return null;
		}
	}
	public JSONObject modify_phone(String session_id,String phone,String code)
	{
		try {
			String url = GetMethodURL("user/modify_phone");
			RequestParams params = getParams();
			params.put("session_id", session_id); 
			params.put("phone", phone);
			params.put("code", code); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("modiy_pwd:"+e);
			return null;
		}
	}
	public JSONObject forget_pwd (String phone,String new_pwd,String code)
	{
		try {
			String url = GetMethodURL("user/forget_pwd");
			RequestParams params = getParams();
			params.put("phone", phone); 
			params.put("new_pwd", new_pwd);
			params.put("code", code); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("modiy_pwd:"+e);
			return null;
		}
	}
	public JSONObject addAdFeedbacks (String session_id,String content)
	{
		try {
			String url = GetMethodURL("ad_feedback/addAdFeedbacks");
			RequestParams params = getParams();
			params.put("session_id", session_id); 
			params.put("content", content); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("modiy_pwd:"+e);
			return null;
		}
	}
	public JSONObject addAdReports (String session_id,String ad_id,String itype,String content)
	{
		try {
			String url = GetMethodURL("ad_report/addAdReports");
			RequestParams params = getParams();
			params.put("session_id", session_id); 
			params.put("content", content); 
			params.put("itype", itype); 
			params.put("ad_id", ad_id); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("modiy_pwd:"+e);
			return null;
		}
	}
}
