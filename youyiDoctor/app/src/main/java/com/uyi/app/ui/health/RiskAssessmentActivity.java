package com.uyi.app.ui.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.adapter.RiskAssessmentAdapter;
import com.uyi.app.ui.personal.customer.CustomerActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.SerializableMap;
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
@ContentView(R.layout.risk_assessment)
public class RiskAssessmentActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener{
    @ViewInject(R.id.lay) private LinearLayout lay;
    @ViewInject(R.id.new_report) private Button new_report;
    @ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private RiskAssessmentAdapter healthDatabaseAdapter;
    private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
    @Override
    protected void onInitLayoutAfter() {
        linearLayoutManager = new LinearLayoutManager(this);
        healthDatabaseAdapter = new RiskAssessmentAdapter(this);
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
@OnClick({R.id.new_report,R.id.back})
public void onClick(View v){
    if(v.getId() == R.id.new_report){
        Intent intent = new Intent();
        intent.setClass(RiskAssessmentActivity.this,NewRiskAssessmentActivity.class);
        startActivityForResult(intent, 10003);

    }else if(v.getId() == R.id.back){
        finish();
    }
}
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        Intent intent = new Intent();
        SerializableMap myMap=new SerializableMap();
        myMap.setMap(data);//将map数据添加到封装的myMap中
        Bundle bundle=new Bundle();
        bundle.putSerializable("map", myMap);
        intent.putExtras(bundle);
        intent.setClass(RiskAssessmentActivity.this,RiskAssessmentDetailsActivity.class);
        startActivityForResult(intent, 10003);
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
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_RISK, CustomerActivity.customer, pageNo, pageSize),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        try {
                            L.e(data.toString());
                            Loading.bulid(activity, null).dismiss();
                            totalPage = data.getInt("pages");
                            JSONArray array = data.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                Map<String, Object> item = new HashMap<String, Object>();
                                JSONObject jsonObject = array.getJSONObject(i);
                                item.put("verifyStatus", jsonObject.getString("verifyStatus"));
                                if(jsonObject.has("verifyMsg")){
                                    item.put("verifyMsg", jsonObject.getString("verifyMsg"));
                                }else{
                                    item.put("verifyMsg", "");
                                }

                                item.put("id", jsonObject.getString("id"));
                                item.put("createTime", jsonObject.getString("createTime"));
                                item.put("doc_name", jsonObject.getString("doc_name"));
                                if(jsonObject.has("percentageASVCD")){
                                    item.put("percentageASVCD", jsonObject.getString("percentageASVCD"));
                                }else{
                                    item.put("percentageASVCD", "%");
                                }
                                if(jsonObject.has("percentageICVD")){
                                    item.put("percentageICVD", jsonObject.getString("percentageICVD"));
                                }else{
                                    item.put("percentageICVD", "%");
                                }
                                if(jsonObject.has("bloodPressureConditions")){
                                    item.put("bloodPressureConditions", jsonObject.getString("bloodPressureConditions"));
                                }else{
                                    item.put("bloodPressureConditions", "");
                                }
                                if(jsonObject.has("bloodSugarConditions")){
                                    item.put("bloodSugarConditions", jsonObject.getString("bloodSugarConditions"));
                                }else{
                                    item.put("bloodSugarConditions", "");
                                }
                                if(jsonObject.has("healthIndicator")){
                                    item.put("healthIndicator", jsonObject.getString("healthIndicator"));
                                }else{
                                    item.put("healthIndicator", "");
                                }
                                if(jsonObject.has("advice")){
                                    item.put("advice", jsonObject.getString("advice"));
                                }else{
                                    item.put("advice", "");
                                }
                                if(jsonObject.has("content")){
                                    item.put("content", jsonObject.getString("content"));
                                }else{
                                    item.put("content", "");
                                }
                                if(jsonObject.has("percentage")){
                                    item.put("percentage", jsonObject.getString("percentage"));
                                }else{
                                    item.put("percentage", "");
                                }

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
