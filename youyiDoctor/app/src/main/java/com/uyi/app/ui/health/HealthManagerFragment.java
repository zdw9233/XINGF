package com.uyi.app.ui.health;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.personal.standard.AlarmStandardActivity;
import com.uyi.app.ui.report.ReportActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 健康管理 Created by ThinkPad on 2016/6/13.
 */
@ContentView( R.layout.fragment_health_manager2_1)
public class HealthManagerFragment extends BaseFragmentActivity implements HeaderView.OnTabChanage {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;

//    public HealthManagerFragment(Main main) {
//        this.main = main;
//    }

    @OnClick({
            R.id.diagnosis, //主诊报告
            R.id.report,       //健康报告
            R.id.database,      //健康数据库
            R.id.assessment,    //风险评估
            R.id.life,          //生活方式
            R.id.diet,          //饮食计划
            R.id.alarm,         //设置报警
            R.id.telephone      //电话回访
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diagnosis:
                startActivity(new Intent(this, ReportActivity.class));
                break; //主诊报告
            case R.id.report:     //健康报告
                startActivity(new Intent(this, HealthManagerMain.class));
                break;
            case R.id.database:    //健康数据库
                startActivity(new Intent(this, HealthDatabaseActivity.class));
                break;
            case R.id.assessment: //风险评估
                startActivity(new Intent(this, RiskAssessmentActivity.class));
                break;
            case R.id.life:      //生活方式
                startActivity(new Intent(this, LifeStyleActivity.class));
                break;
            case R.id.diet:     //饮食计划
                startActivity(new Intent(this, DietPlanActivity.class));
                break;
            case R.id.alarm:   //设置报警
                startActivity(new Intent(this, AlarmStandardActivity.class));
                break;
            case R.id.telephone:
                //个人资料
                RequestManager.getObject(String.format(Constens.DOCTOR_QUERY_CUSTOMER_INFO, FragmentHealthListManager.customer), activity, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                        try {
                            String inputStr = data.getString("phoneNumber");
                            L.e("phone",inputStr);
                            if (inputStr.trim().length() != 0 &&!inputStr.equals("空")&&!inputStr.equals("null")) {
                                Intent phoneIntent = new Intent(
                                        "android.intent.action.CALL", Uri.parse("tel:"
                                        + inputStr));
                                // 启动
                                startActivity(phoneIntent);
                            }else{
                                T.showShort(activity, "该用户没有记录他的联系方式！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


                break;    //电话回访
        }
    }



    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true)
                .showTab(false).showRight(true).showTitle(true)
                .setTitle("健康管理").setTitleColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onChanage(int postion) {
    }
}
