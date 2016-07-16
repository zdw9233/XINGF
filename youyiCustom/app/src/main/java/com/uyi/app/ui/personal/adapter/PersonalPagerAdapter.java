package com.uyi.app.ui.personal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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

    public PersonalPagerAdapter(Context mContext, List<T> list) {
        this.mContext = mContext;
        this.list = list;
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
                content.setText(pagerData.comment1);
                ImageUtil.load(pagerData.bloodPressure_pic, img);
            }
        } else {
            title.setText("血糖趋势图");
            if (pagerData == null) {
                content.setText("您最近还没有测试！");
            } else {
                content.setText(pagerData.comment1);
                ImageUtil.load(pagerData.bloodSugar_pic, img);
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

    public class PagerData {

        /**
         * bloodPressure_pic : http://121.42.142.228:8080/pics/business/images/business/2016/07/bfc30058-0edb-4e09-92ee-0dab79b54ee8.jpg
         * bloodSugar_pic : http://121.42.142.228:8080/pics/business/images/business/2016/07/aaf48243-ca88-4023-9e2d-271db75474ae.jpg
         * comment1 : 您最近30天共测量血压  4次，收缩压和舒张压均控制在比较理想的范围，根据您的年龄段（此处应加入不同年龄段血压控制标准）和我国居民高血压治疗指南的建议，请您继续保持目前的饮食及运动习惯，按时服药，每日监测血压并及时将数据上传到“优医”中。（点击蓝色字体可全文阅读相关内容）。
         */

        public String bloodPressure_pic;
        public String bloodSugar_pic;
        public String comment1;
    }
}
