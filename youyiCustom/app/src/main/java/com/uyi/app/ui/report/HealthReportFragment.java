package com.uyi.app.ui.report;

import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.model.Report;
import com.uyi.custom.app.R;

/**
 * 健康报告 Created by Leeii on 2016/7/2.
 */
public class HealthReportFragment extends BaseFragment {
    @ViewInject(R.id.et_xybd)
    private TextView et_xybd;  //血压波动趋势
    @ViewInject(R.id.et_xyyc)
    private TextView et_xyyc;  //血压异常分析
    @ViewInject(R.id.et_zhfx)
    private TextView et_zhfx;  //综合分析
    @ViewInject(R.id.et_jkjy)
    private TextView et_jkjy;  //健康建议


    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_health_report;
    }

    @Override
    protected void onInitLayoutAfter() {
        ReportActivity mActivity = (ReportActivity) getActivity();
        Report report = mActivity.getReport();

        et_xybd.setText(report.getComment1());
        et_xyyc.setText(report.getComment2());
        et_zhfx.setText(report.getComment3());
        et_jkjy.setText(report.getComment4());

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
