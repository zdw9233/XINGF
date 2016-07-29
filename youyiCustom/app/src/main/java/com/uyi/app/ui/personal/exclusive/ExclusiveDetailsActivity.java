package com.uyi.app.ui.personal.exclusive;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 专属咨询详情  
 * @author user
 *
 */
@ContentView(R.layout.exclusive_details)
public class ExclusiveDetailsActivity extends BaseActivity {
	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.exclusive_details_header) private RoundedImageView exclusive_details_header;
	@ViewInject(R.id.exclusive_details_name) private TextView exclusive_details_name;
	@ViewInject(R.id.exclusive_details_jieshao) private TextView exclusive_details_jieshao;
	@ViewInject(R.id.exclusive_details_dou) private TextView exclusive_details_dou;
	@ViewInject(R.id.exclusive_details_time) private TextView exclusive_details_time;
	@ViewInject(R.id.exclusive_details_address) private TextView exclusive_details_address;
	@ViewInject(R.id.exclusive_details_current_count) private TextView exclusive_details_current_count;
	@ViewInject(R.id.exclusive_details_max_count) private TextView exclusive_details_max_count;
	@ViewInject(R.id.exclusive_details_desc) private TextView exclusive_details_desc;
	
	@ViewInject(R.id.exclusive_details_yiyuyue) private TextView exclusive_details_yiyuyue;
	
	@ViewInject(R.id.exclusive_details_submit) private Button exclusive_details_submit;
	
	
	@ViewInject(R.id.exclusive_details_layout) private LinearLayout exclusive_details_layout;
	
	@ViewInject(R.id.exclusive_details_success_layout) private LinearLayout exclusive_details_success_layout;
	@ViewInject(R.id.exclusive_details_success_ric) private Button exclusive_details_success_ric;
	
	@ViewInject(R.id.exclusive_details_error_layout) private LinearLayout exclusive_details_error_layout;
	@ViewInject(R.id.exclusive_details_error_return) private Button exclusive_details_error_return;
	int beans;
	int needbeans;
	
	
	
	int id ;
	int type ;
	boolean manyuan;
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("专属咨询详情").setTitleColor(getResources().getColor(R.color.blue));
		id = getIntent().getIntExtra("id", 0);
		type = getIntent().getIntExtra("type", 0);
		exclusive_details_yiyuyue.setVisibility(View.GONE);
		exclusive_details_submit.setVisibility(View.GONE);
		beans = UserInfoManager.getLoginUserInfo(this).beans;
		Loading.bulid(activity, null).show();

		RequestManager.getObject(String.format(Constens.ACCOUNT_EXCLUSIVE_CONSULT, id), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					L.d(TAG, data.toString());
					Loading.bulid(activity, null).dismiss();
					exclusive_details_layout.setVisibility(View.VISIBLE);
//					exclusive_details_time.setText(  data.getString("start"));
					//开始时间 
					exclusive_details_time.setText( data.getString("end"));//结束时间
					int people = (int)data.get("people");
					int reserved = (int)data.get("reserved");
					manyuan = reserved >= people;//是否已经满员
					exclusive_details_max_count.setText( people+" "+(manyuan?"(已满员)":""));//人数
					exclusive_details_current_count.setText(reserved+"");//预约人数
					exclusive_details_dou.setText( data.getInt("beans")+"");//费用
					needbeans =  data.getInt("beans");
					exclusive_details_desc.setText( data.getString("desc"));
					exclusive_details_address.setText(  data.getString("address"));
	//				/**
	//				 *  0: 将要进行
	//					1: 删除
	//					2: 预约满员
	//					3: 结束
	//				 */
	//				item.put("status", data.getInt("status"));//专属咨询状态
					JSONObject doctor = data.getJSONObject("doctor"); 
					exclusive_details_jieshao.setText( JSONObjectUtils.getString(doctor, "info"));
					exclusive_details_name.setText( doctor.getString("name")); 
					ImageCacheManager.loadImage(doctor.getString("icon"), ImageCacheManager.getImageListener(exclusive_details_header, null, null));
					
					if(type == 2){
						if(data.getBoolean("isReserved")){
							exclusive_details_submit.setVisibility(View.GONE);
						}else{
							exclusive_details_submit.setVisibility(View.VISIBLE);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@OnClick({R.id.exclusive_details_submit,R.id.exclusive_details_success_ric,R.id.exclusive_details_error_return})
	public void click(View v){
		if(v.getId() == R.id.exclusive_details_submit){
			if(manyuan){
				exclusive_details_layout.setVisibility(View.GONE);
				exclusive_details_error_layout.setVisibility(View.VISIBLE);
				return;
			}
			if(beans < needbeans){
				T.showShort(ExclusiveDetailsActivity.this,"不符合条件！");
				return;
			}
			Loading.bulid(activity, null).show();
			JSONObject params = new JSONObject();
			RequestManager.postObject(String.format(Constens.ACCOUNT_RESERVE_EXCLUSIVE_CONSULT, id), activity,params, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					Loading.bulid(activity, null).dismiss();
					exclusive_details_layout.setVisibility(View.GONE);
					exclusive_details_success_layout.setVisibility(View.VISIBLE);
				}
			},new  RequestErrorListener() {
				public void requestError(VolleyError e) {
					Loading.bulid(activity, null).dismiss();
					exclusive_details_layout.setVisibility(View.GONE);
					exclusive_details_success_layout.setVisibility(View.VISIBLE);

				}
			});
		}else if(v.getId() == R.id.exclusive_details_success_ric){
//			startActivity(new Intent(activity, ScheduleActivity.class));
			finish();
//			onInitLayoutAfter();
		}else if(v.getId() == R.id.exclusive_details_error_return){
			onBackPressed();
		}
	}
	
	
	
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
