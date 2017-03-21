package com.uyi.app.ui.personal_2_1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.common.model.Health;
import com.uyi.app.ui.common.model.MedicineUnit;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.healthinfo.adapter.MedicineAdapter;
import com.uyi.app.ui.healthinfo.model.Mecicine;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.DensityUtils;
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
 *MedicineInfoFragment  Created by ThinkPad on 2016/8/24.
 */
@ContentView(R.layout.fragment_update_medicine_info)
public class MedicineInfoActivity extends BaseActivity implements PopupWindow.OnDismissListener ,TextWatcher,RecyclerView.ItemClickListener{

    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.spm) private TextView spm;
    @ViewInject(R.id.hxm) private TextView hxm;
    @ViewInject(R.id.tv_start_time)
    private TextView tv_start_time;
    @ViewInject(R.id.tv_end_time)
    private TextView tv_end_time;
    private PopupWindow mPopupWindow;
    private RecyclerView mPopupWindowRecyclerView;
    private EditText mPopupWindowSearch;
    private int type =1 ;
    private String spName;
    private String hxName;
    private MedicineAdapter mNameAdapter;

    List<MedicineUnit> medicineUnits;

    @ViewInject(R.id.ywRecyclerView)
    private RecyclerView ywRecyclerView;
    @ViewInject(R.id.yypd)
    private Spinner yypd;
    @ViewInject(R.id.timeUnit)
    private Spinner timeUnit;
    @ViewInject(R.id.valueUnit)
    private Spinner valueUnit;
    @ViewInject(R.id.regester_three_danciyonyaoliang)
    private EditText regester_three_danciyonyaoliang;
    @ViewInject(R.id.register_info_guomingshi)
    private EditText register_info_guomingshi;
    @ViewInject(R.id.register_info_chengyingdeyaowu)
    private EditText register_info_chengyingdeyaowu;
    private List<Health.HealthInfoBean.MedicationUsingSituationsBean> medicationUsingSituationsList;
    private com.uyi.app.ui.common.adapter.MedicineAdapter mMedicineAdapter;
    private int customerId;


    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTab(false).showTitle(true).setTitle("药物情况") .setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        medicationUsingSituationsList = new ArrayList<>();
        View contentView = activity.getLayoutInflater().inflate(R.layout.layout_medicine_name_popup,null);
        mPopupWindow = new PopupWindow(contentView, DensityUtils.dp2px(activity,165),-2);
        mPopupWindowRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        mPopupWindowSearch = (EditText) contentView.findViewById(R.id.search_ywm);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(this);
        mPopupWindowSearch.addTextChangedListener(this);
        mPopupWindowRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mPopupWindowRecyclerView.setAdapter((mNameAdapter= new MedicineAdapter(activity)));
        mNameAdapter.setItemClickListener(this);

        ywRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        Loading.bulid(activity, null).show();
        RequestManager.getArray(Constens.GET_MEDICINE_UNIT, this, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                L.e(jsonArray.toString());
               medicineUnits = JSON.parseArray(jsonArray.toString(), MedicineUnit.class);
                valueUnit.setAdapter(new ArrayAdapter<>(activity, R.layout.layout_spinner_item, R.id.textView2, toStringArray(medicineUnits)));
            }
        });
        RequestManager.getObject(Constens.ACCOUNT_MEDISINE_INFO, this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                L.e(jsonObject.toString());
                try {
                    customerId = jsonObject.getInt("customerId");

                    String historyOfAllergy = jsonObject.getString("historyOfAllergy");
                    String drugAddiction = jsonObject.getString("drugAddiction");
                    String medicationUsingSituations1 = jsonObject.getString("medicationUsingSituations");
                    List<Health.HealthInfoBean.MedicationUsingSituationsBean> medicationUsingSituations = JSON.parseArray(medicationUsingSituations1, Health.HealthInfoBean.MedicationUsingSituationsBean.class);
                     if (medicationUsingSituations!=null)
                    medicationUsingSituationsList.addAll(medicationUsingSituations);
                    register_info_chengyingdeyaowu.setText("null".equals(drugAddiction)?"":drugAddiction);
                    register_info_guomingshi.setText("null".equals(historyOfAllergy)?"":historyOfAllergy);

                    ywRecyclerView.setAdapter(mMedicineAdapter = new com.uyi.app.ui.common.adapter.MedicineAdapter(activity, medicationUsingSituationsList, new com.uyi.app.ui.common.adapter.MedicineAdapter.OnItemViewListener() {
                        @Override
                        public void onDelete(int position) {
                            Health.HealthInfoBean.MedicationUsingSituationsBean bean = medicationUsingSituationsList.get(position);
                            if (bean.id != null) {
                                deleteMedicine(bean.id, position);
                            } else {
                                medicationUsingSituationsList.remove(position);
                                mMedicineAdapter.notifyItemRemoved(position);
                            }
                        }

                        @Override
                        public void onChoiceEndTime(int position, String sDate) {
                            startDatePicker(300+position, sDate);
                        }
                    }));
                    Loading.bulid(activity, null).dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Loading.bulid(activity, null).dismiss();
                }
            }
        });

    }
    private void deleteMedicine(String id, int position) {
        deleteInfo(Integer.valueOf(id), 0, position);
    }
    private void deleteInfo(final int id, final int type, final int position) {
        new AlertDialog.Builder(activity).setTitle("删除提示").setMessage("确定删除该数据？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestManager.getObject(String.format(Locale.CHINA, Constens.DELETE_INFO, id, type), this, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        T.showShort(activity, "删除成功！");
                        if (type == 0) {
                            medicationUsingSituationsList.remove(position);
                            mMedicineAdapter.notifyItemRemoved(position);
                        }
                    }
                });
            }
        }).setNegativeButton("取消", null).show();

    }
    private String[] toStringArray(List<MedicineUnit> strings) {
        String[] strings1 = new String[strings.size()];
        int i = 0;
        for (MedicineUnit s : strings) {
            strings1[i] = s.text;
            i++;
        }
        return strings1;
    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @OnClick({
            R.id.reset,
            R.id.spm,
            R.id.hxm,
            R.id.layout_start_time,
            R.id.layout_end_time,
            R.id.addmedicationsituation,
            R.id.register_info_three_submit
    })
    public void onClick(View v){
        switch (v.getId()){
            case R.id.reset:
                spm.setText("");
                hxm.setText("");
                spName = "";
                hxName = "";
                tv_start_time.setText("");
                tv_end_time.setText("");
                break;
            case R.id.spm:
                type =1;
                mPopupWindowSearch.setText("");
                mPopupWindow.showAsDropDown(spm);
                onTextChanged("",0,0,0);
                mNameAdapter.reset();
                break;
            case R.id.hxm:
                type =2;
                mPopupWindowSearch.setText("");
                mPopupWindow.showAsDropDown(hxm);
                onTextChanged("",0,0,0);
                mNameAdapter.reset();
                break;
            case R.id.layout_start_time:
                startDatePicker(200, null);
                break;
            case R.id.layout_end_time:
                String sDate = tv_start_time.getText().toString().trim();
                if (sDate.equals("开始服药日期")) sDate = null;
                startDatePicker(299, sDate);
                break;
            case R.id.addmedicationsituation:
                if (checkYYBeforeIsFull()) {
                    RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_MEDICINE_INFO, spm.getText().toString(), hxm.getText().toString()), this, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                int id  = jsonObject.getInt("id");
                                String name  = jsonObject.getString("name");
                                addOneMedicineView(id,name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else T.showShort(activity, "请完善用药信息再试");
                break;
            case R.id.register_info_three_submit:
                JSONObject object = new JSONObject();
                try {object.put("customerId",customerId);
                    object.put("historyOfAllergy",register_info_guomingshi.getText().toString());
                    object.put("drugAddiction",register_info_chengyingdeyaowu.getText().toString());

                    object.put("medicationUsingSituations",new JSONArray(JSON.toJSONString(medicationUsingSituationsList)));
//                    List<Health.HealthInfoBean.MedicationUsingSituationsBean> situationsBeen1 = new ArrayList<>();
//                    for (Health.HealthInfoBean.MedicationUsingSituationsBean bean : medicationUsingSituationsList) {
//                        if (bean.id == null) situationsBeen1.add(bean);
//                    }
//                    object.put("medicationUsingSituations",situationsBeen1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                L.e(object.toString());
                Loading.bulid(activity, null).show();
                RequestManager.postObject(Constens.ACCOUNT_MEDISINE_INFO_UPDATE, this, object, new Response.Listener<JSONObject>() {
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
    private boolean checkYYBeforeIsFull() {
            if (TextUtils.isEmpty(tv_start_time.getText().toString().trim()) ||
                    TextUtils.isEmpty(spm.getText().toString()) ||
                    TextUtils.isEmpty(hxm.getText().toString()) ||
                    "用药频度".equals(yypd.getSelectedItem()) ||
                    TextUtils.isEmpty(regester_three_danciyonyaoliang.getText().toString().trim())) {
                return false;
            }
        return true;
    }

    private void addOneMedicineView(int id, String name) {
        Health.HealthInfoBean.MedicationUsingSituationsBean bean = new Health.HealthInfoBean.MedicationUsingSituationsBean();
        bean.endTime = tv_end_time.getText().toString();
        bean.frequencyUnit = (String) timeUnit.getSelectedItem();
        bean.medicineId = id;
        bean.medicineName = name;
        bean.medicineUnit = (String) valueUnit.getSelectedItem();
        bean.singleDose = regester_three_danciyonyaoliang.getText().toString();
        bean.startTime = tv_start_time.getText().toString();
        bean.usingFrequency = (String) yypd.getSelectedItem();
        medicationUsingSituationsList.add(bean);
        mMedicineAdapter.notifyItemInserted(medicationUsingSituationsList.size() - 1);
    }

    /**
     * @param requestCode 100-199 系统外就医 200-299 服药开始时间，300-399服药结束时间 400-499 血管事件发生时间
     */
    private void startDatePicker(int requestCode, String sDate) {
        Intent intent
                = new Intent(activity, DatePickerActivity.class);
        intent.putExtra("sDate", sDate);
        startActivityForResult(intent, requestCode);
    }
    @Override
    public void onDismiss() {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (type ==1){
            spName = s.toString();
            hxName = hxm.getText().toString().trim();
        }else {
            hxName = s.toString();
            spName = spm.getText().toString().trim();
        }
        requestName();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    private void requestName(){
        L.e("type = " +type +" spName = " +spName +" hxName = " +hxName);
        RequestManager.getObject(String.format(Locale.CHINA, Constens.GET_MEDICINE_NAME, type, spName, hxName), this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                L.e(jsonObject.toString());
                try {
                    List<Mecicine> results = JSON.parseArray(jsonObject.getString("results"), Mecicine.class);
                    mNameAdapter.setResults(results);
                    mNameAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        if (type ==1){
           spm.setText(mNameAdapter.getName(position));
        }else {
           hxm.setText(mNameAdapter.getName(position));
        }
        mPopupWindow.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode < 1000) {
            String date = data.getStringExtra("date");
            if (requestCode==200){ //服药开始时间
                tv_start_time.setText(date);
            }else {
                if (requestCode==299){ //服药结束时间
                    tv_end_time.setText(date);
                }else {
                    mMedicineAdapter.notifyEndTimeChanged(requestCode-300,date);
                }
            }
        }
    }
}
