package com.uyi.app.ui.stroke;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.stroke.adapter.StrokeListAdapter;
import com.uyi.app.ui.stroke.fragment.StrockAllFragment;
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
 * Created by ThinkPad on 2017/1/12.
 * 脑卒中列表
 */
@ContentView(R.layout.activity_stroke_list)
public class StrokeListActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener{
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    @ViewInject(R.id.nocomtoms) private LinearLayout nocomtoms;
    private StrokeListAdapter strokeListAdapter;
    private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        linearLayoutManager = new LinearLayoutManager(this);
        strokeListAdapter = new StrokeListAdapter(this);
        strokeListAdapter.setOnItemClickListener(this);
        strokeListAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(strokeListAdapter);
        recyclerView.setPager(this);


        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }
    @OnClick({R.id.view_header_left_layout_return})
    public void onClick(View v){
     if(v.getId() == R.id.view_header_left_layout_return){
                finish();
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
        intent.setClass(StrokeListActivity.this,StrockAllFragment.class);
        startActivity(intent);
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
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_STROKE_LIST, UserInfoManager.getLoginUserInfo(this).userId, pageNo, pageSize),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                       L.e("_________________________________"+data.toString());
                        try {
                            Loading.bulid(activity, null).dismiss();
                            totalPage = data.getInt("pages");
                            JSONArray array = data.getJSONArray("results");
                            if(array.length()>0) {
//                                nocomtoms.setVisibility(View.GONE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);

                                for (int i = 0; i < array.length(); i++) {
                                    Map<String, Object> item = new HashMap<String, Object>();
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    item.put("id", jsonObject.getString("id"));
                                    item.put("startDate", jsonObject.getString("startDate"));
                                    item.put("endDate", jsonObject.getString("endDate"));
                                    item.put("title", jsonObject.getString("title"));
                                    datas.add(item);
                                }
                            }else{
//                                nocomtoms.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            nocomtoms.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                        }
                        strokeListAdapter.notifyDataSetChanged();
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
}
