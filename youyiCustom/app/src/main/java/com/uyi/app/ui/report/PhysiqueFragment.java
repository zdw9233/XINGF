package com.uyi.app.ui.report;

import android.graphics.drawable.Drawable;
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
        zfl.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>脂肪率： </font><font color='#0ea6fa'>%s</font>", report.fatPercentage)));

        jcdx.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>基础代谢： </font><font color='#0ea6fa'>%s</font>", report.basalMetabolism)));
        sfhl.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>水分含量： </font><font color='#0ea6fa'>%s</font>", report.waterContent)));
        yw.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>腰围： </font><font color='#0ea6fa'>%s</font>", report.waist)));
        tw.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>臀围： </font><font color='#0ea6fa'>%s</font>", report.hipline)));
        ytb.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>腰臀比： </font><font color='#0ea6fa'>%s</font>", report.whr)));
        setDrawable(report.fatPercentage_tred,zfl);
        setDrawable(report.basalMetabolism_tred,jcdx);
        setDrawable(report.waterContent_tred,sfhl);
        setDrawable(report.waist_tred,yw);
        setDrawable(report.hipline_tred,tw);
        setDrawable(report.whr_tred,ytb);
    }
public void setDrawable(String type , TextView view){
    if (type.equals("1")) {
        //获取资源图片
        Drawable leftDrawable = context.getResources().getDrawable(R.drawable.high2x);
        //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
        leftDrawable.setBounds(0, 0, 20, 20);
        //设置图片在TextView中的位置
        view.setCompoundDrawables(null, null, leftDrawable, null);
    } else if(type.equals("2")){
        view.setCompoundDrawables(null, null, null, null);
    } else if(type.equals("3")){
        //获取资源图片
        Drawable leftDrawable = context.getResources().getDrawable(R.drawable.low2x);
        //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
        leftDrawable.setBounds(0, 0, 20, 20);
        //设置图片在TextView中的位置
        view.setCompoundDrawables(null, null, leftDrawable, null);
    }
}
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
    }
}
