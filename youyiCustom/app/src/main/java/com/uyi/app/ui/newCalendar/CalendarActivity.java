package com.uyi.app.ui.newCalendar;

import android.widget.Toast;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ContentView(R.layout.activity_calendar_main)
public class CalendarActivity extends BaseActivity {
	@ViewInject(R.id.calendar) private CalendarView calendarView;
	@ViewInject(R.id.headerView) private HeaderView headerView;
	List<Map<String,Object>> datas = new ArrayList<>();
	@Override
	protected void onInitLayoutAfter() {
		Loading.bulid(this, null).show();

		RequestManager.getArray(String.format(Constens.GET_CUSTOM_RICHEN,"2016-09-11","2016-10-11"), this, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray jsonObject) {
				Loading.bulid(CalendarActivity.this, null).dismiss();
				L.e(jsonObject.toString());

					try {
						for(int i = 0 ; i< jsonObject.length();i++){
							Map<String,Object> stringObjectMap = new HashMap<String, Object>();
							JSONObject object = jsonObject.getJSONObject(i);
							stringObjectMap.put("day",object.getString("day"));
							JSONArray array = jsonObject.getJSONObject(i).getJSONArray("type");
							L.e(datas.toString());
							if(array.length()> 0){
								List<Integer > integers = new ArrayList<Integer>();
								for(int j = 0 ;j< array.length();j++){
									integers.add(array.getInt(j));
								}
								stringObjectMap.put("type",integers);
								datas.add(stringObjectMap);
								L.e(datas.toString());
							}


						}
					} catch (JSONException e) {
						e.printStackTrace();
					}


			}
		});
		L.e(datas.toString());
//		List<Date> markDates = new ArrayList<Date>();
//		markDates.add(new Date());
		calendarView.setMarkDates(datas);
		//设置点击操作
		calendarView.setOnCalendarViewListener(new CalendarView.OnCalendarViewListener() {

			@Override
			public void onCalendarItemClick(CalendarView view, Date date) {
				// TODO Auto-generated method stub
				final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
				Toast.makeText(CalendarActivity.this, format.format(date), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCalendarMonthChange(Date firstDate, Date endDate) {

			}
		});


	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}
}

