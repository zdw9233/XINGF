package com.uyi.app.ui.health;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.adapter.ThreeTopListAdapter;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/7/1.
 */
@ContentView(R.layout.activity_preview_personal_program)
public class ThreeTopListActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener{
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.new_report) private Button new_report;
    @ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private ThreeTopListAdapter healthDatabaseAdapter;
    private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("三甲方案").setTitleColor(getResources().getColor(R.color.blue));
        linearLayoutManager = new LinearLayoutManager(this);
        healthDatabaseAdapter = new ThreeTopListAdapter(this);
        healthDatabaseAdapter.setOnItemClickListener(this);
        healthDatabaseAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(healthDatabaseAdapter);
        recyclerView.setPager(this);


        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }
    @OnClick({R.id.new_report})
    public void onClick(View v){
        if(v.getId() == R.id.new_report){
            Intent intent = new Intent();
            intent.setClass(ThreeTopListActivity.this,GreateThreeTopActivity.class);
            startActivityForResult(intent, 10003);

        }
    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        Intent intent = new Intent();
        intent.putExtra("id",data.get("id").toString());
        intent.setClass(ThreeTopListActivity.this,ThreeTopDetailsActivity.class);
        startActivityForResult(intent,10003);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        isLooding = true;
        datas.clear();
        recyclerView.setRefreshing(false);
        loadNextPage();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        onRefresh();
//    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        Loading.bulid(activity, null).show();
//        System.out.println(UserInfoManager.getLoginUserInfo(activity).toString());
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_THREE_TOP_LIST, FragmentHealthListManager.customer, pageNo, pageSize),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        System.out.print("_________________________________"+data.toString());
                        try {
                            Loading.bulid(activity, null).dismiss();
                            totalPage = data.getInt("pages");
                            JSONArray array = data.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                Map<String, Object> item = new HashMap<String, Object>();
                                JSONObject jsonObject = array.getJSONObject(i);

                                item.put("id", jsonObject.getString("id"));
                                item.put("updateTime", jsonObject.getString("updateTime"));
                                item.put("status", jsonObject.getString("status"));
                                item.put("doctorName", jsonObject.getString("doctorName"));


                                datas.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        healthDatabaseAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                        if (pageNo < totalPage) {
                            isLooding = true;
                            pageNo++;
                        } else {
                            recyclerView.setRefreshing(false);

                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10003){
            if(resultCode == RESULT_OK){
                onRefresh();
            }
        }
    }
}
