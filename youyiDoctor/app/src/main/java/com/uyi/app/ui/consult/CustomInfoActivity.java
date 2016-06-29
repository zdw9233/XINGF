package com.uyi.app.ui.consult;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.UYIUtils;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 用户资料
 * @author user
 *
 */
@ContentView(R.layout.custom_info)
public class CustomInfoActivity extends BaseActivity {
	
	@ViewInject(R.id.schedule_add_close) private ImageView schedule_add_close; 
	
	
	@ViewInject(R.id.constom_info_logo) private RoundedImageView constom_info_logo; 
	@ViewInject(R.id.constom_info_name) private TextView constom_info_name; 
	@ViewInject(R.id.constom_info_gender) private TextView constom_info_gender; 
	@ViewInject(R.id.constom_info_chushengriqi) private TextView constom_info_chushengriqi; 
	
	@ViewInject(R.id.custom_jiankang) private TextView custom_jiankang; 
	@ViewInject(R.id.custom_gereng) private TextView custom_gereng; 
	
	@ViewInject(R.id.custom_jiankang_info) private LinearLayout custom_jiankang_info; //健康资料
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
	
	@ViewInject(R.id.custom_yuejing_layout) private LinearLayout custom_yuejing_layout; //月经史
	@ViewInject(R.id.custom_huaiyun_liuchan_layout) private LinearLayout custom_huaiyun_liuchan_layout; //怀孕史
	
	@ViewInject(R.id.custom_gerent_info) private LinearLayout custom_gerent_info; //个人资料
	@ViewInject(R.id.custom_city) private TextView custom_city; 
	@ViewInject(R.id.custom_address) private TextView custom_address; 
	@ViewInject(R.id.custom_phone) private TextView custom_phone; 
	@ViewInject(R.id.custom_mobile) private TextView custom_mobile; 
	@ViewInject(R.id.custom_email) private TextView custom_email; 
	@ViewInject(R.id.custom_zhiye) private TextView custom_zhiye; 
	@ViewInject(R.id.custom_shengao) private TextView custom_shengao; 
	@ViewInject(R.id.custom_tizhong) private TextView custom_tizhong; 
	
	private int customId;
	
	
	@OnClick({R.id.custom_jiankang,R.id.custom_gereng,R.id.schedule_add_close})
	public void click(View v){
		if(v.getId() == R.id.custom_jiankang){
			custom_jiankang_info.setVisibility(View.VISIBLE);
			custom_gerent_info.setVisibility(View.GONE);
			
			custom_jiankang.setBackgroundColor(getResources().getColor(R.color.blue));
			custom_jiankang.setTextColor(getResources().getColor(R.color.white));
			custom_gereng.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_gereng.setTextColor(getResources().getColor(R.color.list_item_content_color));
			
		}else if(v.getId() == R.id.custom_gereng){
			custom_jiankang_info.setVisibility(View.GONE);
			custom_gerent_info.setVisibility(View.VISIBLE);
			
			custom_jiankang.setBackgroundColor(getResources().getColor(R.color.content_background));
			custom_jiankang.setTextColor(getResources().getColor(R.color.list_item_content_color));
			custom_gereng.setBackgroundColor(getResources().getColor(R.color.blue));
			custom_gereng.setTextColor(getResources().getColor(R.color.white));
		}else if(v.getId() == R.id.schedule_add_close){
			onBackPressed();
		}
	}
	

	@Override
	protected void onInitLayoutAfter() {
		customId = getIntent().getIntExtra("customerId", 0);
		//个人资料
		RequestManager.getObject(String.format(Constens.DOCTOR_QUERY_CUSTOMER_INFO, customId), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					L.d(TAG, data.toString());
					JSONObject city = data.getJSONObject("city");
					custom_city.setText(JSONObjectUtils.getString(city, "name"));
					custom_address.setText(JSONObjectUtils.getString(data, "address"));
					custom_mobile.setText(JSONObjectUtils.getString(data, "phoneNumber"));
					custom_phone.setText(JSONObjectUtils.getString(data, "backupPhoneNumber"));
					custom_email.setText(JSONObjectUtils.getString(data, "email"));
					custom_zhiye.setText(JSONObjectUtils.getString(data, "occupation"));
					custom_shengao.setText(JSONObjectUtils.getInt(data, "height")+"CM");
					custom_tizhong.setText(JSONObjectUtils.getInt(data, "weight")+"KG");
					
					
					if(ValidationUtils.equlse(UYIUtils.convertGender(data.getString("gender")), "男")){
						custom_yuejing_layout.setVisibility(View.GONE);
						custom_huaiyun_liuchan_layout.setVisibility(View.GONE);
					}
					
					ImageCacheManager.loadImage(data.getString("icon"), ImageCacheManager.getImageListener(constom_info_logo, null, null));
					constom_info_gender.setText("性别："+UYIUtils.convertGender(data.getString("gender")));
					constom_info_chushengriqi.setText("出生日期："+data.getString("birthday"));
					constom_info_name.setText( data.getString("realName"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//健康资料
		RequestManager.getObject(String.format(Constens.DOCTOR_QUERY_CUSTOMER_HEALTH_INFO, customId), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					custom_jiwangbinshi.setText(JSONObjectUtils.getString(data, "medical"));
					custom_chuanranbinshi.setText(JSONObjectUtils.getString(data, "infection"));
					custom_waishangshi.setText(JSONObjectUtils.getString(data, "trauma"));
					custom_shoushushi.setText(JSONObjectUtils.getString(data, "operation"));
					custom_huaiyun_liuchan.setText(JSONObjectUtils.getString(data, "pregnancy"));
					custom_yuejing.setText(JSONObjectUtils.getString(data, "menstruation"));
					custom_shiwuguoming.setText(JSONObjectUtils.getString(data, "allergic"));
					custom_shuxueshi.setText(JSONObjectUtils.getString(data, "blood"));
					custom_jiazhubingshi.setText(JSONObjectUtils.getString(data, "familyMedical"));
					custom_muqianfuyao.setText(JSONObjectUtils.getString(data, "current"));
					custom_qitabuchong.setText(JSONObjectUtils.getString(data, "others"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		custom_jiankang.performClick();
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {

	}

}
