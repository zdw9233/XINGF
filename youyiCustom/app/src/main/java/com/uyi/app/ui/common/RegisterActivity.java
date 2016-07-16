package com.uyi.app.ui.common;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter.IOnItemSelectListener;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.ui.team.adapter.TeamAdapter;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.FileUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 注册界面
 *
 * @author user
 */
@ContentView(R.layout.register)
public class RegisterActivity extends BaseActivity implements IOnItemSelectListener, OnDateSetListener, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener, BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>> {
    private PopupWindow mSetPhotoPop;
    private File mCurrentPhotoFile;
    private static final int CAMERA_WITH_DATA = 1882;
    private static final int CAMERA_CROP_RESULT = 1883;
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.regester_main)
    private LinearLayout mMainView;
    @ViewInject(R.id.register_xieyi)
    private TextView register_xieyi;
    @ViewInject(R.id.register_one_layout)
    private LinearLayout register_one_layout;
    @ViewInject(R.id.register_one_next)
    private Button register_one_next;
    @ViewInject(R.id.register_one_return)
    private Button register_one_return;

    @ViewInject(R.id.register_two_layout)
    private LinearLayout register_two_layout;
    @ViewInject(R.id.register_username)
    private EditText register_username;
    @ViewInject(R.id.register_pwd)
    private EditText register_pwd;
    @ViewInject(R.id.register_pwds)
    private EditText register_pwds;
    @ViewInject(R.id.register_wenti)
    private TextView register_wenti;
    @ViewInject(R.id.register_dywenti_layout)
    private LinearLayout register_dywenti_layout;
    @ViewInject(R.id.register_zidingyiwenti)
    private EditText register_zidingyiwenti;
    @ViewInject(R.id.register_daan)
    private EditText register_daan;
    @ViewInject(R.id.register_two_next)
    private Button register_two_next;


    @ViewInject(R.id.register_three_layout)
    private LinearLayout register_three_layout;
    @ViewInject(R.id.register_header_image)
    private RoundedImageView register_header_image;
    @ViewInject(R.id.register_name)
    private EditText register_name;
    @ViewInject(R.id.register_xinbie)
    private TextView register_xinbie;
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
    @ViewInject(R.id.register_height)
    private EditText register_height;
    @ViewInject(R.id.register_weight)
    private EditText register_weight;
    @ViewInject(R.id.register_three_submit)
    private Button register_three_submit;

    @ViewInject(R.id.register_four_layout)
    private LinearLayout register_four_layout;
    @ViewInject(R.id.regester_four_city_layout)
    private LinearLayout regester_four_city_layout;
    @ViewInject(R.id.register_four_shen)
    private TextView register_four_shen;
    @ViewInject(R.id.register_four_city)
    private TextView register_four_city;
    @ViewInject(R.id.register_four_chose)
    private TextView register_four_chose;
    @ViewInject(R.id.register_four_selected_caty)
    private TextView register_four_selected_caty;
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private LinearLayoutManager linearLayoutManager;
    private com.uyi.app.ui.team.adapter.TeamAdapter TeamAdapter;

    @ViewInject(R.id.register_five_layout)
    private LinearLayout register_five_layout;
    @ViewInject(R.id.register_five_setting)
    private Button register_five_setting;
    @ViewInject(R.id.register_five_return)
    private Button register_five_return;


    private ArrayList<Map<String, Object>> teams = new ArrayList<Map<String, Object>>();
    private SpinerPopWindow spinerPopWindow;
    private List<String> wentiList = new ArrayList<String>();

    private List<String> xinbie = new ArrayList<String>();
    private List<String> xinbieCode = new ArrayList<String>();

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


    int index = 1;//fragment页码

    /**
     * 1 问题
     * 2 性别
     * 3省
     * 4市
     */
    public int spinerIndex = 1;
    Bitmap photo;
    private boolean isChoseCity = true;
    private File tempFile = new File(Environment.getExternalStorageDirectory(), "header.jpg");
    private int groupId;
    private String accunt;
    private String pwd;
    private String pwds;
    private String safeQuestion;
    private String safeAnswer;
    private String icon;
    private String realName;
    private String gender;
    private Integer shengfen;
    private Integer fourshengfen;
    private Integer city;
    private Integer fourcity;
    private String address;
    private String birthday;
    private String phone;
    private String mobile;
    private String email;
    private String idCardNumber;
    private String occupation;
    private Integer height;
    private Integer weight;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onInitLayoutAfter() {
//		register_xieyi.setText(Html.fromHtml(Constens.REGISTER_XIEYI));
        activity = this;
        linearLayoutManager = new LinearLayoutManager(this);
        TeamAdapter = new TeamAdapter(this);
        TeamAdapter.setOnItemClickListener(this);
        TeamAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(TeamAdapter);
        recyclerView.setPager(this);

        register_four_selected_caty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChoseCity) {
                    isChoseCity = false;
                    regester_four_city_layout.setVisibility(View.VISIBLE);
                    register_four_selected_caty.setText("全部城市");
                    register_four_chose.setText("选择城市");
                    register_four_shen.setText("请选择省份");
                    register_four_city.setText("请选择城市");

                } else {
                    isChoseCity = true;
                    regester_four_city_layout.setVisibility(View.GONE);
                    register_four_selected_caty.setText("选择城市");
                    register_four_chose.setText("全部城市");
                    onRefresh();
                }

            }
        });
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);


//		onRefresh();
        RequestManager.getObject(String.format(Constens.REGISTER_AGREEMENT), activity, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject.toString());
                try {
                    register_xieyi.setText(Html.fromHtml(jsonObject.getString("content")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        xinbie.add("男");
        xinbie.add("女");
        xinbieCode.add("MALE");
        xinbieCode.add("FAMALE");
        headerView.showTitle(true);
        headerView.setHeaderBackgroundColor(getResources().getColor(R.color.blue));
        replaceView(1);
        spinerPopWindow = new SpinerPopWindow(activity);
        spinerPopWindow.setItemListener(this);
        RequestManager.getArray(Constens.SAFE_QUESTIONS, activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        wentiList.add(array.getJSONObject(i).getString("question"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (wentiList.size() > 0) {
                    onItemClick(0);
                }
                spinerPopWindow.refreshData(wentiList, 0);
            }
        });

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
    }

    @OnClick({
            R.id.register_one_next,
            R.id.register_one_return,
            R.id.register_two_next,
            R.id.register_three_submit,
            R.id.register_five_return,
            R.id.register_five_setting,
            R.id.register_four_selected_caty

    })
    public void onLayoutNextClick(View view) {
        if (view.getId() == R.id.register_one_next) {
            replaceView(2);
        } else if (view.getId() == R.id.register_one_return) {
            finish();
        } else if (view.getId() == R.id.register_two_next) {
            accunt = register_username.getText().toString();
            pwd = register_pwd.getText().toString();
            pwds = register_pwds.getText().toString();
            safeQuestion = register_zidingyiwenti.getText().toString();
            safeAnswer = register_daan.getText().toString();
            if (ValidationUtils.isNull(accunt, pwd, pwds, safeQuestion, safeAnswer)) {
                T.showLong(activity, "输入不完整!");
                return;
            }

            if (!ValidationUtils.equlse(pwd, pwds)) {
                T.showLong(activity, "两次密码输入不同!");
                return;
            }

            if (!ValidationUtils.pattern(Constens.USERNAME_REGEX, accunt)) {
                T.showLong(activity, "账户名只能使用英语大小写字符及数字字符和连接字符和下划线!");
                return;
            }
            if (ValidationUtils.length(accunt) < Constens.USERNAME_MIN_LEN || ValidationUtils.length(accunt) > Constens.USERNAME_MAX_LEN) {
                T.showLong(activity, String.format("账户名长度不得小于%s, 大于%s位字符!", Constens.USERNAME_MIN_LEN, Constens.USERNAME_MAX_LEN));
                return;
            }

            if (!ValidationUtils.pattern(Constens.PASSWORD_REGEX, pwd)) {
                T.showLong(activity, "密码必须至少包含一个英文字符,一个数字!");
                return;
            }

            if (ValidationUtils.length(pwd) < Constens.PASSWORD_MIN_LEN || ValidationUtils.length(pwd) > Constens.PASSWORD_MAX_LEN) {
                T.showLong(activity, String.format("密码长度不得小于%s, 大于%s位字符!", Constens.PASSWORD_MIN_LEN, Constens.PASSWORD_MAX_LEN));
                return;
            }


            RequestManager.getObject(String.format(Constens.CHECK_NAME_USED, "1", accunt), activity, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        if (!data.getBoolean("used")) {
                            replaceView(3);
                        } else {
                            T.showLong(activity, "账号名已被使用!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (view.getId() == R.id.register_three_submit) {
            try {
                register();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//		else if(view.getId() == R.id.register_four_selected_caty){
//			//设置基本信息
//			Intent intent = new Intent(activity, RegisterInfoAcitivity.class);
//			intent.putExtra("update", 0);
//			intent.putExtra("gender", gender);
//			startActivity(intent);
//			finish();
//		}
        else if (view.getId() == R.id.register_five_setting) {
            //设置基本信息
            Intent intent = new Intent(activity, RegisterInfoAcitivity.class);
            intent.putExtra("update", 0);
            intent.putExtra("gender", gender);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.register_five_return) {
            finish();
        }
    }

    @OnClick({R.id.register_wenti, R.id.register_xinbie, R.id.register_shen, R.id.register_city, R.id.register_chushennianyue, R.id.register_header_image, R.id.register_four_shen, R.id.register_four_city})
    public void onClick(View v) {
        if (v.getId() == R.id.register_wenti) {
            spinerIndex = 1;
            spinerPopWindow.setWidth(register_wenti.getWidth());
            spinerPopWindow.showAsDropDown(register_wenti);
        } else if (v.getId() == R.id.register_xinbie) {
            spinerIndex = 2;
            spinerPopWindow.refreshData(xinbie, 0);
            spinerPopWindow.setWidth(register_xinbie.getWidth());
            spinerPopWindow.showAsDropDown(register_xinbie);
        } else if (v.getId() == R.id.register_shen) {
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
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        } else if (v.getId() == R.id.register_header_image) {
//			requestGallery();
            showPop();
        } else if (v.getId() == R.id.register_four_shen) {
            spinerIndex = 5;
            spinerPopWindow.refreshData(provinces, 0);
            spinerPopWindow.setWidth(register_four_shen.getWidth());
            spinerPopWindow.showAsDropDown(register_four_shen);

        } else if (v.getId() == R.id.register_four_city) {
            if (fourshengfen == null) {
                return;
            }
            spinerIndex = 6;
            FourloadCity();
        }
    }

    @Override
    protected int getColorResourceId() {
        return R.color.blue;
    }

    public void replaceView(int index) {
        this.index = index;
        register_one_layout.setVisibility(View.GONE);
        register_two_layout.setVisibility(View.GONE);
        register_three_layout.setVisibility(View.GONE);
        register_four_layout.setVisibility(View.GONE);
        if (index == 1) {
            headerView.setTitle("用户注册协议");
            register_one_layout.setVisibility(View.VISIBLE);
        }
        if (index == 2) {
            headerView.setTitle("填写账号信息");
            register_two_layout.setVisibility(View.VISIBLE);
        }
        if (index == 3) {
            headerView.setTitle("填写个人信息");
            register_three_layout.setVisibility(View.VISIBLE);
        }
        if (index == 4) {
            headerView.setTitle("选择健康团队");
            register_four_layout.setVisibility(View.VISIBLE);
        }
        if (index == 5) {
            headerView.setTitle("注册成功");
            register_five_layout.setVisibility(View.VISIBLE);
        }
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

    public void loadCity() {
        province.clear();
        RequestManager.getArray(String.format(Constens.PROVINCD, shengfen), activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                provinceJSON = array;
                for (int i = 0; i < array.length(); i++) {
                    try {
                        province.add(array.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinerIndex = 4;
                register_city.setText(province.get(0));
                spinerPopWindow.refreshData(province, 0);
                spinerPopWindow.setWidth(register_city.getWidth());
                spinerPopWindow.showAsDropDown(register_city);
            }
        });
    }

    public void FourloadCity() {
        province.clear();
        RequestManager.getArray(String.format(Constens.PROVINCD, fourshengfen), activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                provinceJSON = array;
                for (int i = 0; i < array.length(); i++) {
                    try {
                        province.add(array.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinerIndex = 6;
                register_four_city.setText("请选择城市");
                spinerPopWindow.refreshData(province, 0);
                spinerPopWindow.setWidth(register_four_city.getWidth());
                spinerPopWindow.showAsDropDown(register_four_city);

            }
        });
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onItemClick(int pos) {
        if (spinerIndex == 1) {
            if (wentiList.get(pos).equals("自定义问题")) {
                register_dywenti_layout.setVisibility(View.VISIBLE);
            } else {
                register_dywenti_layout.setVisibility(View.GONE);
                register_zidingyiwenti.setText(wentiList.get(pos));
            }
            register_wenti.setText(wentiList.get(pos));
        } else if (spinerIndex == 2) {
            gender = xinbieCode.get(pos);
            register_xinbie.setText(xinbie.get(pos));
        } else if (spinerIndex == 3) {
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
        } else if (spinerIndex == 5) {
            JSONObject json;
            try {
                json = provincesJSON.getJSONObject(pos);
                fourshengfen = json.getInt("id");
                register_four_shen.setText(json.getString("name"));
                FourloadCity();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (spinerIndex == 6) {
            JSONObject json;
            try {
                json = provinceJSON.getJSONObject(pos);
                fourcity = json.getInt("id");
                register_four_city.setText(json.getString("name"));
                register_four_chose.setText(json.getString("name"));
                onRefresh();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        register_chushennianyue.setText(df.format(cal.getTime()));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.PHOTO_REQUEST_TAKEPHOTO) {
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
    }


    public void register() throws JSONException {

        address = register_address.getText().toString();
        birthday = register_chushennianyue.getText().toString();
        phone = register_phone.getText().toString();
        realName = register_name.getText().toString();
        mobile = register_mobile.getText().toString();
        email = register_email.getText().toString();
        idCardNumber = register_card.getText().toString();
        occupation = register_zhiye.getText().toString();

        if (ValidationUtils.isNull(birthday, realName, idCardNumber)) {
            T.showLong(application, "必填项填写完毕!");
            return;
        }
//		if(ValidationUtils.isNull(address,birthday,phone,realName,mobile,idCardNumber)){
//			T.showLong(application, "必填项填写完毕!");
//			return;
//		}
//		if(ValidationUtils.isNull(register_height.getText().toString())){
//			register_height.setText("0");
//		}
//		if(ValidationUtils.isNull(register_weight.getText().toString())){
//			register_weight.setText("0");
//		}
//		height  = Integer.parseInt(register_height.getText().toString());
//		weight  =  Integer.parseInt(register_weight.getText().toString());;
        if (!ValidationUtils.isNull(mobile)) {
        if (!ValidationUtils.isMobile(mobile)) {
            T.showLong(application, "手机号码格式不正确!");
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
//		JSONObject param = new JSONObject();
//		param.put("idCardNumber",idCardNumber);
//		RequestManager.getObjectNoToken(String.format(Constens.JUDGE_ID_CARD,param), activity,new Listener<JSONObject>() {
//			@Override
//			public void onResponse(JSONObject jsonObject) {
//				System.out.println(jsonObject.toString());
//				try {
//					pand = jsonObject.getBoolean("used");
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

        if (ValidationUtils.length(realName) > Constens.REAL_NAME_LEN) {
            T.showLong(application, String.format("姓名长度不能大于%s位!", Constens.REAL_NAME_LEN));
            return;
        }


        if (photo != null) {
            try {
                icon = BitmapUtils.encode(photo);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        RequestManager.getObject(String.format(Constens.JUDGE_ID_CARD, idCardNumber), activity, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject data) {
                try {
                    if (data.getBoolean("used")) {
                        T.showLong(activity, "身份证号码已被使用!");
                    } else {
                        replaceView(4);
                        onRefresh();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

//		startActivity(new Intent(activity,ChoseTeamActivity.class));
//		JSONObject params = new JSONObject();
//		params.put("account", accunt);
//		params.put("password", pwd);
//		params.put("phoneNumber", mobile);
//		params.put("realName", realName);
//		params.put("birthday", birthday);
//		params.put("gender", gender);
//		params.put("idCardNumber", idCardNumber);
//		params.put("backupPhoneNumber",phone);
//		params.put("email", email);
//		params.put("cityId", city);
//		params.put("address", address);
//		params.put("icon", icon);
//		params.put("safeQuestion", safeQuestion);
//		params.put("safeAnswer", safeAnswer);
//		params.put("occupation", occupation);
//		params.put("height", height);
//		params.put("weight", weight);
//		L.d(TAG, params.toString());
//		RequestManager.postObject(Constens.ACCOUNT_REGISTER, activity, params, new Listener<JSONObject>() {
//			@Override
//			public void onResponse(JSONObject data) {
//				if(data.has("id")){
//					try {
//						UserInfo userInfo = new UserInfo();
//						userInfo.userId = data.getInt("id");
//						userInfo.authToken = data.getString("authToken");
//						userInfo.type = data.getInt("type");
//						userInfo.account = data.getString("account");
//						userInfo.realName = data.getString("realName");
//						userInfo.icon = data.getString("icon");
//						userInfo.address = data.getString("address");
//						userInfo.beans = data.getInt("beans");
//						userInfo.consumedBeans = data.getInt("consumedBeans");
//						userInfo.lastLoginTime = data.getString("lastLoginTime");
//						UserInfoManager.setLoginUserInfo(application, userInfo);
//						replaceView(4);
//
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}, null);
    }

    public void registerLast() throws JSONException {
        Loading.bulid(activity, null).show();

        JSONObject params = new JSONObject();
        params.put("account", accunt);
        params.put("password", pwd);
        params.put("phoneNumber", mobile);
        params.put("realName", realName);
        params.put("birthday", birthday);
        params.put("gender", gender);
        params.put("idCardNumber", idCardNumber);
        params.put("backupPhoneNumber", phone);
        params.put("email", email);
        params.put("cityId", city);
        params.put("address", address);
        params.put("icon", icon);
        params.put("safeQuestion", safeQuestion);
        params.put("safeAnswer", safeAnswer);
        params.put("occupation", occupation);
        params.put("groupId", groupId);

        L.d(TAG, params.toString());
        RequestManager.postObject(Constens.ACCOUNT_REGISTER, activity, params, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject data) {
                if (data.has("id")) {
                    try {
                        UserInfo userInfo = new UserInfo();
                        userInfo.userId = (data.getInt("id"));
                        userInfo.authToken = data.getString("authToken");
                        userInfo.type = data.getInt("type");
                        userInfo.account = data.getString("account");
                        userInfo.realName = data.getString("realName");
                        userInfo.icon = data.getString("icon");
                        userInfo.address = data.getString("address");
                        userInfo.beans = data.getInt("beans");
                        userInfo.consumedBeans = data.getInt("consumedBeans");
                        userInfo.lastLoginTime = data.getString("lastLoginTime");
                        UserInfoManager.setLoginUserInfo(application, userInfo);
                        replaceView(5);
                        Loading.bulid(activity, null).dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, null);
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
//                doTakePhoto();
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

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    //	public boolean shouldLoad() {
//		return isLooding;
//	}
    @Override
    public void loadNextPage() {//所有团队
        isLooding = false;
        Loading.bulid(activity, null).show();
        if (register_four_chose.getText().equals("全部城市")) {
            RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_ALL_NOTOKEN, "", "", pageNo, pageSize), RegisterActivity.this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        totalPage = data.getInt("pages");
                        JSONArray array = data.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            Map<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject = array.getJSONObject(i);

                            item.put("id", jsonObject.getInt("id"));
                            item.put("name", jsonObject.getString("name"));
                            item.put("info", jsonObject.getString("info"));
                            item.put("logo", jsonObject.getString("logo"));
                            Loading.bulid(activity, null).dismiss();
                            datas.add(item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TeamAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    if (pageNo <= totalPage) {
                        isLooding = true;
                        pageNo++;
                    } else {
                        recyclerView.setRefreshing(false);

                    }
                }
            });
        } else {
            RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_ALL_NOTOKEN, "", fourcity, pageNo, pageSize), RegisterActivity.this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        totalPage = data.getInt("pages");
                        JSONArray array = data.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            Map<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject = array.getJSONObject(i);

                            item.put("id", jsonObject.getInt("id"));
                            item.put("name", jsonObject.getString("name"));
                            item.put("info", jsonObject.getString("info"));
                            item.put("logo", jsonObject.getString("logo"));

                            datas.add(item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TeamAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    Loading.bulid(activity, null).dismiss();
                    if (pageNo < totalPage) {
                        isLooding = true;
                        pageNo++;
                    } else {
                        recyclerView.setRefreshing(false);

                    }
                }
            });
        }
    }


    @Override
    public void onRefresh() {
        pageNo = 1;
        isLooding = true;
        datas.clear();
        recyclerView.setRefreshing(false);
        loadNextPage();

    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {

    }

    public void gotoChooesTeam(int realPosition, Map<String, Object> data) {
        groupId = (int) datas.get(realPosition).get("id");
        try {
            registerLast();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
