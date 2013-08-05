package com.dianping;

import com.baidu.mapapi.BMapManager;
import com.dianping.util.AppConstance;

import android.app.Application;

public class DianpingApplication extends Application {

	private static DianpingApplication instance;
	public BMapManager bManager;
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		bManager = new BMapManager(this);
		bManager.init(AppConstance.APP_KEY, null);
	}

	public static DianpingApplication getInstance(){
		return instance;
	}
}
