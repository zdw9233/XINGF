package com.uyi.app.ui.report;

import android.support.v7.widget.LinearLayoutManager;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.UserInfoManager;
import com.uyi.app.recycle.RecyclerView;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.adapter.ReportListAdapter;
import com.uyi.doctor.app.R;

/**
 * 主诊报告列表 Created by Leeii on 2016/6/29.
 */

@ContentView(R.layout.activity_report_list)
public class ReportListActivity extends BaseActivity {

    @ViewInject(R.id.header)
    private HeaderView mHeaderView;
    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    @Override
    protected void onInitLayoutAfter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new ReportListAdapter(this));
        mHeaderView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(this).icon)
                .showTitle(true).showRight(true).setTitle("个人中心")
                 .setTitleColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        mHeaderView.setKitkat(systemBarConfig);
    }
}
