package com.uyi.app.ui.report;

import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.doctor.app.R;

/**
 * 健康报告 Created by Leeii on 2016/7/2.
 */
public class HealthReportFragment extends BaseFragment {
    @ViewInject(R.id.et_xybd)
    private EditText et_xybd;  //血压波动趋势
    @ViewInject(R.id.et_xybd)
    private EditText et_xyyc;  //血压异常分析
    @ViewInject(R.id.et_xybd)
    private EditText et_zhfx;  //综合分析
    @ViewInject(R.id.et_xybd)
    private EditText et_jkjy;  //健康建议

    @OnClick(R.id.commit_report) //提交报告
    public void onClick(View v) {
    }

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_health_report;
    }

    @Override
    protected void onInitLayoutAfter() {

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
