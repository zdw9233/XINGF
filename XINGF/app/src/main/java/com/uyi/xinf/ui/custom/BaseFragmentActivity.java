package com.uyi.xinf.ui.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.ViewUtils;
import com.uyi.doctor.xinf.R;
import com.uyi.xinf.UYIApplication;
import com.uyi.xinf.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.xinf.utils.AppUtils;
import com.volley.RequestManager;


/**
 * 统一BaseFragmentActivity父类
 *
 * @author chao
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    protected UYIApplication application = null;
    public Activity activity;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (UYIApplication) getApplication();
        activity = this;
        onInitLayoutBefore();
        ViewUtils.inject(this);
        onInitLayoutAfter();
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(getColorResourceId());
            onBuildVersionGT_KITKAT(tintManager.getConfig());
        }

        if (!AppUtils.checkDeviceHasNavigationBar(activity)) {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 初始化控件
     */
    protected void onInitLayoutBefore() {

    }

    /**
     * 初始化控件之后
     */
    protected abstract void onInitLayoutAfter();

    /**
     * 沉浸式状态栏颜色默认透明   需要改变颜色就重载本函数
     *
     * @return
     */
    protected int getColorResourceId() {
        return R.color.blue;
    }


    /**
     * 当android版本大于4.4的额外处理
     * android 4.4 以上会启用沉浸式状态栏  会导致view被顶到顶部
     * 留这个函数是为了让每个界面自己处理view的paddingtop来解决
     *
     * @param systemBarConfig
     */
    protected abstract void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig);

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
