package com.uyi.app.ui.healthinfo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.ui.healthinfo.model.Mecicine;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.util.List;

/**
 * Created by Leeii on 2016/8/25.
 */
public class MedicineAdapter extends BaseAdapter {
    private List<Mecicine> results;
    public MedicineAdapter(Context context) {
        super(context, R.layout.layout_medicine_name_item);
    }

    public void setResults(List<Mecicine> results) {
        this.results = results;
    }

    @Override
    protected int getCount() {
        return results==null?0:results.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView name = holder.holder(R.id.name,TextView.class);
        name.setText(results.get(position).name);
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public void reset(){
        if (results!=null){
            results.clear();
            notifyDataSetChanged();
        }
    }

    public String getName(int position) {
        return results==null?"":results.get(position).name;
    }
}
