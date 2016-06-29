package com.uyi.app.ui.common;

import com.uyi.app.ui.custom.ExtendedViewPager;
import com.uyi.app.ui.custom.TouchImageView;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 图片 查看 自由缩放
 * @author user
 *
 * 传入数组 imageUrl
 */
public class ViewPagerImageActivity extends Activity {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
        setContentView(R.layout.view_pager_image);
        imageUrl = getIntent().getStringArrayExtra("imageUrl");
        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter());
    }
	
	private static String[] imageUrl = {};

    static class TouchImageAdapter extends PagerAdapter {


        @Override
        public int getCount() {
        	return imageUrl.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
//            img.setImageResource(imageUrl[position]);
            ImageCacheManager.loadImage(imageUrl[position], ImageCacheManager.getImageListener(img,null, null));
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}