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
@ContentView(R.layout.life_style)
public class LifeStyleActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.life_style_details)
    private TextView life_style_details;
    private UserInfo userInfo;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("生活方式").setTitleColor(getResources().getColor(R.color.blue));
        userInfo = UserInfoManager.getLoginUserInfo(activity);
        if(userInfo.liefstyle != null){
            life_style_details.setText(userInfo.liefstyle);
        }else{
            life_style_details.setText("你没有任何资料！");
        }

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
}
