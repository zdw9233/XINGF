package com.uyi.app.ui.personal.discuss;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.consult.ConsultDetailsActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * 讨论组详情
 * @author user
 *
 */
@ContentView(R.layout.discuss_group_details)
public class DiscussGroupDetailsActivity extends BaseActivity {

	@ViewInject(R.id.headerView) private HeaderView headerView; 
	
	@ViewInject(R.id.discuss_group_details_query) private Button discuss_group_details_query; 
	
	@ViewInject(R.id.discuss_group_details_consult_layout) private LinearLayout discuss_group_details_consult_layout;
	
	@ViewInject(R.id.discuss_group_details_caiyong) private Button discuss_group_details_caiyong; 
	@ViewInject(R.id.doctor_logo) private RoundedImageView doctor_logo; 
	
	@ViewInject(R.id.discuss_group_details_yijian) private EditText discuss_group_details_yijian; 
	@ViewInject(R.id.discuss_group_details_fabiao) private Button discuss_group_details_fabiao; 
	
	
	
	private int consultId ;
	private Boolean isMyConsult ;//是否为当前专家   是否可以选择
	private int status;//状态
	
	Map<Integer,Integer> used  = new HashMap<Integer,Integer>();//采用选择的意见
	
	private UserInfo userInfo;
	
	@Override
	protected void onInitLayoutAfter() {
		userInfo = UserInfoManager.getLoginUserInfo(activity);
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("讨论组详情").setTitleColor(getResources().getColor(R.color.blue));
		consultId = getIntent().getIntExtra("id", 0);
		isMyConsult = getIntent().getBooleanExtra("isMyConsult", false);
		status = getIntent().getIntExtra("status", 0);
		Loading.bulid(activity, null).show();
		ImageCacheManager.loadImage(UserInfoManager.getLoginUserInfo(activity).icon, ImageCacheManager.getImageListener(doctor_logo, null, null));
		loadData();
	}
	
	
	public void loadData(){
		used.clear();
		discuss_group_details_consult_layout.removeAllViews();
		RequestManager.getObject(String.format(Constens.HEALTH_CONSULT_DISCUSSES, consultId,pageNo,pageSize), activity, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject data) {
				try {
					Loading.bulid(activity, null).dismiss();
					JSONArray array = data.getJSONArray("results");
					for(int i = 0;i<array.length();i++){
						JSONObject object = array.getJSONObject(i);
						addConsultUserinfo(object);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public void addConsultUserinfo(JSONObject object){
		
		View view = LayoutInflater.from(activity).inflate(R.layout.consult_userinfo, discuss_group_details_consult_layout, false);
		LinearLayout consult_userinfo_left_layout = (LinearLayout)view.findViewById(R.id.consult_userinfo_left_layout);
		RoundedImageView consult_userinfo_logo = (RoundedImageView)view.findViewById(R.id.consult_userinfo_logo);
		TextView consult_userinfo_name = (TextView)view.findViewById(R.id.consult_userinfo_name);
		TextView consult_userinfo_yicaiyong = (TextView)view.findViewById(R.id.consult_userinfo_yicaiyong);
		LinearLayout consult_userinfo_selected_layout = (LinearLayout)view.findViewById(R.id.consult_userinfo_selected_layout);
	    final ImageView consult_userinfo_selected = (ImageView)view.findViewById(R.id.consult_userinfo_selected);
		
		TextView consult_userinfo_time = (TextView)view.findViewById(R.id.consult_userinfo_time);
		TextView consult_userinfo_content = (TextView)view.findViewById(R.id.consult_userinfo_content);
		try {
			consult_userinfo_time.setText("发起时间:"+object.getString("updateTime"));
			consult_userinfo_content.setText( object.getString("advice"));
			JSONObject doctor = object.getJSONObject("doctor");
			consult_userinfo_name.setText(doctor.getString("realName"));
			ImageCacheManager.loadImage(doctor.getString("icon"), ImageCacheManager.getImageListener(consult_userinfo_logo, null, null));
			consult_userinfo_selected_layout.setVisibility(View.GONE);
			consult_userinfo_yicaiyong.setVisibility(View.GONE); 
			if(object.getBoolean("isUsed")){//是否已采用该意见 
					consult_userinfo_yicaiyong.setVisibility(View.VISIBLE);
			}else{
				//if(isMyConsult && (status == 3  || status == 4) && doctor.getInt("id") != userInfo.userId){//可以操作
				if(isMyConsult && doctor.getInt("id") != userInfo.userId){//可以操作
					discuss_group_details_caiyong.setVisibility(View.VISIBLE);
					consult_userinfo_selected_layout.setVisibility(View.VISIBLE);
					consult_userinfo_left_layout.setTag(object.getInt("id"));
					consult_userinfo_left_layout.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							int id = (int) v.getTag();
							if(used.containsKey(id)){
								used.remove(id);
								consult_userinfo_selected.setBackgroundResource(R.drawable.checknormal);
							}else{
								used.put(id, id);
								consult_userinfo_selected.setBackgroundResource(R.drawable.checkright);
							}
						}
					});
				}
			}
			discuss_group_details_consult_layout.addView(view);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@OnClick({R.id.discuss_group_details_query,R.id.discuss_group_details_fabiao,R.id.discuss_group_details_caiyong})
	public void click(View v){
		if(v.getId() == R.id.discuss_group_details_query){
			Intent intent = new Intent(activity, ConsultDetailsActivity.class);
			intent.putExtra("id", consultId);
			startActivity(intent);
		}else if(v.getId() == R.id.discuss_group_details_caiyong){
			if(used.size() > 0 ){
				try {
					JSONArray array = new JSONArray();
					for(Map.Entry<Integer, Integer> map : used.entrySet()){
						array.put(map.getKey());
					}
					Loading.bulid(activity, null).show();
					RequestManager.requestString(Method.POST, String.format(Constens.HEALTH_CONSULT_USE_DISCUSS, consultId), activity, array.toString(), new Listener<String>() {
						public void onResponse(String arg0) {
							Loading.bulid(activity, null).dismiss();
								loadData();
						}
					}, new RequestErrorListener() {
						public void requestError(VolleyError e) {
							Loading.bulid(activity, null).dismiss();
							if(e.networkResponse == null){
								loadData();
							}else{
								T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
							}
						}
					});
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			
			
			
		}else if(v.getId() == R.id.discuss_group_details_fabiao){
			String content = discuss_group_details_yijian.getText().toString();
			if(!ValidationUtils.isNull(content)){
				discuss_group_details_yijian.setText("");
				JSONObject params = new JSONObject();
				try {
					params.put("advice", content);
					Loading.bulid(activity, null).show();
					RequestManager.postObject(String.format(Constens.HEALTH_CONSULT_DISCUSS, consultId), activity, params, null, new RequestErrorListener() {
						public void requestError(VolleyError e) {
							Loading.bulid(activity, null).dismiss();
							if(e.networkResponse == null){
								loadData();
							}else{
								T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
							}
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
