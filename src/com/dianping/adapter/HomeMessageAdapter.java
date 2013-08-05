package com.dianping.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianping.R;
import com.dianping.WebActivity;
import com.dianping.model.HotMessage;

public class HomeMessageAdapter extends BaseImageAdapter<HotMessage> {

	public HomeMessageAdapter(Context context, int textViewResourceId,
			List<HotMessage> objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final HotMessage item = getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.home_item_adapter, null);
			holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			holder.nickname = (TextView) convertView
					.findViewById(R.id.nickname);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.appName = (TextView) convertView.findViewById(R.id.app_name);
			holder.appContent = (TextView) convertView
					.findViewById(R.id.app_content);
			holder.oppose = (TextView) convertView.findViewById(R.id.oppose);
			holder.support = (TextView) convertView.findViewById(R.id.support);
			holder.appDetails = (TextView) convertView
					.findViewById(R.id.app_details);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (!TextUtils.isEmpty(item.user.avatar))
			GetImage(holder.avatar, item.user.avatar);
		if (!TextUtils.isEmpty(item.appInfo.app_icon))
			GetImage(holder.icon, item.appInfo.app_icon);
		if (!TextUtils.isEmpty(item.user.username))
			holder.nickname.setText(item.user.username);
		if (!TextUtils.isEmpty(item.dianPin.reasion))
			holder.appContent.setText(item.dianPin.reasion);
		if (item.dianPin != null) {
			holder.support.setText("" + item.dianPin.support_count);
			holder.oppose.setText("" + item.dianPin.oppose_count);
		}
		if (!TextUtils.isEmpty(item.appInfo.name))
			holder.appName.setText(item.appInfo.name);
		holder.appDetails.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent((Activity)context,WebActivity.class);
				intent.putExtra("url", item.appInfo.detail_url);
				context.startActivity(intent);
				((Activity)context).overridePendingTransition(R.anim.push_left_in, R.anim.activity_anim_out);
			}
		});
		return convertView;
	}

	class ViewHolder {
		ImageView avatar;
		TextView nickname;
		ImageView icon;
		TextView appName;
		TextView appContent;
		TextView support;
		TextView oppose;
		TextView appDetails;
	}

}
