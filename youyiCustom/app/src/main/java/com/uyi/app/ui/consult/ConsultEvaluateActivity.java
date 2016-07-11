package com.uyi.app.ui.consult;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UYIApplication;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


/**
 * 评价咨询
 * @author user
 *
 */
@ContentView(R.layout.consult_evaluate)
public class ConsultEvaluateActivity extends BaseActivity implements OnClickListener {
	
	
	@ViewInject(R.id.headerView) private HeaderView headerView; 
	@ViewInject(R.id.consult_evaluate_rating) private RatingBar consult_evaluate_rating; 
	@ViewInject(R.id.consult_evaluate_edit) private EditText consult_evaluate_edit; 
	@ViewInject(R.id.consult_evaluate_submit) private Button consult_evaluate_submit; 
	
	
	
	private int consultId;
	
	private int rating;
	
	
	
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true)
		.showTitle(true)
		.setTitleColor(getResources().getColor(R.color.blue))
		.setTitle("评价咨询")
		.showRight(true);
		
		if(!getIntent().hasExtra("consultId")){
		    return;
		}
		this.consultId = getIntent().getIntExtra("consultId",0);
		
		consult_evaluate_submit.setOnClickListener(this);
		
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public void onClick(View v) {
		int star = consult_evaluate_rating.getNumStars();
		String comment = consult_evaluate_edit.getText().toString();
		if(ValidationUtils.isNull(comment)){
			return;
		}
		Loading.bulid(activity, null).show();
		try {
			JSONObject params = new JSONObject();
			params.put("star", star);
			params.put("comment", comment);
			RequestManager.postObject(String.format(Constens.HEALTH_CONSULT_CONSULT, this.consultId),activity,params, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					T.showLong(UYIApplication.getContext(), "评价成功!");
					Loading.bulid(activity, null).dismiss();
					finish();
				}
			},new RequestErrorListener() {
				public void requestError(VolleyError e) {
					T.showLong(UYIApplication.getContext(), "评价成功!");
					Loading.bulid(activity, null).dismiss();
					finish();
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
