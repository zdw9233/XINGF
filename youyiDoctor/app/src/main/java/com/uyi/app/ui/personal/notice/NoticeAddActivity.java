package com.uyi.app.ui.personal.notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 公告添加
 * @author user
 *
 */
@ContentView(R.layout.notice_add)
public class NoticeAddActivity extends BaseActivity {
	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.notice_add_target) private TextView notice_add_target;//专家
	
	@ViewInject(R.id.notice_add_target_zczj_layout) private LinearLayout notice_add_target_zczj_layout;//资深专家
	@ViewInject(R.id.notice_add_yh) private TextView notice_add_yh;
	@ViewInject(R.id.notice_add_zj) private TextView notice_add_zj;
	@ViewInject(R.id.notice_add_zszj) private TextView notice_add_zszj;
	
	
	@ViewInject(R.id.notice_add_title) private EditText notice_add_title;
	@ViewInject(R.id.notice_add_content) private EditText notice_add_content;
	@ViewInject(R.id.notice_add_submit) private Button notice_add_submit;
	
	UserInfo userInfo ;
	
	
    boolean one = false;//用户
    boolean two = false;//专家
    boolean three = false;//资深专家
    boolean four = true;//所有咨询过的用户
    
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("发送公告").setTitleColor(getResources().getColor(R.color.blue));
		userInfo = UserInfoManager.getLoginUserInfo(activity);
		if(userInfo.type == 1){//专家
			notice_add_target.setVisibility(View.VISIBLE);
		}else if(userInfo.type == 2){//资深
			notice_add_target_zczj_layout.setVisibility(View.VISIBLE);
		}
	}
	
	@OnClick({R.id.notice_add_yh,R.id.notice_add_zj,R.id.notice_add_zszj,R.id.notice_add_submit})
	public void click(View v){
		if(v.getId() == R.id.notice_add_yh){
			if(one){
				one = false;
				notice_add_yh.setBackgroundResource(R.drawable.hui_stroke);
			}else{
				one = true;
				notice_add_yh.setBackgroundResource(R.drawable.blue_bg);
			}
		}else if(v.getId() == R.id.notice_add_zj){
			if(two){
				two = false;
				notice_add_zj.setBackgroundResource(R.drawable.hui_stroke);
			}else{
				two = true;
				notice_add_zj.setBackgroundResource(R.drawable.blue_bg);
			}
		}else if(v.getId() == R.id.notice_add_zszj){
			if(three){
				three = false;
				notice_add_zszj.setBackgroundResource(R.drawable.hui_stroke);
			}else{
				three = true;
				notice_add_zszj.setBackgroundResource(R.drawable.blue_bg);
			}
		}else if(v.getId() == R.id.notice_add_submit){
			String title = notice_add_title.getText().toString();
			String content = notice_add_content.getText().toString();
			if(ValidationUtils.isNull(title,content)){
				T.showShort(activity, "资料不完整!");
				return;
			}
			JSONObject params = new JSONObject();
			try {
				params.put("type", 2);
				JSONArray value = new JSONArray();
				if(userInfo.type == 1){//专家
					value.put(4);
				}else if(userInfo.type == 2){//资深
					if(one){
						value.put(1);
					}
					if(two){
						value.put(2);
					}
					if(three){
						value.put(3);
					}
				}
				params.put("to", value);
				params.put("title", title);
				params.put("content", content);
				Loading.bulid(activity, null).show();
				RequestManager.postObject(Constens.DOCTOR_MY_PUBLISH, activity, params,null,new RequestErrorListener() {
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
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
