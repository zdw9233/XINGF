package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.doctor.app.R;

import java.util.Map;


/**
 * 健康数据库列表
 * @author user
 *
 */
public class RiskAssessmentAdapter extends BaseRecyclerAdapter<Map<String,Object>> {

	public Context context;
	public RiskAssessmentAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_risk_assessment, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.title.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
//			hodler.item_health_list_content.setText(data.get("uploadItems").toString());
//			hodler.item_health_list_date.setText(data.get("uploadTime").toString());
//			if((Boolean)data.get("isWarning")){
//				hodler.item_health_list_image.setImageResource( R.drawable.warning);
//			}else{
//				hodler.item_health_list_image.setImageResource( R.drawable.success_icon);
//			}

		}
	}
	
	
	
	
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			title = (TextView) v.findViewById(R.id.title);
			time = (TextView) v.findViewById(R.id.time);
			deils = (TextView) v.findViewById(R.id.deils);
		}
        private TextView title;
        private TextView time;
		private TextView deils;
    }

}
