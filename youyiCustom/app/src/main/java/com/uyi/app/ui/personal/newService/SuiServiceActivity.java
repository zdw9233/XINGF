package com.uyi.app.ui.personal.newService;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.personal.EaseHalfYearActivity;
import com.uyi.app.ui.personal.EaseOneYearActivity;
import com.uyi.app.ui.personal.EasePeaceExperienceActivity;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * EaseServiceActivity  Created by ThinkPad on 2016/7/2.
 */
@ContentView(R.layout.activity_sui_of_service)
public class SuiServiceActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.pay)
    private TextView pay;
    private final String TYPE_AX_3_MONTH = "AX1"; //  安心服务基础
    private final String TYPE_AX_6_MONTH = "AX2";   //安心服务精细

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("安心服务").setTitleColor(getResources().getColor(R.color.blue));
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                break;

        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    private void requestServicePackage(final String type) {
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_SERVICE_PACKAGE, type), SuiServiceActivity.this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                L.e(jsonObject.toString());
                try {
                    if (jsonObject.has("id")) {
                        int id = jsonObject.getInt("id");
                        int beans = jsonObject.getInt("beans");
                        boolean b = jsonObject.getBoolean("isActive");
                        Intent intent = new Intent();
                        if (TYPE_AX_3_MONTH.equals(type)) {
                            intent.setClass(SuiServiceActivity.this, EasePeaceExperienceActivity.class);
                        } else if (TYPE_AX_6_MONTH.equals(type)) {
                            intent.setClass(SuiServiceActivity.this, EaseHalfYearActivity.class);
                        } else {
                            intent.setClass(SuiServiceActivity.this, EaseOneYearActivity.class);
                        }
                        intent.putExtra("id", id);
                        intent.putExtra("beans", beans);
                        intent.putExtra("isActive", b);
                        intent.putExtra("name", jsonObject.getString("name"));
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
