package com.uyi.app.ui.health.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.doctor.app.R;

import java.util.Map;


/**
 * 我的团队
 * @author user
 *
 */
public class JiuyiAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

    Context context;

    public JiuyiAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_jiuyi_item, parent, false);
        return new Holder(this,view);
    }

    @Override
    public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
        if(viewHolder instanceof Holder){
            Holder hodler = (Holder)viewHolder;
            hodler.time.setText(data.get("treatmentTime").toString());
            hodler.content.setText(data.get("content").toString());
        }
    }
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(JiuyiAdapter base, View v) {
            base.super(v);
            time = (TextView) v.findViewById(R.id.time);
            content = (TextView) v.findViewById(R.id.content);

        }
        private TextView time;
        private TextView content;


    }

}
