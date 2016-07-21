package com.uyi.app.ui.health;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.doctor.app.R;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.health_manager_main)
public class HealthManagerMain extends BaseFragmentActivity  {
	private FragmentManager fm;// fragment管理器
	private List<Fragment> fragments = new ArrayList<Fragment>();
	

	
	public void replaceView(int postion){
		fm.beginTransaction().replace(R.id.health_main_contalner, fragments.get(postion)).commit();

	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		
	}

	@Override
	protected void onInitLayoutAfter() {
		fragments.add(new FragmentHealthManager().setMain(this));//健康管理
		fragments.add(new FragmentHealthDatabase().setMain(this));//健康数据库
		fragments.add(new FragmentWearableDevice().setMain(this));//可穿戴设备
		ViewUtils.inject(this);
		fm = getSupportFragmentManager();
		replaceView(0);
		
	}
	
	/**
	 * 菜单、返回键响应
	 */
	
}
