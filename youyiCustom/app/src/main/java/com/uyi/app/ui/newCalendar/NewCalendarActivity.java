package com.uyi.app.ui.newCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.uyi.app.utils.T;
import com.uyi.custom.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NewCalendarActivity extends Activity {

	private String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式   
	private TextView popupwindow_calendar_month;
	private SignCalendar calendar;
	private List<String> list = new ArrayList<String>(); //设置标记列表
	boolean isinput=false;
	private String date1 = null;//单天日期
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcalendar_main);

        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd");
		Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
		date1 =formatter.format(curDate); 
		
		popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
		calendar = (SignCalendar) findViewById(R.id.popupwindow_calendar);
		popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
				+ calendar.getCalendarMonth() + "月");
//		if (null != date) {
//			int years = Integer.parseInt(date.substring(0,
//					date.indexOf("-")));
//			int month = Integer.parseInt(date.substring(
//					date.indexOf("-") + 1, date.lastIndexOf("-")));
//			popupwindow_calendar_month.setText(years + "年" + month + "月");
//
//			calendar.showCalendar(years, month);
//			calendar.setCalendarDayBgColor(date,
//					R.drawable.calendar_date_focused);
//		}

		list.add("2015-11-10");
		list.add("2015-11-02");
		list.add("2016-12-01");
		list.add("2016-12-02");
		list.add("2016-12-03");
		query();
           Date today= calendar.getThisday();
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
           /* calendar.removeAllMarks();
           list.add(df.format(today));
           calendar.addMarks(list, 0);*/
           //将当前日期标示出来
//           	add(df.format(today));
            //calendar.addMark(today, 0);
//           	query();
            HashMap<String, Integer> bg = new HashMap<String, Integer>();
            calendar.setCalendarDayBgColor(today, R.drawable.bg_sign_today);
		//监听所选中的日期
		calendar.setOnCalendarClickListener(new SignCalendar.OnCalendarClickListener() {

			public void onCalendarClick(int row, int col, String dateFormat) {
//				int month = Integer.parseInt(dateFormat.substring(
//						dateFormat.indexOf("-") + 1,
//						dateFormat.lastIndexOf("-")));
//
//				if (calendar.getCalendarMonth() - month == 1//跨年跳转
//						|| calendar.getCalendarMonth() - month == -11) {
//					calendar.lastMonth();
//
//				} else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
//						|| month - calendar.getCalendarMonth() == -11) {
//					calendar.nextMonth();
//
//				} else {
//					list.add(dateFormat);
//					calendar.addMarks(list, 0);
//					calendar.removeAllBgColor();
//					calendar.setCalendarDayBgColor(dateFormat,
//							R.drawable.calendar_date_focused);
//					date = dateFormat;//最后返回给全局 date
//				}
				T.showShort(NewCalendarActivity.this,"今天是"+dateFormat+"!");
			}
		});

		//监听当前月份
		calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
			public void onCalendarDateChanged(int year, int month) {
				popupwindow_calendar_month
				.setText(year + "年" + month + "月");
			}
		});
	}
	
	 public void add(String date)
	    {
	        ArrayList<sqlit> persons = new ArrayList<sqlit>();

	        sqlit person1 = new sqlit(date,"true");

	        persons.add(person1);

//	        dbManager.add(persons);
	    }
	 
	 public void query()
	    {
//	        List<sqlit> persons = new ArrayList<>();
//	        for (sqlit person : persons)
//	        {
//	            list.add(person.date);
//	            if(date1.equals(person.getDate())){
//					isinput=true;
//				}
//	        }
	        calendar.addMarks(list, 0);
	    }
	
	  @Override
	    protected void onDestroy()
	    {
	        super.onDestroy();
	    }

}
