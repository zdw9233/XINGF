package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.health.NewFollowUpDetailsActivity;
import com.uyi.doctor.app.R;

import java.util.ArrayList;
import java.util.Map;


/**
 *
 * @author user
 *
 */
public class FollowUpAddAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	Context context;

	public FollowUpAddAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_follow_up_updata_list, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, final int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			final Holder hodler = (Holder)viewHolder;
			hodler.time.setTextColor(context.getResources().getColor(R.color.blue));
			hodler.week.setTextColor(context.getResources().getColor(R.color.blue));
			hodler.time.setText(data.get("time").toString());
			hodler.week.setText(data.get("week").toString());
			hodler.caozuolayout.setVisibility(View.GONE);
			hodler.yanqiashanchu.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					((NewFollowUpDetailsActivity)context).delete(RealPosition,data);
				}
			});
			hodler.xiugai.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((NewFollowUpDetailsActivity)context).update(RealPosition,data);
				}
			});
			ArrayList<Map<String, Object>> datas1 = new ArrayList<Map<String, Object>>();
			datas1 = (ArrayList<Map<String, Object>>) data.get("itemListJsons");
			String str = "";
			for (int j = 0; j < datas1.size(); j++) {
				str += datas1.get(j).get("name").toString()+"，";
			}
			hodler.eventName.setText(str);

		}
	}
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(FollowUpAddAdapter base, View v) {
        	base.super(v);
			time = (TextView) v.findViewById(R.id.time);
			week = (TextView) v.findViewById(R.id.week);
			updateType = (ImageView) v.findViewById(R.id.updateType);
			eventName = (TextView) v.findViewById(R.id.eventName);
			status = (ImageView) v.findViewById(R.id.status);
			caozuolayout = (LinearLayout) v.findViewById(R.id.caozuolayout);
			eventName.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(caozuolayout.getVisibility() == View.GONE){
						caozuolayout.setVisibility(View.VISIBLE);
					}else{
						caozuolayout.setVisibility(View.GONE);
					}
				}
			});
			yanqiashanchu = (TextView) v.findViewById(R.id.yanqiashanchu);
			yanqiashanchu.setText("删除");
			xiugai = (TextView) v.findViewById(R.id.xiugai);
		}
        private TextView time;
        private TextView week;
        private ImageView updateType;
        private TextView eventName;
		private ImageView status;
		private LinearLayout caozuolayout;
		private TextView yanqiashanchu;
		private TextView xiugai;
    }

}
