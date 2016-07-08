package com.uyi.app.ui.personal;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.custom.app.R;

/**
 * Created by ThinkPad on 2016/7/2.
 */
@ContentView(R.layout.sugar_one_year_service)
public class SugarOneYearActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.anxin_pay) private TextView anxin_pay;
    private MessageConform conform;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("糖心服务").setTitleColor(getResources().getColor(R.color.blue));

    }
@OnClick(R.id.anxin_pay)
public void onClick(View v){
    switch (v.getId()){
        case R.id.anxin_pay:


            break;
    }

}
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
