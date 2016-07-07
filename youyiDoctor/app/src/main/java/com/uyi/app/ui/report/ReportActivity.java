package com.uyi.app.ui.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.doctor.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 主诊报告 Created by Leeii on 2016/6/22.
 */
@ContentView(R.layout.activity_report)
public class ReportActivity extends BaseFragmentActivity {
    private List<Fragment> fragments;

    private Fragment currentFragment;
    @ViewInject(R.id.header)
    private HeaderView headerView;

    @OnClick({
            R.id.cgjc,      //常规监测
            R.id.qst,       //趋势图
            R.id.xdt,       //心电图
            R.id.zytzjb,    //中医体质鉴别
            R.id.jkbg       //健康报告
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cgjc:
                changeFragment(0);
                break;  //常规监测
            case R.id.qst:
                changeFragment(1);
                break;  //趋势图
            case R.id.xdt:
                changeFragment(2);
                break;  //心电图
            case R.id.zytzjb:
                changeFragment(3);
                break;  //中医体质鉴别
            case R.id.jkbg:
                changeFragment(4);
                break;  //健康报告
        }
    }

    @Override
    protected void onInitLayoutAfter() {
//        setContentView(R.layout.activity_report);

        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(this).icon)
                .showTab(false).showRight(true).showTitle(true)
                .setTitle("健康管理").setTitleColor(getResources().getColor(R.color.blue));
        fragments = new ArrayList<>();
        fragments.add(new RoutineFragment());
        fragments.add(new TrendMapFragment());
        fragments.add(new ECGFragment());
        fragments.add(new PhysiqueFragment());
        fragments.add(new HealthReportFragment());
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    private void changeFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fragments.get(position);
        if (!f.isAdded()) {
            ft.add(R.id.content, f);
            if (currentFragment != null) {
                ft.hide(currentFragment);
            }
            currentFragment = f;
            ft.show(f);
        } else {
            if (f != currentFragment) {
                ft.hide(currentFragment);
                currentFragment = f;
                ft.show(f);
            }
        }
        ft.commit();
    }
}
