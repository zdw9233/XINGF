package com.uyi.app.ui.home.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.home.fragment.adapter.ZiXunAdapter;
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
 * Created by ThinkPad on 2016/7/1.
 */
@ContentView(R.layout.activity_preview_personal_program_list)
public class ZiXunListActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener{
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private ZiXunAdapter ziXunAdapter;
    private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(false).setTitle("健康资讯").setTitleColor(getResources().getColor(R.color.blue));
        linearLayoutManager = new LinearLayoutManager(this);
        ziXunAdapter = new ZiXunAdapter(this);
        ziXunAdapter.setOnItemClickListener(this);
        ziXunAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(ziXunAdapter);
        recyclerView.setPager(this);


        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        Intent intent = new Intent();
        intent.putExtra("id", data.get("id").toString());
        intent.setClass(ZiXunListActivity.this,ZiXunDetailsActivity.class);
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


    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.HEALTH_ZIXUN, 1, pageNo, pageSize),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        L.e(data.toString());
                        try {
                            Loading.bulid(activity, null).dismiss();
                            totalPage = data.getInt("pages");
                            JSONArray array = data.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                Map<String, Object> item = new HashMap<String, Object>();
                                JSONObject jsonObject = array.getJSONObject(i);
                                item.put("id", jsonObject.getString("id"));
                                item.put("title", jsonObject.getString("title"));
                                item.put("lastUpdateTime", jsonObject.getString("lastUpdateTime"));

                                datas.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ziXunAdapter.notifyDataSetChanged();
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
