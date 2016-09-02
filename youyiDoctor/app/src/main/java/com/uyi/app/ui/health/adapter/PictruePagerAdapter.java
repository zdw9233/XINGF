package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.utils.ImageUtil;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;

import java.util.List;

/**
 * PersonalPagerAdapter  Created by Leeii on 2016/6/19.
 */
public class PictruePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<T> list;
    private List<String> pagerData;

    public PictruePagerAdapter(Context mContext, List<String> pagerData) {
        this.mContext = mContext;
        this.pagerData = pagerData;
    }

    @Override
    public int getCount() {
        return pagerData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pictrue_pager, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.img);
        TextView content = (TextView) view.findViewById(R.id.content);
        title.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        ImageUtil.load( pagerData.get(position), img);
        final String finalUrl = pagerData.get(position);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick(finalUrl);
            }
        });
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


    private void onImageClick(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(mContext, ViewPagerImageActivity.class);
            intent.putExtra("imageUrl", new String[]{url});
            mContext.startActivity(intent);
        }
    }

}
