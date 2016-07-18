package com.uyi.app.ui.personal.messagemanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.L;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 消息管理详情
 * @author user
 *
 */
@ContentView(R.layout.message_manager_details)
public class MessageManagerDetailsActivity extends BaseActivity {
	
	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.message_manager_detail_target) private TextView message_manager_detail_target;
	@ViewInject(R.id.message_manager_details_objects_layout) private LinearLayout message_manager_details_objects_layout;
	
	@ViewInject(R.id.message_manager_detail_title) private TextView message_manager_detail_title;
	@ViewInject(R.id.message_manager_detail_content) private TextView message_manager_detail_content;
	
	boolean objectShow = false;

	int id;
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("消息详情").setTitleColor(getResources().getColor(R.color.blue));
        id = getIntent().getIntExtra("id",0);
        Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.DOCTOR_MY_MESSAGE, id,1), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					 Loading.bulid(activity, null).dismiss();
					message_manager_detail_title.setText(data.getString("title"));
					message_manager_detail_content.setText(data.getString("content"));
					JSONArray array = data.getJSONArray("customers");
					for(int i = 0;i<array.length();i++){
						JSONObject jsonObject = array.getJSONObject(i);
						addUserInfo(jsonObject);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void addUserInfo(JSONObject jsonObject) throws JSONException{
		View view = LayoutInflater.from(activity).inflate(R.layout.userinfo, message_manager_details_objects_layout, false);
		RoundedImageView header = (RoundedImageView)view.findViewById(R.id.header);
		TextView name = (TextView)view.findViewById(R.id.name);
		TextView info = (TextView)view.findViewById(R.id.info);
		name.setText(jsonObject.getString("name"));
		L.d(TAG, jsonObject.toString());
		info.setText(String.format("年龄：%s    性别：%s", jsonObject.getInt("age"),(ValidationUtils.equlse("MALE", jsonObject.getString("gender"))?"男":"女")));
		ImageCacheManager.loadImage(jsonObject.getString("icon"), ImageCacheManager.getImageListener(header, null, null));
		message_manager_details_objects_layout.addView(view);
	}
	
	
	
	
	@OnClick(R.id.message_manager_detail_target)
	public void click(View v){
		if(objectShow){
			objectShow = false;
			message_manager_detail_target.setVisibility(View.VISIBLE);
			message_manager_details_objects_layout.setVisibility(View.GONE);
		}else{
			objectShow = true;
			message_manager_detail_target.setVisibility(View.GONE);
			message_manager_details_objects_layout.setVisibility(View.VISIBLE);
		}
	}
	
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
