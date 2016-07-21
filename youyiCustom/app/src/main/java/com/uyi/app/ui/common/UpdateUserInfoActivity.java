package com.uyi.app.ui.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
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
import com.uyi.app.ui.custom.HeaderView.OnTabChanage;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter.IOnItemSelectListener;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.FileUtils;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.UYIUtils;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * 修改用户基本信息  当前界面布局及控件命名源自拷贝
 *
 * @author user
 */
@ContentView(R.layout.update_user_info)
public class UpdateUserInfoActivity extends BaseActivity implements OnTabChanage, IOnItemSelectListener {
    private PopupWindow mSetPhotoPop;
    private String phone;
    private String dizhi, lianxidianhua, youxiangdizhi, zhiye, shenggao, tizhong;
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    private LayoutInflater mInflater;
    //个人资料
    @ViewInject(R.id.updata_user_main)
    private LinearLayout mMainView;
    @ViewInject(R.id.register_three_layout)
    private LinearLayout register_three_layout;
    @ViewInject(R.id.register_header_image)
    private RoundedImageView register_header_image;
    @ViewInject(R.id.register_shen)
    private TextView register_shen;
    @ViewInject(R.id.register_city)
    private TextView register_city;
    @ViewInject(R.id.register_address)
    private EditText register_address;
    @ViewInject(R.id.register_chushennianyue)
    private TextView register_chushennianyue;
    @ViewInject(R.id.register_phone)
    private EditText register_phone;
    @ViewInject(R.id.register_mobile)
    private EditText register_mobile;
    @ViewInject(R.id.register_email)
    private EditText register_email;
    @ViewInject(R.id.register_card)
    private EditText register_card;
    @ViewInject(R.id.register_zhiye)
    private EditText register_zhiye;
    private Button register_three_submit;


    private SpinerPopWindow spinerPopWindow;
    /**
     * 省
     */
    private List<String> provinces = new ArrayList<String>();
    private JSONArray provincesJSON = new JSONArray();

    /**
     * 市
     */
    private List<String> province = new ArrayList<String>();
    private JSONArray provinceJSON = new JSONArray();

    /**
     * 1 问题
     * 2 性别
     * 3省
     * 4市
     */
    public int spinerIndex = 1;
    Bitmap photo;
    private File tempFile = new File(Environment.getExternalStorageDirectory(), "header.jpg");
    //健康资料
    int index = 1;//fragment页码
    //    @ViewInject(R.id.register_info_one)
//    private ScrollView register_info_one;
//    @ViewInject(R.id.register_info_gerenjiwangbinshi)
//    private EditText register_info_gerenjiwangbinshi;
//    @ViewInject(R.id.register_info_chuanrangbingshi)
//    private EditText register_info_chuanrangbingshi;
//    @ViewInject(R.id.register_info_waishang)
//    private EditText register_info_waishang;
//    @ViewInject(R.id.register_info_shoushushi)
//    private EditText register_info_shoushushi;
//    @ViewInject(R.id.register_info_liucanshi)
//    private EditText register_info_liucanshi;
//    @ViewInject(R.id.register_info_yuejing)
//    private EditText register_info_yuejing;
//    @ViewInject(R.id.register_info_guomingshi)
//    private EditText register_info_guomingshi;
//    @ViewInject(R.id.register_info_shuxueshi)
//    private EditText register_info_shuxueshi;
//    @ViewInject(R.id.register_info_jiazhubinshi)
//    private EditText register_info_jiazhubinshi;
//    @ViewInject(R.id.register_info_muqianfuyaoqingkuang)
//    private EditText register_info_muqianfuyaoqingkuang;
//    @ViewInject(R.id.register_info_qitabuchongqingkuang)
//    private EditText register_info_qitabuchongqingkuang;
//    @ViewInject(R.id.register_info_submit)
//    private Button register_info_submit;
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
    @ViewInject(R.id.outRecyclerView)
    private RecyclerView outRecyclerView;

    @ViewInject(R.id.ywRecyclerView)
    private RecyclerView ywRecyclerView;
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

    private String gender;            //性别
    public int update = 0;            //1为更新信息
    public int id = 0 ;
    private Health health;
    private Health.HealthInfoBean mHealthInfo;
    private List<Health.HealthInfoBean.ExternalSituationsBean> externalSituationsList;
    private List<Health.HealthInfoBean.MedicationUsingSituationsBean> medicationUsingSituationsList;

    private List<View> outViews;
    private List<View> yyViews;

    private String[] ywmString;

    private HashMap<String, Medicine> medicines = new HashMap<>();
    private HashMap<String, AbnormalEvent> abnormalEvents = new HashMap<>();
    int type = 1;

    Calendar cal = Calendar.getInstance();
    private Integer shengfen;
    private Integer city;

    private HealthInfo healthInfo;//病人ID
    private UserInfo userInfo;
    private boolean isMedicineFirst = true;
    private boolean isOutFirst = true;

    private OutAdapter mOutAdapter;
    private MedicineAdapter mMedicineAdapter;

    @Override
    protected void onInitLayoutAfter() {
        String[] tag = getResources().getStringArray(R.array.update_user_info);
        headerView.setTitleTabs(tag);
        headerView.showLeftReturn(true).showTab(true).setOnTabChanage(this);
        headerView.selectTabItem(1);
        healthInfo = new HealthInfo();
        spinerPopWindow = new SpinerPopWindow(activity);
        spinerPopWindow.setItemListener(this);

        ywRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        outRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestManager.getArray(Constens.PROVINCDS, activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                provincesJSON = array;

                for (int i = 0; i < array.length(); i++) {
                    try {
                        provinces.add(array.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        userInfo = UserInfoManager.getLoginUserInfo(activity);
        if (userInfo != null) {


            Loading.bulid(activity, null).show();
            RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Listener<JSONObject>() {
                public void onResponse(JSONObject data) {
                    try {
                        Loading.bulid(activity, null).dismiss();

                        ImageCacheManager.loadImage(JSONObjectUtils.getString(data, "icon"), ImageCacheManager.getImageListener(register_header_image, null, null));
                        register_shen.setText(data.getJSONObject("province").getString("name"));
                        register_city.setText(data.getJSONObject("city").getString("name"));
                        register_address.setText(JSONObjectUtils.getString(data, "address"));
                        dizhi = JSONObjectUtils.getString(data, "address");
                        register_chushennianyue.setText(JSONObjectUtils.getString(data, "birthday"));
                        register_phone.setText(JSONObjectUtils.getString(data, "backupPhoneNumber"));
                        lianxidianhua = JSONObjectUtils.getString(data, "backupPhoneNumber");
                        register_mobile.setText(JSONObjectUtils.getString(data, "phoneNumber"));
                        phone = JSONObjectUtils.getString(data, "phoneNumber");
                        register_email.setText(JSONObjectUtils.getString(data, "email"));
                        youxiangdizhi = JSONObjectUtils.getString(data, "email");
                        register_card.setText(JSONObjectUtils.getString(data, "idCardNumber"));
                        register_zhiye.setText(JSONObjectUtils.getString(data, "occupation"));
                        zhiye = JSONObjectUtils.getString(data, "occupation");
//                        register_height.setText(JSONObjectUtils.getInt(data, "height") + "");
//                        shenggao = JSONObjectUtils.getInt(data, "height") + "";
//                        register_weight.setText(JSONObjectUtils.getInt(data, "weight") + "");
//                        tizhong = JSONObjectUtils.getInt(data, "weight") + "";

                        if (data.has("healthInfo")) {
                            health = JSON.parseObject(data.toString(), Health.class);
                            L.d(TAG, data.toString());
                            if (health.healthInfo == null) {
                                health.healthInfo = mHealthInfo;
                            } else {
                                mHealthInfo = health.healthInfo;
                                if (mHealthInfo.externalSituations == null)
                                    mHealthInfo.externalSituations = externalSituationsList;
                                else {
                                    externalSituationsList = mHealthInfo.externalSituations;
                                }
                                if (mHealthInfo.medicationUsingSituations == null)
                                    mHealthInfo.medicationUsingSituations = medicationUsingSituationsList;
                                else {
                                    medicationUsingSituationsList = mHealthInfo.medicationUsingSituations;
                                }
                            }
                            outRecyclerView.setAdapter(mOutAdapter = new OutAdapter(UpdateUserInfoActivity.this, externalSituationsList, new OutAdapter.OnDeleteListener() {
                                @Override
                                public void onDelete(int position) {
                                    Health.HealthInfoBean.ExternalSituationsBean bean = externalSituationsList.get(position);
                                    if (bean.id != 0) {
                                        deleteOut(bean.id, position);
                                    } else {
                                        externalSituationsList.remove(position);
                                        mOutAdapter.notifyItemRemoved(position);
                                    }
                                }
                            }));
                            ywRecyclerView.setAdapter(mMedicineAdapter = new MedicineAdapter(UpdateUserInfoActivity.this, medicationUsingSituationsList, new MedicineAdapter.OnDeleteListener() {
                                @Override
                                public void onDelete(int position) {
                                    Health.HealthInfoBean.MedicationUsingSituationsBean bean = medicationUsingSituationsList.get(position);
                                    if (bean.id != 0) {
                                        deleteMedicine(bean.id, position);
                                    } else {
                                        medicationUsingSituationsList.remove(position);
                                        mMedicineAdapter.notifyItemRemoved(position);
                                    }
                                }
                            }));

                            if (ValidationUtils.equlse(UYIUtils.convertGender(data.getString("gender")), "男")) {
                                register_info_yuejing_layout.setVisibility(View.GONE);
                                register_info_liucanshi_layout.setVisibility(View.GONE);
                            }
                            Health.HealthInfoBean b = health.healthInfo;
                            register_info_gerenjiwangbinshi.setText(b.medical);
                            register_info_chuanrangbingshi.setText(b.infection);
                            register_info_waishang.setText(b.trauma);
                            register_info_shoushushi.setText(b.operation);
                            register_info_liucanshi.setText(b.pregnancy);
                            register_info_yuejing.setText(b.menstruation);
                            register_info_guomingshi.setText(b.historyOfAllergy);
                            register_info_shuxueshi.setText(b.blood);
                            register_info_jiazhubinshi.setText(b.familyMedical);
                            register_info_muqianfuyaoqingkuang.setText(b.current);
                            register_info_qitabuchongqingkuang.setText(b.others);
                            register_height.setText(health.height);
                            register_weight.setText(health.weight);
                            register_info_one_jiankangzhuangkuang.setText(health.healthCondition);
                            int[] ints = {R.id.rb_gxy, R.id.rb_tnb, R.id.rb_gxy_tnb};
                            if (health.chronicDiseaseType != 0)
                                radioGroup.check(ints[health.chronicDiseaseType - 1]);
                            register_info_chengyingdeyaowu.setText(b.drugAddiction);
                            Health.HealthInfoBean.AbnormalEventJsonsBean j = b.abnormalEventJsons;
                            L.e("J===",j.id +"");
                            if (j != null) {
                                id = j.id;
                                regester_info_xueguanfashengshijian.setText(j.time);
                                int i = 0;
                                for (String s : abnormalEvents.keySet()) {
                                    if (s.equals(j.name)) {
                                        regester_info_xueguanfashengleixing.setSelection(i);
                                    }
                                    i++;
                                }
                                register_info_qitaleixin.setText(j.description);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        mInflater = LayoutInflater.from(this);
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
    }

    private void deleteMedicine(int id, int position) {
        deleteInfo(id, 0, position);
    }

    private void deleteOut(int id, int position) {
        deleteInfo(id, 1, position);
    }

    private void deleteInfo(final int id, final int type, final int position) {
        new AlertDialog.Builder(UpdateUserInfoActivity.this).setTitle("删除提示").setMessage("确定删除该数据？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestManager.getObject(String.format(Locale.CHINA, Constens.DELETE_INFO, id, type), this, new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        T.showShort(UpdateUserInfoActivity.this, "删除成功！");
                        if (type == 0) {
                            medicationUsingSituationsList.remove(position);
                            mMedicineAdapter.notifyItemRemoved(position);
                        } else {
                            externalSituationsList.remove(position);
                            mOutAdapter.notifyItemRemoved(position);
                        }
                    }
                });
            }
        }).setNegativeButton("取消", null).show();

    }

    private void initMedicineViews() {
        if (medicationUsingSituationsList == null || medicationUsingSituationsList.size() == 0)
            return;
//        for (int i = 0; i < medicationUsingSituationsList.size(); i++) {
//            Health.HealthInfoBean.MedicationUsingSituationsBean bb = medicationUsingSituationsList.get(i);
//
//            Holder holder = (Holder) yyViews.get(i).getTag();
//            holder.endTime.setText(bb.endTime);
//            holder.timeUnit.setSelection(getTimeUnitSelection(bb.frequencyUnit));
//            holder.yaowuName.setSelection(getSelection(medicines.keySet(), bb.medicineName));
//            holder.valueUnit.setSelection(bb.medicineUnit.equals("g") ? 0 : 1);
//            holder.dcyyl.setText(bb.singleDose);
//            holder.startTime.setText(bb.startTime);
//            holder.yypd.setSelection(Integer.valueOf(bb.usingFrequency));
//            if (i != medicationUsingSituationsList.size() - 1)
//                addOneMedicineView();
//        }
        mMedicineAdapter.notifyDataSetChanged();
    }

    private int getSelection(Set<String> strings, String s) {
        int i = 0;
        for (String s1 : strings) {
            if (s1.equals(s)) return i;
            i++;
        }
        return 0;
    }

    private int getTimeUnitSelection(String unit) {
        if ("小时".equals(unit)) return 0;
        if ("日".equals(unit)) return 1;
        if ("月".equals(unit)) return 2;
        if ("年".equals(unit)) return 3;
        return 0;
    }

    private void initOutViews() {
        if (externalSituationsList == null || externalSituationsList.size() == 0)
            return;
//        for (int i = 0; i < externalSituationsList.size(); i++) {
//            Health.HealthInfoBean.ExternalSituationsBean bb = externalSituationsList.get(i);
//            OutHolder outHolder = (OutHolder) outViews.get(i).getTag();
//            outHolder.dateText.setText(bb.treatmentTime);
//            outHolder.msgText.setText(bb.content);
//            if (i != externalSituationsList.size() - 1)
//                addOneOutView();
//        }
        mOutAdapter.notifyDataSetChanged();
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

    private String[] toStringArray(Set<String> strings) {
        String[] strings1 = new String[strings.size()];
        int i = 0;
        for (String s : strings) {
            strings1[i] = s;
            i++;
        }
        return strings1;
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
                health.phoneNumber = phone;
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
//                externalSituationsList.clear();
//                for (int i = 0; i < outViews.size(); i++) {
//                    OutHolder outHolder = (OutHolder) outViews.get(i).getTag();
//                    Health.HealthInfoBean.ExternalSituationsBean bean = new Health.HealthInfoBean.ExternalSituationsBean();
//                    bean.content = outHolder.msgText.getText().toString();
//                    bean.treatmentTime = outHolder.dateText.getText().toString();
//                    if (!bean.treatmentTime.equals("请选择就医时间")) {
//                        externalSituationsList.add(bean);
//                    }
//                }

                List<Health.HealthInfoBean.ExternalSituationsBean> situationsBeen = new ArrayList<>();
                for (Health.HealthInfoBean.ExternalSituationsBean b : externalSituationsList) {
                    if (b.id == 0) situationsBeen.add(b);
                }
                mHealthInfo.externalSituations = situationsBeen;

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
//                medicationUsingSituationsList.clear();
                //                for (int i = 0; i < yyViews.size(); i++) {
//                    Holder holder = (Holder) yyViews.get(i).getTag();
//                    Health.HealthInfoBean.MedicationUsingSituationsBean bean = new Health.HealthInfoBean.MedicationUsingSituationsBean();
//                    bean.endTime = holder.endTime.getText().toString();
//                    bean.frequencyUnit = (String) holder.timeUnit.getSelectedItem();
//                    Medicine m = medicines.get(holder.yaowuName.getSelectedItem().toString());
//                    if (m != null) {
//                        bean.medicineId = m.id;
//                        bean.medicineName = m.name;
//                    }
//                    bean.medicineUnit = (String) holder.valueUnit.getSelectedItem();
//                    bean.singleDose = holder.dcyyl.getText().toString();
//                    bean.startTime = holder.startTime.getText().toString();
//                    bean.usingFrequency = (String) holder.yypd.getSelectedItem();
//                    if (!(TextUtils.isEmpty(holder.startTime.getText().toString().trim()) ||
//                            TextUtils.isEmpty(holder.endTime.getText().toString().trim()) ||
//                            holder.yaowuName.getSelectedItem().equals("药物名") ||
//                            holder.yypd.getSelectedItem().equals("用药频度") ||
//                            TextUtils.isEmpty(holder.dcyyl.getText().toString().trim()))) {
//                        medicationUsingSituationsList.add(bean);
//                    }
//                }
                List<Health.HealthInfoBean.MedicationUsingSituationsBean> situationsBeen1 = new ArrayList<>();
                for (Health.HealthInfoBean.MedicationUsingSituationsBean bean : medicationUsingSituationsList) {
                    if (bean.id == 0) situationsBeen1.add(bean);
                }
                mHealthInfo.medicationUsingSituations = situationsBeen1;

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
                    abnormalEventJsonsBean.id = id ;
                    abnormalEventJsonsBean.name = ae.name;
                    abnormalEventJsonsBean.eventId = ae.id;
                }
                abnormalEventJsonsBean.time = fsTime;
                mHealthInfo.abnormalEventJsons = abnormalEventJsonsBean;
                break;
        }
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
                    "药物名".equals(holder.yaowuName.getSelectedItem()) ||
                    "用药频度".equals(holder.yypd.getSelectedItem()) ||
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

    /**
     * @param requestCode 100-199 系统外就医 200-299 服药开始时间，300-399服药结束时间 400-499 血管事件发生时间
     */
    private void startDatePicker(int requestCode, String sDate) {
        Intent intent
                = new Intent(this, DatePickerActivity.class);
        intent.putExtra("sDate", sDate);
        startActivityForResult(intent, requestCode);
    }

    private void requestDrowMenuData() {
        RequestManager.getArray(Constens.ABNORMAL_EVENT, this, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                com.alibaba.fastjson.JSONArray objects = JSON.parseArray(jsonArray.toString());
                for (int i = 0; i < objects.size(); i++) {
                    AbnormalEvent event = JSON.parseObject(objects.getString(i), AbnormalEvent.class);
                    abnormalEvents.put(event.name, event);
                    regester_info_xueguanfashengleixing.setAdapter(new ArrayAdapter<>(UpdateUserInfoActivity.this, R.layout.layout_spinner_item, R.id.textView2, toStringArray(abnormalEvents.keySet())));
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
                    yaowuName.setAdapter(new ArrayAdapter<>(UpdateUserInfoActivity.this, R.layout.layout_spinner_item, R.id.textView2, ywmString = toStringArray(medicines.keySet())));
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
    }

    @OnClick({R.id.register_wenti, R.id.register_xinbie, R.id.register_shen, R.id.register_city, R.id.register_chushennianyue, R.id.register_header_image,
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
            R.id.tv_add_system_out})
    public void onClick(View v) {
        if (v.getId() == R.id.register_shen) {
            spinerIndex = 3;
            spinerPopWindow.refreshData(provinces, 0);
            spinerPopWindow.setWidth(register_shen.getWidth());
            spinerPopWindow.showAsDropDown(register_shen);
        } else if (v.getId() == R.id.register_city) {
            if (shengfen == null) {
                return;
            }
            spinerIndex = 4;
            loadCity();
        } else if (v.getId() == R.id.register_chushennianyue) {
            Intent intent = new Intent(activity, DatePickerActivity.class);
            intent.putExtra("eDate", DateUtils.toDate(new Date(), Constens.DATE_FORMAT_YYYY_MM_DD));
            intent.putExtra("eDateMessage", getString(R.string.e_date_chushengriqi));
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        } else if (v.getId() == R.id.register_header_image) {
            showPop();
        }
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
                if (isOutFirst) {
                    isOutFirst = false;
//                    initOutViews();
                }
                replaceView(2);
                setValue(1);
                break;
            case R.id.register_info_two_submit:
                if (isMedicineFirst) {
                    isMedicineFirst = false;
//                    initMedicineViews();
                }
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
                JSONObject object = null;
                health.phoneNumber = phone;
                try {
                    object = new JSONObject(JSON.toJSONString(health));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (update == 0) {
                    L.e("PA====", object.toString());

//                    RequestManager.postObject(Constens.ACCOUNT_UPDATE, this, object, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//                                T.showShort(UpdateUserInfoActivity.this, "修改成功！");
//                        }
//                    },  new RequestErrorListener() {
//                        @Override
//                        public void requestError(VolleyError e) {
//                            if (e.networkResponse != null) {
//                                if (e.networkResponse.statusCode == 200) {
//                                    T.showShort(activity, "修改成功！");
//                                } else {
//                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
//                                }
//                            } else {
//                                T.showShort(activity, "修改成功！");
//                            }
//                        });
//                }
                    RequestManager.postObject(Constens.ACCOUNT_UPDATE, this, object, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            T.showShort(UpdateUserInfoActivity.this, "修改成功！");
                        }
                    }, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            if (e.networkResponse != null) {
                                if (e.networkResponse.statusCode == 200) {
                                    T.showShort(activity, "修改成功！");
                                } else {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            } else {
                                T.showShort(activity, "修改成功！");
                            }
                        }
                    });
                }
                break;
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
      /*  View view = mInflater.inflate(R.layout.item_medicationsituation, null);

        final Holder holder = new Holder();
        holder.startTime = (TextView) view.findViewById(R.id.tv_start_time);
        holder.endTime = (TextView) view.findViewById(R.id.tv_end_time);
        holder.dcyyl = (EditText) view.findViewById(R.id.regester_three_danciyonyaoliang);
        holder.timeUnit = (Spinner) view.findViewById(R.id.timeUnit);
        holder.valueUnit = (Spinner) view.findViewById(R.id.valueUnit);
        holder.yaowuName = (Spinner) view.findViewById(R.id.yaowuName);
        holder.yaowuName.setAdapter(new ArrayAdapter<>(this, R.layout.layout_spinner_item, R.id.textView2, ywmString));
        holder.yypd = (Spinner) view.findViewById(R.id.yypd);
        final int num = yyViews.size();
        holder.startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePicker(200 + num, null);
            }
        });
        holder.endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = holder.startTime.getText().toString().trim();
                if (sDate.equals("开始服药日期")) sDate = null;
                startDatePicker(300 + num, sDate);
            }
        });
        view.setTag(holder);
        yyViews.add(view);
        add_view_layout.addView(view);*/


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
//        View vs = mInflater.inflate(R.layout.layout_system_out_item, null);
//        OutHolder outHolder = new OutHolder();
//        outHolder.dateText = (TextView) vs.findViewById(R.id.date);
//        outHolder.msgText = (EditText) vs.findViewById(R.id.et_system_out);
//        final int pos = outViews.size();
//        vs.findViewById(R.id.choice_date).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDatePicker(100 + pos, null);
//            }
//        });
//        vs.setTag(outHolder);
//        outViews.add(vs);
//        add_system_out_layout.addView(vs);

        Health.HealthInfoBean.ExternalSituationsBean bean = new Health.HealthInfoBean.ExternalSituationsBean();
        OutHolder outHolder = (OutHolder) outViews.get(0).getTag();
        bean.treatmentTime = outHolder.dateText.getText().toString();
        bean.content = outHolder.msgText.getText().toString();
        externalSituationsList.add(bean);
        mOutAdapter.notifyItemInserted(externalSituationsList.size() - 1);
    }

    public void loadCity() {
        RequestManager.getArray(String.format(Constens.PROVINCD, shengfen), activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                provinceJSON = array;
                province.clear();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        province.add(array.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinerIndex = 4;
                spinerPopWindow.refreshData(province, 0);
                spinerPopWindow.setWidth(register_city.getWidth());
                spinerPopWindow.showAsDropDown(register_city);
                register_city.setText(province.get(0));
            }
        });
    }

    //相册
    private void requestGallery() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constens.PHOTO_REQUEST_GALLERY);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/jpeg");
            startActivityForResult(intent, Constens.PHOTO_REQUEST_GALLERY);
        }
    }

    //照相机
    private void requestTakePhoto() {
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(cameraintent, Constens.PHOTO_REQUEST_TAKEPHOTO);
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, Constens.PHOTO_REQUEST_CUT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode < 1000) {
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
        } else if (requestCode == Constens.PHOTO_REQUEST_TAKEPHOTO) {
            if (resultCode == RESULT_OK) {
                if (tempFile != null) {
                    startPhotoZoom(Uri.fromFile(tempFile));
                }
            }
        } else if (requestCode == Constens.PHOTO_REQUEST_GALLERY) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    startPhotoZoom(Uri.fromFile(new File(FileUtils.getPath(activity, data.getData()))));
                }
            }
        } else if (requestCode == Constens.PHOTO_REQUEST_CUT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        photo = (Bitmap) bundle.get("data");
                        register_header_image.setImageBitmap(photo);
                    }
                }
            }
        } else if (requestCode == Constens.START_ACTIVITY_FOR_RESULT) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("date")) {
                    register_chushennianyue.setText(data.getStringExtra("date"));
                }
            }
        }
//
    }


    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onChanage(int postion) {
        if (type != postion) {
            type = postion;
            if (type == 1) {
                register_three_layout.setVisibility(View.VISIBLE);
//                replaceView(1);
            } else {
                register_three_layout.setVisibility(View.GONE);
                replaceView(1);
            }
        }
    }


    @Override
    public void onItemClick(int pos) {
        if (spinerIndex == 3) {
            JSONObject json;
            try {
                json = provincesJSON.getJSONObject(pos);
                shengfen = json.getInt("id");
                register_shen.setText(json.getString("name"));
                loadCity();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (spinerIndex == 4) {
            JSONObject json;
            try {
                json = provinceJSON.getJSONObject(pos);
                city = json.getInt("id");
                register_city.setText(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick({R.id.register_three_submit, R.id.register_info_submit})
    public void clickInfo(View view) {
        if (view.getId() == R.id.register_three_submit) {//个人资料
            try {
                String address = register_address.getText().toString();
                String birthday = register_chushennianyue.getText().toString();
                String phone = register_phone.getText().toString();
                String mobile = register_mobile.getText().toString();
                String email = register_email.getText().toString();
                String idCardNumber = register_card.getText().toString();
                String occupation = register_zhiye.getText().toString();
                if (ValidationUtils.isNull( birthday, idCardNumber)) {
                    T.showLong(application, "信息输入不完整!");
                    return;
                }
                if (ValidationUtils.isNull(register_height.getText().toString())) {
                    register_height.setText("0");
                }
                if (ValidationUtils.isNull(register_weight.getText().toString())) {
                    register_weight.setText("0");
                }
                int height = Integer.parseInt(register_height.getText().toString());
                int weight = Integer.parseInt(register_weight.getText().toString());

                if (!ValidationUtils.isNull(mobile)) {
                    if (!ValidationUtils.isMobile(mobile)) {
                        T.showLong(application, "手机号格式不正确!");
                        return;
                    }
                }
                if (!ValidationUtils.isNull(phone)) {
                    if (!ValidationUtils.pattern(Constens.PHONE_REGEX, phone)) {
                        T.showLong(application, "联系电话格式不正确!");
                        return;
                    }
                }
                if (!ValidationUtils.isNull(email)) {
                    if (!ValidationUtils.pattern(Constens.EMAIL_REGEX, email)) {
                        T.showLong(application, "邮箱格式不正确!");
                        return;
                    }
                }

                if (!ValidationUtils.pattern(Constens.ID_CARD_REGEX, idCardNumber)) {
                    T.showLong(application, "身份证号码格式不正确!");
                    return;
                }
                JSONObject params = new JSONObject();
                if (photo != null) {
                    try {
                        params.put("icon", BitmapUtils.encode(photo));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                params.put("birthday", birthday);
                params.put("idCardNumber", idCardNumber);
                params.put("phoneNumber", mobile);
                params.put("backupPhoneNumber", phone);
                params.put("email", email);
                if (city != null) {
                    params.put("cityId", city);
                }
                params.put("address", address);
                params.put("occupation", occupation);
                params.put("height", height);
                params.put("weight", weight);
                dizhi = address;
                lianxidianhua = phone;
                youxiangdizhi = email;
                zhiye = occupation;
                shenggao = register_height.getText().toString();
                tizhong = register_weight.getText().toString();
                Loading.bulid(activity, null).show();
                RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, null, new RequestErrorListener() {
                    @Override
                    public void requestError(VolleyError e) {
                        Loading.bulid(activity, null).dismiss();
                        if (e.networkResponse != null) {
                            if (e.networkResponse.statusCode == 204) {
                                T.showShort(activity, "修改成功!");

                            } else {
                                T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                            }
                        } else {
                            T.showShort(activity, "修改成功!");
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (view.getId() == R.id.register_info_submit) {//健康资料

            if (userInfo == null) {
                T.showLong(activity, "获取注册信息失败!");
                return;
            }
//			if(ValidationUtils.isNull(register_info_gerenjiwangbinshi.getText().toString(),register_info_chuanrangbingshi.getText().toString(),
//					register_info_waishang.getText().toString(),register_info_shoushushi.getText().toString(),register_info_liucanshi.getText().toString(),
//					register_info_yuejing.getText().toString(),register_info_guomingshi.getText().toString(),register_info_shuxueshi.getText().toString(),
//					register_info_jiazhubinshi.getText().toString(),register_info_muqianfuyaoqingkuang.getText().toString(),
//					register_info_qitabuchongqingkuang.getText().toString())){
//				T.showLong(application, "信息输入不完整!");
//				System.out.println(register_info_gerenjiwangbinshi.getText().toString()+"-"+register_info_chuanrangbingshi.getText().toString()+""+
//					register_info_waishang.getText().toString()+""+register_info_shoushushi.getText().toString()+""+register_info_liucanshi.getText().toString()+""+
//					register_info_yuejing.getText().toString()+""+register_info_guomingshi.getText().toString()+""+register_info_shuxueshi.getText().toString()+""+
//					register_info_jiazhubinshi.getText().toString()+""+register_info_muqianfuyaoqingkuang.getText().toString()+""+
//					register_info_qitabuchongqingkuang.getText().toString());
//				return;
//			}
            healthInfo.setMedical(register_info_gerenjiwangbinshi.getText().toString());
            healthInfo.setInfection(register_info_chuanrangbingshi.getText().toString());
            healthInfo.setTrauma(register_info_waishang.getText().toString());
            healthInfo.setOperation(register_info_shoushushi.getText().toString());
            healthInfo.setPregnancy(register_info_liucanshi.getText().toString());
            healthInfo.setMenstruation(register_info_yuejing.getText().toString());
            healthInfo.setAllergic(register_info_guomingshi.getText().toString());
            healthInfo.setBlood(register_info_shuxueshi.getText().toString());
            healthInfo.setFamilyMedical(register_info_jiazhubinshi.getText().toString());
            healthInfo.setCurrent(register_info_muqianfuyaoqingkuang.getText().toString());
            healthInfo.setOthers(register_info_qitabuchongqingkuang.getText().toString());
            healthInfo.setUserId(userInfo.userId);
            try {
                JSONObject healthInfoObject = new JSONObject();
                if (!ValidationUtils.isNull(healthInfo.getMedical())) {
                    healthInfoObject.put("medical", healthInfo.getMedical());
                } else {
                    healthInfoObject.put("medical", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getInfection())) {
                    healthInfoObject.put("infection", healthInfo.getInfection());
                } else {
                    healthInfoObject.put("infection", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getTrauma())) {
                    healthInfoObject.put("trauma", healthInfo.getTrauma());
                } else {
                    healthInfoObject.put("trauma", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getOperation())) {
                    healthInfoObject.put("operation", healthInfo.getOperation());
                } else {
                    healthInfoObject.put("operation", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getPregnancy())) {
                    healthInfoObject.put("pregnancy", healthInfo.getPregnancy());
                } else {
                    healthInfoObject.put("pregnancy", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getMenstruation())) {
                    healthInfoObject.put("menstruation", healthInfo.getMenstruation());
                } else {
                    healthInfoObject.put("menstruation", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getAllergic())) {
                    healthInfoObject.put("allergic", healthInfo.getAllergic());
                } else {
                    healthInfoObject.put("allergic", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getBlood())) {
                    healthInfoObject.put("blood", healthInfo.getBlood());
                } else {
                    healthInfoObject.put("blood", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getFamilyMedical())) {
                    healthInfoObject.put("familyMedical", healthInfo.getFamilyMedical());
                } else {
                    healthInfoObject.put("familyMedical", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getCurrent())) {
                    healthInfoObject.put("current", healthInfo.getCurrent());
                } else {
                    healthInfoObject.put("current", "");
                }

                if (!ValidationUtils.isNull(healthInfo.getOthers())) {
                    healthInfoObject.put("others", healthInfo.getOthers());
                } else {
                    healthInfoObject.put("others", "");
                }
                JSONObject params = new JSONObject();

//				String address  = register_address.getText().toString();
//				String birthday = register_chushennianyue.getText().toString();
//				String phone   = register_phone.getText().toString();
//				String mobile= register_mobile.getText().toString();
//				String email  = register_email.getText().toString();
//				String idCardNumber  = register_card.getText().toString();
//				String occupation = register_zhiye.getText().toString();
//				if(ValidationUtils.isNull(address,birthday,phone,mobile,email,idCardNumber,occupation )){
//					T.showLong(application, "信息输入不完整!");
//					return;
//				}
//				if(ValidationUtils.isNull(register_height.getText().toString())){ 
//					register_height.setText("0");
//				}
//				if(ValidationUtils.isNull(register_weight.getText().toString())){ 
//					register_weight.setText("0");
//				}
                int height = Integer.parseInt(shenggao);
                int weight = Integer.parseInt(tizhong);

//				params.put("birthday", birthday);
//				params.put("idCardNumber", idCardNumber);
                params.put("backupPhoneNumber", lianxidianhua);
                params.put("email", youxiangdizhi);
                params.put("address", dizhi);
                params.put("occupation", zhiye);
                params.put("height", height);
                params.put("weight", weight);
                params.put("healthInfo", healthInfoObject);
                Loading.bulid(activity, null).show();
                RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, null, new RequestErrorListener() {
                    @Override
                    public void requestError(VolleyError e) {
                        Loading.bulid(activity, null).dismiss();
                        if (e.networkResponse != null) {
                            if (e.networkResponse.statusCode == 204) {
                                T.showShort(activity, "修改成功!");
//								UYIApplication.loginOut();
//								UpdateUserInfoActivity.this.finish();
                            } else {
                                T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                            }
                        } else {
                            T.showShort(activity, "修改成功!");
//							UYIApplication.loginOut();
//							UpdateUserInfoActivity.this.finish();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    /**
     * 弹出 popupwindow
     */
    public void showPop() {
        View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout, null);
        TextView title = (TextView) mainView.findViewById(R.id.text_set_title);
        title.setText("设置头像");
        Button btnTakePhoto = (Button) mainView.findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
                // 拍照获取
                requestTakePhoto();

            }

        });
        Button btnCheckFromGallery = (Button) mainView.findViewById(R.id.btn_check_from_gallery);
        btnCheckFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
                // 相册获取
                requestGallery();
            }
        });
        Button btnCancle = (Button) mainView.findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
            }
        });
        mSetPhotoPop = new PopupWindow(this);
        mSetPhotoPop.setBackgroundDrawable(new BitmapDrawable());
        mSetPhotoPop.setFocusable(true);
        mSetPhotoPop.setTouchable(true);
        mSetPhotoPop.setOutsideTouchable(true);
        mSetPhotoPop.setContentView(mainView);
        mSetPhotoPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mSetPhotoPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mSetPhotoPop.setAnimationStyle(R.style.bottomStyle);
        mSetPhotoPop.showAtLocation(mMainView, Gravity.BOTTOM, 0, 0);
        mSetPhotoPop.update();
    }

//    @Override
//    public void onBackPressed() {
//        if (type != 1) {
//            if (this.index == 2) {
//                replaceView(1);
//            } else if (this.index == 3) {
//                replaceView(2);
//            } else if (this.index == 4) {
//                replaceView(3);
//            } else {
//                super.onBackPressed();
//            }
//        }
//    }
}
