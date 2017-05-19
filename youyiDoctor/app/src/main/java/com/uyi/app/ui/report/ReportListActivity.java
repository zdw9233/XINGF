package com.uyi.app.ui.report;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.recycle.RecyclerListener;
import com.uyi.app.recycle.RecyclerView;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.customer.CustomerActivity;
import com.uyi.app.ui.report.adapter.ReportListAdapter;
import com.uyi.app.ui.report.model.ReportItem;
import com.uyi.doctor.app.R;
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
    @ViewInject(R.id.lay) private LinearLayout lay;
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
        mAdapter.setItemClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerListener(this));
        requestReportList();
    }

    private void requestReportList() {
        isLooding = false;
        int cusId = CustomerActivity.customer;
        Loading.bulid(this, null).show();
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_REPORT_LIST, cusId, pageIndex), this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Loading.bulid(ReportListActivity.this, null).dismiss();
                com.alibaba.fastjson.JSONObject object = JSON.parseObject(jsonObject.toString());
                List<ReportItem> reportItems = JSON.parseArray(object.getString("results"), ReportItem.class);
                if (reportItems != null && reportItems.size() != 0) {
                    if (pageIndex == 1) {
                        mReportItems.clear();
                    }
                    mReportItems.addAll(reportItems);
                    mAdapter.notifyDataSetChanged();
                    if (reportItems.size() < 20) {
                        mAdapter.setLoad(false);
                    }else{
                        pageIndex++;
                        mAdapter.setLoad(true);
                    }
                } else {
                    if (pageIndex == 1) {
                        mReportItems.clear();
                    }
                }
            }
        });

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
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
        ReportItem reportItem = mReportItems.get(position);
        int id = reportItem.id;
        String editStatus = reportItem.editStatus;
        int status = -1;
        if(editStatus.equals("DONE")){
            status = 1;
        }else{
            status = 0;
        }
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("reportId", id);
        intent.putExtra("status", status);
        startActivityForResult(intent, 0x100);
    }

    @OnClick({R.id.new_report,R.id.back})
    public void onClick(View view) {
        if(view.getId()==R.id.back){
            finish();
        }else if(view.getId() == R.id.new_report){
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("reportId", 0);
        intent.putExtra("status", 0);
        startActivityForResult(intent, 0x100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x100 && resultCode == 0x200) {
            pageIndex = 1;
            requestReportList();
        }
    }
}
