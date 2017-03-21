package com.uyi.app.ui.home.fragment;

import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/9/18.
 */
@ContentView(R.layout.activity_zixun_details)
public class ZiXunDetailsActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.message_details_title)
    private TextView message_details_title;
    @ViewInject(R.id.message_details_time)
    private TextView message_details_time;
    @ViewInject(R.id.message_details_content)
    private WebView message_details_content;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(false).setTitle("健康资讯详情").setTitleColor(getResources().getColor(R.color.blue));
        String id = getIntent().getStringExtra("id");
        Loading.bulid(activity, null).dismiss();
        RequestManager.getObject(String.format(Constens.COMMON_ARTUCLE_DETAILS, id), activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    Loading.bulid(activity, null).dismiss();
                    message_details_title.setText(data.getString("title"));
                    message_details_time.setText(data.getString("lastUpdateTime"));
                    message_details_content.loadDataWithBaseURL(null, "<html><head><style>body{width:100%};img{width:95%}</style></head></body>" + data.getString("content") + "</body></html>", "text/html", "UTF-8", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
}
