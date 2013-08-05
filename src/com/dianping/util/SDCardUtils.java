package com.dianping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class SDCardUtils {

	private final static String SDCARD_DIR = Environment
			.getExternalStorageDirectory().getPath() + "/dianping/";
	private final static String NOMEID_FILE = SDCARD_DIR + "nomedia";

	public static boolean hasSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean hasDIR() {
		File file = new File(SDCARD_DIR);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean hasNoMedia() {
		File file = new File(NOMEID_FILE);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static void createCacheDir() {
		if (!hasDIR()) {
			File file = new File(SDCARD_DIR);
			file.mkdir();
		}
		if (!hasNoMedia()) {
			File file = new File(NOMEID_FILE);
			file.mkdirs();
		}
	}

	public static boolean checkFileExits(String filename) {
		if (!hasSDCard()) {
			return false;
		}
		int key1 = getFileNameKey(filename, true);
		int key2 = getFileNameKey(filename, false);
		File file = new File(SDCARD_DIR + key1 + "/" + key2 + "/" + filename);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static Bitmap getBitmap(String filename) {
		if (!hasSDCard()) {
			return null;
		}
		int key1 = getFileNameKey(filename, true);
		int key2 = getFileNameKey(filename, false);
		String filepath = SDCARD_DIR + key1 + "/" + key2 + "/" + filename;
		return BitmapFactory.decodeFile(filepath);
	}

	public static byte[] getFile(String filename) {
		if (!hasSDCard()) {
			return null;
		}
		int key1 = getFileNameKey(filename, true);
		int key2 = getFileNameKey(filename, false);
		File file = new File(SDCARD_DIR + key1 + "/" + key2 + "/" + filename);
		if (!file.exists()) {
			return null;
		}
		InputStream inputStream = null;
		byte b[] = null;
		try {
			inputStream = new FileInputStream(file);
			b = new byte[inputStream.available()];
			inputStream.read(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b;
	}

	public static boolean isExist(String filename) {
		int key1 = getFileNameKey(filename, true);
		int key2 = getFileNameKey(filename, false);
		File file = new File(SDCARD_DIR + key1 + "/" + key2 + "/" + filename);
		return file.exists();
	}

	public static void saveFile(String filename, byte[] b) {
		if (!hasSDCard()) {
			return;
		}
		createCacheDir();
		int key1 = getFileNameKey(filename, true);
		int key2 = getFileNameKey(filename, false);
		OutputStream outputStream = null;
		File file = new File(SDCARD_DIR + key1 + "/" + key2 + "/" + filename);
		try {
			File dir = new File(SDCARD_DIR + key1 + "/" + key2);
			dir.mkdirs();
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			outputStream.write(b);
		} catch (Exception e) {
			if (file.exists())
				file.delete();
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveBitmap(String filename, Bitmap bitmap) {
		if (!hasSDCard()) { 
			return;
		}
		createCacheDir();
		int key1 = getFileNameKey(filename, true);
		int key2 = getFileNameKey(filename, false);
		OutputStream outputStream = null;
		File file = new File(SDCARD_DIR + key1 + "/" + key2 + "/" + filename);
		try {
			File dir = new File(SDCARD_DIR + key1 + "/" + key2);
			dir.mkdirs();
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
			outputStream.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void delete(String filename) {
		if (!hasSDCard()) {
			return;
		}
		int key1 = getFileNameKey(filename, true);
		int key2 = getFileNameKey(filename, false);

		File file = new File(SDCARD_DIR + key1 + "/" + key2 + "/" + filename);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static int getFileNameKey(String filename, boolean isFirstKey) {
		int filenameHashcode = filename.hashCode();
		if (isFirstKey) {
			return filenameHashcode & (0xf);
		} else {
			return (filenameHashcode >> 4) & (0xf);
		}
	}
}
