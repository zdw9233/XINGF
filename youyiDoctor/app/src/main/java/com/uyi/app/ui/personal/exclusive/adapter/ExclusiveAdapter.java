package com.uyi.app.ui.personal.exclusive.adapter;

import java.text.ParseException;
import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.utils.DateUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * 
 * @author user
 *
 */
public class ExclusiveAdapter  extends BaseRecyclerAdapter<Map<String, Object>> {

	private Context context;
	private OnExclusiveUpdateListenner onExclusiveUpdateListenner;
	
	
	public void setOnExclusiveUpdateListenner(OnExclusiveUpdateListenner onExclusiveUpdateListenner) {
		this.onExclusiveUpdateListenner = onExclusiveUpdateListenner;
	}

	public ExclusiveAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_exclusive, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_exclusive_time.setText("时间："+data.get("end").toString());
			hodler.item_exclusive_address.setText("地点："+data.get("address").toString());
			hodler.item_exclusive_count.setText(data.get("reserved")+"人");
			hodler.item_exclusive_max_count.setText(data.get("people")+"人");
			if((int)data.get("status") == 0){ 
				hodler.item_exclusive_update.setVisibility(View.VISIBLE);
				if(onExclusiveUpdateListenner != null){
					hodler.item_exclusive_update.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							onExclusiveUpdateListenner.onUpdate(data);
						}
					});
				}
			}else {
				hodler.item_exclusive_update.setVisibility(View.INVISIBLE);
			}
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	item_exclusive_time = (TextView) v.findViewById(R.id.item_exclusive_time);
        	item_exclusive_address = (TextView) v.findViewById(R.id.item_exclusive_address);
        	item_exclusive_count = (TextView) v.findViewById(R.id.item_exclusive_count);
        	item_exclusive_max_count = (TextView) v.findViewById(R.id.item_exclusive_max_count);
        	item_exclusive_update = (TextView) v.findViewById(R.id.item_exclusive_update);
		}
        private TextView item_exclusive_time;
        private TextView item_exclusive_address;
        private TextView item_exclusive_count;
        private TextView item_exclusive_max_count;
        private TextView item_exclusive_update;
    }
    
    public interface OnExclusiveUpdateListenner{
    	public void onUpdate(Map<String,Object> item);
    }
}