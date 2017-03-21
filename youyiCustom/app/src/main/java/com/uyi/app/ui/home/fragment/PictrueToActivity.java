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
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的报告（新） Created by zdw on 2016/6/19.
 */
@ContentView(R.layout.activity_picture_to_details_2_1)
public class PictrueToActivity extends BaseFragmentActivity {

    @ViewInject(R.id.lay)
    private LinearLayout lay;
    private View currentView;
    private List<Fragment> fragments;
    AnxinjichuFregment anxinjichuFregment  = new AnxinjichuFregment();
    AnxinjinxiFregment anxinjinxiFregment  = new AnxinjinxiFregment();
    TanxinjichuFregment tanxinjichuFregment  = new TanxinjichuFregment();
    TanxinjinxiFregment tanxinjinxiFregment  = new TanxinjinxiFregment();
    private FragmentManager fm;// fragment管理器
    public void replaceView(int postion) {
        fm.beginTransaction().replace(R.id.content, fragments.get(postion)).commit();
    }
    @OnClick({
            R.id.fxpg,       //
            R.id.grfa,       //
            R.id.ybg,    //
            R.id.jbg,       //
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
            case R.id.fxpg:
                replaceView(0);
                break;  //
            case R.id.grfa:
                replaceView(1);
                break;  //
            case R.id.ybg:
                replaceView(2);
                break;  //
            case R.id.jbg:
                replaceView(3);
                break;  //
        }
        currentView = view;
    }

    @Override
    protected void onInitLayoutAfter() {
        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        fragments.add(anxinjichuFregment);
        fragments.add(anxinjinxiFregment);
        fragments.add(tanxinjichuFregment);
        fragments.add(tanxinjinxiFregment);
        onItemsClick(findViewById(R.id.fxpg));
    }



    @OnClick({
            R.id.back_to_tab_1,//返回
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
        if (requestCode == Constens.START_ACTIVITY_FOR_RESULT) {
            if (resultCode == RESULT_OK) {
            }
        }


    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }
}
