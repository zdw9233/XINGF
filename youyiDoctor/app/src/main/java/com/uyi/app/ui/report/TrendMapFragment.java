package com.uyi.app.ui.report;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.doctor.app.R;

/**
 * 趋势图 Created by Leeii on 2016/7/2.
 */
public class TrendMapFragment extends BaseFragment {
    @ViewInject(R.id.xyqst)
    private SimpleDraweeView xyqst;  //血压趋势图
    @ViewInject(R.id.xtqst)
    private SimpleDraweeView xtqst;  //血糖趋势图

    @OnClick(R.id.commit_report)  //提交报告
    public void onClick(View v) {
    }

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_trend_map;
    }

    @Override
    protected void onInitLayoutAfter() {

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
