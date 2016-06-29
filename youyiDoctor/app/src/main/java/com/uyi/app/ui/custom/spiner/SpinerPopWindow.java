package com.uyi.app.ui.custom.spiner;

import java.util.List;

import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter.IOnItemSelectListener;
import com.uyi.doctor.app.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener{  
	  
    private Context mContext;  
    private ListView mListView;  
    private NormalSpinerAdapter mAdapter;  
    private IOnItemSelectListener mItemSelectListener;  
      
      
    public SpinerPopWindow(Context context)  
    {  
        super(context);  
          
        mContext = context;  
        init();  
    }  
      
      
    public void setItemListener(IOnItemSelectListener listener){  
        mItemSelectListener = listener;  
    }  
  
      
    private void init()  
    {  
    	View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);  
        setContentView(view);         
        setWidth(LayoutParams.WRAP_CONTENT);  
        setHeight(LayoutParams.WRAP_CONTENT);  
          
        setFocusable(true);  
        ColorDrawable dw = new ColorDrawable(0x00);  
        setBackgroundDrawable(dw);  
      
          
        mListView = (ListView) view.findViewById(R.id.spiner_listview);  
          
  
        mAdapter = new NormalSpinerAdapter(mContext);     
        mListView.setAdapter(mAdapter);   
        mListView.setOnItemClickListener(this);  
    }  
      
      
    public void refreshData(List<String> namelist,List<String> numberlist, int selIndex)  
    {  
        if (numberlist != null && selIndex  != -1)  
        {  
            mAdapter.refreshData(namelist,numberlist, selIndex);  
        }  
    }  
  
  
    @Override  
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {  
        dismiss();  
        if (mItemSelectListener != null){  
            mItemSelectListener.onItemClick(pos);
        }  
    }


	public void refreshData(List<String> phoneNameList, int selIndex) {
		if (phoneNameList != null && selIndex  != -1)  
        {  
            mAdapter.refreshData(phoneNameList, selIndex);  
        } 
	}

}  