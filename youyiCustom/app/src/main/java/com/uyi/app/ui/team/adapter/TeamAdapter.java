package com.uyi.app.ui.team.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.common.RegisterActivity;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;

import java.util.Map;


/**
 * 我的团队
 * @author user
 *
 */
public class TeamAdapter extends BaseRecyclerAdapter<Map<String, Object>> {

	Activity mActivity;

	public TeamAdapter(Activity context) {
		super();
		this.mActivity = context;
	}

	@Override
	public ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mActivity).inflate(R.layout.item_chose_team, parent, false);
    	return new Holder(this,view);
	}

	@Override
	public void onBind(ViewHolder viewHolder, final int RealPosition, final Map<String, Object> data) {
		if(viewHolder instanceof Holder){
			Holder hodler = (Holder)viewHolder;
			hodler.item_team_name.setText(data.get("name").toString());
			hodler.item_team_content.setText(data.get("info").toString());
			ImageCacheManager.loadImage(data.get("logo").toString(), ImageCacheManager.getImageListener(hodler.item_team_logo, null, null));
			hodler.chose_team.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((RegisterActivity)mActivity).gotoChooesTeam(RealPosition,data);
				}
			});
		}
	}
	/**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(TeamAdapter base, View v) {
        	base.super(v);
        	item_team_logo = (RoundedImageView) v.findViewById(R.id.item_team_logo);
        	item_team_name = (TextView) v.findViewById(R.id.item_team_name);
        	item_team_content = (TextView) v.findViewById(R.id.item_team_content);
			chose_team = (TextView) v.findViewById(R.id.join_immediately);
		}
		private RoundedImageView    item_team_logo;
        private TextView item_team_name;
        private TextView item_team_content;
		private TextView chose_team;
    }

}
