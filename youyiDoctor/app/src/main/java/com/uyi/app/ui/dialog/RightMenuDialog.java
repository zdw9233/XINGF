package com.uyi.app.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.common.UpdatePasswordActivity;
import com.uyi.app.ui.common.UpdateUserInfoActivity;
import com.uyi.app.ui.personal.standard.ChargeStandardActivity;
import com.uyi.doctor.app.R;

/**
 * 点击右边出来的菜单
 * @author user
 *
 */
public class RightMenuDialog extends AbstrctDialog implements android.view.View.OnClickListener {

	
	private LinearLayout right_menu_update_password; 
	private LinearLayout right_menu_update_info; 
	private LinearLayout right_menu_baoj; 
	private LinearLayout right_menu_shofei; 
	
	public RightMenuDialog(Context context) {
		super(context,R.style.dialog);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.right_menu_dialog);
		right_menu_update_password = $( R.id.right_menu_update_password);
		right_menu_update_info = $( R.id.right_menu_update_info);
		right_menu_baoj = $( R.id.right_menu_baoj);
		right_menu_shofei = $( R.id.right_menu_shofei);
		right_menu_update_password.setOnClickListener(this);
		right_menu_update_info.setOnClickListener(this);
		right_menu_baoj.setOnClickListener(this);
		right_menu_shofei.setOnClickListener(this);
		int type = UserInfoManager.getLoginUserInfo(getContext()).type;
//		if(type == 2){
//			right_menu_baoj.setVisibility(View.VISIBLE);
//			right_menu_shofei.setVisibility(View.VISIBLE);
//		}
		
	}


	public LinearLayout getRight_menu_update_password() {
		return right_menu_update_password;
	}
 

	public LinearLayout getRight_menu_update_info() {
		return right_menu_update_info;
	}


	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.right_menu_update_password){
			Intent intent = new Intent(getContext(), UpdatePasswordActivity.class);
			getContext().startActivity(intent);
		}else if(v.getId() == R.id.right_menu_update_info){
			Intent intent = new Intent(getContext(), UpdateUserInfoActivity.class);
			intent.putExtra("update", 1);
			getContext().startActivity(intent);
		}else if(v.getId() == R.id.right_menu_baoj){
//			Intent intent = new Intent(getContext(), AlarmStandardActivity.class);
//			getContext().startActivity(intent);
		}else if(v.getId() == R.id.right_menu_shofei){
			Intent intent = new Intent(getContext(), ChargeStandardActivity.class);
			getContext().startActivity(intent);
		}
		dismiss();
	}
	
	
}
