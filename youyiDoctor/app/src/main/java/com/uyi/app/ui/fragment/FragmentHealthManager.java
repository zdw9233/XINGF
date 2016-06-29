package com.uyi.app.ui.fragment;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.HeaderView.OnTabChanage;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 健康管理
 * @author user
 *
 */
public class FragmentHealthManager extends BaseFragment implements OnClickListener, OnTabChanage {
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.imageView1) private ImageView imageView1;

	String imageUrl = "http://c.hiphotos.baidu.com/image/w%3D1280%3Bcrop%3D0%2C0%2C1280%2C800/sign=2abcf809eb24b899de3c7d3a563626f6/43a7d933c895d143afcf362a71f082025aaf0779.jpg";
	public Main main;
	public FragmentHealthManager(Main main) {
		this.main = main;
	}

	@Override
	protected int getLayoutResouesId() {
		return R.layout.fragment_two;
	}

	@Override
	protected void onInitLayoutAfter() {
		if(UserInfoManager.getLoginUserInfo(context) == null){
			return;
		}
		headerView.showLeftHeader(true,UserInfoManager.getLoginUserInfo(context).icon).showTab(true).showRight(true).setOnTabChanage(this);
		String[] str = getResources().getStringArray(R.array.health_manager);
		headerView.setTitleTabs(str);
		headerView.selectTabItem(1);
		ImageCacheManager.loadImage(imageUrl, ImageCacheManager.getImageListener(imageView1, this.getResources().getDrawable(R.drawable.manager_icon), null));
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
