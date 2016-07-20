package com.uyi.app.ui.personal.questions;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.questions.adapter.HealthyQuestionsAdapter;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 健康问答
 * @author user
 *
 */
@ContentView(R.layout.healthy_questions)
public class HealthyQuestionsActivity extends BaseActivity implements OnItemClickListener<Map<String, Object>>, OnRefreshListener, Pager {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.new_healthy_questions_layout_start) private LinearLayout new_healthy_questions_layout_start;
	
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	
	private LinearLayoutManager linearLayoutManager;
	private HealthyQuestionsAdapter healthyQuestionsAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
	UserInfo userInfo;
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("健康咨询").setTitleColor(getResources().getColor(R.color.blue));
		
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
	
	
	@OnClick(R.id.new_healthy_questions_layout_start)
	public void click(View v){
		userInfo = UserInfoManager.getLoginUserInfo(this);

		RequestManager.getObject(Constens.HAVE_NUMBER, this, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					System.out.println(data.toString());
					if(data.getInt("healthAdvisory") > 0){
						startActivityForResult(new Intent(activity, HealthyQuestionsAddActivity.class), Constens.START_ACTIVITY_FOR_RESULT);
					}else{
						if(userInfo.isFree == 2){
							T.showShort(HealthyQuestionsActivity.this," 本月健康咨询次数使用完毕");

						}else{
							T.showShort(HealthyQuestionsActivity.this," 该服务仅针对服务包用户，请购买相应服务包！");
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


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
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.HEALTH_ADVICES,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Loading.bulid(activity, null).dismiss();
				try {
					totalPage = data.getInt("pages");
					JSONArray  array = data.getJSONArray("results");
					if (pageNo == 1) datas.clear();
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
