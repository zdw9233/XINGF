package com.uyi.app.ui.health;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.adapter.RiskAssessmentAdapter;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/7/1.
 */
@ContentView(R.layout.risk_assessment)
public class RiskAssessmentActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener,AbstractSpinerAdapter.IOnItemSelectListener {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.new_risk_assessment) private EditText new_risk_assessment;
    @ViewInject(R.id.risk_indexs) private TextView risk_indexs;
    @ViewInject(R.id.new_risk_submit) private Button new_risk_submit;
    @ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private RiskAssessmentAdapter healthDatabaseAdapter;
    private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
    private SpinerPopWindow spinerPopWindow;
    List<String> riskIndixs = new ArrayList<String>() ;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("风险评估").setTitleColor(getResources().getColor(R.color.blue));
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
        for (int i = 0; i < 100;i++){
            riskIndixs.add(""+i+"%");
        }
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setItemListener(this);
        spinerPopWindow.refreshData(riskIndixs, 1);


        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }
@OnClick({R.id.new_risk_submit,R.id.risk_indexs})
public void onClick(View v){
    if(v.getId() == R.id.risk_indexs){
        spinerPopWindow.setWidth(risk_indexs.getWidth());
        spinerPopWindow.refreshData(riskIndixs, 0);
        spinerPopWindow.showAsDropDown(risk_indexs);
    }else if(v.getId() ==R.id.new_risk_submit ){
        try {
            JSONObject params = new JSONObject();
            params.put("percentage",risk_indexs.getText().toString());
            params.put("content",""+new_risk_assessment.getText().toString());
            params.put("customerid",FragmentHealthListManager.customer+"");
            params.put("checked",false);

            RequestManager.postObject(Constens.DOCTOR_HEALTH_RISK_INSERT, activity, params, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject data) {
                    System.out.print("+++++++++++++++++++++"+data.toString());
                    T.showShort(activity, "提交成功!");
                    onRefresh();
                }
            }, new RequestErrorListener() {
                public void requestError(VolleyError e) {
//                    if(e.networkResponse != null){
//                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
//                    }else{
//                        T.showShort(activity, "提交成功!");
//                        onRefresh();
//                    }
                    T.showShort(activity, "提交失败!");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {

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
        Loading.bulid(activity, null).show();
//        System.out.println(UserInfoManager.getLoginUserInfo(activity).toString());
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_RISK, FragmentHealthListManager.customer, pageNo, pageSize),
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

                                item.put("content", jsonObject.getString("content"));
                                item.put("createTime", jsonObject.getString("createTime"));
                                item.put("percentage", jsonObject.getString("percentage"));
                                item.put("doc_name", jsonObject.getString("doc_name"));
//                                item.put("createTime", jsonObject.getString("createTime"));
//								item.put("isWarning", jsonObject.getBoolean("isWarning"));
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
    public void onItemClick(int pos) {
        risk_indexs.setText(riskIndixs.get(pos));
    }
}
