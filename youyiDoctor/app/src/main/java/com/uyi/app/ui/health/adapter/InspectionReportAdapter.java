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
public class InspectionReportAdapter extends BaseRecyclerAdapter<Map<String,Object>> {
	public Context context;
	public InspectionReportAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_inspection_report, parent, false);
    	return new Holder(this,view);

	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.title1.setText(data.get("uploadTime").toString());
			if(data.get("checkItem").toString().equals("NULL")){
				hodler.title2.setText("检查项目");
			}else{
				hodler.title2.setText(data.get("checkItem").toString());
			}
		}
	}
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			title1 = (TextView) v.findViewById(R.id.title1);
			title2 = (TextView) v.findViewById(R.id.title2);

		}
		private TextView    title1;
		private TextView    title2;
    }

}
