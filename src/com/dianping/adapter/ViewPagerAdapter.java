package com.dianping.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> lists;

	public ViewPagerAdapter(List<View> lists) {
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return this.lists.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(this.lists.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(this.lists.get(position));
		return lists.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
