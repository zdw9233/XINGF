package com.uyi.app.ui.health;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.ui.health.adapter.HealthManagerAdapter;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

public class FragmentHealthListManager extends BaseFragment
		implements Pager, OnRefreshListener, OnItemClickListener<Map<String, Object>> {
	@ViewInject(R.id.headerView_health)
	private HeaderView headerView_health;
	@ViewInject(R.id.health_edit_text)
	private EditText health_edit_text;
	@ViewInject(R.id.sousuo_health)
	private TextView sousuo_health;

	private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
	@ViewInject(R.id.recyclerView_healthmanager)
	private EndlessRecyclerView recyclerView_healthmanager;
	@ViewInject(R.id.swipeRefreshLayout_healthmanager)
	private SwipeRefreshLayout swipeRefreshLayout_healthmanager;
	public static int customer;
	private LinearLayoutManager linearLayoutManager;
	private HealthManagerAdapter healthManagerAdapter;
	public boolean initLoad = true;// 初始化加载
	public Main main;

	public FragmentHealthListManager(Main main) {
		this.main = main;
	}

	@Override
	protected int getLayoutResouesId() {
		return R.layout.fragment_health;
	}

	@Override
	protected void onInitLayoutAfter() {
		headerView_health.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showTitle(true)
				.setTitle("健康管理").setTitleColor(getResources().getColor(R.color.blue)).showRight(true);

		linearLayoutManager = new LinearLayoutManager(getView().getContext());
		healthManagerAdapter = new HealthManagerAdapter(getView().getContext());
		healthManagerAdapter.setOnItemClickListener(this);
		healthManagerAdapter.setDatas(datas);
		recyclerView_healthmanager.setLayoutManager(linearLayoutManager);
		recyclerView_healthmanager.addItemDecoration(
				new DividerItemDecoration(getView().getContext(), DividerItemDecoration.VERTICAL_LIST));
		recyclerView_healthmanager.setItemAnimator(new DefaultItemAnimator());
		recyclerView_healthmanager.setProgressView(R.layout.item_progress);
		recyclerView_healthmanager.setAdapter(healthManagerAdapter);
		recyclerView_healthmanager.setPager(this);
		sousuo_health.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onRefresh();
			}
		});

		// 设置刷新时动画的颜色，可以设置4个
		swipeRefreshLayout_healthmanager.setColorSchemeResources(android.R.color.holo_blue_light,
				android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout_healthmanager.setOnRefreshListener(this);
		onRefresh();

	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView_health.setKitkat(systemBarConfig);
	}

	@Override
	public boolean shouldLoad() {
		return isLooding;
	}

	@Override
	public void loadNextPage() {// 所有条目
		isLooding = false;
		Looding.bulid(getActivity(), null).show();
		System.out.println(UserInfoManager.getLoginUserInfo(getActivity()).toString());
		RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_MANAGER, health_edit_text.getText().toString(), pageNo, pageSize),
				getActivity(), new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject data) {
				
						try {
							Looding.bulid(getActivity(), null).dismiss();
							totalPage = data.getInt("pages");
							JSONArray array = data.getJSONArray("results");
							for (int i = 0; i < array.length(); i++) {
								Map<String, Object> item = new HashMap<String, Object>();
								JSONObject jsonObject = array.getJSONObject(i);

								item.put("id", jsonObject.getInt("id"));
								item.put("name", jsonObject.getString("name"));
								item.put("icon", jsonObject.getString("icon"));
								item.put("updateTime", jsonObject.getString("updateTime"));
								item.put("isWarning", jsonObject.getBoolean("isWarning"));

								datas.add(item);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						healthManagerAdapter.notifyDataSetChanged();
						swipeRefreshLayout_healthmanager.setRefreshing(false);
					
						if (pageNo < totalPage) {
							isLooding = true;
							pageNo++;
						} else {
							recyclerView_healthmanager.setRefreshing(false);

						}
					}
				});
	}

	@Override
	public void onRefresh() {
		pageNo = 1;
		isLooding = true;
		datas.clear();
		recyclerView_healthmanager.setRefreshing(false);
		loadNextPage();

	}

	@Override
	public void onItemClick(int position, Map<String, Object> data) {
		startActivity(new Intent(getActivity(), HealthManagerMain.class));
		customer = (int) data.get("id");
		// Intent intent = new Intent(getActivity(),TeamDetailsActivity.class);
		// intent.putExtra("id", (int)data.get("id"));
		// startActivity(intent);
		//// startActivityForResult(intent,Constens.START_ACTIVITY_FOR_RESULT);
		//// healthTeamAdapter.notifyItemChanged(position);
	}

}
