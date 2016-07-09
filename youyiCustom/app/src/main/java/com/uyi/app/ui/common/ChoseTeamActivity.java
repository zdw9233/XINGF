package com.uyi.app.ui.common;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.ui.team.TeamDetailsActivity;
import com.uyi.app.ui.team.adapter.TeamAdapter;
import com.uyi.app.ui.team.city.CityListActivity;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/6/22.
 */
@ContentView(R.layout.chose_team_register)
public class ChoseTeamActivity extends BaseActivity  implements EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener, BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>> {
    @ViewInject(R.id.headerView) private HeaderView headerView;
@ViewInject(R.id.register_four_chose) private TextView register_four_chose;
    @ViewInject(R.id.register_four_selected_caty) private TextView register_four_selected_caty;
    @ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
    private LinearLayoutManager linearLayoutManager;
    private TeamAdapter TeamAdapter;
    public boolean initLoad = true;//初始化加载
    public Main main;
    public static int cityid = -1;
    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftHeader(false,null).showTitle(true).setTitle("健康团队").setTitleColor(getResources().getColor(R.color.blue)).showRight(true);

        linearLayoutManager = new LinearLayoutManager(this);
       TeamAdapter = new TeamAdapter(this);
        TeamAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(TeamAdapter);
        recyclerView.setPager(this);

        register_four_selected_caty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoseTeamActivity.this,CityListActivity.class));

            }
        });
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
//		onRefresh();


    }

    @Override
    public void onStart() {
        super.onStart();
        if(initLoad){
            onRefresh();

        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
    @Override
    public boolean shouldLoad() {
        return isLooding;
    }
    @Override
    public void loadNextPage() {//所有团队
        isLooding = false;
        Looding.bulid(this, null).show();
        if(register_four_chose.getText().equals("全部城市")){
            RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_ALL,"","",pageNo,pageSize),ChoseTeamActivity.this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        totalPage = data.getInt("pages");
                        JSONArray array = data.getJSONArray("results");
                        for(int i = 0;i<array.length();i++){
                            Map<String,Object> item = new HashMap<String,Object>();
                            JSONObject jsonObject = array.getJSONObject(i);

                            item.put("id", jsonObject.getInt("id"));
                            item.put("name", jsonObject.getString("name"));
                            item.put("info", jsonObject.getString("info"));
                            item.put("logo", jsonObject.getString("logo"));

                            datas.add(item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TeamAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    Looding.bulid(ChoseTeamActivity.this, null).dismiss();
                    if(pageNo <= totalPage){
                        isLooding = true;
                        pageNo ++;
                    }else{
                        recyclerView.setRefreshing(false);

                    }
                }
            });
        }else{
            RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_ALL,"",cityid,pageNo,pageSize),ChoseTeamActivity.this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        totalPage = data.getInt("pages");
                        JSONArray  array = data.getJSONArray("results");
                        for(int i = 0;i<array.length();i++){
                            Map<String,Object> item = new HashMap<String,Object>();
                            JSONObject jsonObject = array.getJSONObject(i);

                            item.put("id", jsonObject.getInt("id"));
                            item.put("name", jsonObject.getString("name"));
                            item.put("info", jsonObject.getString("info"));
                            item.put("logo", jsonObject.getString("logo"));

                            datas.add(item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  TeamAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    Looding.bulid(ChoseTeamActivity.this, null).dismiss();
                    if(pageNo < totalPage){
                        isLooding = true;
                        pageNo ++;
                    }else{
                        recyclerView.setRefreshing(false);

                    }
                }
            });
        }
    }



    @Override
    public void onRefresh() {
        pageNo = 1;
        isLooding = true;
        datas.clear();
        recyclerView.setRefreshing(false);
        loadNextPage();

    }
    @Override
    public void onItemClick(int position, Map<String, Object> data) {
//        Intent intent = new Intent(ChoseTeamActivity.this,TeamDetailsActivity.class);
//        intent.putExtra("id", (int)data.get("id"));
//        intent.putExtra("isMy", (int)data.get("isMy"));
//        startActivity(intent);
//		startActivityForResult(intent,Constens.START_ACTIVITY_FOR_RESULT);
//		healthTeamAdapter.notifyItemChanged(position);
    }
    public void gotoChooesTeam(int position, Map<String, Object> data){
        Intent intent = new Intent(ChoseTeamActivity.this, TeamDetailsActivity.class);
        intent.putExtra("id", (int) data.get("id"));
        intent.putExtra("isMy", (int) data.get("isMy"));
        startActivity(intent);
        startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
//        healthTeamAdapter.notifyItemChanged(position);

    }
}
