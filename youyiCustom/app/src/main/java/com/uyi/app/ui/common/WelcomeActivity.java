package com.uyi.app.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.uyi.app.ui.Main;
import com.uyi.custom.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ThinkPad on 2016/6/29.
 */




public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        final Intent it = new Intent(this, Main.class); //你要转向的Activity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
                WelcomeActivity.this.finish();
            }
        };
        timer.schedule(task, 1000 * 2); //10秒后

    }
}
