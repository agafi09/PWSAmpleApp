package com.applaudo.phunwaresampleapp.dataaccesslayer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networking {

	private Context mContext;

	public Networking(Context context) {
		mContext = context;
	}

	/**
	 * Method that checks for Internet availability
	 */
	public boolean isOnline() {
		boolean isInternetAvailable = false;

		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null) {
			int netType = netInfo.getType();
			if (netType == ConnectivityManager.TYPE_WIFI
					|| netType == ConnectivityManager.TYPE_MOBILE
					|| netType == ConnectivityManager.TYPE_WIMAX) {
				isInternetAvailable = netInfo.isConnected();
			}
		}
		return isInternetAvailable;

	}
}
