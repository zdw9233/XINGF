package com.uyi.app.ui.custom;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.ViewUtils;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.AppUtils;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

public abstract class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    protected Context context;

    public boolean isLooding = true;
    public int pageNo = 1;
    public int pageSize = 10;
    public int totalPage = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResouesId(), container, false);
        onInitLayoutBefore();
        ViewUtils.inject(this, view);
        return view;
    }


    @SuppressLint("NewApi")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.context = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && startImmersiveStatus()) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(getColorResourceId());
            onBuildVersionGT_KITKAT(tintManager.getConfig());
        }

        if (!AppUtils.checkDeviceHasNavigationBar(getActivity())) {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP && startImmersiveStatus()) {
                Window window = getActivity().getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
        onInitLayoutAfter();
    }

    /**
     * 获取布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutResouesId();


    /**
     * 初始化控件之前
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

    //是否开启沉浸式
    public boolean startImmersiveStatus() {
        return true;
    }

    /**
     * 当android版本大于4.4的额外处理
     * android 4.4 以上会启用沉浸式状态栏  会导致view被顶到顶部
     * 留这个函数是为了让每个界面自己处理view的paddingtop来解决
     *
     * @param systemBarConfig
     */
    protected abstract void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig);

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
