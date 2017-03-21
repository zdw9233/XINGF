package com.uyi.app.ui.personal.questions;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.questions.adapter.HealthyQuestionsDetailsAdapter;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 健康问答详情
 * @author user
 *
 */
@ContentView(R.layout.healthy_questions_detals)
public class HealthyQuestionsDetailsActivity extends BaseActivity implements Pager {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	@ViewInject(R.id.healthy_questions_details_content) private EditText healthy_questions_details_content;
	@ViewInject(R.id.healthy_questions_details_submit) private Button healthy_questions_details_submit;
	
	
	private LinearLayoutManager linearLayoutManager;
	private HealthyQuestionsDetailsAdapter healthyQuestionsAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
	String id;
	
	@Override
	protected void onInitLayoutAfter() {
		id = getIntent().getStringExtra("id");
		headerView.showLeftReturn(true).showRight(false).showTitle(true).setTitle("健康咨询").setTitleColor(getResources().getColor(R.color.blue));
		linearLayoutManager = new LinearLayoutManager(activity);
		healthyQuestionsAdapter = new HealthyQuestionsDetailsAdapter(activity);
		healthyQuestionsAdapter.setDatas(datas);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(healthyQuestionsAdapter);
		recyclerView.setPager(this);
		onRefresh();
	}
	
	
	
	@OnClick(R.id.healthy_questions_details_submit)
	public void click(View v){
		String content = healthy_questions_details_content.getText().toString();
		if(ValidationUtils.isNull(content)){
			T.showShort(activity, "资料填写不完整!");
			return;
		}
		
		if(ValidationUtils.length(content) > 200){
			T.showShort(activity, "字数不能大于200字!");
		}
		healthy_questions_details_content.setText("");
//		
		JSONObject params = new JSONObject();
		try {
			params.put("consultId", id);
			params.put("content", content);
			RequestManager.postObject(Constens.CONSULT_ADVICD_REPLY, activity, params,null, new RequestErrorListener() {
				@Override
				public void requestError(VolleyError e) {
						onRefresh();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public boolean shouldLoad() {
		return isLooding;
	}

	
	public void onRefresh() {
		pageNo = 1;
		isLooding = true;
		datas.clear();
		recyclerView.setRefreshing(false);
		loadNextPage();
	}
	
	
	@Override
	public void loadNextPage() {
		isLooding = false;
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.CONSULT_HEALTH_ADVICD,id,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Loading.bulid(activity, null).dismiss();
				try {
					totalPage = data.getInt("pages");
					JSONArray  array = data.getJSONArray("results");
					L.d(TAG, data.toString());
					for(int i = 0;i<array.length();i++){
						Map<String,Object> item = new HashMap<String,Object>();
						JSONObject jsonObject = array.getJSONObject(i);
						item.put("datetime", jsonObject.getString("datetime"));
						item.put("content", jsonObject.getString("content"));
						item.put("isDoctor", jsonObject.getBoolean("isDoctor"));
						JSONObject user = jsonObject.getJSONObject("user");
						item.put("id", user.getInt("id"));
						item.put("account", user.getString("account"));
						item.put("realName", user.getString("realName"));
						item.put("iconUrl", user.getString("iconUrl"));
						datas.add(item);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				healthyQuestionsAdapter.notifyDataSetChanged();
				if(pageNo <= totalPage){
					isLooding = true;
					pageNo ++;
				}else{
					recyclerView.setRefreshing(false);
				}
			}
		});
	}

}
