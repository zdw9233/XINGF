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
	public void onBind(final ViewHolder viewHolder, int RealPosition,final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			final Holder hodler = (Holder)viewHolder;
			hodler.item.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						JSONObject params = new JSONObject();

						RequestManager.postObject(String.format(Constens.CUSTOMER_HEALTH_RISK_UPDATA,data.get("id").toString()), context,params, new Response.Listener<JSONObject>() {
							public void onResponse(JSONObject data) {
								System.out.print("+++++++++++++++++++++"+data.toString());
								hodler.checked.setVisibility(View.GONE);
							}
						}, new RequestErrorListener() {
							public void requestError(VolleyError e) {
//                    if(e.networkResponse != null){
//                        T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
//                    }else{
//                        T.showShort(activity, "提交成功!");
//                        onRefresh();
//                    }
//								T.showShort(context, "失败!");
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}

					if(hodler.deils.getVisibility() == View.GONE){
						hodler.deils.setVisibility(View.VISIBLE);
						hodler.riskitem.setVisibility(View.VISIBLE);

					}else{
						hodler.deils.setVisibility(View.GONE);
						hodler.riskitem.setVisibility(View.GONE);
					}

				}
			});
//			hodler.title.setText(data.get("content").toString());
			hodler.time.setText(data.get("createTime").toString());
			hodler.deils.setText(data.get("content").toString());
			hodler.riskindix.setText(data.get("percentage").toString());
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

		}
		private TextView title;
		private TextView time;
		private TextView deils;
		private TextView riskindix;
		private TextView checked;
		private RelativeLayout item;
		private LinearLayout riskitem;
	}

}
