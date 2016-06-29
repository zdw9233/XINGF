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
 * 健康咨询
 * @author user
 *
 */
public class ConsultationAdapter extends BaseRecyclerAdapter<Consult>{
 
	    
	private Context context;
	
    public ConsultationAdapter(Context context) {
		super();
		this.context = context;
	}
    
	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_consultation, parent, false);
    	return new Holder(this,view);
	}


	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Consult data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_consultation_name.setText(UYIUtils.convertConsultationStatus(data.status,data.isCommented,data.isDiscuss));
			hodler.item_consultation_time.setText(data.updateTime);
			hodler.item_consultation_content.setText(data.desc);
			ImageCacheManager.loadImage(data.icon, ImageCacheManager.getImageListener(hodler.item_consultation_logo, null, null));
		}
	}

	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Consult>.Holder {
        public Holder(BaseRecyclerAdapter<Consult> base,View v) {
        	base.super(v);
        	item_consultation_logo = (RoundedImageView) v.findViewById(R.id.item_consultation_logo);
        	item_consultation_name = (TextView) v.findViewById(R.id.item_consultation_name);
        	item_consultation_time = (TextView) v.findViewById(R.id.item_consultation_time);
        	item_consultation_content = (TextView) v.findViewById(R.id.item_consultation_content);
		}
		private RoundedImageView    item_consultation_logo;
        private TextView item_consultation_name;
        private TextView item_consultation_time;
        private TextView item_consultation_content;
    }

 
}