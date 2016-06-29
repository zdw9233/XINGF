package com.uyi.app.ui.personal.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.ui.personal.questions.adapter.HealthyQuestionsAdapter;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;


/**
 * 健康问答
 * @author user
 *
 */
@ContentView(R.layout.healthy_questions)
public class HealthyQuestionsActivity extends BaseActivity implements OnItemClickListener<Map<String, Object>>, OnRefreshListener, Pager {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	
	private LinearLayoutManager linearLayoutManager;
	private HealthyQuestionsAdapter healthyQuestionsAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
	
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("健康问答").setTitleColor(getResources().getColor(R.color.blue));
		
		linearLayoutManager = new LinearLayoutManager(activity);
		healthyQuestionsAdapter = new HealthyQuestionsAdapter(activity);
		healthyQuestionsAdapter.setOnItemClickListener(this);
		healthyQuestionsAdapter.setDatas(datas);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(healthyQuestionsAdapter);
		recyclerView.setPager(this);
		
		 //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(this); 
		onRefresh();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
			if(resultCode == RESULT_OK){
				onRefresh();
			}
		}
		
		
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public void onRefresh() {
		pageNo = 1;
		isLooding = true;
		datas.clear();
		recyclerView.setRefreshing(false);
		loadNextPage();
	}

	@Override
	public void onItemClick(int position, Map<String, Object> data) {
		String id = data.get("id").toString();
		Intent intent = new Intent(activity, HealthyQuestionsDetailsActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	@Override
	public boolean shouldLoad() {
		return isLooding;
	}

	@Override
	public void loadNextPage() {
		isLooding = false;
		Looding.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.HEALTH_ADVICES,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Looding.bulid(activity, null).dismiss();
				try {
					totalPage = data.getInt("pages");
					JSONArray  array = data.getJSONArray("results");
					for(int i = 0;i<array.length();i++){
						Map<String,Object> item = new HashMap<String,Object>();
						JSONObject jsonObject = array.getJSONObject(i);
						item.put("id", jsonObject.getInt("id"));
						item.put("lastUpdateTime", jsonObject.getString("lastUpdateTime"));
						item.put("content", jsonObject.getString("content"));
						datas.add(item);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				healthyQuestionsAdapter.notifyDataSetChanged();
				swipeRefreshLayout.setRefreshing(false);
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
