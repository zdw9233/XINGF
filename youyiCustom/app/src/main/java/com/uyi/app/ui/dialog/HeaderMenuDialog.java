package com.uyi.app.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.service.MessageService;
import com.uyi.app.ui.common.LoginActivity;
import com.uyi.app.ui.dialog.MessageConform.OnMessageClick;
import com.uyi.app.ui.dialog.MessageConform.Result;
import com.uyi.app.ui.newCalendar.CalendarRCActivity;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.custom.app.R;


/**
 * 点击头像出来的菜单
 *
 * @author user
 */
public class HeaderMenuDialog extends AbstrctDialog implements android.view.View.OnClickListener, OnMessageClick {

    Context context;
    private LinearLayout header_menu_dou;
    private TextView header_menu_dou_count;

    private LinearLayout header_menu_message;
    private TextView header_meun_message_count;
    private TextView header_meun_zx_count;
    private TextView header_meun_rc_count;

    private LinearLayout header_menu_zixun;
    private LinearLayout header_menu_richeng;
    private LinearLayout header_menu_exit;

    private MessageConform conform;

    public HeaderMenuDialog(Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_menu_dialog);
        header_menu_dou = $(R.id.header_menu_dou);
        header_menu_dou_count = $(R.id.header_menu_dou_count);
        header_menu_message = $(R.id.header_menu_message);
        header_meun_message_count = $(R.id.header_meun_message_count);
        header_meun_zx_count = $(R.id.header_meun_zx_count);
        header_meun_rc_count = $(R.id.header_meun_rc_count);
        header_menu_zixun = $(R.id.header_menu_zixun);
        header_menu_richeng = $(R.id.header_menu_richeng);
        header_menu_exit = $(R.id.header_menu_exit);
        header_menu_exit.setOnClickListener(this);
        header_menu_zixun.setOnClickListener(this);
        header_menu_richeng.setOnClickListener(this);
        header_menu_message.setOnClickListener(this);
    }

    public LinearLayout getHeader_menu_dou() {
        return header_menu_dou;
    }

    public TextView getHeader_menu_dou_count() {
        return header_menu_dou_count;
    }

    public LinearLayout getHeader_menu_message() {
        return header_menu_message;
    }

    public TextView getHeader_meun_message_count() {
        return header_meun_message_count;
    }


    public LinearLayout getHeader_menu_zixun() {
        return header_menu_zixun;
    }


    public LinearLayout getHeader_menu_richeng() {
        return header_menu_richeng;
    }


    public LinearLayout getHeader_menu_exit() {
        return header_menu_exit;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.header_menu_message) {
            getContext().startActivity(new Intent(getContext(), MessageActivity.class));

        } else if (v.getId() == R.id.header_menu_zixun) {
//			Intent intent = new Intent(getContext(), HealthyQuestionsActivity.class);
//			getContext().startActivity(intent);
        } else if (v.getId() == R.id.header_menu_richeng) {
            getContext().startActivity(new Intent(getContext(), CalendarRCActivity.class));
        } else if (v.getId() == R.id.header_menu_exit) {
//			if(conform == null){
//				conform = new MessageConform(getContext(), MessageType.CONFORM);
//			}
//			conform.setTitle("提示").setContent("确认要退出吗?").setOnMessageClick(this);
//			conform.show();
            new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("确认要退出吗?").setPositiveButton("确定", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserInfoManager.clearLoginUserInfo(getContext());
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }).setNegativeButton("取消", null).show();
        }
        dismiss();
    }



    @Override
    public void onClick(Result result, Object object) {
        if (result == Result.OK) {
            getContext().startActivity(new Intent(getContext(), LoginActivity.class));
            UserInfoManager.clearLoginUserInfo(getContext());
        }
    }


    @Override
    public void show() {
        super.show();
//		MessageService.loadMessagesAll(context);
        int count = MessageService.getMessageCount(getContext(), MessageService.IM);
        if (count == 0) {
            header_meun_message_count.setVisibility(View.INVISIBLE);
        } else {
            header_meun_message_count.setVisibility(View.VISIBLE);
            header_meun_message_count.setText(count + "");
        }
        count = MessageService.getMessageCount(getContext(), MessageService.ZX);
        if (count == 0) {
            header_meun_zx_count.setVisibility(View.INVISIBLE);
        } else {
            header_meun_zx_count.setVisibility(View.VISIBLE);
            header_meun_zx_count.setText(count + "");
        }
        count = MessageService.getMessageCount(getContext(), MessageService.RC);
        if (count == 0) {
            header_meun_rc_count.setVisibility(View.INVISIBLE);
        } else {
            header_meun_rc_count.setVisibility(View.VISIBLE);
            header_meun_rc_count.setText(count + "");
        }
        UserInfo info = UserInfoManager.getLoginUserInfo(getContext());
        if (info != null && info.beans > 0) {
            header_menu_dou_count.setText(info.beans + "");
        }
    }
}
