package com.uyi.xinf.utils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 获取JSONObject 参数 避免 Key不存在 的Exception
 * @author user
 *
 */
public class JSONObjectUtils {

	
	
	public static String getString(JSONObject jsonObject,String key){
		if(jsonObject == null){
			return "";
		}
		if(!jsonObject.has(key)){
			return "";
		}
		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static int getInt(JSONObject jsonObject,String key){
		if(jsonObject == null){
			return 0;
		}
		if(!jsonObject.has(key)){
			return 0;
		}
		try {
			return jsonObject.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Double getDouble(JSONObject jsonObject,String key){
		if(jsonObject == null){
			return 0.0;
		}
		if(!jsonObject.has(key)){
			return 0.0;
		}
		try {
			return jsonObject.getDouble(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	
	
	public static Boolean getBoolean(JSONObject jsonObject,String key){
		if(jsonObject == null){
			return false;
		}
		if(!jsonObject.has(key)){
			return false;
		}
		try {
			return jsonObject.getBoolean(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
}
