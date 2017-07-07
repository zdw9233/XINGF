package com.uyi.xinf.ui.healthmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.doctor.xinf.R;
import com.uyi.xinf.adapter.BaseRecyclerAdapter;

import java.util.Map;



/**
 * 健康数据库列表
 * @author user
 *
 */
public class HealthManagerAdapter extends BaseRecyclerAdapter<Map<String,Object>> {

	public Context context;
	public HealthManagerAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_health_manager_list, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_health_list_text1.setText(data.get("realName").toString());
			hodler.item_health_list_text2.setText(data.get("age").toString());
			hodler.item_health_list_text3.setText(data.get("disease").toString().equals("null")?"":data.get("disease").toString());

		}
	}
	
	
	
	
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			item_health_list_text1 = (TextView) v.findViewById(R.id.item_health_list_text1);
			item_health_list_text2 = (TextView) v.findViewById(R.id.item_health_list_text2);
			item_health_list_text3 = (TextView) v.findViewById(R.id.item_health_list_text3);
			item_health_list_text4 = (TextView) v.findViewById(R.id.item_health_list_text4);
		}
        private TextView item_health_list_text1;
		private TextView item_health_list_text2;
		private TextView item_health_list_text3;
		private TextView item_health_list_text4;
    }

}
