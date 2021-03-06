package com.uyi.app.ui.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.UserInfoManager;
import com.uyi.app.service.MessageService;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.newCalendar.CalendarRCActivity;
import com.uyi.app.ui.personal.customer.CustomerActivity;
import com.uyi.app.ui.personal.discuss.DiscussGroupActivity;
import com.uyi.app.ui.personal.exclusive.ExclusiveActivity;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.ui.personal.messagemanager.MessageManagerActivity;
import com.uyi.app.ui.personal.notice.NoticeActivity;
import com.uyi.app.ui.personal.questions.HealthyQuestionsActivity;
import com.uyi.app.utils.L;
import com.uyi.doctor.app.R;

/**
 * 个人中心（新） Created by Leeii on 2016/6/19.
 */
public class PersonalCenterFragment extends BaseFragment {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.schedule_num)
    private TextView mScheduleNum;
    @ViewInject(R.id.notice_num)
    private TextView mNoticeNum;
    @ViewInject(R.id.consulting_num)
    private TextView mConsultingNum;
    @ViewInject(R.id.question_num)
    private TextView mQuestionNum;
    @ViewInject(R.id.discussion_num)
    private TextView mDiscussionNum;
    private MessageReceiver mMessageReceiver;


    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_personal_center_new;
    }

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showTitle(true).showRight(true).setTitle("首页").setTitleColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @OnClick({
            R.id.schedule,  //日程
            R.id.notice,  //通知
            R.id.consulting,  //专属咨询
            R.id.user,  //用户
            R.id.message,  //消息管理
            R.id.announcement,  //公告管理
            R.id.discussion,  //讨论组
            R.id.question,  //健康问答
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.schedule:
//                startActivity(new Intent(context, ScheduleActivity.class));
                startActivity(new Intent(context, CalendarRCActivity.class));
                break;   //日程
            case R.id.notice:
                startActivity(new Intent(context, MessageActivity.class));
                break;   //通知
            case R.id.consulting:
                startActivity(new Intent(context, ExclusiveActivity.class));
                break;   //专属咨询
            case R.id.user:
                startActivity(new Intent(context, CustomerActivity.class));
                break;  //用户
            case R.id.message:
                startActivity(new Intent(context, MessageManagerActivity.class));
                break; //消息管理
            case R.id.announcement:
                startActivity(new Intent(context, NoticeActivity.class));
                break; //公告管理
            case R.id.discussion:
                startActivity(new Intent(context, DiscussGroupActivity.class));
                break;  //讨论组
            case R.id.question:
                startActivity(new Intent(context, HealthyQuestionsActivity.class));
                break;   //健康问答
        }
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
