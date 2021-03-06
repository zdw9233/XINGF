package com.uyi.app.ui.health;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * 查看健康数据库详情
 * @author user
 *
 */
@ContentView(R.layout.health_database_detals)
public class HealthDatabaseDetails extends BaseActivity implements OnClickListener {
	
	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.health_database_details_check_date_layout) private FlowRadioGroup  health_database_details_check_date_layout;
	@ViewInject(R.id.health_database_details_xueya_layout) private FlowRadioGroup  health_database_details_xueya_layout;
	@ViewInject(R.id.health_database_details_xuetang_layout) private FlowRadioGroup  health_database_details_xuetang_layout;
	@ViewInject(R.id.health_database_details_xuezhi_layout) private FlowRadioGroup  health_database_details_xuezhi_layout;
	@ViewInject(R.id.health_database_details_xueyang_layout) private FlowRadioGroup  health_database_details_xueyang_layout;
	@ViewInject(R.id.health_database_details_qita_layout) private FlowRadioGroup  health_database_details_qita_layout;
	@ViewInject(R.id.health_database_details_jiangchaxiangmu) private TextView  health_database_details_jiangchaxiangmu;
	@ViewInject(R.id.health_database_details_jiangchabaogao_layout) private FlowRadioGroup  health_database_details_jiangchabaogao_layout;
	@ViewInject(R.id.health_database_details_xindiantu_layout) private FlowRadioGroup  health_database_details_xindiantu_layout;

	@ViewInject(R.id.health_database_details_jiangchabaogaorow_layout) private LinearLayout  health_database_details_jiangchabaogaorow_layout;
	@ViewInject(R.id.health_database_details_jiangchaxiangmu_layout) private LinearLayout  health_database_details_jiangchaxiangmu_layout;
	@ViewInject(R.id.health_database_details_xindianturow_layout) private LinearLayout  health_database_details_xindianturow_layout;
	
	@ViewInject(R.id.health_database_jiechubaojing) private Button  health_database_jiechubaojing;//解除报警
	LayoutInflater inflater  ;
	private String id;
	
	@Override
	protected void onInitLayoutAfter() {
		inflater  = LayoutInflater.from(activity);
		headerView.showTitle(true).showLeftReturn(true).showRight(false).setTitle("健康数据库详细资料").setTitleColor(getResources().getColor(R.color.blue));
		
		id = getIntent().getStringExtra("id");
		if(id == null){
			finish();
		}
		if(getIntent().hasExtra("isWarning")){
			if(getIntent().getBooleanExtra("isWarning", false)){
				health_database_jiechubaojing.setVisibility(View.VISIBLE);
			}
		}
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.HEALTH_CHECK_INFO,id), activity, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject data) {
				try {
					
					if(!data.getBoolean("isWarning")){
						health_database_jiechubaojing.setVisibility(View.GONE);
					}
					if(data.has("checkTime"))
						addView(health_database_details_check_date_layout, "体检时间:", data.getString("checkTime"), "","");
					//血压
					if(data.has("morningSystolicPressure"))
					addView(health_database_details_xueya_layout, "收缩(高)压:", data.getString("morningSystolicPressure"), "mm/Hg",JSONObjectUtils.getString(data, "morningSystolicPressureWarning"));
					if(data.has("morningDiastolicPressure"))
					addView(health_database_details_xueya_layout, "舒张(低)压:", data.getString("morningDiastolicPressure"), "mm/Hg",JSONObjectUtils.getString(data,"morningDiastolicPressureWarning"));
					if(data.has("pulseRate"))
					addView(health_database_details_xueya_layout, "脉搏:", data.getString("pulseRate"), "次/分", JSONObjectUtils.getString(data,"pulseRateWarning"));
				
					//血糖
					if(data.has("fastBloodSugar"))
					addView(health_database_details_xuetang_layout, "空腹血糖:", data.getString("fastBloodSugar"), "mmol/l", JSONObjectUtils.getString(data,"fastBloodSugarWarning"));
					if(data.has("postPrandilaSugar"))
					addView(health_database_details_xuetang_layout, "餐后2小时血糖:", data.getString("postPrandilaSugar"), "mmol/l", JSONObjectUtils.getString(data,"postPrandilaSugarWarning"));
					if(data.has("randomBloodSugar"))
					addView(health_database_details_xuetang_layout, "随机血糖:", data.getString("randomBloodSugar"), "mmol/l", JSONObjectUtils.getString(data,"randomBloodSugarWarning"));
					
					//血脂
					if(data.has("bloodFatChol"))
					addView(health_database_details_xuezhi_layout, "总胆固醇:", data.getString("bloodFatChol"), "mmol/l", JSONObjectUtils.getString(data,"bloodFatCholWarning"));
					if(data.has("bloodFatTg"))
					addView(health_database_details_xuezhi_layout, "甘油三酯:", data.getString("bloodFatTg"), "mmol/l", JSONObjectUtils.getString(data,"bloodFatTgWarning"));
					if(data.has("bloodFatLdl"))
					addView(health_database_details_xuezhi_layout, "低密度脂蛋白胆固醇:", data.getString("bloodFatLdl"), "mmol/l", JSONObjectUtils.getString(data,"bloodFatLdlWarning"));
					if(data.has("bloodFatHdl"))
					addView(health_database_details_xuezhi_layout, "高密度脂蛋白胆固醇:", data.getString("bloodFatHdl"), "mmol/l", JSONObjectUtils.getString(data,"bloodFatHdlWarning"));
					
					//学氧
					if(data.has("spo"))
					addView(health_database_details_xueyang_layout, "饱和度:", data.getString("spo"), "umol/l", JSONObjectUtils.getString(data,"spoWarning"));
					if(data.has("heartRate"))
					addView(health_database_details_xueyang_layout, "静息心率(脉率):", data.getString("heartRate"), "次/分", JSONObjectUtils.getString(data,"heartRateWarning"));
					
					
					//其他项目
					if(data.has("urineAcid"))
					addView(health_database_details_qita_layout, "尿酸:", data.getString("urineAcid"), "umol/l", JSONObjectUtils.getString(data,"urineAcidWarning"));
					
					health_database_details_jiangchaxiangmu.setText(JSONObjectUtils.getString(data,"checkItem"));
				
					if(!ValidationUtils.isNull(JSONObjectUtils.getString(data,"checkItem"))){
						health_database_details_jiangchaxiangmu_layout.setVisibility(View.VISIBLE);
					}
					
					//检查报告
					if(data.has("images")){
						health_database_details_jiangchabaogaorow_layout.setVisibility(View.VISIBLE);
						JSONArray array = data.getJSONArray("images");
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject = array.getJSONObject(i);
							View view = inflater.inflate(R.layout.health_database_details_image,health_database_details_jiangchabaogao_layout, false);
							ImageView imageView = (ImageView)view.findViewById(R.id.health_database_details_image_);
							String imageUrl = jsonObject.getString("url").replace("http://localhost:8080", Constens.SERVER_URL);
							health_database_details_jiangchabaogao_layout.addView(view);
							imageView.setTag(imageUrl);
							imageView.setOnClickListener(HealthDatabaseDetails.this);
							ImageCacheManager.loadImage(imageUrl, ImageCacheManager.getImageListener(imageView, null, null));
						}
					}
					
					//心电图
					if(data.has("ecg")){
						health_database_details_xindianturow_layout.setVisibility(View.VISIBLE);
						JSONArray array = data.getJSONArray("ecg");
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject = array.getJSONObject(i);
							View view = inflater.inflate(R.layout.health_database_details_image,health_database_details_xindiantu_layout, false);
							ImageView imageView = (ImageView)view.findViewById(R.id.health_database_details_image_);
							String imageUrl = jsonObject.getString("url").replace("http://localhost:8080", Constens.SERVER_URL);
							imageView.setTag(imageUrl);
							imageView.setOnClickListener(HealthDatabaseDetails.this);
							health_database_details_xindiantu_layout.addView(view);
							ImageCacheManager.loadImage(imageUrl, ImageCacheManager.getImageListener(imageView, null, null));
						}
					}
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Loading.bulid(activity, null).dismiss();
			}
		});
	}
	
	
	
	public void addView(FlowRadioGroup group ,String name,String value,String danwei,String tishi){
		View view = createDetailsItemView(health_database_details_xueya_layout);
		TextView health_database_detals_item_name = (TextView)view.findViewById(R.id.health_database_detals_item_name);
		TextView health_database_detals_item_value = (TextView)view.findViewById(R.id.health_database_detals_item_value);
		TextView health_database_detals_item_danwei = (TextView)view.findViewById(R.id.health_database_detals_item_danwei);
		TextView health_database_detals_item_execption = (TextView)view.findViewById(R.id.health_database_detals_item_execption);
		health_database_detals_item_name.setText(name);
		health_database_detals_item_value.setText(value);
		health_database_detals_item_danwei.setText(danwei);
		if(!ValidationUtils.isNull(tishi)){
			health_database_detals_item_execption.setVisibility(View.VISIBLE);
			health_database_detals_item_execption.setText(tishi);
		}
		group.addView(view);
	}
	
	public View createDetailsItemView(ViewGroup viewGroup){
		return inflater.inflate(R.layout.health_database_detals_item,viewGroup, false);
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	
	@OnClick(R.id.health_database_jiechubaojing)
	public void click(View view){
		Loading.bulid(activity, null).show();
		health_database_jiechubaojing.setVisibility(View.GONE);
		RequestManager.postObject(String.format(Constens.DOCTOR_CLEAR_HEALTH_WARNING_DATA, id), activity,null, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					Loading.bulid(activity, null).dismiss();
					T.showShort(activity, "解除报警成功!");
					setResult(RESULT_OK);
					finish();
				}	
			},new RequestErrorListener() {
				public void requestError(VolleyError e) {
					Loading.bulid(activity, null).dismiss();
					T.showShort(activity, "解除报警成功!");
					setResult(RESULT_OK);
					finish();
				}
			}
		);	
	}

	@Override
	public void onClick(View v) {
		String imageUrl = v.getTag().toString();
		Intent intent = new Intent(activity, ViewPagerImageActivity.class);
		intent.putExtra("imageUrl", new String[]{imageUrl});
		startActivity(intent);
	}

}
