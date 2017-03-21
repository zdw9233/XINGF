package com.uyi.app.ui.newCalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.newCalendar.CalendarRCActivity;
import com.uyi.doctor.app.R;

import java.util.Map;


/**
 *
 * @author user
 *
 */
public class StrokeAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	Context context;

	public StrokeAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_strock_list, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, final Map<String, Object> data) {
		if (viewHolder instanceof Holder) {
			Holder hodler = (Holder) viewHolder;
			hodler.text.setText(data.get("content").toString());
			hodler.details.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((CalendarRCActivity)context).choseStrock( data);
				}
			});
		}
	}
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(StrokeAdapter base, View v) {
        	base.super(v);
			text = (TextView) v.findViewById(R.id.text);
			details = (TextView) v.findViewById(R.id.sandian);
		}
        private TextView text;
		private TextView details;
    }

}
