package com.uyi.app.ui.personal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.home.fragment.PictrueToActivity;
import com.uyi.app.ui.personal.model.PagerData;
import com.uyi.app.utils.ImageUtil;
import com.uyi.app.utils.T;
import com.uyi.app.widget.InnerScrollView;
import com.uyi.custom.app.R;

import java.util.List;

/**
 * PersonalPagerAdapter  Created by Leeii on 2016/6/19.
 */
public class PersonalPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<T> list;
    private PagerData pagerData;

    public PersonalPagerAdapter(Context mContext, PagerData pagerData) {
        this.mContext = mContext;
        this.pagerData = pagerData;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_personal_pager, null);
        InnerScrollView innerScrollView = (InnerScrollView) view.findViewById(R.id.lay2);
        SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.img);
        TextView content = (TextView) view.findViewById(R.id.content);
        LinearLayout lay = (LinearLayout) view.findViewById(R.id.lay);
        String url = null;
        if (position == 0) {
            lay.setBackgroundResource(R.drawable.banner_2_1);
            lay.setEnabled(true);
            img.setVisibility(View.GONE);
            innerScrollView.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
        } else if (position == 1) {
            lay.setBackgroundResource(R.color.white);
            lay.setEnabled(false);
            if (pagerData == null) {
                content.setText("您最近还没有测试！");
            } else {
                content.setText(pagerData.getComment1());
                ImageUtil.load(url = pagerData.getBloodPressure_pic(), img);
            }
        } else if (position == 2) {
            lay.setBackgroundResource(R.color.white);
            lay.setEnabled(false);
            if (pagerData == null) {
                content.setText("您最近还没有测试！");
            } else {
                content.setText(pagerData.getComment2());
                ImageUtil.load(url = pagerData.getBloodSugar_pic(), img);
            }
        }
        final String datacontent = content.getText().toString();
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oncontextClick(datacontent);
            }
        });
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlayClick();
            }
        });
        final String finalUrl = url;
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

    public void setPagerData(PagerData pagerData) {
        this.pagerData = pagerData;
    }

    private void onImageClick(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(mContext, ViewPagerImageActivity.class);
            intent.putExtra("imageUrl", new String[]{url});
            mContext.startActivity(intent);
        }
    }
    private void onlayClick() {
        Intent intent = new Intent(mContext, PictrueToActivity.class);
        mContext.startActivity(intent);
        }
    private void oncontextClick(String context) {
        new AlertDialog.Builder(mContext).setTitle("检测详情").setMessage(context).setPositiveButton("确定", null).show();
    }

}
