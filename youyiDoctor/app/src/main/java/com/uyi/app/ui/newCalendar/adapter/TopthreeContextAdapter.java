package com.uyi.app.ui.newCalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.doctor.app.R;

import java.util.Map;


/**
 *
 * @author user
 *
 */
public class TopthreeContextAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	Context context;

	public TopthreeContextAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_three_list, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if (viewHolder instanceof Holder) {
			final Holder hodler = (Holder) viewHolder;
			hodler.text.setText(data.get("content").toString());
//			if(data.get("extra").toString().equals("true")){
//				hodler.xinxin.setVisibility(View.VISIBLE);
//			}else{
//				hodler.xinxin.setVisibility(View.GONE);
//			}
		}
	}
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(TopthreeContextAdapter base, View v) {
        	base.super(v);
			text = (TextView) v.findViewById(R.id.text);
		}
        private TextView text;
    }

}
