package com.uyi.app.ui.common;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 找回密码
 * @author user
 *
 */
@ContentView(R.layout.get_password)
public class GetPasswordActivity extends BaseActivity {

	@ViewInject(R.id.headerView) 				private HeaderView headerView;
	
	@ViewInject(R.id.get_password_view_one) 	private LinearLayout get_password_view_one;
	@ViewInject(R.id.get_password_username) 	private EditText get_password_username;
	@ViewInject(R.id.get_password_mobile) 		private EditText get_password_mobile;
	@ViewInject(R.id.get_password_one_next) 	private Button get_password_one_next;

	@ViewInject(R.id.get_password_view_two) 	private LinearLayout get_password_view_two;
	@ViewInject(R.id.get_password_wenti_text_layout) 	private LinearLayout get_password_wenti_text_layout;//问题布局
	@ViewInject(R.id.get_password_wenti_text) 		private EditText get_password_wenti_text;//问题题目
	@ViewInject(R.id.wenti) 					private TextView wenti;
	@ViewInject(R.id.get_password_daan) 		private EditText get_password_daan;
	@ViewInject(R.id.get_password_two_next) 	private Button get_password_two_next;
	
	@ViewInject(R.id.get_password_view_three) 	private LinearLayout get_password_view_three;
	@ViewInject(R.id.get_password_new_password) private EditText get_password_new_password;
	@ViewInject(R.id.get_password_new_passwords) private EditText get_password_new_passwords;
	@ViewInject(R.id.get_password_submit) 		private Button get_password_submit;
	
//	private  SpinerPopWindow spinerPopWindow;
//	private  List<String> lists = new ArrayList<String>();
//	private JSONArray arrays = new JSONArray();
	private int id;
	
	private String acount;
	private String mobile;
	private String question;
	private String answer;
	
	
	
	
	int currentPage = 1;
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showTitle(true);
		headerView.setTitle("身份验证");
		headerView.setHeaderBackgroundColor(getResources().getColor(R.color.blue));
//		spinerPopWindow = new SpinerPopWindow(activity);
//		spinerPopWindow.setItemListener(this);
//		RequestManager.getArray(Constens.SAFE_QUESTIONS,activity, new Response.Listener<JSONArray>() {
//			@Override
//			public void onResponse(JSONArray array) {
//				arrays = array;
//				for (int i = 0; i < arrays.length(); i++) {
//					try {
//						lists.add(array.getJSONObject(i).getString("question"));
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//				if(lists.size()>0){
//					onItemClick(0);
//				}
//				spinerPopWindow.refreshData(lists, 0);
//			}
//		});
	}
	
	
	@OnClick({R.id.get_password_one_next,R.id.get_password_two_next,R.id.get_password_submit,R.id.wenti})
	public void onClick(View view) throws JSONException{
		if(view.getId() == R.id.get_password_one_next){
			acount = get_password_username.getText().toString();
			mobile = get_password_mobile.getText().toString();
			if(ValidationUtils.isNull(acount,mobile)){
				T.showLong(activity, "资料未填写完整!");
				return;
			}
			
			if(!ValidationUtils.isMobile(mobile)){
				T.showLong(activity, "手机号错误!");
				return;
			}
			
			RequestManager.getObject(String.format(Constens.SAFE_QUESTION, acount,mobile), activity, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					try {
							wenti.setText(data.getString("question"));
							get_password_view_one.setVisibility(View.GONE);
							get_password_view_two.setVisibility(View.VISIBLE);
							get_password_view_three.setVisibility(View.GONE);
							headerView.setTitle("问题答案");
							currentPage = 2;
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			
		}else if(view.getId() == R.id.get_password_two_next){
			answer = get_password_daan.getText().toString();
			
			if(ValidationUtils.isNull(answer)){
				T.showLong(activity, "资料未填写完整!");
				return;
			}
			
			get_password_view_one.setVisibility(View.GONE);
			get_password_view_two.setVisibility(View.GONE);
			get_password_view_three.setVisibility(View.VISIBLE);
			headerView.setTitle("重置密码");
			currentPage = 3;
			
//			JSONObject params = new JSONObject();
//			params.put("account", acount);
//			params.put("phoneNumber", mobile);
//			params.put("question", id);
//			params.put("answer", answer);
//			L.d(TAG, params.toString());
//			RequestManager.postObject(Constens.SAFE_CHECK_ANSWER, activity, params, new Response.Listener<JSONObject>() {
//				@Override
//				public void onResponse(JSONObject data) {
//					try {
//						if(data.getBoolean("right")){
//							get_password_view_one.setVisibility(View.GONE);
//							get_password_view_two.setVisibility(View.GONE);
//							get_password_view_three.setVisibility(View.VISIBLE);
//							headerView.setTitle("重置密码");
//							currentPage = 3;
//						}else{
//							T.showLong(activity, "验证失败!");
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}, null);
		}else if(view.getId() == R.id.get_password_submit){
			String pwd = get_password_new_password.getText().toString();
			String pwds = get_password_new_passwords.getText().toString();
			
			if(ValidationUtils.isNull(pwd,pwds)){
				return;
			}
			
			if(!ValidationUtils.equlse(pwd, pwds)){
				T.showLong(activity, "两次密码输入不同");
				return;
			}
			
			if(!ValidationUtils.pattern(Constens.PASSWORD_NEW_REGEX, pwd)){
				T.showLong(activity, "密码必须至少包含一个英文字符,一个数字!");
				return;
			}
			
			if(ValidationUtils.length(pwd) < 6 || ValidationUtils.length(pwd) > 32){
				T.showLong(activity, "密码长度必须大于6小于32位!");
				return;
			}

			JSONObject params = new JSONObject();
			params.put("account", acount);
			params.put("phoneNumber", mobile);
			params.put("question", id);
			params.put("answer", answer);
			params.put("newPassword", pwd);
//			FORGOT_PASSWORD
			RequestManager.postObject(Constens.FORGOT_PASSWORD, activity, params, new Response.Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					T.showLong(activity, "重置密码成功!");
					finish();
				}
			}, new RequestErrorListener() {
				public void requestError(VolleyError e) {
					if(e.networkResponse.statusCode == 200){
						T.showLong(activity, "重置密码成功!");
						finish();
					} 
					try {
						String s = new String(e.networkResponse.data,"UTF-8");
						if(!ValidationUtils.isNull(s)){
							JSONObject jsonObject = new JSONObject(s);
							if(jsonObject.has("message")){
								T.showLong(activity,jsonObject.getString("message"));
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			});
		}
//		else if(view.getId() == R.id.wenti){
//			spinerPopWindow.setWidth(wenti.getWidth());
//			spinerPopWindow.showAsDropDown(view);
//		}
	}
	
	@Override
	public void onBackPressed() {
		if(currentPage == 1){
			super.onBackPressed();
		}else if(currentPage == 2){
			currentPage = 1;
			get_password_view_one.setVisibility(View.VISIBLE);
			get_password_view_two.setVisibility(View.GONE);
			get_password_view_three.setVisibility(View.GONE);
			headerView.setTitle("身份验证");
		}else if(currentPage == 3){
			currentPage = 2;
			get_password_view_one.setVisibility(View.GONE);
			get_password_view_two.setVisibility(View.VISIBLE);
			get_password_view_three.setVisibility(View.GONE);
			headerView.setTitle("问题答案");
		}
	}
	
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

//	@Override
//	public void onItemClick(int pos) {
////		if(lists.get(pos).equals("自定义问题")){
//////			get_password_wenti_text_layout.setVisibility(View.VISIBLE);
////			get_password_wenti_text.setText(arrays.getJSONObject(pos).getInt("id"));
////		}else{
//////			get_password_wenti_text_layout.setVisibility(View.GONE);
////			get_password_wenti_text.setText(lists.get(pos));
////		}
//		try {
//			id = arrays.getJSONObject(pos).getInt("id");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
////		T.showLong(activity, lists.get(pos));
//		wenti.setText(lists.get(pos));
//	}

}
