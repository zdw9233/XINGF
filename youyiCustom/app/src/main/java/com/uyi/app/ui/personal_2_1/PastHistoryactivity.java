package com.uyi.app.ui.personal_2_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.healthinfo.HealthInfoActivity;
import com.uyi.app.ui.healthinfo.adapter.OutXiTonAdapter;
import com.uyi.app.ui.healthinfo.model.OutXiTon;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.UYIUtils;
import com.uyi.app.utils.ValidationUtils;
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
 * Created by ThinkPad on 2016/8/24.
 */
@ContentView(R.layout.fragment_update_past_history)
public class PastHistoryactivity extends BaseActivity {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.tv_two_date)
    private TextView tv_two_date;
    @ViewInject(R.id.register_info_xitongwaijiuyi)
    private EditText register_info_xitongwaijiuyi;
    @ViewInject(R.id.register_info_two)
    private LinearLayout register_info_two;
    @ViewInject(R.id.register_info_gerenjiwangbinshi)
    private EditText register_info_gerenjiwangbinshi;
    @ViewInject(R.id.register_info_chuanrangbingshi)
    private EditText register_info_chuanrangbingshi;
    @ViewInject(R.id.register_info_waishang)
    private EditText register_info_waishang;
    @ViewInject(R.id.register_info_shoushushi)
    private EditText register_info_shoushushi;
    @ViewInject(R.id.register_info_liucanshi)
    private EditText register_info_liucanshi;
    @ViewInject(R.id.register_info_yuejing)
    private EditText register_info_yuejing;
    @ViewInject(R.id.register_info_zhongdushi)
    private EditText register_info_zhongdushi;
    @ViewInject(R.id.register_info_shuxueshi)
    private EditText register_info_shuxueshi;
    @ViewInject(R.id.register_info_qitabinshi)
    private EditText register_info_qitabinshi;
    @ViewInject(R.id.register_info_jiazhubinshi)
    private EditText register_info_jiazhubinshi;
    @ViewInject(R.id.register_info_yufangjiezhonshi)
    private EditText register_info_yufangjiezhonshi;
    @ViewInject(R.id.register_info_xitonghuigu)
    private EditText register_info_xitonghuigu;
    @ViewInject(R.id.register_info_two_submit)
    private Button register_info_two_submit;
    @ViewInject(R.id.register_info_liucanshi_layout)
    private LinearLayout register_info_liucanshi_layout;
    @ViewInject(R.id.register_info_yuejing_layout)
    private LinearLayout register_info_yuejing_layout;
    @ViewInject(R.id.outRecyclerView)
    private RecyclerView outRecyclerView;
    @ViewInject(R.id.tv_two_choice_date)
    private TextView tv_two_choice_date;
    private OutXiTonAdapter mOutAdapter;
    private List<OutXiTon> externalSituationsList = new ArrayList<>();

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTab(false).showTitle(true).setTitle("既往史") .setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        Loading.bulid(activity, null).show();
        outRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        RequestManager.getObject(Constens.ACCOUNT_PAST_INFO, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.e("ONE", data.toString());
                    register_info_gerenjiwangbinshi.setText(JSONObjectUtils.getString(data, "medical"));
                    register_info_chuanrangbingshi.setText(JSONObjectUtils.getString(data, "infection"));
                    register_info_waishang.setText(JSONObjectUtils.getString(data, "trauma"));
                    register_info_shoushushi.setText(JSONObjectUtils.getString(data, "operation"));
                    register_info_liucanshi.setText(JSONObjectUtils.getString(data, "pregnancy"));
                    register_info_yuejing.setText(JSONObjectUtils.getString(data, "menstruation"));
                    register_info_zhongdushi.setText(JSONObjectUtils.getString(data, "allergic"));
                    register_info_shuxueshi.setText(JSONObjectUtils.getString(data, "blood"));
                    register_info_qitabinshi.setText(JSONObjectUtils.getString(data, "others"));
                    register_info_jiazhubinshi.setText(JSONObjectUtils.getString(data, "familyMedical"));
                    register_info_yufangjiezhonshi.setText(JSONObjectUtils.getString(data, "vaccinationHistory"));
                    register_info_xitonghuigu.setText(JSONObjectUtils.getString(data, "retrospection"));
                    if (ValidationUtils.equlse(UYIUtils.convertGender(HealthInfoActivity.garner),"男")) {
                        register_info_yuejing_layout.setVisibility(View.GONE);
                        register_info_liucanshi_layout.setVisibility(View.GONE);
                    }
                    JSONArray array = data.getJSONArray("externalSituations");
                    OutXiTon outXiTon;
                    for (int i =0;i < array.length();i++){
                        JSONObject temp = (JSONObject) array.get(i);
                        outXiTon = new OutXiTon();
                        outXiTon.content = temp.getString("content");
                        outXiTon.id = temp.getString("id");
                        outXiTon.treatmentTime = temp.getString("treatmentTime");
                        externalSituationsList.add(outXiTon);
                    }
                    outRecyclerView.setAdapter(mOutAdapter = new OutXiTonAdapter(activity, externalSituationsList, new OutXiTonAdapter.OnDeleteListener() {
                        @Override
                        public void onDelete(int position) {
                            OutXiTon bean = externalSituationsList.get(position);
                            if (bean.id != null) {
                                deleteOut(bean.id, position);
                            } else {
                                externalSituationsList.remove(position);
                                mOutAdapter.notifyItemRemoved(position);
                            }
                        }
                    }));
                    Loading.bulid(activity, null).dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Loading.bulid(activity, null).dismiss();
                }
            }
        });
//        OutHolder outHolder = new OutHolder();
//        outHolder.dateText = (TextView) @ViewInject(R.id.tv_two_date);
//        outHolder.msgText = (EditText) findViewById(R.id.register_info_xitongwaijiuyi);
//        register_info_two.setTag(outHolder);
//        outViews.add(register_info_two);
    }


    private void deleteOut(String id, int position) {
        deleteInfo(Integer.valueOf(id), 1, position);
    }

    private void deleteInfo(final int id, final int type, final int position) {
        new AlertDialog.Builder(activity).setTitle("删除提示").setMessage("确定删除该数据？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestManager.getObject(String.format(Locale.CHINA, Constens.DELETE_INFO, id, type), this, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        T.showShort(activity, "删除成功！");
                            externalSituationsList.remove(position);
                            mOutAdapter.notifyItemRemoved(position);
                    }
                });
            }
        }).setNegativeButton("取消", null).show();

    }


    @OnClick({
            R.id.tv_two_choice_date,
            R.id.tv_add_system_out,
           R.id. register_info_two_submit
           })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_two_choice_date://系统外就医时间选择
                startDatePicker(100, null);
                break;
            case R.id.tv_add_system_out://系统外就医时间选择
                if (checkOutBeforeIsFull()) {
                    addOneOutView();
                } else T.showShort(activity, "请完善就医情况");
                break;
            case R.id.register_info_two_submit://提交
                JSONObject object = new JSONObject();
                try {
                    object.put("medical",register_info_gerenjiwangbinshi.getText().toString());
                    object.put("infection",register_info_chuanrangbingshi.getText().toString());
                    object.put("trauma",register_info_waishang.getText().toString());
                    object.put("operation",register_info_shoushushi.getText().toString());
                    object.put("pregnancy",register_info_liucanshi.getText().toString());
                    object.put("menstruation",register_info_yuejing.getText().toString());
                    object.put("allergic",register_info_zhongdushi.getText().toString());
                    object.put("blood",register_info_shuxueshi.getText().toString());
                    object.put("others",register_info_qitabinshi.getText().toString());
                    object.put("familyMedical",register_info_jiazhubinshi.getText().toString());
                    object.put("vaccinationHistory",register_info_yufangjiezhonshi.getText().toString());
                    object.put("retrospection",register_info_xitonghuigu.getText().toString());
                    object.put("externalSituations",new JSONArray(JSON.toJSONString(externalSituationsList)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Loading.bulid(activity, null).show();
                RequestManager.postObject(Constens.ACCOUNT_PAST_INFO_UPDATE, this, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                    }
                }, new RequestErrorListener() {
                    @Override
                    public void requestError(VolleyError e) {
                        if (e.networkResponse!=null){
                            if (e.networkResponse.statusCode==200){
                                T.showShort(activity,"修改成功");
                                Loading.bulid(activity, null).dismiss();
                            }else {
                                T.showShort(activity,"修改失敗");
                                Loading.bulid(activity, null).dismiss();
                            }
                        }else {
                            T.showShort(activity,"修改成功");
                            Loading.bulid(activity, null).dismiss();
                        }
                    }
                });
                break;
        }
    }
    private boolean checkOutBeforeIsFull() {
            if (tv_two_date.getText().toString().equals("请选择就医时间") ||
                    TextUtils.isEmpty(register_info_xitongwaijiuyi.getText().toString().trim())) {
                return false;
            }
        return true;
    }
    private void addOneOutView() {
        OutXiTon bean = new OutXiTon();
        bean.treatmentTime = tv_two_date.getText().toString();
        bean.content = register_info_xitongwaijiuyi.getText().toString();
        externalSituationsList.add(bean);
        mOutAdapter.notifyItemInserted(externalSituationsList.size() - 1);
    }

    private void startDatePicker(int requestCode, String sDate) {
        Intent intent
                = new Intent(activity, DatePickerActivity.class);
//        intent.putExtra("currentDate", regester_info_xueguanfashengshijian.getText().toString().trim());
        intent.putExtra("sDate", sDate);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            if (data.hasExtra("date")) {
                tv_two_date.setText(data.getStringExtra("date"));
            }
        }
//
    }
    private class OutHolder {
        public TextView dateText;
        public EditText msgText;
    }
}
