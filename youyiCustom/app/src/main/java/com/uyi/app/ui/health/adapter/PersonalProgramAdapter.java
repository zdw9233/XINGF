package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
public class PersonalProgramAdapter extends BaseRecyclerAdapter<Map<String,Object>> {

	public Context context;
	private ArrayList<Integer> showDetailPositions;
	public PersonalProgramAdapter(Context context) {
		this.context = context;
		showDetailPositions = new ArrayList<>();
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_personal_program, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(final ViewHolder viewHolder, final int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			final Holder hodler = (Holder)viewHolder;
			hodler.time.setText(data.get("updateTime").toString().substring(0,10));
			hodler.crectdoctor.setText(data.get("doctorName").toString());
//			if(data.get("verifyStatus").toString().equals("PASS")){
//				hodler.zhuantai.setText("通过");
//			}else if(data.get("verifyStatus").toString().equals("NOPASS")){
//				hodler.zhuantai.setText("未通过");
//			}else if(data.get("verifyStatus").toString().equals("UNREVIEWED")){
//				hodler.zhuantai.setText("未审核");
//			}
		}
	}
	
	
	
	
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			riskitem = (LinearLayout) v.findViewById(R.id.risk_item);
			item = (RelativeLayout) v.findViewById(R.id.item);
			title = (TextView) v.findViewById(R.id.title);
			time = (TextView) v.findViewById(R.id.time);
			deils = (TextView) v.findViewById(R.id.deils);
			riskindix = (TextView) v.findViewById(R.id.risk_index);
			doc_name = (TextView) v.findViewById(R.id.doc_name);
			crectdoctor = (TextView) v.findViewById(R.id.crectdoctor);
			zhuantai = (TextView) v.findViewById(R.id.zhuantai);
//			fxxs = (TextView) v.findViewById(R.id.fxxs);
		}
        private TextView title;
        private TextView time;
		private TextView deils;
		private TextView riskindix;
		private RelativeLayout item;
		private LinearLayout riskitem;
		private TextView doc_name;
		private TextView crectdoctor;
		private TextView zhuantai;
		private TextView fxxs;
    }

}
