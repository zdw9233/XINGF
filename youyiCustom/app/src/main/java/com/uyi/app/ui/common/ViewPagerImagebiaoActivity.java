package com.uyi.app.ui.common;

import com.uyi.app.ui.custom.ExtendedViewPager;
import com.uyi.app.ui.custom.TouchImageView;
import com.uyi.custom.app.R;

import com.volley.ImageCacheManager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 图片 查看 自由缩放
 * @author user
 *
 * 传入bitmap
 */
public class ViewPagerImagebiaoActivity extends Activity {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
        setContentView(R.layout.view_pager_image);
//        bitmap = getIntent().getParcelableExtra("imageUrl");
        byte buf[] = getIntent().getByteArrayExtra("photo_bmp");
        bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter());
        TextView textView = (TextView) findViewById(R.id.fanhui);
        textView.setVisibility(View.VISIBLE);
        textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				ViewPagerImagebiaoActivity.this.finish();
			}
		});
    }
	
	private static Bitmap bitmap;

    static class TouchImageAdapter extends PagerAdapter {


        @Override
        public int getCount() {
        	return 1;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
//            img.setImageResource(imageUrl[position]);
//            ImageCacheManager.loadImage(imageUrl[position], ImageCacheManager.getImageListener(img,null, null));
            img.setImageBitmap(bitmap);
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