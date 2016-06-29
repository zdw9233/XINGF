package com.uyi.app.ui.personal.questions;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * 添加健康问答
 * @author user
 *
 */
@ContentView(R.layout.healthy_questions_add)
public class HealthyQuestionsAddActivity extends BaseActivity {
	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.healthy_questions_content) private EditText healthy_questions_content;
	@ViewInject(R.id.healthy_questions_submit) private Button healthy_questions_submit;
	
	
	
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("健康问答").setTitleColor(getResources().getColor(R.color.blue));
		
		
		
	}
	
	@OnClick(R.id.healthy_questions_submit)
	public void click(View v){
		String content = healthy_questions_content.getText().toString();
		if(ValidationUtils.isNull(content)){
			T.showShort(activity, "资料填写不完整!");
			return;
		}
		
		if(ValidationUtils.length(content) > 200){
			T.showShort(activity, "字数不能大于200字!");
		}
		
		JSONObject params = new JSONObject();
		try {
			params.put("content", content);
			RequestManager.postObject(Constens.CONSULT_ADVICD, activity,params,new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					try {
						if(!ValidationUtils.isNull(data.getString("id"))){
							setResult(RESULT_OK);
							finish();
						}else{
							T.showShort(activity, "创建失败!");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} ,null);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
