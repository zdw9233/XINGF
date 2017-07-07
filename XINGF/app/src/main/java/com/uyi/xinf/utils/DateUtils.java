package com.uyi.xinf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.uyi.xinf.Constens;

public class DateUtils {

	
	public static String toDate(Date date){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    return dateFormat.format(date);
	}
	public static String toDate(Date date,String pattern){
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	public static String toDateStringByDateString(String date) throws ParseException {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 return dateFormat.format(toDateByString(date));
	}
	
	public static Date toDateByString(String date) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constens.DATE_FORMAT);
		return dateFormat.parse(date);
	}
	public static Date toDateByString(String date,String patten) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
		return dateFormat.parse(date);
	}
	
}
