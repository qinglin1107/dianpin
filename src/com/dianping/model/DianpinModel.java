package com.dianping.model;

import org.json.simple.JSONObject;

import com.dianping.util.JSonUtils;

public class DianpinModel {

	public String app_id;
	public String user_id;
	public String reasion;
	public int support_count;
	public int oppose_count;
	public String c_time;
	public double gps_x;
	public double gps_y;
	
	public DianpinModel(){}
	
	public static DianpinModel builder(JSONObject json){
		DianpinModel model = new DianpinModel();
		model.app_id = JSonUtils.getString(json, "app_id");
		model.user_id = JSonUtils.getString(json, "user_id");
		model.reasion = JSonUtils.getString(json, "reasion");
		model.support_count = JSonUtils.getInt(json, "support_count");
		model.oppose_count = JSonUtils.getInt(json, "oppose_count");
		model.c_time = JSonUtils.getString(json, "c_time");
		model.gps_x = Double.parseDouble(JSonUtils.getString(json, "gps_x"));
		model.gps_y = Double.parseDouble(JSonUtils.getString(json, "gps_y"));
		return model;
	}
}
