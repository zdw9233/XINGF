package com.uyi.app.ui.newCalendar;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UYIApplication;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.newCalendar.adapter.ExclusiveAdapter;
import com.uyi.app.ui.newCalendar.adapter.MedicalAdapter;
import com.uyi.app.ui.newCalendar.adapter.SelfAdapter;
import com.uyi.app.ui.newCalendar.adapter.TopthreeAdapter;
import com.uyi.app.ui.personal.schedule.AddScheduleActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ContentView(R.layout.activity_calendar_main)
public class CalendarActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>> {
	@ViewInject(R.id.calendar) private CalendarView calendarView;
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.recyclerView1) private RecyclerView recyclerView1;
	@ViewInject(R.id.updata_user_main)
	private LinearLayout updata_user_main;
	@ViewInject(R.id.recyclerView2) private RecyclerView recyclerView2;
	@ViewInject(R.id.recyclerView3) private RecyclerView recyclerView3;
	@ViewInject(R.id.recyclerView4) private RecyclerView recyclerView4;
	@ViewInject(R.id.view_header_right) private LinearLayout view_header_right;
	@ViewInject(R.id.layout_1) private LinearLayout layout_1;
	@ViewInject(R.id.layout_2) private LinearLayout layout_2;
	@ViewInject(R.id.layout_3) private LinearLayout layout_3;
	@ViewInject(R.id.layout_4) private LinearLayout layout_4;
	@ViewInject(R.id.view_header_left_layout_return) private LinearLayout view_header_left_layout_return;
	private PopupWindow mSetPhotoPop;
	MessageConform conform;
	private LinearLayoutManager linearLayoutManager;
	private LinearLayoutManager linearLayoutManager2;
	private LinearLayoutManager linearLayoutManager3;
	private LinearLayoutManager linearLayoutManager4;
	private ExclusiveAdapter exclusiveAdapter;
	private MedicalAdapter medicalAdapter;
	private SelfAdapter selfAdapter;
	private TopthreeAdapter topthreeAdapter;
	ArrayList<Map<String,Object>> datas = new ArrayList<>();
	ArrayList<Map<String,Object>> dataexclusiveConsult = new ArrayList<>();
	ArrayList<Map<String,Object>> dataselfCreate = new ArrayList<>();
	ArrayList<Map<String,Object>> datatopThree = new ArrayList<>();
	ArrayList<Map<String,Object>> datamedicalConsult = new ArrayList<>();
	String time = "";//记录点击的时间
	String newtime = "";//记录当前日期
	SimpleDateFormat format;
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).setTitle("").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
		SimpleDateFormat   formatter   =   new   SimpleDateFormat("yyyy-MM-dd");
		Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
		newtime  =   formatter.format(curDate);
		time = newtime;
		linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager2 = new LinearLayoutManager(this);
		linearLayoutManager3 = new LinearLayoutManager(this);
		linearLayoutManager4 = new LinearLayoutManager(this);
		exclusiveAdapter = new ExclusiveAdapter(this);
		medicalAdapter = new MedicalAdapter(this);
		selfAdapter = new SelfAdapter(this);
		selfAdapter.setOnItemClickListener(this);
		topthreeAdapter = new TopthreeAdapter(this);
		exclusiveAdapter.setDatas(dataexclusiveConsult);
		medicalAdapter.setDatas(datamedicalConsult);
		selfAdapter.setDatas(dataselfCreate);
		topthreeAdapter.setDatas(datatopThree);
		recyclerView1.setLayoutManager(linearLayoutManager);
		recyclerView1.setAdapter(exclusiveAdapter);
		recyclerView2.setLayoutManager(linearLayoutManager2);
		recyclerView2.setAdapter(selfAdapter);
		recyclerView3.setLayoutManager(linearLayoutManager3);
		recyclerView3.setAdapter(topthreeAdapter);
		recyclerView4.setLayoutManager(linearLayoutManager4);
		recyclerView4.setAdapter(medicalAdapter);

		Loading.bulid(this, null).show();
		format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		getMak();
		getDate(newtime);
			calendarView.setOnCalendarViewListener(new CalendarView.OnCalendarViewListener() {

		@Override
		public void onCalendarItemClick(CalendarView view, Date date) {
//				Toast.makeText(CalendarActivity.this, format.format(date), Toast.LENGTH_SHORT).show();
			time = format.format(date);
			getDate(time);
		}

		@Override
		public void onCalendarMonthChange(Date firstDate, Date endDate) {
			layout_1.setVisibility(View.GONE);
			layout_2.setVisibility(View.GONE);
			layout_3.setVisibility(View.GONE);
			layout_4.setVisibility(View.GONE);
			getMak();
		}

		@Override
		public void onAnimationStart() {

		}
	});
}
	public void getMak(){
		RequestManager.getArray(String.format(Constens.GET_CUSTOM_RICHEN,format.format(calendarView.getFirstDate()),format.format(calendarView.getEndDate())), this, new Response.Listener<JSONArray>() {
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
							List<Integer> integers = new ArrayList<Integer>();
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
				calendarView.notifyDateSetChanged(datas);

			}
		});
		L.e("数据"+datas.toString());
		calendarView.setMarkDates(datas);
	}
	public void getDate(String str){
		dataexclusiveConsult.clear();
 		dataselfCreate.clear();
		datatopThree.clear();
		datamedicalConsult.clear();
		Loading.bulid(this, null).show();
		RequestManager.getObject(String.format(Constens.GET_CUSTOM_RICHEN_DAY,str), this, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				Loading.bulid(CalendarActivity.this, null).dismiss();
				L.e(jsonObject.toString());

				try {
					JSONArray exclusiveConsult = jsonObject.getJSONArray("exclusiveConsult");
					JSONArray medicalConsult = jsonObject.getJSONArray("medicalConsult");
					JSONArray selfCreate = jsonObject.getJSONArray("selfCreate");
					JSONObject topThree = jsonObject.getJSONObject("topThree");
					if(exclusiveConsult.length()>0){
						layout_1.setVisibility(View.VISIBLE);
					}else{
						layout_1.setVisibility(View.GONE);
					}
					if(medicalConsult.length()>0){
						layout_4.setVisibility(View.VISIBLE);
					}else{
						layout_4.setVisibility(View.GONE);
					}
					if(selfCreate.length()>0){
						layout_2.setVisibility(View.VISIBLE);
					}else{
						layout_2.setVisibility(View.GONE);
					}
					for(int i = 0 ; i< exclusiveConsult.length();i++){
						Map<String,Object> stringObjectMap = new HashMap<String, Object>();
						JSONObject object = exclusiveConsult.getJSONObject(i);
						stringObjectMap.put("content",object.getString("content"));
						dataexclusiveConsult.add(stringObjectMap);
					}
					for(int i = 0 ; i< medicalConsult.length();i++){
						Map<String,Object> stringObjectMap = new HashMap<String, Object>();
						JSONObject object = medicalConsult.getJSONObject(i);
						stringObjectMap.put("content",object.getString("content"));
						datamedicalConsult.add(stringObjectMap);
					}
					for(int i = 0 ; i< selfCreate.length();i++){
						Map<String,Object> stringObjectMap = new HashMap<String, Object>();
						JSONObject object = selfCreate.getJSONObject(i);
						stringObjectMap.put("content",object.getString("content"));
						stringObjectMap.put("date",object.getString("date"));
						stringObjectMap.put("id",object.getString("id"));
						dataselfCreate.add(stringObjectMap);
					}
//					for(int i = 0 ; i< topThree.length();i++){
//						Map<String,Object> stringObjectMap = new HashMap<String, Object>();
//						JSONObject object = topThree.getJSONObject(i);
//						stringObjectMap.put("content",object.getString("content"));
//						datatopThree.add(stringObjectMap);
//					}
//					if(topThree!=null&&!topThree.equals("")){
//						JSONArray topThreearry = topThree.getJSONArray("value");
//					}
					if(topThree.toString().equals("{}")){
											layout_3.setVisibility(View.GONE);
					}else{
											layout_3.setVisibility(View.VISIBLE);
										}
					Iterator it = topThree.keys();
					while (it.hasNext()) {
						Map<String,Object> stringObjectMapall = new HashMap<String, Object>();
						String key = (String) it.next();
						String value = topThree.getString(key);
						stringObjectMapall.put("name",key);
						JSONArray array = topThree.getJSONArray(key);
						ArrayList<Map<String,Object>> datatopThree1 = new ArrayList<>();
						for(int i=0;i<array.length();i++){
							JSONObject jsonobject = array.getJSONObject(i);
							Map<String,Object> stringObjectMap = new HashMap<String, Object>();
							stringObjectMap.put("content",jsonobject.getString("content"));
							stringObjectMap.put("id",jsonobject.getString("id"));
							stringObjectMap.put("date",jsonobject.getString("date"));
							stringObjectMap.put("entityId",jsonobject.getString("entityId"));
							stringObjectMap.put("customerId",jsonobject.getString("customerId"));
							datatopThree1.add(stringObjectMap);
						}
						stringObjectMapall.put("content",datatopThree1);
						datatopThree.add(stringObjectMapall);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				exclusiveAdapter.notifyDataSetChanged();
				medicalAdapter.notifyDataSetChanged();
				selfAdapter.notifyDataSetChanged();
				topthreeAdapter.notifyDataSetChanged();

			}
		});
		}
	@OnClick({
			R.id.view_header_left_layout_return, //返回
			R.id.view_header_right   //编辑
	})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.view_header_left_layout_return:
				finish();
				break;
			case R.id.view_header_right:
				startActivityForResult(new Intent(activity, AddScheduleActivity.class), 0x100);
				break;
		}
	}
	public void addDate(String str,String str1){//add
		JSONObject param = new JSONObject();
		try {
			param.put("date",str);
			param.put("content",str1);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Loading.bulid(this, null).show();
		RequestManager.postObject(Constens.GET_CUSTOM_RICHEN_ADD,this,param,new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {
				Loading.bulid(CalendarActivity.this, null).dismiss();
				L.e(jsonObject.toString());


			}
		},new RequestErrorListener() {
			@Override
			public void requestError(VolleyError e) {
				Loading.bulid(activity, null).dismiss();
				T.show(UYIApplication.getContext(), ErrorCode.getErrorByNetworkResponse(e.networkResponse), -1);
				finish();
			}
		});
	}
	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public void onItemClick(int position, Map<String, Object> data) {
		showPop(position,data);
	}
	/**
	 * 弹出 popupwindow
	 */
	public void showPop(int position, final Map<String, Object> data) {
		View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout, null);
		TextView title = (TextView) mainView.findViewById(R.id.text_set_title);
		title.setVisibility(View.GONE);
		Button btnTakePhoto = (Button) mainView.findViewById(R.id.btn_take_photo);
		btnTakePhoto.setText("修改");
		btnTakePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSetPhotoPop.dismiss();
				Intent intent = new Intent();
				intent.setClass(activity,UpdateScheduleActivity.class);
				intent.putExtra("id",data.get("id").toString());
				intent.putExtra("date",data.get("date").toString());
				intent.putExtra("content",data.get("content").toString());
				startActivityForResult(intent, 0x100);
			}

		});
		Button btnCheckFromGallery = (Button) mainView.findViewById(R.id.btn_check_from_gallery);
		btnCheckFromGallery.setText("删除");
		btnCheckFromGallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				conform = new MessageConform(activity, MessageConform.MessageType.CONFORM);
				conform.setContent("确认要删除吗?");
				conform.setOnMessageClick(new MessageConform.OnMessageClick() {
					@Override
					public void onClick(MessageConform.Result result, Object object) {
						if (result == MessageConform.Result.OK) {
							RequestManager.deleteObject(Constens.ACCOUNT_SCHEDULE_DELETE + "/" + data.get("id"), activity, null, new Response.Listener<JSONObject>() {
								public void onResponse(JSONObject arg0) {
									getDate(time);
								}
							}, new RequestErrorListener() {

								@Override
								public void requestError(VolleyError e) {
									getDate(time);
								}
							});
						}
					}
				});
				conform.show();
				mSetPhotoPop.dismiss();

			}
		});
		Button btnCancle = (Button) mainView.findViewById(R.id.btn_cancel);
		btnCancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSetPhotoPop.dismiss();
			}
		});
		mSetPhotoPop = new PopupWindow(this);
		mSetPhotoPop.setBackgroundDrawable(new BitmapDrawable());
		mSetPhotoPop.setFocusable(true);
		mSetPhotoPop.setTouchable(true);
		mSetPhotoPop.setOutsideTouchable(true);
		mSetPhotoPop.setContentView(mainView);
		mSetPhotoPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		mSetPhotoPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		mSetPhotoPop.setAnimationStyle(R.style.bottomStyle);
		mSetPhotoPop.showAtLocation(updata_user_main, Gravity.BOTTOM, 0, 0);
		mSetPhotoPop.update();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0x100 && resultCode == RESULT_OK) {
			getDate(time);
		}
	}
	public  void choseOne(int position, Map<String, Object> data){

		showPop2(position,data);

	}
	public void showPop2(int position, final Map<String, Object> data) {
		View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout3, null);
		Button xiugai = (Button) mainView.findViewById(R.id.btn_take_xiugai);
		xiugai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSetPhotoPop.dismiss();
				Intent intent = new Intent();
				L.e("data",data.toString());
				intent.setClass(activity,UpdateCalendarActivity.class);
				intent.putExtra("id",data.get("id").toString());
				intent.putExtra("entityId",data.get("entityId").toString());
				intent.putExtra("customerId",data.get("customerId").toString());
				intent.putExtra("date",data.get("date").toString());
				intent.putExtra("content",data.get("content").toString());
				startActivityForResult(intent, 0x100);
			}

		});
		Button btnTakePhoto = (Button) mainView.findViewById(R.id.btn_take_photo);
		btnTakePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				conform = new MessageConform(activity, MessageConform.MessageType.CONFORM);
				conform.setContent("确认要延期吗?");
				conform.setOnMessageClick(new MessageConform.OnMessageClick() {
					@Override
					public void onClick(MessageConform.Result result, Object object) {
						if (result == MessageConform.Result.OK) {
							RequestManager.deleteObject(Constens.ACCOUNT_SCHEDULE_YANQI + "/" + data.get("entityId"), activity, null, new Response.Listener<JSONObject>() {
								public void onResponse(JSONObject arg0) {
									T.showShort(CalendarActivity.this,"延期成功！");
									getDate(time);
								}
							}, new RequestErrorListener() {

								@Override
								public void requestError(VolleyError e) {
									getDate(time);
								}
							});
						}
					}
				});

				conform.show();
				mSetPhotoPop.dismiss();
			}

		});
		Button btnCheckFromGallery = (Button) mainView.findViewById(R.id.btn_check_from_gallery);
		btnCheckFromGallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				mSetPhotoPop.dismiss();
				Intent intent = new Intent();
				L.e("data",data.toString());
				intent.putExtra("customerId",data.get("customerId").toString());
				intent.setClass(activity,AddCalendarActivity.class);
				startActivityForResult(intent, 0x100);

			}
		});
		Button btnCancle = (Button) mainView.findViewById(R.id.btn_cancel);
		btnCancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSetPhotoPop.dismiss();
			}
		});
		mSetPhotoPop = new PopupWindow(this);
		mSetPhotoPop.setBackgroundDrawable(new BitmapDrawable());
		mSetPhotoPop.setFocusable(true);
		mSetPhotoPop.setTouchable(true);
		mSetPhotoPop.setOutsideTouchable(true);
		mSetPhotoPop.setContentView(mainView);
		mSetPhotoPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		mSetPhotoPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		mSetPhotoPop.setAnimationStyle(R.style.bottomStyle);
		mSetPhotoPop.showAtLocation(updata_user_main, Gravity.BOTTOM, 0, 0);
		mSetPhotoPop.update();
	}
}

