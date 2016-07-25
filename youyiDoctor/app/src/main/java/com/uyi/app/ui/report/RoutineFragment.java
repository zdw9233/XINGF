package com.uyi.app.ui.report;

import android.support.v7.widget.LinearLayoutManager;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.recycle.RecyclerView;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.adapter.RoutineAdapter;
import com.uyi.doctor.app.R;

/**
 * 常规检测 Created by Leeii on 2016/6/23.
 */
public class RoutineFragment extends BaseFragment {
    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private RoutineAdapter mAdapter;
    private ReportActivity mActivity;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_routine;
    }

    @Override
    protected void onInitLayoutAfter() {
        mActivity = (ReportActivity) getActivity();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new RoutineAdapter(mActivity, mActivity.getReport());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
