package com.uyi.app.ui.health;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.doctor.app.R;

/**
 * Created by ThinkPad on 2016/6/29.
 */
@ContentView(R.layout.life_style)
public class LifeStyleActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.life_style_details) private EditText life_style_details;
    @ViewInject(R.id.life_style_submit) private Button life_style_submit;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("生活方式").setTitleColor(getResources().getColor(R.color.blue));


    }
    @OnClick(R.id.life_style_submit)//更新
    public void onclick(View v){
        if(v.getId() == R.id.life_style_submit){

        }
    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
