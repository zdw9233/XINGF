package com.uyi.app.ui.report;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.model.Report;
import com.uyi.app.utils.ImageUtil;
import com.uyi.custom.app.R;

/**
 * 趋势图 Created by Leeii on 2016/7/2.
 */
public class TrendMapFragment extends BaseFragment {
    @ViewInject(R.id.kfxt)
    private SimpleDraweeView kfxt;  //空腹血糖
    @ViewInject(R.id.chxt)
    private SimpleDraweeView chxt;  //餐后血糖

    private String kfxtUrl;
    private String chxtUrl;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_trend_map;
    }

    @Override
    protected void onInitLayoutAfter() {
        ReportActivity mActivity = (ReportActivity) getActivity();
        Report report = mActivity.getReport();

        ImageUtil.load(kfxtUrl = report.bloodPressure_pic, kfxt);
        ImageUtil.load(chxtUrl = report.bloodSugar_pic, chxt);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }

    @OnClick({R.id.kfxt, R.id.chxt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kfxt:
                onImageClick(kfxtUrl);
                break;
            case R.id.chxt:
                onImageClick(chxtUrl);
                break;
        }
    }

    private void onImageClick(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(getActivity(), ViewPagerImageActivity.class);
            intent.putExtra("imageUrl", new String[]{url});
            startActivity(intent);
        }
    }
}
