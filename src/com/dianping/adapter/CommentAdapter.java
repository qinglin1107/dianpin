package com.dianping.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianping.R;
import com.dianping.model.PackInfo;

public class CommentAdapter extends BaseImageAdapter<PackInfo>{

	public CommentAdapter(Context context, int textViewResourceId,
			List<PackInfo> objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PackInfo info = getItem(position);
		View view = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.comment_adapter_layout, null);
			view = convertView;
		}else{
			view = convertView;
		}
		TextView appName = (TextView)view.findViewById(R.id.appName);
		appName.setText(info.name);
		ImageView icon = (ImageView)view.findViewById(R.id.icon);
		icon.setImageDrawable(info.icon);
		return view;
	}

}
