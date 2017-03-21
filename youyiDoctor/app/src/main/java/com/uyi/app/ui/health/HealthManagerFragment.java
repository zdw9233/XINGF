package com.uyi.app.ui.health;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.personal.standard.AlarmStandardActivity;
import com.uyi.app.ui.report.ReportListActivity;
import com.uyi.app.ui.stroke.StrokeListActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
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
    @ViewInject(R.id.assessment)
    private LinearLayout assessment;
    @ViewInject(R.id.follow_up)
    private LinearLayout follow_up;
    @ViewInject(R.id.life)
    private LinearLayout life;
    @ViewInject(R.id.sanjia)
    private LinearLayout sanjia;
    @ViewInject(R.id.apoplexy)
    private LinearLayout apoplexy;

//    public HealthManagerFragment(Main main) {
//        this.main = main;
//    }

    @OnClick({
            R.id.diagnosis, //主诊报告
            R.id.apoplexy, //卒中随访
            R.id.report,       //健康报告
            R.id.database,      //健康数据库
            R.id.assessment,    //风险评估
            R.id.life,          //生活方式(个人方案)
            R.id.diet,          //饮食计划
            R.id.alarm,         //设置报警
            R.id.telephone,      //电话回访
            R.id.sanjia,      //三甲方案
            R.id.follow_up     //三甲随访
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diagnosis:
                startActivity(new Intent(this, ReportListActivity.class));
                break; //主诊报告
            case  R.id.apoplexy:

                startActivity(new Intent(this, StrokeListActivity.class));
//                startActivity(new Intent(this, TestActivity.class));
                break; //卒中随访
            case R.id.report:     //健康报告
                startActivity(new Intent(this, HealthManagerMain.class));
                break;
            case R.id.database:    //健康数据库
                startActivity(new Intent(this, HealthDatabaseActivity.class));
                break;
            case R.id.assessment: //风险评估
                startActivity(new Intent(this, RiskAssessmentActivity.class));
                break;
            case R.id.life:      //个人方案
//                startActivity(new Intent(this, LifeStyleActivity.class));
                startActivity(new Intent(this, PersonalProgramListActivity.class));
                break;
            case R.id.sanjia:      //三甲方案
                startActivity(new Intent(this, ThreeTopListActivity.class));
                break;
            case R.id.diet:     //饮食计划
                startActivity(new Intent(this, DietPlanActivity.class));
                break;
            case R.id.alarm:   //设置报警
                startActivity(new Intent(this, AlarmStandardActivity.class));
                break;
            case R.id.follow_up:   //三甲随访
                startActivity(new Intent(this, FollowUpaActivity.class));
                break;
            case R.id.telephone:
                //电话

//			UserInfo userInfo = UserInfoManager.getLoginUserInfo(this);
//			userInfo.password = pwd;
//			UserInfoManager.setLoginUserInfo(this,userInfo);
//			L.e("userinfo == ",userInfo.toString());
                RequestManager.getObject(String.format(Constens.COSTOM_HEALTH_CALL_PHONE, FragmentHealthListManager.customer), activity,null ,null, new RequestErrorListener(){
                    @Override
                    public void requestError(VolleyError e) {
                        if (e.networkResponse != null) {
                            if (e.networkResponse.statusCode == 200) {

                            } else {

                            }
                        } else {

                        }
                    }
                });
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
//        if(UserInfoManager.getLoginUserInfo(this).groupType == 1){
//            apoplexy.setVisibility(View.VISIBLE);
//        }else{
//            apoplexy.setVisibility(View.GONE);
//        }
        follow_up.setVisibility(View.GONE);
        sanjia.setVisibility(View.GONE );
        RequestManager.getObject(String.format(Constens.IS_THREE_TOP, FragmentHealthListManager.customer),this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                System.out.println(data.toString());
                try {
                    if (data.getBoolean("topTeam")) {
                        follow_up.setVisibility(View.VISIBLE);
                        sanjia.setVisibility(View.VISIBLE  );
                        life.setVisibility(View.GONE);
                        assessment.setVisibility(View.GONE  );
                    }else{
                        life.setVisibility(View.VISIBLE);
                        assessment.setVisibility(View.VISIBLE );
                        follow_up.setVisibility(View.GONE);
                        sanjia.setVisibility(View.GONE );
                    }
                    if (data.getBoolean("strokeFollowUp")) {
                        apoplexy.setVisibility(View.VISIBLE);
                    }else{
                        apoplexy.setVisibility(View.GONE);
                    }

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

    @Override
    public void onChanage(int postion) {
    }
}
