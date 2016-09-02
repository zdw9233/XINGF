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
public class ElectrocarDiogramAdapter extends BaseRecyclerAdapter<Map<String,Object>> {
	public Context context;
	public ElectrocarDiogramAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_electrocar_diogran, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, final int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.title1.setText(data.get("uploadTime").toString());
//			hodler.button.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					ElectrocarDiogramFragment.gotoChooesTeam(RealPosition,data);
//				}
//			});
		}
	}
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			title1 = (TextView) v.findViewById(R.id.title1);
//			button = (TextView) v.findViewById(R.id.title1);

		}
		private TextView    title1;
//		private TextView    button;
    }

}
