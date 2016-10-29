package com.uyi.app.ui.health;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.report.ReportListActivity;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
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
    @ViewInject(R.id.fx)
    private FrameLayout fx;
    @ViewInject(R.id.sj)
    private FrameLayout sj;
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
    int isfree;

    @OnClick({
            R.id.diagnosis, //主诊报告
            R.id.follow_up, //三甲随访
            R.id.report,       //健康报告
            R.id.database,      //健康数据库
            R.id.assessment,    //风险评估
            R.id.life,          //生活方式
            R.id.diet,          //饮食计划
    })
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.diagnosis:
//                if (isfree == 1) {
////                    T.showShort(getContext(), " 该服务仅针对服务包用户，请购买相应服务包！");
//                    startActivity(new Intent(context, ReportListActivity.class));
//                    requsetOnItemClick(1);
//                } else {
                    startActivity(new Intent(context, ReportListActivity.class));
                    requsetOnItemClick(1);
//                }

                break; //主诊报告
            case R.id.follow_up:
                startActivity(new Intent(context, FollowUpaActivity.class));
                break; //三甲随访
            case R.id.report:     //健康报告
                startActivity(new Intent(context, HealthReportActivity.class));

                break;
            case R.id.database:    //健康数据库
                startActivity(new Intent(context, HealthDatabaseActivity.class));
                break;
            case R.id.assessment: //风险评估
//                if (isfree == 1) {
//                    T.showShort(getContext(), " 该服务仅针对服务包用户，请购买相应服务包！");
//                    startActivity(new Intent(getContext(), RiskAssessmentActivity.class));
//                    requsetOnItemClick(2);
//                } else {
                    startActivity(new Intent(getContext(), RiskAssessmentActivity.class));
                    requsetOnItemClick(2);
//                }
                break;
            case R.id.life:      //生活方式
//                if (isfree == 1) {
//                    T.showShort(getContext(), " 该服务仅针对服务包用户，请购买相应服务包！");
//                    startActivity(new Intent(getContext(), LifeStyleActivity.class));
//                    requsetOnItemClick(3);
//                } else {
                    startActivity(new Intent(getContext(), PreviewPersonalProgramActivity.class));
//                    requsetOnItemClick(3);
//                }
                break;
            case R.id.diet:     //饮食计划
//                if (isfree == 1) {
//                    T.showShort(getContext(), " 该服务仅针对服务包用户，请购买相应服务包！");
//                    startActivity(new Intent(getContext(), DietPlanActivity.class));
//                    requsetOnItemClick(4);
//                } else {
                    startActivity(new Intent(getContext(), DietPlanActivity.class));
                    requsetOnItemClick(4);
//                }
                break;
        }
    }

    public void requsetOnItemClick(int type) {
        RequestManager.getObject(String.format(Locale.CHINA, Constens.UPDATA_MESSEGE_COMSTOMER, type), getContext(), null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
            }
        }, new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon);
        isfree = UserInfoManager.getLoginUserInfo(getContext()).isFree;
        requestIsNew();
        onInitLayoutAfter();
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
        sj.setVisibility(View.GONE);
        fx.setVisibility(View.GONE );
        RequestManager.getObject(String.format(Constens.IS_THREE_TOP,UserInfoManager.getLoginUserInfo(context).userId), getContext(), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                System.out.println(data.toString());
                try {
                    if (data.getBoolean("topTeam")) {
                        sj.setVisibility(View.VISIBLE);
                        fx.setVisibility(View.GONE  );
                    }else{
                        sj.setVisibility(View.GONE);
                        fx.setVisibility(View.VISIBLE );
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
//                    if (data.getBoolean("isLife")) {
//                        life_num.setVisibility(View.VISIBLE);
//                    } else life_num.setVisibility(View.GONE);
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
