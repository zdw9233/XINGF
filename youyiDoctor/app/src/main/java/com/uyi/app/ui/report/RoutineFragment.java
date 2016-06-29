package com.uyi.app.ui.report;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.recycle.RecyclerView;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.doctor.app.R;

/**
 * 常规检测 Created by Leeii on 2016/6/23.
 */
public class RoutineFragment extends BaseFragment {
    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_routine;
    }

    @Override
    protected void onInitLayoutAfter() {

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
