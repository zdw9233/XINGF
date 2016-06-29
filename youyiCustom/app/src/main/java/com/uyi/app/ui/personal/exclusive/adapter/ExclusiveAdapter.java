package com.uyi.app.ui.personal.exclusive.adapter;

import java.text.ParseException;
import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.utils.DateUtils;
import com.uyi.custom.app.R;
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
	
	public ExclusiveAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_exclusive, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
				hodler.item_exclusive_time.setText(data.get("end").toString());
			hodler.item_exclusive_name.setText(data.get("name").toString());
			int type = (int)data.get("type");//我的  还是 所有
			
			int people = (int)data.get("people");
			int reserved = (int)data.get("reserved");
			if( type == 1){
				hodler.item_exclusive_count.setText(reserved + "" + (reserved >= people ? "(已满员)":""));
			}else{
				hodler.item_exclusive_count.setText(reserved+"");
			}
			hodler.item_exclusive_content.setText(data.get("desc").toString());
			if(type == 1){
				int status = (int)data.get("status");
				if(status == 0){
					hodler.item_exclusive_status.setText("将要进行");
				}else if(status == 1){
					hodler.item_exclusive_status.setText("已取消");
				}else if(status == 2){
					hodler.item_exclusive_status.setText("预约满员");
				}else if(status == 3){
					hodler.item_exclusive_status.setText("已结束");
				}
			}else{
				hodler.item_exclusive_status.setText("");
			}
			ImageCacheManager.loadImage(data.get("icon").toString(), ImageCacheManager.getImageListener(hodler.item_exclusive_header, null, null));
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	item_exclusive_status = (TextView) v.findViewById(R.id.item_exclusive_status);
        	item_exclusive_count = (TextView) v.findViewById(R.id.item_exclusive_count);
        	item_exclusive_content = (TextView) v.findViewById(R.id.item_exclusive_content);
        	item_exclusive_time = (TextView) v.findViewById(R.id.item_exclusive_time);
        	item_exclusive_name = (TextView) v.findViewById(R.id.item_exclusive_name);
        	item_exclusive_header = (RoundedImageView) v.findViewById(R.id.item_exclusive_header);
		}
        private TextView item_exclusive_status;
        private TextView item_exclusive_count;
        private TextView item_exclusive_content;
        private TextView item_exclusive_time;
        private TextView item_exclusive_name;
        private RoundedImageView item_exclusive_header;
    }
}