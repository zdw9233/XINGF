package com.uyi.app.ui.report;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.doctor.app.R;

import java.util.Locale;

/**
 * 填写健康报告 Created by Leeii on 2016/7/16.
 */
@ContentView(R.layout.activity_write_report)
public class WriteReportActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.jb)
    private EditText jb;
    @ViewInject(R.id.maxXy)
    private EditText maxXy;
    @ViewInject(R.id.lxxy)
    private EditText lxxy;
    @ViewInject(R.id.mqyw)
    private EditText mqyw;
    @ViewInject(R.id.zywt)
    private EditText zywt;
    @ViewInject(R.id.zdxm)
    private EditText zdxm;
    @ViewInject(R.id.jyfc)
    private EditText jyfc;

    @ViewInject(R.id.xy)
    private Spinner xy;

    private String text1 = "您的血压整体情况符合勺型曲线规律，即：晨起血压水平较高，夜晚睡眠时血压水平较低，在清晨开始上升， 点左右出现高峰，然后逐渐平稳，晚餐前再次出现高峰（次高峰），然后缓慢下降，凌晨睡梦中达低谷并维持到次日清晨，全天出现双峰一谷的长柄勺型曲线。这种节律变化对适应机体活动、保护心血管结构和功能有重要意义。";
    private String text2 = "在本月内的监测记录中，您多次在出现%s偏高，最高时（请您根据该患者情况给出时间段，比如：晨起、午后、晚餐后、睡前）%s曾达到 %smmHg，而理想的舒张压应控制在%smmHg以下。根据您目前所服用的降压药物%s，考虑出现这种情况后需调整降压药物的方案，请联系您所在医疗团队的慢病管理师为您预约专家面诊。\n";
    private String text3 = "您在本月上传了体检报告，您目前主要存在的问题有：%s，提示了您多种代谢指标的紊乱，而这在一定程度上反应出您欠佳规范的饮食和良好的运动习惯。针对体检发现的异常情况，需要心血管内科、内分泌科医生共同为您制定进一步的生活、饮食、药物干预方案。\n";
    private String text4 = "%s目前尚无根治方法，但长期以来的临床实践证实，%s的良好控制可以有效减少心脑血管病发病率和死亡率，您进行这些指标监测和治疗的最终目的也是维护自身健康，减少发病可能在改善生活方式上，我们建议您\n" +
            "①减轻体重。BMI控制在25以内。\n" +
            "②减少钠盐摄入，每日＜6g为宜。\n" +
            "③补充钙和钾盐，每天日吃新鲜蔬菜500g，左右，适量饮用牛奶\n" +
            "④减少脂肪摄入 ⑤戒烟限酒，每日白酒不超过100ml，红酒不超过200ml，尽量避免饮用啤酒及高度白酒  \n" +
            "⑥增加运动，运动有利于减轻体重和改善胰岛素抵抗，提高心血管适应调节能力，稳定血压水平，较好的运动方式是低或中等强度的慢跑、步行或游泳，一般每周3~5次，每次20~60分钟。鉴于您所提供的体检报告针对高血压并发症的筛查还有所欠缺，我们建议您择期再复查%s，这些检查项目有助于发现心脑血管相关的危险因素和靶器官损害，在您完成体检后，请及时将报告上传至APP，我们的医生团队也会根据您自身情况做出对比和解读。";

    @OnClick(R.id.commit_report)
    public void onClick(View v) {
        String jibing = jb.getText().toString();
        String maxXy = this.maxXy.getText().toString();
        String lxxy = this.lxxy.getText().toString();
        String mqyw = this.mqyw.getText().toString();
        String zywt = this.zywt.getText().toString();
        String zdxm = this.zdxm.getText().toString();
        String jyfc = this.jyfc.getText().toString();
        String xueya = (String) xy.getSelectedItem();

        String comment1 = text1;
        String comment2 = String.format(Locale.CHINA, text2, xueya, xueya, maxXy, lxxy, mqyw);
        String comment3 = String.format(Locale.CHINA, text3, zywt);
        String comment4 = String.format(Locale.CHINA, text4, jibing, zdxm, jyfc);
        Intent intent = new Intent();
        intent.putExtra("comment1", comment1);
        intent.putExtra("comment2", comment2);
        intent.putExtra("comment3", comment3);
        intent.putExtra("comment4", comment4);
        setResult(0x200, intent);
        finish();
    }

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("详细报告").setTitleColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
}
