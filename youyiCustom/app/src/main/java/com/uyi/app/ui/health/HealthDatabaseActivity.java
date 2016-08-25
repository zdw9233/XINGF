package com.uyi.app.ui.health;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.adapter.HealthDatabaseAdapter;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * 健康数据库
 *
 * @author user
 */
@ContentView(R.layout.fragment_health_database)
public class HealthDatabaseActivity extends BaseActivity implements OnItemClickListener<Map<String, Object>>, Pager, OnRefreshListener {

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
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private HealthDatabaseAdapter healthDatabaseAdapter;

    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    Calendar cal = Calendar.getInstance();
    /**
     * 选则时间
     */
    private int selectedDate;
    String startDate;
    String endDate;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("健康数据库").setTitleColor(getResources().getColor(R.color.blue));
        String[] str = getResources().getStringArray(R.array.health_manager);
        headerView.setTitleTabs(str);
        headerView.selectTabItem(2);
        linearLayoutManager = new LinearLayoutManager(this);
        healthDatabaseAdapter = new HealthDatabaseAdapter(this);
        healthDatabaseAdapter.setOnItemClickListener(this);
        healthDatabaseAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(healthDatabaseAdapter);
        recyclerView.setPager(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
    @OnClick({R.id.health_database_starttime, R.id.health_database_endtime, R.id.health_database_submit, R.id.new_health_database_layout_start})
    public void onClick(View v) {
        if (v.getId() == R.id.health_database_starttime) {
            selectedDate = 1;
            selectedDate();
        } else if (v.getId() == R.id.health_database_endtime) {
            selectedDate = 2;
            selectedDate();
        } else if (v.getId() == R.id.health_database_submit) {
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
            onRefresh();
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
//		final AlertDialog dialog = new AlertDialog.Builder(this).create();
//        dialog.show();
//        DatePicker picker = new DatePicker(getView().getContext());
//        picker.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
//        picker.setMode(DPMode.SINGLE);
//        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
//            @Override
//            public void onDatePicked(String date) {
//           	 text.setText(date);
//	           	 if(isStartDate){
//	           		startDate  = date;
//	           	 }else{
//	           		 endDate  = date;
//	           	 }
//                dialog.dismiss();
//            }
//        });
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setContentView(picker, params);
//        dialog.getWindow().setGravity(Gravity.CENTER);

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



    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        String id = data.get("id").toString();
        Intent intent = new Intent(this, HealthDatabaseDetails.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        Loading.bulid(this, null).show();
        RequestManager.getObject(String.format(Constens.HEALTH_CHECK_INFOS, startDate, endDate, pageNo, pageSize), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                Loading.bulid(HealthDatabaseActivity.this, null).dismiss();
                try {
                    L.d(TAG, data.toString());
                    totalPage = data.getInt("pages");
                    JSONArray array = data.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
                        item.put("id", jsonObject.getInt("id"));
                        item.put("uploadTime", jsonObject.getString("uploadTime"));
                        item.put("uploadItems", jsonObject.getString("uploadItems"));
                        item.put("isWarning", jsonObject.getBoolean("isWarning"));
                        datas.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                healthDatabaseAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

                if (pageNo <= totalPage) {
                    isLooding = true;
                    pageNo++;
                } else {
                    recyclerView.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        isLooding = true;
        datas.clear();
        recyclerView.setRefreshing(false);
        loadNextPage();
    }

}
