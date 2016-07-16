package com.uyi.app.ui.personal.notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import android.widget.TextView;


/**
 * 公告详情
 * @author user
 *
 */
@ContentView(R.layout.notice_detals)
public class NoticeDetailsActivity extends BaseActivity {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.notice_detail_target) private TextView notice_detail_target;
	@ViewInject(R.id.notice_detail_title) private TextView notice_detail_title;
	@ViewInject(R.id.notice_detail_content) private TextView notice_detail_content;
	
	int id;
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("公告详情").setTitleColor(getResources().getColor(R.color.blue));
		id = getIntent().getIntExtra("id", 0);
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.DOCTOR_MY_MESSAGE, id,2), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					Loading.bulid(activity, null).dismiss();
				JSONArray tos = data.getJSONArray("tos");//发送对象
				notice_detail_target.setText(toTos(tos));
				notice_detail_title.setText(data.getString("title"));
				notice_detail_content.setText(data.getString("content"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}
	
	
	
	public String toTos(JSONArray tos ){
		String result ="";
		if(tos != null){
			for(int i = 0;i<tos.length();i++){
				try {
					int to = tos.getInt(i);
					if(to == 1){
						result += "用户  ";
					}else if(to == 2){
						result += "专家 ";
					}else if(to == 3){
						result += "资深专家  ";
					}else if(to == 4){
						result += "所有咨询过的用户  ";
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
