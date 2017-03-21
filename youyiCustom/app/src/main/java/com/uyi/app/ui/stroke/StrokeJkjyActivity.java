package com.uyi.app.ui.stroke;

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
 * 健康教育
 */
@ContentView(R.layout.activity_stroke_jkjy)
public class StrokeJkjyActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.jkjy_1) private TextView jkjy_1;
    @ViewInject(R.id.jkjy_2) private TextView jkjy_2;
    @ViewInject(R.id.jkjy_3) private TextView jkjy_3;
    @ViewInject(R.id.jkjy_4) private TextView jkjy_4;
    @ViewInject(R.id.jkjy_5) private TextView jkjy_5;
    @ViewInject(R.id.jkjy_6) private TextView jkjy_6;
    List<TextView> textViews = new ArrayList<>();
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("健康教育").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(getIntent().getStringExtra("data"), StrokeFollowUpRecordJson.class);
        textViews.add(jkjy_1);
        textViews.add(jkjy_2);
        textViews.add(jkjy_3);
        textViews.add(jkjy_4);
        textViews.add(jkjy_5);
        textViews.add(jkjy_6);
        for (int i = 0; i <6 ; i++) {
            L.e("DOCTOR_HEALTH_STROKE_DETAILS== " + reportItems.get(i).getHealthEducation());
            if(reportItems.get(i).getHealthEducation()==null){
                textViews.get(i).setText("");

            }else{
                textViews.get(i).setText( reportItems.get(i).getHealthEducation());
            }
        }
    }
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
