package com.uyi.app.ui.health;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter.IOnItemSelectListener;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 健康管理
 *
 * @author user
 */
@ContentView(R.layout.fragment_health_manager)
public class HealthReportActivity extends BaseActivity implements IOnItemSelectListener {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.dianjifangda)
    private TextView dianjifangda;
    @ViewInject(R.id.health_manager_zb)
    private TextView health_manager_zb;
    @ViewInject(R.id.health_manager_starttime)
    private TextView health_manager_starttime;
    @ViewInject(R.id.health_manager_endtime)
    private TextView health_manager_endtime;
    @ViewInject(R.id.health_manager_submit)
    private Button health_manager_submit;
    @ViewInject(R.id.health_manager_tubiao)
    private ImageView health_manager_tubiao;

    Bitmap bitmap = null;
    /**
     * 选则时间
     */
    private int selectedDate;

    private int selectedType = 0;
    private SpinerPopWindow spinerPopWindow;

    @Override
    protected void onInitLayoutAfter() {
        if (UserInfoManager.getLoginUserInfo(this) == null) {
            return;
        }
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setItemListener(this);
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("健康报告").setTitleColor(getResources().getColor(R.color.blue));
        String[] str = getResources().getStringArray(R.array.health_manager);
        headerView.setTitleTabs(str);
        headerView.selectTabItem(1);
        bitmap = null;
        spinerPopWindow.refreshData(zbString, 1);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @OnClick({R.id.health_manager_zb, R.id.health_manager_starttime, R.id.health_manager_endtime, R.id.health_manager_submit, R.id.health_manager_tubiao})
    public void onClick(View v) {
        if (v.getId() == R.id.health_manager_zb) {
            spinerPopWindow.setWidth(health_manager_zb.getWidth());
            spinerPopWindow.refreshData(zbString, 0);
            spinerPopWindow.showAsDropDown(health_manager_zb);
        } else if (v.getId() == R.id.health_manager_starttime) {
            selectedDate = 1;
            selectedDate();
        } else if (v.getId() == R.id.health_manager_endtime) {
            selectedDate = 2;
            selectedDate();
        } else if (v.getId() == R.id.health_manager_submit) {
            String startDate = health_manager_starttime.getText().toString();
            String endDate = health_manager_endtime.getText().toString();
            if (selectedType == 0) {
                T.showLong(this, "请选择指标");
                return;
            }
            if (ValidationUtils.isNull(startDate)) {
                T.showLong(this, "请选择开始时间");
                return;
            }
            if (ValidationUtils.isNull(endDate)) {
                T.showLong(this, "请选择结束时间");
                return;
            }

            try {
                long start = DateUtils.toDateByString(startDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                long end = DateUtils.toDateByString(endDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
                if (start > end) {
                    T.showShort(this, getString(R.string.e_date_message));
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Loading.bulid(this, "努力加载中...").show();
            String url = String.format(Constens.HEALTH_REPORT, selectedType, startDate, endDate);
            ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                public void onResponse(Bitmap bm) {
                    Loading.bulid(HealthReportActivity.this, "努力加载中...").dismiss();
                    if (bm != null) {
                        L.d(TAG, "bmnotnull");
                        if (health_manager_tubiao != null) {
                            L.d(TAG, "health_manager_tubiaonotnull");
                            health_manager_tubiao.setImageBitmap(bm);
                            dianjifangda.setVisibility(View.VISIBLE);
                            bitmap = bm;
                        }
                    }
                }
            }, 0, 0, Config.ARGB_8888, null) {
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
        } else if (v.getId() == R.id.health_manager_tubiao) {
            if (bitmap != null) {
                byte buf[] = new byte[1024 * 1024];
                buf = Bitmap2Bytes(bitmap);
                Intent intent = new Intent(this, ViewPagerImagebiaoActivity.class);
                intent.putExtra("photo_bmp", buf);

                startActivity(intent);
            }


        }
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public void selectedDate() {
       if (selectedDate == 1) {
            Intent intent = new Intent(this, DatePickerActivity.class);
            intent.putExtra("currentDate", health_manager_starttime.getText().toString().trim());
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        } else if (selectedDate == 2) {
            Intent intent = new Intent(this, DatePickerActivity.class);
            intent.putExtra("currentDate", health_manager_endtime.getText().toString().trim());
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.START_ACTIVITY_FOR_RESULT && resultCode == -1) {
            if (data.hasExtra("date")) {
                if (selectedDate == 1) {
                    health_manager_starttime.setText(data.getStringExtra("date"));
                } else if (selectedDate == 2) {
                    String startDate = health_manager_starttime.getText().toString();
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
                    health_manager_endtime.setText(data.getStringExtra("date"));
                }
            }
        }
    }

    @Override
    public void onItemClick(int pos) {
        health_manager_zb.setText(zbString.get(pos));
        selectedType = zbtype[pos];
    }

    List<String> zbString = new ArrayList<String>() {
        public List<String> $() {
            add("晨起收缩压");
            add("晨起舒张压");
            add("空腹血糖");
            add("餐后2小时血糖");
            add("血脂总胆固醇");
            add("血脂甘油三酯");
            add("血脂低密度脂蛋白胆固醇");
            add("血脂高密度脂蛋白胆固醇");
            add("尿酸");
            add("静息心率");
            add("脉搏");
            add("血氧饱和度");
            add("随机血糖");
            return this;
        }
    }.$();
    int[] zbtype = new int[]{1, 2, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 7};

}
