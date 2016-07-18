package com.uyi.app.ui.report.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.recycle.BaseAdapter;
import com.uyi.app.ui.report.model.ReportItem;
import com.uyi.doctor.app.R;

import java.util.List;


/**
 * ReportListAdapter  Created by Leeii on 2016/6/29.
 */
public class ReportListAdapter extends BaseAdapter {
    private final List<ReportItem> mReportItems;

    public ReportListAdapter(Context context, List<ReportItem> mReportItems) {
        super(context, R.layout.item_report_list);
        this.mReportItems = mReportItems;
    }

    @Override
    protected int getCount() {
        return mReportItems == null ? 0 : mReportItems.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
//        TextView title = holder.holder(R.id.title, TextView.class);
        TextView newView = holder.holder(R.id.new_word, TextView.class);
        TextView time = holder.holder(R.id.time, TextView.class);
        ReportItem reportItem = mReportItems.get(position);
        newView.setVisibility(reportItem.checked ? View.INVISIBLE : View.VISIBLE);
        time.setText(reportItem.createTime);
    }

    @Override
    public ViewHolder newHolder(final View itemView) {
        final ViewHolder viewHolder = new ViewHolder(itemView, R.drawable.sel_white_to_press);
//        viewHolder.holder(R.id.time).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callBack != null) callBack.onclick(viewHolder.getAdapterPosition());
//            }
//        });
        return viewHolder;
    }

    CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onclick(int position);
    }
}
