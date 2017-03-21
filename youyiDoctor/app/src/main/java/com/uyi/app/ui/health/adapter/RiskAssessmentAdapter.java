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
import com.uyi.doctor.app.R;

import java.util.ArrayList;
import java.util.Map;


/**
 * 健康数据库列表
 * @author user
 *
 */
public class RiskAssessmentAdapter extends BaseRecyclerAdapter<Map<String,Object>> {

	public Context context;
	private ArrayList<Integer> showDetailPositions;
	public RiskAssessmentAdapter(Context context) {
		this.context = context;
		showDetailPositions = new ArrayList<>();
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_risk_assessment, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(final ViewHolder viewHolder, final int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			final Holder hodler = (Holder)viewHolder;
//			if (showDetailPositions.contains(RealPosition)){
//				hodler.deils.setVisibility(View.VISIBLE);
//				hodler.riskitem.setVisibility(View.VISIBLE);
//			}else {
//				hodler.deils.setVisibility(View.GONE);
//				hodler.riskitem.setVisibility(View.GONE);
//			}
//					hodler.item.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							if (showDetailPositions.contains(RealPosition)){
//								showDetailPositions.remove(showDetailPositions.indexOf(RealPosition));
//							}else{
//								showDetailPositions.add(RealPosition);
//							}
//							notifyItemChanged(RealPosition);
//						}
//					});

//			hodler.title.setText(data.get("content").toString());
			if(data.get("verifyStatus").toString().equals("PASS")){
				hodler.zhuangtai.setText("通过");
			}else if(data.get("verifyStatus").toString().equals("NOPASS")){
				hodler.zhuangtai.setText("未通过");
			}else if(data.get("verifyStatus").toString().equals("UNREVIEWED")){
				hodler.zhuangtai.setText("未审核");
			}
			hodler.time.setText(data.get("createTime").toString().substring(0,10));
//			hodler.fxxs.setText(data.get("percentage").toString());
			hodler.crectdoctor.setText(data.get("doc_name").toString());
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
			zhuangtai = (TextView) v.findViewById(R.id.zhuangtai);
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
		private TextView fxxs;
		private TextView zhuangtai;
    }

}
