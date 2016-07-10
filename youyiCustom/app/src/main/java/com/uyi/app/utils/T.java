package com.uyi.app.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具类
 *
 * @author chao
 */
public abstract class T {
    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    private static Toast mToast;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, message, duration);
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, message, duration);
            mToast.show();
        }
    }
}
