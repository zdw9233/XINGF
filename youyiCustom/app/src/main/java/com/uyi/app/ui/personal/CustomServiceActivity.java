package com.uyi.app.ui.personal;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.adapter.CustomServiceAdapter;
import com.uyi.app.ui.personal.model.ServiceItem;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 定制服务 Created by Leeii on 2016/7/16.
 */
@ContentView(R.layout.activity_custom_service)
public class CustomServiceActivity extends BaseActivity implements CustomServiceAdapter.OnBuyServiceCallBack {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    private CustomServiceAdapter mAdapter;
    private List<ServiceItem> serviceItems;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("定制服务").setTitleColor(getResources().getColor(R.color.blue));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        serviceItems = new ArrayList<>();
        mAdapter = new CustomServiceAdapter(CustomServiceActivity.this, serviceItems);
        mAdapter.setOnBuyServiceCallBack(CustomServiceActivity.this);
        recyclerView.setAdapter(mAdapter);
        requestCustomService();
    }

    private void requestCustomService() {
        Loading.bulid(this, null).show();
        RequestManager.getArray(Constens.GET_CUSTOM_SERVICE, this, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                L.e(jsonObject.toString());
                Loading.bulid(CustomServiceActivity.this, null).dismiss();
                List<ServiceItem> serviceItems = JSON.parseArray(jsonObject.toString(), ServiceItem.class);
                CustomServiceActivity.this.serviceItems.addAll(serviceItems);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void buyIt(final int id, final int count, String name, int beans, String unit) {
        new AlertDialog.Builder(this).setTitle("购买提示").setMessage(String.format(Locale.CHINA, "您即将购买由优医为你提供的%s * %d%s,需要消耗您%d健康豆", name, count, unit, beans * count)).setPositiveButton("购买", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestBuyCustomService(id, count);
            }
        }).setNegativeButton("取消", null).show();
    }

    private void requestBuyCustomService(int id, int count) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", id);
            object.put("count", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestManager.postObject(Constens.BUY_CUSTOM_SERVICE, this, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                T.showShort(CustomServiceActivity.this, "购买成功！");
            }
        }, new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {
                if (e.networkResponse != null) {
                    if (e.networkResponse.statusCode == 200) {
                        T.showShort(activity, "购买成功！");
                    } else {
                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                    }
                } else {
                    T.showShort(activity, "购买成功！");
                }
            }
        });
    }
}
