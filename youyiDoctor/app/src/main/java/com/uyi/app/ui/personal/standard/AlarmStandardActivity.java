package com.uyi.app.ui.personal.standard;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * 健康标准
 * @author user
 *
 */
@ContentView(R.layout.alarm_standard)
public class AlarmStandardActivity extends BaseActivity {

@ViewInject(R.id.headerView) private HeaderView headerView;
	
@ViewInject(R.id.sousuoya_left) private EditText sousuoya_left;
	@ViewInject(R.id.sousuoya_right) private EditText sousuoya_right;
	@ViewInject(R.id.shuzhangya_left) private EditText shuzhangya_left;
	@ViewInject(R.id.shuzhangya_dayu) private EditText shuzhangya_right;
	@ViewInject(R.id.maibo_left) private EditText maibo_left;
	@ViewInject(R.id.maibo_right) private EditText maibo_right;
	
	@ViewInject(R.id.kongfuxuetang_left) private EditText kongfuxuetang_left;
	@ViewInject(R.id.kongfuxuetang_right) private EditText kongfuxuetang_right;
	
	@ViewInject(R.id.chanying2xiaoshi_left) private EditText chanying2xiaoshi_left;
	@ViewInject(R.id.chanying2xiaoshi_right) private EditText chanying2xiaoshi_right;
	
	@ViewInject(R.id.zongduanguchun) private EditText zongduanguchun;
	@ViewInject(R.id.ganyoushanzhi) private EditText ganyoushanzhi;
	@ViewInject(R.id.dimiduzhidanbai) private EditText dimiduzhidanbai;
	@ViewInject(R.id.gaomiduzhidanbai) private EditText gaomiduzhidanbai;
	@ViewInject(R.id.baohedu) private EditText baohedu;
	
	@ViewInject(R.id.jinxixinglv_left) private EditText jinxixinglv_left;
	@ViewInject(R.id.jinxixinglv_right) private EditText jinxixinglv_right;
	
	@ViewInject(R.id.niaosuan) private EditText niaosuan; 
	
	@ViewInject(R.id.health_database_add_submit) private Button health_database_add_submit;
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("报警标准").setTitleColor(getResources().getColor(R.color.blue));
		RequestManager.getObject(Constens.DOCTOR_HEALTH_WARNING_DATA, activity, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject data) {
				L.d(TAG, data.toString());
				sousuoya_left.setText(JSONObjectUtils.getInt(data, "spLow")+"");
				sousuoya_right.setText(JSONObjectUtils.getInt(data, "spHigh")+"");
				shuzhangya_left.setText(JSONObjectUtils.getInt(data, "dpLow")+"");
				try {
					shuzhangya_right.setText(data.getInt("dpHigh")+"");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				maibo_left.setText(JSONObjectUtils.getInt(data, "pulseRateLow")+"");
				maibo_right.setText(JSONObjectUtils.getInt(data, "pulseRateHigh")+"");
				
				kongfuxuetang_left.setText(JSONObjectUtils.getDouble(data, "fbsLow")+"");
				kongfuxuetang_right.setText(JSONObjectUtils.getDouble(data, "fbsHigh")+"");
				
				chanying2xiaoshi_left.setText(JSONObjectUtils.getDouble(data, "pbsLow")+"");
				chanying2xiaoshi_right.setText(JSONObjectUtils.getInt(data, "pbsHigh")+"");
				
				jinxixinglv_left.setText(JSONObjectUtils.getInt(data, "hrLow")+"");
				jinxixinglv_right.setText(JSONObjectUtils.getInt(data, "hrHigh")+"");
				
				
				
				zongduanguchun.setText(JSONObjectUtils.getDouble(data, "bfChol")+"");
				ganyoushanzhi.setText(JSONObjectUtils.getDouble(data, "bfTg")+"");
				dimiduzhidanbai.setText(JSONObjectUtils.getDouble(data, "bfLdl")+"");
				gaomiduzhidanbai.setText(JSONObjectUtils.getDouble(data, "bfHdl")+"");
				baohedu.setText(JSONObjectUtils.getInt(data, "spo")+"");
				
				niaosuan.setText(JSONObjectUtils.getInt(data, "uaThreshold")+"");
				
			}
		});
	}
	
	
	
	@OnClick(R.id.health_database_add_submit)
	public void click(View view){
		try {
		JSONObject params = new JSONObject();
			if(!ValidationUtils.isNull(sousuoya_left.getText().toString())){
				params.put("spLow", sousuoya_left.getText().toString());
			}
			if(!ValidationUtils.isNull(sousuoya_right.getText().toString())){
				params.put("spHigh", sousuoya_right.getText().toString());
			}
			
			
			if(params.has("spLow") && params.has("spHigh")){//
				if(Integer.parseInt(params.getString("spHigh")) <= Integer.parseInt(params.getString("spLow"))){
					T.showShort(activity, "收缩压最低值不能大于或者等于最高值");
					return;
				}
			}
			
			
			if(!ValidationUtils.isNull(shuzhangya_left.getText().toString())){
				params.put("dpLow", shuzhangya_left.getText().toString());
			}
			if(!ValidationUtils.isNull(shuzhangya_right.getText().toString())){
				params.put("dpHigh", shuzhangya_right.getText().toString());
			}
			
			if(params.has("dpLow") && params.has("dpHigh")){//
				if(Integer.parseInt(params.getString("dpHigh")) <= Integer.parseInt(params.getString("dpLow"))){
					T.showShort(activity, "舒张压最低值不能大于或者等于最高值");
					return;
				}
			}
			
			//*
			if(ValidationUtils.isNull(kongfuxuetang_left.getText().toString())){
				params.put("fbsLow", kongfuxuetang_left.getText().toString());
			}
			
			//*
			if(!ValidationUtils.isNull(kongfuxuetang_right.getText().toString())){
				params.put("fbsHigh", kongfuxuetang_right.getText().toString());
			}
			//*
			if(!ValidationUtils.isNull(chanying2xiaoshi_left.getText().toString())){
				params.put("pbsLow", chanying2xiaoshi_left.getText().toString());
			}
			//*
			if(!ValidationUtils.isNull(chanying2xiaoshi_right.getText().toString())){
				params.put("pbsHigh", chanying2xiaoshi_right.getText().toString());
			}
			
			if(!ValidationUtils.isNull(jinxixinglv_left.getText().toString())){
				params.put("hrLow", jinxixinglv_left.getText().toString());
			}
			if(!ValidationUtils.isNull(jinxixinglv_right.getText().toString())){
				params.put("hrHigh", jinxixinglv_right.getText().toString());
			}
			if(!ValidationUtils.isNull(niaosuan.getText().toString())){
				params.put("uaThreshold", niaosuan.getText().toString());
			}
			
			
			if(!ValidationUtils.isNull(zongduanguchun.getText().toString())){
				params.put("bfChol", zongduanguchun.getText().toString());
			}
			if(!ValidationUtils.isNull(ganyoushanzhi.getText().toString())){
				params.put("bfTg", ganyoushanzhi.getText().toString());
			}
			if(!ValidationUtils.isNull(dimiduzhidanbai.getText().toString())){
				params.put("bfLdl", dimiduzhidanbai.getText().toString());
			}
			if(!ValidationUtils.isNull(gaomiduzhidanbai.getText().toString())){
				params.put("bfHdl", gaomiduzhidanbai.getText().toString());
			}
			
			if(!ValidationUtils.isNull(maibo_left.getText().toString())){
				params.put("pulseRateLow", maibo_left.getText().toString());
			}
			
			if(!ValidationUtils.isNull(maibo_right.getText().toString())){
				params.put("pulseRateHigh", maibo_right.getText().toString());
			}
			
			if(!ValidationUtils.isNull(baohedu.getText().toString())){
				params.put("spo", baohedu.getText().toString());
			}
			
			
			RequestManager.postObject(Constens.DOCTOR_HEALTH_WARNING_DATA, activity, params, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					T.showShort(activity, "修改成功!");
				}
			}, new RequestErrorListener() {
				public void requestError(VolleyError e) {
					if(e.networkResponse != null){
						T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
					}else{
						T.showShort(activity, "修改成功!");
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
