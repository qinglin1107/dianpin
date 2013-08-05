package com.dianping.adapter;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.dianping.util.AppConstance;
import com.dianping.util.FetchManager;
import com.dianping.util.MD5;
import com.dianping.util.SDCardUtils;

public class BaseImageAdapter<T> extends ArrayAdapter<T> {

	private Executor threadPool = null;
	private Executor ioPool = null;
	protected Context context = null;
	protected LayoutInflater mInflater = null;
	public BaseImageAdapter(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		mInflater = LayoutInflater.from(context);
		if(threadPool == null){
			threadPool = Executors.newFixedThreadPool(3);
		}
		if(ioPool == null){
			ioPool = Executors.newFixedThreadPool(1);
		}
	}
	
	protected void GetImage(ImageView image,String url){
		image.setImageBitmap(null);
		String filename = new String(MD5.toMd5(url.getBytes()));
		if(SDCardUtils.isExist(filename)){
			ioPool.execute(new GetImageFromSDCard(image, url));
		}else{
			threadPool.execute(new GetImageFromNet(image, url));
		}
	}
	
	class GetImageFromSDCard implements Runnable{

		private ImageView image = null;
		private String fileName = null;
		public GetImageFromSDCard(ImageView image,String url){
			this.image = image;
			this.fileName = url;
		}
		
		@Override
		public void run() {
			byte[] b = FetchManager.getImageFromSDCard(fileName);
			if(b != null && b.length > 0){
				Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
				Message msg = new Message();
				image.setTag(bmp);
				msg.obj = image;
				msg.what = AppConstance.UPDATE_VIEW;
				mHandler.sendMessage(msg);
			}else{
				String str = new String(MD5.toMd5(fileName.getBytes()));
				SDCardUtils.delete(str);
				threadPool.execute(new GetImageFromNet(image, fileName));
			}
		}
	}
	
	class GetImageFromNet implements Runnable{

		private ImageView image = null;
		private String url = null;
		public GetImageFromNet(ImageView image,String url){
			this.image = image;
			this.url = url;
		}
		@Override
		public void run() {
			byte[] b = FetchManager.getImageFromNet(url);
			if(b != null && b.length > 0){
				Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
				saveBitmap(url, bmp);
				Message msg = new Message();
				image.setTag(bmp);
				msg.obj = image;
				msg.what = AppConstance.UPDATE_VIEW;
				mHandler.sendMessage(msg);
			}
		}
	}
	
	private void saveBitmap(final String url,final Bitmap b){
		Runnable run = new Runnable() {
			@Override
			public void run() {
				String filename = new String(MD5.toMd5(url.getBytes()));
				SDCardUtils.saveBitmap(filename, b);
			}
		};
		ioPool.execute(run);
	}

	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			ImageView image = (ImageView) msg.obj;
			Bitmap bmp = (Bitmap) image.getTag();
			image.setImageBitmap(bmp);
		}
		
	};
}
