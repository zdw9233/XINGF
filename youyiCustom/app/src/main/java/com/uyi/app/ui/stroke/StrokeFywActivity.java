package com.uyi.app.ui.stroke;

import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.stroke.model.StrokeFollowUpRecordJson;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2017/1/12.
 * 非药物治疗情况
 */
@ContentView(R.layout.activity_stroke_fyw)
public class StrokeFywActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.fyw_jd_1) private TextView fyw_jd_1;
    @ViewInject(R.id.fyw_jd_2) private TextView fyw_jd_2;
    @ViewInject(R.id.fyw_bn_1) private TextView fyw_bn_1;
    @ViewInject(R.id.fyw_bn_2) private TextView fyw_bn_2;
    List<TextView> textViews_jd = new ArrayList<>();  List<TextView> textViews_bn = new ArrayList<>();
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("非药物治疗情况").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(getIntent().getStringExtra("data"), StrokeFollowUpRecordJson.class);
        textViews_jd.add(fyw_jd_1);
        textViews_jd.add(fyw_bn_1);
        textViews_bn.add(fyw_jd_2);
        textViews_bn.add(fyw_bn_2);
        for (int i = 0; i < 2 ; i++) {
            L.e("DOCTOR_HEALTH_STROKE_DETAILS== " + reportItems.get(3*i + 2).getNonDrugTreatment());
            if(reportItems.get(3*i + 2).getNonDrugTreatment()==null){
                textViews_jd.get(i).setText("非药物治疗措施：");

            }else{
                textViews_jd.get(i).setText( "非药物治疗措施："+reportItems.get(3*i + 2).getNonDrugTreatment().replace("|","; "));
            }
        }
        for (int i = 0; i < 2 ; i++) {
            L.e("DOCTOR_HEALTH_STROKE_DETAILSjson== " + reportItems.get(3*i + 2).getSelfEvaluation());
            if(reportItems.get(3*i + 2).getSelfEvaluation()==null){
                textViews_bn.get(i).setText("自我评价：");

            }else{
                String str = "";
                if(reportItems.get(3*i + 2).getSelfEvaluation().getSmoking() != null && !reportItems.get(3*i + 2).getSelfEvaluation().getSmoking().equals("")){
                    str+= "吸烟量较以前"+reportItems.get(3*i + 2).getSelfEvaluation().getSmoking()+";";
                }
                if(reportItems.get(3*i + 2).getSelfEvaluation().getDrinking() != null&&!reportItems.get(3*i + 2).getSelfEvaluation().getDrinking().equals("")){
                    str+= "饮酒量较以前"+reportItems.get(3*i + 2).getSelfEvaluation().getDrinking()+";";
                }
                if(reportItems.get(3*i + 2).getSelfEvaluation().getMeatIntake() != null&&!reportItems.get(3*i + 2).getSelfEvaluation().getMeatIntake().equals("")){
                    str+= "脂肪摄入较以前"+reportItems.get(3*i + 2).getSelfEvaluation().getMeatIntake()+";";
                }
                if(reportItems.get(3*i + 2).getSelfEvaluation().getVegetablesIntake() != null&&!reportItems.get(3*i + 2).getSelfEvaluation().getVegetablesIntake().equals("")){
                    str+= "蔬菜摄入较以前"+reportItems.get(3*i + 2).getSelfEvaluation().getVegetablesIntake()+";";
                }
                if(reportItems.get(3*i + 2).getSelfEvaluation().getFruitIntake() != null&&!reportItems.get(3*i + 2).getSelfEvaluation().getFruitIntake().equals("")){
                    str+= "水果摄入较以前"+reportItems.get(3*i + 2).getSelfEvaluation().getFruitIntake()+";";
                }
                if(reportItems.get(3*i + 2).getSelfEvaluation().getLfdpIntake() != null&&!reportItems.get(3*i + 2).getSelfEvaluation().getLfdpIntake().equals("")){
                    str+= "低脂肪奶制品摄入较以前"+reportItems.get(3*i + 2).getSelfEvaluation().getLfdpIntake()+";";
                }
                if(reportItems.get(3*i + 2).getSelfEvaluation().getPhysicalActivity() != null&&!reportItems.get(3*i + 2).getSelfEvaluation().getPhysicalActivity().equals("")){
                    str+= "体力活动较以前"+reportItems.get(3*i + 2).getSelfEvaluation().getPhysicalActivity();
                }
                if(str.equals("")){
                    textViews_bn.get(i).setText( "自我评价："+str);
                }else{
                    textViews_bn.get(i).setText( "自我评价："+str);
                }
            }
        }

    }
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
