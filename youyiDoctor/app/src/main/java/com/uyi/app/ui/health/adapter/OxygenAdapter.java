package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.doctor.app.R;

import java.util.Map;


/**
 * 健康数据库列表
 * @author user
 *
 */
public class OxygenAdapter extends BaseRecyclerAdapter<Map<String,Object>> {
	public Context context;
	public OxygenAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_oxygen, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.title1.setText(data.get("uploadTime").toString());
			if(!data.get("heartRate").equals("NULL")){
				hodler.title2.setText(data.get("heartRate").toString()+"次/分");
				if(!data.get("heartRateWarning").equals("NULL")){
					//获取资源图片
					Drawable leftDrawable = context.getResources().getDrawable(R.drawable.warning);
					//设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
					leftDrawable.setBounds(0, 0, 20, 20);
					//设置图片在TextView中的位置
					hodler.title2.setCompoundDrawables(leftDrawable, null, null, null);
//					hodler.title2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warning, 0, 0, 0);
				}else{
					//获取资源图片
					Drawable leftDrawable = context.getResources().getDrawable(R.drawable.success_icon);
					//设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
					leftDrawable.setBounds(0, 0, 20, 20);
					//设置图片在TextView中的位置
					hodler.title2.setCompoundDrawables(leftDrawable, null, null, null);
//					hodler.title2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success_icon, 0, 0, 0);
				}
			}else{
				hodler.title2.setText("--");
				hodler.title2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			}

			if(!data.get("spo").equals("NULL")){
				hodler.title3.setText(data.get("spo").toString()+"%");
				if(!data.get("spoWarning").equals("NULL")){
					//获取资源图片
					Drawable leftDrawable = context.getResources().getDrawable(R.drawable.warning);
					//设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
					leftDrawable.setBounds(0, 0, 20, 20);
					//设置图片在TextView中的位置
					hodler.title3.setCompoundDrawables(leftDrawable, null, null, null);
//					hodler.title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warning, 0, 0, 0);
				}else{
					//获取资源图片
					Drawable leftDrawable = context.getResources().getDrawable(R.drawable.success_icon);
					//设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
					leftDrawable.setBounds(0, 0, 20, 20);
					//设置图片在TextView中的位置
					hodler.title3.setCompoundDrawables(leftDrawable, null, null, null);
//					hodler.title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.success_icon, 0, 0, 0);
				}
			}else{
				hodler.title3.setText("--");
				hodler.title3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			}


		}
	}
    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base, View v) {
        	base.super(v);
			title1 = (TextView) v.findViewById(R.id.title1);
			title2 = (TextView) v.findViewById(R.id.title2);
			title3 = (TextView) v.findViewById(R.id.title3);

		}
		private TextView    title1;
        private TextView title2;
        private TextView title3;
    }

}
