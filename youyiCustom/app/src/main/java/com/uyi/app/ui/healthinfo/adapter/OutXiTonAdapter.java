package com.uyi.app.ui.healthinfo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.ui.healthinfo.model.OutXiTon;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.util.List;

/**
 * Created by Leeii on 2016/7/18.
 */
public class OutXiTonAdapter extends BaseAdapter {
    List<OutXiTon> datas;

    public OutXiTonAdapter(Context context, List<OutXiTon> datas, OnDeleteListener onDeleteListener) {
        super(context, R.layout.layout_out_item);
        this.datas = datas;
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    protected int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView tv_date = holder.holder(R.id.tv_date, TextView.class);
        TextView content = holder.holder(R.id.content, TextView.class);
        OutXiTon bean = datas.get(position);
        tv_date.setText(bean.treatmentTime);
        content.setText(bean.content);
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        final ViewHolder holder = new ViewHolder(itemView);
        holder.holder(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null)
                    onDeleteListener.onDelete(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public OnDeleteListener onDeleteListener;

    public interface OnDeleteListener {
        void onDelete(int position);
    }
}
