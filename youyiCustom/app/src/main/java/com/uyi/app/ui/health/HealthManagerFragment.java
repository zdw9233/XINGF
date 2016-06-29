package com.uyi.app.ui.health;

import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;

/**
 * 健康管理 Created by ThinkPad on 2016/6/13.
 */
public class HealthManagerFragment extends BaseFragment implements HeaderView.OnTabChanage {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;

    @OnClick({
            R.id.diagnosis, //主诊报告
            R.id.report,       //健康报告
            R.id.database,      //健康数据库
            R.id.assessment,    //风险评估
            R.id.life,          //生活方式
            R.id.diet,          //饮食计划
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diagnosis:
                break; //主诊报告
            case R.id.report:     //健康报告
                break;
            case R.id.database:    //健康数据库
                break;
            case R.id.assessment: //风险评估
                break;
            case R.id.life:      //生活方式
                break;
            case R.id.diet:     //饮食计划
                break;
        }
    }

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_health_manager2_1;
    }

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon)
                .showTab(false).showRight(true).showTitle(true)
                .setTitle("健康管理").setTitleColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onChanage(int postion) {
    }
}
