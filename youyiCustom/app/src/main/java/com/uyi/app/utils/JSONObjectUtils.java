package com.uyi.app.utils;

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
			return null;
		}
		if(!jsonObject.has(key)){
			return null;
		}
		try {
			if(ValidationUtils.orEqulse(jsonObject.getString(key), null,"","null")){
				return "";
			}
			return jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
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
	
	
	
}
