package com.uyi.app.ui.common;

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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.HealthInfo;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
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
 * Created by ThinkPad on 2016/6/27.
 */
@ContentView(R.layout.update_guardian_info)
public class UpdateGuardianInfo extends BaseActivity implements AbstractSpinerAdapter.IOnItemSelectListener {
    Bitmap photo;
    private PopupWindow mSetPhotoPop;
    public int spinerIndex = 1;
    private SpinerPopWindow spinerPopWindow;
    private String pwds;
    private String icon;
    private String realName;
    private String gender;
    private Integer shengfen;
    private Integer fourshengfen;
    //    private Integer city;
    private String city;
    private Integer cityId;
    private Integer fourcity;
    private String address;
    private String birthday;
    private String phone;
    private String mobile;
    private String email;
    private String idCardNumber;
    private String occupation;
    private String id;

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
    private static final int CAMERA_WITH_DATA = 1882;
    private static final int CAMERA_CROP_RESULT = 1883;
    private File tempFile = new File(Environment.getExternalStorageDirectory(), "guardianheader.jpg");
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.updata_user_main)
    private LinearLayout updata_user_main;
    @ViewInject(R.id.register_header_image)
    private RoundedImageView register_header_image;
    @ViewInject(R.id.register_name)
    private EditText register_name;
    @ViewInject(R.id.register_sex)
    private TextView register_sex;
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
    @ViewInject(R.id.register_submit)
    private Button register_submit;
    private HealthInfo healthInfo;//病人ID
    private UserInfo userInfo;

    @Override
    protected void onInitLayoutAfter() {
        activity = this;
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("修改监护人资料").setTitleColor(getResources().getColor(R.color.blue));
        spinerPopWindow = new SpinerPopWindow(activity);
        spinerPopWindow.setItemListener(this);
        xinbie.add("男");
        xinbie.add("女");
        xinbieCode.add("MALE");
        xinbieCode.add("FAMALE");


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
            RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject data) {
                    try {
                        Loading.bulid(activity, null).dismiss();
                        System.out.println(data.toString());
                        register_name.setText(data.getJSONObject("guardianInfo").getString("name"));
                        icon = data.getJSONObject("guardianInfo").getString("icon");
                        ImageCacheManager.loadImage(data.getJSONObject("guardianInfo").getString("icon"), ImageCacheManager.getImageListener(register_header_image, null, null));
                        gender = data.getJSONObject("guardianInfo").getString("gender");

                        if (data.getJSONObject("guardianInfo").getString("gender").equals("FAMALE")) {
                            register_sex.setText("女");
                        } else {
                            register_sex.setText("男");
                        }
                        id = data.getJSONObject("guardianInfo").getString("id");
                        register_shen.setText(data.getJSONObject("guardianInfo").getJSONObject("province").getString("name"));
                        register_city.setText(data.getJSONObject("guardianInfo").getJSONObject("city").getString("name"));
                        register_address.setText(JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "address"));
                        address = JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "address");
                        register_chushennianyue.setText(JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "birthday"));
                        register_phone.setText(JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "backupPhoneNumber"));
                        phone = JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "backupPhoneNumber");
                        register_mobile.setText(JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "phoneNumber"));
                        register_email.setText(JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "email"));
                        email = JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "email");
                        register_card.setText(JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "idCardNumber"));
                        cityId = data.getJSONObject("guardianInfo").getJSONObject("city").getInt("id");
                        register_zhiye.setText(JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "occupation"));
                        occupation = JSONObjectUtils.getString(data.getJSONObject("guardianInfo"), "occupation");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }


    @OnClick({
            R.id.register_header_image,
            R.id.register_submit,
            R.id.register_chushennianyue,
            R.id.register_sex,
            R.id.register_shen,
            R.id.register_city,

    })
    public void onClick(View view) {
        if (view.getId() == R.id.register_header_image) {
            showPop();
        } else if (view.getId() == R.id.register_sex) {
            spinerIndex = 2;
            spinerPopWindow.refreshData(xinbie, 0);
            spinerPopWindow.setWidth(register_sex.getWidth());
            spinerPopWindow.showAsDropDown(register_sex);
        } else if (view.getId() == R.id.register_shen) {
            spinerIndex = 3;
            spinerPopWindow.refreshData(provinces, 0);
            spinerPopWindow.setWidth(register_shen.getWidth());
            spinerPopWindow.showAsDropDown(register_shen);
        } else if (view.getId() == R.id.register_city) {
            if (shengfen == null) {
                return;
            }
            spinerIndex = 4;
            loadCity();
        } else if (view.getId() == R.id.register_chushennianyue) {
            Intent intent = new Intent(activity, DatePickerActivity.class);
            intent.putExtra("currentDate", register_chushennianyue.getText().toString().trim());
            intent.putExtra("eDate", DateUtils.toDate(new Date(), Constens.DATE_FORMAT_YYYY_MM_DD));
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        } else if (view.getId() == R.id.register_submit) {
            try {
                register();
            } catch (JSONException e) {
                e.printStackTrace();
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
//        if(ValidationUtils.isNull(register_height.getText().toString())){
//            register_height.setText("0");
//        }
//        if(ValidationUtils.isNull(register_weight.getText().toString())){
//            register_weight.setText("0");
//        }
//        int height  = Integer.parseInt(register_height.getText().toString());
//        int weight  =  Integer.parseInt(register_weight.getText().toString());
        if (ValidationUtils.isNull(birthday, realName, mobile, idCardNumber)) {
            T.showLong(application, "必填项填写完毕!");
            return;
        }
//		if(ValidationUtils.isNull(address,birthday,phone,realName,mobile,idCardNumber)){
//			T.showLong(application, "必填项填写完毕!");
//			return;
//		}

        if (!ValidationUtils.isMobile(mobile)) {
            T.showLong(application, "手机号码格式不正确!");
            return;
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
        JSONObject params = new JSONObject();
        JSONObject guardianInfo = new JSONObject();
        JSONObject city = new JSONObject();
        city.put("id", cityId);
        guardianInfo.put("id", id);
        guardianInfo.put("name", realName);
        guardianInfo.put("phoneNumber", mobile);
        guardianInfo.put("birthday", birthday);
        guardianInfo.put("gender", gender);
        guardianInfo.put("city", city);
        guardianInfo.put("backupPhoneNumber", phone);
        guardianInfo.put("email", email);
        guardianInfo.put("address", address);
        guardianInfo.put("idCardNumber", idCardNumber);
        guardianInfo.put("icon", icon);
        guardianInfo.put("occupation", occupation);
        params.put("guardianInfo", guardianInfo);
        RequestManager.postObject(Constens.GUADIANINFO, activity, params, null, new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {
                Loading.bulid(activity, null).dismiss();
                if (e.networkResponse != null) {
                    if (e.networkResponse.statusCode == 204) {

                        T.showShort(activity, "修改成功!");
                        updateInfo(userInfo.account, userInfo.password);
//                        UpdateGuardianInfo.this.finish();
                    } else {
                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                    }
                } else {
                    updateInfo(userInfo.account, userInfo.password);
                    T.showShort(activity, "修改成功!");
                    updateInfo(userInfo.account, userInfo.password);
//                    UpdateGuardianInfo.this.finish();
                }
            }
        });


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
                    userInfo.icon = data.getString("guardianIcon");
                    UserInfoManager.setLoginUserInfo(activity, userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }
    @Override
    public void onItemClick(int pos) {
        if (spinerIndex == 2) {
            gender = xinbieCode.get(pos);
            register_sex.setText(xinbie.get(pos));
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
                cityId = json.getInt("id");
                city = json.getString("name");
                register_city.setText(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                        cityId = array.getJSONObject(0).getInt("id");
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    L.e("VERSION1==",Build.VERSION.SDK_INT +"");
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 10002);
                } else {
                    L.e("VERSION2==", Build.VERSION.SDK_INT + "");
                    requestGallery();
                }
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
        mSetPhotoPop.showAtLocation(updata_user_main, Gravity.BOTTOM, 0, 0);
        mSetPhotoPop.update();
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


    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UserInfoManager.clearLoginUserInfo(activity);
            startActivity(new Intent(activity, LoginActivity.class));
            finish();

        }

        return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestTakePhoto();
        }else if (requestCode == 10002 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED){
            requestGallery();
        }
    }
}
