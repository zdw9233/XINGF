package com.uyi.app;

import java.util.List;

import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.model.dao.UserInfoDao;
import com.uyi.app.model.dao.impl.UserInfoDaoImpl;

import android.content.Context;


/**
 * 用户信息管理器
 * @author user
 *
 */
public abstract class UserInfoManager{
	 
 
	public static UserInfo getLoginUserInfo(Context context) {
		UserInfoDao userInfoDao = UYIApplication.getBaseDao(UserInfoDaoImpl.class);
		List<UserInfo> userinfos = userInfoDao.findAll();
		for(UserInfo info: userinfos){
			if(info.login){
				 return info;
			}
		}
		return null;
	}
	
	
	public static void updateLoginUserInfo(Context context,UserInfo userInfo){
		UserInfoDao userInfoDao = UYIApplication.getBaseDao(UserInfoDaoImpl.class);
		userInfoDao.update(userInfo);
	}

	public static void setLoginUserInfo(Context context,UserInfo userInfo) {
		UserInfoDao userInfoDao = UYIApplication.getBaseDao(UserInfoDaoImpl.class);
		
		List<UserInfo> userinfos = userInfoDao.findAll();
		for(UserInfo info : userinfos){
			info.login = false;
			userInfoDao.update(info);
		}
		UserInfo info = userInfoDao.get("userId", userInfo.id);
		if(info != null){
			info.authToken = userInfo.authToken;
			info.type = userInfo.type;
			info.account = userInfo.account;
			info.realName = userInfo.realName;
			info.password = userInfo.password;
			info.icon = userInfo.icon;
			info.address = userInfo.address;
			info.city = userInfo.city;
			info.beans = userInfo.beans;
			info.consumedBeans = userInfo.consumedBeans;
			info.lastLoginTime = userInfo.lastLoginTime;
			info.login = true;
			userInfoDao.update(info);
		}else{
			userInfo.login = true;
			userInfoDao.save(userInfo);
		}
	}
	
	public static void clearLoginUserInfo(Context context){
		UserInfoDao userInfoDao = UYIApplication.getBaseDao(UserInfoDaoImpl.class);
		List<UserInfo> userinfos = userInfoDao.findAll();
		for(UserInfo info : userinfos){
			info.login = false;
			userInfoDao.update(info);
		}
	}
}
