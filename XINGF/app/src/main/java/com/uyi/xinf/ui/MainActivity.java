package com.uyi.xinf.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.doctor.xinf.R;
import com.uyi.xinf.ui.bindsekit.BindSekitActivity;
import com.uyi.xinf.ui.custom.BaseActivity;
import com.uyi.xinf.ui.custom.SystemBarTintManager;
import com.uyi.xinf.ui.healthmanager.HealthManagerActivity;
import com.uyi.xinf.ui.healthquestion.HealthQuestionActivity;
import com.uyi.xinf.ui.tongjidata.TongjiDataActivity;


/**
 * Created by ThinkPad on 2017/6/15.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject( R.id.lay)
    public LinearLayout lay;
    @ViewInject( R.id.main_tab1)
    public LinearLayout main_tab1;
    @ViewInject( R.id.main_tab2)
    public LinearLayout main_tab2;
    @ViewInject( R.id.main_tab3)
    public LinearLayout main_tab3;
    @ViewInject( R.id.main_tab4)
    public LinearLayout main_tab4;


    @OnClick({R.id.main_tab1,R.id.main_tab2,R.id.main_tab3,R.id.main_tab4})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab1:
startActivity(new Intent(this, HealthManagerActivity.class));
                break;
            case R.id.main_tab2:
                startActivity(new Intent(this, HealthQuestionActivity.class));
                break;
            case R.id.main_tab3:
                startActivity(new Intent(this, BindSekitActivity.class));
                break;
            case R.id.main_tab4:
                startActivity(new Intent(this, TongjiDataActivity.class));
                break;
        }
    }

    @Override
    protected void onInitLayoutAfter() {

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }
}
