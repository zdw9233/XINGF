package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.app.utils.ImageUtil;
import com.uyi.custom.app.R;

/**
 * HealthMainAdapter  Created by Leeii on 2016/6/18.
 */
public class HealthMainAdapter extends BaseAdapter {
    private String[] titleTextCN = new String[]{"健康报告", "健康数据库", "主诊报告", "风险评估", "生活方式", "饮食计划"};
    private String[] titleTextEN = new String[]{"report", "database", "diagnosis", "assessment", "life", "diet"};
    private String[] colorString = new String[]{"#FE9F21", "#7FCA3C", "#34B7FF", "#4DC7ED", "#8990FA", "#FE7FC2"};

    public HealthMainAdapter(Context context) {
        super(context, R.layout.item_new_main);
    }

    @Override
    protected int getCount() {
        return titleTextCN.length;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        SimpleDraweeView simpleDraweeView = holder.holder(R.id.icon, SimpleDraweeView.class);
        TextView titleCN = holder.holder(R.id.title_cn, TextView.class);
        TextView titleEN = holder.holder(R.id.title_en, TextView.class);
        View parent = holder.holder(R.id.parentView, View.class);
        ImageUtil.load("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=4032588057,3964571653&fm=58&s=F9E31B72CF8369535C9B03E80200702B", simpleDraweeView);
        titleCN.setText(titleTextCN[position]);
        titleEN.setText(titleTextEN[position]);
        parent.setBackgroundColor(Color.parseColor(colorString[position]));
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        return new ViewHolder(itemView);
    }
}
