package com.dianping.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dianping.R;
import com.dianping.adapter.DialogStringAdapter;
import com.dianping.adapter.ViewPagerAdapter;

public class HomeCategoryDialog extends Dialog{

	private TextView soft;
	private TextView game;
	private ViewPager pager;
	private List<View> lists = new ArrayList<View>();
	private List<String> softList = new ArrayList<String>();
	private List<String> gameList = new ArrayList<String>();
	private ListView softLV;
	private ListView gameLV;
	private ViewPagerAdapter adapter;
	private DialogStringAdapter softAdapter;
	private DialogStringAdapter gameAdapter;
	
	
	public HomeCategoryDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initMessage();
	}

	public HomeCategoryDialog(Context context, int theme) {
		super(context, theme);
		initMessage();
	}

	public HomeCategoryDialog(Context context) {
		super(context);
		initMessage();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_dialog_layout);
		soft = (TextView)this.findViewById(R.id.software);
		game = (TextView)this.findViewById(R.id.game);
		pager = (ViewPager)this.findViewById(R.id.dPager);
		
		softLV = new ListView(getContext());
		softLV.setDivider(null);
		softLV.setCacheColorHint(android.R.color.transparent);
		softAdapter = new DialogStringAdapter(getContext(), softList);
		softLV.setAdapter(softAdapter);
		gameLV = new ListView(getContext());
		gameLV.setDivider(null);
		gameLV.setCacheColorHint(android.R.color.transparent);
		gameAdapter = new DialogStringAdapter(getContext(), gameList);
		gameLV.setAdapter(gameAdapter);
		
		lists.add(softLV);
		lists.add(gameLV);
		
		adapter = new ViewPagerAdapter(lists);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				soft.setTextColor(arg0 == 0 ? Color.parseColor("#666666") : Color.GRAY);
				game.setTextColor(arg0 == 1 ? Color.parseColor("#666666") : Color.GRAY);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	void initMessage(){
		gameList.add("全部");
		gameList.add("养成");
		gameList.add("冒险");
		gameList.add("动作");
		gameList.add("战略");
		gameList.add("问答");
		gameList.add("益智");
		gameList.add("棋牌");
		gameList.add("街机");
		gameList.add("运动");
		
		softList.add("装机必备");
		softList.add("主题");
		softList.add("生活助理");
		softList.add("影视多媒体");
		softList.add("交通和位置");
		softList.add("通讯和社交");
		softList.add("资讯与阅读");
		softList.add("金融理财");
		softList.add("学习和教育");
		softList.add("商业办公");
		softList.add("系统和工具");
		softList.add("运动和保健");
		softList.add("外部共享");
	}
}
