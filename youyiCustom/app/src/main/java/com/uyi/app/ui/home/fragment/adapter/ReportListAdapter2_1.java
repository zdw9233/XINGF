package com.uyi.app.ui.home.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.ui.report.model.ReportItem;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.util.List;


/**
 * ReportListAdapter  Created by Leeii on 2016/6/29.
 */
public class ReportListAdapter2_1 extends BaseAdapter {
    private final List<ReportItem> mReportItems;

    public ReportListAdapter2_1(Context context, List<ReportItem> mReportItems) {
        super(context, R.layout.item_report_list_2_1);
        this.mReportItems = mReportItems;
    }

    @Override
    protected int getCount() {
        return mReportItems == null ? 0 : mReportItems.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView newView = holder.holder(R.id.checked, TextView.class);
        TextView title = holder.holder(R.id.title, TextView.class);
        ReportItem reportItem = mReportItems.get(position);
        newView.setVisibility(reportItem.checked ? View.GONE : View.VISIBLE);
        title.setText(reportItem.createTime.substring(0,10).replace("-",".")+"年度健康报告");
    }

    @Override
    public ViewHolder newHolder(final View itemView) {
        final ViewHolder viewHolder = new ViewHolder(itemView, R.drawable.sel_white_to_press);
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
