package com.uyi.app.ui.report;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;

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

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("主诊报告").setTitleColor(getResources().getColor(R.color.blue));

        nameText.setText(Html.fromHtml(String.format(Locale.CHINA, "<font color='#252525'>尊敬的</font><font color='#FF4500'>%s</font><font color='#252525'>先生/女士：</font>", UserInfoManager.getLoginUserInfo(this).realName)));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @OnClick(R.id.detail_report)
    public void onClick(View v) {
        int id = getIntent().getIntExtra("reportId", 0);
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("reportId", id);
        startActivity(intent);
    }
}
