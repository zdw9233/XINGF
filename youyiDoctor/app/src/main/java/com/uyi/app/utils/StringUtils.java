package com.uyi.app.utils;

import java.security.MessageDigest;

public final class StringUtils {

	/**
	 * 截取左边字符串 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getLeftString(String str,int length){
		if(str == null || "".equals(str)){
			return null;
		}
		if(str.length() < length){
			return str;
		}
		return str.substring(0, length)+"...";
	}
	/**
	 * 获取字符串字节长度
	 * @param s
	 * @return
	 */
	public static  int length(String s)   {  
        s = s.replaceAll("[^\\x00-\\xff]", "**");  
        return s.length(); 
    } 
	
	
	public static String md5(String s){
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	
	
}
