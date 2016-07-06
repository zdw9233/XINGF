package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
	public void onBind(final ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			final Holder hodler = (Holder)viewHolder;
			hodler.item.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(hodler.deils.getVisibility() == View.GONE){
						hodler.deils.setVisibility(View.VISIBLE);
					}else{
						hodler.deils.setVisibility(View.GONE);
					}

				}
			});
			hodler.title.setText(data.get("content").toString());
		hodler.time.setText(data.get("createTime").toString());
		hodler.deils.setText(data.get("content").toString());


		}
	}
	
	
	
	
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			item = (RelativeLayout) v.findViewById(R.id.item);
			title = (TextView) v.findViewById(R.id.title);
			time = (TextView) v.findViewById(R.id.time);
			deils = (TextView) v.findViewById(R.id.deils);

		}
        private TextView title;
        private TextView time;
		private TextView deils;
		private RelativeLayout item;
    }

}
