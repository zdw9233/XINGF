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
 * 血管超声检查
 */
@ContentView(R.layout.activity_stroke_xgcsjc)
public class StrokeXgcsjcActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.xgcsjc_1) private TextView xgcsjc_1;
    @ViewInject(R.id.xgcsjc_2) private TextView xgcsjc_2;
    @ViewInject(R.id.xgcsjc_3) private TextView xgcsjc_3;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("血管超声检查").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(getIntent().getStringExtra("data"), StrokeFollowUpRecordJson.class);
        if(reportItems.get(5).getVascularUltrasounds()!= null){
                if(reportItems.get(5).getVascularUltrasounds().size()>0){
                    xgcsjc_1.setText(reportItems.get(5).getVascularUltrasounds().get(0).getResult() == null?"":reportItems.get(5).getVascularUltrasounds().get(0).getResult());
                    xgcsjc_2.setText(reportItems.get(5).getVascularUltrasounds().get(1).getResult() == null?"":reportItems.get(5).getVascularUltrasounds().get(1).getResult());
                    xgcsjc_3.setText(reportItems.get(5).getVascularUltrasounds().get(2).getResult() == null?"":reportItems.get(5).getVascularUltrasounds().get(2).getResult());
                }
        }

    }
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
