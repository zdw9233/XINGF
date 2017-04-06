package com.uyi.app.ui.health;

import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/9/18.
 */
@ContentView(R.layout.activity_three_top_update)
public class ThreeTopDetailsActivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.doctor)
    private TextView doctor;
    @ViewInject(R.id.lururen)
    private TextView lururen;
    @ViewInject(R.id.yonhuxingming)
    private TextView yonhuxingming;
    @ViewInject(R.id.lurushijian)
    private TextView lurushijian;

    @ViewInject(R.id.fyzysx)
    private TextView fyzysx;
    @ViewInject(R.id.yyzd)
    private TextView yyzd;
    @ViewInject(R.id.xxyydzd)
    private TextView xxyydzd;
    @ViewInject(R.id.shzzysx)
    private TextView shzzysx;
    String id = "";

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(false).setTitle("健康管理计划详情").setTitleColor(getResources().getColor(R.color.blue));
        id = getIntent().getStringExtra("id");
        RequestManager.getObject(String.format(Constens.GET_PERSONAL_PROGRAM_THREE_TOP_DETAILS,id),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        L.e(data.toString());
                        try {
                            if(data.has("attendingDoctor")){
                                doctor.setText(data.getString("attendingDoctor"));
                            }else{
                                doctor.setText("");
                            }
                            lururen.setText(data.getString("doctorName"));
                            yonhuxingming.setText(data.getString("customerName"));
                            lurushijian.setText(data.getString("updateTime"));
                            if(data.has("medicationAttention"))
                            fyzysx.setText(data.getString("medicationAttention"));
                            if(data.has("nutritionGuidance"))
                            yyzd.setText(data.getString("nutritionGuidance"));
                            if(data.has("restAndExerciseGuidance"))
                            xxyydzd.setText(data.getString("restAndExerciseGuidance"));
                            if(data.has("lifeInAttentions"))
                            shzzysx.setText(data.getString("lifeInAttentions"));

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
