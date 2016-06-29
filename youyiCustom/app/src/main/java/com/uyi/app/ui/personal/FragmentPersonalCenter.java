package com.uyi.app.ui.personal;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.UserInfoManager;
import com.uyi.app.service.MessageService;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.personal.exclusive.ExclusiveActivity;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.ui.personal.questions.HealthyQuestionsActivity;
import com.uyi.app.ui.personal.schedule.ScheduleActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 个人中心
 * @author user
 *
 */
public class FragmentPersonalCenter extends BaseFragment {
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.center_rz_count) private TextView center_rz_count;
	@ViewInject(R.id.center_wenda_count) private TextView center_wenda_count;
	@ViewInject(R.id.center_zs_zixun_count) private TextView center_zs_zixun_count;
	@ViewInject(R.id.center_tz_count) private TextView center_tz_count;
	
	@ViewInject(R.id.persinal_center_rizhi_layout) private LinearLayout persinal_center_rizhi_layout;
	@ViewInject(R.id.persinal_center_tongzhi_layout) private LinearLayout persinal_center_tongzhi_layout;
	@ViewInject(R.id.persinal_center_zhuanshuzhixun_layout) private LinearLayout persinal_center_zhuanshuzhixun_layout;
	@ViewInject(R.id.persinal_center_yonghu_layout) private LinearLayout persinal_center_yonghu_layout;
	@ViewInject(R.id.persinal_center_message_layout) private LinearLayout persinal_center_message_layout;
	@ViewInject(R.id.persinal_center_gonggao_layout) private LinearLayout persinal_center_gonggao_layout;
	@ViewInject(R.id.persinal_center_taolunzu_layout) private LinearLayout persinal_center_taolunzu_layout;
	@ViewInject(R.id.persinal_center_jiankangwenda_layout) private LinearLayout persinal_center_jiankangwenda_layout;
	
	
	public Main main;
	public FragmentPersonalCenter(Main main) {
		this.main = main;
	}
	@Override
	protected int getLayoutResouesId() {
		return R.layout.fragment_personal_center;
	}

	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftHeader(true,UserInfoManager.getLoginUserInfo(context).icon).showTitle(true).showRight(true).setTitle("个人中心").setTitleColor(getResources().getColor(R.color.blue));
	}

	
	@Override
	public void onResume() {
		super.onResume();
		L.d(TAG, "onResume");
		MessageService.loadMessagesAll(context);
		// 日程
		int count = MessageService.getMessageCount(getView().getContext(), MessageService.RC);
		if (count == 0) {
			center_rz_count.setVisibility(View.INVISIBLE);
		} else {
			center_rz_count.setVisibility(View.VISIBLE);
			center_rz_count.setText(count + "");
		}
		// 通知
		count = MessageService.getMessageCount(getView().getContext(), MessageService.IM);
		if (count == 0) {
			center_tz_count.setVisibility(View.INVISIBLE);
		} else {
			center_tz_count.setVisibility(View.VISIBLE);
			center_tz_count.setText(count + "");
		}

		// 咨询数
//		count = MessageService.getMessageCount(getView().getContext(), MessageService.ZX);
		count = 0;
		if (count == 0) {
			center_zs_zixun_count.setVisibility(View.INVISIBLE);
		} else {
			center_zs_zixun_count.setVisibility(View.VISIBLE);
			center_zs_zixun_count.setText(count + "");
		}
		// 健康问答
		count = MessageService.getMessageCount(getView().getContext(), MessageService.WD);
		if (count == 0) {
			center_wenda_count.setVisibility(View.INVISIBLE);
		} else {
			center_wenda_count.setVisibility(View.VISIBLE);
			center_wenda_count.setText(count + "");
		}
	}
	
	@OnClick(
				{
					R.id.persinal_center_rizhi_layout,
					R.id.persinal_center_tongzhi_layout,
					R.id.persinal_center_zhuanshuzhixun_layout,
					R.id.persinal_center_yonghu_layout,
					R.id.persinal_center_message_layout,
					R.id.persinal_center_gonggao_layout,
					R.id.persinal_center_taolunzu_layout,
					R.id.persinal_center_jiankangwenda_layout
				}
			)
	public void click(View v){
		if(v.getId() == R.id.persinal_center_rizhi_layout){
			startActivity(new Intent(context, ScheduleActivity.class));
		}else if(v.getId() == R.id.persinal_center_tongzhi_layout){
			startActivity(new Intent(context, MessageActivity.class));
		}else if(v.getId() == R.id.persinal_center_zhuanshuzhixun_layout){
			startActivity(new Intent(context, ExclusiveActivity.class));
		}else if(v.getId() == R.id.persinal_center_yonghu_layout){
			
		}else if(v.getId() == R.id.persinal_center_message_layout){
			
		}else if(v.getId() == R.id.persinal_center_gonggao_layout){
			
		}else if(v.getId() == R.id.persinal_center_taolunzu_layout){
			
		}else if(v.getId() == R.id.persinal_center_jiankangwenda_layout){
			startActivity(new Intent(context, HealthyQuestionsActivity.class));
		}
	}
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

}
