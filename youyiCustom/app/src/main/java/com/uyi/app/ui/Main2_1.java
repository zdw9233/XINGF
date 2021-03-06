package com.uyi.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.service.MessageService;
import com.uyi.app.service.UpdateManager;
import com.uyi.app.service.UpdateManager.CheckUpdateCallbackListenner;
import com.uyi.app.service.UserService;
import com.uyi.app.ui.common.LoginActivity;
import com.uyi.app.ui.consult_2_1.fragment.ConsultFragment2_1;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.health.FollowUpPayActivity;
import com.uyi.app.ui.home.fragment.HomeFragment2_1;
import com.uyi.app.ui.personal_2_1.PersonalFragment2_1;
import com.uyi.app.utils.L;
import com.uyi.app.utils.NetUtils;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.main2_1)
public class Main2_1 extends BaseFragmentActivity implements MessageConform.OnMessageClick {
    UpdateManager manager = new UpdateManager();
    private Fragment currentFragment;
    private MessageConform conform;
    private FragmentManager fm;// fragment管理器
    public static Main2_1 main2_1;
    private List<Fragment> fragments = new ArrayList<Fragment>();

    public int currentPage = 1;
    @ViewInject(R.id.main_tab_1)
    public LinearLayout main_tab_1;
    @ViewInject(R.id.main_tab_2)
    public LinearLayout main_tab_2;
    @ViewInject(R.id.main_tab_3)
    public LinearLayout main_tab_3;

    @ViewInject(R.id.main_tab_1_icon)
    private ImageView main_tab_1_icon;
    @ViewInject(R.id.main_tab_2_icon)
    private ImageView main_tab_2_icon;
    @ViewInject(R.id.main_tab_3_icon)
    private ImageView main_tab_3_icon;

    @ViewInject(R.id.main_tab_1_text)
    private TextView main_tab_1_text;
    @ViewInject(R.id.main_tab_2_text)
    private TextView main_tab_2_text;
    @ViewInject(R.id.main_tab_3_text)
    private TextView main_tab_3_text;

    @ViewInject(R.id.couls_new)
    private TextView couls_new;

    // 点击事件
    @OnClick({R.id.main_tab_1, R.id.main_tab_2, R.id.main_tab_3})
    public void onClickTab(View view) {
        switch (view.getId()) {
            case R.id.main_tab_1:
//                currentPage = 1;
//                main_tab_1_icon.setImageResource(R.drawable.tab_home_selected);
//                main_tab_1_text.setTextColor(getResources().getColor(R.color.blue));
//                replaceView(0);
                changeFragment(0);
                break;
            case R.id.main_tab_2:
//                currentPage = 2;
//                main_tab_2_icon.setImageResource(R.drawable.tab_consult_selected);
//                main_tab_2_text.setTextColor(getResources().getColor(R.color.blue));
//                replaceView(1);
                changeFragment(1);
                break;
            case R.id.main_tab_3:
//                currentPage = 3;
//                main_tab_3_icon.setImageResource(R.drawable.tab_personal_selected);
//                main_tab_3_text.setTextColor(getResources().getColor(R.color.blue));
//                replaceView(2);
                changeFragment(2);
                break;
        }
    }


    public void replaceView(int postion) {//这样切换会销毁
        fm.beginTransaction().replace(R.id.main_contalner, fragments.get(postion)).commit();
        //displayCount();
    }


    public void displayCount() {
        int count = MessageService.getMessageCount(activity, MessageService.ZX);
//        if (count == 0) {
//            main_tab_1_count.setVisibility(View.INVISIBLE);
//        } else {
//            main_tab_1_count.setVisibility(View.VISIBLE);
//            main_tab_1_count.setText(count + "");
//        }
//        count = MessageService.getMessageCount(activity, MessageService.BJ);
//        if (count == 0) {
//            main_tab_2_count.setVisibility(View.INVISIBLE);
//        } else {
//            main_tab_2_count.setVisibility(View.VISIBLE);
//            main_tab_2_count.setText(count + "");
//        }
    }


    public void initView() {
        fragments.add(new HomeFragment2_1());//首页
        fragments.add(new ConsultFragment2_1());//咨询
        fragments.add(new PersonalFragment2_1());//我的
    }

    public void changeFragment(int position) {//这样切换fragment不会销毁
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fragments.get(position);
        if (!f.isAdded()) {
            ft.add(R.id.main_contalner, f);
            if (currentFragment != null) {
                ft.hide(currentFragment);
            }
            currentFragment = f;
            ft.show(f);
        } else {
            if (f != currentFragment) {
                ft.hide(currentFragment);
                currentFragment = f;
                ft.show(f);
            }
        }
        ft.commit();
        main_tab_1_icon.setImageResource(R.drawable.tab_home);
        main_tab_1_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_2_icon.setImageResource(R.drawable.tab_consult);
        main_tab_2_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_3_icon.setImageResource(R.drawable.tab_personal);
        main_tab_3_text.setTextColor(getResources().getColor(R.color.footer_somber));
        if (position == 0) {
            currentPage = 1;
            main_tab_1_icon.setImageResource(R.drawable.tab_home_selected);
            main_tab_1_text.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 1) {
            currentPage = 2;
            main_tab_2_icon.setImageResource(R.drawable.tab_consult_selected);
            main_tab_2_text.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 2) {
            currentPage = 3;
            main_tab_3_icon.setImageResource(R.drawable.tab_personal_selected);
            main_tab_3_text.setTextColor(getResources().getColor(R.color.blue));
        }
    }

    @Override
    protected void onInitLayoutAfter() {
        main2_1 = this;
        if (UserInfoManager.getLoginUserInfo(activity) == null) {
            startActivityForResult(new Intent(activity, LoginActivity.class), Constens.BACK_LOGIN);
        } else {
            ViewUtils.inject(this);
            initView();

            if (NetUtils.isConnected(activity)) {

                manager.isUpdate(activity, new CheckUpdateCallbackListenner() {
                    @Override
                    public void success(boolean isUpdate) {
                        if (isUpdate) {
                            manager.update(activity);
                        }
                        if (!isUpdate) {
                            getPay();
                        }
                    }
                });
            }

            fm = getSupportFragmentManager();
            UserService.loadUserInfo(application);
            MessageService.loadMessagesAll(activity);
//            replaceView(0);
//            getNews();
            changeFragment(0);
//            mBeansReceiver = new BeansReceiver();
//            registerReceiver(mBeansReceiver, new IntentFilter("com.uyi.beans"));
        }

    }

    public void getPay() {//查看是否有违付费的服务包
        RequestManager.getObject(Constens.GET_SERVER_THREE_PAY, this, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println("_____________________" + jsonObject.toString());
                JSONArray array = new JSONArray();
                try {
                    array = jsonObject.getJSONArray("unpaidTopThreeServiceJsonList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (array.length() > 0) {
                    if (conform == null) {
                        conform = new MessageConform(Main2_1.this, MessageConform.MessageType.CONFORM);
                    }
                    conform.setTitle("提示").setContent("您有需要付款的三甲服务包，是否马上去付款?").setOnMessageClick(Main2_1.this);
                    conform.show();
                }

            }
        }, new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {
                T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
//                if (e.networkResponse != null) {
//                    if (e.networkResponse.statusCode == 200) {
//                        if(conform == null){
//                            conform = new MessageConform(Main.this, MessageConform.MessageType.CONFORM);
//                        }
//                        conform.setTitle("提示").setContent("您有需要付款的三甲服务包，是否马上去付款?").setOnMessageClick(Main.this);
//                        conform.show();
//                    } else {
//                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
//                    }
//                } else {
//                    if(conform == null){
//                        conform = new MessageConform(Main.this, MessageConform.MessageType.CONFORM);
//                    }
//                    conform.setTitle("提示").setContent("您有需要付款的三甲服务包，是否马上去付款?").setOnMessageClick(Main.this);
//                    conform.show();
//                }
            }
        });


    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {

    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentPage == 1) {
                exitBy2Click();
            } else {
                onClickTab(main_tab_1);
            }
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            T.showLong(this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.BACK_LOGIN && resultCode == RESULT_OK) {
            onInitLayoutAfter();
        }
    }

    @Override
    protected void onDestroy() {
        if (mBeansReceiver != null)
            unregisterReceiver(mBeansReceiver);
        super.onDestroy();
    }
    private BeansReceiver mBeansReceiver;
    @Override
    public void onClick(MessageConform.Result result, Object object) {
        if (result == MessageConform.Result.OK) {
            startActivity(new Intent(Main2_1.this, FollowUpPayActivity.class));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getNews();

    }
    public void getNews() {
        RequestManager.getObject(Constens.DOCTOR_HEALTH_NEWS, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.e("MainNEW==",data.toString());
                            if(data.getInt("consult")> 0){
                                couls_new.setVisibility(View.VISIBLE);
                            }else{
                                couls_new.setVisibility(View.GONE);
                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class BeansReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            requestUpdateInfo();
        }
    }

    public void requestUpdateInfo() {
        RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    data.getString("beans");
                    UserInfo loginUserInfo = UserInfoManager.getLoginUserInfo(Main2_1.this);
                    loginUserInfo.beans = Integer.parseInt(data.getString("beans"));
                    UserInfoManager.setLoginUserInfo(Main2_1.this, loginUserInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
