package com.uyi.app.ui.report.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.recycle.BaseAdapter;
import com.uyi.app.ui.report.model.Report;
import com.uyi.doctor.app.R;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * 常规检测 Created by Leeii on 2016/6/23.
 */
public class RoutineAdapter extends BaseAdapter {
    private Report report;

    private static final String[] GET_METHOD = {
            "getBmi",
            "getBloodFatChol",
            "getBloodFatTg",
            "getBloodFatLdl",
            "getBloodFatHdl",
            "getSpo",
            "getUrineAcid"
    };

    private static final String[] TITLE = {
            "BMI",
            "总胆固醇",
            "甘油三酯",
            "低密度脂蛋白胆固醇",
            "高密度脂蛋白胆固醇",
            "血氧饱和度",
            "尿酸"
    };
    private static final String[] TIPS = {
            "",
            "（参考范围2.9～5.8mmol/L）",
            "（参考范围0.23～1.70mmol/L）",
            "（参考范围0.5～3.1mmol/L）",
            "（参考范围0.98～2mmol/L）",
            "（参考范围94～100%）",
            "（参考范围150～420umol/L）"
    };
    private static final String[] UNIT = {
            "",
            "mmol/L",
            "mmol/L",
            "mmol/L",
            "mmol/L",
            "%",
            "umol/L"
    };
    private int[] index = new int[7];

    public RoutineAdapter(Context context, Report report) {
        super(context, R.layout.item_routine);
        this.report = report;
        initIndex();
    }

    private void initIndex() {
        index[0] = report.getBmi() == null ? 0 : report.getBmi().size();
        index[1] = report.getBloodFatChol() == null ? index[0] : index[0] + report.getBloodFatChol().size();
        index[2] = report.getBloodFatTg() == null ? index[1] : index[1] + report.getBloodFatTg().size();
        index[3] = report.getBloodFatLdl() == null ? index[2] : index[2] + report.getBloodFatLdl().size();
        index[4] = report.getBloodFatHdl() == null ? index[3] : index[3] + report.getBloodFatHdl().size();
        index[5] = report.getSpo() == null ? index[4] : index[4] + report.getSpo().size();
        index[6] = report.getUrineAcid() == null ? index[5] : index[5] + report.getUrineAcid().size();
    }

    @Override
    protected int getCount() {
        return index[6];     //尿酸
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        View itemHeaderView = holder.holder(R.id.item_header);
        TextView tvTitleName = holder.holder(R.id.tv_item_name, TextView.class);
        TextView tip = holder.holder(R.id.tv_item_tip, TextView.class);
        TextView item_name = holder.holder(R.id.item_name, TextView.class);
        TextView tv_date = holder.holder(R.id.tv_date, TextView.class);
        TextView value = holder.holder(R.id.tv_values, TextView.class);
        View last_footer = holder.holder(R.id.last_footer, View.class);

        Report.TimeValue timeValue = getTimeValue(position);

        int type = getTypeIndex(position);

        int itemType = isFirstOrLast(position);
        if (itemType == -1) { //最后一个
            itemHeaderView.setVisibility(View.GONE);
            last_footer.setVisibility(View.VISIBLE);
            tv_date.setText(timeValue.getUptime());
            value.setText(timeValue.getItemValue().concat(UNIT[type]));
        } else if (itemType == 1) { // 第一个
            itemHeaderView.setVisibility(View.VISIBLE);
            last_footer.setVisibility(View.GONE);
            tvTitleName.setText(TITLE[type]);
            tip.setText(TIPS[type]);
            item_name.setText(TITLE[type]);
            tv_date.setText(timeValue.getUptime());
            value.setText(timeValue.getItemValue().concat(UNIT[type]));
        } else if (itemType == 2) {        //既是第一个也是最后一个
            itemHeaderView.setVisibility(View.VISIBLE);
            last_footer.setVisibility(View.VISIBLE);
            tvTitleName.setText(TITLE[type]);
            tip.setText(TIPS[type]);
            item_name.setText(TITLE[type]);
            tv_date.setText(timeValue.getUptime());
            value.setText(timeValue.getItemValue().concat(UNIT[type]));
        } else {  //普通
            itemHeaderView.setVisibility(View.GONE);
            last_footer.setVisibility(View.GONE);
            tv_date.setText(timeValue.getUptime());
            value.setText(timeValue.getItemValue().concat(UNIT[type]));
        }
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        ViewHolder holder = new ViewHolder(itemView);
//        View write_report = holder.holder(R.id.write_report, View.class);
//        write_report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((ReportActivity) mContext).startActivityForResult(new Intent(mContext, WriteReportActivity.class), 0x100);
//            }
//        });
        return holder;
    }

    private int getTypeIndex(int position) {
        for (int i = 0; i < index.length; i++) {
            if (position < index[i]) return i;
        }
        return 0;
    }

    private Report.TimeValue getTimeValue(int position) {
        int type = getTypeIndex(position);
        int listPosition = type == 0 ? position : position - index[type - 1];
        try {
            List<Report.TimeValue> timeValue = (List<Report.TimeValue>) Report.class.getMethod(GET_METHOD[type]).invoke(report);
            return timeValue.get(listPosition);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int isFirstOrLast(int position) {
        if (position == 0) return 1;
        for (int i = 0; i < index.length; i++) {
            if (position == index[i] - 1 && i > 0 && position == index[i - 1]) return 2;
            if (position == index[i] - 1) return -1;   // 最后一个
            if (i > 0 && position == index[i - 1]) return 1; // 第一个
        }
        return 0;  //普通
    }
}
