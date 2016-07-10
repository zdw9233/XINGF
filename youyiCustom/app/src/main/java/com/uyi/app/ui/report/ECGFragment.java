package com.uyi.app.ui.report;

import android.support.v7.widget.LinearLayoutManager;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.adapter.ECGAdapter;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;

/**
 * 心电图 Created by Leeii on 2016/7/2.
 */
public class ECGFragment extends BaseFragment {
    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_ecg;
    }

    @Override
    protected void onInitLayoutAfter() {
        ReportActivity  mActivity = (ReportActivity) getActivity();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new ECGAdapter(context,mActivity.getReport().getElectrocardiogram()));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
