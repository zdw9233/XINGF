package com.uyi.app.ui.stroke;

import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.stroke.model.StrokeFollowUpRecordJson;
import com.uyi.custom.app.R;

import java.util.List;

/**
 * Created by ThinkPad on 2017/1/12.
 * 卒中预防半年报告
 */
@ContentView(R.layout.activity_stroke_zzyfbnbg)
public class StrokeZzyfbnbgActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.zzyfbnbg_1) private TextView zzyfbnbg_1;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("详情").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(getIntent().getStringExtra("data"), StrokeFollowUpRecordJson.class);
        zzyfbnbg_1.setText(reportItems.get(5).getStrokePreventiveInterventionReport() == null ? "" : reportItems.get(5).getStrokePreventiveInterventionReport().getPlanAdjustment());

    }
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
