package com.dianping.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;

public class FetchManager {

	public static byte[] getImage(String imageuri) {
		if (imageuri == null) {
			return null;
		}
		byte[] b = null;
		String filename = new String(MD5.toMd5(imageuri.getBytes()));
		if (SDCardUtils.hasSDCard()) {
			if (SDCardUtils.checkFileExits(filename)) {
				b = SDCardUtils.getFile(filename);
				if (null == b || b.length == 0) {
					SDCardUtils.delete(filename);
				}
			}
		}

		if (b == null || b.length == 0) {
			b = FetchManager.getImageFromNet(imageuri);
			if (b != null && b.length != 0) {
				SDCardUtils.saveFile(filename, b);
			}
		}
		return b;
	}

	public static byte[] getImageFromSDCard(String url) {
		String filename = new String(MD5.toMd5(url.getBytes()));
		if (SDCardUtils.checkFileExits(filename)) {
			byte[] b = SDCardUtils.getFile(filename);
			if (null == b || b.length == 0) {
				SDCardUtils.delete(filename);
			}
			return b;
		}
		return null;
	}

	public static Bitmap getBitmapFromSDCard(String url) {
		if (null != url) {
			String filename = new String(MD5.toMd5(url.getBytes()));

			if (SDCardUtils.checkFileExits(filename)) {
				Bitmap b = SDCardUtils.getBitmap(filename);
				if (null == b) {
					SDCardUtils.delete(filename);
				}
				return b;
			}
		}
		return null;
	}

	public static byte[] getImageFromNet(String picUrl) {
		InputStream inputStream = null;
		ByteArrayOutputStream bos = null;
		try {
			URL url = new URL(picUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			inputStream = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			bos = new ByteArrayOutputStream();
			while ((len = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			return bos.toByteArray();
		} catch (IOException e) {
		}finally{
			if(null != inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
