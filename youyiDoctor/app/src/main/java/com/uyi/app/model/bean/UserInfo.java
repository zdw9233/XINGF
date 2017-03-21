package com.uyi.app.model.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;

/**
 * 登陆用户信息
 * @author user
 *
 */
public class UserInfo {

	@Id
	public int id;
	@Column
	public String authToken;
	/**
	 *  0: 病人 
	 * 	1: 专家
	 *	2: 资深专家
	 *	3: 助理
	 */
	@Column
	public Integer type; 
	@Column
	public int userId;
	@Column
	public String account;
	@Column
	public String realName;
	@Column
	public String password;
	@Column
	public String icon;
	@Column
	public String address;
	@Column
	public String city;
	@Column
	public Integer beans;
	@Column
	public Integer consumedBeans;
	@Column
	public String lastLoginTime;
	@Column
	public boolean login;//当前登录的
	
	
	//医生有的字段
	@Column
	public String info;
	
	//是否加入健康团队
	//医生/助理有此字段
	@Column
	public Boolean joinedGroup;
//	@Column
//	public int groupType;
	
	
	public UserInfo() {
		super();
	}

	public UserInfo(String authToken, Integer type, int userId, String account, String realName, String password,
			String icon, String address, String city, Integer beans, Integer consumedBeans, String lastLoginTime) {
		super();
		this.authToken = authToken;
		this.type = type;
		this.userId = userId;
		this.account = account;
		this.realName = realName;
		this.password = password;
		this.icon = icon;
		this.address = address;
		this.city = city;
		this.beans = beans;
		this.consumedBeans = consumedBeans;
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return " [id=" + id + ", authToken=" + authToken + ", type=" + type + ", userId=" + userId
				+ ", account=" + account + ", realName=" + realName + ", password=" + password + ", icon=" + icon
				+ ", address=" + address + ", city=" + city + ", beans=" + beans + ", consumedBeans=" + consumedBeans
				+ ", lastLoginTime=" + lastLoginTime + ", login=" + login + ", info=" + info + ", joinedGroup="
				+ joinedGroup + "]";
	}
	
}
