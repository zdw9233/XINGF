package com.uyi.app.ui.consult;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.custom.app.R;

import android.widget.Button;


/**
 * 咨询提交成功
 * @author user
 *
 */
@ContentView(R.layout.consult_success)
public class ConsultSuccessActivity extends BaseActivity{
	
	@ViewInject(R.id.headerView) private HeaderView headerView; 
	@ViewInject(R.id.consult_success_submit) private Button consult_success_submit; 
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("提交成功").setTitleColor(getResources().getColor(R.color.blue));
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
