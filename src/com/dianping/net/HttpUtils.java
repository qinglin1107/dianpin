package com.dianping.net;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.dianping.util.AppConstance;

public class HttpUtils {
	public static void HttpGet(List<NameValuePair> param, String method,
			HttpInterface listener) {
		HttpClient client = new DefaultHttpClient();
		String query = URLEncodedUtils.format(param, HTTP.UTF_8);
		HttpGet httpGet = new HttpGet(AppConstance.BASE_URL + method+"?"+query);
		try {
			HttpResponse response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entry = response.getEntity();
				listener.success(EntityUtils.toString(entry, HTTP.UTF_8));
			} else {
				HttpEntity entry = response.getEntity();
				listener.failed(EntityUtils.toString(entry, HTTP.UTF_8));
			}
		} catch (Exception e) {
		}
	}

	public static void HttpGetAsync(final List<NameValuePair> param,
			final String method, final HttpInterface listener) {
		new Thread(){

			@Override
			public void run() {
				HttpGet(param, method, listener);
			}
			
		}.start();
	}
}
