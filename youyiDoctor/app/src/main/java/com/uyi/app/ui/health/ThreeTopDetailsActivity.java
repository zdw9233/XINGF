package com.uyi.app.ui.health;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2016/9/18.
 */
@ContentView(R.layout.activity_three_top_update)
public class ThreeTopDetailsActivity extends BaseActivity implements AbstractSpinerAdapter.IOnItemSelectListener {
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
    private EditText fyzysx;
    @ViewInject(R.id.yyzd)
    private EditText yyzd;
    @ViewInject(R.id.xxyydzd)
    private EditText xxyydzd;
    @ViewInject(R.id.shzzysx)
    private EditText shzzysx;
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";
    @ViewInject(R.id.sl1)
    private Button sl1;
    @ViewInject(R.id.sl2)
    private Button sl2;
    @ViewInject(R.id.sl3)
    private Button sl3;
    @ViewInject(R.id.sl4)
    private Button sl4;
    @ViewInject(R.id.submit)
    private Button submit;
    String id = "";
    String customerId = "";
    @ViewInject(R.id.wxys_chose)
    private TextView wxys_chose;
    private int selectedType = 1;
    private SpinerPopWindow spinerPopWindow;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("三甲方案详情").setTitleColor(getResources().getColor(R.color.blue));
        id = getIntent().getStringExtra("id");
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setItemListener(this);
        spinerPopWindow.refreshData(wxfa_str, 1);
        getExample();
        RequestManager.getObject(String.format(Constens.GET_PERSONAL_PROGRAM_THREE_TOP_DETAILS, id),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        try {
                            customerId = data.getString("customerId");
                            doctor.setText(data.getString("attendingDoctor"));
                            lururen.setText(data.getString("doctorName"));
                            yonhuxingming.setText(data.getString("customerName"));
                            lurushijian.setText(data.getString("updateTime"));
                            if (data.has("medicationAttention"))
                                fyzysx.setText(data.getString("medicationAttention"));
                            if (data.has("nutritionGuidance"))
                                yyzd.setText(data.getString("nutritionGuidance"));
                            if (data.has("restAndExerciseGuidance"))
                                xxyydzd.setText(data.getString("restAndExerciseGuidance"));
                            if (data.has("lifeInAttentions"))
                                shzzysx.setText(data.getString("lifeInAttentions"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    private void getExample() {
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.GET_PERSONAL_PROGRAM_THREE_TOP, selectedType + ""),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        Loading.bulid(activity, null).dismiss();
                        try {

                            s1 = data.getString("medicationAttention");
                            s2 = data.getString("nutritionGuidance");
                            s3 = data.getString("restAndExerciseGuidance");
                            s4 = data.getString("lifeInAttentions");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @OnClick({R.id.wxys_chose, R.id.submit, R.id.sl1, R.id.sl2, R.id.sl3, R.id.sl4, R.id.sl5, R.id.sl6, R.id.sl7})
    public void onClick(View v) {
        if (v.getId() == R.id.wxys_chose) {
            spinerPopWindow.setWidth(wxys_chose.getWidth());
            spinerPopWindow.refreshData(wxfa_str, 0);
            spinerPopWindow.showAsDropDown(wxys_chose);
        } else if (v.getId() == R.id.sl1) {
            new AlertDialog.Builder(ThreeTopDetailsActivity.this).setTitle("示例").setMessage(s1).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fyzysx.setText(s1);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl2) {
            new AlertDialog.Builder(ThreeTopDetailsActivity.this).setTitle("示例").setMessage(s2).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    yyzd.setText(s2);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl3) {
            new AlertDialog.Builder(ThreeTopDetailsActivity.this).setTitle("示例").setMessage(s3).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    xxyydzd.setText(s3);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl4) {
            new AlertDialog.Builder(ThreeTopDetailsActivity.this).setTitle("示例").setMessage(s4).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    shzzysx.setText(s4);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.submit) {
            try {
                JSONObject personalHealthManagementTemplateJson = new JSONObject();
                personalHealthManagementTemplateJson.put("id", id);
                personalHealthManagementTemplateJson.put("customerId", customerId);
                personalHealthManagementTemplateJson.put("status", "UNREVIEWED");
                personalHealthManagementTemplateJson.put("medicationAttention", fyzysx.getText().toString());
                personalHealthManagementTemplateJson.put("nutritionGuidance", yyzd.getText().toString());
                personalHealthManagementTemplateJson.put("restAndExerciseGuidance", xxyydzd.getText().toString());
                personalHealthManagementTemplateJson.put("lifeInAttentions", shzzysx.getText().toString());
//                JSONObject params = new JSONObject();

//                params.put("personalHealthManagementTemplateJson", personalHealthManagementTemplateJson);
                RequestManager.postObject(Constens.CREACT_THREE_TOP_PROGRAM, activity, personalHealthManagementTemplateJson, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                        System.out.print("+++++++++++++++++++++" + data.toString());
                        T.showShort(activity, "提交成功!");
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new RequestErrorListener() {
                    public void requestError(VolleyError e) {
                        if (e.networkResponse != null) {
                            T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                        } else {
                            T.showShort(activity, "提交成功!");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onItemClick(int pos) {
        wxys_chose.setText(wxfa_str.get(pos));
        selectedType = wxfa_type[pos];
        getExample();
    }

    List<String> wxfa_str = new ArrayList<String>() {
        public List<String> $() {
            add("默认");
            add("高血压");
            add("糖尿病");
            add("高脂血症");
            add("饮食与营养");
            add("缺乏体力活动");
            add("肥胖");
            add("饮酒");
            add("吸烟");
            add("房颤");
            add("高尿酸/痛风");
            return this;
        }
    }.$();
    int[] wxfa_type = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
}
