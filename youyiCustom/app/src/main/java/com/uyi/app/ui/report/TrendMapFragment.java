package com.uyi.app.ui.report;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.model.Report;
import com.uyi.app.utils.ImageUtil;
import com.uyi.custom.app.R;

/**
 * 趋势图 Created by Leeii on 2016/7/2.
 */
public class TrendMapFragment extends BaseFragment {
    @ViewInject(R.id.ssy)
    private SimpleDraweeView ssy;  //收缩压
    @ViewInject(R.id.szy)
    private SimpleDraweeView szy;  //舒张压
    @ViewInject(R.id.kfxt)
    private SimpleDraweeView kfxt;  //空腹血糖
    @ViewInject(R.id.chxt)
    private SimpleDraweeView chxt;  //餐后血糖


    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_trend_map;
    }

    @Override
    protected void onInitLayoutAfter() {
        ReportActivity  mActivity = (ReportActivity) getActivity();
        Report report  = mActivity.getReport();
        ImageUtil.load(report.getMorningSystolicPressure_pic(),ssy);
        ImageUtil.load(report.getMorningSystolicPressure_pic(),szy);
        ImageUtil.load(report.getFastBloodSugar_pic(),kfxt);
        ImageUtil.load(report.getPostPrandilaSugar_pic(),chxt);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
