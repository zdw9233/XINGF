package com.uyi.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.uyi.app.ui.consult.FragmentConsultation;
import com.uyi.app.ui.consult.FragmentFollow;
import com.uyi.app.ui.consult.FragmentLineInspection;
import com.uyi.app.ui.custom.BaseFragmentActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.health.FollowUpPayActivity;
import com.uyi.app.ui.health.FragmentWearableDevice;
import com.uyi.app.ui.health.HealthManagerFragment;
import com.uyi.app.ui.personal.PersonalCenterFragment;
import com.uyi.app.ui.team.FragmentHealthTeam;
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

@ContentView(R.layout.main)
public class Main extends BaseFragmentActivity implements MessageConform.OnMessageClick {
    UpdateManager manager = new UpdateManager();

    private MessageConform conform;
    private FragmentManager fm;// fragment管理器
    private List<Fragment> fragments = new ArrayList<Fragment>();

    public int currentPage = 1;
    @ViewInject(R.id.main_tab_1)
    public LinearLayout main_tab_1;
    @ViewInject(R.id.main_tab_2)
    public LinearLayout main_tab_2;
    @ViewInject(R.id.main_tab_3)
    public LinearLayout main_tab_3;
    @ViewInject(R.id.main_tab_4)
    public LinearLayout main_tab_4;

    @ViewInject(R.id.main_tab_1_icon)
    private ImageView main_tab_1_icon;
    @ViewInject(R.id.main_tab_2_icon)
    private ImageView main_tab_2_icon;
    @ViewInject(R.id.main_tab_3_icon)
    private ImageView main_tab_3_icon;
    @ViewInject(R.id.main_tab_4_icon)
    private ImageView main_tab_4_icon;

    @ViewInject(R.id.main_tab_1_text)
    private TextView main_tab_1_text;
    @ViewInject(R.id.main_tab_2_text)
    private TextView main_tab_2_text;
    @ViewInject(R.id.main_tab_3_text)
    private TextView main_tab_3_text;
    @ViewInject(R.id.main_tab_4_text)
    private TextView main_tab_4_text;

    @ViewInject(R.id.main_tab_1_count)
    private TextView main_tab_1_count;
    @ViewInject(R.id.main_tab_2_count)
    private TextView main_tab_2_count;

    // 点击事件
    @OnClick({R.id.main_tab_1, R.id.main_tab_2, R.id.main_tab_3, R.id.main_tab_4})
    public void onClickTab(View view) {

        main_tab_1_icon.setImageResource(R.drawable.nav_personalcenter_default);
        main_tab_1_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_2_icon.setImageResource(R.drawable.nav_manage_default);
        main_tab_2_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_3_icon.setImageResource(R.drawable.nav_team_default);
        main_tab_3_text.setTextColor(getResources().getColor(R.color.footer_somber));

        main_tab_4_icon.setImageResource(R.drawable.nav_consultation_default);
        main_tab_4_text.setTextColor(getResources().getColor(R.color.footer_somber));

        switch (view.getId()) {
            case R.id.main_tab_1:
                currentPage = 1;
                main_tab_1_icon.setImageResource(R.drawable.nav_personalcenter_selected);
                main_tab_1_text.setTextColor(getResources().getColor(R.color.blue));
                replaceView(0);
                break;
            case R.id.main_tab_2:
                currentPage = 2;
                main_tab_2_icon.setImageResource(R.drawable.nav_manage_selected);
                main_tab_2_text.setTextColor(getResources().getColor(R.color.blue));
                replaceView(1);
                break;
            case R.id.main_tab_3:
                currentPage = 3;
                main_tab_3_icon.setImageResource(R.drawable.nav_team_selected);
                main_tab_3_text.setTextColor(getResources().getColor(R.color.blue));
                replaceView(2);
                break;
            case R.id.main_tab_4:
//			Intent intent = new Intent(activity, RecylerViewActiviry.class);
//			startActivity(intent);
                System.out.println("登陆信息======" + UserInfoManager.getLoginUserInfo(Main.this).toString());
                currentPage = 4;
                main_tab_4_icon.setImageResource(R.drawable.nav_consultation_selected);
                main_tab_4_text.setTextColor(getResources().getColor(R.color.blue));
                replaceView(3);
                break;
        }
    }


    public void replaceView(int postion) {
        fm.beginTransaction().replace(R.id.main_contalner, fragments.get(postion)).commit();
        //displayCount();
    }


    public void displayCount() {
        int count = MessageService.getMessageCount(activity, MessageService.ZX);
        if (count == 0) {
            main_tab_1_count.setVisibility(View.INVISIBLE);
        } else {
            main_tab_1_count.setVisibility(View.VISIBLE);
            main_tab_1_count.setText(count + "");
        }
        count = MessageService.getMessageCount(activity, MessageService.BJ);
        if (count == 0) {
            main_tab_2_count.setVisibility(View.INVISIBLE);
        } else {
            main_tab_2_count.setVisibility(View.VISIBLE);
            main_tab_2_count.setText(count + "");
        }
    }


    public void initView() {
        fragments.add(new PersonalCenterFragment());//个人中心
        fragments.add(new HealthManagerFragment());//健康管理
        fragments.add(new FragmentHealthTeam().setMain(this));//健康团队
        fragments.add(new FragmentConsultation().setMain(this));//所有咨询
        fragments.add(new FragmentFollow().setMain(this));//随访
        fragments.add(new FragmentLineInspection().setMain(this));//线下检查
        fragments.add(new FragmentWearableDevice().setMain(this));//可穿戴设备
//        fragments.add(new HealthDatabaseActivity(this));//健康数据库
    }

    @Override
    protected void onInitLayoutAfter() {
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
                        if(!isUpdate){
                            getPay();
                        }
                    }
                });
            }

            fm = getSupportFragmentManager();
            UserService.loadUserInfo(application);
            MessageService.loadMessagesAll(activity);
            replaceView(0);
            mBeansReceiver = new BeansReceiver();
            registerReceiver(mBeansReceiver, new IntentFilter("com.uyi.beans"));
        }

    }
    public void getPay() {
        RequestManager.getObject(Constens.GET_SERVER_THREE_PAY, this, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println("_____________________"+jsonObject.toString());
                JSONArray array= new JSONArray();
                try {
                   array = jsonObject.getJSONArray("unpaidTopThreeServiceJsonList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(array.length() > 0){
                    if(conform == null){
                        conform = new MessageConform(Main.this, MessageConform.MessageType.CONFORM);
                    }
                    conform.setTitle("提示").setContent("您有需要付款的三甲服务包，是否马上去付款?").setOnMessageClick(Main.this);
                    conform.show()   ;
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

    protected void pay() {


    }


    /**
     * 菜单、返回键响应
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (currentPage == 1) {
//                exitBy2Click();
//            } else {
//                onClickTab(main_tab_1);
//            }
//        }
//        return false;
//    }

    /**
     * 双击退出函数
     */
//    private static Boolean isExit = false;
//
//    private void exitBy2Click() {
//        Timer tExit = null;
//        if (isExit == false) {
//            isExit = true; // 准备退出
//            T.showLong(this, "再按一次退出程序");
//            tExit = new Timer();
//            tExit.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isExit = false; // 取消退出
//                }
//            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//        } else {
//            finish();
//            System.exit(0);
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.BACK_LOGIN && resultCode == RESULT_OK) {
            onInitLayoutAfter();
        }
    }

    @Override
    protected void onDestroy() {
        if(mBeansReceiver!=null)
        unregisterReceiver(mBeansReceiver);
        super.onDestroy();
    }

    private BeansReceiver mBeansReceiver;

    @Override
    public void onClick(MessageConform.Result result, Object object) {
        if(result == MessageConform.Result.OK){
            startActivity(new Intent(Main.this, FollowUpPayActivity.class));
        }
    }


    private class BeansReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            requestUpdateInfo();
        }
    }

    public void requestUpdateInfo() {
        RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    data.getString("beans");
                    UserInfo loginUserInfo = UserInfoManager.getLoginUserInfo(Main.this);

                    loginUserInfo.beans = Integer.parseInt(data.getString("beans"));
                    UserInfoManager.setLoginUserInfo(Main.this, loginUserInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
