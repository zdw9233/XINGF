package com.uyi.app.ui.team.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.utils.ImageUtil;
import com.uyi.custom.app.R;

import java.util.Map;


/**
 * 我的团队
 *
 * @author user
 */
public class HealthTeamAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

    Context context;

    public HealthTeamAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team, parent, false);
        return new Holder(this, view);
    }

    @Override
    public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
        if (viewHolder instanceof Holder) {
            Holder hodler = (Holder) viewHolder;
            hodler.item_team_name.setText(data.get("name").toString());
            if ((int) data.get("isMy") == 1) {
                hodler.item_team_time.setVisibility(View.GONE);
                hodler.item_team_my_group.setVisibility(View.VISIBLE);
                hodler.item_team_time.setText("有效期:" + data.get("start").toString() + "到" + data.get("end").toString());
            } else if ((int) data.get("isMy") == 2) {
                hodler.item_team_time.setVisibility(View.GONE);
                hodler.item_team_time.setText("已过期");

            } else {
                hodler.item_team_time.setVisibility(View.GONE);
                hodler.item_team_my_group.setVisibility(View.GONE);
            }

            hodler.item_team_content.setText(data.get("info").toString());
//            ImageCacheManager.loadImage(data.get("logo").toString(), ImageCacheManager.getImageListener(hodler.item_team_logo, null, null));
            ImageUtil.load(data.get("logo").toString(),hodler.item_team_logo);
        }
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(HealthTeamAdapter base, View v) {
            base.super(v);
            item_team_logo = (SimpleDraweeView) v.findViewById(R.id.item_team_logo);
            item_team_name = (TextView) v.findViewById(R.id.item_team_name);
            item_team_my_group = (TextView) v.findViewById(R.id.item_team_my_group);
            item_team_time = (TextView) v.findViewById(R.id.item_team_time);
            item_team_content = (TextView) v.findViewById(R.id.item_team_content);
        }

        private SimpleDraweeView item_team_logo;
        private TextView item_team_name;
        private TextView item_team_my_group;
        private TextView item_team_time;
        private TextView item_team_content;
    }

}
