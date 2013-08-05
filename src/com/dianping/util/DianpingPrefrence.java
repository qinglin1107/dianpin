package com.dianping.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DianpingPrefrence {

	private static String prefrence_name = "dianping_prefrence";
	public static void setCity(Context context,String city){
		SharedPreferences spf = context.getSharedPreferences(prefrence_name, 0);
		spf.edit().putString("city", city).commit();
	}
	public static String getCity(Context context){
		SharedPreferences spf = context.getSharedPreferences(prefrence_name, 0);
		return spf.getString("city", null);
	}
}
