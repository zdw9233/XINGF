package com.uyi.app.ui.personal.messagemanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.personal.messagemanager.fragment.MessageConstomFragment;
import com.uyi.app.ui.personal.messagemanager.fragment.MessageDoctorListFragment;
import com.uyi.doctor.app.R;

import java.util.ArrayList;
import java.util.List;



/**
 * 消息管理
 * @author user
 *
 */
@ContentView(R.layout.message_manager)
public class MessageManagerActivity extends BaseFragmentActivity implements HeaderView.OnTabChanage {
	private List<Fragment> fragments;
	private Fragment currentFragment;
	@ViewInject(R.id.headerView) private HeaderView headerView;

	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTab(true).showRight(true).setOnTabChanage(this);
		String[] str = getResources().getStringArray(R.array.message_who);
		headerView.setTitleTabs(str);
		fragments = new ArrayList<>();
		fragments.add(new MessageConstomFragment());
		fragments.add(new MessageDoctorListFragment());
		headerView.selectTabItem(1);
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}



	@Override
	public void onChanage(int postion) {
		if(postion == 1){
			changeFragment(0);
		}else{
			changeFragment(1);
		}

	}
	private void changeFragment(int position) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment f = fragments.get(position);
		if (!f.isAdded()) {
			ft.add(R.id.content, f);
			if (currentFragment != null) {
				ft.hide(currentFragment);
			}
			currentFragment = f;
			ft.show(f);
		} else {
			if (f != currentFragment) {
				ft.hide(currentFragment);
				currentFragment = f;
				ft.show(f);
			}
		}
		ft.commit();
	}
}
