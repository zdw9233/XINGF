package com.uyi.app.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.uyi.app.Constens;
import com.uyi.app.ui.Main;
import com.uyi.app.utils.ImageUtil;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ThinkPad on 2016/6/29.
 */




public class WelcomeActivity extends AppCompatActivity {
private SimpleDraweeView welcomePictrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        final Intent it = new Intent(this, Main.class); //你要转向的Activit
        RequestManager.getObject(String.format(Constens.WELCOME_PICTRUE,"Android"), this,null,new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {

                try {
                    Log.e("DATA  = ", data.toString());
                    if(!data.getString("url").equals("null")){
                        ImageUtil.load(data.getJSONArray("url").get(0).toString(),welcomePictrue);
                    }
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(it); //执行
                            WelcomeActivity.this.finish();
                        }
                    };
                    timer.schedule(task, 1000 * 2); //10秒后
                } catch (JSONException e) {
                    e.printStackTrace();
                    startActivity(it); //执行
                    WelcomeActivity.this.finish();
                }
            }
        },new RequestErrorListener() {
            public void requestError(VolleyError e) {
                if (e.networkResponse != null) {
                    startActivity(it); //执行
                    WelcomeActivity.this.finish();
                }
            }
        });

        welcomePictrue = (SimpleDraweeView) findViewById(R.id.welcome_image);
        welcomePictrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(it); //执行
                RequestManager.cancelAll(this);
                WelcomeActivity.this.finish();
            }
        });



    }
}
