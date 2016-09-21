package com.uyi.app.ui.health;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/9/18.
 *
 */
@ContentView(R.layout.activity_preview_personal_program)
public class PreviewPersonalProgramActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.lrr) private TextView lrr;
    @ViewInject(R.id.lrsj) private TextView lrsj;
    @ViewInject(R.id.no_selution) private TextView no_selution;
    @ViewInject(R.id.scroll) private ScrollView scroll;

    @ViewInject(R.id.layout_icvd) private LinearLayout layout_icvd;
    @ViewInject(R.id.layout_ASCVD) private LinearLayout layout_ASCVD;
    @ViewInject(R.id.layout_xy) private LinearLayout layout_xy;
    @ViewInject(R.id.layout_xt) private LinearLayout layout_xt;
    @ViewInject(R.id.layout_zdgz) private LinearLayout layout_zdgz;
    @ViewInject(R.id.layout_jkjy) private LinearLayout layout_jkjy;

    @ViewInject(R.id.xygljy) private TextView xygljy;
    @ViewInject(R.id.xtgljy) private TextView xtgljy;
    @ViewInject(R.id.xgzjcjgjjy) private TextView xgzjcjgjjy;
    @ViewInject(R.id.zlfajy) private TextView zlfajy;
    @ViewInject(R.id.ysjyjjjtx) private TextView ysjyjjjtx;
    @ViewInject(R.id.ydjy) private TextView ydjy;
    @ViewInject(R.id.grxgjy) private TextView grxgjy;

    String id = "";
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("个人方案").setTitleColor(getResources().getColor(R.color.blue));
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.HEALTH_PERSONAL_PROGRAM, UserInfoManager.getLoginUserInfo(this).userId),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        Loading.bulid(activity, null).dismiss();
                        System.out.print("_________________________________" + data.toString());
                        try {
                            if (data.has("healthManagementJson")) {
                                JSONObject healthManagementJson = data.getJSONObject("healthManagementJson");
                                if (healthManagementJson.getString("doctorName").equals("null")) {
//                                    lrr.setText("");
                                    no_selution.setVisibility(View.VISIBLE);
                                    scroll.setVisibility(View.GONE);

                                } else {
                                    no_selution.setVisibility(View.GONE);
                                    scroll.setVisibility(View.VISIBLE);
                                    lrr.setText(healthManagementJson.getString("doctorName"));
                                }

                                JSONObject personalHealthManagementTemplateJson = healthManagementJson.getJSONObject("personalHealthManagementTemplateJson");
                                if (personalHealthManagementTemplateJson.has("id")) {
                                    id = personalHealthManagementTemplateJson.getString("id");
                                }

                                xygljy.setText(personalHealthManagementTemplateJson.getString("bloodPressureManagementAdvice"));
                                xtgljy.setText(personalHealthManagementTemplateJson.getString("bloodSugarManagementAdvice"));
                                xgzjcjgjjy.setText(personalHealthManagementTemplateJson.getString("resultsAndSuggestions"));
                                zlfajy.setText(personalHealthManagementTemplateJson.getString("medicalAdvice"));
                                ysjyjjjtx.setText(personalHealthManagementTemplateJson.getString("dietaryAdviceToRemindAndTaboos"));
                                ydjy.setText(personalHealthManagementTemplateJson.getString("exerciseAdvice"));
                                grxgjy.setText(personalHealthManagementTemplateJson.getString("personalHabitsSuggest"));
                                if (personalHealthManagementTemplateJson.has("updateTime")) {
                                    lrsj.setText(personalHealthManagementTemplateJson.getString("updateTime"));
                                }
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
}
