package com.uyi.app.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.uyi.app.Constens;
import com.uyi.app.utils.SPUtils;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 管理消息
 *
 * @author user
 */
public class MessageService {

    /**
     * 今日日程数
     */
    public final static int RC = 1;

    /**
     * 未读消息+公告数
     */
    public final static int IM = 2;

    /**
     * 咨询数
     */
    public final static int ZX = 3;

    /**
     * 随访数
     */
    public final static int SF = 4;

    /**
     * 线下检查数
     */
    public final static int OFF = 5;

    /**
     * 健康管理新报警数
     */
    public final static int BJ = 6;

    /**
     * 健康问答数
     */
    public final static int WD = 8;

    /**
     * KEY
     */
    public final static String MESSAGE_KEY_ = "MESSAGE_KEY_";

    /**
     * 加载单个消息数量
     *
     * @param code
     */
    public static void loadMessages(final Context context, final int code) {
        RequestManager.getObject(String.format(Constens.ACCOUNT_STATISTICS, code), context, null, new Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    int total = data.getInt("total");
                    SPUtils.put(context, MESSAGE_KEY_ + code, total);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("com.uyi.message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {
                
            }
        });
    }


    /**
     * 加载所有消息数量
     */
    public static void loadMessagesAll(Context context) {
        loadMessages(context, 1);
        loadMessages(context, 2);
        loadMessages(context, 3);
        loadMessages(context, 4);
        loadMessages(context, 5);
        loadMessages(context, 6);
        loadMessages(context, 8);
    }

    public static void clearMessage(Context context) {
        SPUtils.clear(context);
    }


    /**
     * 获取消息数量
     *
     * @param context
     * @param code
     * @return
     */
    public static int getMessageCount(Context context, int code) {
        if (!SPUtils.contains(context, MESSAGE_KEY_ + code)) {
            return 0;
        }
        return (int) SPUtils.get(context, MESSAGE_KEY_ + code, 0);
    }
}

