package com.uyi.app.ui.stroke;

import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.stroke.model.StrokeFollowUpRecordJson;
import com.uyi.custom.app.R;

import java.util.List;

/**
 * Created by ThinkPad on 2017/1/12.
 * 卒中预防半年报告
 */
@ContentView(R.layout.activity_stroke_zxjkzk)
public class StrokeZxjkzkActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.zxjkzk_1) private TextView zxjkzk_1;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("详情").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(getIntent().getStringExtra("data"), StrokeFollowUpRecordJson.class);
        zxjkzk_1.setText(reportItems.get(5).getStrokePreventiveInterventionReport() == null ? "" : reportItems.get(5).getStrokePreventiveInterventionReport().getPlanAdjustment());
        String jdm = "";
        if(intToString(reportItems.get(5).getHealthStatus().getNewDiscovery() +"") .equals("")){
            jdm = "";
        }else{
            jdm = "体检新发现："+reportItems.get(5).getHealthStatus().getNewDiscovery() +"";
        }
        if(reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList()!=null&&reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().size()>0) {
            if (intToString(reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(0).getDiagnosisDate() + "").equals("")) {
                jdm += "";
            } else {
                jdm += "\n" + "颈动脉狭窄 ：" + reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(0).getDiagnosisDate() + "";
            }
            if (intToString(reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(1).getDiagnosisDate() + "").equals("")) {
                jdm += "";
            } else {
                jdm += "\n" + "颈动脉斑块 ：" + reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(1).getDiagnosisDate() + "";
            }
            if (intToString(reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(2).getDiagnosisDate() + "").equals("")) {
                jdm += "";
            } else {
                jdm += "\n" + "脑卒中（缺血性）"
                        + intToString2(reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(2).getDiagnosisType() + "")
                        + "：" + reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(2).getDiagnosisDate() + "";
            }
            if (intToString(reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(3).getDiagnosisDate() + "").equals("")) {
                jdm += "";
            } else {
                jdm += "\n" + "脑卒中（出血性）：" + reportItems.get(5).getHealthStatus().getDiseaseDiagnosisList().get(3).getDiagnosisDate() + "";
            }
        }
        zxjkzk_1.setText(jdm);

    }
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
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
