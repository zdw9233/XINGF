package com.uyi.app.ui.health.adapter;

import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.utils.UYIUtils;
import com.uyi.doctor.app.R;

import com.volley.ImageCacheManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
			hodler.item_health_time.setText(data.get("updateTime").toString().subSequence(0, 10));		
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
        
		}
		private RoundedImageView    item_health_header;
        private TextView item_health_name;
        private TextView item_health_time;
    
    }

}
