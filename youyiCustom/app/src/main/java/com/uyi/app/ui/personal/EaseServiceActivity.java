package com.uyi.app.ui.personal;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by ThinkPad on 2016/7/2.
 */
@ContentView(R.layout.ease_of_service)
public class EaseServiceActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.anxin_one)
    private RelativeLayout anxin_one;
    @ViewInject(R.id.anxin_two)
    private RelativeLayout anxin_two;
    @ViewInject(R.id.anxin_three)
    private RelativeLayout anxin_three;
    private final String TYPE_AX_3_MONTH = "AX1"; //  糖心服务3个月
    private final String TYPE_AX_6_MONTH = "AX2";   //糖心服务半年
    private final String TYPE_AX_12_MONTH = "AX3";  //糖心服务一年

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("安心服务").setTitleColor(getResources().getColor(R.color.blue));
    }

    @OnClick({R.id.anxin_one, R.id.anxin_two, R.id.anxin_three})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anxin_one:
                requestServicePackage(TYPE_AX_3_MONTH);
                break;
            case R.id.anxin_two:
                requestServicePackage(TYPE_AX_6_MONTH);
                break;
            case R.id.anxin_three:
                requestServicePackage(TYPE_AX_12_MONTH);
                break;

        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    private void requestServicePackage(final String type) {
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_SERVICE_PACKAGE, type), EaseServiceActivity.this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                L.e(jsonObject.toString());
                try {
                    if (jsonObject.has("id")) {
                        int id = jsonObject.getInt("id");
                        int beans = jsonObject.getInt("beans");
                        Intent intent = new Intent();
                        if (TYPE_AX_3_MONTH.equals(type)) {
                            intent.setClass(EaseServiceActivity.this, EasePeaceExperienceActivity.class);
                        } else if (TYPE_AX_6_MONTH.equals(type)) {
                            intent.setClass(EaseServiceActivity.this, EaseHalfYearActivity.class);
                        } else {
                            intent.setClass(EaseServiceActivity.this, EaseOneYearActivity.class);
                        }
                        intent.putExtra("id", id);
                        intent.putExtra("beans", beans);
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
