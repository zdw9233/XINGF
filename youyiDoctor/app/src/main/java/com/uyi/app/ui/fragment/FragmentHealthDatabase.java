package com.uyi.app.ui.fragment;

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
 * 健康数据库
 * @author user
 *
 */
public class FragmentHealthDatabase extends BaseFragment implements OnClickListener, OnTabChanage {

	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.textView1) private TextView textView;
	
	public Main main;
	public FragmentHealthDatabase(Main main) {
		this.main = main;
	}
	@Override
	protected int getLayoutResouesId() {
		return R.layout.fragment_consultation;
	}

	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftHeader(true,UserInfoManager.getLoginUserInfo(context).icon).showTab(true).showRight(true).setLeftOnClick(this).setRightOnClick(this).setOnTabChanage(this);
		String[] str = getResources().getStringArray(R.array.health_manager);
		headerView.setTitleTabs(str);
		headerView.selectTabItem(2);
		
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
		
	}

}
