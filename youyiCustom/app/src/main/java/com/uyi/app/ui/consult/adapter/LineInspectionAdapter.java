package com.uyi.app.ui.consult.adapter;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.model.bean.Consult;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.utils.UYIUtils;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 线下咨询咨询
 * @author user
 *
 */
public class LineInspectionAdapter extends BaseRecyclerAdapter<Consult>{
 
	    
	private Context context;
	
    public LineInspectionAdapter(Context context) {
		super();
		this.context = context;
	}
    
	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_follow_list, parent, false);
    	return new Holder(this,view);
	}


	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Consult data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_follow_name.setText("线下检查时间:"+data.updateTime);
			hodler.item_follow_content.setText(data.desc);
			ImageCacheManager.loadImage(data.icon, ImageCacheManager.getImageListener(hodler.item_follow_logo, null, null));
		}
	}

	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Consult>.Holder {
        public Holder(BaseRecyclerAdapter<Consult> base,View v) {
        	base.super(v);
        	item_follow_logo = (RoundedImageView) v.findViewById(R.id.item_follow_logo);
        	item_follow_name = (TextView) v.findViewById(R.id.item_follow_name);
        	item_follow_content = (TextView) v.findViewById(R.id.item_follow_content);
		}
		private RoundedImageView    item_follow_logo;
        private TextView item_follow_name;
        private TextView item_follow_content;
    }

 
}