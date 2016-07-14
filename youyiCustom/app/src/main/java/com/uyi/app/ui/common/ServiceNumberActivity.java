package com.uyi.app.ui.common;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/7/13.
 */
@ContentView(R.layout.activity_sevice_number)
public class ServiceNumberActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.bloodPressure)
    private TextView bloodPressure;
    @ViewInject(R.id.bloodSugar)
    private TextView bloodSugar;
    @ViewInject(R.id.bloodLipid)
    private TextView bloodLipid;
    @ViewInject(R.id.bloodOxygen)
    private TextView bloodOxygen;
    @ViewInject(R.id.ecg)
    private TextView ecg;
    @ViewInject(R.id.uric)
    private TextView uric;
    @ViewInject(R.id.healthAdvisory)
    private TextView healthAdvisory;
    @ViewInject(R.id.stageReport)
    private TextView stageReport;
    @ViewInject(R.id.doctorPhoneVisit)
    private TextView doctorPhoneVisit;
    @ViewInject(R.id.customerCarePhoneVisit)
    private TextView customerCarePhoneVisit;
    @ViewInject(R.id.doctorAdvisory)
    private TextView doctorAdvisory;



    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("个人信息").setTitleColor(getResources().getColor(R.color.blue));
        RequestManager.getObject(String.format(Constens.SERVICE_NUMBER),this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject data) {
                try {
                    Log.e("DATA_________________",data.toString());
                    bloodPressure.setText(data.getString("bloodPressure"));
                    bloodSugar.setText(data.getString("bloodSugar"));
                    bloodLipid.setText(data.getString("bloodLipid"));
                    bloodOxygen.setText(data.getString("bloodOxygen"));
                    ecg.setText(data.getString("ecg"));
                    uric.setText(data.getString("uric"));
                    healthAdvisory.setText(data.getString("healthAdvisory"));
                    stageReport.setText(data.getString("stageReport"));
                    doctorPhoneVisit.setText(data.getString("doctorPhoneVisit"));
                    customerCarePhoneVisit.setText(data.getString("customerCarePhoneVisit"));
                    doctorAdvisory.setText(data.getString("doctorAdvisory"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
