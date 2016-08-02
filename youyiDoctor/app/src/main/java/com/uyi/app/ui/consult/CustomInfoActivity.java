package com.uyi.app.ui.consult;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.consult.model.AbnormalEvent;
import com.uyi.app.ui.consult.model.Medicine;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.health.adapter.JiuyiAdapter;
import com.uyi.app.ui.health.adapter.YaowuAdapter;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.UYIUtils;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户资料
 * @author user
 *
 */
@ContentView(R.layout.custom_info)
public class CustomInfoActivity extends BaseActivity {
	
	@ViewInject(R.id.schedule_add_close) private ImageView schedule_add_close;
	@ViewInject(R.id.recyclerView_healthmanager_jiuyi)
	private EndlessRecyclerView recyclerView_healthmanager_jiuyi;
	@ViewInject(R.id.recyclerView_healthmanager_yaowu)
	private EndlessRecyclerView recyclerView_healthmanager_yaowu;
	
	@ViewInject(R.id.constom_info_logo) private RoundedImageView constom_info_logo; 
	@ViewInject(R.id.constom_info_name) private TextView constom_info_name;
	@ViewInject(R.id.docter) private TextView docter;
	@ViewInject(R.id.guaridon) private TextView guaridon;
	@ViewInject(R.id.service_pakege) private TextView service_pakege;
	@ViewInject(R.id.constom_info_gender) private TextView constom_info_gender; 
	@ViewInject(R.id.constom_info_chushengriqi) private TextView constom_info_chushengriqi; 
	
	@ViewInject(R.id.custom_jiankang) private TextView custom_jiankang; 
	@ViewInject(R.id.custom_gereng) private TextView custom_gereng;
	@ViewInject(R.id.custom_jiuyi) private TextView custom_jiuyi;
	@ViewInject(R.id.custom_yaowu) private TextView custom_yaowu;
	@ViewInject(R.id.custom_xueguan) private TextView custom_xueguan;


	@ViewInject(R.id.custom_jiankang_info) private LinearLayout custom_jiankang_info; //健康资料
	@ViewInject(R.id.custom_zhongdu) private TextView custom_zhongdu;
	@ViewInject(R.id.custom_xitonghuigu) private TextView custom_xitonghuigu;
	@ViewInject(R.id.custom_yufangjiezhonshi) private TextView custom_yufangjiezhonshi;
	@ViewInject(R.id.custom_jiwangbinshi) private TextView custom_jiwangbinshi; 
	@ViewInject(R.id.custom_chuanranbinshi) private TextView custom_chuanranbinshi; 
	@ViewInject(R.id.custom_waishangshi) private TextView custom_waishangshi; 
	@ViewInject(R.id.custom_shoushushi) private TextView custom_shoushushi; 
	@ViewInject(R.id.custom_huaiyun_liuchan) private TextView custom_huaiyun_liuchan; 
	@ViewInject(R.id.custom_yuejing) private TextView custom_yuejing; 
	@ViewInject(R.id.custom_shiwuguoming) private TextView custom_shiwuguoming; 
	@ViewInject(R.id.custom_shuxueshi) private TextView custom_shuxueshi; 
	@ViewInject(R.id.custom_jiazhubingshi) private TextView custom_jiazhubingshi; 
	@ViewInject(R.id.custom_muqianfuyao) private TextView custom_muqianfuyao; 
	@ViewInject(R.id.custom_qitabuchong) private TextView custom_qitabuchong;
	@ViewInject(R.id.custom_qitabubingshi) private TextView custom_qitabubingshi;
	@ViewInject(R.id.custom_yuejing_layout) private LinearLayout custom_yuejing_layout; //月经史
	@ViewInject(R.id.custom_huaiyun_liuchan_layout) private LinearLayout custom_huaiyun_liuchan_layout; //怀孕史
	
	@ViewInject(R.id.custom_gerent_info) private LinearLayout custom_gerent_info; //个人资料
	@ViewInject(R.id.custom_city) private TextView custom_city; 
	@ViewInject(R.id.custom_address) private TextView custom_address; 
	@ViewInject(R.id.custom_phone) private TextView custom_phone; 
	@ViewInject(R.id.custom_mobile) private TextView custom_mobile; 
	@ViewInject(R.id.custom_email) private TextView custom_email; 
	@ViewInject(R.id.custom_zhiye) private TextView custom_zhiye; 


	@ViewInject(R.id.custom_xueguan_layout) private LinearLayout custom_xueguan_layout; //血管事件
	@ViewInject(R.id.custom_xueguan_time) private TextView custom_xueguan_time;
	@ViewInject(R.id.custom_xueguan_neixin) private TextView custom_xueguan_neixin;
	@ViewInject(R.id.custom_xueguan_miaoshu) private TextView custom_xueguan_miaoshu;

	@ViewInject(R.id.custom_yaowu_layout) private LinearLayout custom_yaowu_layout; //药物情况
	@ViewInject(R.id.custom_yaowu_guomin) private TextView custom_yaowu_guomin;
	@ViewInject(R.id.custom_yaowu_ying) private TextView custom_yaowu_ying;

	@ViewInject(R.id.custom_jiuyi_layout) private LinearLayout custom_jiuyi_layout; //基本情况
	@ViewInject(R.id.custom_shengao) private TextView custom_shengao;
	@ViewInject(R.id.custom_tizhong) private TextView custom_tizhong;
	@ViewInject(R.id.custom_jibing) private TextView jibing;
	@ViewInject(R.id.custom_jiankanzhuangkuang) private TextView jiankanzhuangk;

	private HashMap<String, Medicine> medicines = new HashMap<>();
	private HashMap<String, AbnormalEvent> abnormalEvents = new HashMap<>();

	private int customId;
	private ArrayList<Map<String, Object>> datajiuyi = new ArrayList<Map<String, Object>>();
	private ArrayList<Map<String, Object>> datayaowu = new ArrayList<Map<String, Object>>();

	private YaowuAdapter healthyaowu;
	private JiuyiAdapter healthjiuyi;
	private LinearLayoutManager linearLayoutManager;
	private LinearLayoutManager linearLayoutManager2;
	@OnClick({R.id.custom_jiankang,R.id.custom_gereng,R.id.schedule_add_close,R.id.custom_jiuyi,R.id.custom_yaowu,R.id.custom_xueguan})
	public void click(View v){
		if(v.getId() == R.id.custom_jiankang){
			custom_jiankang_info.setVisibility(View.VISIBLE);
			custom_gerent_info.setVisibility(View.GONE);
			custom_jiuyi_layout.setVisibility(View.GONE);
			custom_yaowu_layout.setVisibility(View.GONE);
			custom_xueguan_layout.setVisibility(View.GONE);
			
			custom_jiankang.setBackgroundColor(getResources().getColor(R.color.blue));
			custom_jiankang.setTextColor(getResources().getColor(R.color.white));
			custom_gereng.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_gereng.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_jiuyi.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiuyi.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_yaowu.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_yaowu.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_xueguan.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_xueguan.setTextColor(getResources().getColor(R.color.list_item_content_color));
			
		}else if(v.getId() == R.id.custom_gereng){
			custom_jiankang_info.setVisibility(View.GONE);
			custom_gerent_info.setVisibility(View.VISIBLE);
			custom_jiuyi_layout.setVisibility(View.GONE);
			custom_yaowu_layout.setVisibility(View.GONE);
			custom_xueguan_layout.setVisibility(View.GONE);

			custom_jiankang.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiankang.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_gereng.setBackgroundColor(getResources().getColor(R.color.blue));
			custom_gereng.setTextColor(getResources().getColor(R.color.white));
			custom_jiuyi.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiuyi.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_yaowu.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_yaowu.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_xueguan.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_xueguan.setTextColor(getResources().getColor(R.color.list_item_content_color));

		}else if(v.getId() == R.id.custom_jiuyi){
			custom_jiankang_info.setVisibility(View.GONE);
			custom_gerent_info.setVisibility(View.GONE);
			custom_jiuyi_layout.setVisibility(View.VISIBLE);
			custom_yaowu_layout.setVisibility(View.GONE);
			custom_xueguan_layout.setVisibility(View.GONE);

			custom_jiankang.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiankang.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_gereng.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_gereng.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_jiuyi.setBackgroundColor(getResources().getColor(R.color.blue));
			custom_jiuyi.setTextColor(getResources().getColor(R.color.white));
			custom_yaowu.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_yaowu.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_xueguan.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_xueguan.setTextColor(getResources().getColor(R.color.list_item_content_color));

		}else if(v.getId() == R.id.custom_yaowu){
			custom_jiankang_info.setVisibility(View.GONE);
			custom_gerent_info.setVisibility(View.GONE);
			custom_jiuyi_layout.setVisibility(View.GONE);
			custom_yaowu_layout.setVisibility(View.VISIBLE);
			custom_xueguan_layout.setVisibility(View.GONE);

			custom_jiankang.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiankang.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_gereng.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_gereng.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_jiuyi.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiuyi.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_yaowu.setBackgroundColor(getResources().getColor(R.color.blue));
			custom_yaowu.setTextColor(getResources().getColor(R.color.white));
			custom_xueguan.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_xueguan.setTextColor(getResources().getColor(R.color.list_item_content_color));

		}else if(v.getId() == R.id.custom_xueguan){
			custom_jiankang_info.setVisibility(View.GONE);
			custom_gerent_info.setVisibility(View.GONE);
			custom_jiuyi_layout.setVisibility(View.GONE);
			custom_yaowu_layout.setVisibility(View.GONE);
			custom_xueguan_layout.setVisibility(View.VISIBLE);

			custom_jiankang.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiankang.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_gereng.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_gereng.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_jiuyi.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiuyi.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_yaowu.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_yaowu.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_xueguan.setBackgroundColor(getResources().getColor(R.color.blue));
			custom_xueguan.setTextColor(getResources().getColor(R.color.white));
		}else if(v.getId() == R.id.schedule_add_close){
			onBackPressed();
		}
	}
	

	@Override
	protected void onInitLayoutAfter() {
		linearLayoutManager = new LinearLayoutManager(this);
		healthyaowu = new YaowuAdapter(this);
		healthyaowu.setDatas(datayaowu);
		healthjiuyi = new JiuyiAdapter(this);
		healthjiuyi.setDatas(datajiuyi);
		linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager2 = new LinearLayoutManager(this);
		recyclerView_healthmanager_jiuyi.setLayoutManager(linearLayoutManager);
		recyclerView_healthmanager_jiuyi.addItemDecoration(
				new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		recyclerView_healthmanager_jiuyi.setItemAnimator(new DefaultItemAnimator());
		recyclerView_healthmanager_jiuyi.setProgressView(R.layout.item_progress);
		recyclerView_healthmanager_jiuyi.setAdapter(healthjiuyi);
		recyclerView_healthmanager_yaowu.setLayoutManager(linearLayoutManager2);
		recyclerView_healthmanager_yaowu.addItemDecoration(
				new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		recyclerView_healthmanager_yaowu.setItemAnimator(new DefaultItemAnimator());
		recyclerView_healthmanager_yaowu.setProgressView(R.layout.item_progress);
		recyclerView_healthmanager_yaowu.setAdapter(healthyaowu);

		customId = getIntent().getIntExtra("customerId", 0);
		//个人资料
		RequestManager.getObject(String.format(Constens.DOCTOR_QUERY_CUSTOMER_INFO, customId), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					L.d(TAG, data.getJSONObject("healthInfo").toString());
					JSONObject city = data.getJSONObject("city");
					JSONObject province = data.getJSONObject("province");
					custom_city.setText(JSONObjectUtils.getString(province, "name")+JSONObjectUtils.getString(city, "name"));

					custom_address.setText(JSONObjectUtils.getString(data, "address").equals("null")?"无":JSONObjectUtils.getString(data, "address"));
					custom_mobile.setText(JSONObjectUtils.getString(data, "phoneNumber").equals("null")?"无":JSONObjectUtils.getString(data, "phoneNumber"));
					custom_phone.setText(JSONObjectUtils.getString(data, "backupPhoneNumber").equals("null")?"无":JSONObjectUtils.getString(data, "backupPhoneNumber"));
					custom_email.setText(JSONObjectUtils.getString(data, "email").equals("null")?"无":JSONObjectUtils.getString(data, "email"));
					custom_zhiye.setText(JSONObjectUtils.getString(data, "occupation").equals("null")?"无":JSONObjectUtils.getString(data, "occupation"));
//					if(data.getString("abnormalEventJsons").equals("null")){
//						custom_xueguan_time.setText("");	custom_xueguan_neixin.setText("");	custom_xueguan_miaoshu.setText("");
//					}else{



//					}
					if(!data.getString("height").equals("null")){
						custom_shengao.setText(JSONObjectUtils.getInt(data, "height")+"CM");
					}else{
						custom_shengao.setText("未填写");
					}
					if(!data.getString("weight").equals("null")){
						custom_tizhong.setText(JSONObjectUtils.getInt(data, "weight")+"KG");
					}else{
						custom_tizhong.setText("未填写");
					}
					if (data.getJSONObject("serviceCount").equals("免费服务包")) {
						service_pakege.setText("免费服务包(永久)");
					} else {
						service_pakege.setText(data.getJSONObject("serviceCount").getString("name")+"("+data.getJSONObject("serviceCount").getString("endTime")+")");
					}
					if(data.getString("attendingDoctorName").equals("null"))
						docter.setText("无");
					else
						docter.setText(data.getString("attendingDoctorName"));
					if(ValidationUtils.equlse(UYIUtils.convertGender(data.getString("gender")), "男")){
						custom_yuejing_layout.setVisibility(View.GONE);
						custom_huaiyun_liuchan_layout.setVisibility(View.GONE);
					}
					if(data.getString("guardianInfo").equals("null")){
						guaridon.setText("无");
					}else{
						guaridon.setText(data.getJSONObject("guardianInfo").getString("name"));
					}
					ImageCacheManager.loadImage(data.getString("icon"), ImageCacheManager.getImageListener(constom_info_logo, null, null));
					constom_info_gender.setText("性别："+UYIUtils.convertGender(data.getString("gender")));
					constom_info_chushengriqi.setText("出生日期："+data.getString("birthday"));
					constom_info_name.setText( data.getString("realName"));

					if(JSONObjectUtils.getInt(data, "chronicDiseaseType") == 1){
						jibing.setText("高血压");
					}else if(JSONObjectUtils.getInt(data, "chronicDiseaseType") == 2){
						jibing.setText("糖尿病");
					}else if(JSONObjectUtils.getInt(data, "chronicDiseaseType") == 3){
						jibing.setText("高血压兼糖尿病");
					}else{
						jibing.setText("无");
					}



					jiankanzhuangk.setText(JSONObjectUtils.getString(data, "healthCondition").equals("null")?"无":JSONObjectUtils.getString(data, "healthCondition"));
//					//						@ViewInject(R.id.custom_zhongdu) private TextView custom_zhongdu;
////						@ViewInject(R.id.custom_xitonghuigu) private TextView custom_xitonghuigu;
////						@ViewInject(R.id.custom_yufangjiezhonshi) private TextView custom_yufangjiezhonshi;
////						@ViewInject(R.id.custom_jibing) private TextView jibing;
////						@ViewInject(R.id.custom_jiankanzhuangkuang) private TextView jiankanzhuangk;
//
					if(data.has("healthInfo")){
						custom_qitabuchong.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "others").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "others"));
						custom_yufangjiezhonshi.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "vaccinationHistory").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "vaccinationHistory"));
						custom_shuxueshi.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "blood").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "blood"));
						custom_waishangshi.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "trauma").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "trauma"));
						custom_jiwangbinshi.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "medical").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "medical"));
						custom_chuanranbinshi.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "infection").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "infection"));

						custom_shoushushi.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "operation").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "operation"));
						custom_huaiyun_liuchan.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "pregnancy").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "pregnancy"));
						custom_yuejing.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "menstruation").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "menstruation"));
						custom_shiwuguoming.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "allergic").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "allergic"));

						custom_jiazhubingshi.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "familyMedical").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "familyMedical"));
						custom_muqianfuyao.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "current").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "current"));
						custom_qitabubingshi.setText(JSONObjectUtils.getString(data, "others").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "others"));
						custom_zhongdu.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "allergic").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "allergic"));
						custom_xitonghuigu.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "retrospection").equals("null")?"无":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "retrospection"));


						custom_yaowu_guomin.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "historyOfAllergy").equals("null")?"":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "historyOfAllergy"));
						custom_yaowu_ying.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "drugAddiction").equals("null")?"":JSONObjectUtils.getString(data.getJSONObject("healthInfo"), "drugAddiction"));
						JSONArray jiuyi = data.getJSONObject("healthInfo").getJSONArray("externalSituations");
						for (int i = 0; i < jiuyi.length(); i++) {
							Map<String, Object> item = new HashMap<String, Object>();
							JSONObject jsonObject = jiuyi.getJSONObject(i);

							item.put("content", jsonObject.getString("content"));
							item.put("treatmentTime", jsonObject.getString("treatmentTime"));

							datajiuyi.add(item);
						}
						healthjiuyi.notifyDataSetChanged();
						JSONArray yaowu = data.getJSONObject("healthInfo").getJSONArray("medicationUsingSituations");
						for (int i = 0; i < yaowu.length(); i++) {
							Map<String, Object> item = new HashMap<String, Object>();
							JSONObject jsonObject = yaowu.getJSONObject(i);

							item.put("medicineName", jsonObject.getString("medicineName"));
							item.put("startTime", jsonObject.getString("startTime"));
							item.put("endTime", jsonObject.getString("endTime"));
							item.put("usingFrequency", jsonObject.getString("usingFrequency"));
							item.put("frequencyUnit", jsonObject.getString("frequencyUnit"));
							item.put("singleDose", jsonObject.getString("singleDose"));
							item.put("medicineUnit", jsonObject.getString("medicineUnit"));

							datayaowu.add(item);
						}
						healthyaowu.notifyDataSetChanged();

						if(data.getJSONObject("healthInfo").has("abnormalEventJsons")) {
							if(data.getJSONObject("healthInfo").getJSONObject("abnormalEventJsons").has("time"))
								custom_xueguan_time.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo").getJSONObject("abnormalEventJsons"), "time"));
							if(data.getJSONObject("healthInfo").getJSONObject("abnormalEventJsons").has("name"))
								custom_xueguan_neixin.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo").getJSONObject("abnormalEventJsons"), "name"));
							if(data.getJSONObject("healthInfo").getJSONObject("abnormalEventJsons").has("description"))
								custom_xueguan_miaoshu.setText(JSONObjectUtils.getString(data.getJSONObject("healthInfo").getJSONObject("abnormalEventJsons"), "description"));
						}

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//健康资料
//		RequestManager.getObject(String.format(Constens.DOCTOR_QUERY_CUSTOMER_HEALTH_INFO, customId), activity, new Listener<JSONObject>() {
//			public void onResponse(JSONObject data) {
//				try {
//					custom_jiwangbinshi.setText(JSONObjectUtils.getString(data, "medical").equals("null")?"无":JSONObjectUtils.getString(data, "medical"));
//					custom_chuanranbinshi.setText(JSONObjectUtils.getString(data, "infection").equals("null")?"无":JSONObjectUtils.getString(data, "infection"));
//					custom_waishangshi.setText(JSONObjectUtils.getString(data, "trauma").equals("null")?"无":JSONObjectUtils.getString(data, "trauma"));
//					custom_shoushushi.setText(JSONObjectUtils.getString(data, "operation").equals("null")?"无":JSONObjectUtils.getString(data, "operation"));
//					custom_huaiyun_liuchan.setText(JSONObjectUtils.getString(data, "pregnancy").equals("null")?"无":JSONObjectUtils.getString(data, "pregnancy"));
//					custom_yuejing.setText(JSONObjectUtils.getString(data, "menstruation").equals("null")?"无":JSONObjectUtils.getString(data, "menstruation"));
//					custom_shiwuguoming.setText(JSONObjectUtils.getString(data, "allergic").equals("null")?"无":JSONObjectUtils.getString(data, "allergic"));
//					custom_shuxueshi.setText(JSONObjectUtils.getString(data, "blood").equals("null")?"无":JSONObjectUtils.getString(data, "blood"));
//					custom_jiazhubingshi.setText(JSONObjectUtils.getString(data, "familyMedical").equals("null")?"无":JSONObjectUtils.getString(data, "familyMedical"));
//					custom_muqianfuyao.setText(JSONObjectUtils.getString(data, "current").equals("null")?"无":JSONObjectUtils.getString(data, "current"));
//					custom_qitabuchong.setText(JSONObjectUtils.getString(data, "others").equals("null")?"无":JSONObjectUtils.getString(data, "others"));
//					custom_zhongdu.setText(JSONObjectUtils.getString(data, "allergic").equals("null")?"无":JSONObjectUtils.getString(data, "allergic"));
//					custom_xitonghuigu.setText(JSONObjectUtils.getString(data, "retrospection").equals("null")?"无":JSONObjectUtils.getString(data, "retrospection"));
//					custom_yufangjiezhonshi.setText(JSONObjectUtils.getString(data, "vaccinationHistory").equals("null")?"无":JSONObjectUtils.getString(data, "vaccinationHistory"));
//					jibing.setText(JSONObjectUtils.getString(data, "others").equals("null")?"无":JSONObjectUtils.getString(data, "others"));
//					custom_qitabuchong.setText(JSONObjectUtils.getString(data, "others").equals("null")?"无":JSONObjectUtils.getString(data, "others"));
//					//						@ViewInject(R.id.custom_zhongdu) private TextView custom_zhongdu;
////						@ViewInject(R.id.custom_xitonghuigu) private TextView custom_xitonghuigu;
////						@ViewInject(R.id.custom_yufangjiezhonshi) private TextView custom_yufangjiezhonshi;
////						@ViewInject(R.id.custom_jibing) private TextView jibing;
////						@ViewInject(R.id.custom_jiankanzhuangkuang) private TextView jiankanzhuangk;
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		custom_gereng.performClick();
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {

	}

}
