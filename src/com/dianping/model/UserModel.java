package com.dianping.model;

import org.json.simple.JSONObject;

import com.dianping.util.JSonUtils;

public class UserModel {

	public String uid;
	public String avatar;
	public String username;
	
	public UserModel(){};
	
	public static UserModel builder(JSONObject object){
		UserModel model = new UserModel();
		model.uid = JSonUtils.getString(object, "uid");
		model.avatar = JSonUtils.getString(object, "avtar");
		model.username = JSonUtils.getString(object, "username");
		return model;
	}
}
