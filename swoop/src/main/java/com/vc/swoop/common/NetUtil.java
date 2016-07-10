package com.vc.swoop.common;
 
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;

public class NetUtil {

	public static boolean isOpenNetwork(Context mContext) {
		ConnectivityManager connManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}

	 

}
