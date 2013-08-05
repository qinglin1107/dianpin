package com.dianping.model;

import org.json.simple.JSONObject;

import com.dianping.util.JSonUtils;

public class HotMessage {

	public AppInfoModel appInfo;
	public DianpinModel dianPin;
	public UserModel user;

	public HotMessage() {
	};

	public static HotMessage builder(JSONObject json) {
		HotMessage message = new HotMessage();
		JSONObject appJson = JSonUtils.getJSonObject(json, "app");
		if (appJson != null) {
			message.appInfo = AppInfoModel.builder(appJson);
		}
		JSONObject dianpinJson = JSonUtils.getJSonObject(json, "dianpin");
		if (dianpinJson != null) {
			message.dianPin = DianpinModel.builder(dianpinJson);
		}
		JSONObject userJson = JSonUtils.getJSonObject(json, "user");
		if (userJson != null) {
			message.user = UserModel.builder(userJson);
		}
		return message;
	}
}
