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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.FileUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * 修改用户基本信息
 *
 * @author user
 */
@ContentView(R.layout.update_user_info)
public class UpdateUserInfoActivity extends BaseActivity {
    private PopupWindow mSetPhotoPop;
    @ViewInject(R.id.updata_user_main)
    private LinearLayout mMainView;
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    // 个人资料
    @ViewInject(R.id.header_image)
    private RoundedImageView header_image;
    @ViewInject(R.id.update_info)
    private EditText update_info;
    @ViewInject(R.id.update_submit)
    private Button update_submit;

    @ViewInject(R.id.update_user_info_layout_two)
    private LinearLayout update_user_info_layout_two;
    @ViewInject(R.id.update_user_info_layout_one)
    private LinearLayout update_user_info_layout_one;
    @ViewInject(R.id.update_colse)
    private Button update_colse;

    Calendar cal = Calendar.getInstance();
    private UserInfo userInfo;

    private Bitmap photo;
    private File tempFile = new File(Environment.getExternalStorageDirectory(), "header.jpg");

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("修改资料")
                .setTitleColor(getResources().getColor(R.color.blue));
        userInfo = UserInfoManager.getLoginUserInfo(activity);
        if (userInfo != null) {
            ImageCacheManager.loadImage(userInfo.icon, ImageCacheManager.getImageListener(header_image, null, null));
            update_info.setText(userInfo.info);
        }
    }

    @OnClick({R.id.header_image})
    public void onClick(View v) {
        showPop();
    }

    // 相册
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

    // 裁剪
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
                        header_image.setImageBitmap(photo);
                    }
                }
            }
        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @OnClick({R.id.update_submit, R.id.update_colse})
    public void clickInfo(View view) {
        if (view.getId() == R.id.update_submit) {
            try {
                final String info = update_info.getText().toString();
                if (ValidationUtils.isNull(info)) {
                    T.showLong(application, "信息输入不完整!");
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
                params.put("info", info);
                Loading.bulid(activity, null).show();


                RequestManager.requestString(Method.POST, Constens.DOCTOR_UPDATE, activity, params.toString(),
                        new Listener<String>() {
                            @Override
                            public void onResponse(String arg0) {
                                userInfo.info = info;
                                Loading.bulid(activity, null).dismiss();
                                update_user_info_layout_one.setVisibility(View.GONE);
                                update_user_info_layout_two.setVisibility(View.VISIBLE);
                                updateInfo(userInfo.account, userInfo.password);
                            }
                        }, new RequestErrorListener() {
                            public void requestError(VolleyError e) {
                                Loading.bulid(activity, null).dismiss();
                                if (e.networkResponse != null) {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            }
                        });


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.update_colse) {
            onBackPressed();
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
                    userInfo.icon = data.getString("icon");
                    UserInfoManager.updateLoginUserInfo(activity, userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
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
        mSetPhotoPop.showAtLocation(mMainView, Gravity.BOTTOM, 0, 0);
        mSetPhotoPop.update();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestTakePhoto();
        }else if (requestCode == 10002 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED){
            requestGallery();
        }else{}

    }
}
