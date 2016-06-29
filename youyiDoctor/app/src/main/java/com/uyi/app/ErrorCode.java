package com.uyi.app;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.utils.L;



/**
 * 解析错误信息
 * @author user
 *
 */
public final class ErrorCode {
	public static Map<String,String> error ;
	
	static {
		
		error = new HashMap<String,String>();
		error.put("301", "请求的数据具有新的位置且更改是永久的");
		error.put("302", "请求的数据临时具有不同 URI");
		error.put("400", "请求中有语法问题");
//		error.put("401", "未授权客户机访问数据");
		error.put("401", "登陆信息已失效!");
		error.put("404", "URI错误");
		error.put("415", "不支持的请求");
		error.put("1000", "服务器内部异常");
//		error.put("2000", "AuthToken错误或过期");
		error.put("2000", "登陆信息已失效!");
		error.put("3000", "账户名字包含非法字符");
		error.put("3001", "账户名太长或太短");
		error.put("3002", "密码设置不规范");
		error.put("3003", "密码太长或太短");
		error.put("3004", "账户名已被使用");
		error.put("3005", "注册电话已被使用");
		error.put("3006", "电话号码格式错误");
		error.put("3007", "身份证格式错误");
		error.put("3008", "不存在的账户");
		error.put("3009", "账户名或密码错误");
		error.put("3010", "旧密码错误");
		error.put("3011", "电子邮箱格式错误");
		
	}
	
	public static CharSequence getErrorByNetworkResponse(NetworkResponse networkResponse) {
		if(networkResponse == null){
			System.out.println("空");
			
			return error.get("1000");
		}
		if(networkResponse != null && networkResponse.data != null){
			try {
				L.d("ResponseError", "ResponseCode:"+networkResponse.statusCode+"="+new String(networkResponse.data,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(networkResponse.statusCode == 301){
			return error.get("301");
		}else if(networkResponse.statusCode == 302){
			return error.get("302");
		}else if(networkResponse.statusCode == 400 || networkResponse.statusCode == 204){
			String str;
			try {
				str = new String(networkResponse.data,"UTF-8");
				JSONObject jsonObject = new JSONObject(str);
				if(jsonObject.has("errorCode") && jsonObject.has("message")){
					return jsonObject.getString("message");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return error.get("400");
		}else if(networkResponse.statusCode == 401){
			UYIApplication.loginOut();
			return error.get("401");
		}else if(networkResponse.statusCode == 404){
			return error.get("404");
		}else if(networkResponse.statusCode == 415){
			return error.get("415");
		}else{
			System.out.println("qita");
			return error.get("1000");
		}
	}
	
	public static String getError(String errorCode){
		if(error.containsKey(errorCode)){
			return error.get(errorCode);
		}else{
			return error.get("1000");
		}
	}
	
}
