package com.vc.net;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Message;
 



/** 
 * ����ͬ������http�������
 * 
 * 
 */
public class SyncHttpClient  extends AsyncHttpClient {

	private int responseCode;
	private HttpResponse response;
	protected String result;
	protected AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
	{

		@Override
		protected void sendResponseMessage(org.apache.http.HttpResponse r)
		{
			responseCode = r.getStatusLine().getStatusCode();
			response=r;
			super.sendResponseMessage(r);
		}

		@Override
		protected void sendMessage(Message msg)
		{
			handleMessage(msg);
		}

		@Override
		public void onSuccess(String content)
		{
			result = content;
		}

		@Override
		public void onFailure(Throwable error, String content)
		{
			result = onRequestFailed(error, content);
		}
	};

	public int getResponseCode()
	{
		return responseCode;
	}
	public HttpResponse getHttpResponse()
	{
		return response;
	}
	@Override
	protected void sendRequest(DefaultHttpClient client,
			HttpContext httpContext, HttpUriRequest uriRequest,
			String contentType, AsyncHttpResponseHandler responseHandler,
			Context context)
	{
		if (contentType != null)
		{
			uriRequest.addHeader("Content-Type", contentType);
		}
		new AsyncHttpRequest(client, httpContext, uriRequest, responseHandler)
				.run();
	}

	public String onRequestFailed(Throwable error, String content)
	{
		return "";
	}
	
	/**
	 * ͬ��delete
	 * */
	public void delete(String url, RequestParams queryParams,
			AsyncHttpResponseHandler responseHandler)
	{
		delete(url, responseHandler);
	}

	/**
	 * ͬ��get����
	 * */
	public String get(String url, RequestParams params)
	{
		this.get(url, params, responseHandler);
		return result;
	}

	/**
	 * ͬ��get����
	 * */
	public String get(String url)
	{
		this.get(url, null, responseHandler);
		return result;
	}
	/**
	 * ͬ��get
	 * */
	public JSONObject getToJson(String url, RequestParams params)
	{
		this.get(url, params, responseHandler);
		try {
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * ͬ��put
	 * */
	public String put(String url, RequestParams params)
	{
		this.put(url, params, responseHandler);
		return result;
	}

	/**
	 * ͬ��put
	 * */
	public String put(String url)
	{
		this.put(url, null, responseHandler);
		return result;
	}

	/**
	 * ͬ��post
	 * */
	public String post(String url, RequestParams params)
	{
		this.post(url, params, responseHandler);
		return result;
	}
	

	/**
	 * ͬ��post����  ���ص���jsonobjct
	 * */
	public JSONObject postToJson(String url, RequestParams params)
	{
		this.post(url, params, responseHandler);
		try {
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * ͬ��post����  
	 * */
	public String post(String url)
	{
		this.post(url, null, responseHandler);
		return result;
	}
	


	/**
	 * ͬ��post����  ���ص���jsonobject
	 * */
	public JSONObject postToJson(String url)
	{
		this.post(url, null, responseHandler);
		try {
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * ͬ��delete����   
	 * */
	public String delete(String url, RequestParams params)
	{
		this.delete(url, params, responseHandler);
		return result;
	}


	/**
	 * ͬ��delete����   
	 * */
	public String delete(String url)
	{
		this.delete(url, null, responseHandler);
		return result;
	}

}
