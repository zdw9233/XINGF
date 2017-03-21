package com.uyi.app.ui.home.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.home.fragment.adapter.ReportListAdapter2_1;
import com.uyi.app.ui.report.ReportMainActivity;
import com.uyi.app.ui.report.model.ReportItem;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.widget.recycle.RecyclerListener;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.uyi.custom.app.R.id.recyclerView;

/**
 * 主诊报告列表 Created by Leeii on 2016/6/29.
 */

public class MainReportFragment extends BaseFragment implements RecyclerView.LoadMoreListener, RecyclerView.ItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    @ViewInject(recyclerView)
    private RecyclerView mRecyclerView;
    private ReportListAdapter2_1 mAdapter;
    @ViewInject(R.id.nocomtoms)
    private LinearLayout no_assessment;
    private int pageIndex = 1;
    List<ReportItem> mReportItems;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_report_list_2_1;
    }

    @Override
    protected void onInitLayoutAfter() {
        mReportItems = new ArrayList<>();
        mAdapter = new ReportListAdapter2_1(getActivity(), mReportItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerListener(this));
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    private void requestReportList() {
        int cusId = UserInfoManager.getLoginUserInfo(getActivity()).userId;
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_REPORT_LIST, cusId, pageIndex), getActivity(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                com.alibaba.fastjson.JSONObject object = JSON.parseObject(jsonObject.toString());
//                int total = object.getIntValue("total");
                List<ReportItem> reportItems = JSON.parseArray(object.getString("results"), ReportItem.class);
                if (reportItems != null && reportItems.size() != 0) {
                    if (pageIndex == 1) {
                        mReportItems.clear();
                    }
                    mReportItems.addAll(reportItems);
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    if (reportItems.size() < 20) {
                        mAdapter.setLoad(false);
                    }else{
                        pageIndex++;
                        mAdapter.setLoad(true);
                    }


                } else {
                    if (pageIndex == 1) {
                        mReportItems.clear();
                        no_assessment.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
    }

    @Override
    public void onLoadMore() {
        if (mAdapter.isLoad()) {
            requestReportList();
            mAdapter.setLoad(false);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
//        pageIndex = 1;
//        L.d(TAG, "onResume");
//        requestReportList();
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {

        ReportItem reportItem = mReportItems.get(position);
        int id = mReportItems.get(position).id;
        if(!reportItem.checked) {
            MyReport.chosehosttype = 5;
            RequestManager.postObject(String.format(Constens.SETTING_REPORT,3, id), this, null, new Response.Listener<JSONObject>() {
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
        }else{
            MyReport.chosehosttype = 6;
        }
        Intent intent = new Intent(getActivity(), ReportMainActivity.class);
        intent.putExtra("reportId", id);
        startActivityForResult(intent,10003);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        isLooding = true;
        requestReportList();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10003){
            onRefresh();
            L.e("111.onRefresh();");
        }
    }
}
