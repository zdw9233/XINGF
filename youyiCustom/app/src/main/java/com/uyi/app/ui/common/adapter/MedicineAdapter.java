package com.uyi.app.ui.common.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uyi.app.ui.common.model.Health;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.util.List;
import java.util.Locale;

/**
 * 用药Adapter Created by Leeii on 2016/7/18.
 */
public class MedicineAdapter extends BaseAdapter {

    public List<Health.HealthInfoBean.MedicationUsingSituationsBean> datas;

    public MedicineAdapter(Context context, List<Health.HealthInfoBean.MedicationUsingSituationsBean> medicationUsingSituationsList, OnItemViewListener onItemViewListener) {
        super(context, R.layout.layout_yw_item);
        this.datas = medicationUsingSituationsList;
        this.onItemViewListener = onItemViewListener;
    }

    @Override
    protected int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView tv_start_time = holder.holder(R.id.tv_start_time, TextView.class);
        TextView tv_end_time = holder.holder(R.id.tv_end_time, TextView.class);
        TextView tv_ywm = holder.holder(R.id.tv_ywm, TextView.class);
        TextView yypd = holder.holder(R.id.yypd, TextView.class);
        Button xzrq = holder.holder(R.id.xzrq, Button.class);
        Health.HealthInfoBean.MedicationUsingSituationsBean bean = datas.get(position);
        tv_start_time.setText(String.format(Locale.CHINA, "开始服药日期：%s", bean.startTime));
        tv_end_time.setText(String.format(Locale.CHINA, "结束服药日期：%s", bean.endTime==null?"":bean.endTime));
        tv_ywm.setText(String.format(Locale.CHINA, "药物名：%s", bean.medicineName));
        yypd.setText(String.format(Locale.CHINA, "%s%s服药%s%s", bean.usingFrequency, bean.frequencyUnit, bean.singleDose, bean.medicineUnit));

        if (TextUtils.isEmpty(bean.endTime)){
            xzrq.setVisibility(View.VISIBLE);
        }else xzrq.setVisibility(View.GONE);
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        final ViewHolder holder = new ViewHolder(itemView);
        final TextView tv_start_time = holder.holder(R.id.tv_start_time, TextView.class);
        holder.holder(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewListener != null)
                    onItemViewListener.onDelete(holder.getAdapterPosition());
            }
        });
        holder.holder(R.id.xzrq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = tv_start_time.getText().toString().trim();
                if (sDate.equals("开始服药日期")) sDate = null;
                if (onItemViewListener != null)
                    onItemViewListener.onChoiceEndTime(holder.getAdapterPosition(),sDate);
            }
        });
        return holder;
    }

    public void setOnItemViewListener(OnItemViewListener onItemViewListener) {
        this.onItemViewListener = onItemViewListener;
    }

    public OnItemViewListener onItemViewListener;

    public void notifyEndTimeChanged(int i, String date) {
        datas.get(i).endTime=date;
        notifyItemChanged(i);
    }

    public interface OnItemViewListener {
        void onDelete(int position);
        void onChoiceEndTime(int position, String sDate);
    }

}
