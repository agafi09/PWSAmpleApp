package com.applaudo.phunwaresampleapp.dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networking {

	private Context ApplicationContext;

	private DefaultHttpClient httpClient;
	private HttpContext localContext;
	private HttpResponse response;
	private HttpPost httpPost = null;
	private HttpGet httpGet = null;
	private InputStream ret = null;

	public Networking(Context ApplicationContext) {
		this.ApplicationContext = ApplicationContext;
		httpClient = new DefaultHttpClient();
		localContext = new BasicHttpContext();
	}

	// To consume a JSon service without parameters 
	public InputStream getWebGetJSONData(String url) {

		if (isOnline()) {
			DefaultHttpClient httpClienty = new DefaultHttpClient();
			URI uri;

			try {
				uri = new URI(url);
				httpGet = new HttpGet(uri);
				response = httpClienty.execute(httpGet);
				ret = response.getEntity().getContent();

			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}


	public InputStream getWebPostJSONData(String url,
			List<NameValuePair> nameValuePairs) {
		if (isOnline()) {
			httpPost = new HttpPost(url);

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
			try {

				response = httpClient.execute(httpPost, localContext);
				if (response != null) {
					ret = response.getEntity().getContent();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	// To verify Internet connection 
	public boolean isOnline() {
		boolean returnValue = false;

		ConnectivityManager cm = (ConnectivityManager) ApplicationContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null) {
			int netType = netInfo.getType();
			if (netType == ConnectivityManager.TYPE_WIFI
					|| netType == ConnectivityManager.TYPE_MOBILE
					|| netType == ConnectivityManager.TYPE_WIMAX) {
				returnValue = netInfo.isConnected();
			}
		}
		return returnValue;

	}
}
