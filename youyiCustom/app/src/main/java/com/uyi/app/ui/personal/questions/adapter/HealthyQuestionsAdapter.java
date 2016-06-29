package com.uyi.app.ui.personal.questions.adapter;

import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.health.adapter.HealthDatabaseAdapter.Holder;
import com.uyi.custom.app.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 健康问答
 * @author user
 *
 */
public class HealthyQuestionsAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	private Context context;
	
	public HealthyQuestionsAdapter(Context context) {
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
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	item_healthy_questions_content = (TextView) v.findViewById(R.id.item_healthy_questions_content);
        	item_healthy_questions_date = (TextView) v.findViewById(R.id.item_healthy_questions_date);
		}
        private TextView item_healthy_questions_content;
        private TextView item_healthy_questions_date;
    }

}
