package com.uyi.app.ui.health;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.health.fragment.AllReportFragment;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * 健康数据库
 *
 * @author user
 */
@ContentView(R.layout.fragment_health_database)
public class HealthDatabaseActivity extends BaseFragmentActivity {
    private List<Fragment> fragments;
    private Fragment currentFragment;
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.health_database_starttime)
    private TextView health_database_starttime;
    @ViewInject(R.id.health_database_endtime)
    private TextView health_database_endtime;
    @ViewInject(R.id.health_database_submit)
    private Button health_database_submit;

    @ViewInject(R.id.new_health_database_layout_start)
    private LinearLayout new_health_database_layout_start;
    int clicknum = 0;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    Calendar cal = Calendar.getInstance();
    /**
     * 选则时间
     */
    private int selectedDate;
   public static String startDate;
    public static  String endDate;
    private View currentView;
    AllReportFragment allReportFragment = new AllReportFragment();
    @OnClick({
            R.id.qb,      //
            R.id.xy,       //
            R.id.xt,       //
            R.id.xz,    //
            R.id.xyang,       //
            R.id.qtxm,       //
            R.id.xdt,    //
            R.id.jcbg         //
    })
    public void onItemsClick(View view) {
        if (currentView != null) {
            currentView.setBackgroundColor(getResources().getColor(R.color.white));
            ((CheckedTextView) ((FrameLayout) currentView).getChildAt(0)).setChecked(false);
        }
        view.setBackgroundColor(getResources().getColor(R.color.blue));
        ((CheckedTextView) ((FrameLayout) view).getChildAt(0)).setChecked(true);
        switch (view.getId()) {
            case R.id.qb:
                clicknum = 0;
                changeFragment(0);
                break;  //
            case R.id.xy:
                clicknum = 1;
//                changeFragment(1);
                break;  //
            case R.id.xt:
                clicknum = 2;
//                changeFragment(2);
                break;  //
            case R.id.xz:
                clicknum = 3;
//                changeFragment(3);
                break;  //
            case R.id.xyang:
                clicknum = 4;
//                changeFragment(4);
                break;  //
            case R.id.qtxm:
                clicknum = 5;
//                changeFragment(5);
                break;  //
            case R.id.xdt:
                clicknum = 6;
//                changeFragment(6);
                break;  //
            case R.id.jcbg:
                clicknum = 7;
//                changeFragment(7);
                break;  //
        }
        currentView = view;
    }
    @Override
    protected void onInitLayoutAfter() {
        startDate=null;endDate = null;
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("健康数据库").setTitleColor(getResources().getColor(R.color.blue));
        String[] str = getResources().getStringArray(R.array.health_manager);
        headerView.setTitleTabs(str);
        headerView.selectTabItem(2);
        fragments = new ArrayList<>();
        fragments.add(allReportFragment);
//        fragments.add(new TrendMapFragment());
//        fragments.add(new ECGFragment());
//        fragments.add(new PhysiqueFragment());
//        fragments.add(new HealthReportFragment());
        onItemsClick(findViewById(R.id.qb));
    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
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
    @Override
    public void onResume() {
        super.onResume();
    }
    @OnClick({R.id.health_database_starttime, R.id.health_database_endtime, R.id.health_database_submit, R.id.new_health_database_layout_start})
    public void onClick(View v) {
        if (v.getId() == R.id.health_database_starttime) {
            selectedDate = 1;
            selectedDate();
        } else if (v.getId() == R.id.health_database_endtime) {
            selectedDate = 2;
            selectedDate();
        } else if (v.getId() == R.id.health_database_submit) {//查询
            startDate =health_database_starttime.getText().toString();
            endDate = health_database_endtime.getText().toString();
            if (!ValidationUtils.isNull(startDate) && !ValidationUtils.isNull(endDate)) {
                try {
                    long start = DateUtils.toDateByString(startDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                    long end = DateUtils.toDateByString(endDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                    if (start > end) {
                        T.showLong(this, "开始时间不能大于结束时间");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //查询
            switch (clicknum){
                case 0:
                    allReportFragment.onRefresh();
                    break;
            }

        } else if (v.getId() == R.id.new_health_database_layout_start) {
            Intent intent = new Intent(this, AddHealthDatabase.class);
            startActivity(intent);
        }
    }


    /**
     * 选择时间
     *
     */
    public void selectedDate() {

        if (selectedDate == 1) {
            Intent intent = new Intent(this, DatePickerActivity.class);
            intent.putExtra("currentDate", health_database_starttime.getText().toString().trim());
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        } else if (selectedDate == 2) {
            Intent intent = new Intent(this, DatePickerActivity.class);
            intent.putExtra("currentDate", health_database_endtime.getText().toString().trim());
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.START_ACTIVITY_FOR_RESULT && resultCode == -1) {
            if (data.hasExtra("date")) {
                if (selectedDate == 1) {
                    try {
                        long stime = DateUtils.toDateByString(data.getStringExtra("date"), Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                        if (stime >= System.currentTimeMillis()) {
                            T.showShort(this, "起始时间不能大于当前日期");
                            return;
                        }
                        health_database_starttime.setText(data.getStringExtra("date"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (selectedDate == 2) {
                    String startDate = health_database_starttime.getText().toString();
                    if (startDate != null) {
                        try {
                            long stime = DateUtils.toDateByString(startDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                            long etime = DateUtils.toDateByString(data.getStringExtra("date"), Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                            if (etime < stime) {
                                T.showShort(this, getString(R.string.e_date_message));
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    health_database_endtime.setText(data.getStringExtra("date"));
                }
            }
        }
    }



}
