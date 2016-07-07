package com.uyi.app.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
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
import com.uyi.app.ui.health.FragmentHealthDatabase;
import com.uyi.app.ui.health.FragmentWearableDevice;
import com.uyi.app.ui.health.HealthManagerFragment;
import com.uyi.app.ui.personal.PersonalCenterFragment;
import com.uyi.app.ui.team.FragmentHealthTeam;
import com.uyi.app.utils.NetUtils;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.main)
public class Main extends BaseFragmentActivity {
    UpdateManager manager = new UpdateManager();

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
        fragments.add(new FragmentHealthTeam(this));//健康团队
        fragments.add(new FragmentConsultation(this));//所有咨询
        fragments.add(new FragmentFollow(this));//随访
        fragments.add(new FragmentHealthDatabase(this));//健康数据库
        fragments.add(new FragmentLineInspection(this));//线下检查
        fragments.add(new FragmentWearableDevice(this));//可穿戴设备
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
                    }
                });
            }

            UserService.loadUserInfo(application);
            MessageService.loadMessagesAll(activity);
            fm = getSupportFragmentManager();
            replaceView(0);
        }
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

}
