package com.uyi.app.ui.personal.exclusive;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.HeaderView.OnTabChanage;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.exclusive.adapter.ExclusiveAdapter;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 专属预约    我的预约/所有预约
 * @author user
 *
 */
@ContentView(R.layout.exclusive)
public class ExclusiveActivity extends BaseActivity implements OnTabChanage, OnItemClickListener<Map<String, Object>>, Pager, OnRefreshListener {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	
	private LinearLayoutManager linearLayoutManager;
	private ExclusiveAdapter exclusiveAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

	/**
	 * 当type为1, 我的咨询
		当type为2, 所有可咨询的
	 */
	private int type = 1;
	
	
	@Override
	protected void onInitLayoutAfter() {

		headerView.showLeftReturn(true).showTab(true).showRight(true).setOnTabChanage(this);
		String[] str = getResources().getStringArray(R.array.exclusive);
		headerView.setTitleTabs(str);
		headerView.selectTabItem(1);
		
		linearLayoutManager = new LinearLayoutManager(activity);
		exclusiveAdapter = new ExclusiveAdapter(activity);
		exclusiveAdapter.setOnItemClickListener(this);
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
		Loading.bulid(activity, null).show();
		L.d(TAG, type+"");
		RequestManager.getObject(String.format(Constens.ACCOUNT_QUERY_EXCLUSIVE_CONSULTS,type,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
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
						item.put("type", type);
						item.put("start", jsonObject.getString("start"));//开始时间 
						item.put("end", jsonObject.getString("end"));//结束时间
						item.put("people", jsonObject.getInt("people"));//人数
						item.put("reserved", jsonObject.getInt("reserved"));//预约人数
						item.put("beans", jsonObject.getInt("beans"));//费用
						item.put("desc", jsonObject.getString("desc"));
						item.put("address", jsonObject.getString("address"));
						
						/**
						 *  0: 将要进行
							1: 删除
							2: 预约满员
							3: 结束
						 */
						item.put("status", jsonObject.getInt("status"));//专属咨询状态
						
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
		Intent intent = new Intent(activity, ExclusiveDetailsActivity.class);
		intent.putExtra("id", (int)data.get("id"));
		intent.putExtra("type", type);
		startActivity(intent);
	}

	@Override
	public void onChanage(int postion) {
		if(postion != type){
			type = postion;
			onRefresh() ;
		}		
	}

}
