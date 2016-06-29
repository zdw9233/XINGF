package com.uyi.app.ui.personal.discuss.adapter;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.model.bean.Consult;
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
 * 讨论组
 * @author user
 *
 */
public class DiscussGroupAdapter extends BaseRecyclerAdapter<Consult>{
 
	    
	private Context context;
	
    public DiscussGroupAdapter(Context context) {
		super();
		this.context = context;
	}
    
	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_discuss_group, parent, false);
    	return new Holder(this,view);
	}


	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Consult data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_discuss_group_time.setText("发起时间："+data.updateTime);
			hodler.item_discuss_group_content.setText(data.desc);
			ImageCacheManager.loadImage(data.icon, ImageCacheManager.getImageListener(hodler.item_discuss_group_logo, null, null));
		}
	}

	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Consult>.Holder {
        public Holder(BaseRecyclerAdapter<Consult> base,View v) {
        	base.super(v);
        	item_discuss_group_logo = (RoundedImageView) v.findViewById(R.id.item_discuss_group_logo);
        	item_discuss_group_time = (TextView) v.findViewById(R.id.item_discuss_group_time);
        	item_discuss_group_content = (TextView) v.findViewById(R.id.item_discuss_group_content);
		}
		private RoundedImageView    item_discuss_group_logo;
        private TextView item_discuss_group_time;
        private TextView item_discuss_group_content;
    }

 
}