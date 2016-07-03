package com.uyi.app.ui.report;

import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.doctor.app.R;

/**
 * 心电图 Created by Leeii on 2016/7/2.
 */
public class ECGFragment extends BaseFragment {
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_ecg;
    }

    @Override
    protected void onInitLayoutAfter() {

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
