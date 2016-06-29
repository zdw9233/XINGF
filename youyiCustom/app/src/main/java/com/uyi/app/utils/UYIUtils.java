package com.uyi.app.utils;

public class UYIUtils {

	
	
	/**
	 * 转换随访状态
	 * @param status
	 * @return
	 */
	public static String convertConsultationStatus(int status,boolean isCommented,boolean isDiscuss){
		if(status == 1){
			return "已提交";
		}
		if(status == 2){
			return "补充报告";	
		}
		if(status == 3){
			if(isDiscuss){
				return "医生提交到讨论组";
			}else{
				return "医生正在处理";
			}
		}
		if(status == 4){
			if(isCommented){
				return "已评论";
			}else{
				return "医生已处理";
			}
		}
		return "";
	}
	
	public static String convertGender(String gender){
		if("MALE".equals(gender)){
			return "男";
		}else{
			return "女";
		}
	}
}
