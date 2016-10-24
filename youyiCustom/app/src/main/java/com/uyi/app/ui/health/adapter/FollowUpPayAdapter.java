package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.custom.app.R;

import java.util.Map;


/**
 *
 * @author user
 *
 */
public class FollowUpPayAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	Context context;

	public FollowUpPayAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_follow_up_pay_list, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.text.setText(data.get("planName").toString());
			if(data.get("one").toString().equals("Y")){
				hodler.zhuangtai.setVisibility(View.VISIBLE);
			}else{
				hodler.zhuangtai.setVisibility(View.GONE);
			}
		}
	}
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(FollowUpPayAdapter base, View v) {
        	base.super(v);
			layout = (RelativeLayout) v.findViewById(R.id.layout);
//			layout.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//
//					if(zhuangtai.getVisibility() == View.VISIBLE){
//						zhuangtai.setVisibility(View.GONE);
//					}else{
//						zhuangtai.setVisibility(View.VISIBLE);
//					}
//				}
//			});
			text = (TextView) v.findViewById(R.id.text);
			zhuangtai = (ImageView) v.findViewById(R.id.zhuangtai);
		}
        private RelativeLayout layout;
        private TextView text;
        private ImageView zhuangtai;
    }

}
