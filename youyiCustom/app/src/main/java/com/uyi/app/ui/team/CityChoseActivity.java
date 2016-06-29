package com.uyi.app.ui.team;

import android.widget.EditText;
import android.widget.ListView;

import android.widget.LinearLayout;

import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseActivity;

import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.custom.app.R;

@ContentView(R.layout.city_chose)
public class CityChoseActivity extends BaseActivity{
	@ViewInject(R.id.back_city) private LinearLayout back_city; 
	@ViewInject(R.id.danqiancity) private TextView danqiancity; 
	@ViewInject(R.id.team_city_edit_text) private EditText team_city_edit_text; 
	@ViewInject(R.id.city_list) private ListView city_list; 
	
	
	@Override
	protected void onInitLayoutAfter() {
	
		
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		// TODO Auto-generated method stub
		
	}

}
