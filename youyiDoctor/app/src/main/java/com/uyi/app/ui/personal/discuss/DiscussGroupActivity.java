package com.uyi.app.ui.personal.discuss;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.model.bean.Consult;
import com.uyi.app.ui.consult.ConsultDetailsActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.ui.personal.discuss.adapter.DiscussGroupAdapter;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;



/**
 * 讨论组
 * @author user
 *
 */
@ContentView(R.layout.discuss_group)
public class DiscussGroupActivity extends BaseActivity implements Pager, OnRefreshListener, OnItemClickListener<Consult> {
	
	@ViewInject(R.id.headerView) private HeaderView headerView; 
	
	private ArrayList<Consult> datas = new ArrayList<Consult>();
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView; 
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;

	private LinearLayoutManager linearLayoutManager;
	private DiscussGroupAdapter discussGroupAdapter;


	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).setTitle("讨论组").setTitleColor(getResources().getColor(R.color.blue)).showRight(true) ;
		linearLayoutManager = new LinearLayoutManager(activity);
		discussGroupAdapter = new DiscussGroupAdapter(activity);
		discussGroupAdapter.setOnItemClickListener(this);
		discussGroupAdapter.setDatas(datas);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(discussGroupAdapter);
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
	public boolean shouldLoad() {
		return isLooding;
	}

	@Override
	public void loadNextPage() {
		isLooding = false;
		Looding.bulid(activity,null).show();
		RequestManager.getObject(String.format(Constens.HEALTH_CONSULTS, "5","1",pageNo,pageSize),activity, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject data) {
				try {
					Looding.bulid(activity,null).dismiss();
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
						consult.isMyConsult = jsonObject.getBoolean("isMyConsult");
						consult.addDiscussReason = JSONObjectUtils.getString(jsonObject, "addDiscussReason");
						datas.add(consult);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				discussGroupAdapter.notifyDataSetChanged();
				swipeRefreshLayout.setRefreshing(false);
				if(pageNo <= totalPage){
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
	public void onRefresh() {
		pageNo = 1;
		isLooding = true;
		datas.clear();
		recyclerView.setRefreshing(false);
		loadNextPage();
	}  

	@Override
	public void onItemClick(int position, Consult data) {
		Intent intent = new Intent(activity, DiscussGroupDetailsActivity.class);
		intent.putExtra("id", data.id);
		intent.putExtra("status", data.status);
		intent.putExtra("isMyConsult", data.isMyConsult);
//		L.d(TAG, data.id+":"+data.status+":"+data.isMyConsult);
		startActivity(intent);
	}
 

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
			if(resultCode == -1){
				onRefresh();
			}
		}
	}

}
