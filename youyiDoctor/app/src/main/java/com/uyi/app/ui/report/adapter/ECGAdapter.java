package com.uyi.app.ui.report.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uyi.app.recycle.BaseAdapter;
import com.uyi.app.ui.report.model.Report;
import com.uyi.app.utils.ImageUtil;
import com.uyi.doctor.app.R;

import java.util.List;

/**
 * Created by Leeii on 2016/7/10.
 */
public class ECGAdapter extends BaseAdapter {
    private List<Report.TimeValue> mdata;

    private String[] imageUrls;

    public ECGAdapter(Context context, List<Report.TimeValue> mdata) {
        super(context, R.layout.item_ecg);
        this.mdata = mdata;
    }

    @Override
    protected int getCount() {
        return mdata == null ? 0 : mdata.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView date = holder.holder(R.id.date, TextView.class);
        SimpleDraweeView simpleDraweeView = holder.holder(R.id.img, SimpleDraweeView.class);
        Report.TimeValue value = mdata.get(position);
        date.setText(value.getUptime());
        ImageUtil.load(value.getItemValue(), simpleDraweeView);

    }

    @Override
    public ViewHolder newHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public String[] getImageUrls() {
        if (imageUrls == null) {
            imageUrls = new String[getCount()];
            for (int i = 0; i < imageUrls.length; i++) {
                imageUrls[i] = mdata.get(i).getItemValue();
            }
        }
        return imageUrls;
    }
}
