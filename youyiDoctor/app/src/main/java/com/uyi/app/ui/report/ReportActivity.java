package com.uyi.app.ui.report;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.FragmentHealthListManager;
import com.uyi.app.ui.report.model.Report;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 主诊报告 Created by Leeii on 2016/6/22.
 */
@ContentView(R.layout.activity_report)
public class ReportActivity extends BaseFragmentActivity {
    private List<Fragment> fragments;

    private Fragment currentFragment;
    @ViewInject(R.id.jkbg)
    private View jkbg;
    @ViewInject(R.id.header)
    private HeaderView headerView;
    @ViewInject(R.id.write_report)
    private Button write_report;
    private Report mReport;

    private View currentView;


    private int reportId;

    @OnClick({
            R.id.cgjc,      //常规监测
            R.id.qst,       //趋势图
            R.id.xdt,       //心电图
            R.id.zytzjb,    //中医体质鉴别
            R.id.jkbg,   //健康报告
            R.id.write_report       //健康报告
    })
    public void onClick(View view) {
        if (view.getId() == R.id.write_report) {
            startActivityForResult(new Intent(this, WriteReportActivity.class), 0x100);
            return;
        }
        if (currentView != null) {
            currentView.setBackgroundColor(getResources().getColor(R.color.white));
            ((CheckedTextView) ((FrameLayout) currentView).getChildAt(0)).setChecked(false);
        }
        view.setBackgroundColor(getResources().getColor(R.color.blue));
        ((CheckedTextView) ((FrameLayout) view).getChildAt(0)).setChecked(true);
        switch (view.getId()) {
            case R.id.cgjc:
                changeFragment(0);
                break;  //常规监测
            case R.id.qst:
                changeFragment(1);
                break;  //趋势图
            case R.id.xdt:
                changeFragment(2);
                break;  //心电图
            case R.id.zytzjb:
                changeFragment(3);
                break;  //中医体质鉴别
            case R.id.jkbg:
                changeFragment(4);
                break;  //健康报告
        }
        currentView = view;
    }

    @Override
    protected void onInitLayoutAfter() {
        reportId = getIntent().getIntExtra("reportId", 0);
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("详细报告").setTitleColor(getResources().getColor(R.color.blue));
        fragments = new ArrayList<>();
        fragments.add(new RoutineFragment());
        fragments.add(new TrendMapFragment());
        fragments.add(new ECGFragment());
        fragments.add(new PhysiqueFragment());
        fragments.add(new HealthReportFragment());


//        mReport = JSON.parseObject("{\"bmi\":[],\"bloodFatChol\":[{\"uptime\":\"2016-06-17\",\"itemValue\":\"7.0\"}],\"bloodFatTg\":[{\"uptime\":\"2016-06-17\",\"itemValue\":\"8.0\"}],\"bloodFatLdl\":[{\"uptime\":\"2016-06-17\",\"itemValue\":\"9.0\"}],\"bloodFatHdl\":[{\"uptime\":\"2016-06-17\",\"itemValue\":\"10.0\"}],\"spo\":[{\"uptime\":\"2016-06-17\",\"itemValue\":\"11\"}],\"urineAcid\":[{\"uptime\":\"2016-04-08\",\"itemValue\":\"1.0\"},{\"uptime\":\"2016-04-15\",\"itemValue\":\"1.0\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"2.0\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"111.0\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"333.0\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"222.0\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"13.0\"}],\"morningSystolicPressure\":[{\"uptime\":\"2016-04-20\",\"itemValue\":\"140\"},{\"uptime\":\"2016-04-28\",\"itemValue\":\"1\"},{\"uptime\":\"2016-04-28\",\"itemValue\":\"111\"},{\"uptime\":\"2016-05-09\",\"itemValue\":\"11\"},{\"uptime\":\"2016-05-09\",\"itemValue\":\"111\"},{\"uptime\":\"2016-05-09\",\"itemValue\":\"130\"},{\"uptime\":\"2016-05-09\",\"itemValue\":\"112\"},{\"uptime\":\"2016-05-09\",\"itemValue\":\"90\"},{\"uptime\":\"2016-05-10\",\"itemValue\":\"123\"},{\"uptime\":\"2016-05-10\",\"itemValue\":\"333\"},{\"uptime\":\"2016-05-10\",\"itemValue\":\"555\"},{\"uptime\":\"2016-05-11\",\"itemValue\":\"9999\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"54321\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"22\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"54321\"},{\"uptime\":\"2016-05-18\",\"itemValue\":\"132\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"1\"}],\"morningDiastolicPressure\":[{\"uptime\":\"2016-04-20\",\"itemValue\":\"79\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"1366\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"1366\"},{\"uptime\":\"2016-05-18\",\"itemValue\":\"92\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"3\"}],\"fastBloodSugar\":[{\"uptime\":\"2016-04-28\",\"itemValue\":\"1.0\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"9311.6\"},{\"uptime\":\"2016-05-13\",\"itemValue\":\"9311.6\"},{\"uptime\":\"2016-05-18\",\"itemValue\":\"6.9\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"4.0\"}],\"postPrandilaSugar\":[{\"uptime\":\"2016-06-17\",\"itemValue\":\"5.0\"}],\"electrocardiogram\":[{\"uptime\":\"2016-04-15\",\"itemValue\":\"http://121.42.142.228:8080/pics/business/images/business/2016/04/582af901-02c8-46bd-ad67-b110ee66170e.jpeg\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"http://121.42.142.228:8080/pics/business/images/business/2016/06/95f2212e-40d6-42d6-9938-d052c1f342d6.jpeg\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"http://121.42.142.228:8080/pics/business/images/business/2016/06/f02d290b-753f-4702-b4c5-1d61ef1f5f05.jpeg\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"http://121.42.142.228:8080/pics/business/images/business/2016/06/d41e7400-a54f-4acd-8fab-d51835347a5d.jpeg\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"http://121.42.142.228:8080/pics/business/images/business/2016/06/35e5d9c6-8dea-4550-afe6-3e9d699e7d90.jpeg\"},{\"uptime\":\"2016-06-17\",\"itemValue\":\"http://121.42.142.228:8080/pics/business/images/business/2016/06/70fd4ebc-9be6-4cf0-b390-d4a8178261c9.jpeg\"}],\"morningDiastolicPressure_pic\":\"http://localhost:8080/pics/business/images/business/2016/07/134428e9-86c4-4eea-9106-fc4e3f633a34.jpg\",\"morningSystolicPressure_pic\":\"http://localhost:8080/pics/business/images/business/2016/07/73918fab-e239-4cc2-b299-68d92a06d3e8.jpg\",\"fastBloodSugar_pic\":\"http://localhost:8080/pics/business/images/business/2016/07/c62424cf-961c-4686-bd21-9c21dfb79e9d.jpg\",\"postPrandilaSugar_pic\":\"http://localhost:8080/pics/business/images/business/2016/07/810b0fb1-ac58-4624-b150-640b4f8b2970.jpg\",\"comment1\":\"您的血压整体情况符合勺型曲线规律，即：晨起血压水平较高，夜晚睡眠时血压水平较低，在清晨开始上升， 点左右出现高峰，然后逐渐平稳，晚餐前再次出现高峰（次高峰），然后缓慢下降，凌晨睡梦中达低谷并维持到次日清晨，全天出现双峰一谷的长柄勺型曲线。这种节律变化对适应机体活动、保护心血管结构和功能有重要意义。\",\"comment2\":\"在本月内的监测记录中，您多次在出现收缩压偏高，最高时     （请您根据该患者情况给出时间段，比如：晨起、午后、晚餐后、睡前）收缩压曾达到dfdfdmmHg，而理想的舒张压应控制在dfdfdfmmHg以下。根据您目前所服用的降压药物dfdfdf，考虑出现这种情况后需调整降压药物的方案，请联系您所在医疗团队的慢病管理师为您预约专家面诊。\",\"comment3\":\"您在本月上传了体检报告，您目前主要存在的问题有：aadsf，提示了您多种代谢指标的紊乱，而这在一定程度上反应出您欠佳规范的饮食和良好的运动习惯。针对体检发现的异常情况，需要心血管内科、内分泌科医生共同为您制定进一步的生活、饮食、药物干预方案。\",\"comment4\":\"dfdfd目前尚无根治方法，但长期以来的临床实践证实，sdfsdf的良好控制可以有效减少心脑血管病发病率和死亡率，您进行这些指标监测和治疗的最终目的也是维护自身健康，减少发病可能在改善生活方式上，我们建议您\\n①减轻体重。BMI控制在25以内。\\n②减少钠盐摄入，每日＜6g为宜。\\n③补充钙和钾盐，每天日吃新鲜蔬菜500g，左右，适量饮用牛奶\\n④减少脂肪摄入 ⑤戒烟限酒，每日白酒不超过100ml，红酒不超过200ml，尽量避免饮用啤酒及高度白酒  \\n⑥增加运动，运动有利于减轻体重和改善胰岛素抵抗，提高心血管适应调节能力，稳定血压水平，较好的运动方式是低或中等强度的慢跑、步行或游泳，一般每周3~5次，每次20~60分钟。鉴于您所提供的体检报告针对高血压并发症的筛查还有所欠缺，我们建议您择期再复查sdfsdfdf，这些检查项目有助于发现心脑血管相关的危险因素和靶器官损害，在您完成体检后，请及时将报告上传至APP，我们的医生团队也会根据您自身情况做出对比和解读。\"}", Report.class);
        Loading.bulid(ReportActivity.this, null).show();
        requestReportDetail();
    }

    private void requestReportDetail() {
//        int userId = UserInfoManager.getLoginUserInfo(this).userId;
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_REPORT_DETAIL, FragmentHealthListManager.customer, FragmentHealthListManager.customer, reportId), this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Loading.bulid(ReportActivity.this, null).dismiss();
                mReport = JSON.parseObject(jsonObject.toString(), Report.class);
                onClick(findViewById(R.id.cgjc));
                if (TextUtils.isEmpty(mReport.getComment1())) {
                    write_report.setVisibility(View.VISIBLE);
                } else {
                    write_report.setVisibility(View.GONE);
                    jkbg.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    private void changeFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fragments.get(position);
        if (!f.isAdded()) {
            ft.add(R.id.content, f);
            if (currentFragment != null) {
                ft.hide(currentFragment);
            }
            currentFragment = f;
            ft.show(f);
        } else {
            if (f != currentFragment) {
                ft.hide(currentFragment);
                currentFragment = f;
                ft.show(f);
            }
        }
        ft.commit();
    }

    public Report getReport() {
        return mReport;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x100 && resultCode == 0x200) {
            jkbg.setVisibility(View.VISIBLE);
            String comment1 = data.getStringExtra("comment1");
            String comment2 = data.getStringExtra("comment2");
            String comment3 = data.getStringExtra("comment3");
            String comment4 = data.getStringExtra("comment4");

            mReport.setComment1(comment1);
            mReport.setComment2(comment2);
            mReport.setComment3(comment3);
            mReport.setComment4(comment4);

            onClick(jkbg);

            write_report.setVisibility(View.GONE);
        }
    }
}
