package com.uyi.xinf.ui.healthmanager;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.doctor.xinf.R;
import com.uyi.xinf.Constens;
import com.uyi.xinf.ErrorCode;
import com.uyi.xinf.ui.custom.BaseActivity;
import com.uyi.xinf.ui.custom.SystemBarTintManager;
import com.uyi.xinf.ui.custom.spiner.AbstractSpinerAdapter;
import com.uyi.xinf.ui.custom.spiner.SpinerPopWindow;
import com.uyi.xinf.ui.dialog.Loading;
import com.uyi.xinf.utils.L;
import com.uyi.xinf.utils.T;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.uyi.xinf.UYIApplication.getContext;


/**
 * Created by ThinkPad on 2017/616.
 */
@ContentView(R.layout.activity_add_health_manager)
public class HealthManagerDetailsActivity extends BaseActivity implements AbstractSpinerAdapter.IOnItemSelectListener {
    @ViewInject(R.id.lay)
    public LinearLayout lay;
    @ViewInject(R.id.back)
    private LinearLayout back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.add)
    private TextView add;
    private int selectedType = 0;
    private SpinerPopWindow spinerPopWindow;
    Calendar c = Calendar.getInstance();//
    private int mYear = c.get(Calendar.YEAR); // 获取当前年份
    private int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
    private int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
    private ArrayList<CheckBox> ylfyzffsbos = new ArrayList<CheckBox>();
    int ylfyzfssnum = 0;
    private ArrayList<CheckBox> ywgmsbos = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> blsbos = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> jbbos = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> fqbos = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> mqbos = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> xdjmbos = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> znbos = new ArrayList<CheckBox>();
    private ArrayList<CheckBox> cjqkbos = new ArrayList<CheckBox>();
    private ArrayList<TextView> jbtts = new ArrayList<TextView>();
    int xbtype = 1 ;
    int czlxtype = 1 ;
    int xxtype = 1 ;
    int rhtype = 1 ;
    int whcdtype = 1 ;
    int hyzktype = 1 ;
    int zytype = 0 ;
    int cfpfsstype = 1 ;
    int rllxtype = 1 ;
    int ystype = 1 ;
    int cstype = 1 ;
    int qcltype = 1 ;
    @OnClick({R.id.add, R.id.back, R.id.submit})
    public void widgetClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.back:
                finish();
                break;
            case R.id.add:
                break;
            case R.id.submit:
                postData();
                break;

        }
    }

    private void postData() {
        try {
            if(tt_beathday.getText().toString().equals("")){
                T.show(activity,"请填写出生日期！",0);
                return;
            }
            Loading.bulid(activity, null).show();
            JSONObject params = new JSONObject();
//            params.put("realName", et_name.getText().toString());//村id   暂时写死
            params.put("realName", et_name.getText().toString());//姓名

            params.put("birthday", tt_beathday.getText().toString());//生日
            params.put("gender",xbtype+"");//性别
            params.put("idCardNumber", et_idcard.getText().toString());//身份证
            params.put("workUnit", et_workunit.getText().toString());//工作单位
            params.put("phoneNumber", et_phonenumber.getText().toString());//电话
            params.put("emergencyContact", et_contactsname.getText().toString());//联系人
            params.put("emergencyContactPhpone", et_contactsphonenumber.getText().toString());//联系人电话
            params.put("residentType", czlxtype+"");//常住类型
            if (mz_rb1.isChecked()) {
                params.put("nation", "01");//民族
            } else if (mz_rb2.isChecked()) {
                params.put("nation", "99" + "_" + mz_et_name.getText().toString());//民族
            } else {
                params.put("nation", "01");//民族
            }
            params.put("bloodType", xxtype+"");//血型
            params.put("rh", rhtype+"");//RH
            params.put("degreeOfEducation", whcdtype+"");//文化程度
            params.put("marriage", hyzktype+"");//婚姻状况
            params.put("occupation", zytype+"");//职业

            String ylfyzffsstr = "";
            for (int i = 0; i < 7; i++) {
                if (ylfyzffsbos.get(i).isChecked()) {
                    ylfyzffsstr += "," + (i+1);
                }
            }
            if (ylfyzffsbos.get(7).isChecked()) {
                ylfyzffsstr += "," + 8 + "_" + paytype_cb8_et.getText().toString();
            }
            if(!ylfyzffsstr.equals("")){
                ylfyzffsstr = ylfyzffsstr.substring(1,ylfyzffsstr.length());
            }
            params.put("medicalPaymentMode", ylfyzffsstr);//医疗费用支付方式
            String ywgmsstr = "";
            for (int i = 0; i < 4; i++) {
                if (ywgmsbos.get(i).isChecked()) {
                    ywgmsstr += "," + (i+1);
                }
            }
            if (ywgmsbos.get(4).isChecked()) {
                ywgmsstr += "," + 5 + "_" + ywgms_cb5_et.getText().toString();
            }
            if(!ywgmsstr.equals("")){
                ywgmsstr = ywgmsstr.substring(1,ywgmsstr.length());
            }
            params.put("historyOfDrugAllergy", ywgmsstr);//药物过敏史
            String blsstr = "";
            for (int i = 0; i < 4; i++) {
                if (blsbos.get(i).isChecked()) {
                    blsstr += "," + (i+1);
                }
            }
            if(!blsstr.equals("")){
                blsstr = blsstr.substring(1,blsstr.length());
            }
            params.put("expose", blsstr);//暴露史
            JSONArray jbaar = new JSONArray();
            JSONObject jbparams;
            for (int i = 0; i < 13; i++) {
                if (jbbos.get(i).isChecked()) {
                    jbparams = new JSONObject();
                    switch (i) {
                        case 0:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime", "");//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 1:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime", jb_cb2_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 2:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb3_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 3:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb4_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 4:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb5_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 5:
                            jbparams.put("diseaseName", jb_cb6_et_name.getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb6_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 6:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb7_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 7:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb8_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 8:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb9_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 9:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb10_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 10:
                            jbparams.put("diseaseName", jbbos.get(i).getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb11_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 11:
                            jbparams.put("diseaseName", jb_cb12_et_name.getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb12_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                        case 12:
                            jbparams.put("diseaseName", jb_cb13_et_name.getText().toString());//疾病名称
                            jbparams.put("diseaseCode", "" + (i+1));//疾病编码
                            jbparams.put("diagnosisTime",  jb_cb13_time.getText().toString());//疾病时间
                            jbaar.put(jbparams);
                            break;
                    }
                }


            }
            params.put("userDiseaseManagementJson", jbaar);//疾病

            JSONArray ssaar = new JSONArray();
            if (ss_rb2.isChecked()) {
                JSONObject ssparams = new JSONObject();
                JSONObject ssparams1 = new JSONObject();
                ssparams.put("operationName", ss_et_name1.getText().toString());//手术名称
                ssparams.put("operationTime", ss_tt_time1.getText().toString());//手术时间
                ssparams1.put("operationName", ss_et_name2.getText().toString());//手术名称
                ssparams1.put("operationTime", ss_tt_time2.getText().toString());//手术时间
                ssaar.put(ssparams);
                ssaar.put(ssparams1);
            }
            params.put("operation", ssaar);//手术

            JSONArray wsaar = new JSONArray();
            if (ws_rb2.isChecked()) {
                JSONObject wsparams = new JSONObject();
                JSONObject wsparams1 = new JSONObject();
                wsparams.put("traumaName", ws_et_name1.getText().toString());//手术名称
                wsparams.put("traumaTime", ws_tt_time1.getText().toString());//手术时间
                wsparams1.put("traumaName", ws_et_name2.getText().toString());//手术名称
                wsparams1.put("traumaTime", ws_tt_time2.getText().toString());//手术时间
                wsaar.put(wsparams);
                wsaar.put(wsparams1);
            }
            params.put("trauma", wsaar);//外伤

            JSONArray sxaar = new JSONArray();
            if (sx_rb2.isChecked()) {
                JSONObject sxparams = new JSONObject();
                JSONObject sxparams1 = new JSONObject();
                sxparams.put("bloodName", sx_et_name1.getText().toString());//手术名称
                sxparams.put("bloodTime", sx_tt_time1.getText().toString());//手术时间
                sxparams1.put("bloodName", sx_et_name2.getText().toString());//手术名称
                sxparams1.put("bloodTime", sx_tt_time2.getText().toString());//手术时间
                sxaar.put(sxparams);
                sxaar.put(sxparams1);
            }
            params.put("bloodTransfusion", sxaar);//输血
            String fqstr = "";
            for (int i = 0; i < 11; i++) {
                if (fqbos.get(i).isChecked()) {
                    fqstr += "," + (i+1);
                }
            }
            if (fqbos.get(11).isChecked()) {
                fqstr += "," + 12 + "_" + fq_cb12_et.getText().toString();
            }
            if(!fqstr.equals("")){
                fqstr = fqstr.substring(1,fqstr.length());
            }
            params.put("familyHistoryFather", fqstr);//父亲

            String mqstr = "";
            for (int i = 0; i < 11; i++) {
                if (mqbos.get(i).isChecked()) {
                    mqstr += "," + (i+1);
                }
            }
            if (mqbos.get(11).isChecked()) {
                mqstr += "," + 12 + "_" + mq_cb12_et.getText().toString();
            }
            if(!mqstr.equals("")){
                mqstr = mqstr.substring(1,mqstr.length());
            }
            params.put("familyHistoryMother", mqstr);//母亲

            String xdjmstr = "";
            for (int i = 0; i < 11; i++) {
                if (xdjmbos.get(i).isChecked()) {
                    xdjmstr += "," + (i+1);
                }
            }
            if (xdjmbos.get(11).isChecked()) {
                xdjmstr += "," + 12 + "_" + xdjm_cb12_et.getText().toString();
            }
            if(!xdjmstr.equals("")){
                xdjmstr = xdjmstr.substring(1,xdjmstr.length());
            }
            params.put("familyHistoryBorther", xdjmstr);//兄弟

            String znstr = "";
            for (int i = 0; i < 11; i++) {
                if (znbos.get(i).isChecked()) {
                    znstr += "," + (i+1);
                }
            }
            if (znbos.get(11).isChecked()) {
                znstr += "," + 12 + "_" + zn_cb12_et.getText().toString();
            }
            if(!znstr.equals("")){
                znstr = znstr.substring(1,znstr.length());
            }
            params.put("familyHistorySon", znstr);//子女
            String ycbsstr = "";
            if (ycbs_rb1.isChecked()) {
                ycbsstr = "1";
            } else if (ycbs_rb2.isChecked()) {
                ycbsstr = "2_" + ycbs_rb_name.getText().toString();
            } else {
                ycbsstr = "1";
            }
            params.put("geneticHistory", ycbsstr);//遗传病史

            String cjqkstr = "";
            for (int i = 0; i < 7; i++) {
                if (cjqkbos.get(i).isChecked()) {
                    cjqkstr += "," + (i+1);
                }
            }
            if (cjqkbos.get(7).isChecked()) {
                cjqkstr += "," + 8 + "_" + cjqk_cb8_et.getText().toString();
            }
            if(!cjqkstr.equals("")){
                cjqkstr = cjqkstr.substring(1,cjqkstr.length());
            }
            params.put("disability", cjqkstr);//残疾情况
            params.put("kitchenVentilationFacilities", cfpfsstype+"");//厨房排风设施
            params.put("fuelType", rllxtype+"");//燃料类型
            params.put("drinkingWater", ystype+"");//饮水
            params.put("toilet",cstype+"");//厕所
            params.put("aBird", qcltype+"");//禽畜栏
            params.put("address", et_xzz.getText().toString());//现住址
            params.put("placeOfOrigin", et_hjdz.getText().toString());//户籍地址
            params.put("neighborhoodCommittee", et_jwh.getText().toString());//居委会
            params.put("subdistrictOffice", et_jdb.getText().toString());//街道办
            params.put("responsibleDoctor", et_zrys.getText().toString());//责任医生
            params.put("responsiblePhone", et_zrysdh.getText().toString());//责任医生电话
            params.put("filingUnit", et_jdjg.getText().toString());//建档机构
            params.put("filingUnitPhone", et_jdjgdh.getText().toString());//建档机构电话
            L.e("params == ", params.toString());
            RequestManager.postObject(Constens.POST_BASIC_INFORMATION, activity, params, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject data) {
                    Loading.bulid(activity,"").dismiss();
                    T.showShort(activity, "新建成功!");
                    setResult(RESULT_OK);
                    finish();
                }
            }, new RequestErrorListener() {
                public void requestError(VolleyError e) {
                    if (e.networkResponse != null) {
                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                        Loading.bulid(activity,"").dismiss();
                        T.showShort(activity, "新建失败!");
                    } else {
                        T.showShort(activity, "新建成功!");
                        Loading.bulid(activity,"").dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
//            RequestManager.postObject(Constens.POST_BASIC_INFORMATION, activity, params, null, new RequestErrorListener() {
//                @Override
//                public void requestError(VolleyError e) {
//                    Loading.bulid(activity,"").dismiss();
//                    setResult(RESULT_OK);
//                    T.showShort(activity, "提交成功!");
//                }
//            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //残疾情况
    @OnClick({R.id.cjqk_cb1, R.id.cjqk_cb2, R.id.cjqk_cb3, R.id.cjqk_cb4, R.id.cjqk_cb5, R.id.cjqk_cb6,
            R.id.cjqk_cb7, R.id.cjqk_cb8})
    public void cjqkClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.cjqk_cb1:
                if (cjqk_cb1.isChecked()) {
                    for (int j = 0; j < cjqkbos.size(); j++) {
                        cjqkbos.get(j).setChecked(false);
                    }
                    cjqk_cb8_et.setVisibility(View.GONE);
                    cjqk_cb1.setChecked(true);
                }
                cjqkcheck(cjqk_cb1);
                break;
            case R.id.cjqk_cb2:
                cjqk_cb1.setChecked(false);
                cjqkcheck(cjqk_cb2);
                break;
            case R.id.cjqk_cb3:
                cjqk_cb1.setChecked(false);
                cjqkcheck(cjqk_cb3);
                break;
            case R.id.cjqk_cb4:
                cjqk_cb1.setChecked(false);
                cjqkcheck(cjqk_cb4);
                break;
            case R.id.cjqk_cb5:
                cjqk_cb1.setChecked(false);
                cjqkcheck(cjqk_cb5);
                break;
            case R.id.cjqk_cb6:
                cjqk_cb1.setChecked(false);
                cjqkcheck(cjqk_cb6);
                break;
            case R.id.cjqk_cb7:
                cjqk_cb1.setChecked(false);
                cjqkcheck(cjqk_cb7);
                break;
            case R.id.cjqk_cb8:
                cjqk_cb1.setChecked(false);
                cjqkcheck(cjqk_cb8);
                if (cjqk_cb8.isChecked()) {
                    cjqk_cb8_et.setVisibility(View.VISIBLE);
                } else {
                    cjqk_cb8_et.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void cjqkcheck(CheckBox box) {
        ylfyzfssnum = 0;
        for (int i = 0; i < cjqkbos.size(); i++) {
            if (cjqkbos.get(i).isChecked()) {
                ylfyzfssnum++;
            }
        }
        if (ylfyzfssnum > 6) {
            T.show(this, "最多选择六项！", 0);
            box.setChecked(false);
        }
    }

    //子女
    @OnClick({R.id.zn_cb1, R.id.zn_cb2, R.id.zn_cb3, R.id.zn_cb4, R.id.zn_cb5, R.id.zn_cb6,
            R.id.zn_cb7, R.id.zn_cb8, R.id.zn_cb9, R.id.zn_cb10, R.id.zn_cb11, R.id.zn_cb12})
    public void znClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.zn_cb1:
                if (zn_cb1.isChecked()) {
                    for (int j = 0; j < znbos.size(); j++) {
                        znbos.get(j).setChecked(false);
                    }
                    zn_cb12_et.setVisibility(View.GONE);
                    zn_cb1.setChecked(true);
                }
                zncheck(zn_cb1);
                break;
            case R.id.zn_cb2:
                zn_cb1.setChecked(false);
                zncheck(zn_cb2);
                break;
            case R.id.zn_cb3:
                zn_cb1.setChecked(false);
                zncheck(zn_cb3);
                break;
            case R.id.zn_cb4:
                zn_cb1.setChecked(false);
                zncheck(zn_cb4);
                break;
            case R.id.zn_cb5:
                zn_cb1.setChecked(false);
                zncheck(zn_cb5);
                break;
            case R.id.zn_cb6:
                zn_cb1.setChecked(false);
                zncheck(zn_cb6);
                break;
            case R.id.zn_cb7:
                zn_cb1.setChecked(false);
                zncheck(zn_cb7);
                break;
            case R.id.zn_cb8:
                zn_cb1.setChecked(false);
                zncheck(zn_cb8);
                break;
            case R.id.zn_cb9:
                zn_cb1.setChecked(false);
                zncheck(zn_cb9);
                break;
            case R.id.zn_cb10:
                zn_cb1.setChecked(false);
                zncheck(zn_cb10);
                break;
            case R.id.zn_cb11:
                zn_cb1.setChecked(false);
                zncheck(zn_cb11);
                break;
            case R.id.zn_cb12:
                zn_cb1.setChecked(false);
                zncheck(zn_cb12);
                if (zn_cb12.isChecked()) {
                    zn_cb12_et.setVisibility(View.VISIBLE);
                } else {
                    zn_cb12_et.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void zncheck(CheckBox box) {
        ylfyzfssnum = 0;
        for (int i = 0; i < znbos.size(); i++) {
            if (znbos.get(i).isChecked()) {
                ylfyzfssnum++;
            }
        }
        if (ylfyzfssnum > 6) {
            T.show(this, "最多选择六项！", 0);
            box.setChecked(false);
        }
    }

    //兄弟姐妹
    @OnClick({R.id.xdjm_cb1, R.id.xdjm_cb2, R.id.xdjm_cb3, R.id.xdjm_cb4, R.id.xdjm_cb5, R.id.xdjm_cb6,
            R.id.xdjm_cb7, R.id.xdjm_cb8, R.id.xdjm_cb9, R.id.xdjm_cb10, R.id.xdjm_cb11, R.id.xdjm_cb12})
    public void xdjmClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.xdjm_cb1:
                if (xdjm_cb1.isChecked()) {
                    for (int j = 0; j < xdjmbos.size(); j++) {
                        xdjmbos.get(j).setChecked(false);
                    }
                    xdjm_cb12_et.setVisibility(View.GONE);
                    xdjm_cb1.setChecked(true);
                }
                xdjmcheck(xdjm_cb1);
                break;
            case R.id.xdjm_cb2:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb2);
                break;
            case R.id.xdjm_cb3:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb3);
                break;
            case R.id.xdjm_cb4:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb4);
                break;
            case R.id.xdjm_cb5:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb5);
                break;
            case R.id.xdjm_cb6:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb6);
                break;
            case R.id.xdjm_cb7:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb7);
                break;
            case R.id.xdjm_cb8:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb8);
                break;
            case R.id.xdjm_cb9:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb9);
                break;
            case R.id.xdjm_cb10:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb10);
                break;
            case R.id.xdjm_cb11:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb11);
                break;
            case R.id.xdjm_cb12:
                xdjm_cb1.setChecked(false);
                xdjmcheck(xdjm_cb12);
                if (xdjm_cb12.isChecked()) {
                    xdjm_cb12_et.setVisibility(View.VISIBLE);
                } else {
                    xdjm_cb12_et.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void xdjmcheck(CheckBox box) {
        ylfyzfssnum = 0;
        for (int i = 0; i < xdjmbos.size(); i++) {
            if (xdjmbos.get(i).isChecked()) {
                ylfyzfssnum++;
            }
        }
        if (ylfyzfssnum > 6) {
            T.show(this, "最多选择六项！", 0);
            box.setChecked(false);
        }
    }

    //母亲
    @OnClick({R.id.mq_cb1, R.id.mq_cb2, R.id.mq_cb3, R.id.mq_cb4, R.id.mq_cb5, R.id.mq_cb6,
            R.id.mq_cb7, R.id.mq_cb8, R.id.mq_cb9, R.id.mq_cb10, R.id.mq_cb11, R.id.mq_cb12})
    public void mqClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.mq_cb1:
                if (mq_cb1.isChecked()) {
                    for (int j = 0; j < mqbos.size(); j++) {
                        mqbos.get(j).setChecked(false);
                    }
                    mq_cb12_et.setVisibility(View.GONE);
                    mq_cb1.setChecked(true);
                }
                mqcheck(mq_cb1);
                break;
            case R.id.mq_cb2:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb2);
                break;
            case R.id.mq_cb3:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb3);
                break;
            case R.id.mq_cb4:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb4);
                break;
            case R.id.mq_cb5:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb5);
                break;
            case R.id.mq_cb6:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb6);
                break;
            case R.id.mq_cb7:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb7);
                break;
            case R.id.mq_cb8:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb8);
                break;
            case R.id.mq_cb9:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb9);
                break;
            case R.id.mq_cb10:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb10);
                break;
            case R.id.mq_cb11:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb11);
                break;
            case R.id.mq_cb12:
                mq_cb1.setChecked(false);
                mqcheck(mq_cb12);
                if (mq_cb12.isChecked()) {
                    mq_cb12_et.setVisibility(View.VISIBLE);
                } else {
                    mq_cb12_et.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void mqcheck(CheckBox box) {
        ylfyzfssnum = 0;
        for (int i = 0; i < mqbos.size(); i++) {
            if (mqbos.get(i).isChecked()) {
                ylfyzfssnum++;
            }
        }
        if (ylfyzfssnum > 6) {
            T.show(this, "最多选择六项！", 0);
            box.setChecked(false);
        }
    }

    //父亲
    @OnClick({R.id.fq_cb1, R.id.fq_cb2, R.id.fq_cb3, R.id.fq_cb4, R.id.fq_cb5, R.id.fq_cb6,
            R.id.fq_cb7, R.id.fq_cb8, R.id.fq_cb9, R.id.fq_cb10, R.id.fq_cb11, R.id.fq_cb12})
    public void fqClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.fq_cb1:
                if (fq_cb1.isChecked()) {
                    for (int j = 0; j < fqbos.size(); j++) {
                        fqbos.get(j).setChecked(false);
                    }
                    fq_cb12_et.setVisibility(View.GONE);
                    fq_cb1.setChecked(true);
                }
                fqcheck(fq_cb1);
                break;
            case R.id.fq_cb2:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb2);
                break;
            case R.id.fq_cb3:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb3);
                break;
            case R.id.fq_cb4:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb4);
                break;
            case R.id.fq_cb5:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb5);
                break;
            case R.id.fq_cb6:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb6);
                break;
            case R.id.fq_cb7:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb7);
                break;
            case R.id.fq_cb8:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb8);
                break;
            case R.id.fq_cb9:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb9);
                break;
            case R.id.fq_cb10:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb10);
                break;
            case R.id.fq_cb11:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb11);
                break;
            case R.id.fq_cb12:
                fq_cb1.setChecked(false);
                fqcheck(fq_cb12);
                if (fq_cb12.isChecked()) {
                    fq_cb12_et.setVisibility(View.VISIBLE);
                } else {
                    fq_cb12_et.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void fqcheck(CheckBox box) {
        ylfyzfssnum = 0;
        for (int i = 0; i < fqbos.size(); i++) {
            if (fqbos.get(i).isChecked()) {
                ylfyzfssnum++;
            }
        }
        if (ylfyzfssnum > 6) {
            T.show(this, "最多选择六项！", 0);
            box.setChecked(false);
        }
    }

    //疾病
    @OnClick({R.id.jb_cb1, R.id.jb_cb2, R.id.jb_cb3, R.id.jb_cb4, R.id.jb_cb5, R.id.jb_cb6, R.id.jb_cb7, R.id.jb_cb8,
            R.id.jb_cb9, R.id.jb_cb10, R.id.jb_cb11, R.id.jb_cb12, R.id.jb_cb13})
    public void jbClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.jb_cb1:
                if (jb_cb1.isChecked()) {
                    for (int j = 0; j < jbbos.size(); j++) {
                        jbbos.get(j).setChecked(false);
                    }
                    for (int j = 0; j < jbtts.size(); j++) {
                        jbtts.get(j).setVisibility(View.GONE);
                    }
                    jb_cb6_et_name.setVisibility(View.GONE);
                    jb_cb12_et_name.setVisibility(View.GONE);
                    jb_cb13_et_name.setVisibility(View.GONE);
                    jb_cb1.setChecked(true);
                }
                jbcheck(jb_cb1);
                break;
            case R.id.jb_cb2:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb2);
                if (jb_cb2.isChecked()) {
                    jb_cb2_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb2_time.setVisibility(View.GONE);
                }

                break;
            case R.id.jb_cb3:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb3);
                if (jb_cb3.isChecked()) {
                    jb_cb3_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb3_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb4:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb4);
                if (jb_cb4.isChecked()) {
                    jb_cb4_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb4_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb5:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb5);
                if (jb_cb5.isChecked()) {
                    jb_cb5_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb5_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb6:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb6);
                if (jb_cb6.isChecked()) {
                    jb_cb6_time.setVisibility(View.VISIBLE);
                    jb_cb6_et_name.setVisibility(View.VISIBLE);
                } else {
                    jb_cb6_time.setVisibility(View.GONE);
                    jb_cb6_et_name.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb7:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb7);
                if (jb_cb7.isChecked()) {
                    jb_cb7_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb7_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb8:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb8);
                if (jb_cb8.isChecked()) {
                    jb_cb8_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb8_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb9:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb9);
                if (jb_cb9.isChecked()) {
                    jb_cb9_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb9_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb10:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb10);
                if (jb_cb10.isChecked()) {
                    jb_cb10_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb10_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb11:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb11);
                if (jb_cb11.isChecked()) {
                    jb_cb11_time.setVisibility(View.VISIBLE);
                } else {
                    jb_cb11_time.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb12:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb12);
                if (jb_cb12.isChecked()) {
                    jb_cb12_time.setVisibility(View.VISIBLE);
                    jb_cb12_et_name.setVisibility(View.VISIBLE);
                } else {
                    jb_cb12_time.setVisibility(View.GONE);
                    jb_cb12_et_name.setVisibility(View.GONE);
                }
                break;
            case R.id.jb_cb13:
                jb_cb1.setChecked(false);
                jbcheck(jb_cb13);
                if (jb_cb13.isChecked()) {
                    jb_cb13_time.setVisibility(View.VISIBLE);
                    jb_cb13_et_name.setVisibility(View.VISIBLE);
                } else {
                    jb_cb13_time.setVisibility(View.GONE);
                    jb_cb13_et_name.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void jbcheck(CheckBox box) {
        ylfyzfssnum = 0;
        for (int i = 0; i < jbbos.size(); i++) {
            if (jbbos.get(i).isChecked()) {
                ylfyzfssnum++;
            }
        }
        if (ylfyzfssnum > 4) {
            T.show(this, "最多选择四项！", 0);
            box.setChecked(false);
        }
    }

    //暴露史
    @OnClick({R.id.bls_cb1, R.id.bls_cb2, R.id.bls_cb3, R.id.bls_cb4})
    public void blsClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.bls_cb1:
                if (bls_cb1.isChecked()) {
                    for (int j = 0; j < blsbos.size(); j++) {
                        blsbos.get(j).setChecked(false);
                    }
                    bls_cb1.setChecked(true);
                }
                break;
            case R.id.bls_cb2:
                bls_cb1.setChecked(false);
                break;
            case R.id.bls_cb3:
                bls_cb1.setChecked(false);
                break;
            case R.id.bls_cb4:
                bls_cb1.setChecked(false);
                break;
        }
    }

    //药物过敏史
    @OnClick({R.id.ywgms_cb1, R.id.ywgms_cb2, R.id.ywgms_cb3, R.id.ywgms_cb4, R.id.ywgms_cb5})
    public void ywgmsClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.ywgms_cb1:
                if (ywgms_cb1.isChecked()) {
                    for (int j = 0; j < ywgmsbos.size(); j++) {
                        ywgmsbos.get(j).setChecked(false);
                    }
                    ywgms_cb5_et.setVisibility(View.GONE);
                    ywgms_cb1.setChecked(true);
                }
                break;
            case R.id.ywgms_cb2:
                ywgms_cb1.setChecked(false);
                break;
            case R.id.ywgms_cb3:
                ywgms_cb1.setChecked(false);
                break;
            case R.id.ywgms_cb4:
                ywgms_cb1.setChecked(false);
                break;
            case R.id.ywgms_cb5:
                ywgms_cb1.setChecked(false);
                if (ywgms_cb5.isChecked()) {
                    ywgms_cb5_et.setVisibility(View.VISIBLE);
                } else {
                    ywgms_cb5_et.setVisibility(View.GONE);
                }
                break;
        }
    }

    //支付方式
    @OnClick({R.id.paytype_cb1, R.id.paytype_cb2, R.id.paytype_cb3, R.id.paytype_cb4, R.id.paytype_cb5, R.id.paytype_cb6, R.id.paytype_cb7, R.id.paytype_cb8})
    public void payClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.paytype_cb1:
                paycheck(paytype_cb1);
                break;
            case R.id.paytype_cb2:
                paycheck(paytype_cb2);
                break;
            case R.id.paytype_cb3:
                paycheck(paytype_cb3);
                break;
            case R.id.paytype_cb4:
                paycheck(paytype_cb4);
                break;
            case R.id.paytype_cb5:
                paycheck(paytype_cb5);
                break;
            case R.id.paytype_cb6:
                paycheck(paytype_cb6);
                break;
            case R.id.paytype_cb7:
                paycheck(paytype_cb7);
                break;
            case R.id.paytype_cb8:
                if (paytype_cb8.isChecked()) {
                    paytype_cb8_et.setVisibility(View.VISIBLE);
                } else {
                    paytype_cb8_et.setVisibility(View.GONE);
                }
                paycheck(paytype_cb8);
                break;
        }
    }

    private void paycheck(CheckBox box) {
        ylfyzfssnum = 0;
        for (int i = 0; i < ylfyzffsbos.size(); i++) {
            if (ylfyzffsbos.get(i).isChecked()) {
                ylfyzfssnum++;
            }
        }
        if (ylfyzfssnum > 3) {
            T.show(this, "最多选择三项！", 0);
            box.setChecked(false);
            if (!paytype_cb8.isChecked()) {
                paytype_cb8_et.setVisibility(View.GONE);
            }
        }
    }

    //民族
    @OnClick({R.id.mz_rb2, R.id.mz_rb1})
    public void setClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.mz_rb1:
                mz_et_name.setVisibility(View.GONE);
                break;
            case R.id.mz_rb2:
                mz_et_name.setVisibility(View.VISIBLE);
                break;
        }
    }

    //手术
    @OnClick({R.id.ss_rb2, R.id.ss_rb1})
    public void ssClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.ss_rb1:
                lay_ss.setVisibility(View.GONE);
                break;
            case R.id.ss_rb2:
                lay_ss.setVisibility(View.VISIBLE);
                break;
        }
    }

    //外伤
    @OnClick({R.id.ws_rb2, R.id.ws_rb1})
    public void wsClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.ws_rb1:
                lay_ws.setVisibility(View.GONE);
                break;
            case R.id.ws_rb2:
                lay_ws.setVisibility(View.VISIBLE);
                break;
        }
    }

    //输血
    @OnClick({R.id.sx_rb2, R.id.sx_rb1})
    public void sxClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.sx_rb1:
                lay_sx.setVisibility(View.GONE);
                break;
            case R.id.sx_rb2:
                lay_sx.setVisibility(View.VISIBLE);
                break;
        }
    }

    //遗传病史
    @OnClick({R.id.ycbs_rb2, R.id.ycbs_rb1})
    public void ycbsClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.ycbs_rb1:
                ycbs_rb_name.setVisibility(View.GONE);
                break;
            case R.id.ycbs_rb2:
                ycbs_rb_name.setVisibility(View.VISIBLE);
                break;
        }
    }

    //时间选择监听
    @OnClick({R.id.tt_beathday, R.id.jb_cb2_time, R.id.jb_cb3_time, R.id.jb_cb4_time, R.id.jb_cb5_time, R.id.jb_cb6_time, R.id.jb_cb7_time,
            R.id.jb_cb8_time, R.id.jb_cb9_time, R.id.jb_cb10_time, R.id.jb_cb11_time, R.id.jb_cb12_time, R.id.jb_cb13_time, R.id.ss_tt_time1,
            R.id.ss_tt_time2, R.id.ws_tt_time1, R.id.ws_tt_time2, R.id.sx_tt_time1, R.id.sx_tt_time2})
    public void timeClick(View v) {

        int i = v.getId();
        switch (i) {
            case R.id.tt_beathday:
                selectedDate(tt_beathday);
                break;
            case R.id.jb_cb2_time:
                selectedDate(jb_cb2_time);
                break;
            case R.id.jb_cb3_time:
                selectedDate(jb_cb3_time);
                break;
            case R.id.jb_cb4_time:
                selectedDate(jb_cb4_time);
                break;
            case R.id.jb_cb5_time:
                selectedDate(jb_cb5_time);
                break;
            case R.id.jb_cb6_time:
                selectedDate(jb_cb6_time);
                break;
            case R.id.jb_cb7_time:
                selectedDate(jb_cb7_time);
                break;
            case R.id.jb_cb8_time:
                selectedDate(jb_cb8_time);
                break;
            case R.id.jb_cb9_time:
                selectedDate(jb_cb9_time);
                break;
            case R.id.jb_cb10_time:
                selectedDate(jb_cb10_time);
                break;
            case R.id.jb_cb11_time:
                selectedDate(jb_cb11_time);
                break;
            case R.id.jb_cb12_time:
                selectedDate(jb_cb12_time);
                break;
            case R.id.jb_cb13_time:
                selectedDate(jb_cb13_time);
                break;
            case R.id.ss_tt_time1:
                selectedDate(ss_tt_time1);
                break;
            case R.id.ss_tt_time2:
                selectedDate(ss_tt_time2);
                break;
            case R.id.ws_tt_time1:
                selectedDate(ws_tt_time1);
                break;
            case R.id.ws_tt_time2:
                selectedDate(ws_tt_time2);
                break;
            case R.id.sx_tt_time1:
                selectedDate(sx_tt_time1);

                break;
            case R.id.sx_tt_time2:
                selectedDate(sx_tt_time2);

                break;

        }
    }

    public void selectedDate(final TextView text) {
        new DatePickerDialog(HealthManagerDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String month = "";
                if (i1 + 1 < 10) {
                    month = "0" + (i1 + 1);
                } else {
                    month = "" + (i1 + 1);
                }
                String day = "";
                if (i2 < 10) {
                    day = "0" + i2;
                } else {
                    day = "" + i2;
                }
                text.setText(i + "-" + month + "-" + day);
            }
        }, mYear, mMonth - 1, mDay).show();
    }

    //下拉监听
    @OnClick({R.id.tt_sex, R.id.tt_household, R.id.tt_bloodtype, R.id.tt_rh, R.id.tt_education, R.id.tt_marriage, R.id.tt_ocupation, R.id.tt_cfpfss, R.id.tt_rllx, R.id.tt_ys, R.id.tt_cs, R.id.tt_qcl})
    public void spinerClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.tt_sex:
                selectedType = 1;
                spinerPopWindow.setWidth(tt_sex.getWidth());
                spinerPopWindow.refreshData(xblist, 0);
                spinerPopWindow.showAsDropDown(tt_sex);
                break;
            case R.id.tt_household:
                selectedType = 2;
                spinerPopWindow.setWidth(tt_household.getWidth());
                spinerPopWindow.refreshData(czlxlist, 0);
                spinerPopWindow.showAsDropDown(tt_household);
                break;
            case R.id.tt_bloodtype:
                selectedType = 3;
                spinerPopWindow.setWidth(tt_bloodtype.getWidth());
                spinerPopWindow.refreshData(xxlist, 0);
                spinerPopWindow.showAsDropDown(tt_bloodtype);
                break;
            case R.id.tt_rh:
                selectedType = 4;
                spinerPopWindow.setWidth(tt_rh.getWidth());
                spinerPopWindow.refreshData(rhlist, 0);
                spinerPopWindow.showAsDropDown(tt_rh);
                break;
            case R.id.tt_education:
                selectedType = 5;
                spinerPopWindow.setWidth(tt_education.getWidth());
                spinerPopWindow.refreshData(whcdlist, 0);
                spinerPopWindow.showAsDropDown(tt_education);
                break;
            case R.id.tt_marriage:
                selectedType = 6;
                spinerPopWindow.setWidth(tt_marriage.getWidth());
                spinerPopWindow.refreshData(hyzklist, 0);
                spinerPopWindow.showAsDropDown(tt_marriage);
                break;
            case R.id.tt_ocupation:
                selectedType = 7;
                spinerPopWindow.setWidth(tt_ocupation.getWidth());
                spinerPopWindow.refreshData(zylist, 0);
                spinerPopWindow.showAsDropDown(tt_ocupation);
                break;
            case R.id.tt_cfpfss:
                selectedType = 8;
                spinerPopWindow.setWidth(tt_cfpfss.getWidth());
                spinerPopWindow.refreshData(cfpfsslist, 0);
                spinerPopWindow.showAsDropDown(tt_cfpfss);
                break;
            case R.id.tt_rllx:
                selectedType = 9;
                spinerPopWindow.setWidth(tt_rllx.getWidth());
                spinerPopWindow.refreshData(rllxlist, 0);
                spinerPopWindow.showAsDropDown(tt_rllx);
                break;
            case R.id.tt_ys:
                selectedType = 10;
                spinerPopWindow.setWidth(tt_ys.getWidth());
                spinerPopWindow.refreshData(yslist, 0);
                spinerPopWindow.showAsDropDown(tt_ys);
                break;
            case R.id.tt_cs:
                selectedType = 11;
                spinerPopWindow.setWidth(tt_cs.getWidth());
                spinerPopWindow.refreshData(cslist, 0);
                spinerPopWindow.showAsDropDown(tt_cs);
                break;
            case R.id.tt_qcl:
                selectedType = 12;
                spinerPopWindow.setWidth(tt_qcl.getWidth());
                spinerPopWindow.refreshData(qcllist, 0);
                spinerPopWindow.showAsDropDown(tt_qcl);
                break;

        }
    }

    @Override
    protected void onInitLayoutAfter() {
        title.setText("用户档案详情");
        add.setText("");
        submit.setText("修改");
        submit.setVisibility(View.GONE);
        spinerPopWindow = new SpinerPopWindow(getContext());
        spinerPopWindow.setItemListener(this);
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.GET_BASIC_INFORMATION_DETAILS,getIntent().getStringExtra("id") ), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {

                try {
                    L.d(TAG, "data == "+data.toString());
                    et_name.setText(data.getString("realName"));
                    tt_beathday.setText(data.getString("birthday"));
                    String sex = data.getString("gender");

                    if(sex.equals("1")){
                        tt_sex.setText("男");
                    }else if(sex.equals("2")){
                        tt_sex.setText("女");
                    }
                    else if(sex.equals("9")){
                        tt_sex.setText("未说明的性别");
                    }
                    else if(sex.equals("0")){
                        tt_sex.setText("未知的性别");
                    }
                    et_idcard.setText(data.getString("idCardNumber"));
                    et_workunit.setText(data.getString("workUnit"));
                    et_phonenumber.setText(data.getString("phoneNumber"));
                    if(data.has("emergencyContact"))
                        et_contactsname.setText(data.getString("emergencyContact"));
                    if(data.has("emergencyContactPhpone"))
                        et_contactsphonenumber.setText(data.getString("emergencyContactPhpone"));
                    String czlxstr = data.getString("residentType");
                    if(czlxstr.equals("1")){
                        tt_household.setText("户籍");
                    }else if(czlxstr.equals("2")){
                        tt_household.setText("非户籍");
                    }
                    if(data.getString("nation").substring(0,2).equals("01")){
                        mz_rb1.setChecked(true);
                    }else{
                        mz_rb2.setChecked(true);
                        mz_et_name.setVisibility(View.VISIBLE);
                        mz_et_name.setText(data.getString("nation").substring(3,data.getString("nation").length()));
                    }

                    String xxstr = data.getString("bloodType");
                    if(xxstr.equals("1")){
                        tt_bloodtype.setText("A型");
                    }else if(xxstr.equals("2")){
                        tt_bloodtype.setText("B型");
                    }
                    else if(xxstr.equals("3")){
                        tt_bloodtype.setText("O型");
                    }
                    else if(xxstr.equals("4")){
                        tt_bloodtype.setText("AB型");
                    }
                    else if(xxstr.equals("5")){
                        tt_bloodtype.setText("不详");
                    }
                    String rhstr = data.getString("rh");
                    if(rhstr.equals("1")){
                        tt_rh.setText("阴性");
                    }else if(rhstr.equals("2")){
                        tt_rh.setText("阳性");
                    }
                    else if(rhstr.equals("3")){
                        tt_rh.setText("不详");
                    }
                    String whcdstr = data.getString("degreeOfEducation");
                    if(whcdstr.equals("1")){
                        tt_education.setText("研究生");
                    }else if(whcdstr.equals("2")){
                        tt_education.setText("大学本科");
                    }
                    else if(whcdstr.equals("3")){
                        tt_education.setText("大学专科和专科学校");
                    }
                    else if(whcdstr.equals("4")){
                        tt_education.setText("中等专业学校");
                    }
                    else if(whcdstr.equals("5")){
                        tt_education.setText("技工学校");
                    }
                    else if(whcdstr.equals("6")){
                        tt_education.setText("高中");
                    }
                    else if(whcdstr.equals("7")){
                        tt_education.setText("初中");
                    }
                    else if(whcdstr.equals("8")){
                        tt_education.setText("小学");
                    }
                    else if(whcdstr.equals("9")){
                        tt_education.setText("文盲或半文盲");
                    }
                    else if(whcdstr.equals("10")){
                        tt_education.setText("不详");
                    }
                    String hyzkstr = data.getString("marriage");
                    if(hyzkstr.equals("1")){
                        tt_marriage.setText("未婚");
                    }else if(hyzkstr.equals("2")){
                        tt_marriage.setText("已婚");
                    }
                    else if(hyzkstr.equals("3")){
                        tt_marriage.setText("丧偶");
                    }
                    else if(hyzkstr.equals("4")){
                        tt_marriage.setText("离婚");
                    }
                    else if(hyzkstr.equals("5")){
                        tt_marriage.setText("未说明的婚姻状况");
                    }

                    String zystr = data.getString("occupation");
                    if(zystr.equals("0")){
                        tt_ocupation.setText("国家机关、党群组织、企业、事业单位负责人");
                    }else if(zystr.equals("1")){
                        tt_ocupation.setText("专业技术人员");
                    }else if(zystr.equals("2")){
                        tt_ocupation.setText("办事人员和有关人员");
                    }else if(zystr.equals("3")){
                        tt_ocupation.setText("商业、服务业人员");
                    }else if(zystr.equals("4")){
                        tt_ocupation.setText("农、林、牧、渔、水利业生产人员");
                    }else if(zystr.equals("5")){
                        tt_ocupation.setText("生产、运输设备操作人员及有关人员");
                    }else if(zystr.equals("6")){
                        tt_ocupation.setText("军人");
                    }else if(zystr.equals("7")){
                        tt_ocupation.setText("不便分类的其他从业人员");
                    }else if(zystr.equals("8")){
                        tt_ocupation.setText("无职业");
                    }
                    String ylfyzffsstr = data.getString("medicalPaymentMode");
                    if(!ylfyzffsstr.equals("")){
                        String [] ylfyzffsstrs = ylfyzffsstr.split(",");
                        for (int i = 0; i < ylfyzffsstrs.length; i++) {
                            ylfyzffsbos.get(Integer.parseInt(ylfyzffsstrs[i].substring(0,1))-1).setChecked(true);
                            if(Integer.parseInt(ylfyzffsstrs[i].substring(0,1)) == 8){
                                paytype_cb8_et.setVisibility(View.VISIBLE);
                                paytype_cb8_et.setText(ylfyzffsstrs[i].substring(2,ylfyzffsstrs[i].length()));
                            }

                        }
                    }
                    String ywgmsstr = data.getString("historyOfDrugAllergy");
                    if(!ywgmsstr.equals("")){
                        String [] ywgmsstrs = ywgmsstr.split(",");
                        for (int i = 0; i < ywgmsstrs.length; i++) {
                            ywgmsbos.get(Integer.parseInt(ywgmsstrs[i].substring(0,1))-1).setChecked(true);
                            if(Integer.parseInt(ywgmsstrs[i].substring(0,1)) == 5){
                                ywgms_cb5_et.setVisibility(View.VISIBLE);
                                ywgms_cb5_et.setText(ywgmsstrs[i].substring(2,ywgmsstrs[i].length()));
                            }

                        }
                    }
                    String blsstr = data.getString("expose");
                    if(!blsstr.equals("")){
                        String [] blsstrs = blsstr.split(",");
                        for (int i = 0; i < blsstrs.length; i++) {
                            blsbos.get(Integer.parseInt(blsstrs[i].substring(0,1))-1).setChecked(true);
                        }
                    }
                    JSONArray arrayjb = data.getJSONArray("userDiseaseManagementJson");
                    for (int i = 0; i < arrayjb.length(); i++) {
                        jbbos.get(Integer.parseInt(arrayjb.getJSONObject(i).getString("diseaseCode")) - 1).setChecked(true);
                        int num = Integer.parseInt(arrayjb.getJSONObject(i).getString("diseaseCode"));
                        if(num == 2){
                            jb_cb2_time.setVisibility(View.VISIBLE);
                            jb_cb2_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }else if(num == 3){
                            jb_cb3_time.setVisibility(View.VISIBLE);
                            jb_cb3_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }else if(num == 4){
                            jb_cb4_time.setVisibility(View.VISIBLE);
                            jb_cb4_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }
                        else if(num == 5){
                            jb_cb5_time.setVisibility(View.VISIBLE);
                            jb_cb5_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }
                        else if(num == 6){
                            jb_cb6_time.setVisibility(View.VISIBLE);
                            jb_cb6_et_name.setVisibility(View.VISIBLE);
                            jb_cb6_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                            jb_cb6_et_name.setText(arrayjb.getJSONObject(i).getString("diseaseName"));
                        }
                        else if(num == 7){
                            jb_cb7_time.setVisibility(View.VISIBLE);
                            jb_cb7_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }
                        else if(num == 8){
                            jb_cb8_time.setVisibility(View.VISIBLE);
                            jb_cb8_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }
                        else if(num == 9){
                            jb_cb9_time.setVisibility(View.VISIBLE);
                            jb_cb9_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }
                        else if(num == 10){
                            jb_cb10_time.setVisibility(View.VISIBLE);
                            jb_cb10_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }
                        else if(num == 11){
                            jb_cb11_time.setVisibility(View.VISIBLE);
                            jb_cb11_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                        }
                        else if(num == 12){
                            jb_cb12_time.setVisibility(View.VISIBLE);
                            jb_cb12_et_name.setVisibility(View.VISIBLE);
                            jb_cb12_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                            jb_cb12_et_name.setText(arrayjb.getJSONObject(i).getString("diseaseName"));
                        }
                        else if(num == 13){
                            jb_cb13_time.setVisibility(View.VISIBLE);
                            jb_cb13_et_name.setVisibility(View.VISIBLE);
                            jb_cb13_time.setText(arrayjb.getJSONObject(i).getString("diagnosisTime"));
                            jb_cb13_et_name.setText(arrayjb.getJSONObject(i).getString("diseaseName"));
                        }
                    }


                    JSONArray array = data.getJSONArray("operationJson");
                    if(array.length()>0){
                        lay_ss.setVisibility(View.VISIBLE);
                        ss_rb2.setChecked(true);
                        ss_tt_time1.setText( array.getJSONObject(0).getString("operationTime"));
                        ss_et_name1.setText( array.getJSONObject(0).getString("operationName"));
                        if(array.length()>1){
                            ss_tt_time2.setText( array.getJSONObject(1).getString("operationTime"));
                            ss_et_name2.setText( array.getJSONObject(1).getString("operationName"));
                        }
                    }else{
                        ss_rb1.setChecked(true);
                    }
                    JSONArray arrayws = data.getJSONArray("traumaJson");
                    if(arrayws.length()>0){
                        lay_ws.setVisibility(View.VISIBLE);
                        ws_rb2.setChecked(true);
                        ws_tt_time1.setText( arrayws.getJSONObject(0).getString("traumaTime"));
                        ws_et_name1.setText( arrayws.getJSONObject(0).getString("traumaName"));
                        if(arrayws.length()>1){
                            ws_tt_time2.setText( arrayws.getJSONObject(1).getString("traumaTime"));
                            ws_et_name2.setText( arrayws.getJSONObject(1).getString("traumaName"));
                        }
                    }else{
                        ws_rb1.setChecked(true);
                    }
                    JSONArray arraysx = data.getJSONArray("bloodTransfusionJson");
                    if(arraysx.length()>0){
                        lay_sx.setVisibility(View.VISIBLE);
                        sx_et_name1.setVisibility(View.VISIBLE);
                        sx_et_name2.setVisibility(View.VISIBLE);
                        sx_tt_time1.setVisibility(View.VISIBLE);
                        sx_tt_time2.setVisibility(View.VISIBLE);
                        sx_rb2.setChecked(true);
                        sx_tt_time1.setText( arraysx.getJSONObject(0).getString("bloodTime"));
                        sx_et_name1.setText( arraysx.getJSONObject(0).getString("bloodName"));
                        if(arraysx.length()>1){
                            sx_tt_time2.setText( arraysx.getJSONObject(1).getString("bloodTime"));
                            sx_et_name2.setText( arraysx.getJSONObject(1).getString("bloodName"));
                        }
                    }else{
                        sx_rb1.setChecked(true);
                    }

                    String fqstr = data.getString("familyHistoryFather");
                    if(!fqstr.equals("")){
                        String [] fqstrs = fqstr.split(",");

                        for (int i = 0; i < fqstrs.length; i++) {
                            if(fqstrs[i].contains("_")){
                                fqbos.get(Integer.parseInt(fqstrs[i].split("_")[0])-1).setChecked(true);
                                fq_cb12_et.setVisibility(View.VISIBLE);
                                fq_cb12_et.setText(fqstrs[i].split("_")[1]);
                            }else{
                                fqbos.get(Integer.parseInt(fqstrs[i])-1).setChecked(true);
                            }
                        }
                    }
                    String mqstr = data.getString("familyHistoryMother");
                    if(!mqstr.equals("")){
                        String [] mqstrs = mqstr.split(",");
                        for (int i = 0; i < mqstrs.length; i++) {
                            if(mqstrs[i].contains("_")){
                                mqbos.get(Integer.parseInt(mqstrs[i].split("_")[0])-1).setChecked(true);
                                mq_cb12_et.setVisibility(View.VISIBLE);
                                mq_cb12_et.setText(mqstrs[i].split("_")[1]);
                            }else{
                                mqbos.get(Integer.parseInt(mqstrs[i])-1).setChecked(true);
                            }
                        }
                    }
                    String xdjmstr = data.getString("familyHistoryBorther");
                    if(!xdjmstr.equals("")){
                        String [] xdjmstrs = xdjmstr.split(",");
                        for (int i = 0; i < xdjmstrs.length; i++) {
                            if(xdjmstrs[i].contains("_")){
                                xdjmbos.get(Integer.parseInt(xdjmstrs[i].split("_")[0])-1).setChecked(true);
                                xdjm_cb12_et.setVisibility(View.VISIBLE);
                                xdjm_cb12_et.setText(xdjmstrs[i].split("_")[1]);
                            }else{
                                xdjmbos.get(Integer.parseInt(xdjmstrs[i])-1).setChecked(true);
                            }
                        }
                    }
                    String znstr = data.getString("familyHistorySon");
                    if(!znstr.equals("")){
                        String [] znstrs = znstr.split(",");
                        for (int i = 0; i < znstrs.length; i++) {
                            if(znstrs[i].contains("_")){
                                znbos.get(Integer.parseInt(znstrs[i].split("_")[0])-1).setChecked(true);
                                zn_cb12_et.setVisibility(View.VISIBLE);
                                zn_cb12_et.setText(znstrs[i].split("_")[1]);
                            }else{
                                znbos.get(Integer.parseInt(znstrs[i])-1).setChecked(true);
                            }
                        }
                    }
                    if(data.getString("geneticHistory").substring(0,1).equals("1")){
                        ycbs_rb1.setChecked(true);
                    }else{
                        ycbs_rb2.setChecked(true);
                        ycbs_rb_name.setVisibility(View.VISIBLE);
                        ycbs_rb_name.setText(data.getString("geneticHistory").substring(2,data.getString("geneticHistory").length()));
                    }
                    String cjqkstr = data.getString("disability");
                    if(!cjqkstr.equals("")){
                        String [] cjqkstrs = cjqkstr.split(",");
                        for (int i = 0; i < cjqkstrs.length; i++) {
                            cjqkbos.get(Integer.parseInt(cjqkstrs[i].substring(0,1))-1).setChecked(true);
                            if(Integer.parseInt(cjqkstrs[i].substring(0,1)) == 8){
                                cjqk_cb8_et.setVisibility(View.VISIBLE);
                                cjqk_cb8_et.setText(cjqkstrs[i].substring(2,cjqkstrs[i].length()));
                            }

                        }
                    }

                    String cfpfssstr = data.getString("kitchenVentilationFacilities");
                    if(cfpfssstr.equals("1")){
                        tt_cfpfss.setText("无");
                    }else if(cfpfssstr.equals("2")){
                        tt_cfpfss.setText("油烟机");
                    }else if(cfpfssstr.equals("3")){
                        tt_cfpfss.setText("换气扇");
                    }else if(cfpfssstr.equals("4")){
                        tt_cfpfss.setText("烟囱");
                    }
                    String rllxstr = data.getString("fuelType");
                    if(rllxstr.equals("1")){
                        tt_rllx.setText("液化气");
                    }else if(rllxstr.equals("2")){
                        tt_rllx.setText("煤");
                    }else if(rllxstr.equals("3")){
                        tt_rllx.setText("天然气");
                    }else if(rllxstr.equals("4")){
                        tt_rllx.setText("沼气");
                    }else if(rllxstr.equals("5")){
                        tt_rllx.setText("柴火");
                    }else if(rllxstr.equals("6")){
                        tt_rllx.setText("其他");
                    }

                    String ysstr = data.getString("drinkingWater");
                    if(ysstr.equals("1")){
                        tt_ys.setText("自来水");
                    }else if(ysstr.equals("2")){
                        tt_ys.setText("经净化过滤的水");
                    }else if(ysstr.equals("3")){
                        tt_ys.setText("井水");
                    }else if(ysstr.equals("4")){
                        tt_ys.setText("河湖水");
                    }else if(ysstr.equals("5")){
                        tt_ys.setText("塘水");
                    }else if(ysstr.equals("6")){
                        tt_ys.setText("其他");
                    }

                    String csstr = data.getString("toilet");
                    if(csstr.equals("1")){
                        tt_cs.setText("卫生厕所");
                    }else if(csstr.equals("2")){
                        tt_cs.setText("一格或二格粪池式");
                    }
                    else if(csstr.equals("3")){
                        tt_cs.setText("马桶");
                    }else if(csstr.equals("4")){
                        tt_cs.setText("露天粪坑");
                    }else if(csstr.equals("5")){
                        tt_cs.setText("简易棚厕");
                    }
                    String qclstr = data.getString("aBird");
                    if(qclstr.equals("1")){
                        tt_qcl.setText("无");
                    }else if(qclstr.equals("2")){
                        tt_qcl.setText("单设");
                    }else if(qclstr.equals("3")){
                        tt_qcl.setText("室内");
                    }else if(qclstr.equals("4")){
                        tt_qcl.setText("室外");
                    }
                    if(data.has("address"))
                    et_xzz.setText(data.getString("address"));
                    if(data.has("placeOfOrigin"))
                    et_hjdz.setText(data.getString("placeOfOrigin"));
                    if(data.has("neighborhoodCommittee"))
                    et_jwh.setText(data.getString("neighborhoodCommittee"));
                    if(data.has("subdistrictOffice"))
                    et_jdb.setText(data.getString("subdistrictOffice"));
                    if(data.has("responsibleDoctor"))
                    et_zrys.setText(data.getString("responsibleDoctor"));
                    if(data.has("responsiblePhone"))
                    et_zrysdh.setText(data.getString("responsiblePhone"));
                    if(data.has("filingUnit"))
                    et_jdjg.setText(data.getString("filingUnit"));
                    if(data.has("filingUnitPhone")){
                        et_jdjgdh.setText(data.getString("filingUnitPhone"));
                    }
                    Loading.bulid(activity, null).dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Loading.bulid(activity, null).dismiss();
                }
            }
        });
        initdata();
    }

    private void initdata() {
        ylfyzffsbos.add(paytype_cb1);
        ylfyzffsbos.add(paytype_cb2);
        ylfyzffsbos.add(paytype_cb3);
        ylfyzffsbos.add(paytype_cb4);
        ylfyzffsbos.add(paytype_cb5);
        ylfyzffsbos.add(paytype_cb6);
        ylfyzffsbos.add(paytype_cb7);
        ylfyzffsbos.add(paytype_cb8);

        ywgmsbos.add(ywgms_cb1);
        ywgmsbos.add(ywgms_cb2);
        ywgmsbos.add(ywgms_cb3);
        ywgmsbos.add(ywgms_cb4);
        ywgmsbos.add(ywgms_cb5);

        blsbos.add(bls_cb1);
        blsbos.add(bls_cb2);
        blsbos.add(bls_cb3);
        blsbos.add(bls_cb4);

        jbbos.add(jb_cb1);
        jbbos.add(jb_cb2);
        jbbos.add(jb_cb3);
        jbbos.add(jb_cb4);
        jbbos.add(jb_cb5);
        jbbos.add(jb_cb6);
        jbbos.add(jb_cb7);
        jbbos.add(jb_cb8);
        jbbos.add(jb_cb9);
        jbbos.add(jb_cb10);
        jbbos.add(jb_cb11);
        jbbos.add(jb_cb12);
        jbbos.add(jb_cb13);

        fqbos.add(fq_cb1);
        fqbos.add(fq_cb2);
        fqbos.add(fq_cb3);
        fqbos.add(fq_cb4);
        fqbos.add(fq_cb5);
        fqbos.add(fq_cb6);
        fqbos.add(fq_cb7);
        fqbos.add(fq_cb8);
        fqbos.add(fq_cb9);
        fqbos.add(fq_cb10);
        fqbos.add(fq_cb11);
        fqbos.add(fq_cb12);

        mqbos.add(mq_cb1);
        mqbos.add(mq_cb2);
        mqbos.add(mq_cb3);
        mqbos.add(mq_cb4);
        mqbos.add(mq_cb5);
        mqbos.add(mq_cb6);
        mqbos.add(mq_cb7);
        mqbos.add(mq_cb8);
        mqbos.add(mq_cb9);
        mqbos.add(mq_cb10);
        mqbos.add(mq_cb11);
        mqbos.add(mq_cb12);

        xdjmbos.add(xdjm_cb1);
        xdjmbos.add(xdjm_cb2);
        xdjmbos.add(xdjm_cb3);
        xdjmbos.add(xdjm_cb4);
        xdjmbos.add(xdjm_cb5);
        xdjmbos.add(xdjm_cb6);
        xdjmbos.add(xdjm_cb7);
        xdjmbos.add(xdjm_cb8);
        xdjmbos.add(xdjm_cb9);
        xdjmbos.add(xdjm_cb10);
        xdjmbos.add(xdjm_cb11);
        xdjmbos.add(xdjm_cb12);

        znbos.add(zn_cb1);
        znbos.add(zn_cb2);
        znbos.add(zn_cb3);
        znbos.add(zn_cb4);
        znbos.add(zn_cb5);
        znbos.add(zn_cb6);
        znbos.add(zn_cb7);
        znbos.add(zn_cb8);
        znbos.add(zn_cb9);
        znbos.add(zn_cb10);
        znbos.add(zn_cb11);
        znbos.add(zn_cb12);

        cjqkbos.add(cjqk_cb1);
        cjqkbos.add(cjqk_cb2);
        cjqkbos.add(cjqk_cb3);
        cjqkbos.add(cjqk_cb4);
        cjqkbos.add(cjqk_cb5);
        cjqkbos.add(cjqk_cb6);
        cjqkbos.add(cjqk_cb7);
        cjqkbos.add(cjqk_cb8);

        jbtts.add(jb_cb2_time);
        jbtts.add(jb_cb3_time);
        jbtts.add(jb_cb4_time);
        jbtts.add(jb_cb5_time);
        jbtts.add(jb_cb6_time);
        jbtts.add(jb_cb7_time);
        jbtts.add(jb_cb8_time);
        jbtts.add(jb_cb9_time);
        jbtts.add(jb_cb10_time);
        jbtts.add(jb_cb11_time);
        jbtts.add(jb_cb12_time);
        jbtts.add(jb_cb13_time);
    }

    @Override
    public void onItemClick(int pos) {
        switch (selectedType) {
            case 1:
                tt_sex.setText(xblist.get(pos));
                xbtype = xblistid[pos];
                break;
            case 2:
                tt_household.setText(czlxlist.get(pos));
                czlxtype = czlxlistid[pos];
                break;
            case 3:
                tt_bloodtype.setText(xxlist.get(pos));
                xxtype = xxlistid[pos];
                break;
            case 4:
                tt_rh.setText(rhlist.get(pos));    rhtype = rhlistid[pos];
                break;
            case 5:
                tt_education.setText(whcdlist.get(pos));    whcdtype = whcdlistid[pos];
                break;
            case 6:
                tt_marriage.setText(hyzklist.get(pos));    hyzktype = hyzklistid[pos];
                break;
            case 7:
                tt_ocupation.setText(zylist.get(pos));    zytype = zylistid[pos];
                break;
            case 8:
                tt_cfpfss.setText(cfpfsslist.get(pos));    cfpfsstype = cfpfsslistid[pos];
                break;
            case 9:
                tt_rllx.setText(rllxlist.get(pos));    rllxtype = rllxlistid[pos];
                break;
            case 10:
                tt_ys.setText(yslist.get(pos));    ystype = yslistid[pos];
                break;
            case 11:
                tt_cs.setText(cslist.get(pos));    cstype = cslistid[pos];
                break;
            case 12:
                tt_qcl.setText(qcllist.get(pos));   qcltype = qcllistid[pos];
                break;
        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }

    List<String> xblist = new ArrayList<String>() {
        public List<String> $() {
            add("男");
            add("女");
            add("未说明的性别");
            add("未知的性别");
            return this;
        }
    }.$();
    int[] xblistid = new int[]{1, 2, 9, 0};
    List<String> czlxlist = new ArrayList<String>() {
        public List<String> $() {
            add("户籍");
            add("非户籍");
            return this;
        }
    }.$();
    int[] czlxlistid = new int[]{1, 2};
    List<String> xxlist = new ArrayList<String>() {
        public List<String> $() {
            add("A型");
            add("B型");
            add("O型");
            add("AB型");
            add("不详");
            return this;
        }
    }.$(); int[] xxlistid = new int[]{1, 2, 3, 4, 5};
    List<String> rhlist = new ArrayList<String>() {
        public List<String> $() {
            add("阴性");
            add("阳性");
            add("不详");
            return this;
        }
    }.$(); int[] rhlistid = new int[]{1, 2, 3};
    List<String> whcdlist = new ArrayList<String>() {
        public List<String> $() {
            add("研究生");
            add("大学本科");
            add("大学专科和专科学校");
            add("中等专业学校");
            add("技工学校");
            add("高中");
            add("初中");
            add("小学");
            add("文盲或半文盲");
            add("不详");
            return this;
        }
    }.$(); int[] whcdlistid = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    List<String> zylist = new ArrayList<String>() {
        public List<String> $() {
            add("国家机关、党群组织、企业、事业单位负责人");
            add("专业技术人员");
            add("办事人员和有关人员");
            add("商业、服务业人员");
            add("农、林、牧、渔、水利业生产人员");
            add("生产、运输设备操作人员及有关人员");
            add("军人");
            add("不便分类的其他从业人员");
            add("无职业");
            return this;
        }
    }.$(); int[] zylistid = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    List<String> hyzklist = new ArrayList<String>() {
        public List<String> $() {
            add("未婚");
            add("已婚");
            add("丧偶");
            add("离婚");
            add("未说明的婚姻状况");

            return this;
        }
    }.$(); int[] hyzklistid = new int[]{1, 2, 3, 4, 5};
    List<String> cfpfsslist = new ArrayList<String>() {
        public List<String> $() {
            add("无");
            add("油烟机");
            add("换气扇");
            add("烟囱");
            return this;
        }
    }.$(); int[] cfpfsslistid = new int[]{1, 2, 3, 4};
    List<String> rllxlist = new ArrayList<String>() {
        public List<String> $() {
            add("液化气");
            add("煤");
            add("天然气");
            add("沼气");
            add("柴火");
            add("其他");
            return this;
        }
    }.$(); int[] rllxlistid = new int[]{1, 2, 3, 4, 5, 6};
    List<String> yslist = new ArrayList<String>() {
        public List<String> $() {
            add("自来水");
            add("经净化过滤的水");
            add("井水");
            add("河湖水");
            add("塘水");
            add("其他");
            return this;
        }
    }.$(); int[] yslistid =  new int[]{1, 2, 3, 4, 5, 6};
    List<String> cslist = new ArrayList<String>() {
        public List<String> $() {
            add("卫生厕所");
            add("一格或二格粪池式");
            add("马桶");
            add("露天粪坑");
            add("简易棚厕");
            return this;
        }
    }.$(); int[] cslistid = new int[]{1, 2, 3, 4, 5};
    List<String> qcllist = new ArrayList<String>() {
        public List<String> $() {
            add("无");
            add("单设");
            add("室内");
            add("室外");
            return this;
        }
    }.$(); int[] qcllistid = new int[]{1, 2, 3, 4};
    //基本信息
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.tt_sex)
    private TextView tt_sex;
    @ViewInject(R.id.tt_beathday)
    private TextView tt_beathday;
    @ViewInject(R.id.et_idcard)
    private EditText et_idcard;
    @ViewInject(R.id.et_workunit)
    private EditText et_workunit;
    @ViewInject(R.id.et_phonenumber)
    private EditText et_phonenumber;
    @ViewInject(R.id.et_contactsname)
    private EditText et_contactsname;
    @ViewInject(R.id.et_contactsphonenumber)
    private EditText et_contactsphonenumber;
    @ViewInject(R.id.tt_household)
    private TextView tt_household;
    @ViewInject(R.id.mz_rb1)
    private RadioButton mz_rb1;
    @ViewInject(R.id.mz_rb2)
    private RadioButton mz_rb2;
    @ViewInject(R.id.mz_et_name)
    private EditText mz_et_name;
    @ViewInject(R.id.tt_bloodtype)
    private TextView tt_bloodtype;
    @ViewInject(R.id.tt_rh)
    private TextView tt_rh;
    @ViewInject(R.id.tt_education)
    private TextView tt_education;
    @ViewInject(R.id.tt_marriage)
    private TextView tt_marriage;
    @ViewInject(R.id.tt_ocupation)
    private TextView tt_ocupation;

    //支付方式
    @ViewInject(R.id.paytype_cb1)
    private CheckBox paytype_cb1;
    @ViewInject(R.id.paytype_cb2)
    private CheckBox paytype_cb2;
    @ViewInject(R.id.paytype_cb3)
    private CheckBox paytype_cb3;
    @ViewInject(R.id.paytype_cb4)
    private CheckBox paytype_cb4;
    @ViewInject(R.id.paytype_cb5)
    private CheckBox paytype_cb5;
    @ViewInject(R.id.paytype_cb6)
    private CheckBox paytype_cb6;
    @ViewInject(R.id.paytype_cb7)
    private CheckBox paytype_cb7;
    @ViewInject(R.id.paytype_cb8)
    private CheckBox paytype_cb8;
    @ViewInject(R.id.paytype_cb8_et)
    private EditText paytype_cb8_et;

    //药物过敏史
    @ViewInject(R.id.ywgms_cb1)
    private CheckBox ywgms_cb1;
    @ViewInject(R.id.ywgms_cb2)
    private CheckBox ywgms_cb2;
    @ViewInject(R.id.ywgms_cb3)
    private CheckBox ywgms_cb3;
    @ViewInject(R.id.ywgms_cb4)
    private CheckBox ywgms_cb4;
    @ViewInject(R.id.ywgms_cb5)
    private CheckBox ywgms_cb5;
    @ViewInject(R.id.ywgms_cb5_et)
    private EditText ywgms_cb5_et;

    //暴露史
    @ViewInject(R.id.bls_cb1)
    private CheckBox bls_cb1;
    @ViewInject(R.id.bls_cb2)
    private CheckBox bls_cb2;
    @ViewInject(R.id.bls_cb3)
    private CheckBox bls_cb3;
    @ViewInject(R.id.bls_cb4)
    private CheckBox bls_cb4;

    //疾病
    @ViewInject(R.id.jb_cb1)
    private CheckBox jb_cb1;
    @ViewInject(R.id.jb_cb2)
    private CheckBox jb_cb2;
    @ViewInject(R.id.jb_cb2_time)
    private TextView jb_cb2_time;
    @ViewInject(R.id.jb_cb3)
    private CheckBox jb_cb3;
    @ViewInject(R.id.jb_cb3_time)
    private TextView jb_cb3_time;
    @ViewInject(R.id.jb_cb4)
    private CheckBox jb_cb4;
    @ViewInject(R.id.jb_cb4_time)
    private TextView jb_cb4_time;
    @ViewInject(R.id.jb_cb5)
    private CheckBox jb_cb5;
    @ViewInject(R.id.jb_cb5_time)
    private TextView jb_cb5_time;
    @ViewInject(R.id.jb_cb6)
    private CheckBox jb_cb6;
    @ViewInject(R.id.jb_cb6_time)
    private TextView jb_cb6_time;
    @ViewInject(R.id.jb_cb6_et_name)
    private EditText jb_cb6_et_name;
    @ViewInject(R.id.jb_cb7)
    private CheckBox jb_cb7;
    @ViewInject(R.id.jb_cb7_time)
    private TextView jb_cb7_time;
    @ViewInject(R.id.jb_cb8)
    private CheckBox jb_cb8;
    @ViewInject(R.id.jb_cb8_time)
    private TextView jb_cb8_time;
    @ViewInject(R.id.jb_cb9)
    private CheckBox jb_cb9;
    @ViewInject(R.id.jb_cb9_time)
    private TextView jb_cb9_time;
    @ViewInject(R.id.jb_cb10)
    private CheckBox jb_cb10;
    @ViewInject(R.id.jb_cb10_time)
    private TextView jb_cb10_time;
    @ViewInject(R.id.jb_cb11)
    private CheckBox jb_cb11;
    @ViewInject(R.id.jb_cb11_time)
    private TextView jb_cb11_time;
    @ViewInject(R.id.jb_cb12)
    private CheckBox jb_cb12;
    @ViewInject(R.id.jb_cb12_time)
    private TextView jb_cb12_time;
    @ViewInject(R.id.jb_cb12_et_name)
    private EditText jb_cb12_et_name;
    @ViewInject(R.id.jb_cb13)
    private CheckBox jb_cb13;
    @ViewInject(R.id.jb_cb13_time)
    private TextView jb_cb13_time;
    @ViewInject(R.id.jb_cb13_et_name)
    private EditText jb_cb13_et_name;

    //手术
    @ViewInject(R.id.lay_ss)
    private LinearLayout lay_ss;
    @ViewInject(R.id.ss_rb1)
    private RadioButton ss_rb1;
    @ViewInject(R.id.ss_rb2)
    private RadioButton ss_rb2;
    @ViewInject(R.id.ss_tt_time1)
    private TextView ss_tt_time1;
    @ViewInject(R.id.ss_et_name1)
    private EditText ss_et_name1;
    @ViewInject(R.id.ss_tt_time2)
    private TextView ss_tt_time2;
    @ViewInject(R.id.ss_et_name2)
    private EditText ss_et_name2;

    //外伤
    @ViewInject(R.id.lay_ws)
    private LinearLayout lay_ws;
    @ViewInject(R.id.ws_rb1)
    private RadioButton ws_rb1;
    @ViewInject(R.id.ws_rb2)
    private RadioButton ws_rb2;
    @ViewInject(R.id.ws_tt_time1)
    private TextView ws_tt_time1;
    @ViewInject(R.id.ws_et_name1)
    private EditText ws_et_name1;
    @ViewInject(R.id.ws_tt_time2)
    private TextView ws_tt_time2;
    @ViewInject(R.id.ws_et_name2)
    private EditText ws_et_name2;

    //输血
    @ViewInject(R.id.lay_sx)
    private LinearLayout lay_sx;
    @ViewInject(R.id.sx_rb1)
    private RadioButton sx_rb1;
    @ViewInject(R.id.sx_rb2)
    private RadioButton sx_rb2;
    @ViewInject(R.id.sx_tt_time1)
    private TextView sx_tt_time1;
    @ViewInject(R.id.sx_et_name1)
    private EditText sx_et_name1;
    @ViewInject(R.id.sx_tt_time2)
    private TextView sx_tt_time2;
    @ViewInject(R.id.sx_et_name2)
    private EditText sx_et_name2;

    //父亲
    @ViewInject(R.id.fq_cb1)
    private CheckBox fq_cb1;
    @ViewInject(R.id.fq_cb2)
    private CheckBox fq_cb2;
    @ViewInject(R.id.fq_cb3)
    private CheckBox fq_cb3;
    @ViewInject(R.id.fq_cb4)
    private CheckBox fq_cb4;
    @ViewInject(R.id.fq_cb5)
    private CheckBox fq_cb5;
    @ViewInject(R.id.fq_cb6)
    private CheckBox fq_cb6;
    @ViewInject(R.id.fq_cb7)
    private CheckBox fq_cb7;
    @ViewInject(R.id.fq_cb8)
    private CheckBox fq_cb8;
    @ViewInject(R.id.fq_cb9)
    private CheckBox fq_cb9;
    @ViewInject(R.id.fq_cb10)
    private CheckBox fq_cb10;
    @ViewInject(R.id.fq_cb11)
    private CheckBox fq_cb11;
    @ViewInject(R.id.fq_cb12)
    private CheckBox fq_cb12;
    @ViewInject(R.id.fq_cb12_et)
    private EditText fq_cb12_et;

    //母亲
    @ViewInject(R.id.mq_cb1)
    private CheckBox mq_cb1;
    @ViewInject(R.id.mq_cb2)
    private CheckBox mq_cb2;
    @ViewInject(R.id.mq_cb3)
    private CheckBox mq_cb3;
    @ViewInject(R.id.mq_cb4)
    private CheckBox mq_cb4;
    @ViewInject(R.id.mq_cb5)
    private CheckBox mq_cb5;
    @ViewInject(R.id.mq_cb6)
    private CheckBox mq_cb6;
    @ViewInject(R.id.mq_cb7)
    private CheckBox mq_cb7;
    @ViewInject(R.id.mq_cb8)
    private CheckBox mq_cb8;
    @ViewInject(R.id.mq_cb9)
    private CheckBox mq_cb9;
    @ViewInject(R.id.mq_cb10)
    private CheckBox mq_cb10;
    @ViewInject(R.id.mq_cb11)
    private CheckBox mq_cb11;
    @ViewInject(R.id.mq_cb12)
    private CheckBox mq_cb12;
    @ViewInject(R.id.mq_cb12_et)
    private EditText mq_cb12_et;

    //兄弟姐妹
    @ViewInject(R.id.xdjm_cb1)
    private CheckBox xdjm_cb1;
    @ViewInject(R.id.xdjm_cb2)
    private CheckBox xdjm_cb2;
    @ViewInject(R.id.xdjm_cb3)
    private CheckBox xdjm_cb3;
    @ViewInject(R.id.xdjm_cb4)
    private CheckBox xdjm_cb4;
    @ViewInject(R.id.xdjm_cb5)
    private CheckBox xdjm_cb5;
    @ViewInject(R.id.xdjm_cb6)
    private CheckBox xdjm_cb6;
    @ViewInject(R.id.xdjm_cb7)
    private CheckBox xdjm_cb7;
    @ViewInject(R.id.xdjm_cb8)
    private CheckBox xdjm_cb8;
    @ViewInject(R.id.xdjm_cb9)
    private CheckBox xdjm_cb9;
    @ViewInject(R.id.xdjm_cb10)
    private CheckBox xdjm_cb10;
    @ViewInject(R.id.xdjm_cb11)
    private CheckBox xdjm_cb11;
    @ViewInject(R.id.xdjm_cb12)
    private CheckBox xdjm_cb12;
    @ViewInject(R.id.xdjm_cb12_et)
    private EditText xdjm_cb12_et;

    //子女
    @ViewInject(R.id.zn_cb1)
    private CheckBox zn_cb1;
    @ViewInject(R.id.zn_cb2)
    private CheckBox zn_cb2;
    @ViewInject(R.id.zn_cb3)
    private CheckBox zn_cb3;
    @ViewInject(R.id.zn_cb4)
    private CheckBox zn_cb4;
    @ViewInject(R.id.zn_cb5)
    private CheckBox zn_cb5;
    @ViewInject(R.id.zn_cb6)
    private CheckBox zn_cb6;
    @ViewInject(R.id.zn_cb7)
    private CheckBox zn_cb7;
    @ViewInject(R.id.zn_cb8)
    private CheckBox zn_cb8;
    @ViewInject(R.id.zn_cb9)
    private CheckBox zn_cb9;
    @ViewInject(R.id.zn_cb10)
    private CheckBox zn_cb10;
    @ViewInject(R.id.zn_cb11)
    private CheckBox zn_cb11;
    @ViewInject(R.id.zn_cb12)
    private CheckBox zn_cb12;
    @ViewInject(R.id.zn_cb12_et)
    private EditText zn_cb12_et;

    //遗传病史
    @ViewInject(R.id.ycbs_rb1)
    private RadioButton ycbs_rb1;
    @ViewInject(R.id.ycbs_rb2)
    private RadioButton ycbs_rb2;
    @ViewInject(R.id.ycbs_rb_name)
    private EditText ycbs_rb_name;

    //残疾情况
    @ViewInject(R.id.cjqk_cb1)
    private CheckBox cjqk_cb1;
    @ViewInject(R.id.cjqk_cb2)
    private CheckBox cjqk_cb2;
    @ViewInject(R.id.cjqk_cb3)
    private CheckBox cjqk_cb3;
    @ViewInject(R.id.cjqk_cb4)
    private CheckBox cjqk_cb4;
    @ViewInject(R.id.cjqk_cb5)
    private CheckBox cjqk_cb5;
    @ViewInject(R.id.cjqk_cb6)
    private CheckBox cjqk_cb6;
    @ViewInject(R.id.cjqk_cb7)
    private CheckBox cjqk_cb7;
    @ViewInject(R.id.cjqk_cb8)
    private CheckBox cjqk_cb8;
    @ViewInject(R.id.cjqk_cb8_et)
    private EditText cjqk_cb8_et;

    //生活环境
    @ViewInject(R.id.tt_cfpfss)
    private TextView tt_cfpfss;
    @ViewInject(R.id.tt_rllx)
    private TextView tt_rllx;
    @ViewInject(R.id.tt_ys)
    private TextView tt_ys;
    @ViewInject(R.id.tt_cs)
    private TextView tt_cs;
    @ViewInject(R.id.tt_qcl)
    private TextView tt_qcl;

    //新增
    @ViewInject(R.id.et_xzz)
    private EditText et_xzz;
    @ViewInject(R.id.et_hjdz)
    private EditText et_hjdz;
    @ViewInject(R.id.et_jwh)
    private EditText et_jwh;
    @ViewInject(R.id.et_jdb)
    private EditText et_jdb;
    @ViewInject(R.id.et_zrys)
    private EditText et_zrys;
    @ViewInject(R.id.et_zrysdh)
    private EditText et_zrysdh;
    @ViewInject(R.id.et_jdjg)
    private EditText et_jdjg;
    @ViewInject(R.id.et_jdjgdh)
    private EditText et_jdjgdh;

    @ViewInject(R.id.submit)
    private TextView submit;

}
