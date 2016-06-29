package com.uyi.app.ui.personal.customer.adapter;

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
 * 用户
 * @author user
 *
 */
public class CustomerAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	private Context context;
	private OnItemCustomerClickListenner onItemCustomerClickListenner;
	
	
	public void setOnItemCustomerClickListenner(OnItemCustomerClickListenner onItemCustomerClickListenner) {
		this.onItemCustomerClickListenner = onItemCustomerClickListenner;
	}

	public CustomerAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_customer_name.setText(data.get("realName").toString());
			hodler.item_customer_age.setText("年龄："+data.get("age"));
			hodler.item_customer_gender.setText("性别："+UYIUtils.convertGender(data.get("gender").toString()));
			
			if(onItemCustomerClickListenner != null){
				hodler.item_customer_info.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						onItemCustomerClickListenner.click(data, v, 1);
					}
				});
				hodler.item_customer_consult.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						onItemCustomerClickListenner.click(data, v, 2);
					}
				});
			}
			
			ImageCacheManager.loadImage(data.get("icon").toString(), ImageCacheManager.getImageListener(hodler.roundedImageView1, null, null));
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	roundedImageView1 = (RoundedImageView) v.findViewById(R.id.roundedImageView1);
        	item_customer_name = (TextView) v.findViewById(R.id.item_customer_name);
        	item_customer_age = (TextView) v.findViewById(R.id.item_customer_age);
        	item_customer_gender = (TextView) v.findViewById(R.id.item_customer_gender);
        	item_customer_info = (TextView) v.findViewById(R.id.item_customer_info);
        	item_customer_consult = (TextView) v.findViewById(R.id.item_customer_consult);
		}
        
        private RoundedImageView roundedImageView1;
        private TextView item_customer_name;
        private TextView item_customer_age;
        private TextView item_customer_gender;
        private TextView item_customer_info;
        private TextView item_customer_consult;
    }

    
    public interface OnItemCustomerClickListenner{
    	public void click(Map<String,Object> item,View view,int type);
    }
}
