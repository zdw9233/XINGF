package com.uyi.app.ui.health.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.common.fragment.FragmentCustomjksjk;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.adapter.OtherItemsAdapter;
import com.uyi.app.ui.personal.customer.CustomerActivity;
import com.uyi.app.utils.L;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/8/30.
 * 其它项目
 */
public class OtherItemsFragment extends BaseFragment implements EndlessRecyclerView.Pager,SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private OtherItemsAdapter otherItemsAdapter;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_other_item_health;
    }

    @Override
    protected void onInitLayoutAfter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        otherItemsAdapter = new OtherItemsAdapter(getActivity());
//        healthDatabaseAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>>() {
//            @Override
//            public void onItemClick(int position, Map<String, Object> data) {
//                String id = data.get("id").toString();
//                Intent intent = new Intent(getActivity(), HealthDatabaseDetails.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });
        otherItemsAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(otherItemsAdapter);
        recyclerView.setPager(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
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
        Loading.bulid(getActivity(), null).show();
        RequestManager.getObject(String.format(Constens.HEALTH_CHECK_INFOS, CustomerActivity.customer, FragmentCustomjksjk.startDate, FragmentCustomjksjk.endDate, pageNo, pageSize,"5"), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                Loading.bulid(getActivity(), null).dismiss();
                try {
                    L.d(TAG, data.toString());
                    totalPage = data.getInt("pages");
                    if (pageNo == 1) datas.clear();
                    JSONArray array = data.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
                        item.put("id", jsonObject.getInt("id"));
                        item.put("uploadTime", jsonObject.getString("uploadTime").substring(0,10));
                        if(jsonObject.has("urineAcid")){
                            item.put("urineAcid", jsonObject.getString("urineAcid"));
                        }else{
                            item.put("urineAcid","NULL");
                        }
                        if(jsonObject.has("urineAcidWarning")){
                            item.put("urineAcidWarning", jsonObject.getString("urineAcidWarning"));
                        }else{
                            item.put("urineAcidWarning","NULL");
                        }
                        datas.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                otherItemsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

                if (pageNo <= totalPage) {
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

