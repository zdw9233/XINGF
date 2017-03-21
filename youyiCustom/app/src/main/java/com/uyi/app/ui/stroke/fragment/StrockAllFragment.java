package com.uyi.app.ui.stroke.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.stroke.StrokeFywActivity;
import com.uyi.app.ui.stroke.StrokeJkjyActivity;
import com.uyi.app.ui.stroke.StrokeXgcsjcActivity;
import com.uyi.app.ui.stroke.StrokeYwzlActivity;
import com.uyi.app.ui.stroke.StrokeZxjkzkActivity;
import com.uyi.app.ui.stroke.StrokeZzjszzActivity;
import com.uyi.app.ui.stroke.StrokeZzyfbnbgActivity;
import com.uyi.app.ui.stroke.model.StrokeFollowUpRecordJson;
import com.uyi.app.ui.stroke.view.ObservableScrollView;
import com.uyi.app.ui.stroke.view.ScrollViewListener;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 *
 * 卒中汇总
 */
@ContentView( R.layout.fragment_stroke_all)
public class StrockAllFragment extends BaseActivity implements ScrollViewListener{
    @ViewInject(R.id.headerView) private HeaderView headerView;
    private   String id = "0";//随访ID
private String json = "";


    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("卒中随访汇总").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        id = getIntent().getStringExtra("id");
        scoll1.setScrollViewListener(this);
        scoll2.setScrollViewListener(this);
        getData();
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        if (scrollView == scoll1) {
            scoll2.scrollTo(x, y);
        } else if (scrollView == scoll2) {
            scoll1.scrollTo(x, y);
        }
    }


    private void getData() {
        Loading.bulid(activity, null).show();
//        System.out.println(UserInfoManager.getLoginUserInfo(activity).toString());
        RequestManager.getArray(String.format(Constens.DOCTOR_HEALTH_STROKE_DETAILS_ALL, id),
                activity, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray data) {
                        Loading.bulid(activity, null).dismiss();
                        json = data.toString();
                        L.e("Fuck=="+json);
                        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(data.toString(), StrokeFollowUpRecordJson.class);
                        try {
                            L.e("DOCTOR_HEALTH_STROKE_DETAILS_ALL == " + data.getJSONObject(0).toString());
                            title1.setText(reportItems.get(0).getFollowUpDate());
                            title2.setText(reportItems.get(1).getFollowUpDate());
                            title3.setText(reportItems.get(2).getFollowUpDate());
                            title4.setText(reportItems.get(3).getFollowUpDate());
                            title5.setText(reportItems.get(4).getFollowUpDate());
                            title6.setText(reportItems.get(5).getFollowUpDate());

                            ssy1.setText(intToString(reportItems.get(0).getSbp()+""));
                            ssy2.setText(intToString(reportItems.get(1).getSbp()+""));
                            ssy3.setText(intToString(reportItems.get(2).getSbp()+""));
                            ssy4.setText(intToString(reportItems.get(3).getSbp()+""));
                            ssy5.setText(intToString(reportItems.get(4).getSbp()+""));
                            ssy6.setText(intToString(reportItems.get(5).getSbp()+""));

                            szy1.setText(intToString(reportItems.get(0).getDbp()+""));
                            szy2.setText(intToString(reportItems.get(1).getDbp()+""));
                            szy3.setText(intToString(reportItems.get(2).getDbp()+""));
                            szy4.setText(intToString(reportItems.get(3).getDbp()+""));
                            szy5.setText(intToString(reportItems.get(4).getDbp()+""));
                            szy6.setText(intToString(reportItems.get(5).getDbp()+""));

                            kfxt1.setText(intToString(reportItems.get(0).getFbg()+""));
                            kfxt2.setText(intToString(reportItems.get(1).getFbg()+""));
                            kfxt3.setText(intToString(reportItems.get(2).getFbg()+""));
                            kfxt4.setText(intToString(reportItems.get(3).getFbg()+""));
                            kfxt5.setText(intToString(reportItems.get(4).getFbg()+""));
                            kfxt6.setText(intToString(reportItems.get(5).getFbg()+""));

                            chxt1.setText(intToString(reportItems.get(0).getPbg()+""));
                            chxt2.setText(intToString(reportItems.get(1).getPbg()+""));
                            chxt3.setText(intToString(reportItems.get(2).getPbg()+""));
                            chxt4.setText(intToString(reportItems.get(3).getPbg()+""));
                            chxt5.setText(intToString(reportItems.get(4).getPbg()+""));
                            chxt6.setText(intToString(reportItems.get(5).getPbg()+""));

                            fckljc1.setText(intToString(reportItems.get(0).getInr()+""));
                            fckljc2.setText(intToString(reportItems.get(1).getInr()+""));
                            fckljc3.setText(intToString(reportItems.get(2).getInr()+""));
                            fckljc4.setText(intToString(reportItems.get(3).getInr()+""));
                            fckljc5.setText(intToString(reportItems.get(4).getInr()+""));
                            fckljc6.setText(intToString(reportItems.get(5).getInr()+""));

                            if(reportItems.get(0).getDrugTreatments().size() != 0){
                                ywzlqk1.setText("详情");
                            }else {
                                ywzlqk1.setText("");
                            }
                            if(reportItems.get(1).getDrugTreatments().size() != 0){
                                ywzlqk2.setText("详情");
                            }else {
                                ywzlqk2.setText("");
                            }
                            if(reportItems.get(2).getDrugTreatments().size() != 0){
                                ywzlqk3.setText("详情");
                            }else {
                                ywzlqk3.setText("");
                            }
                            if(reportItems.get(3).getDrugTreatments().size() != 0){
                                ywzlqk4.setText("详情");
                            }else {
                                ywzlqk4.setText("");
                            }
                            if(reportItems.get(4).getDrugTreatments().size() != 0){
                                ywzlqk5.setText("详情");
                            }else {
                                ywzlqk5.setText("");
                            }
                            if(reportItems.get(5).getDrugTreatments().size() != 0){
                                ywzlqk6.setText("详情");
                            }else {
                                ywzlqk6.setText("");
                            }

                            if(reportItems.get(0).getStrokeWarningSymptoms() != null){
                                zzjszz1.setText("详情");
                            }else {
                                zzjszz1.setText("");
                            }
                            if(reportItems.get(1).getStrokeWarningSymptoms() != null){
                                zzjszz2.setText("详情");
                            }else {
                                zzjszz2.setText("");
                            }
                            if(reportItems.get(2).getStrokeWarningSymptoms() != null){
                                zzjszz3.setText("详情");
                            }else {
                                zzjszz3.setText("");
                            }
                            if(reportItems.get(3).getStrokeWarningSymptoms() != null){
                                zzjszz4.setText("详情");
                            }else {
                                zzjszz4.setText("");
                            }
                            if(reportItems.get(4).getStrokeWarningSymptoms() != null){
                                zzjszz5.setText("详情");
                            }else {
                                zzjszz5.setText("");
                            }
                            if(reportItems.get(5).getStrokeWarningSymptoms() != null){
                                zzjszz6.setText("详情");
                            }else {
                                zzjszz6.setText("");
                            }

                            if(reportItems.get(0).getHealthEducation() != null){
                                jkjy1.setText("详情");
                            }else {
                                jkjy1.setText("");
                            }
                            if(reportItems.get(1).getHealthEducation() != null){
                                jkjy2.setText("详情");
                            }else {
                                jkjy2.setText("");
                            }
                            if(reportItems.get(2).getHealthEducation() != null){
                                jkjy3.setText("详情");
                            }else {
                                jkjy3.setText("");
                            }
                            if(reportItems.get(3).getHealthEducation() != null){
                                jkjy4.setText("详情");
                            }else {
                                jkjy4.setText("");
                            }
                            if(reportItems.get(4).getHealthEducation() != null){
                                jkjy5.setText("详情");
                            }else {
                                jkjy5.setText("");
                            }
                            if(reportItems.get(5).getHealthEducation() != null){
                                jkjy6.setText("详情");
                            }else {
                                jkjy6.setText("");
                            }

                            zdgc1.setText(intToString(reportItems.get(2).getChol()+""));
                            zdgc2.setText(intToString(reportItems.get(5).getChol()+""));

                            gysz1.setText(intToString(reportItems.get(2).getTg()+""));
                            gysz2.setText(intToString(reportItems.get(5).getTg()+""));

                            dmdzdbdgc1.setText(intToString(reportItems.get(2).getLdl()+""));
                            dmdzdbdgc2.setText(intToString(reportItems.get(5).getLdl()+""));

                            gmdzdbdgc1.setText(intToString(reportItems.get(2).getHdl()+""));
                            gmdzdbdgc2.setText(intToString(reportItems.get(5).getHdl()+""));

                            String str_sgtzsjjc1 = "";
                            if(intToString(reportItems.get(2).getHeight()+"").equals("")){
                                str_sgtzsjjc1 = "";
                            }else{
                                str_sgtzsjjc1 = "身高 ："+reportItems.get(2).getHeight() +"cm" + "\n";
                            }
                            if(intToString(reportItems.get(2).getWeight()+"").equals("")){
                                str_sgtzsjjc1 += "";
                            }else{
                                str_sgtzsjjc1 += "体重 ：" +reportItems.get(2).getWeight() +
                                        "kg" +"\n";
                            }
                            if(intToString(reportItems.get(2).getBmi()+"").equals("")){
                                str_sgtzsjjc1 += "";
                            }else{
                                str_sgtzsjjc1 += "BMI ："+reportItems.get(2).getBmi() + "kg/m²" +"\n";
                            }
                            if(intToString(reportItems.get(2).getWaist()+"").equals("")){
                                str_sgtzsjjc1 += "";
                            }else{
                                str_sgtzsjjc1 += "腰围 ："+reportItems.get(2).getWaist()+"cm";
                            }
                            sgtzsjjc1.setText(str_sgtzsjjc1);
                            String str_sgtzsjjc2 = "";
                            if(intToString(reportItems.get(5).getHeight()+"").equals("")){
                                str_sgtzsjjc2 = "";
                            }else{
                                str_sgtzsjjc2 = "身高 ："+reportItems.get(5).getHeight() +"cm" + "\n";
                            }
                            if(intToString(reportItems.get(5).getWeight()+"").equals("")){
                                str_sgtzsjjc2 += "";
                            }else{
                                str_sgtzsjjc2 += "体重 ：" +reportItems.get(5).getWeight() +
                                        "kg" +"\n";
                            }
                            if(intToString(reportItems.get(5).getBmi()+"").equals("")){
                                str_sgtzsjjc2 += "";
                            }else{
                                str_sgtzsjjc2 += "BMI ："+reportItems.get(5).getBmi() + "kg/m²" +"\n";
                            }
                            if(intToString(reportItems.get(5).getWaist()+"").equals("")){
                                str_sgtzsjjc2 += "";
                            }else{
                                str_sgtzsjjc2 += "腰围 ："+reportItems.get(5).getWaist()+"cm";
                            }
                            sgtzsjjc2.setText(str_sgtzsjjc2);

                            if(reportItems.get(2).getNonDrugTreatment() != null ){
                                fywzlqk1.setText("详情");
                                fywzlqk1.setClickable(true);
                            }else {
                                fywzlqk1.setText("");
                                fywzlqk1.setClickable(false);
                            }
                            if(reportItems.get(5).getNonDrugTreatment() != null ){
                                fywzlqk2.setText("详情");
                                fywzlqk2.setClickable(true);
                            }else {
                                fywzlqk2.setText("");
                                fywzlqk2.setClickable(false);
                            }

                            jsglcd1.setText(reportItems.get(2).getAcceptTheDegreeOfManagement());
                            jsglcd2.setText(reportItems.get(5).getAcceptTheDegreeOfManagement());

                           if(reportItems.get(5).getVascularUltrasounds().size() > 0){
                               xgcsjcjc.setText("详情");
                               xgcsjcjc.setClickable(true);
                           }else{
                               xgcsjcjc.setText("");
                               xgcsjcjc.setClickable(false);
                           }

                            if(reportItems.get(5).getHealthStatus() != null){
                                zxjkzk.setText("详情");
                                zxjkzk.setClickable(true);
                            }else{
                                zxjkzk.setText("");
                                zxjkzk.setClickable(false);
                            }

                            hzzzrzsp.setText(reportItems.get(5).getStrokePreventiveInterventionReport() == null ? "" : reportItems.get(5).getStrokePreventiveInterventionReport().getStrokeCognitiveLevel());
                            bnwxysglpj.setText(reportItems.get(5).getStrokePreventiveInterventionReport() == null ? "" : reportItems.get(5).getStrokePreventiveInterventionReport().getRiskFactorManagementEvaluation());
                            if(reportItems.get(5).getStrokePreventiveInterventionReport() != null ){
                                zzyfbnbg.setText("详情");
                                zzyfbnbg.setClickable(true);
                            }else {
                                zzyfbnbg.setText("");
                                zzyfbnbg.setClickable(false);
                            }
                            if(reportItems.get(5).getStrokePreventiveInterventionReport()!=null){
                                L.e( data.getJSONObject(5).getString("strokePreventiveInterventionReport"));
                                if((reportItems.get(5).getStrokePreventiveInterventionReport().getThinkStroke()+"").equals("高") || (reportItems.get(5).getStrokePreventiveInterventionReport().getThinkStroke()+"").equals("中")||(reportItems.get(5).getStrokePreventiveInterventionReport().getThinkStroke()+"").equals("低")){
//                                    if(reportItems.get(5).getStrokePreventiveInterventionReport().getThinkStroke()+"")
                                    hzrwzjsfzf.setText(("是，患者认为自己中风的危险等级 ："+reportItems.get(5).getStrokePreventiveInterventionReport().getThinkStroke()));
                                }else{
                                    hzrwzjsfzf.setText((""+reportItems.get(5).getStrokePreventiveInterventionReport().getThinkStroke()));
                                }
                            }else{
                                hzrwzjsfzf.setText("");
                            }









//                                Map<String, Object> item = new HashMap<String, Object>();
//                                JSONObject jsonObject = data.getJSONObject(0);
//                                item.put("followUpDate", jsonObject.getString("followUpDate"));
//                                item.put("writeDate", jsonObject.getString("writeDate").equals("null")?"":jsonObject.getString("writeDate") );
//                                item.put("sbp", jsonObject.getString("sbp").equals("null")?"":jsonObject.getString("sbp"));
//                            item.put("sbp", jsonObject.getString("sbp").equals("null")?"":jsonObject.getString("sbp"));
//                            item.put("dbp", jsonObject.getString("dbp").equals("null")?"":jsonObject.getString("dbp"));
//                            item.put("fbg", jsonObject.getString("fbg").equals("null")?"":jsonObject.getString("fbg"));
//                            item.put("pbg", jsonObject.getString("pbg").equals("null")?"":jsonObject.getString("pbg"));
//                            item.put("inr", jsonObject.getString("inr").equals("null")?"":jsonObject.getString("inr"));
//
//
//
//
//                            datas.add(item);
//                            for (int i = 0; i <datas.size() ; i++) {
//                            }
//                            for (int i = 0; i <datas.size() ; i++) {
//                                if(datas.get(i).get("writeDate").toString().equals("null")){
//                                }else{
//                                }
//
//                            }
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
    @ViewInject(R.id.scoll1) private ObservableScrollView scoll1;
    @ViewInject(R.id.scoll2) private ObservableScrollView  scoll2;
    @ViewInject(R.id.title1) private TextView title1;
    @ViewInject(R.id.title2) private TextView title2;
    @ViewInject(R.id.title3) private TextView title3;
    @ViewInject(R.id.title4) private TextView title4;
    @ViewInject(R.id.title5) private TextView title5;
    @ViewInject(R.id.title6) private TextView title6;

    @ViewInject(R.id.ssy1) private TextView ssy1;
    @ViewInject(R.id.ssy2) private TextView ssy2;
    @ViewInject(R.id.ssy3) private TextView ssy3;
    @ViewInject(R.id.ssy4) private TextView ssy4;
    @ViewInject(R.id.ssy5) private TextView ssy5;
    @ViewInject(R.id.ssy6) private TextView ssy6;

    @ViewInject(R.id.szy1) private TextView szy1;
    @ViewInject(R.id.szy2) private TextView szy2;
    @ViewInject(R.id.szy3) private TextView szy3;
    @ViewInject(R.id.szy4) private TextView szy4;
    @ViewInject(R.id.szy5) private TextView szy5;
    @ViewInject(R.id.szy6) private TextView szy6;

    @ViewInject(R.id.kfxt1) private TextView kfxt1;
    @ViewInject(R.id.kfxt2) private TextView kfxt2;
    @ViewInject(R.id.kfxt3) private TextView kfxt3;
    @ViewInject(R.id.kfxt4) private TextView kfxt4;
    @ViewInject(R.id.kfxt5) private TextView kfxt5;
    @ViewInject(R.id.kfxt6) private TextView kfxt6;

    @ViewInject(R.id.chxt1) private TextView chxt1;
    @ViewInject(R.id.chxt2) private TextView chxt2;
    @ViewInject(R.id.chxt3) private TextView chxt3;
    @ViewInject(R.id.chxt4) private TextView chxt4;
    @ViewInject(R.id.chxt5) private TextView chxt5;
    @ViewInject(R.id.chxt6) private TextView chxt6;

    @ViewInject(R.id.fckljc1) private TextView fckljc1;
    @ViewInject(R.id.fckljc2) private TextView fckljc2;
    @ViewInject(R.id.fckljc3) private TextView fckljc3;
    @ViewInject(R.id.fckljc4) private TextView fckljc4;
    @ViewInject(R.id.fckljc5) private TextView fckljc5;
    @ViewInject(R.id.fckljc6) private TextView fckljc6;

    @ViewInject(R.id.ywzlqk1) private TextView ywzlqk1;
    @ViewInject(R.id.ywzlqk2) private TextView ywzlqk2;
    @ViewInject(R.id.ywzlqk3) private TextView ywzlqk3;
    @ViewInject(R.id.ywzlqk4) private TextView ywzlqk4;
    @ViewInject(R.id.ywzlqk5) private TextView ywzlqk5;
    @ViewInject(R.id.ywzlqk6) private TextView ywzlqk6;

    @ViewInject(R.id.zzjszz1) private TextView zzjszz1;
    @ViewInject(R.id.zzjszz2) private TextView zzjszz2;
    @ViewInject(R.id.zzjszz3) private TextView zzjszz3;
    @ViewInject(R.id.zzjszz4) private TextView zzjszz4;
    @ViewInject(R.id.zzjszz5) private TextView zzjszz5;
    @ViewInject(R.id.zzjszz6) private TextView zzjszz6;

    @ViewInject(R.id.jkjy1) private TextView jkjy1;
    @ViewInject(R.id.jkjy2) private TextView jkjy2;
    @ViewInject(R.id.jkjy3) private TextView jkjy3;
    @ViewInject(R.id.jkjy4) private TextView jkjy4;
    @ViewInject(R.id.jkjy5) private TextView jkjy5;
    @ViewInject(R.id.jkjy6) private TextView jkjy6;

    @ViewInject(R.id.zdgc1) private TextView zdgc1;
    @ViewInject(R.id.zdgc2) private TextView zdgc2;

    @ViewInject(R.id.gysz1) private TextView gysz1;
    @ViewInject(R.id.gysz2) private TextView gysz2;

    @ViewInject(R.id.dmdzdbdgc1) private TextView dmdzdbdgc1;
    @ViewInject(R.id.dmdzdbdgc2) private TextView dmdzdbdgc2;

    @ViewInject(R.id.gmdzdbdgc1) private TextView gmdzdbdgc1;
    @ViewInject(R.id.gmdzdbdgc2) private TextView gmdzdbdgc2;

    @ViewInject(R.id.sgtzsjjc1) private TextView sgtzsjjc1;
    @ViewInject(R.id.sgtzsjjc2) private TextView sgtzsjjc2;

    @ViewInject(R.id.fywzlqk1) private TextView fywzlqk1;
    @ViewInject(R.id.fywzlqk2) private TextView fywzlqk2;

    @ViewInject(R.id.jsglcd1) private TextView jsglcd1;
    @ViewInject(R.id.jsglcd2) private TextView jsglcd2;

    @ViewInject(R.id.xgcsjcjc) private TextView xgcsjcjc;

    @ViewInject(R.id.zxjkzk) private TextView zxjkzk;

    @ViewInject(R.id.hzzzrzsp) private TextView hzzzrzsp;

    @ViewInject(R.id.bnwxysglpj) private TextView bnwxysglpj;

    @ViewInject(R.id.zzyfbnbg) private TextView zzyfbnbg;

    @ViewInject(R.id.hzrwzjsfzf) private TextView hzrwzjsfzf;

    @OnClick({
            R.id.ywzlqk1,
            R.id.ywzlqk2,
            R.id.ywzlqk3,
            R.id.ywzlqk4,
            R.id.ywzlqk5,
            R.id.ywzlqk6,
            R.id.zzjszz1 ,
            R.id.zzjszz2,
            R.id.zzjszz3,
            R.id.zzjszz4,
            R.id.zzjszz5,
            R.id.zzjszz6,
            R.id.jkjy1,
            R.id.jkjy2,
            R.id.jkjy3,
            R.id.jkjy4,
            R.id.jkjy5,
            R.id.jkjy6,
            R.id.fywzlqk1,
            R.id.fywzlqk2,
            R.id.xgcsjcjc,
            R.id.zzyfbnbg,
            R.id.zxjkzk
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ywzlqk1:
                topActivity(StrokeYwzlActivity.class);
                break;
            case R.id.ywzlqk2:
                topActivity(StrokeYwzlActivity.class);
                break;
            case R.id.ywzlqk3:
                topActivity(StrokeYwzlActivity.class);
                break;
            case R.id.ywzlqk4:
                topActivity(StrokeYwzlActivity.class);
                break;
            case R.id.ywzlqk5:
                topActivity(StrokeYwzlActivity.class);
                break;
            case R.id.ywzlqk6:
                topActivity(StrokeYwzlActivity.class);
                break;
            case R.id.zzjszz1:
                topActivity(StrokeZzjszzActivity.class);
                break;
            case R.id.zzjszz2:
                topActivity(StrokeZzjszzActivity.class);
                break;
            case R.id.zzjszz3:
                topActivity(StrokeZzjszzActivity.class);
                break;
            case R.id.zzjszz4:
                topActivity(StrokeZzjszzActivity.class);
                break;
            case R.id.zzjszz5:
                topActivity(StrokeZzjszzActivity.class);
                break;
            case R.id.zzjszz6:
                topActivity(StrokeZzjszzActivity.class);
                break;
            case R.id.jkjy1:
                topActivity(StrokeJkjyActivity.class);
                break;
            case R.id.jkjy2:
                topActivity(StrokeJkjyActivity.class);
                break;
            case R.id.jkjy3:
                topActivity(StrokeJkjyActivity.class);
                break;
            case R.id.jkjy4:
                topActivity(StrokeJkjyActivity.class);
                break;
            case R.id.jkjy5:
                topActivity(StrokeJkjyActivity.class);
                break;
            case R.id.jkjy6:
                topActivity(StrokeJkjyActivity.class);
                break;
            case R.id.fywzlqk1:
                topActivity(StrokeFywActivity.class);
                break;
            case R.id.fywzlqk2:
                topActivity(StrokeFywActivity.class);
                break;
            case R.id.xgcsjcjc:
                topActivity(StrokeXgcsjcActivity.class);
                break;
            case R.id.zzyfbnbg:
                topActivity(StrokeZzyfbnbgActivity.class);
                break;
            case R.id.zxjkzk:
                topActivity(StrokeZxjkzkActivity.class);
                break;
        }
    }
public void topActivity(Class newactivity){
    Intent intent = new Intent();
    intent.setClass(activity,newactivity);
    intent.putExtra("data",json);
    startActivity(intent);
}
    public String intToString(String s){
        if(s .equals("0")){
            return "";
        }else if( s.equals("null")){
            return "";
        }else{
            return s;
        }

    }
    public String intToString2(String s){
        if(s .equals("0")){
            return "";
        }else if( s.equals("null")){
            return "";
        }else if( s.equals("")){
            return "";
        }else{
            return "("+s+")";
        }

    }
}

