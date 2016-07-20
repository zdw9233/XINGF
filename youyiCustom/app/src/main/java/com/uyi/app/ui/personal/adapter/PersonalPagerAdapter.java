package com.uyi.app.ui.personal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uyi.app.ui.personal.model.PagerData;
import com.uyi.app.utils.ImageUtil;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;

import java.util.List;

/**
 * PersonalPagerAdapter  Created by Leeii on 2016/6/19.
 */
public class PersonalPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<T> list;
    private PagerData pagerData;

    public PersonalPagerAdapter(Context mContext,PagerData pagerData) {
        this.mContext = mContext;
        this.pagerData = pagerData;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_personal_pager, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.img);
        TextView content = (TextView) view.findViewById(R.id.content);

        if (position == 0) {
            title.setText("血压趋势图");
            if (pagerData == null) {
                content.setText("您最近还没有测试！");
            } else {
                content.setText(pagerData.getComment1());
                ImageUtil.load(pagerData.getBloodPressure_pic(), img);
            }
        } else {
            title.setText("血糖趋势图");
            if (pagerData == null) {
                content.setText("您最近还没有测试！");
            } else {
                content.setText(pagerData.getComment1());
                ImageUtil.load(pagerData.getBloodSugar_pic(), img);
            }
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setPagerData(PagerData pagerData) {
        this.pagerData = pagerData;
    }


}
