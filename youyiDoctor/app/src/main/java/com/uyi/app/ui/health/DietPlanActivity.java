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
@ContentView(R.layout.diet_plan)
public class DietPlanActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.diet_plan_details) private EditText diet_plan_details;
    @ViewInject(R.id.diet_plan_submit) private Button diet_plan_submit;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("饮食计划").setTitleColor(getResources().getColor(R.color.blue));

        
    }
@OnClick(R.id.diet_plan_submit)//更新
public void onclick(View v){
if(v.getId() == R.id.diet_plan_submit){

}
}
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
