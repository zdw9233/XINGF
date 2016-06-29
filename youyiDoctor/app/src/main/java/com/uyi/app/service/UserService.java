package com.uyi.app.service;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.utils.JSONObjectUtils;
import com.volley.RequestManager;

import android.content.Context;


/**
 * 用户信息
 * @author user
 *
 */
public class UserService {

	
//	public static void loadUserInfo(final Context context){
//		if(UserInfoManager.getLoginUserInfo(context) != null){
//			RequestManager.getObject(Constens.ACCOUNT_DETAIL, context, new Listener<JSONObject>() {
//				public void onResponse(JSONObject data) {
//					try {
//						UserInfo userInfo = new UserInfo();
//						userInfo.userId = data.getInt("id");
//						userInfo.account = data.getString("account");
//						userInfo.realName = data.getString("realName");
//						userInfo.icon = data.getString("icon");
//						userInfo.address = data.getString("address");
//						userInfo.beans = data.getInt("beans");
//						UserInfoManager.setLoginUserInfo(context, userInfo);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}
//	}
	
}
