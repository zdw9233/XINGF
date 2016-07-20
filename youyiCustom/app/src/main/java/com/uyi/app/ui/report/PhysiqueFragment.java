package com.uyi.app.ui.report;

import android.text.Html;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.model.Report;
import com.uyi.custom.app.R;

import java.util.Locale;

/**
 * 中医体质识别 Created by Leeii on 2016/7/2.
 */
public class PhysiqueFragment extends BaseFragment {
    @ViewInject(R.id.zfl)
    private TextView zfl;     //脂肪率
    @ViewInject(R.id.jcdx)
    private TextView jcdx;   //基础代谢
    @ViewInject(R.id.sfhl)
    private TextView sfhl;   //水分含量
    @ViewInject(R.id.yw)
    private TextView yw;       //腰围
    @ViewInject(R.id.tw)
    private TextView tw;       //臀围
    @ViewInject(R.id.ytb)
    private TextView ytb;     //腰臀比

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_physique;
    }

    @Override
    protected void onInitLayoutAfter() {
        ReportActivity mActivity = (ReportActivity) getActivity();
        Report report = mActivity.getReport();
        zfl.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>脂肪率： </font><font color='#0ea6fa'>%s</font>", report.fatPercentage).equals("null")?"":String.format(Locale.CHINA, "<font color='#252525'>脂肪率： </font><font color='#0ea6fa'>%s</font>", report.fatPercentage)));
        jcdx.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>基础代谢： </font><font color='#0ea6fa'>%s</font>", report.basalMetabolism).equals("null")?"":String.format(Locale.CHINA, "<font color='#252525'>基础代谢： </font><font color='#0ea6fa'>%s</font>", report.basalMetabolism)));
        sfhl.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>水分含量： </font><font color='#0ea6fa'>%s</font>", report.waterContent).equals("null")?"":String.format(Locale.CHINA, "<font color='#252525'>水分含量： </font><font color='#0ea6fa'>%s</font>", report.waterContent)));
        yw.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>腰围： </font><font color='#0ea6fa'>%s</font>", report.waist).equals("null")?"":String.format(Locale.CHINA, "<font color='#252525'>腰围： </font><font color='#0ea6fa'>%s</font>", report.waist)));
        tw.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>臀围： </font><font color='#0ea6fa'>%s</font>", report.hipline).equals("null")?"":String.format(Locale.CHINA, "<font color='#252525'>臀围： </font><font color='#0ea6fa'>%s</font>", report.hipline)));
        ytb.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>腰臀比： </font><font color='#0ea6fa'>%s</font>", report.whr).equals("null")?"":String.format(Locale.CHINA, "<font color='#252525'>腰臀比： </font><font color='#0ea6fa'>%s</font>", report.whr)));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
    }
}
