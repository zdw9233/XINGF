package com.uyi.app.service;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.volley.RequestManager;

import android.content.Context;


/**
 * 用户信息
 * @author user
 *
 */
public class UserService {

	
	public static void loadUserInfo(final Context context){
		UserInfo userInfo = UserInfoManager.getLoginUserInfo(context);
		if(userInfo != null){
			RequestManager.getObject(Constens.ACCOUNT_DETAIL, context, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					try {
						UserInfo userInf = UserInfoManager.getLoginUserInfo(context);
						userInf.realName = data.getString("realName");
						userInf.icon = data.getString("icon");
						userInf.beans = data.getInt("beans");
						UserInfoManager.setLoginUserInfo(context, userInf);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
//			try {
//				JSONObject params = new JSONObject();
//				params.put("account", userInfo.account);
//				params.put("password", userInfo.password);
//				RequestManager.postObject(Constens.LOGIN_URL, context, params, new Response.Listener<JSONObject>() {
//					public void onResponse(JSONObject data) {
//						UserInfo userInfo = new UserInfo();
//						try {
//							userInfo.authToken = data.getString("authToken");
//							userInfo.type = data.getInt("type");
//							userInfo.userId = data.getInt("id");
//							userInfo.account = data.getString("account");
//							userInfo.realName = data.getString("realName");
//							userInfo.password = userInfo.password;
//							userInfo.icon = data.getString("icon");
//							userInfo.address = data.has("address")?data.getString("address"):null;
//							userInfo.city = data.has("city")?data.getString("city"):null;
//							userInfo.beans = data.has("beans")?data.getInt("beans"):null;
//							userInfo.consumedBeans = data.has("consumedBeans")?data.getInt("consumedBeans"):null;
//							userInfo.lastLoginTime = data.getString("lastLoginTime");
//							UserInfoManager.setLoginUserInfo(context, userInfo);
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				}, null);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			
		}
	}
	
}
