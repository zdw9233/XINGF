package com.uyi.app.ui.health;

import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/6/29.
 */
@ContentView(R.layout.diet_plan)
public class DietPlanActivity extends BaseActivity {
    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.diet_plan_details) private TextView diet_plan_details;
    private UserInfo userInfo;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("饮食计划").setTitleColor(getResources().getColor(R.color.blue));
        userInfo = UserInfoManager.getLoginUserInfo(activity);
        RequestManager.getObject(String.format(Constens.LIFE_DEIT, UserInfoManager.getLoginUserInfo(this).userId), activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    System.out.println(data.toString());

                    if (!data.getString("eatinghabiit").equals("null")) {
                        diet_plan_details.setText(data.getString("eatinghabiit"));
                    } else {
                        diet_plan_details.setText("你没有任何资料！");
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
}
