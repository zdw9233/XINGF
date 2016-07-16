package com.uyi.app.ui.health;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.ReportListActivity;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * 健康管理 Created by ThinkPad on 2016/6/13.
 */
public class HealthManagerFragment extends BaseFragment implements HeaderView.OnTabChanage {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.schedule_num)
    private TextView schedule_num;
    @ViewInject(R.id.notice_num)
    private TextView notice_num;
    @ViewInject(R.id.consulting_num)
    private TextView consulting_num;
    @ViewInject(R.id.assessment_num)
    private TextView assessment_num;
    @ViewInject(R.id.life_num)
    private TextView life_num;
    @ViewInject(R.id.diet_num)
    private TextView diet_num;
    JSONObject params;

    @OnClick({
            R.id.diagnosis, //主诊报告
            R.id.report,       //健康报告
            R.id.database,      //健康数据库
            R.id.assessment,    //风险评估
            R.id.life,          //生活方式
            R.id.diet,          //饮食计划
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diagnosis:
                startActivity(new Intent(context, ReportListActivity.class));
                RequestManager.postObject(String.format(Locale.CHINA, Constens.UPDATA_MESSEGE_COMSTOMER, 1), getContext(), new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                    }
                });
                break; //主诊报告
            case R.id.report:     //健康报告
                startActivity(new Intent(context, HealthReportActivity.class));

                break;
            case R.id.database:    //健康数据库
                startActivity(new Intent(context, HealthDatabaseActivity.class));
                break;
            case R.id.assessment: //风险评估
                startActivity(new Intent(getContext(), RiskAssessmentActivity.class));
                RequestManager.postObject(String.format(Locale.CHINA, Constens.UPDATA_MESSEGE_COMSTOMER, 2), getContext(), new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                    }
                });
                break;
            case R.id.life:      //生活方式
                startActivity(new Intent(getContext(), LifeStyleActivity.class));
                RequestManager.postObject(String.format(Locale.CHINA, Constens.UPDATA_MESSEGE_COMSTOMER, 3), getContext(), new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                        Log.e("yes", "true=============================================");
                    }

                });
                break;
            case R.id.diet:     //饮食计划
                startActivity(new Intent(getContext(), DietPlanActivity.class));
                RequestManager.postObject(String.format(Locale.CHINA, Constens.UPDATA_MESSEGE_COMSTOMER, 4), getContext(), new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                    }
                });
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon);
        requestIsNew();
    }

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_health_manager2_1;
    }

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon)
                .showTab(false).showRight(true).showTitle(true)
                .setTitle("健康管理").setTitleColor(getResources().getColor(R.color.blue));
        requestIsNew();
    }


    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onChanage(int postion) {
    }

    private void requestIsNew() {
        RequestManager.getObject(Constens.MESSEGE_COMSTOMER, getContext(), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                System.out.println(data.toString());
                try {
                    if (data.getBoolean("isDiagnosis")) {
                        schedule_num.setVisibility(View.VISIBLE);
                    } else schedule_num.setVisibility(View.GONE);
                    if (data.getBoolean("isAssessment")) {
                        assessment_num.setVisibility(View.VISIBLE);
                    } else assessment_num.setVisibility(View.GONE);
                    if (data.getBoolean("isLife")) {
                        life_num.setVisibility(View.VISIBLE);
                    } else life_num.setVisibility(View.GONE);
                    if (data.getBoolean("isDiet")) {
                        diet_num.setVisibility(View.VISIBLE);
                    } else diet_num.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
