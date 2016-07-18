package com.uyi.app.ui.report;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.model.Report;
import com.uyi.app.utils.ImageUtil;
import com.uyi.app.utils.L;
import com.uyi.doctor.app.R;

/**
 * 趋势图 Created by Leeii on 2016/7/2.
 */
public class TrendMapFragment extends BaseFragment {
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
        ReportActivity mActivity = (ReportActivity) getActivity();
        Report report = mActivity.getReport();

        L.e(report.bloodPressure_pic);
        L.e(report.bloodSugar_pic);
        ImageUtil.load(report.bloodPressure_pic, kfxt);
        ImageUtil.load(report.bloodSugar_pic, chxt);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
