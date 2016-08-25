package com.uyi.app.ui.common;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.HealthInfo;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.common.adapter.MedicineAdapter;
import com.uyi.app.ui.common.adapter.OutAdapter;
import com.uyi.app.ui.common.model.AbnormalEvent;
import com.uyi.app.ui.common.model.Health;
import com.uyi.app.ui.common.model.Medicine;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * 修改资料
 *
 * @author user
 */
@ContentView(R.layout.register_inf0_one)
public class RegisterInfoAcitivity extends BaseActivity {
    private LayoutInflater mInflater;
    int index = 1;//fragment页码

    @ViewInject(R.id.headerView)
    private HeaderView headerView;

    //	@ViewInject(R.id.register_info_one) 				private ScrollView register_info_one;
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

    @ViewInject(R.id.register_info_three)
    private LinearLayout register_info_three;
    @ViewInject(R.id.register_info_guomingshi)
    private EditText register_info_guomingshi;
    @ViewInject(R.id.register_info_chengyingdeyaowu)
    private EditText register_info_chengyingdeyaowu;
    @ViewInject(R.id.addmedicationsituation)
    private TextView addmedicationsituation;
    @ViewInject(R.id.register_info_three_submit)
    private Button register_info_three_submit;

    @ViewInject(R.id.register_info_four)
    private LinearLayout register_info_four;
    @ViewInject(R.id.regester_info_xueguanfashengshijian)
    private TextView regester_info_xueguanfashengshijian;
    @ViewInject(R.id.regester_info_xueguanfashengleixing)
    private Spinner regester_info_xueguanfashengleixing;
    @ViewInject(R.id.register_info_qitaleixin)
    private EditText register_info_qitaleixin;
    @ViewInject(R.id.register_info_four_submit)
    private Button register_info_four_submit;

    @ViewInject(R.id.register_info_muqianfuyaoqingkuang)
    private EditText register_info_muqianfuyaoqingkuang;
    @ViewInject(R.id.register_info_qitabuchongqingkuang)
    private EditText register_info_qitabuchongqingkuang;
    @ViewInject(R.id.register_info_submit)
    private Button register_info_submit;

    @ViewInject(R.id.register_info_liucanshi_layout)
    private LinearLayout register_info_liucanshi_layout;
    @ViewInject(R.id.register_info_yuejing_layout)
    private LinearLayout register_info_yuejing_layout;

    //	@ViewInject(R.id.register_info_two) 	private LinearLayout register_info_two;
    @ViewInject(R.id.register_info_five)
    private LinearLayout register_info_five;
    @ViewInject(R.id.register_info_return)
    private Button register_info_return;

    @ViewInject(R.id.register_info_one)
    private LinearLayout register_info_one;
    @ViewInject(R.id.register_height)
    private EditText register_height;
    @ViewInject(R.id.register_weight)
    private EditText register_weight;
    @ViewInject(R.id.register_info_one_jiankangzhuangkuang)
    private EditText register_info_one_jiankangzhuangkuang;
    @ViewInject(R.id.register_info_one_submit)
    private Button register_info_one_submit;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;


    @ViewInject(R.id.add_view_layout)
    private LinearLayout add_view_layout;
    @ViewInject(R.id.add_system_out_layout)
    private LinearLayout add_system_out_layout;


    @ViewInject(R.id.yaowuName)
    private Spinner yaowuName;
    @ViewInject(R.id.yypd)
    private Spinner yypd;
    @ViewInject(R.id.timeUnit)
    private Spinner timeUnit;
    @ViewInject(R.id.valueUnit)
    private Spinner valueUnit;
    @ViewInject(R.id.regester_three_danciyonyaoliang)
    private EditText regester_three_danciyonyaoliang;
    @ViewInject(R.id.tv_start_time)
    private TextView tv_start_time;
    @ViewInject(R.id.tv_end_time)
    private TextView tv_end_time;

    private HealthInfo healthInfo;    //病人ID
    private UserInfo userInfo;
    private String gender;            //性别
    public int update = 0;            //1为更新信息

    private Health health;
    private Health.HealthInfoBean mHealthInfo;
    private List<Health.HealthInfoBean.ExternalSituationsBean> externalSituationsList;
    private List<Health.HealthInfoBean.MedicationUsingSituationsBean> medicationUsingSituationsList;

    private List<View> outViews;
    private List<View> yyViews;

    private String[] ywmString;

    private HashMap<String, Medicine> medicines = new HashMap<>();
    private HashMap<String, AbnormalEvent> abnormalEvents = new HashMap<>();


    private OutAdapter mOutAdapter;
    private MedicineAdapter mMedicineAdapter;

    @ViewInject(R.id.outRecyclerView)
    private RecyclerView outRecyclerView;

    @ViewInject(R.id.ywRecyclerView)
    private RecyclerView ywRecyclerView;

    @Override
    protected void onInitLayoutAfter() {
        update = getIntent().getIntExtra("update", 0);
        gender = getIntent().getStringExtra("gender");


        ywRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        outRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (gender != null && ValidationUtils.equlse(UYIUtils.convertGender(gender), "男")) {
            register_info_liucanshi_layout.setVisibility(View.GONE);
            register_info_yuejing_layout.setVisibility(View.GONE);
        }
        headerView.showTitle(true);
        headerView.setTitle(update == 0 ? "填写健康资料" : "修改健康资料");
        headerView.setHeaderBackgroundColor(getResources().getColor(R.color.blue));
        healthInfo = new HealthInfo();
        replaceView(1);
        mInflater = LayoutInflater.from(this);
        userInfo = UserInfoManager.getLoginUserInfo(activity);
//		if(userInfo != null){
//			Loading.bulid(activity, null).show();
//			RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Listener<JSONObject>() {
//				public void onResponse(JSONObject data) {
//					try {
//						Loading.bulid(activity, null).dismiss();
//						JSONObject healthInfo = data.getJSONObject("healthInfo");
//						register_info_gerenjiwangbinshi.setText(JSONObjectUtils.getString(healthInfo, "medical") );
//						register_info_chuanrangbingshi.setText(JSONObjectUtils.getString(healthInfo, "infection") );
//						register_info_waishang.setText(JSONObjectUtils.getString(healthInfo, "trauma"));
//						register_info_shoushushi.setText(JSONObjectUtils.getString(healthInfo, "operation") );
//
//
//						register_info_liucanshi.setText(JSONObjectUtils.getString(healthInfo, "pregnancy"));
//						register_info_yuejing.setText(JSONObjectUtils.getString(healthInfo, "menstruation"));
//
//						register_info_guomingshi.setText(JSONObjectUtils.getString(healthInfo, "allergic"));
//						register_info_shuxueshi.setText(JSONObjectUtils.getString(healthInfo, "blood"));
//						register_info_jiazhubinshi.setText(JSONObjectUtils.getString(healthInfo, "familyMedical"));
//						register_info_muqianfuyaoqingkuang.setText(JSONObjectUtils.getString(healthInfo, "current"));
//						register_info_qitabuchongqingkuang.setText(JSONObjectUtils.getString(healthInfo, "others"));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}
        health = new Health();
        mHealthInfo = new Health.HealthInfoBean();
        health.healthInfo = mHealthInfo;
        externalSituationsList = new ArrayList<>();
        medicationUsingSituationsList = new ArrayList<>();
        mHealthInfo.externalSituations = externalSituationsList;
        mHealthInfo.medicationUsingSituations = medicationUsingSituationsList;
        outViews = new ArrayList<>();
        yyViews = new ArrayList<>();

        initViews();

        requestDrowMenuData();


        outRecyclerView.setAdapter(mOutAdapter = new OutAdapter(RegisterInfoAcitivity.this, externalSituationsList, new OutAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                Health.HealthInfoBean.ExternalSituationsBean bean = externalSituationsList.get(position);
                externalSituationsList.remove(position);
                mOutAdapter.notifyItemRemoved(position);
            }
        }));
        ywRecyclerView.setAdapter(mMedicineAdapter = new MedicineAdapter(RegisterInfoAcitivity.this, medicationUsingSituationsList, new MedicineAdapter.OnItemViewListener() {
            @Override
            public void onDelete(int position) {
                Health.HealthInfoBean.MedicationUsingSituationsBean bean = medicationUsingSituationsList.get(position);
                medicationUsingSituationsList.remove(position);
                mMedicineAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onChoiceEndTime(int position, String sDate) {

            }
        }));

    }

    private void requestDrowMenuData() {
        RequestManager.getArray(Constens.ABNORMAL_EVENT, this, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                com.alibaba.fastjson.JSONArray objects = JSON.parseArray(jsonArray.toString());
                for (int i = 0; i < objects.size(); i++) {
                    AbnormalEvent event = JSON.parseObject(objects.getString(i), AbnormalEvent.class);
                    abnormalEvents.put(event.name, event);
                    regester_info_xueguanfashengleixing.setAdapter(new ArrayAdapter<>(RegisterInfoAcitivity.this, R.layout.layout_spinner_item, R.id.textView2, toStringArray(abnormalEvents.keySet())));
                }
            }
        });
        RequestManager.getArray(Constens.GET_MEDICINE, this, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                com.alibaba.fastjson.JSONArray objects = JSON.parseArray(jsonArray.toString());
                for (int i = 0; i < objects.size(); i++) {
                    Medicine event = JSON.parseObject(objects.getString(i), Medicine.class);
                    medicines.put(event.name, event);
                    yaowuName.setAdapter(new ArrayAdapter<>(RegisterInfoAcitivity.this, R.layout.layout_spinner_item, R.id.textView2, ywmString = toStringArray(medicines.keySet())));
                }
            }
        });
    }

    public void replaceView(int index) {
        this.index = index;
        register_info_one.setVisibility(View.GONE);
        register_info_two.setVisibility(View.GONE);
        register_info_three.setVisibility(View.GONE);
        register_info_four.setVisibility(View.GONE);
        register_info_five.setVisibility(View.GONE);
        if (index == 1) {
            register_info_one.setVisibility(View.VISIBLE);
        }
        if (index == 2) {
            register_info_two.setVisibility(View.VISIBLE);
        }
        if (index == 3) {
            register_info_three.setVisibility(View.VISIBLE);
        }
        if (index == 4) {
            register_info_four.setVisibility(View.VISIBLE);
        }
        if (index == 5) {
            register_info_five.setVisibility(View.VISIBLE);
        }
    }


    private void setValue(int index) {
        switch (index) {
            case 1:
                String height = register_height.getText().toString();              //身高
                String weight = register_weight.getText().toString();           //体重
                int radioButtonId = radioGroup.getCheckedRadioButtonId();           //有啥子病
                String jkzk = register_info_one_jiankangzhuangkuang.getText().toString();  //健康状况
                health.height = height;
                health.weight = weight;
                if (radioButtonId == R.id.rb_gxy) {
                    health.chronicDiseaseType = 1;
                } else if (radioButtonId == R.id.rb_tnb) {
                    health.chronicDiseaseType = 2;
                } else if (radioButtonId == R.id.rb_gxy_tnb) {
                    health.chronicDiseaseType = 3;
                }
                health.healthCondition = jkzk;
                break;
            case 2:
                String jbs = register_info_gerenjiwangbinshi.getText().toString();
                String crbs = register_info_chuanrangbingshi.getText().toString();
                String wss = register_info_waishang.getText().toString();
                String sss = register_info_shoushushi.getText().toString();
                String nxhzhr = register_info_liucanshi.getText().toString();
                String nxhzjxqk = register_info_yuejing.getText().toString();
                String zds = register_info_zhongdushi.getText().toString();
                String sxs = register_info_shuxueshi.getText().toString();
                String qtbs = register_info_qitabinshi.getText().toString();
                String jzbs = register_info_jiazhubinshi.getText().toString();
                String yfjzs = register_info_yufangjiezhonshi.getText().toString();
                String xthg = register_info_xitonghuigu.getText().toString();


                mHealthInfo.medical = jbs;
                mHealthInfo.infection = crbs;
                mHealthInfo.trauma = wss;
                mHealthInfo.operation = sss;
                mHealthInfo.pregnancy = nxhzhr;
                mHealthInfo.menstruation = nxhzjxqk;
                mHealthInfo.allergic = zds;
                mHealthInfo.blood = sxs;
                mHealthInfo.others = qtbs;
                mHealthInfo.familyMedical = jzbs;
                mHealthInfo.vaccinationHistory = yfjzs;
                mHealthInfo.retrospection = xthg;
                break;
            case 3:


                String gms = register_info_guomingshi.getText().toString();  //过敏史
                String cyy = register_info_chengyingdeyaowu.getText().toString(); //成瘾史
                mHealthInfo.historyOfAllergy = gms;
                mHealthInfo.drugAddiction = cyy;
                break;
            case 4:
                Health.HealthInfoBean.AbnormalEventJsonsBean abnormalEventJsonsBean = new Health.HealthInfoBean.AbnormalEventJsonsBean();
                String fsTime = regester_info_xueguanfashengshijian.getText().toString(); // 发生时间
                String fsType = regester_info_xueguanfashengleixing.getSelectedItem().toString(); //发生类型
                abnormalEventJsonsBean.description = register_info_qitaleixin.getText().toString();
                AbnormalEvent ae = abnormalEvents.get(fsType);
                if (ae != null) {
                    abnormalEventJsonsBean.eventType = ae.eventType;
                    abnormalEventJsonsBean.id = ae.id;
                    abnormalEventJsonsBean.name = ae.name;
                    abnormalEventJsonsBean.eventId = ae.id;
                }
                abnormalEventJsonsBean.time = fsTime;
                mHealthInfo.abnormalEventJsons = abnormalEventJsonsBean;
                break;
        }
    }

    private void initViews() {
        Holder holder = new Holder();
        holder.startTime = tv_start_time;
        holder.endTime = tv_end_time;
        holder.dcyyl = regester_three_danciyonyaoliang;
        holder.timeUnit = timeUnit;
        holder.valueUnit = valueUnit;
        holder.yaowuName = yaowuName;
        holder.yypd = yypd;
        register_info_three.setTag(holder);
        yyViews.add(register_info_three);

        OutHolder outHolder = new OutHolder();
        outHolder.dateText = (TextView) findViewById(R.id.tv_two_date);
        outHolder.msgText = (EditText) findViewById(R.id.register_info_xitongwaijiuyi);
        register_info_two.setTag(outHolder);
        outViews.add(register_info_two);
    }

    @OnClick({
            R.id.register_info_return,
            R.id.register_info_submit,
            R.id.register_info_one_submit,
            R.id.register_info_two_submit,
            R.id.register_info_three_submit,
            R.id.register_info_four_submit,
            R.id.addmedicationsituation,
            R.id.layout_start_time,
            R.id.layout_end_time,
            R.id.regester_info_xueguanfashengshijian,
            R.id.tv_two_choice_date,
            R.id.tv_add_system_out

    })
    public void onUpdate(View v) {
        switch (v.getId()) {
            case R.id.register_info_return:
                finish();
                break;
            case R.id.addmedicationsituation:
                if (checkYYBeforeIsFull()) {
                    addOneMedicineView();
                } else T.showShort(this, "请完善用药信息再试");
                break;
            case R.id.register_info_one_submit:
                replaceView(2);
                setValue(1);
                break;
            case R.id.register_info_two_submit:
                replaceView(3);
                setValue(2);
                break;
            case R.id.register_info_three_submit:
                replaceView(4);
                setValue(3);
                break;
            case R.id.register_info_four_submit:  //提交健康资料
                setValue(4);
                if (TextUtils.isEmpty(health.healthInfo.abnormalEventJsons.time)) {
                    mHealthInfo.abnormalEventJsons = null;
                }
                L.e(JSON.toJSONString(health));
                JSONObject object = null;
                try {
                    object = new JSONObject(JSON.toJSONString(health));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (update == 0) {
                    RequestManager.postObject(Constens.UPDATE_HEALTH_ZL, this, object, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            if (jsonObject.has("success")) {
                                T.showShort(RegisterInfoAcitivity.this, "添加成功");
                                replaceView(5);
                            }
                        }
                    }, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            T.showShort(RegisterInfoAcitivity.this, "请求失败，请核实数据完整性");
                            L.e(e.toString());
                            e.printStackTrace();
                        }
                    });
                }
//                if (userInfo == null) {
//                    T.showLong(activity, "获取注册信息失败!");
//                    return;
//                }
//                healthInfo.setMedical(register_info_gerenjiwangbinshi.getText().toString());
//                healthInfo.setInfection(register_info_chuanrangbingshi.getText().toString());
//                healthInfo.setTrauma(register_info_waishang.getText().toString());
//                healthInfo.setOperation(register_info_shoushushi.getText().toString());
//                healthInfo.setPregnancy(register_info_liucanshi.getText().toString());
//                healthInfo.setMenstruation(register_info_yuejing.getText().toString());
//                healthInfo.setAllergic(register_info_guomingshi.getText().toString());
//                healthInfo.setBlood(register_info_shuxueshi.getText().toString());
//                healthInfo.setFamilyMedical(register_info_jiazhubinshi.getText().toString());
//                healthInfo.setCurrent(register_info_muqianfuyaoqingkuang.getText().toString());
//                healthInfo.setOthers(register_info_qitabuchongqingkuang.getText().toString());
//                healthInfo.setUserId(userInfo.userId);
//                try {
//                    JSONObject healthInfoObject = new JSONObject();
//                    if (!ValidationUtils.isNull(healthInfo.getMedical())) {
//                        healthInfoObject.put("medical", healthInfo.getMedical());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getInfection())) {
//                        healthInfoObject.put("infection", healthInfo.getInfection());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getTrauma())) {
//                        healthInfoObject.put("trauma", healthInfo.getTrauma());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getOperation())) {
//                        healthInfoObject.put("operation", healthInfo.getOperation());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getPregnancy())) {
//                        healthInfoObject.put("pregnancy", healthInfo.getPregnancy());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getMenstruation())) {
//                        healthInfoObject.put("menstruation", healthInfo.getMenstruation());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getAllergic())) {
//                        healthInfoObject.put("allergic", healthInfo.getAllergic());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getBlood())) {
//                        healthInfoObject.put("blood", healthInfo.getBlood());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getFamilyMedical())) {
//                        healthInfoObject.put("familyMedical", healthInfo.getFamilyMedical());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getCurrent())) {
//                        healthInfoObject.put("current", healthInfo.getCurrent());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getOthers())) {
//                        healthInfoObject.put("others", healthInfo.getOthers());
//                    }
//                    JSONObject params = new JSONObject();
//                    params.put("healthInfo", healthInfoObject);
//                    RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, new Response.Listener<JSONObject>() {
//                        public void onResponse(JSONObject arg0) {
//                            if (update == 1) {
//                                T.showShort(activity, "修改成功!");
//                            } else {
//                                register_info_one.setVisibility(View.GONE);
//                                register_info_two.setVisibility(View.VISIBLE);
//                                headerView.setTitle("填写完成");
//                            }
//                        }
//                    }, new RequestErrorListener() {
//                        @Override
//                        public void requestError(VolleyError e) {
//                            if (update == 1) {
//                                T.showShort(activity, "修改成功!");
//                            } else {
//                                register_info_one.setVisibility(View.GONE);
//                                register_info_two.setVisibility(View.VISIBLE);
//                                headerView.setTitle("填写完成");
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////			replaceView(5);

                break;
//            case R.id.register_info_submit:
//                if (userInfo == null) {
//                    T.showLong(activity, "获取注册信息失败!");
//                    return;
//                }
//                healthInfo.setMedical(register_info_gerenjiwangbinshi.getText().toString());
//                healthInfo.setInfection(register_info_chuanrangbingshi.getText().toString());
//                healthInfo.setTrauma(register_info_waishang.getText().toString());
//                healthInfo.setOperation(register_info_shoushushi.getText().toString());
//                healthInfo.setPregnancy(register_info_liucanshi.getText().toString());
//                healthInfo.setMenstruation(register_info_yuejing.getText().toString());
//                healthInfo.setAllergic(register_info_guomingshi.getText().toString());
//                healthInfo.setBlood(register_info_shuxueshi.getText().toString());
//                healthInfo.setFamilyMedical(register_info_jiazhubinshi.getText().toString());
//                healthInfo.setCurrent(register_info_muqianfuyaoqingkuang.getText().toString());
//                healthInfo.setOthers(register_info_qitabuchongqingkuang.getText().toString());
//                healthInfo.setUserId(userInfo.userId);
//                try {
//                    JSONObject healthInfoObject = new JSONObject();
//                    if (!ValidationUtils.isNull(healthInfo.getMedical())) {
//                        healthInfoObject.put("medical", healthInfo.getMedical());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getInfection())) {
//                        healthInfoObject.put("infection", healthInfo.getInfection());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getTrauma())) {
//                        healthInfoObject.put("trauma", healthInfo.getTrauma());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getOperation())) {
//                        healthInfoObject.put("operation", healthInfo.getOperation());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getPregnancy())) {
//                        healthInfoObject.put("pregnancy", healthInfo.getPregnancy());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getMenstruation())) {
//                        healthInfoObject.put("menstruation", healthInfo.getMenstruation());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getAllergic())) {
//                        healthInfoObject.put("allergic", healthInfo.getAllergic());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getBlood())) {
//                        healthInfoObject.put("blood", healthInfo.getBlood());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getFamilyMedical())) {
//                        healthInfoObject.put("familyMedical", healthInfo.getFamilyMedical());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getCurrent())) {
//                        healthInfoObject.put("current", healthInfo.getCurrent());
//                    }
//
//                    if (!ValidationUtils.isNull(healthInfo.getOthers())) {
//                        healthInfoObject.put("others", healthInfo.getOthers());
//                    }
//                    JSONObject params = new JSONObject();
//                    params.put("healthInfo", healthInfoObject);
//                    RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, new Response.Listener<JSONObject>() {
//                        public void onResponse(JSONObject arg0) {
//                            if (update == 1) {
//                                T.showShort(activity, "修改成功!");
//                            } else {
//                                register_info_one.setVisibility(View.GONE);
//                                register_info_five.setVisibility(View.VISIBLE);
//                                headerView.setTitle("填写完成");
//                            }
//                        }
//                    }, new RequestErrorListener() {
//                        @Override
//                        public void requestError(VolleyError e) {
//                            if (update == 1) {
//                                T.showShort(activity, "修改成功!");
//                            } else {
//                                register_info_one.setVisibility(View.GONE);
//                                register_info_five.setVisibility(View.VISIBLE);
//                                headerView.setTitle("填写完成");
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
            case R.id.layout_start_time:
                startDatePicker(200, null);
                break;
            case R.id.layout_end_time:
                String sDate = tv_start_time.getText().toString().trim();
                if (sDate.equals("开始服药日期")) sDate = null;
                startDatePicker(300, sDate);
                break;
            case R.id.tv_add_system_out:
                if (checkOutBeforeIsFull()) {
                    addOneOutView();
                } else T.showShort(this, "请完善就医情况");
                break;
            case R.id.regester_info_xueguanfashengshijian:
                startDatePicker(400, null);
                break;
            case R.id.tv_two_choice_date://系统外就医时间选择
                startDatePicker(100, null);
                break;
        }
    }

    private void addOneMedicineView() {
        Health.HealthInfoBean.MedicationUsingSituationsBean bean = new Health.HealthInfoBean.MedicationUsingSituationsBean();
        Holder holder = (Holder) yyViews.get(0).getTag();
        bean.endTime = holder.endTime.getText().toString();
        bean.frequencyUnit = (String) holder.timeUnit.getSelectedItem();
        Medicine m = medicines.get(holder.yaowuName.getSelectedItem().toString());
        if (m != null) {
            bean.medicineId = m.id;
            bean.medicineName = m.name;
        }
        bean.medicineUnit = (String) holder.valueUnit.getSelectedItem();
        bean.singleDose = holder.dcyyl.getText().toString();
        bean.startTime = holder.startTime.getText().toString();
        bean.usingFrequency = (String) holder.yypd.getSelectedItem();
        medicationUsingSituationsList.add(bean);
        mMedicineAdapter.notifyItemInserted(medicationUsingSituationsList.size() - 1);
    }

    private void addOneOutView() {

        Health.HealthInfoBean.ExternalSituationsBean bean = new Health.HealthInfoBean.ExternalSituationsBean();
        OutHolder outHolder = (OutHolder) outViews.get(0).getTag();
        bean.treatmentTime = outHolder.dateText.getText().toString();
        bean.content = outHolder.msgText.getText().toString();
        externalSituationsList.add(bean);
        mOutAdapter.notifyItemInserted(externalSituationsList.size() - 1);
    }

    @Override
    public void onBackPressed() {
        if (this.index == 2) {
            replaceView(1);
        } else if (this.index == 3) {
            replaceView(2);
        } else if (this.index == 4) {
            replaceView(3);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    private class OutHolder {
        public TextView dateText;
        public EditText msgText;
    }

    private class Holder {
        public TextView startTime;
        public TextView endTime;
        public Spinner yaowuName;
        public Spinner yypd;
        public Spinner timeUnit;
        public EditText dcyyl;
        public Spinner valueUnit;
    }

    private boolean checkYYBeforeIsFull() {
        for (View view : yyViews) {
            Holder holder = (Holder) view.getTag();
            if (TextUtils.isEmpty(holder.startTime.getText().toString().trim()) ||
                    TextUtils.isEmpty(holder.endTime.getText().toString().trim()) ||
                    holder.yaowuName.getSelectedItem().equals("药物名") ||
                    holder.yypd.getSelectedItem().equals("用药频度") ||
                    TextUtils.isEmpty(holder.dcyyl.getText().toString().trim())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkOutBeforeIsFull() {
        for (View view : outViews) {
            OutHolder holder = (OutHolder) view.getTag();
            if (holder.dateText.getText().toString().equals("请选择就医时间") ||
                    TextUtils.isEmpty(holder.msgText.getText().toString().trim())) {
                return false;
            }
        }
        return true;
    }

    private String[] toStringArray(Set<String> strings) {
        String[] strings1 = new String[strings.size()];
        int i = 0;
        for (String s : strings) {
            strings1[i] = s;
            i++;
        }
        return strings1;
    }

    /**
     * @param requestCode 100-199 系统外就医 200-299 服药开始时间，300-399服药结束时间 400-499 血管事件发生时间
     */
    private void startDatePicker(int requestCode, String sDate) {
        Intent intent
                = new Intent(this, DatePickerActivity.class);
        intent.putExtra("sDate", sDate);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int ys = requestCode % 100;
            String date = data.getStringExtra("date");
            switch (requestCode / 100) {
                case 1:    //系统外就医
                    OutHolder outHolder = (OutHolder) outViews.get(ys).getTag();
                    outHolder.dateText.setText(date);
                    break;
                case 2:        //服药开始时间
                    Holder holder = (Holder) yyViews.get(ys).getTag();
                    holder.startTime.setText(date);
                    break;
                case 3:     //服药结束时间
                    Holder holder1 = (Holder) yyViews.get(ys).getTag();
                    holder1.endTime.setText(date);
                    break;
                case 4:         //血管时间发生时间
                    regester_info_xueguanfashengshijian.setText(date);
                    break;
            }
        }
    }
}
