package com.uyi.app.ui.personal.message.adapter;

import java.text.ParseException;
import java.util.Map;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.health.adapter.HealthDatabaseAdapter.Holder;
import com.uyi.app.utils.DateUtils;
import com.uyi.doctor.app.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 消息 公告 adapter
 * @author user
 *
 */
public class MessageAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	private Context context;
	
	public MessageAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			try {
				hodler.item_message_date.setText(DateUtils.toDateStringByDateString(data.get("updateTime").toString()) );
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hodler.item_message_content.setText(data.get("title").toString());
			boolean isRead = (Boolean)data.get("isRead");
			if((int)data.get("type") == 1){//消息
				if(isRead){
					hodler.item_message_icon.setImageResource(R.drawable.message_read);
				}else{
					hodler.item_message_icon.setImageResource(R.drawable.message_unread);
				}
			}else{//公告
				if(isRead){
					hodler.item_message_icon.setImageResource(R.drawable.notice_read);
				}else{
					hodler.item_message_icon.setImageResource(R.drawable.notice_unread);
				}
			}
		}
	}


    public static class Holder extends BaseRecyclerAdapter<Map<String,Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String,Object>> base,View v) {
        	base.super(v);
        	item_message_date = (TextView) v.findViewById(R.id.item_message_date);
        	item_message_content = (TextView) v.findViewById(R.id.item_message_content);
        	item_message_icon = (ImageView) v.findViewById(R.id.item_message_icon);
		}
        private TextView item_message_date;
        private TextView item_message_content;
        private ImageView item_message_icon;
    }

}
