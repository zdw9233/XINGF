package com.uyi.app.adapter;


import java.util.ArrayList;
import java.util.List;

import com.uyi.app.utils.DensityUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;


/**
 * RecylerView Adapter
 * @author user
 *
 */
public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.RecyclerViewHolder>{
	/**
	 * item
	 */
	private static final int IS_NORMAL = 1;
	/**
	 * header
	 */
	private static final int IS_HEADER = 2;
	/**
	 * footer
	 */
	private static final int IS_FOOTER = 3;
	    
	public List<String> lists = new ArrayList<String>();
	private View header;
	private View footer;
	private Context context;
	
    public RecylerViewAdapter(Context context,List<String> lists, View header, View footer) {
		super();
		this.context = context;
		this.lists = lists;
		this.header = header;
		this.footer = footer;
	}
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if(position==lists.size()+1){
           return IS_FOOTER;
        }else {
            return IS_NORMAL;
        }
    }
    
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public  int viewType;
        public RecyclerViewHolder(View v,int viewType) {
            super(v);
            this.viewType = viewType;
            if(viewType==IS_HEADER){
            }
            if(viewType==IS_FOOTER){
            }
            if(viewType==IS_NORMAL){
            	textView = (TextView) v.findViewById(android.R.id.text1);
            }
        }
        public TextView getTextView() {
            return textView;
        }
    }
    
    
	@Override
	public int getItemCount() {
		if(header != null && footer != null){
			return lists.size()+2;
		}
		if(header != null || footer != null){
			return lists.size()+1;
		}
		return lists.size(); 
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
		
		 //对不同的Item相应不同的操作
        if(position != 0 && position != lists.size()+1 && viewHolder.viewType == IS_NORMAL){
        	viewHolder.getTextView().setText(lists.get(position-1));
        	if(position %2 == 0){
        		
        		viewHolder.itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 30)));
        	}else{
        		viewHolder.itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 50)));
        		
        	}
        }
        if(position	== 0 &&	viewHolder.viewType	==	IS_HEADER){
            //header
        	viewHolder.itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 220)));
        }
        if(position	==	lists.size()+1	&&	viewHolder.viewType	==	IS_FOOTER){
            //footer
        	viewHolder.itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 120)));
        }
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		  //对不同的flag创建不同的Holder
	        if (viewType == IS_HEADER && header != null) {
	        	return new RecyclerViewHolder(header,IS_HEADER);
	        } else if (viewType == IS_FOOTER && footer != null) {
	        	return new RecyclerViewHolder(footer,IS_FOOTER);
	        }else{
	        	View view = LayoutInflater.from(viewGroup.getContext())  .inflate(android.R.layout.simple_list_item_1, viewGroup, false);
	        	return new RecyclerViewHolder(view,IS_NORMAL);
	        }
	}


}
