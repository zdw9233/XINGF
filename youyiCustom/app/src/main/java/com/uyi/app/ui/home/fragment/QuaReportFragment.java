package com.uyi.app.ui.home.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.home.fragment.adapter.QuaReportAdapter2_1;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ThinkPad on 2017/2/28.
 */

public class QuaReportFragment extends BaseFragment implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private QuaReportAdapter2_1 quaReportAdapter2_1;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.nocomtoms)
    private LinearLayout no_assessment;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_preview_personal_program_list;
    }

    @Override
    protected void onInitLayoutAfter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        quaReportAdapter2_1 = new QuaReportAdapter2_1(getActivity());
        quaReportAdapter2_1.setOnItemClickListener(this);
        quaReportAdapter2_1.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(quaReportAdapter2_1);
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
    public void onItemClick(int position, Map<String, Object> data) {
        if(data.get("checked").toString().equals("false") ) {
            RequestManager.postObject(String.format(Constens.SETTING_REPORT, 4, data.get("id").toString()), this, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new RequestErrorListener() {
                @Override
                public void requestError(VolleyError e) {
                    if (e.networkResponse != null) {
                        if (e.networkResponse.statusCode == 200) {
                        } else {
                            T.showShort(getActivity(), ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                        }
                    } else {
                    }
                }
            });
        }
//        Intent intent = new Intent();
//        SerializableMap myMap = new SerializableMap();
//        myMap.setMap(data);//将map数据添加到封装的myMap中
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("map", myMap);
//        intent.putExtras(bundle);
//        intent.setClass(getActivity(), PreviewPersonalProgramActivity.class);
//        startActivityForResult(intent, 10003);
        Intent intent = new Intent();
        intent.putExtra("startDate",data.get("startTime").toString());
        intent.putExtra("endDate",data.get("endTime").toString());
        intent.setClass(getContext(),QuaReportDetailsActivity.class);
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
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_MONTH_AND_QUA, 2, pageNo, pageSize),
                getActivity(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        L.e(data.toString());
                        try {
                            totalPage = data.getInt("pages");
                            JSONArray array = data.getJSONArray("results");
//                            if (pageNo == 1) datas.clear();
                            for (int i = 0; i < array.length(); i++) {
                                Map<String, Object> item = new HashMap<String, Object>();
                                JSONObject jsonObject = array.getJSONObject(i);
                                item.put("startTime", jsonObject.getString("startTime"));
                                item.put("endTime", jsonObject.getString("endTime"));
                                item.put("id", jsonObject.getString("id"));
                                item.put("checked", jsonObject.getString("checked"));
                                item.put("reportType", jsonObject.getString("reportType"));
                                datas.add(item);
                            }
                            if (datas.size() > 0) {
                                no_assessment.setVisibility(View.GONE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                            } else {
                                no_assessment.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        quaReportAdapter2_1.notifyDataSetChanged();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10003) {
                onRefresh();
        }
    }
}

