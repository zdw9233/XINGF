package com.uyi.app.ui.common;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.adapter.ServiceAdapter;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/7/13.
 */
@ContentView(R.layout.activity_sevice_number)
public class ServiceNumberActivity extends BaseActivity {

    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.header_image)
    private RoundedImageView header_image;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.guardian)
    private TextView guardian;
    @ViewInject(R.id.doctor)
    private TextView doctor;
    @ViewInject(R.id.service)
    private TextView service;
    @ViewInject(R.id.service_time)
    private TextView service_time;
    @ViewInject(R.id.team)
    private TextView team;
    @ViewInject(R.id.beans)
    private TextView beans;
//    @ViewInject(R.id.bloodPressure)
//    private TextView bloodPressure;
//    @ViewInject(R.id.bloodSugar)
//    private TextView bloodSugar;
//    @ViewInject(R.id.bloodLipid)
//    private TextView bloodLipid;
//    @ViewInject(R.id.bloodOxygen)
//    private TextView bloodOxygen;
//    @ViewInject(R.id.ecg)
//    private TextView ecg;
//    @ViewInject(R.id.uric)
//    private TextView uric;
//    @ViewInject(R.id.healthAdvisory)
//    private TextView healthAdvisory;
//    @ViewInject(R.id.stageReport)
//    private TextView stageReport;
//    @ViewInject(R.id.doctorPhoneVisit)
//    private TextView doctorPhoneVisit;
//    @ViewInject(R.id.customerCarePhoneVisit)
//    private TextView customerCarePhoneVisit;
//    @ViewInject(R.id.doctorAdvisory)
//    private TextView doctorAdvisory;
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private LinearLayoutManager linearLayoutManager;
    private ServiceAdapter healthTeamAdapter;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("个人信息").setTitleColor(getResources().getColor(R.color.blue));
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        healthTeamAdapter = new ServiceAdapter(this);
        healthTeamAdapter.setDatas(datas);
        recyclerView.setAdapter(healthTeamAdapter);
        RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    System.out.println(data.toString());
                    ImageCacheManager.loadImage(data.getString("icon"), ImageCacheManager.getImageListener(header_image, null, null));
                    name.setText(data.getString("realName"));
                    beans.setText(data.getString("beans"));
                    if(data.getString("attendingDoctorName").equals("null"))
                    doctor.setText("无");
                    else
                        doctor.setText(data.getString("attendingDoctorName"));
                    if(data.getString("guardianInfo").equals("null")){
                        guardian.setText("无");
                    }else{
                        guardian.setText(data.getJSONObject("guardianInfo").getString("name"));
                    }
//                    if(data.getJSONObject("guardianInfo"))
//                    name.setText(data.getJSONObject("guardianInfo").getString("name"));
//                    name.setText(data.getJSONObject("guardianInfo").getJSONObject("province").getString("name"));
//                    name.setText(data.getJSONObject("guardianInfo").getJSONObject("province").getString("name"));
//                    name.setText(data.getJSONObject("guardianInfo").getJSONObject("province").getString("name"));
//                    name.setText(data.getJSONObject("guardianInfo").getJSONObject("province").getString("name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        RequestManager.getObject(String.format(Constens.LIFE_DEIT, UserInfoManager.getLoginUserInfo(this).userId), activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    System.out.println(data.toString());

                    System.out.println(data.getJSONObject("serviceCount"));
                        service.setText(data.getJSONObject("serviceCount").getString("name"));
                        service_time.setText(data.getJSONObject("serviceCount").getString("endTime").equals("-")?"永久":data.getJSONObject("serviceCount").getString("endTime"));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         * 查询健康团队
         */
        RequestManager.getArray(Constens.HEALTH_GROUPS, activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                Loading.bulid(activity, null).dismiss();
                if(array.length() == 0 ){
//                    T.showShort(application, "请先加入健康团队");
//                    finish();
                }else{
                    for(int i = 0;i<array.length();i++){
                        try {
                            JSONObject jsonObject = array.getJSONObject(i);
                            team.setText(jsonObject.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }



            }
        });
        RequestManager.getArray(Constens.SERVICE_NUMBER,activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray data) {
                try {
                    System.out.println(data.toString());
                    for (int i = 0; i < data.length(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        JSONObject jsonObject = data.getJSONObject(i);
                        item.put("name", jsonObject.getString("name"));
                        if(jsonObject.getString("defaultCount").equals("-1")){

                            item.put("defaultCount", "不限");
                            item.put("surplusCount",    "不限");
                        }else{
                            item.put("defaultCount", jsonObject.getString("defaultCount"));
                            item.put("surplusCount", jsonObject.getString("surplusCount"));
                        }


                        datas.add(item);
                    }
                    System.out.println(datas.toString()+"=============================");

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                healthTeamAdapter.notifyDataSetChanged();
            }
        });
//        System.out.println(Constens.SERVICE_NUMBER.toString()+"=============================");

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }



}
