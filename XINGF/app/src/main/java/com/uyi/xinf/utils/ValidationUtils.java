package com.uyi.xinf.utils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查工具类
 * @author chao
 *
 */
public abstract class ValidationUtils implements Serializable {
	private static final long serialVersionUID = -7712299555022613697L;
	
		
	/**
	 * 匹配正则表达式
	 * @param reg 正则表达式
	 * @param content 匹配内容
	 * @return
	 */
	public static boolean pattern(String reg,String content){
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		boolean b= matcher.matches();
		return b;
	}
	
	/**
	 * 是否为null/字符串""
	 * @param str
	 * @return
	 */
	public static boolean isNull(String... str){
		for(String s : str){
			if(s == null||s.equals("")){
				return true;
			}
		}
		return false;
	}

	/** 手机号码匹配	 * 
	 *  rg\iuyt	qwertyuio[po0`124`569hqw23
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobile(String mobiles) {
		Pattern p = Pattern .compile("^1[3,4,5,7,8]\\d{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**  
    * 验证邮箱地址是否正确  
    * @param email  
    * @return  
    */  
   public static boolean isEmail(String email){  
    boolean flag = false;  
	     try{  
	      String check = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";  
	      Pattern regex = Pattern.compile(check);  
	      Matcher matcher = regex.matcher(email);  
	      flag = matcher.matches();  
	     }catch(Exception e){  
	      flag = false;  
	     }  
    return flag;  
   }  
   
   /** 
    * 检查QQ是否合法，必须是数字，且首位不能为0，最长15位 
    * @param value 
    * @return 
    */  
     
   public boolean checkQQ(String value){  
       return value.matches("[1-9][0-9]{4,13}");  
   }  
   /** 
    * 检查输入是否超出规定长度 
    *  
    * @param length 
    * @param value 
    * @return 
    */  
   public boolean checkLength(String value, int length) {  
       return ((value == null || "".equals(value.trim())) ? 0 : value.length()) <= length;  
   }  
   
   
   
   public static int length(String str){
	   str  =  str.replaceAll("[^\\x00-\\xff]","**");
       int  length  =  str.length();
       return  length;
   }
	
   /**
    * 检查是否为整数
    * @param value
    * @return
    */
   public boolean checkInteger(String value){
   		Pattern p = Pattern .compile("^\\d*$");
		Matcher m = p.matcher(value);
		return m.matches();
   }
   
   public static boolean equlse(String v,String v1){
		if(v == null|| v1 == null){ return false;}
		if(v.equals(v1)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 只要有一个 引用相等 都返回True
	 * @Title: orEqulse
	 * @Description: TODO
	 * @param value
	 * @param v
	 * @return
	 * @return: boolean
	 */
	public static boolean orEqulse(String value,String...v){
		if(value == null){ return false;}
		if(v.length > 0){
			for(String i : v){
				if(equlse(value, i)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 字符串函数  模仿数据库 REGIHT()
	 * @param value
	 * @param count
	 * @return
	 */
	public static String right(String value,Integer count){
		
		return value.substring(value.length()-count);
	}
	/**
	 * 字符串函数  模仿数据库 LEFT()
	 * @param value
	 * @param count
	 * @return
	 */
	public static String left(String value,Integer count){
		if(value.length()>=count){
			return value.substring(0,count)+"...";
		}else {
			return value;
		}
	}
}
