package com.uyi.app.ui.personal.messagemanager;

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
import com.uyi.app.ui.personal.messagemanager.adapter.MessageManagerAdapter;
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
 * 消息管理
 * @author user
 *
 */
@ContentView(R.layout.message_manager)
public class MessageManagerActivity extends BaseActivity implements OnRefreshListener, OnItemClickListener<Map<String, Object>>, Pager {

	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.new_message_manager_layout_start) private LinearLayout new_message_manager_layout_start;
	
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	
	
	private LinearLayoutManager linearLayoutManager;
	private MessageManagerAdapter messageManagerAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("消息管理").setTitleColor(getResources().getColor(R.color.blue));
		linearLayoutManager = new LinearLayoutManager(activity);
		messageManagerAdapter = new MessageManagerAdapter(activity);
		messageManagerAdapter.setOnItemClickListener(this);
		messageManagerAdapter.setDatas(datas);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(messageManagerAdapter);
		recyclerView.setPager(this);
		
		 //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(this); 
		onRefresh();
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
	public boolean shouldLoad() {
		return isLooding;
	}

	@Override
	public void loadNextPage() {
		isLooding = false;
		Looding.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.DOCTOR_MY_MESSAGES,1,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Looding.bulid(activity, null).dismiss();
				try {
					totalPage = data.getInt("pages");
					JSONArray  array = data.getJSONArray("results");
					for(int i = 0;i<array.length();i++){
						Map<String,Object> item = new HashMap<String,Object>();
						JSONObject jsonObject = array.getJSONObject(i);
						item.put("id", jsonObject.getInt("id"));
						item.put("updateTime", jsonObject.getString("updateTime"));
						item.put("title", jsonObject.getString("title"));
						datas.add(item);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				messageManagerAdapter.notifyDataSetChanged();
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

	@Override
	public void onItemClick(int position, Map<String, Object> data) {
		Intent intent = new Intent(activity,MessageManagerDetailsActivity.class);
		intent.putExtra("id", (int)data.get("id"));
		startActivityForResult(intent,Constens.START_ACTIVITY_FOR_RESULT);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
			if(resultCode == RESULT_OK){
				onRefresh() ;
			}
		}else if(requestCode == Constens.START_ACTIVITY_FOR_RESULT_TWO){
			if(resultCode == RESULT_OK){
				onRefresh() ;
			}
		}
	}
	
	
	@OnClick(R.id.new_message_manager_layout_start)
	public void click(View view){
		startActivityForResult(new Intent(activity, MessageManagerSendActivity.class), Constens.START_ACTIVITY_FOR_RESULT_TWO);
	}
	
	
	
}
