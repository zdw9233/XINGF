package com.uyi.app.ui.personal.questions.adapter;

import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 健康问答详细
 * @author user
 *
 */
public class HealthyQuestionsDetailsAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	private Context context;
	
	public HealthyQuestionsDetailsAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_healthy_questions_detals, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_healthy_questions_detail_left_layout.setVisibility(View.GONE);
			hodler.item_healthy_questions_detail_right_layout.setVisibility(View.GONE);
			if((Boolean)data.get("isDoctor")){//医生
				
				hodler.item_healthy_questions_detail_right_layout.setVisibility(View.VISIBLE);
				hodler.item_healthy_questions_detail_right_name.setText(data.get("realName").toString());
				hodler.item_healthy_questions_detail_right_content.setText(data.get("content").toString());
				ImageCacheManager.loadImage(data.get("iconUrl").toString(), ImageCacheManager.getImageListener(hodler.item_healthy_questions_detail_right_header, null, null));
			}else{//病人
				hodler.item_healthy_questions_detail_left_layout.setVisibility(View.VISIBLE);
				hodler.item_healthy_questions_detail_left_name.setText(data.get("realName").toString());
				hodler.item_healthy_questions_detail_left_content.setText(data.get("content").toString());
				ImageCacheManager.loadImage(data.get("iconUrl").toString(), ImageCacheManager.getImageListener(hodler.item_healthy_questions_detail_left_header, null, null));
			}
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	item_healthy_questions_detail_left_layout = (LinearLayout) v.findViewById(R.id.item_healthy_questions_detail_left_layout);
        	item_healthy_questions_detail_left_header = (RoundedImageView) v.findViewById(R.id.item_healthy_questions_detail_left_header);
        	item_healthy_questions_detail_left_name = (TextView) v.findViewById(R.id.item_healthy_questions_detail_left_name);
        	item_healthy_questions_detail_left_content = (TextView) v.findViewById(R.id.item_healthy_questions_detail_left_content);
        	
        	item_healthy_questions_detail_right_layout = (LinearLayout) v.findViewById(R.id.item_healthy_questions_detail_right_layout);
        	item_healthy_questions_detail_right_header = (RoundedImageView) v.findViewById(R.id.item_healthy_questions_detail_right_header);
        	item_healthy_questions_detail_right_name = (TextView) v.findViewById(R.id.item_healthy_questions_detail_right_name);
        	item_healthy_questions_detail_right_content = (TextView) v.findViewById(R.id.item_healthy_questions_detail_right_content);
		}
        private LinearLayout item_healthy_questions_detail_left_layout;
        private RoundedImageView item_healthy_questions_detail_left_header;
        private TextView item_healthy_questions_detail_left_name;
        private TextView item_healthy_questions_detail_left_content;
        
        private LinearLayout item_healthy_questions_detail_right_layout;
        private RoundedImageView item_healthy_questions_detail_right_header;
        private TextView item_healthy_questions_detail_right_name;
        private TextView item_healthy_questions_detail_right_content;
    }

}
