package com.uyi.app.ui.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.service.MessageService;
import com.uyi.app.ui.common.RegisterInfoAcitivity;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.personal.adapter.PersonalPagerAdapter;
import com.uyi.app.ui.personal.exclusive.ExclusiveActivity;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.ui.personal.questions.HealthyQuestionsActivity;
import com.uyi.app.ui.personal.schedule.ScheduleActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    List<T> list = new ArrayList<>();
    private int[] radioIds = {R.id.radioButton1, R.id.radioButton2};

    private MessageReceiver mMessageReceiver;

    PersonalPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_personal_center_new;
    }

    @Override
    protected void onInitLayoutAfter() {
        RequestManager.getObject(String.format(Constens.ELECTROCARDIOGRAN, UserInfoManager.getLoginUserInfo(getContext()).userId), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    PersonalPagerAdapter.PagerData pagerData = JSON.parseObject(data.toString(), PersonalPagerAdapter.PagerData.class);
                    mPagerAdapter.setPagerData(pagerData);
                    mPagerAdapter.notifyDataSetChanged();
                }catch (Exception e){

                }

            }
        });
        RequestManager.getObject(String.format("http://121.42.142.228:8080/app/api/servicePackage/query/custom"), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    Log.e("data=",data.toString());
                }catch (Exception e){

                }

            }
        });


        mViewPager.setAdapter(mPagerAdapter = new PersonalPagerAdapter(context, list));
        mViewPager.addOnPageChangeListener(this);

        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showTitle(true).showRight(true).setTitle("首页").setTitleColor(getResources().getColor(R.color.blue));

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
                startActivity(new Intent(context, RegisterInfoAcitivity.class));
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
