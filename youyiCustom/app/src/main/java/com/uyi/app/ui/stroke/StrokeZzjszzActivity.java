package com.uyi.app.ui.stroke;

import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.stroke.model.StrokeFollowUpRecordJson;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2017/1/12.
 * 卒中警示症状
 */
@ContentView(R.layout.activity_stroke_zzjszz)
public class StrokeZzjszzActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.zzjszz_1) private TextView zzjszz_1;
    @ViewInject(R.id.zzjszz_2) private TextView zzjszz_2;
    @ViewInject(R.id.zzjszz_3) private TextView zzjszz_3;
    @ViewInject(R.id.zzjszz_4) private TextView zzjszz_4;
    @ViewInject(R.id.zzjszz_5) private TextView zzjszz_5;
    @ViewInject(R.id.zzjszz_6) private TextView zzjszz_6;
    List<TextView> textViews = new ArrayList<>();
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("卒中警示症状").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(getIntent().getStringExtra("data"), StrokeFollowUpRecordJson.class);
        textViews.add(zzjszz_1);   textViews.add(zzjszz_2);   textViews.add(zzjszz_3);   textViews.add(zzjszz_4);   textViews.add(zzjszz_5);   textViews.add(zzjszz_6);
        for (int i = 0; i <6 ; i++) {
            L.e("DOCTOR_HEALTH_STROKE_DETAILS== " + reportItems.get(i).getStrokeWarningSymptoms());
            if(reportItems.get(i).getStrokeWarningSymptoms()==null){
                textViews.get(i).setVisibility(View.GONE);
                textViews.get(i).setText("");

            }else{
                textViews.get(i).setText( reportItems.get(i).getStrokeWarningSymptoms().replace("|",";"+"\n"));
            }
        }

    }
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
