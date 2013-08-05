package com.dianping;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.dianping.adapter.CommentAdapter;
import com.dianping.adapter.ViewPagerAdapter;
import com.dianping.model.PackInfo;

public class CommentActivity extends Activity implements View.OnClickListener{

	private TextView installed;
	private TextView unInstalled;
	private ViewPager viewPager;
	private List<View> lists = new ArrayList<View>();
	private List<PackInfo> installedList = new ArrayList<PackInfo>();
	private CommentAdapter installedAdapter;
	private CommentAdapter unInstalledAdapter;
	private ViewPagerAdapter vAdapter;
	private static final int UPDATE_PACKEGE_INFOMATION = 10121;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comment_layout);

		viewPager = (ViewPager) this.findViewById(R.id.vPager);
		installed = (TextView) this.findViewById(R.id.installed);
		unInstalled = (TextView) this.findViewById(R.id.unInstalled);
		installed.setOnClickListener(this);
		unInstalled.setOnClickListener(this);
		
		initPackageMessage();
		
		ListView installLV = new ListView(this);
		installedAdapter = new CommentAdapter(this,
				R.layout.comment_adapter_layout, installedList);
		installLV.setAdapter(installedAdapter);
		ListView unInstallLV = new ListView(this);
		unInstalledAdapter = new CommentAdapter(this,
				R.layout.comment_adapter_layout, installedList);
		unInstallLV.setAdapter(unInstalledAdapter);
		lists.add(installLV);
		lists.add(unInstallLV);
		vAdapter = new ViewPagerAdapter(lists);
		viewPager.setAdapter(vAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				initTextColor(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		initTextColor(0);
		
	}
	
	void initTextColor(int arg0){
		installed.setTextColor(arg0 == 0 ? Color.parseColor("#666666")
				: Color.GRAY);
		unInstalled.setTextColor(arg0 == 1 ? Color
				.parseColor("#666666") : Color.GRAY);
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_PACKEGE_INFOMATION:
				installedAdapter.notifyDataSetChanged();
				unInstalledAdapter.notifyDataSetChanged();
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			this.overridePendingTransition(R.anim.activity_anim_in, R.anim.push_left_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initPackageMessage() {
		List<PackageInfo> infos = getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo info : infos) {
			if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
				PackInfo pInfo = new PackInfo();
				pInfo.name = info.applicationInfo.loadLabel(getPackageManager()).toString();
				pInfo.versionCode = info.versionCode;
				pInfo.versionName = info.versionName;
				pInfo.icon = info.applicationInfo.loadIcon(getPackageManager());
				installedList.add(pInfo);
			}
			
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.installed:
			viewPager.setCurrentItem(0);
			break;
		case R.id.unInstalled:
			viewPager.setCurrentItem(1);
			break;
		}
	}
}
