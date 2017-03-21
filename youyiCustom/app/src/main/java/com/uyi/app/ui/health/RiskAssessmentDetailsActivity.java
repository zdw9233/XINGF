package com.uyi.app.ui.health;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.utils.SerializableMap;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/9/18.
 *
 */
@ContentView(R.layout.activity_riskassessment_details)
public class RiskAssessmentDetailsActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.time) private TextView time;
    @ViewInject(R.id.doctor) private TextView doctor;
    @ViewInject(R.id.icvd) private TextView icvd;
    @ViewInject(R.id.ascvd) private TextView ascvd;
    @ViewInject(R.id.xy) private TextView xy;
    @ViewInject(R.id.xt) private TextView xt;
    @ViewInject(R.id.zdgz) private TextView zdgz;
    @ViewInject(R.id.jkjy) private TextView jkjy;
    @ViewInject(R.id.layout_icvd) private LinearLayout layout_icvd;
    @ViewInject(R.id.layout_ASCVD) private LinearLayout layout_ASCVD;
    @ViewInject(R.id.layout_xy) private LinearLayout layout_xy;
    @ViewInject(R.id.layout_xt) private LinearLayout layout_xt;
    @ViewInject(R.id.layout_zdgz) private LinearLayout layout_zdgz;
    @ViewInject(R.id.layout_jkjy) private LinearLayout layout_jkjy;
    @ViewInject(R.id.h_asvcd) private TextView h_asvcd;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(false).setTitle("风险评估详情").setTitleColor(getResources().getColor(R.color.blue));
        RequestManager.getObject(String.format(Constens.IS_THREE_TOP,UserInfoManager.getLoginUserInfo(this).userId), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                System.out.println(data.toString());
                try {
                    if(data.getBoolean("strokeFollowUp")){
                        layout_icvd.setVisibility(View.GONE);
                    }else{
                        layout_icvd.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("map");
        time.setText(serializableMap.getMap().get("createTime").toString());
        doctor.setText(serializableMap.getMap().get("doc_name").toString());
        if(serializableMap.getMap().get("percentageICVD").toString().equals("%")){
            icvd.setText("0%");
        }else{
            icvd.setText(serializableMap.getMap().get("percentageICVD").toString());
        }
     if(serializableMap.getMap().get("percentageASVCD").toString().equals("%")){
         ascvd.setText("0%");
     }else{
         ascvd.setText(serializableMap.getMap().get("percentageASVCD").toString());
     }

        if(serializableMap.getMap().get("bloodPressureConditions").toString().equals("1")){
            xy.setText("良好");
        }else if(serializableMap.getMap().get("bloodPressureConditions").toString().equals("2")){
            xy.setText("一般");
        }else if(serializableMap.getMap().get("bloodPressureConditions").toString().equals("3")){
            xy.setText("较差");
        }
        if(serializableMap.getMap().get("bloodSugarConditions").toString().equals("1")){
            xt.setText("良好");
        }else if(serializableMap.getMap().get("bloodSugarConditions").toString().equals("2")){
            xt.setText("一般");
        }else if(serializableMap.getMap().get("bloodSugarConditions").toString().equals("3")){
            xt.setText("较差");
        }
        zdgz.setText(serializableMap.getMap().get("healthIndicator").toString());
        jkjy.setText(serializableMap.getMap().get("advice").toString());
        if(!ascvd.getText().toString().equals("")){
            try {
                String str = ascvd.getText().toString().substring(0,ascvd.getText().toString().length()-1);
                if( Double.parseDouble(str) > 10){
                    h_asvcd.setText("高");
                    h_asvcd.setTextColor(getResources().getColor(R.color.red));
                }else{
                    h_asvcd.setText("低");
                    h_asvcd.setTextColor(getResources().getColor(R.color.tet_type_6));
                }
            }catch (Exception e){

            }

        }

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
}
