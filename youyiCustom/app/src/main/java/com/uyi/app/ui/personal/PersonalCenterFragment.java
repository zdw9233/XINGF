package com.uyi.app.ui.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.UserInfoManager;
import com.uyi.app.service.MessageService;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.personal.adapter.PersonalPagerAdapter;
import com.uyi.app.ui.personal.exclusive.ExclusiveActivity;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.ui.personal.questions.HealthyQuestionsActivity;
import com.uyi.app.ui.personal.schedule.ScheduleActivity;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;

/**
 * 个人中心（新） Created by Leeii on 2016/6/19.
 */
public class PersonalCenterFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.viewPager)
    private ViewPager mViewPager;
    @ViewInject(R.id.radioGroup)
    private RadioGroup mRadioGroup;
    @ViewInject(R.id.schedule_num)
    private TextView mScheduleNum;
    @ViewInject(R.id.notice_num)
    private TextView mNoticeNum;
    @ViewInject(R.id.consulting_num)
    private TextView mConsultingNum;
    @ViewInject(R.id.question_num)
    private TextView mQuestionNum;

    private int[] radioIds = {R.id.radioButton1, R.id.radioButton2};

    private MessageReceiver mMessageReceiver;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_personal_center_new;
    }

    @Override
    protected void onInitLayoutAfter() {
        mViewPager.setAdapter(new PersonalPagerAdapter(context));
        mViewPager.addOnPageChangeListener(this);

        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showTitle(true).showRight(true).setTitle("个人中心").setTitleColor(getResources().getColor(R.color.blue));

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @OnClick({
            R.id.sxfw,  //安心服务
            R.id.txfw,  //糖心服务
            R.id.dzfw,  //定制服务
            R.id.schedule,  //日程
            R.id.notice,  //通知
            R.id.consulting,  //专属咨询
            R.id.question,  //健康问答
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.sxfw:
                startActivity(new Intent(context, EaseServiceActivity.class));
                break;  //随心服务
            case R.id.txfw:
                startActivity(new Intent(context, SugarServiceActivity.class));
                break;  //糖心服务
            case R.id.dzfw:
                break;   //定制服务
            case R.id.schedule:
                startActivity(new Intent(context, ScheduleActivity.class));
                break;   //日程
            case R.id.notice:
                startActivity(new Intent(context, MessageActivity.class));
                break;   //通知
            case R.id.consulting:
                startActivity(new Intent(context, ExclusiveActivity.class));
                break;   //专属咨询
            case R.id.question:
                startActivity(new Intent(context, HealthyQuestionsActivity.class));
                break;   //健康问答
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRadioGroup.check(radioIds[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMessageReceiver == null) {
            mMessageReceiver = new MessageReceiver();
        }
        IntentFilter intentFilter = new IntentFilter("com.uyi.message");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon);
        L.d(TAG, "onResume");
        MessageService.loadMessagesAll(context);
        refreshMessage();
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshMessage();
        }
    }

    private void refreshMessage() {
        // 日程
        int count = MessageService.getMessageCount(context, MessageService.RC);
        if (count == 0) {
            mScheduleNum.setVisibility(View.INVISIBLE);
        } else {
            mScheduleNum.setVisibility(View.VISIBLE);
            mScheduleNum.setText(count + "");
        }
        // 通知
        count = MessageService.getMessageCount(context, MessageService.IM);
        if (count == 0) {
            mNoticeNum.setVisibility(View.INVISIBLE);
        } else {
            mNoticeNum.setVisibility(View.VISIBLE);
            mNoticeNum.setText(count + "");
        }

        // 咨询数
//		count = MessageService.getMessageCount(context, MessageService.ZX);
        count = 0;
        if (count == 0) {
            mConsultingNum.setVisibility(View.INVISIBLE);
        } else {
            mConsultingNum.setVisibility(View.VISIBLE);
            mConsultingNum.setText(count + "");
        }
        // 健康问答
        count = MessageService.getMessageCount(context, MessageService.WD);
        if (count == 0) {
            mQuestionNum.setVisibility(View.INVISIBLE);
        } else {
            mQuestionNum.setVisibility(View.VISIBLE);
            mQuestionNum.setText(count + "");
        }
    }
}
