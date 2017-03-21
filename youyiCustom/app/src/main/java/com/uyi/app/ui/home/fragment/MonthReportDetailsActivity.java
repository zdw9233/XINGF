package com.uyi.app.ui.home.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的报告（新） Created by zdw on 2016/6/19.
 */
@ContentView(R.layout.activity_month_report_2_1)
public class MonthReportDetailsActivity extends BaseFragmentActivity {
    @ViewInject(R.id.lay)
    private LinearLayout lay;
    private View currentView;
    private List<Fragment> fragments;
    private FragmentManager fm;// fragment管理器
    public void replaceView(int postion) {
        fm.beginTransaction().replace(R.id.content, fragments.get(postion)).commit();
    }
    @OnClick({
            R.id.yhjbxx, //
            R.id.qmtzjc,       //
            R.id.xyqst,    //
            R.id.xtqst,       //
            R.id.nsxzsxjc         //
    })
    public void onItemsClick(View view) {
        if (currentView != null) {
            currentView.setBackgroundResource(R.drawable.sel_white_to_press);
            ((CheckedTextView) ((LinearLayout) currentView).getChildAt(0)).setChecked(false);
            ((CheckedTextView) ((LinearLayout) currentView).getChildAt(1)).setChecked(false);
        }
        view.setBackgroundResource(R.color.blue);
        ((CheckedTextView) ((LinearLayout) view).getChildAt(0)).setChecked(true);
        ((CheckedTextView) ((LinearLayout) view).getChildAt(1)).setChecked(true);
        switch (view.getId()) {
            case R.id.yhjbxx:
                break;  //
            case R.id.qmtzjc:
                break;  //
            case R.id.xyqst:
                break;  //
            case R.id.xtqst:
                break;  //
            case R.id.nsxzsxjc:
                break;  //
        }
        currentView = view;
    }


    @Override
    protected void onInitLayoutAfter() {

        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        onItemsClick(findViewById(R.id.yhjbxx));
    }



    @OnClick({
            R.id.back_to_tab_1//返回
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.back_to_tab_1:
                finish();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }
}
