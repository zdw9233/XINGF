package com.uyi.app.ui.personal.standard;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * 收费标准
 * @author user
 *
 */
@ContentView(com.uyi.doctor.app.R.layout.charge_standard)
public class ChargeStandardActivity extends BaseActivity {
	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.charge_standard_text) private EditText charge_standard_text;
	@ViewInject(R.id.charge_standard_submit) private Button charge_standard_submit;
	
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("收费标准").setTitleColor(getResources().getColor(R.color.blue));
		RequestManager.getObject(Constens.DOCTOR_HEALTH_FEE, activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					charge_standard_text.setText(data.getInt("beans")+"");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	
	
	@OnClick(R.id.charge_standard_submit)
	public void click(View view){
		String money  = charge_standard_text.getText().toString();
		if(ValidationUtils.isNull(money)){
			T.showShort(activity, "请输入收费标准");
			return;
		}
		
		RequestManager.postObject(Constens.DOCTOR_HEALTH_FEE+money, activity,null, new Listener<JSONObject>() {
			public void onResponse(JSONObject arg0) {
				T.showShort(activity, "设置成功");
				finish();
			}
		},new RequestErrorListener() {
			public void requestError(VolleyError e) {
				T.showShort(activity, "设置成功");
				finish();
			}
		});
	}
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
