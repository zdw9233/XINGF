package com.uyi.app.ui.health;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UYIApplication;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.common.ViewPagerImagebiaoActivity;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.fragment.AllReportFragment;
import com.uyi.app.ui.health.fragment.BloodFatFragment;
import com.uyi.app.ui.health.fragment.BloodPressureFragment;
import com.uyi.app.ui.health.fragment.BloodSugarFragment;
import com.uyi.app.ui.health.fragment.ElectrocarDiogramFragment;
import com.uyi.app.ui.health.fragment.InspectionReportFragment;
import com.uyi.app.ui.health.fragment.OtherItemsFragment;
import com.uyi.app.ui.health.fragment.OxygenFragment;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 健康数据库
 *
 * @author user
 */
@ContentView(R.layout.fragment_health_database)
public class HealthDatabaseActivity extends BaseFragmentActivity {
    private FragmentManager fm;// fragment管理器
    @ViewInject(R.id.health_manager_tubiao)
    private ImageView health_manager_tubiao;
    @ViewInject(R.id.lay_6_7_gone)
    private LinearLayout lay_6_7_gone;
    @ViewInject(R.id.chose_lay1)
    private LinearLayout chose_lay1;
    @ViewInject(R.id.chose_lay2)
    private LinearLayout chose_lay2;
    @ViewInject(R.id.chose_lay3)
    private LinearLayout chose_lay3;
    @ViewInject(R.id.chose_lay4)
    private LinearLayout chose_lay4;
    @ViewInject(R.id.chose_lay5)
    private LinearLayout chose_lay5;
    @ViewInject(R.id.lay)
    private LinearLayout lay;
    @ViewInject(R.id.lay_chose_time)
    private LinearLayout lay_chose_time;
    @ViewInject(R.id.picture)
    private LinearLayout picture;
    private List<Fragment> fragments;
    private Fragment currentFragment;
    @ViewInject(R.id.health_database_starttime)
    private TextView health_database_starttime;
    @ViewInject(R.id.health_database_endtime)
    private TextView health_database_endtime;
    @ViewInject(R.id.chose_time_1)
    private TextView chose_time_1;
    @ViewInject(R.id.chose_time_2)
    private TextView chose_time_2;
    @ViewInject(R.id.chose_time_3)
    private TextView chose_time_3;
    @ViewInject(R.id.chose_time_4)
    private TextView chose_time_4;
    @ViewInject(R.id.radio1)
    private RadioButton radio1;
    @ViewInject(R.id.radio2)
    private RadioButton radio2;
    @ViewInject(R.id.radio5)
    private RadioButton radio5;
    @ViewInject(R.id.radio6)
    private RadioButton radio6;
    @ViewInject(R.id.radio7)
    private RadioButton radio7;
    @ViewInject(R.id.radio8)
    private RadioButton radio8;
    @ViewInject(R.id.radio9)
    private RadioButton radio9;
    @ViewInject(R.id.radio10)
    private RadioButton radio10;
    @ViewInject(R.id.radio11)
    private RadioButton radio11;
    @ViewInject(R.id.radio12)
    private RadioButton radio12;
    @ViewInject(R.id.radio13)
    private RadioButton radio13;
    @ViewInject(R.id.radio15)
    private RadioButton radio15;
    @ViewInject(R.id.arrow)
    private TextView arrow;
    @ViewInject(R.id.arrow_image)
    private ImageView arrow_image;
    @ViewInject(R.id.new_health_database_layout_start)
    private LinearLayout new_health_database_layout_start;
    int clicknum = 0;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    Calendar cal = Calendar.getInstance();
    int arrow_num = 1;
    int image_num;
    int time_num = 4;
    Bitmap bitmap = null;
    /**
     * 选则时间
     */
    private int selectedDate;
    public static String startDate;
    public static String endDate;
    private View currentView;
    AllReportFragment allReportFragment = new AllReportFragment();
    BloodPressureFragment bloodPressureFragment = new BloodPressureFragment();
    BloodSugarFragment bloodSugarFragment = new BloodSugarFragment();
    BloodFatFragment bloodFatFragment = new BloodFatFragment();
    OxygenFragment oxygenFragment = new OxygenFragment();
    OtherItemsFragment otherItemsFragment = new OtherItemsFragment();
    ElectrocarDiogramFragment electrocarDiogramFragment = new ElectrocarDiogramFragment();
    InspectionReportFragment inspectionReportFragment = new InspectionReportFragment();

    @OnClick({
            R.id.xy,       //
            R.id.xt,       //
            R.id.xz,    //
            R.id.xyang,       //
            R.id.qtxm,       //
            R.id.xdt,    //
            R.id.jcbg         //
    })
    public void onItemsClick(View view) {
        chose_lay1.setVisibility(View.GONE);
        chose_lay2.setVisibility(View.GONE);
        chose_lay3.setVisibility(View.GONE);
        chose_lay4.setVisibility(View.GONE);
        chose_lay5.setVisibility(View.GONE);
        if (currentView != null) {
            currentView.setBackgroundResource(R.drawable.sel_white_to_press);
            ((CheckedTextView) ((LinearLayout) currentView).getChildAt(0)).setChecked(false);
            ((CheckedTextView) ((LinearLayout) currentView).getChildAt(1)).setChecked(false);
        }
        view.setBackgroundResource(R.color.blue);
        ((CheckedTextView) ((LinearLayout) view).getChildAt(0)).setChecked(true);
        ((CheckedTextView) ((LinearLayout) view).getChildAt(1)).setChecked(true);
        switch (view.getId()) {
            case R.id.xy:
                chose_lay1.setVisibility(View.VISIBLE);
                clicknum = 1;
                lay_6_7_gone.setVisibility(View.VISIBLE);
                choseradioClick(findViewById(R.id.radio1));
                radio1.setChecked(true);
//                changeFragment(1);
                replaceView(1);
                break;  //
            case R.id.xt:
                chose_lay2.setVisibility(View.VISIBLE);
                clicknum = 2;
                lay_6_7_gone.setVisibility(View.VISIBLE);
                choseradioClick(findViewById(R.id.radio5));
                radio5.setChecked(true);
//                changeFragment(2);
                replaceView(2);
                break;  //
            case R.id.xz:
                chose_lay3.setVisibility(View.VISIBLE);
                clicknum = 3;
                lay_6_7_gone.setVisibility(View.VISIBLE);
                choseradioClick(findViewById(R.id.radio8));
                radio8.setChecked(true);
//                changeFragment(3);
                replaceView(3);
                break;  //
            case R.id.xyang:
                chose_lay4.setVisibility(View.VISIBLE);
                clicknum = 4;
                lay_6_7_gone.setVisibility(View.VISIBLE);
                choseradioClick(findViewById(R.id.radio15));
                radio15.setChecked(true);
//                changeFragment(4);
                replaceView(4);
                break;  //
            case R.id.qtxm:
                chose_lay5.setVisibility(View.VISIBLE);
                clicknum = 5;
                lay_6_7_gone.setVisibility(View.VISIBLE);
                choseradioClick(findViewById(R.id.radio12));
                radio12.setChecked(true);
//                changeFragment(5);
                replaceView(5);
                break;  //
            case R.id.xdt:
                clicknum = 6;
                lay_6_7_gone.setVisibility(View.GONE);
//                changeFragment(6);
                replaceView(6);
                break;  //
            case R.id.jcbg:
                clicknum = 7;
                lay_6_7_gone.setVisibility(View.GONE);
//                changeFragment(7);
                replaceView(7);
                break;  //
        }
        currentView = view;
    }

    @OnClick({
            R.id.chose_time_1,       //
            R.id.chose_time_2,       //
            R.id.chose_time_3,    //
            R.id.chose_time_4      //
    })
    private void choseTimeClick(View view) {
        chose_time_1.setBackgroundResource(R.color.my_service_bg);
        chose_time_2.setBackgroundResource(R.color.my_service_bg);
        chose_time_3.setBackgroundResource(R.color.my_service_bg);
        chose_time_4.setBackgroundResource(R.color.my_service_bg);
        chose_time_1.setTextColor(getResources().getColor(R.color.tet_type_6));
        chose_time_2.setTextColor(getResources().getColor(R.color.tet_type_6));
        chose_time_3.setTextColor(getResources().getColor(R.color.tet_type_6));
        chose_time_4.setTextColor(getResources().getColor(R.color.tet_type_6));
        switch (view.getId()) {
            case R.id.chose_time_1:


                chose_time_1.setTextColor(getResources().getColor(R.color.white));
                chose_time_1.setBackgroundResource(R.color.blue);
                lay_chose_time.setVisibility(View.VISIBLE);
                if (time_num != 1) {
                    time_num = 1;
                    health_database_endtime.setText("");
                    health_database_starttime.setText("");

                }
                break;  //
            case R.id.chose_time_2:

                chose_time_2.setTextColor(getResources().getColor(R.color.white));
                chose_time_2.setBackgroundResource(R.color.blue);
                lay_chose_time.setVisibility(View.GONE);
                if (time_num != 2) {
                    time_num = 2;
                    startDate = getTime(-7);
                    endDate = getTime(0);
                    laodImage(image_num);
                    loadData();
                }


                break;  //
            case R.id.chose_time_3:
                chose_time_3.setTextColor(getResources().getColor(R.color.white));
                chose_time_3.setBackgroundResource(R.color.blue);
                lay_chose_time.setVisibility(View.GONE);
                if (time_num != 3) {
                    time_num = 3;
                    startDate = getTime(-14);
                    endDate = getTime(0);
                    laodImage(image_num);
                    loadData();
                }
                break;  //
            case R.id.chose_time_4:
                chose_time_4.setTextColor(getResources().getColor(R.color.white));
                chose_time_4.setBackgroundResource(R.color.blue);
                lay_chose_time.setVisibility(View.GONE);
                if (time_num != 4) {
                    time_num = 4;
                    startDate = getTime(-30);
                    endDate = getTime(0);
                    laodImage(image_num);
                    loadData();
                }
                break;  //

        }
    }

    private void loadData() {
        switch (clicknum) {
            case 1:
                bloodPressureFragment.onRefresh();
                break;
            case 2:
                bloodSugarFragment.onRefresh();
                break;
            case 3:
                bloodFatFragment.onRefresh();
                break;
            case 4:
                otherItemsFragment.onRefresh();
                break;
            case 5:
                otherItemsFragment.onRefresh();
                break;
            case 6:
                electrocarDiogramFragment.onRefresh();
                break;
            case 7:
                inspectionReportFragment.onRefresh();
                break;
        }
    }

    //    private void getNewData(){
//   bloodPressureFragment.onRefresh();
//    bloodSugarFragment.onRefresh();
//        bloodFatFragment.onRefresh();
//        oxygenFragment.onRefresh();
//         otherItemsFragment.onRefresh();
//       electrocarDiogramFragment.onRefresh();
//       inspectionReportFragment.onRefresh();
//    }
    @OnClick({
            R.id.radio1,       //
            R.id.radio2,       //
            R.id.radio5,       //
            R.id.radio6,       //
            R.id.radio7,       //
            R.id.radio8,       //
            R.id.radio9,       //
            R.id.radio10,       //
            R.id.radio11,       //
            R.id.radio12,       //
            R.id.radio13,       //
            R.id.radio15,       //
            R.id.arrow     //
    })
    private void choseradioClick(View view) {
        switch (view.getId()) {
            case R.id.radio1:
                laodImage(1);
                break;  //
            case R.id.radio2:
                laodImage(2);
                break;  //
            case R.id.radio5:
                laodImage(5);
                break;  //
            case R.id.radio6:
                laodImage(6);
                break;  //
            case R.id.radio7:
                laodImage(7);
                break;  //
            case R.id.radio8:
                laodImage(8);
                break;  //
            case R.id.radio9:
                laodImage(9);
                break;  //
            case R.id.radio10:
                laodImage(10);
                break;  //
            case R.id.radio11:
                laodImage(11);
                break;  //
            case R.id.radio12:
                laodImage(12);
                break;  //
            case R.id.radio13:
                laodImage(13);
                break;  //
            case R.id.radio15:
                laodImage(15);
                break;  //
            case R.id.arrow:
                if (arrow_num == 0) {
                    arrow.setText("收起");
                    arrow_image.setBackgroundResource(R.drawable.arrow_top2x);
                    picture.setVisibility(View.VISIBLE);
                    arrow_num = 1;
                } else {
                    arrow.setText("展开");
                    arrow_image.setBackgroundResource(R.drawable.arrow_down2x);
                    picture.setVisibility(View.GONE);
                    arrow_num = 0;
                }

                break;  //
        }
    }

    private String getTime(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, i);//往上推一天  30推三十天  365推一年
        return sdf.format(calendar.getTime());
    }

    @Override
    protected void onInitLayoutAfter() {

        fm = getSupportFragmentManager();
        startDate = getTime(-30);
        endDate = getTime(0);
        String[] str = getResources().getStringArray(R.array.health_manager);
        fragments = new ArrayList<>();
        fragments.add(allReportFragment);
        fragments.add(bloodPressureFragment);
        fragments.add(bloodSugarFragment);
        fragments.add(bloodFatFragment);
        fragments.add(oxygenFragment);
        fragments.add(otherItemsFragment);
        fragments.add(electrocarDiogramFragment);
        fragments.add(inspectionReportFragment);
//        fragments.add(new AllReportFragment());
//        fragments.add(new BloodPressureFragment());
//        fragments.add(new BloodSugarFragment());
//        fragments.add(new BloodFatFragment());
//        fragments.add(new OxygenFragment());
//        fragments.add(new OtherItemsFragment());
//        fragments.add(new ElectrocarDiogramFragment());
//        fragments.add(new InspectionReportFragment());
        onItemsClick(findViewById(R.id.xy));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
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

    @OnClick({R.id.health_database_starttime, R.id.health_database_endtime, R.id.title_add, R.id.back_to_tab_1, R.id.health_manager_tubiao})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.health_database_starttime:
                selectedDate = 1;
                selectedDate();
                break;
            case R.id.health_database_endtime:
                selectedDate = 2;
                selectedDate();
                break;
//            case R.id.health_database_submit://查询
//                startDate =health_database_starttime.getText().toString();
//                endDate = health_database_endtime.getText().toString();
//                if (!ValidationUtils.isNull(startDate) && !ValidationUtils.isNull(endDate)) {
//                    try {
//                        long start = DateUtils.toDateByString(startDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
//                        long end = DateUtils.toDateByString(endDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
//                        if (start > end) {
//                            T.showLong(this, "开始时间不能大于结束时间");
//                            return;
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                //查询
//                switch (clicknum){
//                    case 0:
//                        allReportFragment.onRefresh();
//                        break;
//                    case 1:
//                        bloodPressureFragment.onRefresh();
//                        break;
//                    case 2:
//                        bloodSugarFragment.onRefresh();
//                        break;
//                    case 3:
//                        bloodSugarFragment.onRefresh();
//                        break;
//                    case 4:
//                        oxygenFragment.onRefresh();
//                        break;
//                    case 5:
//                        otherItemsFragment.onRefresh();
//                        break;
//                    case 6:
//                        electrocarDiogramFragment.onRefresh();
//                        break;
//                    case 7:
//                        inspectionReportFragment.onRefresh();
//                        break;
//                }
//                break;
            case R.id.title_add:
                Intent intent = new Intent(this, AddHealthDatabase.class);
                startActivity(intent);
                break;
            case R.id.back_to_tab_1:
                finish();
                break;
            case R.id.health_manager_tubiao:
                if (bitmap != null) {
                    byte buf[] = new byte[1024 * 1024];
                    buf = Bitmap2Bytes(bitmap);
                    Intent intentimage = new Intent(this, ViewPagerImagebiaoActivity.class);
                    intentimage.putExtra("photo_bmp", buf);

                    startActivity(intentimage);
                }

        }
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 选择时间
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
                        if (stime > System.currentTimeMillis()) {
                            T.showShort(this, "起始时间不能大于当前日期");
                            return;
                        }
//                        String enDate = health_database_endtime.getText().toString();
//                        if(enDate != null&&!enDate.equals("")){
//                            long etime = DateUtils.toDateByString(endDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
//                            if (stime >etime) {
//                                T.showShort(this, "起始时间不能大于结束时间");
//                                return;
//                            }
//                        }
                        health_database_starttime.setText(data.getStringExtra("date"));
                        startDate = health_database_starttime.getText().toString();
                        endDate = health_database_endtime.getText().toString();
                        myTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (selectedDate == 2) {
                    String sDate = health_database_starttime.getText().toString();
                    if (sDate != null && !sDate.equals("")) {
                        try {
                            long stime = DateUtils.toDateByString(sDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
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
                    startDate = health_database_starttime.getText().toString();
                    endDate = health_database_endtime.getText().toString();
                    myTime();
                }
            }
        }
    }

    private void myTime() {
        if (!ValidationUtils.isNull(startDate) && !ValidationUtils.isNull(endDate)) {
            try {
                long start = DateUtils.toDateByString(startDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                long end = DateUtils.toDateByString(endDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                if (start > end) {
                    T.showLong(this, "开始时间不能大于结束时间");
                } else {
                    laodImage(image_num);
                    loadData();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceView(int postion) {
        fm.beginTransaction().replace(R.id.content, fragments.get(postion)).commit();
        //displayCount();
    }

    private void laodImage(int i) {
        image_num = i;
        Loading.bulid(this, "努力加载中...").show();
        String url = String.format(Constens.HEALTH_REPORT, i, startDate, endDate);
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            public void onResponse(Bitmap bm) {
                Loading.bulid(HealthDatabaseActivity.this, "努力加载中...").dismiss();
                if (bm != null) {
                    if (health_manager_tubiao != null) {
                        health_manager_tubiao.setImageBitmap(bm);
                        bitmap = bm;
                    }
                }
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                if (UserInfoManager.getLoginUserInfo(UYIApplication.getContext()) != null) {
                    headers.put("authToken", UserInfoManager.getLoginUserInfo(UYIApplication.getContext()).authToken);
                }
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("User-Agent", "Android");
                return headers;
            }
        };
        executeRequest(imageRequest);
    }


}
