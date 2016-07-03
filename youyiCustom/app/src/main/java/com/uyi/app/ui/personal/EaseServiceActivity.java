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
@ContentView(R.layout.ease_of_service)
public class EaseServiceActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.anxin_one) private RelativeLayout anxin_one;
    @ViewInject(R.id.anxin_two) private RelativeLayout anxin_two;
    @ViewInject(R.id.anxin_three) private RelativeLayout anxin_three;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("安心服务").setTitleColor(getResources().getColor(R.color.blue));
    }
@OnClick({R.id.anxin_one,R.id.anxin_two,R.id.anxin_three})
public void onClick(View v){
switch (v.getId()){
    case R.id.anxin_one:

        break;
    case R.id.anxin_two:

        break;
    case R.id.anxin_three:

        break;

}
}
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
