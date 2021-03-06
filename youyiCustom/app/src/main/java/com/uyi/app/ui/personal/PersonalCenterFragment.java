package com.uyi.app.ui.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.service.MessageService;
import com.uyi.app.ui.consult.NewConsultActivity;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.newCalendar.CalendarRCActivity;
import com.uyi.app.ui.personal.adapter.PersonalPagerAdapter;
import com.uyi.app.ui.personal.exclusive.ExclusiveActivity;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.ui.personal.model.PagerData;
import com.uyi.app.ui.personal.questions.HealthyQuestionsActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
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
    UserInfo userInfo;
    private MessageReceiver mMessageReceiver;

    PersonalPagerAdapter mPagerAdapter;
    PagerData pagerData;

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_personal_center_new;
    }

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showTitle(true).showRight(true).setTitle("首页").setTitleColor(getResources().getColor(R.color.blue));
        pagerData = new PagerData();
        mViewPager.addOnPageChangeListener(this);
        RequestManager.getObject(String.format(Constens.ELECTROCARDIOGRAN, UserInfoManager.getLoginUserInfo(getContext()).userId), this, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.e("data==", data.toString());
//                    PagerData pagerData = JSON.parseObject(data.toString(), PersonalPagerAdapter.PagerData.class);

                    pagerData.setBloodPressure_pic(data.getString("bloodPressure_pic"));
                    pagerData.setBloodSugar_pic(data.getString("bloodSugar_pic"));
                    pagerData.setComment1(data.getString("comment1"));
                    if(data.has("comment2")){
                        pagerData.setComment2(data.getString("comment2"));
                    }else{
                        pagerData.setComment2("暂无数据！");
                    }
                    L.e("pagerData==",pagerData.toString());
//                    mPagerAdapter.setPagerData(pagerData);
                    mPagerAdapter = new PersonalPagerAdapter(context, pagerData);
                    mViewPager.setAdapter(mPagerAdapter);
                    mPagerAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    L.e("e==", e.toString());
                }

            }
        }, new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {
                pagerData.setComment1("最近没有任何测试！");
                mPagerAdapter = new PersonalPagerAdapter(context, pagerData);
                mViewPager.setAdapter(mPagerAdapter);
                mPagerAdapter.notifyDataSetChanged();
            }
        });
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
            R.id.doctor_help,//醫師互動
            R.id.consulting,  //专属咨询
            R.id.question,  //健康问答
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.sxfw:
                startActivity(new Intent(context, com.uyi.app.ui.personal.newService.EaseServiceActivity.class));
                break;  //随心服务
            case R.id.txfw:
                startActivity(new Intent(context, com.uyi.app.ui.personal.newService.SugarServiceActivity.class));
                break;  //糖心服务
            case R.id.dzfw:
                startActivity(new Intent(context, com.uyi.app.ui.personal.newService.SuiServiceActivity.class));
                break;   //定制服务
            case R.id.schedule:
//                startActivity(new Intent(context, CalendarActivity.class));
                startActivity(new Intent(context, CalendarRCActivity.class));
//                startActivity(new Intent(context, ScheduleActivity.class));
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
            case R.id.doctor_help:
                userInfo = UserInfoManager.getLoginUserInfo(getContext());

                RequestManager.getObject(Constens.HAVE_NUMBER, getContext(), new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                        try {
                            System.out.println(data.toString());
                            if (data.getInt("doctorAdvisory") > 0) {
                                startActivity(new Intent(context, NewConsultActivity.class));
//                                Intent intent = new Intent(context, NewConsultActivity.class);
//                                startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
                            } else {
//                                if (userInfo.isFree == 2) {
//                                    T.showShort(getContext(), " 本月医疗咨询次数使用完毕");
//
//                                } else {
//                                    T.showShort(getContext(), " 该服务仅针对服务包用户，请购买相应服务包！");
//                                }
                                new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("您的本月医师互动次数已使用完毕，如有疑问请咨询您的慢病管理师！").setPositiveButton("确定", null).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;   //醫師互動
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
