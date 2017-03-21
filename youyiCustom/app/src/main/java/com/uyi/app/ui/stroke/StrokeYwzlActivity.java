package com.uyi.app.ui.stroke;

import android.view.View;
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
 * 药物治疗情况
 */
@ContentView(R.layout.activity_stroke_ywzlqk)
public class StrokeYwzlActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.ywzlqk_1_1)
    private TextView ywzlqk_1_1;
    @ViewInject(R.id.ywzlqk_1_2)
    private TextView ywzlqk_1_2;
    @ViewInject(R.id.ywzlqk_1_3)
    private TextView ywzlqk_1_3;
    @ViewInject(R.id.ywzlqk_1_4)
    private TextView ywzlqk_1_4;
    @ViewInject(R.id.ywzlqk_1_5)
    private TextView ywzlqk_1_5;

    List<TextView> textViews1 = new ArrayList<>();
    List<TextView> textViews2 = new ArrayList<>();
    List<TextView> textViews3 = new ArrayList<>();
    List<TextView> textViews4 = new ArrayList<>();
    List<TextView> textViews5 = new ArrayList<>();
    List<TextView> textViews6 = new ArrayList<>();
    List<String> textViewsstr = new ArrayList<>();
    @ViewInject(R.id.ywzlqk_2_1)
    private TextView ywzlqk_2_1;
    @ViewInject(R.id.ywzlqk_2_2)
    private TextView ywzlqk_2_2;
    @ViewInject(R.id.ywzlqk_2_3)
    private TextView ywzlqk_2_3;
    @ViewInject(R.id.ywzlqk_2_4)
    private TextView ywzlqk_2_4;
    @ViewInject(R.id.ywzlqk_2_5)
    private TextView ywzlqk_2_5;
    @ViewInject(R.id.ywzlqk_3_1)
    private TextView ywzlqk_3_1;
    @ViewInject(R.id.ywzlqk_3_2)
    private TextView ywzlqk_3_2;
    @ViewInject(R.id.ywzlqk_3_3)
    private TextView ywzlqk_3_3;
    @ViewInject(R.id.ywzlqk_3_4)
    private TextView ywzlqk_3_4;
    @ViewInject(R.id.ywzlqk_3_5)
    private TextView ywzlqk_3_5;
    @ViewInject(R.id.ywzlqk_4_1)
    private TextView ywzlqk_4_1;
    @ViewInject(R.id.ywzlqk_4_2)
    private TextView ywzlqk_4_2;
    @ViewInject(R.id.ywzlqk_4_3)
    private TextView ywzlqk_4_3;
    @ViewInject(R.id.ywzlqk_4_4)
    private TextView ywzlqk_4_4;
    @ViewInject(R.id.ywzlqk_4_5)
    private TextView ywzlqk_4_5;
    @ViewInject(R.id.ywzlqk_5_1)
    private TextView ywzlqk_5_1;
    @ViewInject(R.id.ywzlqk_5_2)
    private TextView ywzlqk_5_2;
    @ViewInject(R.id.ywzlqk_5_3)
    private TextView ywzlqk_5_3;
    @ViewInject(R.id.ywzlqk_5_4)
    private TextView ywzlqk_5_4;
    @ViewInject(R.id.ywzlqk_5_5)
    private TextView ywzlqk_5_5;
    @ViewInject(R.id.ywzlqk_6_1)
    private TextView ywzlqk_6_1;
    @ViewInject(R.id.ywzlqk_6_2)
    private TextView ywzlqk_6_2;
    @ViewInject(R.id.ywzlqk_6_3)
    private TextView ywzlqk_6_3;
    @ViewInject(R.id.ywzlqk_6_4)
    private TextView ywzlqk_6_4;
    @ViewInject(R.id.ywzlqk_6_5)
    private TextView ywzlqk_6_5;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("药物治疗情况").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        List<StrokeFollowUpRecordJson> reportItems = JSON.parseArray(getIntent().getStringExtra("data"), StrokeFollowUpRecordJson.class);

        initView();

//        L.e("DOCTOR_HEALTH_STROKE_DETAILS1 == " + reportItems.get(0).getDrugTreatments());

        if (reportItems.get(0).getDrugTreatments().size() == 0) {
            ywzlqk_1_1.setVisibility(View.GONE);
            ywzlqk_1_2.setVisibility(View.GONE);
            ywzlqk_1_3.setVisibility(View.GONE);
            ywzlqk_1_4.setVisibility(View.GONE);
            ywzlqk_1_5.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < textViews1.size(); i++) {

                if (reportItems.get(0).getDrugTreatments().get(i).getMedicationMethods() != null) {


                    if (reportItems.get(0).getDrugTreatments().get(i).getMedicationMethods().equals("规律用药")) {
                        textViews1.get(i).setText(textViewsstr.get(i) + "规律用药-" + (reportItems.get(0).getDrugTreatments().get(i).getDrug() == null ? "" : reportItems.get(0).getDrugTreatments().get(i).getDrug().toString()));
                    } else if (reportItems.get(0).getDrugTreatments().get(i).getMedicationMethods().equals("不规律用药")) {
                        textViews1.get(i).setText(textViewsstr.get(i) + "不规律用药-" + (reportItems.get(0).getDrugTreatments().get(i).getIrregularMedication() == null ? "" : reportItems.get(0).getDrugTreatments().get(i).getIrregularMedication().toString()));
                    } else if (reportItems.get(0).getDrugTreatments().get(i).getMedicationMethods().equals("不用药")) {
                        textViews1.get(i).setText(textViewsstr.get(i) + "不用药");
                    } else {
                        textViews1.get(i).setVisibility(View.GONE);
                    }
                } else {
                    textViews1.get(i).setVisibility(View.GONE);
                }
            }

        }
        if (reportItems.get(1).getDrugTreatments().size() == 0) {
            ywzlqk_2_1.setVisibility(View.GONE);
            ywzlqk_2_2.setVisibility(View.GONE);
            ywzlqk_2_3.setVisibility(View.GONE);
            ywzlqk_2_4.setVisibility(View.GONE);
            ywzlqk_2_5.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < textViews2.size(); i++) {
                if (reportItems.get(1).getDrugTreatments().get(i).getMedicationMethods() != null) {
                    if (reportItems.get(1).getDrugTreatments().get(i).getMedicationMethods().equals("规律用药")) {
                        textViews2.get(i).setText(textViewsstr.get(i) + "规律用药-" + (reportItems.get(1).getDrugTreatments().get(i).getDrug() == null ? "" : reportItems.get(1).getDrugTreatments().get(i).getDrug().toString()));
                    } else if (reportItems.get(1).getDrugTreatments().get(i).getMedicationMethods().equals("不规律用药")) {
                        textViews2.get(i).setText(textViewsstr.get(i) + "不规律用药-" + (reportItems.get(1).getDrugTreatments().get(i).getIrregularMedication() == null ? "" : reportItems.get(1).getDrugTreatments().get(i).getIrregularMedication().toString()));
                    } else if (reportItems.get(1).getDrugTreatments().get(i).getMedicationMethods().equals("不用药")) {
                        textViews2.get(i).setText(textViewsstr.get(i) + "不用药");
                    } else {
                        textViews2.get(i).setVisibility(View.GONE);
                    }
                } else {
                    textViews2.get(i).setVisibility(View.GONE);
                }
            }

        }
        if (reportItems.get(2).getDrugTreatments().size() == 0) {
            ywzlqk_3_1.setVisibility(View.GONE);
            ywzlqk_3_2.setVisibility(View.GONE);
            ywzlqk_3_3.setVisibility(View.GONE);
            ywzlqk_3_4.setVisibility(View.GONE);
            ywzlqk_3_5.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < textViews3.size(); i++) {
                if (reportItems.get(2).getDrugTreatments().get(i).getMedicationMethods() != null) {
                    if (reportItems.get(2).getDrugTreatments().get(i).getMedicationMethods().equals("规律用药")) {
                        textViews3.get(i).setText(textViewsstr.get(i) + "规律用药-" + (reportItems.get(2).getDrugTreatments().get(i).getDrug() == null ? "" : reportItems.get(2).getDrugTreatments().get(i).getDrug().toString()));
                    } else if (reportItems.get(2).getDrugTreatments().get(i).getMedicationMethods().equals("不规律用药")) {
                        textViews3.get(i).setText(textViewsstr.get(i) + "不规律用药-" + (reportItems.get(2).getDrugTreatments().get(i).getIrregularMedication() == null ? "" : reportItems.get(2).getDrugTreatments().get(i).getIrregularMedication().toString()));
                    } else if (reportItems.get(2).getDrugTreatments().get(i).getMedicationMethods().equals("不用药")) {
                        textViews3.get(i).setText(textViewsstr.get(i) + "不用药");
                    } else {
                        textViews3.get(i).setVisibility(View.GONE);
                    }
                } else {
                    textViews3.get(i).setVisibility(View.GONE);
                }
            }

        }
        if (reportItems.get(3).getDrugTreatments().size() == 0) {
            ywzlqk_4_1.setVisibility(View.GONE);
            ywzlqk_4_2.setVisibility(View.GONE);
            ywzlqk_4_3.setVisibility(View.GONE);
            ywzlqk_4_4.setVisibility(View.GONE);
            ywzlqk_4_5.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < textViews4.size(); i++) {
                if (reportItems.get(3).getDrugTreatments().get(i).getMedicationMethods() != null) {
                    if (reportItems.get(3).getDrugTreatments().get(i).getMedicationMethods().equals("规律用药")) {
                        textViews4.get(i).setText(textViewsstr.get(i) + "规律用药-" + (reportItems.get(3).getDrugTreatments().get(i).getDrug() == null ? "" : reportItems.get(3).getDrugTreatments().get(i).getDrug().toString()));
                    } else if (reportItems.get(3).getDrugTreatments().get(i).getMedicationMethods().equals("不规律用药")) {
                        textViews4.get(i).setText(textViewsstr.get(i) + "不规律用药-" + (reportItems.get(3).getDrugTreatments().get(i).getIrregularMedication() == null ? "" : reportItems.get(3).getDrugTreatments().get(i).getIrregularMedication().toString()));
                    } else if (reportItems.get(3).getDrugTreatments().get(i).getMedicationMethods().equals("不用药")) {
                        textViews4.get(i).setText(textViewsstr.get(i) + "不用药");
                    } else {
                        textViews4.get(i).setVisibility(View.GONE);
                    }
                } else {
                    textViews4.get(i).setVisibility(View.GONE);
                }
            }

        }
        if (reportItems.get(4).getDrugTreatments().size() == 0) {
            ywzlqk_5_1.setVisibility(View.GONE);
            ywzlqk_5_2.setVisibility(View.GONE);
            ywzlqk_5_3.setVisibility(View.GONE);
            ywzlqk_5_4.setVisibility(View.GONE);
            ywzlqk_5_5.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < textViews5.size(); i++) {
                if (reportItems.get(4).getDrugTreatments().get(i).getMedicationMethods() != null) {
                    if (reportItems.get(4).getDrugTreatments().get(i).getMedicationMethods().equals("规律用药")) {
                        textViews5.get(i).setText(textViewsstr.get(i) + "规律用药-" + (reportItems.get(4).getDrugTreatments().get(i).getDrug() == null ? "" : reportItems.get(4).getDrugTreatments().get(i).getDrug().toString()));
                    } else if (reportItems.get(4).getDrugTreatments().get(i).getMedicationMethods().equals("不规律用药")) {
                        textViews5.get(i).setText(textViewsstr.get(i) + "不规律用药-" + (reportItems.get(4).getDrugTreatments().get(i).getIrregularMedication() == null ? "" : reportItems.get(4).getDrugTreatments().get(i).getIrregularMedication().toString()));
                    } else if (reportItems.get(4).getDrugTreatments().get(i).getMedicationMethods().equals("不用药")) {
                        textViews5.get(i).setText(textViewsstr.get(i) + "不用药");
                    } else {
                        textViews5.get(i).setVisibility(View.GONE);
                    }
                } else {
                    textViews5.get(i).setVisibility(View.GONE);
                }
            }

        }
        if (reportItems.get(5).getDrugTreatments().size() == 0) {
            ywzlqk_6_1.setVisibility(View.GONE);
            ywzlqk_6_2.setVisibility(View.GONE);
            ywzlqk_6_3.setVisibility(View.GONE);
            ywzlqk_6_4.setVisibility(View.GONE);
            ywzlqk_6_5.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < textViews6.size(); i++) {
                if (reportItems.get(5).getDrugTreatments().get(i).getMedicationMethods() != null) {
                    if (reportItems.get(5).getDrugTreatments().get(i).getMedicationMethods().equals("规律用药")) {
                        textViews6.get(i).setText(textViewsstr.get(i) + "规律用药-" + (reportItems.get(5).getDrugTreatments().get(i).getDrug() == null ? "" : reportItems.get(5).getDrugTreatments().get(i).getDrug().toString()));
                    } else if (reportItems.get(5).getDrugTreatments().get(i).getMedicationMethods().equals("不规律用药")) {
                        textViews6.get(i).setText(textViewsstr.get(i) + "不规律用药-" + (reportItems.get(5).getDrugTreatments().get(i).getIrregularMedication() == null ? "" : reportItems.get(5).getDrugTreatments().get(i).getIrregularMedication().toString()));
                    } else if (reportItems.get(5).getDrugTreatments().get(i).getMedicationMethods().equals("不用药")) {
                        textViews6.get(i).setText(textViewsstr.get(i) + "不用药");
                    } else {
                        textViews6.get(i).setVisibility(View.GONE);
                    }
                } else {
                    textViews6.get(i).setVisibility(View.GONE);
                }
            }

        }
        L.e("DOCTOR_HEALTH_STROKE_DETAILS1 == " + JSON.parseArray(getIntent().getStringExtra("data")).get(5).toString());
        L.e("DOCTOR_HEALTH_STROKE_DETAILS2 == " + reportItems.get(1).getDrugTreatments().toString());
        L.e("DOCTOR_HEALTH_STROKE_DETAILS3 == " + reportItems.get(2).getDrugTreatments().toString());
        L.e("DOCTOR_HEALTH_STROKE_DETAILS4 == " + reportItems.get(3).getDrugTreatments().toString());
        L.e("DOCTOR_HEALTH_STROKE_DETAILS5 == " + reportItems.get(4).getDrugTreatments().toString());
        L.e("DOCTOR_HEALTH_STROKE_DETAILS6 == " + reportItems.get(5).getDrugTreatments().toString());
    }

    private void initView() {
        textViews1.add(ywzlqk_1_1);
        textViews1.add(ywzlqk_1_2);
        textViews1.add(ywzlqk_1_3);
        textViews1.add(ywzlqk_1_4);
        textViews1.add(ywzlqk_1_5);
        textViews2.add(ywzlqk_2_1);
        textViews2.add(ywzlqk_2_2);
        textViews2.add(ywzlqk_2_3);
        textViews2.add(ywzlqk_2_4);
        textViews2.add(ywzlqk_2_5);
        textViews3.add(ywzlqk_3_1);
        textViews3.add(ywzlqk_3_2);
        textViews3.add(ywzlqk_3_3);
        textViews3.add(ywzlqk_3_4);
        textViews3.add(ywzlqk_3_5);
        textViews4.add(ywzlqk_4_1);
        textViews4.add(ywzlqk_4_2);
        textViews4.add(ywzlqk_4_3);
        textViews4.add(ywzlqk_4_4);
        textViews4.add(ywzlqk_4_5);
        textViews5.add(ywzlqk_5_1);
        textViews5.add(ywzlqk_5_2);
        textViews5.add(ywzlqk_5_3);
        textViews5.add(ywzlqk_5_4);
        textViews5.add(ywzlqk_5_5);
        textViews6.add(ywzlqk_6_1);
        textViews6.add(ywzlqk_6_2);
        textViews6.add(ywzlqk_6_3);
        textViews6.add(ywzlqk_6_4);
        textViews6.add(ywzlqk_6_5);
        textViewsstr.add("控制血压药物：");
        textViewsstr.add("控制血糖药物：");
        textViewsstr.add("他汀类药物：");
        textViewsstr.add("抗血小板聚集药物：");
        textViewsstr.add("房颤抗凝药物：");
    }

    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
