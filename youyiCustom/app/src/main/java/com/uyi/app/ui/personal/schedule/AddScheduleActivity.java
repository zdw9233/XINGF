package com.uyi.app.ui.personal.schedule;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONObject;

import java.util.Date;


/**
 * 日程
 * @author user
 *
 */

@ContentView(R.layout.schedule_add)
public class AddScheduleActivity extends BaseActivity {
	
	@ViewInject(R.id.schedule_add_time) private TextView schedule_add_time;
	@ViewInject(R.id.schedule_add_content) private EditText schedule_add_content;
	@ViewInject(R.id.add_schedule_submit) private Button add_schedule_submit;
	
	@ViewInject(R.id.schedule_add_close) private ImageView schedule_add_close;
	
	@Override
	protected void onInitLayoutAfter() {
		
	
	}
	
	@OnClick({R.id.add_schedule_submit,R.id.schedule_add_close,R.id.schedule_add_time})
	public void click(View v){
		if(v.getId() == R.id.add_schedule_submit){
			submit();
		}else if(v.getId() == R.id.schedule_add_close){
			onBackPressed();
		}else if(v.getId() == R.id.schedule_add_time){
			Intent intent = new Intent(activity, DatePickerActivity.class);
			intent.putExtra("sDate", DateUtils.toDate(new Date(), Constens.DATE_FORMAT_YYYY_MM_DD));
			intent.putExtra("currentDate", schedule_add_time.getText().toString().trim());
			intent.putExtra("sDateMessage", getString(R.string.s_date_message));
			startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
		}
	}
	
	
	public void submit(){
		String date = schedule_add_time.getText().toString();
		String content = schedule_add_content.getText().toString();
		if(ValidationUtils.isNull(date,content)){
			T.showShort(activity, "资料填写不完整!");
			return;
		}
		JSONObject params = new JSONObject();
		try {
			params.put("scheduleDate", date+" "+DateUtils.toDate(new Date(), Constens.DATE_FORMAT_HH_MM_SS));
			params.put("content", content);
			Loading.bulid(activity, null).show();
			RequestManager.postObject(Constens.ACCOUNT_SCHEDULE, activity, params, new Listener<JSONObject>( ) {
				public void onResponse(JSONObject arg0) {
					L.d(TAG, arg0.toString());
					Loading.bulid(activity, null).dismiss();
					T.showShort(activity, "添加成功");
					setResult(RESULT_OK);
					finish();
				}
			}, new RequestErrorListener() {
				@Override
				public void requestError(VolleyError e) {
					Loading.bulid(activity, null).dismiss();
					if(e.networkResponse != null){
						T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
					}else{
						T.showShort(activity, "添加成功");
						setResult(RESULT_OK);
						finish();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
			if(resultCode == RESULT_OK){
				if(data.hasExtra("date")){
					schedule_add_time.setText(data.getStringExtra("date"));
				}
			}
		}
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		
		
	}

}
