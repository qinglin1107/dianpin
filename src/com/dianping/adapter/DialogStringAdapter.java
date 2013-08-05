package com.dianping.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dianping.R;

public class DialogStringAdapter extends BaseAdapter {

	private List<String> msgs;
	private LayoutInflater inflater;
	public DialogStringAdapter(Context context,List<String> lists){
		this.msgs = lists;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return msgs.size();
	}

	@Override
	public Object getItem(int position) {
		return msgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.text_adapter_layout, null);
			view = convertView;
		}else{
			view = convertView;
		}
		
		TextView tv = (TextView) view.findViewById(R.id.text);
		tv.setText(msgs.get(position));
		
		return view;
	}

}
