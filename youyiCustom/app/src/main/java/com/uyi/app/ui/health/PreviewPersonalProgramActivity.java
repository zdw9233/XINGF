package com.uyi.app.ui.health;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.utils.SerializableMap;
import com.uyi.custom.app.R;

/**
 * Created by ThinkPad on 2016/9/18.
 *
 */
@ContentView(R.layout.activity_preview_personal_program)
public class PreviewPersonalProgramActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.lrr) private TextView lrr;
    @ViewInject(R.id.lrsj) private TextView lrsj;
    @ViewInject(R.id.no_selution) private TextView no_selution;
    @ViewInject(R.id.scroll) private ScrollView scroll;

    @ViewInject(R.id.layout_icvd) private LinearLayout layout_icvd;
    @ViewInject(R.id.layout_ASCVD) private LinearLayout layout_ASCVD;
    @ViewInject(R.id.layout_xy) private LinearLayout layout_xy;
    @ViewInject(R.id.layout_xt) private LinearLayout layout_xt;
    @ViewInject(R.id.layout_zdgz) private LinearLayout layout_zdgz;
    @ViewInject(R.id.layout_jkjy) private LinearLayout layout_jkjy;

    @ViewInject(R.id.xygljy) private TextView xygljy;
    @ViewInject(R.id.xtgljy) private TextView xtgljy;
    @ViewInject(R.id.xgzjcjgjjy) private TextView xgzjcjgjjy;
    @ViewInject(R.id.zlfajy) private TextView zlfajy;
    @ViewInject(R.id.ysjyjjjtx) private TextView ysjyjjjtx;
    @ViewInject(R.id.ydjy) private TextView ydjy;
    @ViewInject(R.id.grxgjy) private TextView grxgjy;

    String id = "";
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(false).setTitle("个人方案详情").setTitleColor(getResources().getColor(R.color.blue));
        no_selution.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("map");
        lrsj.setText(serializableMap.getMap().get("updateTime").toString());
        lrr.setText(serializableMap.getMap().get("doctorName").toString());
        xygljy.setText(serializableMap.getMap().get("bloodPressureManagementAdvice").toString());
        xtgljy.setText(serializableMap.getMap().get("bloodSugarManagementAdvice").toString());
        xgzjcjgjjy.setText(serializableMap.getMap().get("resultsAndSuggestions").toString());
        zlfajy.setText(serializableMap.getMap().get("medicalAdvice").toString());
        ysjyjjjtx.setText(serializableMap.getMap().get("dietaryAdviceToRemindAndTaboos").toString());
        ydjy.setText(serializableMap.getMap().get("exerciseAdvice").toString());
        grxgjy.setText(serializableMap.getMap().get("personalHabitsSuggest").toString());

    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
}
