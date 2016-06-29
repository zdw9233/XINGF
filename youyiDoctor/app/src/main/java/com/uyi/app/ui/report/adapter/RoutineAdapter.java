package com.uyi.app.ui.report.adapter;

import android.content.Context;
import android.view.View;

import com.uyi.app.recycle.BaseAdapter;
import com.uyi.doctor.app.R;

/**
 * 常规检测 Created by Leeii on 2016/6/23.
 */
public class RoutineAdapter extends BaseAdapter {
    public RoutineAdapter(Context context) {
        super(context, R.layout.item_routine);
    }

    @Override
    protected int getCount() {
        return 2;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {

    }

    @Override
    public ViewHolder newHolder(View itemView) {
        return null;
    }
}
