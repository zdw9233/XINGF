package com.uyi.app.ui.home.fragment;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.home.fragment.adapter.DataAdapter2_1;
import com.uyi.app.ui.home.fragment.model.QuaReport;
import com.uyi.app.utils.ImageUtil;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


/**
 * 我的报告（新） Created by zdw on 2016/6/19.
 */
@ContentView(R.layout.activity_month_report_2_1)
public class QuaReportDetailsActivity extends BaseFragmentActivity {
    @ViewInject(R.id.tab_1)
    private TextView tab_1;
    @ViewInject(R.id.yhjbzl_context)
    private LinearLayout yhjbzl_context;
    @ViewInject(R.id.xyqst_context)
    private LinearLayout xyqst_context;
    @ViewInject(R.id.xtqst_context)
    private LinearLayout xtqst_context;
    @ViewInject(R.id.qmtzjc_context)
    private LinearLayout qmtzjc_context;
    @ViewInject(R.id.nsxzsxjc_context)
    private LinearLayout nsxzsxjc_context;
    @ViewInject(R.id.lay)
    private LinearLayout lay;
    private View currentView;
    private QuaReport quaReport;
    String startData;
    String endData;
    //用户基本资料

    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.idcard)
    private TextView idcard;
    @ViewInject(R.id.hight)
    private TextView hight;
    @ViewInject(R.id.wight)
    private TextView wight;
    @ViewInject(R.id.BMI)
    private TextView BMI;
    @ViewInject(R.id.bingshi)
    private TextView bingshi;
    @ViewInject(R.id.time)
    private TextView time;
    @ViewInject(R.id.number)
    private TextView number;
    //尿酸血脂4项
    @ViewInject(R.id.recyclerView1)
    private EndlessRecyclerView recyclerView1;
    private ArrayList<Map<String, Object>> datas1 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView2)
    private EndlessRecyclerView recyclerView2;
    private ArrayList<Map<String, Object>> datas2 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView3)
    private EndlessRecyclerView recyclerView3;
    private ArrayList<Map<String, Object>> datas3 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView4)
    private EndlessRecyclerView recyclerView4;
    private ArrayList<Map<String, Object>> datas4 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView5)
    private EndlessRecyclerView recyclerView5;
    private ArrayList<Map<String, Object>> datas5 = new ArrayList<Map<String, Object>>();
    //全面体征
    @ViewInject(R.id.recyclerView6)
    private EndlessRecyclerView recyclerView6;
    private ArrayList<Map<String, Object>> datas6 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView7)
    private EndlessRecyclerView recyclerView7;
    private ArrayList<Map<String, Object>> datas7 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView8)
    private EndlessRecyclerView recyclerView8;
    private ArrayList<Map<String, Object>> datas8 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView9)
    private EndlessRecyclerView recyclerView9;
    private ArrayList<Map<String, Object>> datas9 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView10)
    private EndlessRecyclerView recyclerView10;
    private ArrayList<Map<String, Object>> datas10 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView11)
    private EndlessRecyclerView recyclerView11;
    private ArrayList<Map<String, Object>> datas11 = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView12)
    private EndlessRecyclerView recyclerView12;
    private ArrayList<Map<String, Object>> datas12 = new ArrayList<Map<String, Object>>();
    //血压趋势
    @ViewInject(R.id.recyclerView_xy)
    private EndlessRecyclerView recyclerView_xy;
    private ArrayList<Map<String, Object>> datas_xy = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.img_xy)
    private SimpleDraweeView img_xy;
    @ViewInject(R.id.img_xy_text)
    private TextView img_xy_text;
    //血糖趋势
    @ViewInject(R.id.recyclerView_xt)
    private EndlessRecyclerView recyclerView_xt;
    private ArrayList<Map<String, Object>> datas_xt = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.img_xt)
    private SimpleDraweeView img_xt;
    @ViewInject(R.id.img_xt_text)
    private TextView img_xt_text;

    private LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager7 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager8 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager9 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager10 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager11 = new LinearLayoutManager(activity);
    private LinearLayoutManager linearLayoutManager12 = new LinearLayoutManager(activity);

    DataAdapter2_1 adapter1 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter2 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter3 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter4 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter5 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter6 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter7 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter8 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter9 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter10 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter11 = new DataAdapter2_1(this);
    DataAdapter2_1 adapter12 = new DataAdapter2_1(this);


    @OnClick({
            R.id.yhjbxx,       //
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
                showContext(1);
                break;  //
            case R.id.qmtzjc:
                showContext(2);
                break;  //
            case R.id.xyqst:
                showContext(3);
                break;  //
            case R.id.xtqst:
                showContext(4);
                break;  //
            case R.id.nsxzsxjc:
                showContext(5);
                break;  //
        }
        currentView = view;
    }

    private void showContext(int i) {
        yhjbzl_context.setVisibility(View.GONE);
        xyqst_context.setVisibility(View.GONE);
        xtqst_context.setVisibility(View.GONE);
        qmtzjc_context.setVisibility(View.GONE);
        nsxzsxjc_context.setVisibility(View.GONE);
        switch (i) {
            case 1:
                yhjbzl_context.setVisibility(View.VISIBLE);
                break;
            case 2:
                qmtzjc_context.setVisibility(View.VISIBLE);
                break;
            case 3:
                xyqst_context.setVisibility(View.VISIBLE);
                break;
            case 4:
                xtqst_context.setVisibility(View.VISIBLE);
                break;
            case 5:
                nsxzsxjc_context.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    protected void onInitLayoutAfter() {
        tab_1.setText("季度健康报告");
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        recyclerView4.setLayoutManager(linearLayoutManager4);
        recyclerView5.setLayoutManager(linearLayoutManager5);
        recyclerView6.setLayoutManager(linearLayoutManager6);
        recyclerView7.setLayoutManager(linearLayoutManager7);
        recyclerView8.setLayoutManager(linearLayoutManager8);
        recyclerView9.setLayoutManager(linearLayoutManager9);
        recyclerView10.setLayoutManager(linearLayoutManager10);
        recyclerView11.setLayoutManager(linearLayoutManager11);
        recyclerView12.setLayoutManager(linearLayoutManager12);
        adapter1.setDatas(datas1);
        adapter2.setDatas(datas2);
        adapter3.setDatas(datas3);
        adapter4.setDatas(datas4);
        adapter5.setDatas(datas5);
        adapter6.setDatas(datas6);
        adapter7.setDatas(datas7);
        adapter8.setDatas(datas8);
        adapter9.setDatas(datas9);
        adapter10.setDatas(datas10);
        adapter11.setDatas(datas11);
        adapter12.setDatas(datas12);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);
        recyclerView4.setAdapter(adapter4);
        recyclerView5.setAdapter(adapter5);
        recyclerView6.setAdapter(adapter6);
        recyclerView7.setAdapter(adapter7);
        recyclerView8.setAdapter(adapter8);
        recyclerView9.setAdapter(adapter9);
        recyclerView10.setAdapter(adapter10);
        recyclerView11.setAdapter(adapter11);
        recyclerView12.setAdapter(adapter12);
        onItemsClick(findViewById(R.id.yhjbxx));
        startData = getIntent().getStringExtra("startDate");
        endData = getIntent().getStringExtra("endDate");
        L.e(startData + "--" + endData);
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_MONTH_AND_QUA_DETAILS, startData, endData),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        L.e(data.toString());
                        quaReport = JSON.parseObject(data.toString(), QuaReport.class);
                        name.setText(quaReport.getRealName());
                        idcard.setText(quaReport.getIdNumber());
                        hight.setText(quaReport.getHeight()+"cm");
                        wight.setText(quaReport.getWeight()+"Kg");
                        BMI.setText(quaReport.getBmi());
                        bingshi.setText(quaReport.getChronicDiseaseType());
                        time.setText(startData + "~" + endData);
                        number.setText(quaReport.getAllNum() + "次");
                        ImageUtil.load(quaReport.getBloodPressure_pic(), img_xy);
                        ImageUtil.load(quaReport.getBloodSugar_pic(), img_xt);
                        img_xy_text.setText(quaReport.getPressureReportText());
                        img_xt_text.setText(quaReport.getSugarReportText());
                        for (int i = 0; i < quaReport.getBloodFats().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getBloodFats().get(i).getCheckTime());
                            map.put("data", quaReport.getBloodFats().get(i).getUrineAcid());
                            datas1.add(map);
                        }
                        adapter1.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getBloodFats().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getBloodFats().get(i).getCheckTime());
                            map.put("data", quaReport.getBloodFats().get(i).getBloodFatChol());
                            datas2.add(map);
                        }
                        adapter2.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getBloodFats().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getBloodFats().get(i).getCheckTime());
                            map.put("data", quaReport.getBloodFats().get(i).getBloodFatTg());
                            datas3.add(map);
                        }
                        adapter3.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getBloodFats().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getBloodFats().get(i).getCheckTime());
                            map.put("data", quaReport.getBloodFats().get(i).getBloodFatHdl());
                            datas4.add(map);
                        }
                        adapter4.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getBloodFats().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getBloodFats().get(i).getCheckTime());
                            map.put("data", quaReport.getBloodFats().get(i).getBloodFatLdl());
                            datas5.add(map);
                        }
                        adapter5.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getRoutines().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getRoutines().get(i).getCheckTime());
                            map.put("data", quaReport.getRoutines().get(i).getWaist());
                            datas6.add(map);
                        }
                        L.e(datas6.size()+"");
                        adapter6.notifyDataSetChanged();
                        recyclerView6.setAdapter(new DataAdapter2_1(activity));
                        for (int i = 0; i < quaReport.getRoutines().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getRoutines().get(i).getCheckTime());
                            map.put("data", quaReport.getRoutines().get(i).getHipline());
                            datas7.add(map);
                        }
                        L.e(datas7.size()+"");
                        adapter7.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getRoutines().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getRoutines().get(i).getCheckTime());
                            map.put("data", quaReport.getRoutines().get(i).getWhr());
                            datas8.add(map);
                        }
                        adapter8.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getRoutines().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getRoutines().get(i).getCheckTime());
                            map.put("data", quaReport.getRoutines().get(i).getFatPercentage());
                            datas9.add(map);
                        }
                        adapter9.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getRoutines().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getRoutines().get(i).getCheckTime());
                            map.put("data", quaReport.getRoutines().get(i).getBasalMetabolism());
                            datas10.add(map);
                        }
                        adapter10.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getRoutines().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getRoutines().get(i).getCheckTime());
                            map.put("data", quaReport.getRoutines().get(i).getWaterContent());
                            datas11.add(map);
                        }
                        adapter11.notifyDataSetChanged();
                        for (int i = 0; i < quaReport.getRoutines().size(); i++) {
                            Map<String, Object> map = new ArrayMap<String, Object>();
                            map.put("time", quaReport.getRoutines().get(i).getCheckTime());
                            map.put("data", quaReport.getRoutines().get(i).getSpo());
                            datas12.add(map);
                        }
                        adapter12.notifyDataSetChanged();


                    }
                });
//        RequestManager.getObject(String.format(DOCTOR_HEALTH_MONTH_AND_QUA_DETAILS,"2016-12-09", "2017-03-09"),
//                activity,params , new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject data) {
//                        L.e(data.toString());
////                        try {
////                            JSONArray array = data.getJSONArray("results");
//////                            if (pageNo == 1) datas.clear();
////                            for (int i = 0; i < array.length(); i++) {
////                                Map<String, Object> item = new HashMap<String, Object>();
////                                JSONObject jsonObject = array.getJSONObject(i);
////                                item.put("startTime", jsonObject.getString("startTime"));
////                                item.put("endTime", jsonObject.getString("endTime"));
////                                item.put("id", jsonObject.getString("id"));
////                                item.put("checked", jsonObject.getString("checked"));
////                                item.put("reportType", jsonObject.getString("reportType"));
////                            }
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//
//                    }
//                }, new RequestErrorListener() {
//                    @Override
//                    public void requestError(VolleyError e) {
//
//                    }
//                });
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
