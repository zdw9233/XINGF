package com.uyi.app.ui.report;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.recycle.RecyclerListener;
import com.uyi.app.recycle.RecyclerView;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.FragmentHealthListManager;
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
        mHeaderView.showLeftReturn(true)
                .showTitle(true).showRight(true).setTitle("主诊报告列表")
                .setTitleColor(getResources().getColor(R.color.blue));
        mRecyclerView.addOnScrollListener(new RecyclerListener(this));
        requestReportList();
    }

    private void requestReportList() {
        isLooding = false;
        int cusId = FragmentHealthListManager.customer;
        Loading.bulid(this, null).show();
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_REPORT_LIST, cusId, pageIndex), this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Loading.bulid(ReportListActivity.this, null).dismiss();
//                L.e("data",jsonObject.toString());
                com.alibaba.fastjson.JSONObject object = JSON.parseObject(jsonObject.toString());
//                int total = object.getIntValue("total");
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
//                        T.showShort(ReportListActivity.this, "您还没有报告");
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
        ReportItem reportItem = mReportItems.get(position);
        int id = reportItem.id;
        String editStatus = reportItem.editStatus;
        int status = -1;
        if(editStatus.equals("DONE")){
            status = 1;
        }else{
            status = 0;
        }

//        if (!reportItem.checked) {
//            mReportItems.get(position).checked = true;
//            mAdapter.notifyItemChanged(position);
//        }
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("reportId", id);
        intent.putExtra("status", status);
        startActivityForResult(intent, 0x100);
    }

    @OnClick(R.id.new_report)
    public void onClick(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("reportId", 0);
        intent.putExtra("status", 0);
        startActivityForResult(intent, 0x100);
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
