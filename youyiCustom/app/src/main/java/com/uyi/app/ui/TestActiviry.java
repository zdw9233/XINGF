package com.uyi.app.ui;

import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.custom.app.R;


@ContentView(R.layout.fragment_personal_2_1)
public class TestActiviry extends BaseActivity  {
    @ViewInject(R.id.lay2) private LinearLayout lay2;

	@Override
	protected void onInitLayoutAfter() {
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		if(systemBarConfig!=null)
		lay2.setPadding(0,systemBarConfig.getStatusBarHeight(),0,0);
	}



	

}
