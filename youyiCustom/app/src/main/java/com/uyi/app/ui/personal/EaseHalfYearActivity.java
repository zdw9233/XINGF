package com.uyi.app.ui.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * 安心半年包详情 Created by ThinkPad on 2016/7/2.
 */
@ContentView(R.layout.ease_half_year_service)
public class EaseHalfYearActivity extends BaseActivity implements DialogInterface.OnClickListener {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.anxin_pay)
    private TextView anxin_pay;
    private MessageConform conform;
    private boolean b;
    private int id;
    private String name;
    private int beans;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("安心服务").setTitleColor(getResources().getColor(R.color.blue));
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        b = getIntent().getBooleanExtra("isActive", false);
        beans = getIntent().getIntExtra("beans", 0);
        if(b == true){
            anxin_pay.setEnabled(true);
        }else{
            anxin_pay.setEnabled(false);
            anxin_pay.setText("暂未开放，敬请期待！");
        }
    }

    @OnClick(R.id.anxin_pay)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anxin_pay:
                new AlertDialog.Builder(this).setTitle("购买提示").setMessage(String.format(Locale.CHINA, "您即将购买由优医为你提供的%s,需要消耗您%d健康豆,并且下月生效！", name, beans)).setPositiveButton("购买", this).setNegativeButton("取消", this).show();
                break;
        }

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    public void requestBuyPackage(int id) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestManager.postObject(Constens.BUY_SERVICE_PACKAGE, this, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                T.showShort(EaseHalfYearActivity.this, "购买成功！");
                sendBroadcast(new Intent("com.uyi.beans"));
                onBackPressed();
            }
        }, new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {
                if (e.networkResponse != null) {
                    if (e.networkResponse.statusCode == 200) {
                        T.showShort(activity, "购买成功！");
                        sendBroadcast(new Intent("com.uyi.beans"));
                        onBackPressed();
                    } else {
                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                    }
                } else {
                    T.showShort(activity, "购买成功！");
                    sendBroadcast(new Intent("com.uyi.beans"));
                    onBackPressed();
                }
            }
        });

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                requestBuyPackage(id);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }
}
