package com.vc.swoop.webservice;

import java.io.InputStream;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.vc.net.RequestParams;
import com.vc.net.SyncHttpClient;
import com.vc.util.LogUtils;

public class AdWS extends BaseWS {

	public AdWS(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public JSONObject search_near_ad(String sessionId, String search,
			 String pageindex) {
		try {
			String url = GetMethodURL("ad/search_near_ad");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("search", search);
			params.put("pageindex", pageindex);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:" + e);
			return null;
		}
	}
	public JSONObject near_ad(String sessionId, String orderby,
			String category, String lng, String lat, String pageindex) {
		try {
			String url = GetMethodURL("ad/near_ad?");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("orderby", orderby);
			params.put("category", category);
			params.put("lng", lng);
			params.put("lat", lat);
			params.put("pageindex", pageindex);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:" + e);
			return null;
		}
	}

	public JSONObject my_ads(String sessionId, String state, String pageindex) {
		try {
			String url = GetMethodURL("ad/my_ads");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("state", state);
			params.put("pageindex", pageindex);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("logout_clientid:" + e);
			return null;
		}
	}

	public JSONObject get_my_watchlist(String sessionId, String pageindex) {
		try {
			String url = GetMethodURL("watchlist/get_my_watchlist");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("pageindex", pageindex);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}

	public JSONObject del_watchlist(String sessionId, String ad_id) {
		try {
			String url = GetMethodURL("watchlist/del_watchlist");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("ad_id", (ad_id));
			Log.d("Swoop",params.toString());
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}
	public JSONObject ad_withdrawn(String sessionId, String ad_id) {
		try {
			String url = GetMethodURL("ad/ad_withdrawn");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("ad_id", ad_id);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("ad_withdrawn:" + e);
			return null;
		}
	}
	public JSONObject relist_ad(String sessionId, String json_ad) {
		try {
			String url = GetMethodURL("ad/relist_ad");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("json_ad", json_ad);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("ad_withdrawn:" + e);
			return null;
		}
	}
	public JSONObject add_to_watchlist(String sessionId, String ad_id) {
		try {
			String url = GetMethodURL("watchlist/add_to_watchlist");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("ad_id", ad_id);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}

	public JSONObject selling_ad(String sessionId, String ad_id,
			String category, String lng, String lat, String pricetype,
			String price, String title, String describe, String follow_me,
			String phone, InputStream pic1, InputStream pic2, InputStream pic3,
			InputStream pic4, InputStream pic5) {
		try {
			String url = GetMethodURL("ad/selling_ad");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			if (!TextUtils.isEmpty(ad_id)) {
				params.put("ad_id", ad_id);

			}
			params.put("category", category);
			params.put("lng", lng);
			params.put("lat", lat);
			params.put("pricetype", pricetype);
			params.put("price", price);
			params.put("title", title);
			params.put("describe", describe);
			params.put("follow_me", follow_me);
			params.put("phone", phone);
			if (pic1 != null) {
				params.put("pic1", pic1);
			}
			if (pic2 != null) {
				params.put("pic2", pic2);
			}
			if (pic3 != null) {
				params.put("pic3", pic3);
			}
			if (pic4 != null) {
				params.put("pic4", pic4);
			}
			if (pic5 != null) {
				params.put("pic5", pic5);
			}

			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}
	
	public JSONObject addAdComments(String sessionId, String ad_id,String content) {
		try {
			String url = GetMethodURL("ad_comment/addAdComments");
			RequestParams params = getParams();
			params.put("session_id", sessionId);
			params.put("ad_id", ad_id);
			params.put("content", content);
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}
	public JSONObject getAdComments(String pageindex, String ad_id) {
		try {
			String url = GetMethodURL("ad_comment/getAdComments");
			RequestParams params = getParams();
			params.put("pageindex", pageindex);
			params.put("ad_id", ad_id); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}
	public JSONObject uploadPushStatus(String sessionId) {
		try {
			String url = GetMethodURL("ad_comment/uploadPushStatus");
			RequestParams params = getParams();
			params.put("sessionId", sessionId); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}
	public JSONObject getAd(String sessionId,  String ad_id) {
		try {
			String url = GetMethodURL("ad/getAd");
			RequestParams params = getParams(); 
			params.put("id", ad_id); 
			params.put("session_id", sessionId); 
			SyncHttpClient synchttpclient = getAsyncHttpClient();
			JSONObject result = synchttpclient.postToJson(url, params);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("get_my_watchlist:" + e);
			return null;
		}
	}
}
