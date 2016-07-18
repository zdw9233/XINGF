package com.uyi.app.ui.health;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/6/29.
 */
@ContentView(R.layout.life_style)
public class LifeStyleActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.life_style_details) private EditText life_style_details;
    @ViewInject(R.id.life_style_submit) private Button life_style_submit;
    private String diet = "";
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("生活方式").setTitleColor(getResources().getColor(R.color.blue));
        RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_LIFE_DIET,FragmentHealthListManager.customer+""),this, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        System.out.print("_________________________________"+data.toString());
                        try {
                            life_style_details.setText( data.getString("liefstyle"));
                            diet = data.getString("eatinghabiit");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
    @OnClick(R.id.life_style_submit)//更新
    public void onclick(View v){
        if(v.getId() == R.id.life_style_submit) {
            if (life_style_details.getText().toString().equals("")) {
                T.showShort(activity, "请输入你要更新的内容!");
            } else {
                try {
                    JSONObject params = new JSONObject();
                    params.put("id", FragmentHealthListManager.customer);
                    params.put("liefstyle", life_style_details.getText().toString());
                    params.put("eatinghabiit", diet);
                    RequestManager.postObject(String.format(Constens.DOCTOR_HEALTH_LIFE_DIET_UPDATA, FragmentHealthListManager.customer + ""), activity, params, new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject data) {
                            System.out.print("+++++++++++++++++++++" + data.toString());
                            T.showShort(activity, "提交成功!");
                            finish();
                        }
                    }, new RequestErrorListener() {
                        public void requestError(VolleyError e) {
//                    if(e.networkResponse != null){
//                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
//                    }else{
//                        T.showShort(activity, "提交成功!");
//                        onRefresh();
//                    }
                            T.showShort(activity, "提交失败!");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
}
