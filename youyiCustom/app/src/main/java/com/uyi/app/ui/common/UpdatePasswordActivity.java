package com.uyi.app.ui.common;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UYIApplication;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.dialog.MessageConform.MessageType;
import com.uyi.app.ui.dialog.MessageConform.Result;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * 修改密码
 * @author user
 *
 */
@ContentView(R.layout.update_password)
public class UpdatePasswordActivity extends BaseActivity implements MessageConform.OnMessageClick{

	@ViewInject(R.id.headerView) 						private HeaderView headerView;
	
	@ViewInject(R.id.update_password_password) 			private EditText update_password_password;
	@ViewInject(R.id.update_password_newpassword) 		private EditText update_password_newpassword;
	@ViewInject(R.id.update_password_newpasswords) 		private EditText update_password_newpasswords;
	@ViewInject(R.id.update_password_submit) 			private Button update_password_submit;
	
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).setTitle("修改密码").setTitleColor(getResources().getColor(R.color.blue));
	}
	
	
	@OnClick(R.id.update_password_submit)
	public void click(View view){
		String cpwd = update_password_password.getText().toString();
		String pwd =  update_password_newpassword.getText().toString();
		String pwds = update_password_newpasswords.getText().toString();
		
		if(ValidationUtils.isNull(cpwd,pwd,pwds)){
			return;
		}
		
		if(!ValidationUtils.equlse(pwd, pwds)){
			T.showLong(activity, "两次密码输入不同!");
			return;
		}
		if(ValidationUtils.equlse(cpwd, pwds)){
			T.showLong(activity, "新密码不能和旧密码相同!");
			return;
		}
		
		if(ValidationUtils.length(pwd) < 6 || ValidationUtils.length(pwd) > 32){
			T.showLong(activity, "密码长度必须大于6小于32位!");
			return;
		}
		
		if(!ValidationUtils.pattern(Constens.PASSWORD_NEW_REGEX, pwd)){
			T.showLong(activity, "密码必须至少包含一个英文字符,一个数字!");
			return;
		}
		
		try {
			JSONObject params = new JSONObject();
			params.put("oldPassword", cpwd);
			params.put("newPassword", pwd);
//			UserInfo userInfo = UserInfoManager.getLoginUserInfo(this);
//			userInfo.password = pwd;
//			UserInfoManager.setLoginUserInfo(this,userInfo);
//			L.e("userinfo == ",userInfo.toString());
			Loading.bulid(activity, null).show();
			RequestManager.postObject(Constens.SETTING_PASSWORD, activity, params,null, new RequestErrorListener() {
				public void requestError(VolleyError e) {
					Loading.bulid(activity, null).dismiss();
					if(e.networkResponse != null){
						if(e.networkResponse.statusCode == 204){
							MessageConform conform = new MessageConform(activity, MessageType.ALERT);
							conform.setOnMessageClick(UpdatePasswordActivity.this);
							conform.setTitle("修改密码成功,请使用新密码重新登陆!");
							conform.show();
						}else{
							T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse)); 
						}
					}else{
						MessageConform conform = new MessageConform(activity, MessageType.ALERT);
						conform.setOnMessageClick(UpdatePasswordActivity.this);
						conform.setContent("修改密码成功,请使用新密码重新登陆!");
						conform.show();
					}
				}
			});
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}


	@Override
	public void onClick(Result result, Object object) {
		UYIApplication.loginOut();
		finish();
	}

}
