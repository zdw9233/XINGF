package com.uyi.app.ui.report;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.adapter.ECGAdapter;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;

/**
 * 心电图 Created by Leeii on 2016/7/2.
 */
public class ECGFragment extends BaseFragment implements RecyclerView.ItemClickListener {
    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;
    private ECGAdapter mAdapter;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_ecg;
    }

    @Override
    protected void onInitLayoutAfter() {
        ReportActivity mActivity = (ReportActivity) getActivity();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter = new ECGAdapter(context, mActivity.getReport().getElectrocardiogram()));
        mAdapter.setItemClickListener(this);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        String[] imageUrls = mAdapter.getImageUrls();
        if (imageUrls != null && imageUrls.length > 0) {
            Intent intent = new Intent(getActivity(), ViewPagerImageActivity.class);
            intent.putExtra("imageUrl", imageUrls);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }
}
