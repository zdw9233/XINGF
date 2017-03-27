package com.uyi.app.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.health.PreviewPersonalProgramActivity;
import com.uyi.app.ui.health.RiskAssessmentDetailsActivity;
import com.uyi.app.ui.health.ThreeTopDetailsActivity;
import com.uyi.app.ui.report.ReportMainActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.SerializableMap;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.uyi.app.UYIApplication.getContext;

/**
 * 我的报告（新） Created by zdw on 2016/6/19.
 */
@ContentView(R.layout.activity_my_report_2_1)
public class MyReport extends BaseFragmentActivity {
    //总页面
    @ViewInject(R.id.lay)
    private LinearLayout lay;
    @ViewInject(R.id.tab_line_1)
    private TextView tab_line_1;
    @ViewInject(R.id.tab_1)
    private TextView tab_1;
    @ViewInject(R.id.tab_2)
    private TextView tab_2;
    @ViewInject(R.id.tab_line_2)
    private TextView tab_line_2;
    @ViewInject(R.id.new_scroll)
    private ScrollView new_scroll;
    @ViewInject(R.id.old_scroll)
    private LinearLayout old_scroll;
    int choseType = 1;
    public static boolean isTop;

    //最新
    @ViewInject(R.id.content1)
    private RelativeLayout content1;
    @ViewInject(R.id.content2)
    private RelativeLayout content2;
    @ViewInject(R.id.content3)
    private RelativeLayout content3;
    @ViewInject(R.id.content4)
    private RelativeLayout content4;
    @ViewInject(R.id.content5)
    private RelativeLayout content5;
String monthStartTime  = "";
    String monthEndTime  = "";
    String quaStartTime  = "";
    String quaEndTime  = "";
    @ViewInject(R.id.image1)
    private ImageView image1;
    @ViewInject(R.id.image2)
    private ImageView image2;
    @ViewInject(R.id.image3)
    private ImageView image3;
    @ViewInject(R.id.image4)
    private ImageView image4;
    @ViewInject(R.id.image5)
    private ImageView image5;
    @ViewInject(R.id.image1_bag)
    private ImageView image1_bag;
    @ViewInject(R.id.image2_bag)
    private ImageView image2_bag;
    @ViewInject(R.id.image3_bag)
    private ImageView image3_bag;
    @ViewInject(R.id.image4_bag)
    private ImageView image4_bag;
    @ViewInject(R.id.image5_bag)
    private ImageView image5_bag;

    @ViewInject(R.id.news_1)
    private ImageView news_1;
    @ViewInject(R.id.news_2)
    private ImageView news_2;
    @ViewInject(R.id.news_3)
    private ImageView news_3;
    @ViewInject(R.id.news_4)
    private ImageView news_4;
    @ViewInject(R.id.news_5)
    private ImageView news_5;

    @ViewInject(R.id.text1)
    private TextView text1;
    @ViewInject(R.id.text2)
    private TextView text2;
    @ViewInject(R.id.text3)
    private TextView text3;
    @ViewInject(R.id.text4)
    private TextView text4;
    @ViewInject(R.id.text5)
    private TextView text5;

    @ViewInject(R.id.time1)
    private TextView time1;
    @ViewInject(R.id.time2)
    private TextView time2;
    @ViewInject(R.id.time3)
    private TextView time3;
    @ViewInject(R.id.time4)
    private TextView time4;
    @ViewInject(R.id.time5)
    private TextView time5;
    @ViewInject(R.id.new_report_time)
    private TextView new_report_time;
    public static int chosehosttype = 6;
    //记录最新的数据
    JSONObject context1;
    int context1id;
    JSONObject context2;
    int context2id;
    JSONObject context3;
    int context3id;
    JSONObject context4;
    int context4id;
    JSONObject context5;
    Map<String, Object> item;
    Map<String, Object> item2;
    Map<String, Object> item3;
    Map<String, Object> item4;
    int mainReportId;
    //历史
    private View currentView;
    private List<Fragment> fragments;
    AssessmentFragment assessmentFragment = new AssessmentFragment();
    PersonalProgramFragment personalProgramFragment = new PersonalProgramFragment();
    MonthReportFragment monthReportFragment = new MonthReportFragment();
    QuaReportFragment quaReportFragment = new QuaReportFragment();
    MainReportFragment mainReportFragment = new MainReportFragment();
    ThreeTopFragment threeTopFragment = new ThreeTopFragment();
    private FragmentManager fm;// fragment管理器
    private Animation hyperspaceJumpAnimation;

    public void replaceView(int postion) {
        fm.beginTransaction().replace(R.id.content, fragments.get(postion)).commit();
    }

    @OnClick({
            R.id.fxpg,       //
            R.id.grfa,       //
            R.id.ybg,    //
            R.id.jbg,       //
            R.id.zzbg         //
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
            case R.id.fxpg:
                replaceView(0);
                break;  //
            case R.id.grfa:
                replaceView(1);
                break;  //
            case R.id.ybg:
                replaceView(2);
                break;  //
            case R.id.jbg:
                replaceView(3);
                break;  //
            case R.id.zzbg:
                replaceView(4);
                break;  //
        }
        currentView = view;
    }

    @OnClick({
            R.id.content1,  //风险评估
            R.id.content2,  //个人方案
            R.id.content3,  //月度报告
            R.id.content4,  //季度报告
            R.id.content5  //主诊报告
    })
    private void newClick(View v) {
        switch (v.getId()) {
            case R.id.content1:
                if (news_1.getVisibility() == View.VISIBLE) {
                    RequestManager.postObject(String.format(Constens.SETTING_REPORT, 1, context1id), this, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                        }
                    }, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            if (e.networkResponse != null) {
                                if (e.networkResponse.statusCode == 200) {
                                } else {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            } else {
                            }
                        }
                    });
                }
                Intent intent = new Intent();
                SerializableMap myMap = new SerializableMap();
                myMap.setMap(item);//将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("map", myMap);
                intent.putExtras(bundle);
                intent.setClass(activity, RiskAssessmentDetailsActivity.class);
                startActivityForResult(intent, 10003);
                break;  //风险评估
            case R.id.content2:
                if (!isTop) {
                    if (news_2.getVisibility() == View.VISIBLE) {
                        RequestManager.postObject(String.format(Constens.SETTING_REPORT,2, context2id), this, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                            }
                        }, new RequestErrorListener() {
                            @Override
                            public void requestError(VolleyError e) {
                                if (e.networkResponse != null) {
                                    if (e.networkResponse.statusCode == 200) {
                                    } else {
                                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                    }
                                } else {
                                }
                            }
                        });
                    }
                    L.e(context2id + "");
                    Intent intent2 = new Intent();
                    SerializableMap myMap2 = new SerializableMap();
                    myMap2.setMap(item2);//将map数据添加到封装的myMap中
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("map", myMap2);
                    intent2.putExtras(bundle2);
                    intent2.setClass(this, PreviewPersonalProgramActivity.class);
                    startActivityForResult(intent2, 10003);
                } else {
                    if (news_2.getVisibility() == View.VISIBLE) {
                        RequestManager.postObject(String.format(Constens.SETTING_REPORT,2, context2id), this, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                            }
                        }, new RequestErrorListener() {
                            @Override
                            public void requestError(VolleyError e) {
                                if (e.networkResponse != null) {
                                    if (e.networkResponse.statusCode == 200) {
                                    } else {
                                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                    }
                                } else {
                                }
                            }
                        });
                    }
                    L.e(context2id + "");
                    Intent intent2 = new Intent();
                    intent2.putExtra("id", context2id + "");
                    intent2.setClass(MyReport.this, ThreeTopDetailsActivity.class);
                    startActivityForResult(intent2, 10003);
                }
                break;  //个人方案
            case R.id.content3:
                if(news_2.getVisibility() == View.VISIBLE) {
                    RequestManager.postObject(String.format(Constens.SETTING_REPORT,4,context3id), this, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                        }
                    }, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            if (e.networkResponse != null) {
                                if (e.networkResponse.statusCode == 200) {
                                } else {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            } else {
                            }
                        }
                    });
                }
                Intent intent3 = new Intent();
                intent3.putExtra("startDate",monthStartTime);
                intent3.putExtra("endDate",monthEndTime);
                intent3.setClass(getContext(),MonthReportDetailsActivity.class);
                startActivityForResult(intent3,10003);
                break;   //月度报告
            case R.id.content4:
                if(news_2.getVisibility() == View.VISIBLE) {
                    RequestManager.postObject(String.format(Constens.SETTING_REPORT,4,context4id), this, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                        }
                    }, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            if (e.networkResponse != null) {
                                if (e.networkResponse.statusCode == 200) {
                                } else {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            } else {
                            }
                        }
                    });
                }
                Intent intent4 = new Intent();
                intent4.putExtra("startDate",quaStartTime);
                intent4.putExtra("endDate",quaEndTime);
                intent4.setClass(getContext(),QuaReportDetailsActivity.class);
                startActivityForResult(intent4,10003);
                break;   //季度报告
            case R.id.content5:
                if (news_5.getVisibility() == View.VISIBLE) {
                    RequestManager.postObject(String.format(Constens.SETTING_REPORT, 3, mainReportId), this, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                        }
                    }, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            if (e.networkResponse != null) {
                                if (e.networkResponse.statusCode == 200) {
                                } else {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            } else {
                            }
                        }
                    });
                }
                Intent intent5 = new Intent(this, ReportMainActivity.class);
                intent5.putExtra("reportId", mainReportId);
                startActivityForResult(intent5, 10003);
                break;   //主诊报告

        }
    }

    private void getNews() {
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_NEWS_REPORT),
                this, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        L.e(data.toString());
                        try {
                            new_report_time.setText(data.getString("nextReport"));
                            isTop = data.getBoolean("topThree");
                            if (data.has("riskReportJson")) {
                                try {
                                    hasReport(1);
                                    context1 = data.getJSONObject("riskReportJson");
                                    time1.setText(context1.getString("createTime").substring(0, 10));
                                    context1id = context1.getInt("id");
                                    item = new HashMap<String, Object>();
                                    JSONObject jsonObject = context1;

                                    item.put("id", jsonObject.getString("id"));
                                    item.put("createTime", jsonObject.getString("createTime"));
                                    item.put("doc_name", jsonObject.getString("doc_name"));
                                    item.put("checked", jsonObject.getString("checked"));
                                    if (!jsonObject.getBoolean("checked")) {
                                        hasNewReport(1);
                                    }
                                    if (jsonObject.has("percentageASVCD")) {
                                        item.put("percentageASVCD", jsonObject.getString("percentageASVCD"));
                                    } else {
                                        item.put("percentageASVCD", "");
                                    }
                                    if (jsonObject.has("percentageICVD")) {
                                        item.put("percentageICVD", jsonObject.getString("percentageICVD"));
                                    } else {
                                        item.put("percentageICVD", "");
                                    }
                                    if (jsonObject.has("bloodPressureConditions")) {
                                        item.put("bloodPressureConditions", jsonObject.getString("bloodPressureConditions"));
                                    } else {
                                        item.put("bloodPressureConditions", "");
                                    }
                                    if (jsonObject.has("bloodSugarConditions")) {
                                        item.put("bloodSugarConditions", jsonObject.getString("bloodSugarConditions"));
                                    } else {
                                        item.put("bloodSugarConditions", "");
                                    }
                                    if (jsonObject.has("healthIndicator")) {
                                        item.put("healthIndicator", jsonObject.getString("healthIndicator"));
                                    } else {
                                        item.put("healthIndicator", "");
                                    }
                                    if (jsonObject.has("advice")) {
                                        item.put("advice", jsonObject.getString("advice"));
                                    } else {
                                        item.put("advice", "");
                                    }
                                    if (jsonObject.has("content")) {
                                        item.put("content", jsonObject.getString("content"));
                                    } else {
                                        item.put("content", "");
                                    }
                                    if (jsonObject.has("percentage")) {
                                        item.put("percentage", jsonObject.getString("percentage"));
                                    } else {
                                        item.put("percentage", "");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (!isTop) {
                                if (data.has("healthManagementJson")) {
                                    try {
                                        hasReport(2);
                                        context2 = data.getJSONObject("healthManagementJson");

                                        item2 = new HashMap<String, Object>();
                                        JSONObject jsonObject = context2;
                                        item2.put("customerId", jsonObject.getString("customerId"));
                                        item2.put("customerName", jsonObject.getString("customerName"));
                                        item2.put("doctorId", jsonObject.getString("doctorId"));
                                        item2.put("doctorName", jsonObject.getString("doctorName"));
                                        item2.put("attendingDoctor", jsonObject.getString("attendingDoctor"));
                                        item2.put("checked", jsonObject.getString("checked"));
                                        if (!jsonObject.getBoolean("checked")) {
                                            hasNewReport(2);
                                        }
                                        if (jsonObject.has("personalHealthManagementTemplateJson")) {
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("id")) {
                                                item2.put("id", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("id"));
                                                context2id = jsonObject.getJSONObject("personalHealthManagementTemplateJson").getInt("id");
                                            } else {
                                                item2.put("id", 0);
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("bloodPressureManagementAdvice")) {
                                                item2.put("bloodPressureManagementAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("bloodPressureManagementAdvice"));
                                            } else {
                                                item2.put("bloodPressureManagementAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("bloodSugarManagementAdvice")) {
                                                item2.put("bloodSugarManagementAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("bloodSugarManagementAdvice"));
                                            } else {
                                                item2.put("bloodSugarManagementAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("resultsAndSuggestions")) {
                                                item2.put("resultsAndSuggestions", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("resultsAndSuggestions"));
                                            } else {
                                                item2.put("resultsAndSuggestions", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("medicalAdvice")) {
                                                item2.put("medicalAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("medicalAdvice"));
                                            } else {
                                                item2.put("medicalAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("dietaryAdviceToRemindAndTaboos")) {
                                                item2.put("dietaryAdviceToRemindAndTaboos", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("dietaryAdviceToRemindAndTaboos"));
                                            } else {
                                                item2.put("dietaryAdviceToRemindAndTaboos", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("exerciseAdvice")) {
                                                item2.put("exerciseAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("exerciseAdvice"));
                                            } else {
                                                item2.put("exerciseAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("personalHabitsSuggest")) {
                                                item2.put("personalHabitsSuggest", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("personalHabitsSuggest"));
                                            } else {
                                                item2.put("personalHabitsSuggest", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("updateTime")) {
                                                item2.put("updateTime", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("updateTime"));
                                                time2.setText(jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("updateTime").toString().substring(0, 10));
                                            } else {
                                                item2.put("updateTime", "");
                                            }

                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("verifyStatus")) {
                                                item2.put("verifyStatus", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("verifyStatus"));
                                            } else {
                                                item2.put("verifyStatus", "");
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                if (data.has("topThreeJson")) {
                                    try {
                                        hasReport(2);
                                        context2id = data.getJSONObject("topThreeJson").getInt("id");
                                        if(!data.getJSONObject("topThreeJson").getBoolean("read")){
                                            hasNewReport(2);
                                        }
//                                            hasNewReport(2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                    }
                            }
                            if (data.has("monthReport")) {
                                hasReport(3);
                                context3 = data.getJSONObject("monthReport");
                                monthStartTime = data.getJSONObject("monthReport").getString("startTime");
                                monthEndTime = data.getJSONObject("monthReport").getString("endTime");
                                if (!context3.getBoolean("checked")) {
                                    hasNewReport(3);
                                }
                                time3.setText(context3.getString("endTime").substring(0, 10));
                            }
                            if (data.has("seasonReport")) {
                                hasReport(4);
                                context4 = data.getJSONObject("seasonReport");
                                quaStartTime = data.getJSONObject("seasonReport").getString("startTime");
                                quaEndTime = data.getJSONObject("seasonReport").getString("endTime");
                                context4id = data.getJSONObject("seasonReport").getInt("id");
                                if (!context4.getBoolean("checked")) {
                                    hasNewReport(4);
                                }
                                time4.setText(context4.getString("endTime").substring(0, 10));
                            }
                            if (data.has("diagnosisReportJson")) {
                                hasReport(5);
                                context5 = data.getJSONObject("diagnosisReportJson");
                                if (!context5.getBoolean("checked")) {
                                    hasNewReport(5);
                                }
                                time5.setText(context5.getString("createTime").substring(0, 10));
                                mainReportId = context5.getInt("id");
                            }

                            fm = getSupportFragmentManager();
                            fragments = new ArrayList<>();
                            fragments.add(assessmentFragment);
                            if (isTop) {
                                fragments.add(threeTopFragment);
                            } else {
                                fragments.add(personalProgramFragment);
                            }
                            fragments.add(monthReportFragment);
                            fragments.add(quaReportFragment);
                            fragments.add(mainReportFragment);
                            onItemsClick(findViewById(R.id.fxpg));
                        } catch (JSONException e) {
                            fragments.add(assessmentFragment);
                            fragments.add(personalProgramFragment);
                            fragments.add(monthReportFragment);
                            fragments.add(quaReportFragment);
                            fragments.add(mainReportFragment);
                            onItemsClick(findViewById(R.id.fxpg));
                            e.printStackTrace();
                        }


                    }
                });
    }
    private void getNews1() {
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_NEWS_REPORT),
                this, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        L.e(data.toString());
                        try {
                            new_report_time.setText(data.getString("nextReport"));
                            isTop = data.getBoolean("topThree");
                            if (data.has("riskReportJson")) {
                                try {
                                    hasReport(1);
                                    context1 = data.getJSONObject("riskReportJson");
                                    time1.setText(context1.getString("createTime").substring(0, 10));
                                    context1id = context1.getInt("id");
                                    item = new HashMap<String, Object>();
                                    JSONObject jsonObject = context1;

                                    item.put("id", jsonObject.getString("id"));
                                    item.put("createTime", jsonObject.getString("createTime"));
                                    item.put("doc_name", jsonObject.getString("doc_name"));
                                    item.put("checked", jsonObject.getString("checked"));
                                    if (!jsonObject.getBoolean("checked")) {
                                        hasNewReport(1);
                                    }else{
                                        news_1.setVisibility(View.GONE);
                                        if( image1_bag.getAnimation() != null)
                                            image1_bag.clearAnimation();
                                    }
                                    if (jsonObject.has("percentageASVCD")) {
                                        item.put("percentageASVCD", jsonObject.getString("percentageASVCD"));
                                    } else {
                                        item.put("percentageASVCD", "");
                                    }
                                    if (jsonObject.has("percentageICVD")) {
                                        item.put("percentageICVD", jsonObject.getString("percentageICVD"));
                                    } else {
                                        item.put("percentageICVD", "");
                                    }
                                    if (jsonObject.has("bloodPressureConditions")) {
                                        item.put("bloodPressureConditions", jsonObject.getString("bloodPressureConditions"));
                                    } else {
                                        item.put("bloodPressureConditions", "");
                                    }
                                    if (jsonObject.has("bloodSugarConditions")) {
                                        item.put("bloodSugarConditions", jsonObject.getString("bloodSugarConditions"));
                                    } else {
                                        item.put("bloodSugarConditions", "");
                                    }
                                    if (jsonObject.has("healthIndicator")) {
                                        item.put("healthIndicator", jsonObject.getString("healthIndicator"));
                                    } else {
                                        item.put("healthIndicator", "");
                                    }
                                    if (jsonObject.has("advice")) {
                                        item.put("advice", jsonObject.getString("advice"));
                                    } else {
                                        item.put("advice", "");
                                    }
                                    if (jsonObject.has("content")) {
                                        item.put("content", jsonObject.getString("content"));
                                    } else {
                                        item.put("content", "");
                                    }
                                    if (jsonObject.has("percentage")) {
                                        item.put("percentage", jsonObject.getString("percentage"));
                                    } else {
                                        item.put("percentage", "");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (!isTop) {
                                if (data.has("healthManagementJson")) {
                                    try {
                                        hasReport(2);
                                        context2 = data.getJSONObject("healthManagementJson");

                                        item2 = new HashMap<String, Object>();
                                        JSONObject jsonObject = context2;
                                        item2.put("customerId", jsonObject.getString("customerId"));
                                        item2.put("customerName", jsonObject.getString("customerName"));
                                        item2.put("doctorId", jsonObject.getString("doctorId"));
                                        item2.put("doctorName", jsonObject.getString("doctorName"));
                                        item2.put("attendingDoctor", jsonObject.getString("attendingDoctor"));
                                        item2.put("checked", jsonObject.getString("checked"));
                                        if (!jsonObject.getBoolean("checked")) {
                                            hasNewReport(2);
                                        }else{
                                            news_2.setVisibility(View.GONE);
                                            if( image2_bag.getAnimation() != null)
                                                image2_bag.clearAnimation();
                                        }
                                        if (jsonObject.has("personalHealthManagementTemplateJson")) {
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("id")) {
                                                item2.put("id", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("id"));
                                                context2id = jsonObject.getJSONObject("personalHealthManagementTemplateJson").getInt("id");
                                            } else {
                                                item2.put("id", 0);
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("bloodPressureManagementAdvice")) {
                                                item2.put("bloodPressureManagementAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("bloodPressureManagementAdvice"));
                                            } else {
                                                item2.put("bloodPressureManagementAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("bloodSugarManagementAdvice")) {
                                                item2.put("bloodSugarManagementAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("bloodSugarManagementAdvice"));
                                            } else {
                                                item2.put("bloodSugarManagementAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("resultsAndSuggestions")) {
                                                item2.put("resultsAndSuggestions", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("resultsAndSuggestions"));
                                            } else {
                                                item2.put("resultsAndSuggestions", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("medicalAdvice")) {
                                                item2.put("medicalAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("medicalAdvice"));
                                            } else {
                                                item2.put("medicalAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("dietaryAdviceToRemindAndTaboos")) {
                                                item2.put("dietaryAdviceToRemindAndTaboos", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("dietaryAdviceToRemindAndTaboos"));
                                            } else {
                                                item2.put("dietaryAdviceToRemindAndTaboos", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("exerciseAdvice")) {
                                                item2.put("exerciseAdvice", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("exerciseAdvice"));
                                            } else {
                                                item2.put("exerciseAdvice", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("personalHabitsSuggest")) {
                                                item2.put("personalHabitsSuggest", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("personalHabitsSuggest"));
                                            } else {
                                                item2.put("personalHabitsSuggest", "");
                                            }
                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("updateTime")) {
                                                item2.put("updateTime", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("updateTime"));
                                                time2.setText(jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("updateTime").toString().substring(0, 10));
                                            } else {
                                                item2.put("updateTime", "");
                                            }

                                            if (jsonObject.getJSONObject("personalHealthManagementTemplateJson").has("verifyStatus")) {
                                                item2.put("verifyStatus", jsonObject.getJSONObject("personalHealthManagementTemplateJson").getString("verifyStatus"));
                                            } else {
                                                item2.put("verifyStatus", "");
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                if (data.has("topThreeJson")) {
                                    try {
                                        hasReport(2);
                                        context2id = data.getJSONObject("topThreeJson").getInt("id");
                                        if(!data.getJSONObject("topThreeJson").getBoolean("read")){
                                            hasNewReport(2);
                                        }else{
                                            news_2.setVisibility(View.GONE);
                                            if( image2_bag.getAnimation() != null)
                                                image2_bag.clearAnimation();
                                        }
//                                            hasNewReport(2);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (data.has("monthReport")) {
                                hasReport(3);
                                context3 = data.getJSONObject("monthReport");
                                monthStartTime = data.getJSONObject("monthReport").getString("startTime");
                                monthEndTime = data.getJSONObject("monthReport").getString("endTime");
                                if (!context3.getBoolean("checked")) {
                                    hasNewReport(3);
                                }else{
                                    news_3.setVisibility(View.GONE);
                                    if( image3_bag.getAnimation() != null)
                                        image3_bag.clearAnimation();
                                }
                                time3.setText(context3.getString("endTime").substring(0, 10));
                            }
                            if (data.has("seasonReport")) {
                                hasReport(4);
                                context4 = data.getJSONObject("seasonReport");
                                quaStartTime = data.getJSONObject("seasonReport").getString("startTime");
                                quaEndTime = data.getJSONObject("seasonReport").getString("endTime");
                                context4id = data.getJSONObject("seasonReport").getInt("id");
                                if (!context4.getBoolean("checked")) {
                                    hasNewReport(4);
                                }else{
                                    news_4.setVisibility(View.GONE);
                                    if( image4_bag.getAnimation() != null)
                                        image4_bag.clearAnimation();
                                }
                                time4.setText(context4.getString("endTime").substring(0, 10));
                            }
                            if (data.has("diagnosisReportJson")) {
                                hasReport(5);
                                context5 = data.getJSONObject("diagnosisReportJson");
                                if (!context5.getBoolean("checked")) {
                                    hasNewReport(5);
                                }else{
                                    news_5.setVisibility(View.GONE);
                                    if( image5_bag.getAnimation() != null)
                                        image5_bag.clearAnimation();
                                }
                                time5.setText(context5.getString("createTime").substring(0, 10));
                                mainReportId = context5.getInt("id");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }
    @Override
    protected void onInitLayoutAfter() {
        choseType = 1;
        initNewView();//初始化最新界面
        chose(1);
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.big_and_alpha);
        getNews();//获取最新报告
    }

    @OnClick({
            R.id.tab_1,  //问健管师
            R.id.tab_2,  //问医生
            R.id.back_to_tab_1,//返回
            R.id.title_messge//messge
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tab_1:
                chose(1);
                getNews1();//获取最新报告
                break;
            case R.id.tab_2:
                chose(2);
                break;
            case R.id.back_to_tab_1:
                finish();
                break;
        }
    }

    private void chose(int i) {
        choseType = i;
        if (i == 1) {
            tab_line_1.setVisibility(View.VISIBLE);
            new_scroll.setVisibility(View.VISIBLE);
            old_scroll.setVisibility(View.GONE);
            tab_line_2.setVisibility(View.INVISIBLE);
            tab_1.setTextColor(getResources().getColor(R.color.tet_type_3));
            tab_2.setTextColor(getResources().getColor(R.color.tet_type_9));

        } else {
            tab_line_2.setVisibility(View.VISIBLE);
            new_scroll.setVisibility(View.GONE);
            old_scroll.setVisibility(View.VISIBLE);
            tab_line_1.setVisibility(View.INVISIBLE);
            tab_1.setTextColor(getResources().getColor(R.color.tet_type_9));
            tab_2.setTextColor(getResources().getColor(R.color.tet_type_3));
        }
    }

    private void hasNewReport(int num) {//设置（最新）界面

        switch (num) {
            case 1:
                news_1.setVisibility(View.VISIBLE);
                image1_bag.startAnimation(hyperspaceJumpAnimation);
                break;
            case 2:
                news_2.setVisibility(View.VISIBLE);
                image2_bag.startAnimation(hyperspaceJumpAnimation);
                break;
            case 3:
                news_3.setVisibility(View.VISIBLE);
                image3_bag.startAnimation(hyperspaceJumpAnimation);
                break;
            case 4:
                news_4.setVisibility(View.VISIBLE);
                image4_bag.startAnimation(hyperspaceJumpAnimation);
                break;
            case 5:
                news_5.setVisibility(View.VISIBLE);
                image5_bag.startAnimation(hyperspaceJumpAnimation);
                break;
        }
    }

    private void hasReport(int num) {//设置（最新）界面

        switch (num) {
            case 1:
                text1.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_3));
                time1.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_6));
                image1.setBackgroundResource(R.drawable.risk_assessment2x);
                content1.setClickable(true);
                break;
            case 2:
                text2.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_3));
                time2.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_6));
                image2.setBackgroundResource(R.drawable.personal_plan2x);
                content2.setClickable(true);
                break;
            case 3:
                text3.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_3));
                time3.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_6));
                image3.setBackgroundResource(R.drawable.monthly_report2x);
                content3.setClickable(true);
                break;
            case 4:
                text4.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_3));
                time4.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_6));
                image4.setBackgroundResource(R.drawable.quarterly_report2x);
                content4.setClickable(true);
                break;
            case 5:
                text5.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_3));
                time5.setTextColor(ContextCompat.getColor(activity, R.color.tet_type_6));
                image5.setBackgroundResource(R.drawable.main_report2x);
                content5.setClickable(true);
                break;
        }
    }

    private void initNewView() {//初始化（最新）界面
        content1.setClickable(false);
        content2.setClickable(false);
        content3.setClickable(false);
        content4.setClickable(false);
        content5.setClickable(false);
        news_1.setVisibility(View.GONE);
        text1.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        time1.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        image1.setBackgroundResource(R.drawable.risk_assessment_grey2x);
        news_2.setVisibility(View.GONE);
        text2.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        time2.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        image2.setBackgroundResource(R.drawable.personal_plan_grey2x);
        news_3.setVisibility(View.GONE);
        text3.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        time3.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        image3.setBackgroundResource(R.drawable.monthly_report_grey2x);
        news_4.setVisibility(View.GONE);
        text4.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        time4.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        image4.setBackgroundResource(R.drawable.quarterly_report_grey2x);
        news_5.setVisibility(View.GONE);
        text5.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        time5.setTextColor(ContextCompat.getColor(activity, R.color.gray_background));
        image5.setBackgroundResource(R.drawable.main_report_grey2x);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10003) {
            getNews1();//获取最新报告
        }


    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }
}
