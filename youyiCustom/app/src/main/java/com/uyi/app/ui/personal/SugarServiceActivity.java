package com.uyi.app.ui.personal;

import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;

/**
 * Created by ThinkPad on 2016/7/2.
 */
@ContentView(R.layout.sugar_heart_service)
public class SugarServiceActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.tanxin_one) private RelativeLayout tanxin_one;
    @ViewInject(R.id.tanxin_two) private RelativeLayout tanxin_two;
    @ViewInject(R.id.tanxin_three) private RelativeLayout tanxin_three;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("糖心服务").setTitleColor(getResources().getColor(R.color.blue));
    }
@OnClick({R.id.tanxin_one,R.id.tanxin_two,R.id.tanxin_three})
public void onClick(View v){
switch (v.getId()){
    case R.id.tanxin_one:
            
        break;
    case R.id.tanxin_two:

        break;
    case R.id.tanxin_three:

        break;

}
}
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
