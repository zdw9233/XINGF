package com.uyi.app.ui.team.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.team.Pinjia;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;

public class DocterDtailsAdapter extends BaseRecyclerAdapter<Pinjia>{
	 
    
	private Context context;
	
    public DocterDtailsAdapter(Context context) {
		super();
		this.context = context;
	}
    
	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_pinjia, parent, false);
    	return new Holder(this,view);
	}


	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Pinjia data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.docter_pinjia_info.setText(data.comment);
			hodler.docter_pinjia_time.setText(data.date);
			hodler.docter_pinjia_rating.setRating(data.star);
			ImageCacheManager.loadImage(data.icon, ImageCacheManager.getImageListener(hodler.docter_pinjia_icon, null, null));
		}
	}

	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Pinjia>.Holder {
        public Holder(BaseRecyclerAdapter<Pinjia> base,View v) {
        	base.super(v);
        	docter_pinjia_icon = (RoundedImageView) v.findViewById(R.id.docter_pinjia_icon);
        	docter_pinjia_info = (TextView) v.findViewById(R.id.docter_pinjia_info);
        	docter_pinjia_time = (TextView) v.findViewById(R.id.docter_pinjia_time);
        	docter_pinjia_rating = (RatingBar) v.findViewById(R.id.docter_pinjia_rating);
		}
		private RoundedImageView    docter_pinjia_icon;
        private TextView docter_pinjia_info;
        private TextView docter_pinjia_time;
        private RatingBar docter_pinjia_rating;
    }

 
}
