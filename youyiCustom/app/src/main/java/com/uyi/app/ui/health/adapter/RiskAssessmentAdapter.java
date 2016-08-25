package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONObject;

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
		showDetailPositions.add(0);

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
			if (showDetailPositions.contains(RealPosition)){
				hodler.deils.setVisibility(View.VISIBLE);
				hodler.riskitem.setVisibility(View.VISIBLE);
			}else {
				hodler.deils.setVisibility(View.GONE);
				hodler.riskitem.setVisibility(View.GONE);
			}
			hodler.item.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (data.get("checked").toString().equals("false")){
						try {
							JSONObject params = new JSONObject();

							RequestManager.postObject(String.format(Constens.CUSTOMER_HEALTH_RISK_UPDATA,data.get("id").toString()), context,params, new Response.Listener<JSONObject>() {
								public void onResponse(JSONObject jsonData) {
	//								hodler.checked.setVisibility(View.GONE);
									data.put("checked","true");
									notifyItemChanged(RealPosition);
								}
							}, new RequestErrorListener() {
								public void requestError(VolleyError e) {
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (showDetailPositions.contains(RealPosition)){
						showDetailPositions.remove(showDetailPositions.indexOf(RealPosition));
					}else showDetailPositions.add(RealPosition);
					notifyItemChanged(RealPosition);
				}
			});
//			hodler.title.setText(data.get("content").toString());
			hodler.time.setText(data.get("createTime").toString());
			hodler.deils.setText(data.get("content").toString());
			hodler.riskindix.setText("风险系数:  "+data.get("percentage").toString());
			hodler.doc_name.setText("填写医生:  "+data.get("doc_name").toString());
			if(data.get("checked").toString().equals("false") ){
				hodler.checked.setVisibility(View.VISIBLE);
			}else hodler.checked.setVisibility(View.GONE);
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
			checked = (TextView) v.findViewById(R.id.checked);
			riskindix = (TextView) v.findViewById(R.id.risk_index);
			doc_name = (TextView) v.findViewById(R.id.doc_name);

		}
		private TextView title;
		private TextView time;
		private TextView deils;
		private TextView riskindix;
		private TextView doc_name;
		private TextView checked;
		private RelativeLayout item;
		private LinearLayout riskitem;
	}

}
