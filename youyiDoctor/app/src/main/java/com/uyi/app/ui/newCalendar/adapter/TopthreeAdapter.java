package com.uyi.app.ui.newCalendar.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.newCalendar.CalendarRCActivity;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;

import java.util.ArrayList;
import java.util.Map;


/**
 *
 * @author user
 *
 */
public class TopthreeAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	Context context;

	public TopthreeAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_three_name_list, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if (viewHolder instanceof Holder) {
			final Holder hodler = (Holder) viewHolder;
			hodler.text.setText(data.get("name").toString());
			ImageCacheManager.loadImage(data.get("customerIcon").toString(), ImageCacheManager.getImageListener(hodler.item_health_header, null, null));
			hodler.text.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(hodler.recyclerView.getVisibility()==View.GONE){
						hodler.recyclerView.setVisibility(View.VISIBLE);
						hodler.image1.setVisibility(View.VISIBLE);
						hodler.image.setVisibility(View.GONE);
					}else{
						hodler.recyclerView.setVisibility(View.GONE);
						hodler.image1.setVisibility(View.GONE);
						hodler.image.setVisibility(View.VISIBLE);
					}
				}
			});
			ArrayList<Map<String,Object>> datatopThree = new ArrayList<>();
			datatopThree = (ArrayList<Map<String, Object>>) data.get("content");
			LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context);
			TopthreeContextAdapter topthreeContextAdapter = new TopthreeContextAdapter(context);
			topthreeContextAdapter.setDatas(datatopThree);
			hodler.recyclerView.setLayoutManager(linearLayoutManager4);
			hodler.recyclerView.setAdapter(topthreeContextAdapter);
			topthreeContextAdapter.setOnItemClickListener(new OnItemClickListener<Map<String, Object>>() {
				@Override
				public void onItemClick(int position, Map<String, Object> data) {
					((CalendarRCActivity)context).choseOne( position, data);
				}
			});
			topthreeContextAdapter.notifyDataSetChanged();


		}
	}
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(TopthreeAdapter base, View v) {
        	base.super(v);
			text = (TextView) v.findViewById(R.id.text);
			item_health_header = (RoundedImageView) v.findViewById(R.id.item_health_header);
			image= (ImageView) v.findViewById(R.id.image);
			image1= (ImageView) v.findViewById(R.id.image1);
			recyclerView= (RecyclerView) v.findViewById(R.id.recyclerView);
		text.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(recyclerView.getVisibility()==View.GONE){
						recyclerView.setVisibility(View.VISIBLE);
						image1.setVisibility(View.VISIBLE);
						image.setVisibility(View.GONE);
					}else{
						recyclerView.setVisibility(View.GONE);
					image1.setVisibility(View.GONE);
					image.setVisibility(View.VISIBLE);
					}
				}
			});
		}
        private TextView text;
		private RoundedImageView item_health_header;
		private ImageView image;
		private ImageView image1;
		private RecyclerView recyclerView;
    }

}
