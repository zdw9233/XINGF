package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;

import java.util.Map;


/**
 * 我的团队
 * @author user
 *
 */
public class HealthManagerAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	Context context;
	
	public HealthManagerAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_health_manager, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_health_name.setText(data.get("name").toString());
//			hodler.item_health_time.setText(data.get("updateTime").toString().subSequence(0, 10));
//			"diagnosis": "--:--",
//					"life": "--:--",
//					"assessment": "--:--",
//					"diet": "--:--",
//					"alarm": "--:--",
//					"telephone": "--:--",
//					"isDiagnosis": true,
//					"isAssessment": true,
//					"isLife": true,
//					"isDiet": true,
//					"isAlarm": true,
//					"isTelephone": true
			hodler.item_user_time1.setText(data.get("diagnosis").toString());
			hodler.item_user_time2.setText(data.get("life").toString());
			hodler.item_user_time3.setText(data.get("assessment").toString());
			hodler.item_user_time4.setText(data.get("diet").toString());
			hodler.item_user_time5.setText(data.get("alarm").toString());
			hodler.item_user_time6.setText(data.get("telephone").toString());

			if(data.get("isDiagnosis").toString().equals("false")){
				hodler.layout1.setVisibility(View.GONE);
			}
			if(data.get("isAssessment").toString().equals("false")){
				hodler.layout2.setVisibility(View.GONE);
			}
			if(data.get("isLife").toString().equals("false")){
				hodler.layout3.setVisibility(View.GONE);
			}
			if(data.get("isDiet").toString().equals("false")){
				hodler.layout4.setVisibility(View.GONE);
			}
			if(data.get("isAlarm").toString().equals("false")){
				hodler.layout5.setVisibility(View.GONE);
			}
			if(data.get("isTelephone").toString().equals("false")){
				hodler.layout6.setVisibility(View.GONE);
			}
ImageCacheManager.loadImage(data.get("icon").toString(), ImageCacheManager.getImageListener(hodler.item_health_header, null, null));
		}
	}
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(HealthManagerAdapter base,View v) {
        	base.super(v);
        	item_health_header = (RoundedImageView) v.findViewById(R.id.item_health_header);
        	item_health_name = (TextView) v.findViewById(R.id.item_health_name);
        	item_health_time = (TextView) v.findViewById(R.id.item_health_time);
			item_user_time1 = (TextView) v.findViewById(R.id.item_user_time1);
			item_user_time2 = (TextView) v.findViewById(R.id.item_user_time2);
			item_user_time3 = (TextView) v.findViewById(R.id.item_user_time3);
			item_user_time4 = (TextView) v.findViewById(R.id.item_user_time4);
			item_user_time5 = (TextView) v.findViewById(R.id.item_user_time5);
			item_user_time6 = (TextView) v.findViewById(R.id.item_user_time6);
			layout1 = (LinearLayout) v.findViewById(R.id.item_user_layout1);
			layout2 = (LinearLayout) v.findViewById(R.id.item_user_layout2);
			layout3 = (LinearLayout) v.findViewById(R.id.item_user_layout3);
			layout4 = (LinearLayout) v.findViewById(R.id.item_user_layout4);
			layout5 = (LinearLayout) v.findViewById(R.id.item_user_layout5);
			layout6 = (LinearLayout) v.findViewById(R.id.item_user_layout6);

		}
		private RoundedImageView    item_health_header;
        private TextView item_health_name;
        private TextView item_health_time;
		private TextView item_user_time1;
		private TextView item_user_time2;
		private TextView item_user_time3;
		private TextView item_user_time4;
		private TextView item_user_time5;
		private TextView item_user_time6;
		private LinearLayout layout1;
		private LinearLayout layout2;
		private LinearLayout layout3;
		private LinearLayout layout4;
		private LinearLayout layout5;
		private LinearLayout layout6;

    
    }

}
