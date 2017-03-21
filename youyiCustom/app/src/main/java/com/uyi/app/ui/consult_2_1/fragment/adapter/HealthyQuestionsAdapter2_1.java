package com.uyi.app.ui.consult_2_1.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;

import java.util.Map;


/**
 * 健康问答
 * @author user
 *
 */
public class HealthyQuestionsAdapter2_1 extends BaseRecyclerAdapter<Map<String, Object>> {

	private Context context;

	public HealthyQuestionsAdapter2_1(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_healthy_questions, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_healthy_questions_content.setText(data.get("content").toString());
			hodler.item_healthy_questions_date.setText(data.get("lastUpdateTime").toString());
			hodler.item_healthy_questions_name.setText(data.get("realName").toString());
			ImageCacheManager.loadImage(data.get("iconUrl").toString(), ImageCacheManager.getImageListener(hodler.item_consultation_logo, null, null));
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	item_healthy_questions_content = (TextView) v.findViewById(R.id.item_healthy_questions_content);
        	item_healthy_questions_date = (TextView) v.findViewById(R.id.item_healthy_questions_date);
			item_healthy_questions_name = (TextView) v.findViewById(R.id.item_healthy_questions_name);
			item_consultation_logo = (RoundedImageView) v.findViewById(R.id.item_consultation_logo);
		}
        private TextView item_healthy_questions_content;
        private TextView item_healthy_questions_date;
		private TextView item_healthy_questions_name;
		private RoundedImageView item_consultation_logo;
    }

}
