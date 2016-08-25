package com.uyi.app.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyi.app.UserInfoManager;
import com.uyi.app.sweep.CaptureActivity;
import com.uyi.app.ui.common.ServiceNumberActivity;
import com.uyi.app.ui.common.UpdateGuardianInfo;
import com.uyi.app.ui.common.UpdatePasswordActivity;
import com.uyi.app.ui.healthinfo.HealthInfoActivity;
import com.uyi.app.ui.recharge.RechargeActivity;
import com.uyi.custom.app.R;

/**
 * 点击右边出来的菜单
 * @author user
 *
 */
public class RightMenuDialog extends AbstrctDialog implements android.view.View.OnClickListener {


	private LinearLayout right_menu_update_password;
	private LinearLayout right_menu_update_info_guardian;
	private LinearLayout right_menu_update_info;
	private LinearLayout right_menu_update_pay;
	private LinearLayout right_menu_sweep;
	private LinearLayout right_menu_service;
	private TextView xiugai;
	
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
		right_menu_service = $( R.id.right_menu_service);
		right_menu_update_info_guardian = $(R.id.right_menu_update_guardian_info);
		xiugai = $(R.id.right_xiugai);
		right_menu_update_pay = $( R.id.right_menu_update_pay);
		right_menu_sweep = $(R.id.right_menu_Sweep);
		right_menu_update_password.setOnClickListener(this);
		right_menu_update_info.setOnClickListener(this);
		right_menu_update_info_guardian.setOnClickListener(this);
		right_menu_update_pay.setOnClickListener(this);
		right_menu_service.setOnClickListener(this);
		right_menu_sweep.setOnClickListener(this);
		Log.e("logasguardian=======",UserInfoManager.getLoginUserInfo(getContext()).logasguardian+"");
		if( UserInfoManager.getLoginUserInfo(getContext()).logasguardian){
			right_menu_update_info_guardian.setVisibility(View.VISIBLE);
			xiugai.setText("修改被监护人资料");
		}
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
			Intent intent = new Intent(getContext(), HealthInfoActivity.class);
//			intent.putExtra("update", 1);
			getContext().startActivity(intent);
		}else if(v.getId() == R.id.right_menu_update_guardian_info){
			Intent intent = new Intent(getContext(), UpdateGuardianInfo.class);
			getContext().startActivity(intent);
		}else if(v.getId() == R.id.right_menu_update_pay){
			Intent intent = new Intent(getContext(), RechargeActivity.class);
			getContext().startActivity(intent);
			
		}else if(v.getId() == R.id.right_menu_service){
//个人信息（服务次数）
			Intent intent = new Intent(getContext(), ServiceNumberActivity.class);
			getContext().startActivity(intent);

		}else if(v.getId() == R.id.right_menu_Sweep){
			Intent intent = new Intent(getContext(), CaptureActivity.class);
			getContext().startActivity(intent);
//			JSONObject params = new JSONObject();
//			try {
//				params.put("customerId",UserInfoManager.getLoginUserInfo(getContext()).id );
//				params.put("ccid","0075515000000000");
//				params.put("useTime","2016-4-26 15:07");
//				
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			RequestManager.postObject(String.format(Constens.SWEEP_UP), getContext(), params , new Response.Listener<JSONObject>() {
//				public void onResponse(JSONObject data) {
//					Loading.bulid(getContext(),null).dismiss();
//					System.out.println("成功");
////					team_goup_jiaru.setVisibility(View.GONE);
//					Toast.makeText(getContext(), "上传成功！", 0).show();
//				
//				}
//			}, new RequestErrorListener() {
//				public void requestError(VolleyError e) {
//					Loading.bulid(getContext(),null).dismiss();
//					System.out.println(e.toString());
//					Toast.makeText(getContext(), "上传失败", 0).show();
//				}
//			});
		}
		dismiss();
	}
	
	
}
