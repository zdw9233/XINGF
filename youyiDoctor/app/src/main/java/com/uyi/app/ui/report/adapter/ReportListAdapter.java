package com.uyi.app.ui.report.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.recycle.BaseAdapter;
import com.uyi.doctor.app.R;

/**
 * Created by Leeii on 2016/6/29.
 */
public class ReportListAdapter extends BaseAdapter {
    public ReportListAdapter(Context context) {
        super(context, R.layout.item_report_list);
    }

    @Override
    protected int getCount() {
        return 8;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView title = holder.holder(R.id.title, TextView.class);
        TextView newView = holder.holder(R.id.new_word, TextView.class);
        TextView time = holder.holder(R.id.time, TextView.class);
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        return new ViewHolder(itemView);
    }
}
