package com.uyi.app.ui.newCalendar;

/**
 * Created by ThinkPad on 2016/10/26.
 */
class NumberHelper {
    public static String LeftPad_Tow_Zero(int str) {
        java.text.DecimalFormat format = new java.text.DecimalFormat("00");
        return format.format(str);
    }
}
