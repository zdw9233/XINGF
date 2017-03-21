package com.uyi.app.ui.health;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
@ContentView(R.layout.activity_new_personal_program_new)
public class GreatePersonalProgramActivity extends BaseActivity implements AbstractSpinerAdapter.IOnItemSelectListener{
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.doctor)
    private TextView doctor;
    @ViewInject(R.id.lururen)
    private TextView lururen;
    @ViewInject(R.id.yonhuxingming)
    private TextView yonhuxingming;
    @ViewInject(R.id.lurushijian)
    private TextView jklurushijian;

    @ViewInject(R.id.layout_icvd)
    private LinearLayout layout_icvd;
    @ViewInject(R.id.layout_ASCVD)
    private LinearLayout layout_ASCVD;
    @ViewInject(R.id.layout_xy)
    private LinearLayout layout_xy;
    @ViewInject(R.id.layout_xt)
    private LinearLayout layout_xt;
    @ViewInject(R.id.layout1)
    private LinearLayout layout1;
    @ViewInject(R.id.layout2)
    private LinearLayout layout2;
    @ViewInject(R.id.layout_zdgz)
    private LinearLayout layout_zdgz;
    @ViewInject(R.id.layout_jkjy)
    private LinearLayout layout_jkjy;
    @ViewInject(R.id.sclrr)
    private TextView sclrr;
    @ViewInject(R.id.sclusj)
    private TextView sclusj;
    @ViewInject(R.id.xygljy)
    private EditText xygljy;
    @ViewInject(R.id.xtgljy)
    private EditText xtgljy;
    @ViewInject(R.id.xgzjcjgjjy)
    private EditText xgzjcjgjjy;
    @ViewInject(R.id.zlfajy)
    private EditText zlfajy;
    @ViewInject(R.id.ysjyjjjtx)
    private EditText ysjyjjjtx;
    @ViewInject(R.id.ydjy)
    private EditText ydjy;
    @ViewInject(R.id.grxgjy)
    private EditText grxgjy;
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";
    String s5 = "";
    String s6 = "";
    String s7 = "";
    @ViewInject(R.id.sl1)
    private Button sl1;
    @ViewInject(R.id.sl2)
    private Button sl2;
    @ViewInject(R.id.sl3)
    private Button sl3;
    @ViewInject(R.id.sl4)
    private Button sl4;
    @ViewInject(R.id.sl5)
    private Button sl5;
    @ViewInject(R.id.sl6)
    private Button sl6;
    @ViewInject(R.id.sl7)
    private Button sl7;
    @ViewInject(R.id.submit)
    private Button submit;
    String id = "";
    private int selectedType = 1;
    private SpinerPopWindow spinerPopWindow;
    @ViewInject(R.id.wxys_chose)
    private TextView wxys_chose;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("新建个人方案").setTitleColor(getResources().getColor(R.color.blue));
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setItemListener(this);
        spinerPopWindow.refreshData(wxfa_str, 1);
        initData();
        getExample();
    }

    private void getExample() {
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.GET_PERSONAL_PROGRAM_EXAMPLE, selectedType + ""),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        Loading.bulid(activity, null).dismiss();
                        try {
                            s1 = data.getString("bloodPressureManagementAdvice");
                            s2 = data.getString("bloodSugarManagementAdvice");
                            s3 = data.getString("resultsAndSuggestions");
                            s4 = data.getString("medicalAdvice");
                            s5 = data.getString("dietaryAdviceToRemindAndTaboos");
                            s6 = data.getString("exerciseAdvice");
                            s7 = data.getString("personalHabitsSuggest");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void initData() {
        layout_xy.setVisibility(View.GONE);
        layout_xt.setVisibility(View.GONE);
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
    }

    @OnClick({R.id.wxys_chose, R.id.submit, R.id.sl1, R.id.sl2, R.id.sl3, R.id.sl4, R.id.sl5, R.id.sl6, R.id.sl7})
    public void onClick(View v) {
        if (v.getId() == R.id.wxys_chose) {
            spinerPopWindow.setWidth(wxys_chose.getWidth());
            spinerPopWindow.refreshData(wxfa_str, 0);
            spinerPopWindow.showAsDropDown(wxys_chose);

        } else if (v.getId() == R.id.sl1) {
            new AlertDialog.Builder(GreatePersonalProgramActivity.this).setTitle("示例").setMessage(s1).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    xygljy.setText(s1);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl2) {
            new AlertDialog.Builder(GreatePersonalProgramActivity.this).setTitle("示例").setMessage(s2).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    xtgljy.setText(s2);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl3) {
            new AlertDialog.Builder(GreatePersonalProgramActivity.this).setTitle("示例").setMessage(s3).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    xgzjcjgjjy.setText(s3);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl4) {
            new AlertDialog.Builder(GreatePersonalProgramActivity.this).setTitle("示例").setMessage(s4).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    zlfajy.setText(s4);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl5) {
            new AlertDialog.Builder(GreatePersonalProgramActivity.this).setTitle("示例").setMessage(s5).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ysjyjjjtx.setText(s5);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl6) {
            new AlertDialog.Builder(GreatePersonalProgramActivity.this).setTitle("示例").setMessage(s6).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ydjy.setText(s6);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.sl7) {
            new AlertDialog.Builder(GreatePersonalProgramActivity.this).setTitle("示例").setMessage(s7).setPositiveButton("使用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    grxgjy.setText(s7);
                }
            }).setNegativeButton("取消", null).show();
        } else if (v.getId() == R.id.submit) {
            try {
                JSONObject personalHealthManagementTemplateJson = new JSONObject();
                personalHealthManagementTemplateJson.put("bloodPressureManagementAdvice", xygljy.getText().toString());
                personalHealthManagementTemplateJson.put("bloodSugarManagementAdvice", xtgljy.getText().toString());
                personalHealthManagementTemplateJson.put("resultsAndSuggestions", xgzjcjgjjy.getText().toString());
                personalHealthManagementTemplateJson.put("medicalAdvice", zlfajy.getText().toString());
                personalHealthManagementTemplateJson.put("dietaryAdviceToRemindAndTaboos", ysjyjjjtx.getText().toString());
                personalHealthManagementTemplateJson.put("exerciseAdvice", ydjy.getText().toString());
                personalHealthManagementTemplateJson.put("personalHabitsSuggest", grxgjy.getText().toString());
                JSONObject params = new JSONObject();
                params.put("customerId", FragmentHealthListManager.customer + "");
                params.put("personalHealthManagementTemplateJson", personalHealthManagementTemplateJson);
                RequestManager.postObject(Constens.CREACT_PERSONAL_PROGRAM, activity, params, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                        System.out.print("+++++++++++++++++++++" + data.toString());
                        T.showShort(activity, "新建成功!");
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new RequestErrorListener() {
                    public void requestError(VolleyError e) {
                        if (e.networkResponse != null) {
                            T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                        } else {
                            T.showShort(activity, "新建成功!");
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
