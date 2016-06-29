package com.uyi.app.ui.health.adapter;

import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.doctor.app.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 健康数据库列表
 * @author user
 *
 */
public class HealthDatabaseAdapter extends BaseRecyclerAdapter<Map<String,Object>>{

	public Context context;
	public HealthDatabaseAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_health_list, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_health_list_content.setText(data.get("uploadItems").toString());
			hodler.item_health_list_date.setText(data.get("uploadTime").toString());
			if((Boolean)data.get("isWarning")){
				hodler.item_health_list_image.setImageResource( R.drawable.warning);
			}else{
				hodler.item_health_list_image.setImageResource( R.drawable.success_icon);
			}
			
		}
	}
	
	
	
	
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	item_health_list_image = (ImageView) v.findViewById(R.id.item_health_list_image);
        	item_health_list_content = (TextView) v.findViewById(R.id.item_health_list_content);
        	item_health_list_date = (TextView) v.findViewById(R.id.item_health_list_date);
		}
		private ImageView    item_health_list_image;
        private TextView item_health_list_content;
        private TextView item_health_list_date;
    }

}
