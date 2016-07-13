package com.uyi.app.ui.health;

import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;

/**
 * Created by ThinkPad on 2016/6/29.
 */
@ContentView(R.layout.diet_plan)
public class DietPlanActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.diet_plan_details) private TextView diet_plan_details;
    private UserInfo userInfo;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("饮食计划").setTitleColor(getResources().getColor(R.color.blue));
        userInfo = UserInfoManager.getLoginUserInfo(activity);
        if(userInfo.eatinghabiit != null){
            diet_plan_details.setText(userInfo.eatinghabiit);
        }else{
            diet_plan_details.setText("你没有任何资料！");
        }
        
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
}
