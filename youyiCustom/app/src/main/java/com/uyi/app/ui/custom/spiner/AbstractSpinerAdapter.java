package com.uyi.app.ui.custom.spiner;

import java.util.ArrayList;
import java.util.List;

import com.uyi.custom.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class AbstractSpinerAdapter<T> extends BaseAdapter {

	public static interface IOnItemSelectListener{
		public void onItemClick(int pos);
	};
	
	 private Context mContext;   
	 private List<T> phoneNameList = new ArrayList<T>();
	 private List<T> phoneNumberList = new ArrayList<T>();
	 private int mSelectItem = 0;
	    
	 private LayoutInflater mInflater;
	
	 public  AbstractSpinerAdapter(Context context){
		 init(context);
	 }
	 
	 public void refreshData(List<T> nameList,List<T> numberList, int selIndex){
		 phoneNameList = nameList;
		 phoneNumberList = numberList;
		 if (selIndex < 0){
			 selIndex = 0;
		 }
		 if (selIndex >= numberList.size()){
			 selIndex = numberList.size() - 1;
		 }
		 
		 mSelectItem = selIndex;
	 }
	public void refreshData(List<T> nameList,int selIndex){
		 switch (selIndex) {
		case 0:
			 phoneNameList = nameList;
			break;
		case 1:
			
			break;
		default:
			break;
		}
		
	 }
	 
	 private void init(Context context) {
	        mContext = context;
	        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	    
	    
	@Override
	public int getCount() {
		return phoneNameList.size();
	}

	@Override
	public Object getItem(int pos) {
		return pos;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		 ViewHolder viewHolder;
    	 
	     if (convertView == null) {
	    	 convertView = mInflater.inflate(R.layout.spiner_item_layout, null);
	         viewHolder = new ViewHolder();
	         viewHolder.mTextView = (TextView) convertView.findViewById(R.id.spiner_item_textView);
	         convertView.setTag(viewHolder);
	     } else {
	         viewHolder = (ViewHolder) convertView.getTag();
	     }

	     if(phoneNumberList.size()>0){
		 viewHolder.mTextView.setText(phoneNameList.get(pos).toString()+"("+phoneNumberList.get(pos).toString()+")");
	     }else{
	    	 viewHolder.mTextView.setText(phoneNameList.get(pos).toString());
	     }
		 return convertView;
	}

	public static class ViewHolder
	{
	    public TextView mTextView;
    }


}
