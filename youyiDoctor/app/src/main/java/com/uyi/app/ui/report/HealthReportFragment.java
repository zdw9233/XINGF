package com.uyi.app.ui.report;

import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.FragmentHealthListManager;
import com.uyi.app.ui.report.model.Report;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 健康报告 Created by Leeii on 2016/7/2.
 */
public class HealthReportFragment extends BaseFragment {
    @ViewInject(R.id.et_xybd)
    private TextView et_xybd;  //血压波动趋势
    @ViewInject(R.id.et_xyyc)
    private TextView et_xyyc;  //血压异常分析
    @ViewInject(R.id.et_zhfx)
    private TextView et_zhfx;  //综合分析
    @ViewInject(R.id.et_jkjy)
    private TextView et_jkjy;  //健康建议
    @ViewInject(R.id.et_xybd1)
    private TextView et_xybd1;  //血压波动趋势
    @ViewInject(R.id.et_xyyc1)
    private TextView et_xyyc1;  //血压异常分析
    @ViewInject(R.id.et_zhfx1)
    private TextView et_zhfx1;  //综合分析
    @ViewInject(R.id.et_jkjy1)
    private TextView et_jkjy1;  //健康建议
    @ViewInject(R.id.old_layout)
    private View old_layout;  //不能改
    @ViewInject(R.id.new_layout)
    private View new_layout;  //可以改


    @OnClick(R.id.commit_report)
    public void onClick(View v) {
        ReportActivity mActivity = (ReportActivity) getActivity();
        Report report = mActivity.getReport();

        JSONObject object = new JSONObject();
        try {
            object.put("comment1", report.getComment1());
            object.put("comment2", report.getComment2());
            object.put("comment3", report.getComment3());
            object.put("comment4", report.getComment4());
            object.put("cusid", FragmentHealthListManager.customer);
            object.put("bloodPressure_pic", report.bloodPressure_pic);
            object.put("BloodSugar_pic", report.bloodSugar_pic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Loading.bulid(context, null).show();
        RequestManager.postObject(Constens.COMMIT_REPORT, context, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Loading.bulid(context, null).dismiss();
                T.showLong(context, "提交成功！");
                getActivity().setResult(0x200);
                getActivity().finish();
            }
        }, null);
    }

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_health_report;
    }

    @Override
    protected void onInitLayoutAfter() {
        ReportActivity mActivity = (ReportActivity) getActivity();
        Report report = mActivity.getReport();

        et_xybd.setText(report.getComment1());
        et_xyyc.setText(report.getComment2());
        et_zhfx.setText(report.getComment3());
        et_jkjy.setText(report.getComment4());
        et_xybd1.setText(report.getComment1());
        et_xyyc1.setText(report.getComment2());
        et_zhfx1.setText(report.getComment3());
        et_jkjy1.setText(report.getComment4());

        if (mActivity.isOld()) {
            old_layout.setVisibility(View.VISIBLE);
            new_layout.setVisibility(View.GONE);
        } else {
            old_layout.setVisibility(View.GONE);
            new_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
