package com.dianping.model;

import org.json.simple.JSONObject;

import android.util.Log;

import com.dianping.util.JSonUtils;

public class AppInfoModel {

	public String package_name;
	public String name;
	public String app_id;
	public String download_url;
	public String detail_url;
	public String app_icon;
	
	public AppInfoModel (){};
	
	public static AppInfoModel builder(JSONObject object){
		AppInfoModel model = new AppInfoModel();
		model.package_name = JSonUtils.getString(object, "package_name");
		model.name = JSonUtils.getString(object, "name");
		model.app_id = JSonUtils.getString(object, "app_id");
		model.detail_url = JSonUtils.getString(object, "detail_url");
		model.download_url = JSonUtils.getString(object, "download_url");
		model.app_icon = JSonUtils.getString(object, "app_icon");
		return model;
	}
}
