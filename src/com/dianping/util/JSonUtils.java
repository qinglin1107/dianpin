package com.dianping.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSonUtils {

	public static String getString(JSONObject object, String propname) {
		try {
			if (object != null && object.containsKey(propname)) {
				Object value = object.get(propname);
				if (value instanceof String) {
					return (String) object.get(propname);
				} else {
					return String.valueOf(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Integer getInt(JSONObject object, String propname) {
		try {
			if (object != null && object.containsKey(propname)) {
				Object value = object.get(propname);
				if (value instanceof Float) {
					value = ((Float) value).intValue();
				} else if (value instanceof Double) {
					value = ((Double) value).intValue();
				} else if (value instanceof Long) {
					value = ((Long) value).intValue();
				} else if (value instanceof String) {
					String temp = (String) value;
					if (temp.contains(".")) {
						temp = temp.substring(0, temp.lastIndexOf("."));
					}
					value = Integer.parseInt(temp);
				}
				return (Integer) value;
			}
		} catch (Exception e) {
		}
		return 0;
	}

	// public static Long getLong(JSONObject object, String propname) {
	// try {
	// if (object != null && object.containsKey(propname)) {
	// return (Long) object.get(propname);
	// }
	// } catch (Exception e) {
	// }
	// return -1l;
	// }
	//
	// public static Float getFloat(JSONObject object, String propname) {
	// try {
	// if (object != null && object.containsKey(propname)) {
	// return (Float) object.get(propname);
	// }
	// } catch (Exception e) {
	// }
	//
	// return -1.0f;
	// }
	//
	// public static Double getDouble(JSONObject object, String propname) {
	// try {
	// if (object != null && object.containsKey(propname)) {
	// return (Double) object.get(propname);
	// }
	// } catch (Exception e) {
	// }
	//
	// return -1.0d;
	// }

	public static Boolean getBoolean(JSONObject object, String propname) {
		try {
			if (object != null && object.containsKey(propname)) {
				return (Boolean) object.get(propname);
			}
		} catch (Exception e) {
		}

		return false;
	}

	public static JSONObject getJSonObject(JSONObject object, String propname) {
		try {
			if (object != null && object.containsKey(propname)) {
				return (JSONObject) object.get(propname);
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static JSONArray getJSonArray(JSONObject object, String propname) {
		JSONArray array = new JSONArray();
		try {
			if (object != null && object.containsKey(propname)) {
				return (JSONArray) object.get(propname);
			}
		} catch (Exception e) {
			return array;
		}
		return array;
	}
	
	public static JSONObject getJSONObject(String content){
		JSONObject object = null;
		try {
		 object =(JSONObject) JSONValue.parse(content);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}
}
