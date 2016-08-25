package com.uyi.app.ui.report;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * 用户端主诊报告首页 Created by Leeii on 2016/7/9.
 */
@ContentView(R.layout.activity_report_main)
public class ReportMainActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.tv_name)
    private TextView nameText;
    @ViewInject(R.id.send_messge)
    private TextView send_messge;
    @ViewInject(R.id.detail_messge)
    private EditText detail_messge;

    @Override
    protected void onInitLayoutAfter() {

        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("主诊报告").setTitleColor(getResources().getColor(R.color.blue));

        nameText.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>尊敬的</font><font color='#FF4500'>%s</font><font color='#252525'>先生/女士：</font>", UserInfoManager.getLoginUserInfo(this).realName)));
        Loading.bulid(ReportMainActivity.this, null).show();
        RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                Loading.bulid(ReportMainActivity.this, null).dismiss();
                try {
                    if( UserInfoManager.getLoginUserInfo(ReportMainActivity.this).logasguardian){
                        detail_messge.setText(data.getJSONObject("guardianInfo").getString("email"));
                    }else{
                        detail_messge.setText(data.getString("email"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @OnClick({R.id.detail_report,R.id.send_messge})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_report:
                int id = getIntent().getIntExtra("reportId", 0);

                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("reportId", id);
                startActivity(intent);
                break;
            case R.id.send_messge:
                String emeile = detail_messge.getText().toString();
                if (!ValidationUtils.isNull(emeile)) {
                    if (!ValidationUtils.pattern(Constens.EMAIL_REGEX, emeile)) {
                        T.showLong(application, "邮箱格式不正确!");
                        return;
                    }else{
                        int reportId = getIntent().getIntExtra("reportId", 0);
                        JSONObject params = new JSONObject();
                        try {
                            params.put("recipientEmail", emeile);
                            params.put("reportId", reportId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Loading.bulid(ReportMainActivity.this, null).show();
                        RequestManager.getObject(String.format(Locale.CHINA, Constens.SEND_REPORT, reportId,emeile), this, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
//                                L.e("jsonObject",jsonObject.toString());
                                Loading.bulid(ReportMainActivity.this, null).dismiss();
                                T.showLong(application, "发送成功!");
                            }
                        });

                    }
                }else{
                    T.showLong(application, "邮箱不能为空!");
                }
                break;
        }

    }
}
