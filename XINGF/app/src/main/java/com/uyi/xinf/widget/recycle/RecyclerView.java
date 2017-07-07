package com.uyi.xinf.widget.recycle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Recycler带距离顶端的高度 Created by Pirate on 2015/3/28.
 */
public class RecyclerView extends android.support.v7.widget.RecyclerView {
    
    /** 距离顶端高度的距离 */
    private int mContentTopClearance = 0;
    
    /**
     * RecyclerView的构造方法
     *
     * @param context
     *            上下文
     */
    public RecyclerView(Context context) {
        super(context);
    }
    
    /**
     * RecyclerView的构造方法
     * 
     * @param context
     *            上下文
     * @param attrs
     *            属性
     */
    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    /**
     * RecyclerView的构造方法
     * 
     * @param context
     *            上下文
     * @param attrs
     *            属性
     * @param defStyle
     *            样式
     */
    public RecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    /**
     * 设置距离顶端的高度
     *
     * @param clearance
     *            高度
     */
    public void setContentTopClearance(int clearance) {
        if (mContentTopClearance != clearance) {
            mContentTopClearance = clearance;
            setPadding(getPaddingLeft(),
                       mContentTopClearance,
                       getPaddingRight(),
                       getPaddingBottom());
        }
    }
    
    /**
     * 加载更多的接口
     */
    public interface LoadMoreListener {
        /**
         * 加载更多
         */
        void onLoadMore();
    }
    
    /**
     * 点击Item的时间监听
     */
    public interface ItemClickListener {
        /**
         * RecyclerView点击监听
         * 
         * @param v
         *            布局View
         * @param position
         *            索引角标
         */
        void onRecyclerItemClick(View v, int position);
    }
    
    /**
     * 长按点击Item的时间监听
     */
    public interface ItemLongClickListener {
        /**
         * 长按RecyclerView点击监听
         *
         * @param v
         *            布局View
         * @param position
         *            索引角标
         * @return 是否消耗
         */
        boolean onRecyclerItemLongClick(View v, int position);
    }
}
