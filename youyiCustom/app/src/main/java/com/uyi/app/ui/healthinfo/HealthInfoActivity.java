package com.uyi.app.ui.healthinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.FileUtils;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
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
import java.util.Date;
import java.util.List;

/**
 * Created by ThinkPad on 资料修改
 */
@ContentView(R.layout.activity_update_health_info)
public class HealthInfoActivity extends BaseFragmentActivity implements HeaderView.OnTabChanage ,AbstractSpinerAdapter.IOnItemSelectListener {
    //个人资料
    private UserInfo userInfo;
    @ViewInject(R.id.updata_user_main)
    private LinearLayout mMainView;
    @ViewInject(R.id.jbzl)
    private FrameLayout jbzl;

    private PopupWindow mSetPhotoPop;
    private Integer shengfen;
    private Integer city;
    int type = 1;
    @ViewInject(R.id.updata_content_layout)
    private LinearLayout updata_content_layout;
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
    private TextView register_card;
    @ViewInject(R.id.register_zhiye)
    private EditText register_zhiye;
    @ViewInject(R.id.register_three_submit)
    private Button register_three_submit;
    private SpinerPopWindow spinerPopWindow;
    private int Id;
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


    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    private List<Fragment> fragments;
    private View currentView;
    private Fragment currentFragment;
    public  static  String garner = "";
    @Override
    protected void onInitLayoutAfter() {
        String[] tag = getResources().getStringArray(R.array.update_user_info);
        headerView.setTitleTabs(tag);
        headerView.showLeftReturn(true).showTab(true).setOnTabChanage(this);
        headerView.selectTabItem(1);
        fragments = new ArrayList<>();
        userInfo = UserInfoManager.getLoginUserInfo(activity);
        spinerPopWindow = new SpinerPopWindow(activity);
        spinerPopWindow.setItemListener(this);
//        register_card.setEnabled(false);
        Loading.bulid(this, null).show();
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
        RequestManager.getObject(Constens.ACCOUNT_INFO, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.e("ONE", data.toString());
                    ImageCacheManager.loadImage(JSONObjectUtils.getString(data, "icon"), ImageCacheManager.getImageListener(register_header_image, null, null));
                    garner = JSONObjectUtils.getString(data, "gender");
                    register_shen.setText(data.getJSONObject("province").getString("name"));
                    shengfen = data.getJSONObject("province").getInt("id");
                    register_city.setText(data.getJSONObject("city").getString("name"));
                    city = data.getJSONObject("city").getInt("id");
                    register_address.setText(JSONObjectUtils.getString(data, "address"));
                    register_chushennianyue.setText(JSONObjectUtils.getString(data, "birthday"));
                    register_phone.setText(JSONObjectUtils.getString(data, "backupPhoneNumber"));
                    register_mobile.setText(JSONObjectUtils.getString(data, "phoneNumber"));
                    register_email.setText(JSONObjectUtils.getString(data, "email"));
                    register_card.setText(JSONObjectUtils.getString(data, "idCardNumber"));
                    register_zhiye.setText(JSONObjectUtils.getString(data, "occupation"));
                    Loading.bulid(HealthInfoActivity.this, null).dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Loading.bulid(HealthInfoActivity.this, null).dismiss();
                }
            }
        });

        fragments.add(new BasicInfoFragment());
        fragments.add(new PastHistoryFragment());
        fragments.add(new MedicineInfoFragment());
        fragments.add(new BloodInfoFragment());
        changeFragment(0);
        setBackground(jbzl);

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
    public void setBackground(View view){
        jbzl.setBackgroundColor(getResources().getColor(R.color.white));
        ((CheckedTextView) jbzl.getChildAt(0)).setChecked(false);
        if (currentView != null) {
            currentView.setBackgroundColor(getResources().getColor(R.color.white));
            ((CheckedTextView) ((FrameLayout) currentView).getChildAt(0)).setChecked(false);
        }
        view.setBackgroundColor(getResources().getColor(R.color.blue));
        ((CheckedTextView) ((FrameLayout) view).getChildAt(0)).setChecked(true);
    }
    @OnClick({
//            R.id.grzl,      //个人资料
            R.id.jbzl,       //基本资料
            R.id.jws,       //既往史
            R.id.yyqk,    //药物情况
            R.id.xgsj,       //血管事件
            R.id.register_three_submit, R.id.register_shen, R.id.register_city, R.id.register_chushennianyue, R.id.register_header_image
    })
    public void onClick(View view) {

        switch (view.getId()) {
//            case R.id.grzl:
//                changeFragment(0);
//                break;  //个人资料
            case R.id.register_shen:
                spinerIndex = 3;
                spinerPopWindow.refreshData(provinces, 0);
                spinerPopWindow.setWidth(register_shen.getWidth());
                spinerPopWindow.showAsDropDown(register_shen);
                break;
            case R.id.register_city:
                if (shengfen == null) {
                return;
            }
                spinerIndex = 4;
                loadCity();
                break;
            case R.id.register_chushennianyue:
                Intent intent = new Intent(activity, DatePickerActivity.class);
                intent.putExtra("eDate", DateUtils.toDate(new Date(), Constens.DATE_FORMAT_YYYY_MM_DD));
                intent.putExtra("currentDate", register_chushennianyue.getText().toString().trim());
                intent.putExtra("eDateMessage", getString(R.string.e_date_chushengriqi));
                startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
                break;
            case R.id.register_header_image:
                showPop();
                break;
            case R.id.jbzl:
                setBackground(view);
                changeFragment(0);
                currentView = view;
                break;  //基本资料
            case R.id.jws:
                setBackground(view);
                changeFragment(1);
                currentView = view;
                break;  //既往史
            case R.id.yyqk:
                setBackground(view);
                changeFragment(2);
                currentView = view;
                break;  //药物情况
            case R.id.xgsj:
                setBackground(view);
                changeFragment(3);
                currentView = view;
                break;  //血管事件
            case R.id.register_three_submit:
                try {
                    String address = register_address.getText().toString();
                    String birthday = register_chushennianyue.getText().toString();
                    String phone = register_phone.getText().toString();
                    String mobile = register_mobile.getText().toString();
                    String email = register_email.getText().toString();
                    String idCardNumber = register_card.getText().toString();
                    String occupation = register_zhiye.getText().toString();
                    if (ValidationUtils.isNull(birthday, idCardNumber)) {
                        T.showLong(application, "信息输入不完整!");
                        return;
                    }
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

                    Loading.bulid(activity, null).show();
                    RequestManager.postObject(Constens.ACCOUNT_INFO_UPDATE, activity, params, null, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            Loading.bulid(activity, null).dismiss();
                            if (e.networkResponse != null) {
                                if (e.networkResponse.statusCode == 204) {
                                    T.showShort(activity, "修改成功!");
                                    updateInfo(userInfo.account, userInfo.password);
                                } else {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            } else {
                                T.showShort(activity, "修改成功!");
                                updateInfo(userInfo.account, userInfo.password);
                            }
                        }
                    });

//                    RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, new Response.Listener<JSONObject>() {
//                        public void onResponse(JSONObject data) {
//                            try {
//                               System.out.print(data.toString()+"11111111111111111111111111111");
//                                if(data != null)
//                                T.showShort(activity, "修改成功!");
//
//                                if(userInfo.logasguardian == true){
//
//                                }else
//                                    userInfo.icon = data.getString("icon");
//                                UserInfoManager.setLoginUserInfo(activity, userInfo);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, null);
//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;  //提交
        }

    }
    private void updateInfo(String account, String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("account", account);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestManager.postObject(Constens.LOGIN_URL, activity, params, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    userInfo.authToken = data.getString("authToken");
                    if(userInfo.logasguardian == true){
                        userInfo.icon = data.getString("guardianIcon");
                    }else
                        userInfo.icon = data.getString("icon");
                    UserInfoManager.setLoginUserInfo(activity, userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }
    private void changeFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fragments.get(position);
        if (!f.isAdded()) {
            ft.add(R.id.content, f);
            if (currentFragment != null) {
                ft.hide(currentFragment);
            }
            currentFragment = f;
            ft.show(f);
        } else {
            if (f != currentFragment) {
                ft.hide(currentFragment);
                currentFragment = f;
                ft.show(f);
            }
        }
        ft.commit();
    }
    @Override
    public void onChanage(int postion) {
        if (type != postion) {
            type = postion;
            if (type == 1) {
                register_three_layout.setVisibility(View.VISIBLE);
                updata_content_layout.setVisibility(View.GONE);
            } else {
                register_three_layout.setVisibility(View.GONE);
                updata_content_layout.setVisibility(View.VISIBLE);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 0x100);
                } else
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestTakePhoto();
        }
    }
    public void loadCity() {
        RequestManager.getArray(String.format(Constens.PROVINCD, shengfen), activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                provinceJSON = array;
                province.clear();
                try {
                    city = array.getJSONObject(0).getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("data-path = " + data.getData().getPath() + JSON.toJSONString(data));
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
//
    }
}
