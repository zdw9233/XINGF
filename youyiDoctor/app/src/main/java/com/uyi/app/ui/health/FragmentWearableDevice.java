package com.uyi.app.ui.health;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.HeaderView.OnTabChanage;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.doctor.app.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


/**
 * 可穿戴设备
 * @author user
 *
 */
public class FragmentWearableDevice extends BaseFragment implements OnClickListener, OnTabChanage {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	
	public HealthManagerMain main;
	public FragmentWearableDevice(HealthManagerMain healthManagerMain) {
		this.main = healthManagerMain;
	}
	
	@Override
	protected int getLayoutResouesId() {
		return R.layout.fragment_wearable_device;
	}

	@Override
	protected void onInitLayoutAfter() {
//		headerView.showLeftHeader(true,UserInfoManager.getLoginUserInfo(context).icon).showTab(true).showRight(true).setOnTabChanage(this);
		headerView.showLeftReturn(true).showTab(true).showRight(true).setOnTabChanage(this);
		String[] str = getResources().getStringArray(R.array.health_manager);
		headerView.setTitleTabs(str);
		headerView.selectTabItem(3);
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void onChanage(int postion) {
		if(postion == 1){
			main.replaceView(0);
		}else if(postion == 2){
			main.replaceView(1);
		}
	}

}
