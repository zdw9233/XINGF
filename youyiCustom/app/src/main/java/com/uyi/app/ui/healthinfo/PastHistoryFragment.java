package com.uyi.app.ui.healthinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.healthinfo.adapter.OutXiTonAdapter;
import com.uyi.app.ui.healthinfo.model.OutXiTon;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ThinkPad on 2016/8/24.
 */
public class PastHistoryFragment extends BaseFragment {
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
    private List<View> outViews;
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_update_past_history;
    }

    @Override
    protected void onInitLayoutAfter() {
        outRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        outViews = new ArrayList<>();
        RequestManager.getObject(Constens.ACCOUNT_PAST_INFO, getActivity(), new Response.Listener<JSONObject>() {
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
//                    if (ValidationUtils.equlse(UYIUtils.convertGender(data.getString("gender")), "男")) {
//                        register_info_yuejing_layout.setVisibility(View.GONE);
//                        register_info_liucanshi_layout.setVisibility(View.GONE);
//                    }
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
                    outRecyclerView.setAdapter(mOutAdapter = new OutXiTonAdapter(getActivity(), externalSituationsList, new OutXiTonAdapter.OnDeleteListener() {
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
                } catch (Exception e) {
                    e.printStackTrace();
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
        new AlertDialog.Builder(getActivity()).setTitle("删除提示").setMessage("确定删除该数据？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestManager.getObject(String.format(Locale.CHINA, Constens.DELETE_INFO, id, type), this, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        T.showShort(getActivity(), "删除成功！");
                            externalSituationsList.remove(position);
                            mOutAdapter.notifyItemRemoved(position);
                    }
                });
            }
        }).setNegativeButton("取消", null).show();

    }


    @OnClick({
            R.id.tv_two_choice_date
           })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_two_choice_date://系统外就医时间选择
                startDatePicker(100, null);
                break;
        }
    }
    private void startDatePicker(int requestCode, String sDate) {
        Intent intent
                = new Intent(getActivity(), DatePickerActivity.class);
//        intent.putExtra("currentDate", regester_info_xueguanfashengshijian.getText().toString().trim());
        intent.putExtra("sDate", sDate);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            if (data.hasExtra("date")) {
                OutHolder outHolder = (OutHolder) outViews.get(1).getTag();
                outHolder.dateText.setText(data.getStringExtra("date"));
            }
        }
//
    }
    private class OutHolder {
        public TextView dateText;
        public EditText msgText;
    }
}
