package com.uyi.app.ui.personal.newService;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
@ContentView(R.layout.activity_sugar_of_service)
public class SugarServiceActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.anxin_jichu)
    private TextView anxin_jichu;
    @ViewInject(R.id.anxin_jingxi)
    private TextView anxin_jingxi;
    @ViewInject(R.id.jichu_1)
    private LinearLayout jichu_1;
    @ViewInject(R.id.jinxi_1)
    private LinearLayout jinxi_1;
    @ViewInject(R.id.jichu_2)
    private LinearLayout jichu_2;
    @ViewInject(R.id.jinxi_2)
    private LinearLayout jinxi_2;
    @ViewInject(R.id.jichu_3)
    private LinearLayout jichu_3;
    @ViewInject(R.id.jinxi_3)
    private LinearLayout jinxi_3;
    @ViewInject(R.id.anxin_jichu_line)
    private TextView anxin_jichu_line;
    @ViewInject(R.id.anxin_jingxi_line)
    private TextView anxin_jingxi_line;
    @ViewInject(R.id.pay)
    private TextView pay;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("糖心服务").setTitleColor(getResources().getColor(R.color.blue));
        initData();
    }
    private void initData() {
        jinxi_1.setVisibility(View.GONE);
        jinxi_2.setVisibility(View.GONE);
        jinxi_3.setVisibility(View.GONE);
        anxin_jingxi_line.setVisibility(View.INVISIBLE);
    }
    @OnClick({R.id.anxin_jichu, R.id.anxin_jingxi, R.id.pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anxin_jichu:
                anxin_jichu.setTextColor(getResources().getColor(R.color.blue));
                anxin_jingxi.setTextColor(getResources().getColor(R.color.footer_somber_more));
                jichu_1.setVisibility(View.VISIBLE);
                jichu_2.setVisibility(View.VISIBLE);
                jichu_3.setVisibility(View.VISIBLE);
                anxin_jichu_line.setVisibility(View.VISIBLE);
                jinxi_1.setVisibility(View.GONE);
                jinxi_2.setVisibility(View.GONE);
                jinxi_3.setVisibility(View.GONE);
                anxin_jingxi_line.setVisibility(View.INVISIBLE);
                break;
            case R.id.anxin_jingxi:
                anxin_jichu.setTextColor(getResources().getColor(R.color.footer_somber_more));
                anxin_jingxi.setTextColor(getResources().getColor(R.color.blue));
                jichu_1.setVisibility(View.GONE);
                jichu_2.setVisibility(View.GONE);
                jichu_3.setVisibility(View.GONE);
                anxin_jichu_line.setVisibility(View.INVISIBLE);
                jinxi_1.setVisibility(View.VISIBLE);
                jinxi_2.setVisibility(View.VISIBLE);
                jinxi_3.setVisibility(View.VISIBLE);
                anxin_jingxi_line.setVisibility(View.VISIBLE);
                break;
            case R.id.pay:
                break;

        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    private void requestServicePackage(final String type) {
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_SERVICE_PACKAGE, type), SugarServiceActivity.this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                L.e(jsonObject.toString());
                try {
                    if (jsonObject.has("id")) {
                        int id = jsonObject.getInt("id");
                        int beans = jsonObject.getInt("beans");
                        boolean b =  jsonObject.getBoolean("isActive");
                        Intent intent = new Intent();
//                        if (TYPE_TX_3_MONTH.equals(type)) {
//                            intent.setClass(SugarServiceActivity.this, SugarPeaceExperienceActivity.class);
//                        } else if (TYPE_TX_6_MONTH.equals(type)) {
//                            intent.setClass(SugarServiceActivity.this, SugarHalfYearActivity.class);
//                        } else {
//                            intent.setClass(SugarServiceActivity.this, SugarOneYearActivity.class);
//                        }
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
