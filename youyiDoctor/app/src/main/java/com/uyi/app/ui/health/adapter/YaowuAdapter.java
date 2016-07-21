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
public class YaowuAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

    Context context;

    public YaowuAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_yaowu_item, parent, false);
        return new Holder(this,view);
    }

    @Override
    public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
        if(viewHolder instanceof Holder){
            Holder hodler = (Holder)viewHolder;
            hodler.time_start.setText("开始日期:"+data.get("startTime").toString());
            hodler.time_end.setText("结束日期:"+data.get("endTime").toString());
            hodler.medsin.setText(data.get("medicineName").toString());
            hodler.jiliang.setText(data.get("usingFrequency").toString()+data.get("frequencyUnit").toString()+data.get("singleDose").toString()+data.get("medicineUnit").toString());
        }
    }
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(YaowuAdapter base, View v) {
            base.super(v);
            time_start = (TextView) v.findViewById(R.id.time_start);
            time_end = (TextView) v.findViewById(R.id.time_end);
            medsin = (TextView) v.findViewById(R.id.medsin);
            jiliang = (TextView) v.findViewById(R.id.jiliang);
        }
        private TextView time_start;
        private TextView time_end;
        private TextView medsin;
        private TextView jiliang;


    }

}
