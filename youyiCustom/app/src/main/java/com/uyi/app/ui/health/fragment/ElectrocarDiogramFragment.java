package com.uyi.app.ui.health.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.health.ElectrocarDiogramDetailsActivity;
import com.uyi.app.ui.health.HealthDatabaseActivity;
import com.uyi.app.ui.health.adapter.ElectrocarDiogramAdapter;
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
 * Created by ThinkPad on 2016/8/30.
 * 心电图
 */
public class ElectrocarDiogramFragment extends BaseFragment implements EndlessRecyclerView.Pager,SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.nocomtoms)
    private LinearLayout nocomtoms;
    @ViewInject(R.id.hascontent)
    private LinearLayout hascontent;
    private LinearLayoutManager linearLayoutManager;
    private ElectrocarDiogramAdapter electrocarDiogramAdapter;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_electrocar_diogran_health;
    }

    @Override
    protected void onInitLayoutAfter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        electrocarDiogramAdapter = new ElectrocarDiogramAdapter(getActivity());
        electrocarDiogramAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>>() {
            @Override
            public void onItemClick(int position, Map<String, Object> data) {
                String id = data.get("id").toString();
                Intent intent = new Intent(getActivity(), ElectrocarDiogramDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        electrocarDiogramAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(electrocarDiogramAdapter);
        recyclerView.setPager(this);
        //设置刷新时动画的颜色，可以设置4个
        onRefresh();
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        RequestManager.getObject(String.format(Constens.HEALTH_CHECK_INFOS, HealthDatabaseActivity.startDate, HealthDatabaseActivity.endDate, pageNo, pageSize,"6"), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.d(TAG, data.toString());
                    totalPage = data.getInt("pages");
                    if (pageNo == 1) datas.clear();
                    JSONArray array = data.getJSONArray("results");
                    if(array.length() > 0){
                        nocomtoms.setVisibility(View.GONE);
                        hascontent.setVisibility(View.VISIBLE);
                    for (int i = 0; i < array.length(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
                        item.put("id", jsonObject.getInt("id"));
                        item.put("uploadTime", jsonObject.getString("uploadTime").replace(" ","\n"));
                        JSONArray array1 = array.getJSONObject(i).getJSONArray("ecg");
                        for (int j = 0; j < array1.length(); j++) {
                            Map<String, Object> item1 = new HashMap<String, Object>();
                            JSONObject jsonObject1 = array1.getJSONObject(j);
                            item1.put("id", jsonObject1.getInt("id"));
                            item1.put("url", jsonObject1.getString("url"));
                            item.put("ecg", item1);
                        }
                        datas.add(item);
                    }
                    }else{
                        hascontent.setVisibility(View.GONE);
                        nocomtoms.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hascontent.setVisibility(View.GONE);
                    nocomtoms.setVisibility(View.VISIBLE);
                }
                electrocarDiogramAdapter.notifyDataSetChanged();

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
    public void onRefresh() {
        pageNo = 1;
        isLooding = true;
        recyclerView.setRefreshing(false);
        loadNextPage();
    }
}

