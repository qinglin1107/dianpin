package com.dianping;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.dianping.adapter.HomeMessageAdapter;
import com.dianping.adapter.ViewPagerAdapter;
import com.dianping.dialog.HomeCategoryDialog;
import com.dianping.model.HotMessage;
import com.dianping.net.HttpInterface;
import com.dianping.net.HttpUtils;
import com.dianping.util.JSonUtils;

public class HomeActivity extends Activity implements
		ViewPager.OnPageChangeListener, View.OnClickListener {

	private ViewPager viewPager;
	private List<View> lists = new ArrayList<View>();
	private List<HotMessage> list_new = new ArrayList<HotMessage>();
	private List<HotMessage> list_hot = new ArrayList<HotMessage>();
	private ViewPagerAdapter adapter;
	private View hot_hasMore;
	private View hot_noMore;
	private View new_hasMore;
	private View new_noMore;
	private View comment;
	private TextView category;
	private HomeCategoryDialog dialog;
	private int newOffset = 0;
	private int hotOffset = 0;
	private final int length = 20;
	private HomeMessageAdapter adapter_home_hot;
	private HomeMessageAdapter adapter_home_new;

	private static final int UPDATE_NEW = 1;
	private static final int UPDATE_HOT = 2;
	private static final int NEW_NOMORE = 3;
	private static final int HOT_NOMORE = 4;

	private boolean hot_get_new = false;
	private boolean new_get_new = false;

	private int state = 0;// 0 hot 1 new
	
	private TextView tv_hot;
	private TextView tv_new;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_NEW:
				adapter_home_new.notifyDataSetChanged();
				break;
			case UPDATE_HOT:
				adapter_home_hot.notifyDataSetChanged();
				break;
			case NEW_NOMORE:
				new_hasMore.setVisibility(View.GONE);
				new_noMore.setVisibility(View.VISIBLE);
				break;
			case HOT_NOMORE:
				hot_hasMore.setVisibility(View.GONE);
				hot_noMore.setVisibility(View.VISIBLE);
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_home);
		initView();
		//readHotMessage();
	}

	void initView() {
		viewPager = (ViewPager) this.findViewById(R.id.vPager);
		category = (TextView) this.findViewById(R.id.selector);
		tv_hot = (TextView)this.findViewById(R.id.btn_hot);
		tv_new = (TextView)this.findViewById(R.id.btn_new);
		category.setOnClickListener(this);
		comment = this.findViewById(R.id.comment);
		comment.setOnClickListener(this);
		tv_hot.setOnClickListener(this);
		tv_new.setOnClickListener(this);

		View hotView = getLayoutInflater().inflate(R.layout.main_home_view,
				null);
		View newView = getLayoutInflater().inflate(R.layout.main_home_view,
				null);
		View hotBottom = getLayoutInflater().inflate(R.layout.hasmore_layout,
				null);
		hot_hasMore = hotBottom.findViewById(R.id.hasMore);
		hot_noMore = hotBottom.findViewById(R.id.noMore);

		View newBottom = getLayoutInflater().inflate(R.layout.hasmore_layout,
				null);
		new_hasMore = newBottom.findViewById(R.id.hasMore);
		new_noMore = newBottom.findViewById(R.id.noMore);

		ListView hotListView = (ListView) hotView.findViewById(R.id.listview);
		adapter_home_hot = new HomeMessageAdapter(HomeActivity.this.getParent(),
				R.layout.home_item_adapter, list_hot);
		hotListView.addFooterView(hotBottom);
		hotListView.setAdapter(adapter_home_hot);
		hotListView.setOnScrollListener(onScrollListener);
		ListView newListView = (ListView) newView.findViewById(R.id.listview);
		adapter_home_new = new HomeMessageAdapter(HomeActivity.this.getParent(),
				R.layout.home_item_adapter, list_new);
		newListView.addFooterView(newBottom);
		newListView.setAdapter(adapter_home_new);
		newListView.setOnScrollListener(onScrollListener);

		lists.add(hotView);
		lists.add(newView);
		adapter = new ViewPagerAdapter(lists);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		dialog = new HomeCategoryDialog(this, R.style.CategoryTheme);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selector:
			dialog.show();
			break;
		case R.id.comment:
			Intent intent = new Intent(HomeActivity.this.getParent(),
					CommentActivity.class);
			HomeActivity.this.getParent().startActivity(intent);
			HomeActivity.this.getParent().overridePendingTransition(
					R.anim.push_left_in, R.anim.activity_anim_out);
			break;
		case R.id.btn_hot:
			viewPager.setCurrentItem(0, true);
			break;
		case R.id.btn_new:
			viewPager.setCurrentItem(1,true);
			break;
		}
	}

	private void readHotMessage() {
		hot_get_new = true;
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("page", String.valueOf(hotOffset)));
		param.add(new BasicNameValuePair("length", String.valueOf(length)));
		HttpUtils.HttpGetAsync(param, "readhot", new HttpInterface() {

			@Override
			public void success(String content) {
				hot_get_new = false;
				JSONObject json = (JSONObject) JSONValue.parse(content);
				JSONArray array = JSonUtils.getJSonArray(json, "data");
				int count = array.size();
				for (int i = 0; i < count; i++) {
					JSONObject obj = (JSONObject) array.get(i);
					list_hot.add(HotMessage.builder(obj));
				}
				if (count == 0) {
					mHandler.sendEmptyMessage(HOT_NOMORE);
				} else {
					newOffset += length;
					mHandler.sendEmptyMessage(UPDATE_HOT);
				}
			}

			@Override
			public void failed(String content) {
				hot_get_new = false;
			}
		});
	}
	
	private void readNewMessage() {
		new_get_new = true;
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("page", String.valueOf(newOffset)));
		param.add(new BasicNameValuePair("length", String.valueOf(length)));
		HttpUtils.HttpGetAsync(param, "readnew", new HttpInterface() {

			@Override
			public void success(String content) {
				new_get_new = false;
				JSONObject json = (JSONObject) JSONValue.parse(content);
				JSONArray array = JSonUtils.getJSonArray(json, "data");
				int count = array.size();
				for (int i = 0; i < count; i++) {
					JSONObject obj = (JSONObject) array.get(i);
					list_new.add(HotMessage.builder(obj));
				}
				if (count == 0) {
					mHandler.sendEmptyMessage(NEW_NOMORE);
				} else {
					newOffset += length;
					mHandler.sendEmptyMessage(UPDATE_NEW);
				}
			}

			@Override
			public void failed(String content) {
				new_get_new = false;
			}
		});
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		state = arg0;
		tv_hot.setTextColor(arg0 == 0 ? Color.parseColor("#666666") : Color.GRAY);
		tv_new.setTextColor(arg0 == 1 ? Color.parseColor("#666666") : Color.GRAY);
		if (arg0 == 1 && list_new.size() == 0 && !new_get_new) {
			readNewMessage();
		}
	}

	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem + visibleItemCount == totalItemCount) {
				if (state == 0) {
					if (hot_get_new)
						return;
					readHotMessage();
				} else {
					if(new_get_new)
						return;
					readNewMessage();
				}
			}

		}
	};
}
