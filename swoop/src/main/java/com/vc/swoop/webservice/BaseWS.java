package com.vc.swoop.webservice;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.vc.net.PersistentCookieStore;
import com.vc.net.RequestParams;
import com.vc.net.SyncHttpClient;

import android.R.string;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;


public class BaseWS {
    protected Context mContext;


    //	public static String Method_URL = "http://52.62.60.238/airshop/index.php?/";
    public static String Method_URL = "http://swoop.appworks.co.nz/airshop/index.php?/";

    protected String GetMethodURL(String cmd) {

        //serverIp="218.244.159.194";
        //serverPort="9000";
        return Method_URL + cmd;
        //return "http://" + serverIp + ":"+serverPort+"/yr/index.php/write/" + str;
    }

    public BaseWS(Context context) {
        mContext = context;
    }


    public RequestParams getParams() {
        RequestParams params = new RequestParams();
        return params;
    }

    public SyncHttpClient getAsyncHttpClient() {
        SyncHttpClient httpClient = new SyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(mContext);
        // myCookieStore.addCookie(cookie)
        httpClient.setCookieStore(myCookieStore);
        return httpClient;
    }
}
