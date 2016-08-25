package com.uyi.app.ui.health;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.ImageUtil;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 添加 健康数据库
 *
 * @author user
 */
@ContentView(R.layout.health_database_add)
public class AddHealthDatabase extends BaseActivity {


    @ViewInject(R.id.headerView)
    private HeaderView headerView;

    @ViewInject(R.id.checkTime)
    private TextView checkTime;
    @ViewInject(R.id.randomBloodSugar)
    private EditText randomBloodSugar;
    @ViewInject(R.id.gaoya)
    private EditText gaoya;
    @ViewInject(R.id.diya)
    private EditText diya;
    @ViewInject(R.id.maibo)
    private EditText maibo;
    @ViewInject(R.id.kongfuxuetang)
    private EditText kongfuxuetang;
    @ViewInject(R.id.canyinxuetang)
    private EditText canyinxuetang;
    @ViewInject(R.id.zongduanguchun)
    private EditText zongduanguchun;
    @ViewInject(R.id.ganyoushanzhi)
    private EditText ganyoushanzhi;
    @ViewInject(R.id.dimiduzhidanbai)
    private EditText dimiduzhidanbai;
    @ViewInject(R.id.gaomiduzhidanbai)
    private EditText gaomiduzhidanbai;
    @ViewInject(R.id.baohedu)
    private EditText baohedu;
    @ViewInject(R.id.xinglv)
    private EditText xinglv;
    @ViewInject(R.id.niaosuan)
    private EditText niaosuan;
    @ViewInject(R.id.jianchaxiangmu)
    private EditText jianchaxiangmu;

    @ViewInject(R.id.health_database_add_submit)
    private Button health_database_add_submit;

    @ViewInject(R.id.health_database_add_jianchabaogao_layout)
    private FlowRadioGroup health_database_add_jianchabaogao_layout;
    @ViewInject(R.id.health_database_add_jianchabaogao_add)
    private ImageView health_database_add_jianchabaogao_add;
    @ViewInject(R.id.health_database_add_xindiantu_layout)
    private FlowRadioGroup health_database_add_xindiantu_layout;
    @ViewInject(R.id.health_database_add_xindiantu_add)
    private ImageView health_database_add_xindiantu_add;


    List<Bitmap> images = new ArrayList<>();
    List<Bitmap> ecgs = new ArrayList<>();
    Map<String,String> datas = new HashMap<>();
    private int addImageIndex;


    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("添加健康数据库").setTitleColor(getResources().getColor(R.color.blue));
    }


    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                JSONObject params = (JSONObject) msg.obj;
                Loading.bulid(activity, null).show();
                RequestManager.postObject(Constens.HEALTH_CHECK_PREVIEW, activity,params, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                        L.d(TAG, data.toString());
                        Loading.bulid(activity, "").dismiss();
                        Intent intent
                                 = new Intent(AddHealthDatabase.this,HealthDatabasePreviewActivity.class);
                        intent.putExtra("data",data.toString());
                        startActivity(intent);
                        finish();
                    }
                }, null);
            } else if (msg.what == 2) {
                T.showLong(activity, msg.obj.toString());
            }
        }

        ;
    };

    @OnClick(R.id.health_database_add_submit)
    public void onClick(View view) {


//			 if(!(images.size() != 0 || ecgs.size() != 0)){
//				 T.showLong(activity, "资料不完整!");
//				 return;
//			 }


        Loading.bulid(activity, null).show();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    JSONObject params = new JSONObject();
                    if (!ValidationUtils.isNull(gaoya.getText().toString())) {
                        params.put("morningSystolicPressure", gaoya.getText().toString());//晨起血压: 收缩压
                    }
                    if (!ValidationUtils.isNull(diya.getText().toString())) {
                        params.put("morningDiastolicPressure", diya.getText().toString());//晨起血压: 舒张压
                    }
                    if (!ValidationUtils.isNull(xinglv.getText().toString())) {
                        params.put("heartRate", xinglv.getText().toString());//静息心率
                    }
                    if (!ValidationUtils.isNull(kongfuxuetang.getText().toString())) {
                        params.put("fastBloodSugar", kongfuxuetang.getText().toString());//空腹血糖
                    }
                    if (!ValidationUtils.isNull(canyinxuetang.getText().toString())) {
                        params.put("postPrandilaSugar", canyinxuetang.getText().toString());//餐后2小时血糖
                    }
                    if (!ValidationUtils.isNull(niaosuan.getText().toString())) {
                        params.put("urineAcid", niaosuan.getText().toString());//尿酸
                    }
                    if (!ValidationUtils.isNull(zongduanguchun.getText().toString())) {
                        params.put("bloodFatChol", zongduanguchun.getText().toString());//血胎: 总胆固醇
                    }
                    if (!ValidationUtils.isNull(ganyoushanzhi.getText().toString())) {
                        params.put("bloodFatTg", ganyoushanzhi.getText().toString());//血胎: 甘油三酯
                    }
                    if (!ValidationUtils.isNull(dimiduzhidanbai.getText().toString())) {
                        params.put("bloodFatLdl", dimiduzhidanbai.getText().toString());//血胎: 低密度脂蛋白胆固醇
                    }
                    if (!ValidationUtils.isNull(gaomiduzhidanbai.getText().toString())) {
                        params.put("bloodFatHdl", gaomiduzhidanbai.getText().toString());//血胎: 高密度脂蛋白胆固醇
                    }
                    if (!ValidationUtils.isNull(jianchaxiangmu.getText().toString())) {
                        params.put("checkItem", jianchaxiangmu.getText().toString());//定期检查报告检查项目
                    }
                    if (!ValidationUtils.isNull(maibo.getText().toString())) {
                        params.put("pulseRate", maibo.getText().toString());//脉搏
                    }
                    if (!ValidationUtils.isNull(baohedu.getText().toString())) {
                        params.put("spo", baohedu.getText().toString());//血氧饱和度
                    }
                    if (!ValidationUtils.isNull(checkTime.getText().toString())) {
                        params.put("checkTime", checkTime.getText().toString());//体检时间
                    }
                    if (!ValidationUtils.isNull(randomBloodSugar.getText().toString())) {
                        params.put("randomBloodSugar", randomBloodSugar.getText().toString());//随机血糖
                    }

                    if (images.size() > 0) {
                        JSONArray image = new JSONArray();//检查报告
                        for (Bitmap bm : images) {
                            JSONObject json = new JSONObject();
                            json.put("binary", BitmapUtils.encode(bm));
                            image.put(json);
                        }
                        params.put("images", image);
                    }

                    if (ecgs.size() > 0) {
                        JSONArray ecg = new JSONArray();//心电图
                        for (Bitmap bm : ecgs) {
                            JSONObject json = new JSONObject();
                            json.put("binary", BitmapUtils.encode(bm));
                            ecg.put(json);
                        }
                        params.put("ecg", ecg);
                    }
                    if (params.length() < 1) {
                        handler.sendMessage(handler.obtainMessage(2, "至少填写一项!"));
                    } else {
                        handler.sendMessage(handler.obtainMessage(1, params));

                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        thread.start();
    }


    @OnClick({R.id.health_database_add_jianchabaogao_add, R.id.health_database_add_xindiantu_add, R.id.checkTime})
    public void click(View view) {
        if (view.getId() == R.id.health_database_add_jianchabaogao_add) {
            addImageIndex = 1;
            requestGallery();
        } else if (view.getId() == R.id.health_database_add_xindiantu_add) {
            addImageIndex = 2;
            requestGallery();
        } else if (view.getId() == R.id.checkTime) {
            Intent intent = new Intent(activity, DatePickerActivity.class);
            intent.putExtra("format", Constens.DATE_FORMAT);
            intent.putExtra("currentDate", checkTime.getText().toString().trim());
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        }
    }


    //相册
    private void requestGallery() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, Constens.PHOTO_REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.PHOTO_REQUEST_GALLERY) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (addImageIndex == 1) {
                        try {
                            Bitmap bitmap = ImageUtil.decodeSampledBitmapFromFile(data.getData().getPath(), 240, 400);
                            images.add(bitmap);
                            showJCView();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (addImageIndex == 2) {
                        try {
                            Bitmap bitmap = ImageUtil.decodeSampledBitmapFromFile(data.getData().getPath(), 240, 400);
                            ecgs.add(bitmap);
                            showYaowuWenbenView();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (requestCode == Constens.START_ACTIVITY_FOR_RESULT) {
            if (resultCode == RESULT_OK) {
                checkTime.setText(data.getStringExtra("date"));
            }
        }
    }


    public void showJCView() {
        health_database_add_jianchabaogao_layout.removeAllViews();
        for (int i = 0; i < images.size(); i++) {
            final View view = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, health_database_add_jianchabaogao_layout, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.new_consult_image_upload);
            ImageView new_consult_upload_delete = (ImageView) view.findViewById(R.id.new_consult_upload_delete);
            new_consult_upload_delete.setTag(images.get(i));
            new_consult_upload_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    images.remove((Bitmap) v.getTag());
                    health_database_add_jianchabaogao_layout.removeView(view);
                }
            });
            imageView.setImageBitmap(images.get(i));
            health_database_add_jianchabaogao_layout.addView(view);
        }
    }

    public void showYaowuWenbenView() {
        health_database_add_xindiantu_layout.removeAllViews();
        for (int i = 0; i < ecgs.size(); i++) {
            final View view = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, health_database_add_xindiantu_layout, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.new_consult_image_upload);
            ImageView new_consult_upload_delete = (ImageView) view.findViewById(R.id.new_consult_upload_delete);
            new_consult_upload_delete.setTag(ecgs.get(i));
            new_consult_upload_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ecgs.remove((Bitmap) v.getTag());
                    health_database_add_xindiantu_layout.removeView(view);
                }
            });
            imageView.setImageBitmap(ecgs.get(i));
            health_database_add_xindiantu_layout.addView(view);
        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
