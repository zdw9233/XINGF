package com.uyi.xinf.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.doctor.xinf.R;
import com.uyi.xinf.ui.custom.BaseActivity;
import com.uyi.xinf.ui.custom.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ThinkPad on 2017/6/15.
 */
@ContentView(R.layout.main)
public class Main extends BaseActivity {
    private FragmentManager fm;// fragment管理器
    private List<Fragment> fragments = new ArrayList<Fragment>();
    public int currentPage = 1;
    @ViewInject(R.id.main_tab_1)
    public LinearLayout main_tab_1;
    @ViewInject(  R.id.main_tab_2)
    public LinearLayout main_tab_2;
    @ViewInject(  R.id.main_tab_3)
    public LinearLayout main_tab_3;
    @ViewInject(  R.id.main_tab_4)
    public LinearLayout main_tab_4;

    @ViewInject(  R.id.main_tab_1_icon)
    private ImageView main_tab_1_icon;
    @ViewInject(  R.id.main_tab_2_icon)
    private ImageView main_tab_2_icon;
    @ViewInject(  R.id.main_tab_3_icon)
    private ImageView main_tab_3_icon;
    @ViewInject(  R.id.main_tab_4_icon)
    private ImageView main_tab_4_icon;

    @ViewInject(  R.id.main_tab_1_text)
    private TextView main_tab_1_text;
    @ViewInject(  R.id.main_tab_2_text)
    private TextView main_tab_2_text;
    @ViewInject(  R.id.main_tab_3_text)
    private TextView main_tab_3_text;
    @ViewInject(  R.id.main_tab_4_text)
    private TextView main_tab_4_text;

    @ViewInject(  R.id.main_tab_1_count)
    private TextView main_tab_1_count;
    @ViewInject(R.id.main_tab_2_count)
    private TextView main_tab_2_count;



    @OnClick({R.id.main_tab_1,R.id.main_tab_2,R.id.main_tab_3,R.id.main_tab_4})
    public void widgetClick(View v) {
        main_tab_1_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_2_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_3_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_4_text.setTextColor(getResources().getColor(R.color.footer_somber));
        switch (v.getId()) {
            case R.id.main_tab_1:
                currentPage = 1;
                main_tab_1_text.setTextColor(getResources().getColor(R.color.red));
                replaceView(0);
                break;
            case R.id.main_tab_2:
                currentPage = 2;
                main_tab_2_text.setTextColor(getResources().getColor(R.color.red));
                replaceView(1);
                break;
            case R.id.main_tab_3:
                currentPage = 3;
                main_tab_3_text.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.main_tab_4:
                currentPage = 4;
                main_tab_4_text.setTextColor(getResources().getColor(R.color.red));
                replaceView(3);
                break;
        }
    }
    public void replaceView(int postion) {
        fm.beginTransaction().replace(R.id.main_contalner, fragments.get(postion)).commit();
    }

    @Override
    protected void onInitLayoutAfter() {

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
