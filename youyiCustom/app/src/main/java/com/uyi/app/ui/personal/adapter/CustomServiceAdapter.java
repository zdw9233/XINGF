package com.uyi.app.ui.personal.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uyi.app.ui.personal.model.ServiceItem;
import com.uyi.app.utils.T;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.util.List;

/**
 * Created by Leeii on 2016/7/16.
 */
public class CustomServiceAdapter extends BaseAdapter {
    private List<ServiceItem> serviceItem;
    private OnBuyServiceCallBack mOnBuyServiceCallBack;

    public CustomServiceAdapter(Context context, List<ServiceItem> serviceItem) {
        super(context, R.layout.layout_custom_service_item);
        this.serviceItem = serviceItem;
    }

    @Override
    protected int getCount() {
        return serviceItem != null ? serviceItem.size() : 0;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        ImageView view = holder.holder(R.id.img, ImageView.class);
        view.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        TextView name = holder.holder(R.id.name, TextView.class);
        TextView money = holder.holder(R.id.money, TextView.class);

        ServiceItem serviceItem = this.serviceItem.get(position);
        name.setText(serviceItem.name);
        money.setText(String.valueOf(serviceItem.beans));
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        final ViewHolder holder = new ViewHolder(itemView, R.drawable.sel_white_to_press);
        final TextView number = holder.holder(R.id.number, TextView.class);
        holder.holder(R.id.sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(number.getText().toString());
                number.setText(String.valueOf(num == 1 ? 1 : num - 1));
            }
        });
        holder.holder(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(number.getText().toString());
                int max = serviceItem.get(holder.getAdapterPosition()).count;
                if (max == -1) number.setText(String.valueOf(num + 1));
                else if (num + 1 > max) {
                    T.showShort(mContext, "该项目只能最多购买" + max + "次");
                } else number.setText(String.valueOf(num + 1));
            }
        });
        holder.holder(R.id.buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                int count = Integer.valueOf(number.getText().toString());
                ServiceItem item = serviceItem.get(position);
                if (mOnBuyServiceCallBack != null)
                    mOnBuyServiceCallBack.buyIt(item.id, count, item.name, item.beans);
            }
        });
        return holder;
    }

    public void setOnBuyServiceCallBack(OnBuyServiceCallBack mOnBuyServiceCallBack) {
        this.mOnBuyServiceCallBack = mOnBuyServiceCallBack;
    }

    public interface OnBuyServiceCallBack {
        void buyIt(int id, int count, String name, int beans);
    }
}
