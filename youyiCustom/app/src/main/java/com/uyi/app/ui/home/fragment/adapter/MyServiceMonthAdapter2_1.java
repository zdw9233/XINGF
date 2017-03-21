package com.uyi.app.ui.home.fragment.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.ui.home.fragment.model.MyServerce;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.util.List;


public class MyServiceMonthAdapter2_1 extends BaseAdapter {
    private final List<MyServerce.ServerceMonth> mReportItems;

    public MyServiceMonthAdapter2_1(Context context, List<MyServerce.ServerceMonth> mReportItems) {
        super(context, R.layout.item_myservece_month);
        this.mReportItems = mReportItems;
    }

    @Override
    protected int getCount() {
        return mReportItems == null ? 0 : mReportItems.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView time_month = holder.holder(R.id.time_month, TextView.class);
		TextView context = holder.holder(R.id.context, TextView.class);

		MyServerce.ServerceMonth reportItem = mReportItems.get(position);
		time_month.setText(Html.fromHtml(reportItem.getMonth()));
		context.setText(Html.fromHtml(reportItem.getData()));
    }

    @Override
    public ViewHolder newHolder(final View itemView) {
        final ViewHolder viewHolder = new ViewHolder(itemView, R.color.gra_bg);
        return viewHolder;
    }

    com.uyi.app.ui.report.adapter.ReportListAdapter.CallBack callBack;

    public void setCallBack(com.uyi.app.ui.report.adapter.ReportListAdapter.CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onclick(int position);
    }
}
