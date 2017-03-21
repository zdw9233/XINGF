package com.uyi.app.ui.home.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.custom.app.R;

import java.util.ArrayList;
import java.util.Map;


/**
 * 列表
 * @author user
 *
 */
public class QuaReportAdapter2_1 extends BaseRecyclerAdapter<Map<String,Object>> {

	public Context context;
	private ArrayList<Integer> showDetailPositions;
	public QuaReportAdapter2_1(Context context) {
		this.context = context;
		showDetailPositions = new ArrayList<>();
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_personal_program_2_1, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(final ViewHolder viewHolder, final int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			final Holder hodler = (Holder)viewHolder;
			hodler.title.setText(data.get("endTime").toString().substring(0,10).replace("-",".")+"季度健康报告");
			if(data.get("checked").toString().equals("false") ){
				hodler.checked.setVisibility(View.VISIBLE);
			}else hodler.checked.setVisibility(View.GONE);
		}
	}
	
	
	
	
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			title = (TextView) v.findViewById(R.id.title);
			checked = (TextView) v.findViewById(R.id.checked);
		}
		private TextView title;
		private TextView checked;
    }

}
