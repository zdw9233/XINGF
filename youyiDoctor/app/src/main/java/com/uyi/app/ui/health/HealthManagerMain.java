package com.uyi.app.ui.health;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.service.MessageService;
import com.uyi.app.service.UpdateManager;
import com.uyi.app.service.UpdateManager.CheckUpdateCallbackListenner;
import com.uyi.app.service.UserService;
import com.uyi.app.ui.common.LoginActivity;
import com.uyi.app.ui.consult.FragmentConsultation;
import com.uyi.app.ui.consult.FragmentFollow;
import com.uyi.app.ui.consult.FragmentLineInspection;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.health.FragmentHealthDatabase;
import com.uyi.app.ui.health.FragmentHealthListManager;
import com.uyi.app.ui.health.FragmentHealthManager;
import com.uyi.app.ui.health.FragmentWearableDevice;
import com.uyi.app.ui.personal.FragmentPersonalCenter;
import com.uyi.app.ui.team.FragmentHealthTeam;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		fragments.add(new FragmentHealthManager(this));//健康管理
		fragments.add(new FragmentHealthDatabase(this));//健康数据库
		fragments.add(new FragmentWearableDevice(this));//可穿戴设备
		ViewUtils.inject(this);
		fm = getSupportFragmentManager();
		replaceView(0);
		
	}
	
	/**
	 * 菜单、返回键响应
	 */
	
}
