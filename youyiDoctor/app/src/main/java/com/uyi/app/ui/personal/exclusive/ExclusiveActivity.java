package com.uyi.app.ui.personal.exclusive;

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
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.exclusive.adapter.ExclusiveAdapter;
import com.uyi.app.ui.personal.exclusive.adapter.ExclusiveAdapter.OnExclusiveUpdateListenner;
import com.uyi.app.utils.L;
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
 * 专属咨询 
 * @author user
 *
 */
@ContentView(R.layout.exclusive)
public class ExclusiveActivity extends BaseActivity implements  OnItemClickListener<Map<String, Object>>, Pager, OnRefreshListener, OnExclusiveUpdateListenner {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.new_consult_layout_start) private LinearLayout new_consult_layout_start;
	
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	
	private LinearLayoutManager linearLayoutManager;
	private ExclusiveAdapter exclusiveAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).setTitle("专属咨询 ").setTitleColor(getResources().getColor(R.color.blue)).showRight(true);
		linearLayoutManager = new LinearLayoutManager(activity);
		exclusiveAdapter = new ExclusiveAdapter(activity);
		exclusiveAdapter.setOnItemClickListener(this);
		exclusiveAdapter.setOnExclusiveUpdateListenner(this);
		exclusiveAdapter.setDatas(datas);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(exclusiveAdapter);
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

	@OnClick(R.id.new_consult_layout_start)
	public void click(View v){
		startActivityForResult(new Intent(activity, NewExclusiveActivity.class),Constens.START_ACTIVITY_FOR_RESULT);
	}
	
	@Override
	public void loadNextPage() {
		isLooding = false;
		L.d(TAG, datas.size()+"");
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.DOCTOR_EXCLUSIVE_CONSULTS,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Loading.bulid(activity, null).dismiss();
				try {
					totalPage = data.getInt("pages");
					JSONArray  array = data.getJSONArray("results");
					for(int i = 0;i<array.length();i++){
						Map<String,Object> item = new HashMap<String,Object>();
						JSONObject jsonObject = array.getJSONObject(i);
						item.put("id", jsonObject.getInt("id"));
						item.put("start", jsonObject.getString("start"));//开始时间 
						item.put("end", jsonObject.getString("end"));//结束时间
						item.put("people", jsonObject.getInt("people"));//人数
						item.put("reserved", jsonObject.getInt("reserved"));//预约人数
						item.put("beans", jsonObject.getInt("beans"));//费用
						item.put("status", jsonObject.getInt("status"));//费用
						item.put("address", jsonObject.getString("address"));
						item.put("canBeDeleted", jsonObject.getBoolean("canBeDeleted"));//是否能被删除
						JSONObject doctor = jsonObject.getJSONObject("doctor");
						item.put("doctorId", doctor.getString("id"));
						item.put("name", doctor.getString("name"));
						item.put("icon", doctor.getString("icon"));
						datas.add(item);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				exclusiveAdapter.notifyDataSetChanged();
				swipeRefreshLayout.setRefreshing(false);
				if(pageNo < totalPage){
					isLooding = true;
					pageNo ++;
				}else{
					recyclerView.setRefreshing(false);
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
	public void onItemClick(int position, Map<String, Object> data) {
		Intent intent = new Intent(activity, ExclusiveDetailsActivity.class);
		intent.putExtra("id", (int)data.get("id"));
		startActivity(intent);
	}

	@Override
	public void onUpdate(Map<String, Object> item) {
		Intent intent = new Intent(activity, NewExclusiveActivity.class);
		intent.putExtra("id", (int)item.get("id"));
		startActivityForResult(intent,Constens.START_ACTIVITY_FOR_RESULT);
	}

}
