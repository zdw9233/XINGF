package com.uyi.app.ui.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.health.HealthDatabaseActivity;
import com.uyi.app.ui.newCalendar.CalendarRCActivity;
import com.uyi.app.ui.personal.adapter.PersonalPagerAdapter;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.ui.personal.model.PagerData;
import com.uyi.app.ui.view.AutoScrollViewPager;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页（新） Created by Leeii on 2016/6/19.
 */
public class HomeFragment2_1 extends BaseFragment implements ViewPager.OnPageChangeListener {
    @ViewInject(R.id.fragment_home_2_1)
    private LinearLayout fragment_home_2_1;
    @ViewInject(R.id.viewPager)
    private AutoScrollViewPager mViewPager;
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
    private int[] radioIds = {R.id.radioButton1, R.id.radioButton2,R.id.radioButton3};
    UserInfo userInfo;
    private MessageReceiver mMessageReceiver;

    PersonalPagerAdapter mPagerAdapter;
    PagerData pagerData;
    @ViewInject(R.id.vf)
    private UPMarqueeView vf;
    List<String> textdata = new ArrayList<>();
    List<View> views = new ArrayList<>();
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_home_2_1;
    }

    @Override
    protected void onInitLayoutAfter() {
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
                    mViewPager.startAutoScroll();
                    mViewPager.setInterval(4000);
                    mViewPager.setSlideBorderMode(2);
                    mViewPager.setStopScrollWhenTouch(false);

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
        initdata();


    }
    /**
     * 初始化界面程序
     */
    private void initView() {
        setView();
        vf.setViews(views);
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < textdata.size(); i = i + 2) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.tv2);
            //进行对控件赋值
            tv1.setText(textdata.get(i).toString());
            if (textdata.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(textdata.get(i + 1).toString());
            }else {
                moreView.findViewById(R.id.tv2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    /**
     * 初始化数据
     */
    private void initdata() {
        textdata = new ArrayList<>();
        RequestManager.getObject(String.format(Constens.HEALTH_ZIXUN,1, pageNo, 6),
                getActivity(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                     L.e(data.toString());
                        try {
                            JSONArray array = data.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                textdata.add(jsonObject.getString("title"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initView();
                    }
                });

    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        fragment_home_2_1.setPadding(0,systemBarConfig.getStatusBarHeight(),0,0);
    }

    @OnClick({
            R.id.my_servece,  //我的服务
            R.id.my_report,  //我的报告
            R.id.my_datas,  //我的数据
            R.id.my_schedule,  //我的日程
            R.id.title_messge,  //通知
            R.id.vf,  //资讯
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.my_servece:
//                Intent it = new Intent(getContext(), Main.class);
                Intent it = new Intent(getContext(), MyService.class);
                startActivity(it); //执行
                break;  //我的服务
            case R.id.vf:
                Intent it_vf = new Intent(getContext(), ZiXunListActivity.class);
                startActivity(it_vf); //执行
                break;  //资讯

            case R.id.my_report:
                getContext().startActivity(new Intent(getContext(), MyReport.class));
                break;  //我的报告
            case R.id.my_datas:
                getContext().startActivity(new Intent(getContext(), HealthDatabaseActivity.class));
                break;   //我的数据
            case R.id.my_schedule:
                getContext().startActivity(new Intent(getContext(), CalendarRCActivity.class));
                break;   //我的日程
            case R.id.title_messge:
                getContext().startActivity(new Intent(context, MessageActivity.class));
                break;   //通知

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
        L.d(TAG, "onResume");
        MessageService.loadMessagesAll(context);
//        refreshMessage();
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            refreshMessage();
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
