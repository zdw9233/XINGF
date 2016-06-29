package com.uyi.app.ui.custom;

import com.android.volley.Request;
import com.lidroid.xutils.ViewUtils;
import com.uyi.app.UYIApplication;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.AppUtils;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * 统一Activity父类
 * @author chao
 *
 */
public abstract class BaseActivity extends Activity {

	protected  String TAG =  getClass().getSimpleName();
	protected UYIApplication application= null;
    public Activity activity;	
    /**屏幕信息*/
	public static DisplayMetrics dm ;
	/** 屏幕DPI*/
	public static int densityDpi = 0;
	/** 屏幕宽度（像素）*/
	public static int widthPixels = 0; 
	/** 屏幕高度（像素）*/
	public static int heightPixels = 0;
	
	public boolean isLooding = true;
	public int pageNo = 1;
	public int pageSize = 20;
	public int totalPage = 10;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (UYIApplication)getApplication();
		activity=this;
		onInitLayoutBefore();
		ViewUtils.inject(this);
		loadInfo();
		onInitLayoutAfter();
		Window window = getWindow();  
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && startImmersiveStatus()) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(getColorResourceId());
			onBuildVersionGT_KITKAT(tintManager.getConfig());
		}
		if(!AppUtils.checkDeviceHasNavigationBar(activity)){
			if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP && startImmersiveStatus() ) {  
	            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  
	            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
	                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  
	                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);  
	            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  
	            window.setStatusBarColor(Color.TRANSPARENT);  
	            window.setNavigationBarColor(Color.TRANSPARENT);  
	        } 
		}
	}
	private void loadInfo() {
	   //屏幕信息
	   dm = new DisplayMetrics();
	   getWindowManager().getDefaultDisplay().getMetrics(dm);
	   densityDpi = dm.densityDpi;
	   widthPixels = dm.widthPixels;
	   heightPixels = dm.heightPixels;
	}
	/**
	 * 初始化控件之前
	 */
	protected  void onInitLayoutBefore(){
		
	}
	
	public boolean startImmersiveStatus(){
		return true;
	}
	
	/**
	 *  初始化控件之后
	 */
	protected abstract void onInitLayoutAfter();
	
	/**
	 * 沉浸式状态栏颜色默认透明   需要改变颜色就重载本函数
	 * @return
	 */
	protected int getColorResourceId(){
	     return R.color.blue;
	}
	
	
	/**
	 * 当android版本大于4.4的额外处理
	 * android 4.4 以上会启用沉浸式状态栏  会导致view被顶到顶部
	 * 留这个函数是为了让每个界面自己处理view的paddingtop来解决
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
	protected void onStop() {
		super.onStop();
		RequestManager.cancelAll(this);
	}
	
}
