package com.uyi.app.ui.personal.messagemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * 发送消息
 * @author user
 *
 */
@ContentView(R.layout.message_manager_send)
public class MessageManagerSendActivity extends BaseActivity {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.message_manager_send_search) private TextView message_manager_send_search;
	
	@ViewInject(R.id.message_manager_send_selected_layout) private FlowRadioGroup  message_manager_send_selected_layout;//存放选择的用户的布局
	
	
	@ViewInject(R.id.message_manager_send_names) private EditText message_manager_send_names;
	@ViewInject(R.id.message_manager_send_title) private EditText message_manager_send_title;
	@ViewInject(R.id.message_manager_send_content) private EditText message_manager_send_content;
	@ViewInject(R.id.message_manager_send_submit) private Button message_manager_send_submit;
	
	Map<Integer,UserInfo> users = new HashMap<Integer,UserInfo>();
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("发送消息").setTitleColor(getResources().getColor(R.color.blue));
	}
	
	
	@OnClick({R.id.message_manager_send_search,R.id.message_manager_send_submit})
	public void click(View v){
		if(v.getId() == R.id.message_manager_send_search){
			String name = message_manager_send_names.getText().toString();
			Intent intent = new Intent(activity, QueryCustomerActivity.class);
			intent.putExtra("name", name);
			startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT_THREE);
		}else{
			if(users.size() == 0 ){
				T.showShort(activity, "请选择发送对象!");
				return;
			}
			
			String title = message_manager_send_title.getText().toString();
			String content = message_manager_send_content.getText().toString();
			
			if(ValidationUtils.isNull(title,content)){
				T.showShort(activity, "资料填写不完整!");
				return;
			}
			
			JSONObject params = new JSONObject();
			try {
				Loading.bulid(activity, null).show();
				params.put("type", 1);
				params.put("title", title);
				params.put("content", content);
				JSONArray array = new JSONArray();
				for(int id : users.keySet()){
					array.put(id);
				}
				params.put("customerIds", array);
				RequestManager.postObject(Constens.DOCTOR_MY_PUBLISH, activity, params, null, new RequestErrorListener() {
					@Override
					public void requestError(VolleyError e) {
						Loading.bulid(activity, null).dismiss();
						if(e.networkResponse != null){
							T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
						}else{
							setResult(RESULT_OK);
							finish();
						}
					}
				});
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public void addUser(){
		message_manager_send_selected_layout.removeAllViews();
		for(Entry<Integer, UserInfo> map : users.entrySet()){
			UserInfo userInfo = map.getValue();
			View view = LayoutInflater.from(activity).inflate(R.layout.user_selected, message_manager_send_selected_layout, false);
			ImageView close = (ImageView)view.findViewById(R.id.close);
			TextView name = (TextView)view.findViewById(R.id.name);
			close.setTag(map.getKey());
			close.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					users.remove(v.getTag());
					 addUser();
				}
			});
			name.setText(userInfo.realName);
			message_manager_send_selected_layout.addView(view);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode ==  Constens.START_ACTIVITY_FOR_RESULT_THREE){
			if(resultCode == RESULT_OK){
				UserInfo user = new UserInfo();
				user.id = data.getIntExtra("id", 0);
				user.realName = data.getStringExtra("realName");
				users.put(user.id, user);
				addUser();
			}
		}
	}
	
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
