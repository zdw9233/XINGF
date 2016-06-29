package com.uyi.app.ui.fragment;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.doctor.app.R;

import android.view.View;
import android.view.View.OnClickListener;


/**
 * 健康团队
 * @author user
 *
 */
public class FragmentHealthTeam extends BaseFragment implements OnClickListener {
	@ViewInject(R.id.headerView) private HeaderView headerView;
	public Main main;
	public FragmentHealthTeam(Main main) {
		this.main = main;
	}
	@Override
	protected int getLayoutResouesId() {
		return R.layout.fragment_three;
	}

	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftHeader(true,UserInfoManager.getLoginUserInfo(context).icon).showTitle(true).showRight(true).setLeftOnClick(this).setRightOnClick(this);
		
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}
	@Override
	public void onClick(View v) {
		
	}

}
