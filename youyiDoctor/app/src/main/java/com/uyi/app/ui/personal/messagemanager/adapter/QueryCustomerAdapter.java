package com.uyi.app.ui.personal.messagemanager.adapter;

import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 *  查询病人名称
 * @author user
 *
 */
public class QueryCustomerAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	private Context context;
	
	public QueryCustomerAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_query_customer, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.name.setText(data.get("realName").toString());
			String info = String.format("年龄：%s    性别：%s", data.get("age"),(ValidationUtils.equlse("MALE", data.get("gender").toString())?"男":"女"));
			hodler.info.setText(info);
			ImageCacheManager.loadImage(data.get("icon").toString(), ImageCacheManager.getImageListener(hodler.header, null, null));
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	header = (RoundedImageView) v.findViewById(R.id.header);
        	name = (TextView) v.findViewById(R.id.name);
        	info = (TextView) v.findViewById(R.id.info);
		}
        private RoundedImageView header;
        private TextView name;
        private TextView info;
    }

}
