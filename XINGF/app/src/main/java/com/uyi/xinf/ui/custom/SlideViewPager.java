package com.uyi.xinf.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.uyi.doctor.xinf.R;
import com.uyi.xinf.model.bean.ViewPagerBean;
import com.volley.ImageCacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 带自动滚动的幻灯片
 * @author chao
 *
 */
public class SlideViewPager extends LinearLayout {

	
	private ViewPager viewPager;	
	private LinearLayout viewGroup;	
	private ImageView[] imageViews; 
	private Context context;	
	
	//单击事件
	private OnItemClick onItemClick;
	
	private int time = 3000;//滚动时间
	
	private int pageCount = 0;
	private int currentPage = 0;
	
	private List<View> pageview ;
	private SlidePagerAdapter mPagerAdapter;
	public SlideViewPager(Context context) {
		this(context, null);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.viewpager, this);
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		viewGroup = (LinearLayout)findViewById(R.id.viewGroup);
	}
	
	
	public SlideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.viewpager, this);
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		viewGroup = (LinearLayout)findViewById(R.id.viewGroup);
	}
	
	public void setImages(List<ViewPagerBean> images){
		viewGroup.removeAllViews();
		pageview =new ArrayList<View>();
		for(final ViewPagerBean viewPagerBean : images){
			final ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			ImageCacheManager.loadImage(viewPagerBean.url, ImageCacheManager.getImageListener(imageView, null, null));
			imageView.setScaleType(ScaleType.CENTER_CROP);
			if(onItemClick != null){
				imageView.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						onItemClick.onItemClick(viewPagerBean, imageView);
					}
				});
			}
			pageview.add(imageView);
		}
        //有多少张图就有多少个点点
		pageCount = pageview.size();
        imageViews = new ImageView[pageview.size()];
        for(int i =0;i<pageview.size();i++){
        	ImageView  imageView = new ImageView(context);
            imageView.setLayoutParams(new LayoutParams(20,20));
            imageView.setPadding(20, 0, 20, 0); 
            imageViews[i] = imageView;   
            //默认第一张图显示为选中状态
            if (i == 0) {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);  
            }  
            viewGroup.addView(imageViews[i]);  
        }
		 //绑定适配器
        mPagerAdapter=new SlidePagerAdapter(pageview);
	    viewPager.setAdapter(mPagerAdapter);
	  //pageView监听器
        class GuidePageChangeListener implements OnPageChangeListener{

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            //如果切换了，就把当前的点点设置为选中背景，其他设置未选中背景
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                for(int i=0;i<imageViews.length;i++){
                    imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
                     if (arg0 != i) {  
                            imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);  
                        }  
                }
            }
            
        }
        
        //绑定监听事件
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	    
	    
	}
	
	/**
	 * 每一个View的单击事件
	 * @param onItemClick
	 */
	public void setOnItemClick(OnItemClick onItemClick){
		this.onItemClick = onItemClick;
	}
	
	public void setTime(int time){
		this.time = time;
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentPage);
		};
	};
	
	Timer timer;
	
	public void startSlide(){
		if(timer == null){
			timer = new Timer();
			timer.schedule(timerTask, time, time);
		}
	}
	
	
	
	TimerTask timerTask = new  TimerTask() {
		public void run() {
			currentPage++;
			if(currentPage > pageCount){
				currentPage = 0;
			}
			handler.sendEmptyMessage(1);
		}
	};
	
	class  SlidePagerAdapter extends PagerAdapter{
		private List<View> listViews=new ArrayList<View>();
		public SlidePagerAdapter(List<View> list) {
			listViews=list;
		}
		@Override
		public int getCount() {
			return listViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		  //是从ViewGroup中移出当前View
		@Override
        public void destroyItem(View arg0, int arg1, Object arg2) {  
               ((ViewPager) arg0).removeView(listViews.get(arg1));  
        }  
       //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
		@Override
		public Object instantiateItem(View arg0, int arg1){
           ((ViewPager)arg0).addView(listViews.get(arg1));
           return listViews.get(arg1);                
       }
	}
	/*   //数据适配器
    PagerAdapter mPagerAdapter = new PagerAdapter(){

        @Override
        //获取当前窗体界面数
        public int getCount() {
            // TODO Auto-generated method stub
            return pageview.size();
        }

        @Override
        //断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0==arg1;
        }
        //是从ViewGroup中移出当前View
         public void destroyItem(View arg0, int arg1, Object arg2) {  
                ((ViewPager) arg0).removeView(pageview.get(arg1));  
         }  
        
        //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1){
            ((ViewPager)arg0).addView(pageview.get(arg1));
            return pageview.get(arg1);                
        }
        
    };*/
    

   public interface OnItemClick{
	   public void onItemClick(ViewPagerBean viewPagerBean,View view);
   }

}
