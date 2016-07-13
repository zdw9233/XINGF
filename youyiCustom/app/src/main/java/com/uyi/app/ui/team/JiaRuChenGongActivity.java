package com.uyi.app.ui.team;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.consult.NewConsultActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.custom.app.R;




@ContentView(R.layout.group_jiaru_sucess)
public class JiaRuChenGongActivity extends BaseActivity{

	@ViewInject(R.id.jiaru_zixun) private Button jiaru_zixun;
	@ViewInject(R.id.jiaru_headerView) private HeaderView jiaru_headerView;
	@Override
	protected void onInitLayoutAfter() {
		jiaru_headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("转入团队").setTitleColor(getResources().getColor(R.color.blue)).showRight(true) ;
		jiaru_zixun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JiaRuChenGongActivity.this.finish();
				startActivity(new Intent(activity, NewConsultActivity.class));
				
			}
		});
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		// TODO Auto-generated method stub
		jiaru_headerView.setKitkat(systemBarConfig);
	}

}
