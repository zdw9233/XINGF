package com.uyi.app.ui.consult;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.model.bean.Consult;
import com.uyi.app.ui.consult.adapter.HistoryConsultAdapter;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.L;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 历史咨询
 * @author user
 *
 */

@ContentView(R.layout.fragment_consultation)
public class HistoryConsultActivity extends BaseActivity implements OnItemClickListener<Consult>, Pager, OnRefreshListener {
	@ViewInject(R.id.lay) private LinearLayout lay;
	private ArrayList<Consult> datas = new ArrayList<Consult>();
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView; 
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	private LinearLayoutManager linearLayoutManager;
	private HistoryConsultAdapter historyConsultAdapter;
	
	private int customerId;
	
	@Override
	protected void onInitLayoutAfter() {
		customerId = getIntent().getIntExtra("customerId", 0);
		linearLayoutManager = new LinearLayoutManager(activity);
		historyConsultAdapter = new HistoryConsultAdapter(activity);
		historyConsultAdapter.setOnItemClickListener(this);
		historyConsultAdapter.setDatas(datas);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(historyConsultAdapter);
		recyclerView.setPager(this);
		
		 //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		onRefresh();
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
	}
@OnClick(R.id.back)
private void click(View view){
	if(view.getId()==R.id.back){
		finish();
	}
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
		Loading.bulid(activity,null).show();
//		curl "http://121.42.142.228:8080/app/api/doctor/health/consults?customerId=57&page=1&pageSize=10"
		RequestManager.getObject(String.format(Constens.HEALTH_CONSULTS+"&customerId="+customerId, "1","1",pageNo,pageSize), activity, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject data) {
				try {
					L.d(TAG, data.toString());
					Loading.bulid(activity,null).dismiss();
					totalPage = data.getInt("pages");
					JSONArray  array = data.getJSONArray("results");
					for(int i = 0;i<array.length();i++){
						Consult consult  = new Consult();
						JSONObject jsonObject = array.getJSONObject(i);
						consult.id = jsonObject.getInt("id");
						consult.icon = jsonObject.getString("icon");
						consult.desc = jsonObject.getString("desc");
						consult.updateTime = jsonObject.getString("updateTime");
						consult.status = jsonObject.getInt("status");
						if(jsonObject.has("isDiscuss") && jsonObject.getBoolean("isDiscuss")){
							consult.status =6;
						}
						if(jsonObject.has("isCommented") && jsonObject.getBoolean("isCommented")){
							consult.status =5;
						}
						
//						map.put("isDiscuss", jsonObject.getBoolean("isCommented"));
						datas.add(consult);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				historyConsultAdapter.notifyDataSetChanged();
				swipeRefreshLayout.setRefreshing(false);
				
				if(pageNo < totalPage){
					isLooding = true;
					pageNo ++;
				}else{
					recyclerView.setRefreshing(false);
//					T.showLong(UYIApplication.getContext(),R.string.looding_all);
				}
			}
		});
	}

	@Override
	public void onItemClick(int position, Consult data) {
		Intent intent = new Intent(activity,HistoryDetailsActivity.class );
		intent.putExtra("id", data.id);
		startActivity(intent);
	}

}
