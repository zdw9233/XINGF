package com.uyi.app.ui.report;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.report.adapter.ReportListAdapter;
import com.uyi.app.ui.report.model.ReportItem;
import com.uyi.app.utils.T;
import com.uyi.app.widget.recycle.RecyclerListener;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 主诊报告列表 Created by Leeii on 2016/6/29.
 */

@ContentView(R.layout.activity_report_list)
public class ReportListActivity extends BaseActivity implements RecyclerView.LoadMoreListener, RecyclerView.ItemClickListener {

    @ViewInject(R.id.header)
    private HeaderView mHeaderView;
    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;
    private ReportListAdapter mAdapter;

    private int pageIndex = 1;

    List<ReportItem> mReportItems;

    @Override
    protected void onInitLayoutAfter() {
        mReportItems = new ArrayList<>();
        mAdapter = new ReportListAdapter(this, mReportItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setCallBack(new ReportListAdapter.CallBack() {
//            @Override
//            public void onclick(int position) {
//                T.showShort(ReportListActivity.this, "Position = " + position);
//            }
//        });
        mAdapter.setItemClickListener(this);
        mHeaderView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(this).icon)
                .showTitle(true).showRight(true).setTitle("主诊报告列表")
                .setTitleColor(getResources().getColor(R.color.blue));
        mRecyclerView.addOnScrollListener(new RecyclerListener(this));
        Loading.bulid(this, null).show();
        requestReportList();
    }

    private void requestReportList() {
        int cusId = UserInfoManager.getLoginUserInfo(this).userId;

        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_REPORT_LIST, 13, pageIndex), this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Loading.bulid(ReportListActivity.this, null).dismiss();
                com.alibaba.fastjson.JSONObject object = JSON.parseObject(jsonObject.toString());
//                int total = object.getIntValue("total");
                List<ReportItem> reportItems = JSON.parseArray(object.getString("results"), ReportItem.class);
                if (reportItems != null && reportItems.size() != 0) {
                    if (pageIndex == 1) {
                        mReportItems.clear();
                    }
                    mReportItems.addAll(reportItems);
                    if (reportItems.size() < 20) {
                        mAdapter.setLoad(false);
                    }
                    pageIndex++;
                    mAdapter.notifyDataSetChanged();
                } else {
                    if (pageIndex == 1) {
                        mReportItems.clear();
                        T.showShort(ReportListActivity.this, "您还没有报告");
                    }
                }
            }
        });

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        mHeaderView.setKitkat(systemBarConfig);
    }

    @Override
    public void onLoadMore() {
        if (mAdapter.isLoad()) {
            requestReportList();
            mAdapter.setLoad(false);
        }
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        int id = mReportItems.get(position).id;
        Intent intent = new Intent(this, ReportMainActivity.class);
        intent.putExtra("reportId", id);
        startActivity(intent);
    }
}
