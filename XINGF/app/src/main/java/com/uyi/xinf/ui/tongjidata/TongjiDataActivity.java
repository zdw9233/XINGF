package com.uyi.xinf.ui.tongjidata;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.doctor.xinf.R;
import com.uyi.xinf.ui.custom.BaseActivity;
import com.uyi.xinf.ui.custom.SystemBarTintManager;


/**
 * Created by ThinkPad on 2017/6/16.
 */
@ContentView(R.layout.activity_tongji_data)
public class TongjiDataActivity extends BaseActivity {
    @ViewInject( R.id.lay)
    public LinearLayout lay;
    @ViewInject( R.id.back)
    private LinearLayout back;
    @ViewInject( R.id.title)
    private TextView title;
    @ViewInject( R.id.add)
    private TextView add;


    @OnClick({ R.id.back,R.id.add})
    public void widgetClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.back:
                finish();
                break;
            case R.id.add:
                break;
        }
    }


    @Override
    protected void onInitLayoutAfter() {
        title.setText("统计数据");
        add.setVisibility(View.GONE);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }
}
