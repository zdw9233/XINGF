package com.uyi.app.ui.recharge;

import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.service.UserService;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.dialog.MessageConform.MessageType;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 充值
 * @author user
 *
 */
@ContentView(R.layout.recharge)
public class RechargeActivity extends BaseActivity {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.recharge_dou) private TextView recharge_dou;
	@ViewInject(R.id.recharge_money) private EditText recharge_money;
	@ViewInject(R.id.recharge_money_submit) private Button recharge_money_submit;
	
	String orderNo;
	MessageConform messageCoonform;
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).setTitle("充值").setTitleColor(getResources().getColor(R.color.blue));
		recharge_dou.setText(UserInfoManager.getLoginUserInfo(activity).beans+"");
		messageCoonform = new MessageConform(activity, MessageType.ALERT);
	}
	
	
	@OnClick(R.id.recharge_money_submit)
	public void click(View v){
		String beans = recharge_money.getText().toString();
		if(ValidationUtils.isNull(beans)){
			return;
		}
		Intent intent = new Intent(activity, AlipayActivity.class);
		intent.putExtra("beans", beans);
		startActivityForResult(intent, Constens.START_ALIPAY_FOR_RESULT);
	}
	
	public void getRechargeBeans(String orderNo){
		Looding.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.CUSTOMER_BUY_COIN_RESULT, orderNo), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Looding.bulid(activity, null).dismiss();
				try {
					messageCoonform.setContent(data.getString("message"));
					messageCoonform.show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.START_ALIPAY_FOR_RESULT){
			if(resultCode == RESULT_OK){
				String orderNo = data.getStringExtra("orderNo");
				String resultStatus = data.getStringExtra("resultStatus");
				if(ValidationUtils.equlse(resultStatus, "9000")){//充值成功
					T.showShort(activity, "充值成功!");
//					getRechargeBeans(orderNo);
					UserService.loadUserInfo(application);
				}else if(ValidationUtils.equlse(resultStatus, "8000")){
					messageCoonform.setContent("支付系统延迟,充值到账已系统为准!");
					messageCoonform.show();
				}else{
					T.showShort(activity, "充值失败!");
				}
			}
		}
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}
}
