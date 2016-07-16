package com.uyi.app.ui.personal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.custom.app.R;

import java.util.Map;


/**
 * 我的团队
 *
 * @author user
 */
public class ServiceAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

    Context context;

    public ServiceAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_service_number_item, parent, false);
        return new Holder(this, view);
    }

    @Override
    public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
        if (viewHolder instanceof Holder) {
            Holder hodler = (Holder) viewHolder;
            hodler.layout_service_number_item_name.setText(data.get("name").toString());
            hodler.layout_service_number_item_number.setText("总次数"+data.get("defaultCount").toString());
            hodler.layout_service_number_item_surplus.setText("剩余次数"+data.get("surplusCount").toString());
        }
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(ServiceAdapter base, View v) {
            base.super(v);
            layout_service_number_item_name = (TextView) v.findViewById(R.id.layout_service_number_item_name);
            layout_service_number_item_number = (TextView) v.findViewById(R.id.layout_service_number_item_number);
            layout_service_number_item_surplus = (TextView) v.findViewById(R.id.layout_service_number_item_surplus);
        }


        private TextView layout_service_number_item_name;
        private TextView layout_service_number_item_number;
        private TextView layout_service_number_item_surplus;
    }

}
