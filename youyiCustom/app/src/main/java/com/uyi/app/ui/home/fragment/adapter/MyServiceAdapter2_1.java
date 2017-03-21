package com.uyi.app.ui.home.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.home.fragment.model.MyServerce;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.util.List;


public class MyServiceAdapter2_1 extends BaseAdapter {
    private final List<MyServerce> mReportItems;
    Context context;

    public MyServiceAdapter2_1(Context context, List<MyServerce> mReportItems) {
        super(context, R.layout.item_myservece);
        this.mReportItems = mReportItems;
        this.context = context;
    }

    @Override
    protected int getCount() {
        return mReportItems == null ? 0 : mReportItems.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView time_year = holder.holder(R.id.time_year, TextView.class);
        EndlessRecyclerView recyclerView = holder.holder(R.id.recyclerView, EndlessRecyclerView.class);
        ;
        MyServerce reportItem = mReportItems.get(position);
        time_year.setText(reportItem.getYear());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        MyServiceMonthAdapter2_1 myServiceMonthAdapter2_1 = new MyServiceMonthAdapter2_1(context, reportItem.getServerceMonths());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myServiceMonthAdapter2_1);
        myServiceMonthAdapter2_1.notifyDataSetChanged();
    }

    @Override
    public ViewHolder newHolder(final View itemView) {
        final ViewHolder viewHolder = new ViewHolder(itemView,R.color.gra_bg);
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
