package com.uyi.app.ui.team;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.team.adapter.HealthTeamAdapter;
import com.uyi.app.ui.team.city.CityListActivity;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 健康团队
 *
 * @author user
 */
public class FragmentHealthTeam extends BaseFragment implements Pager, OnRefreshListener, OnItemClickListener<Map<String, Object>> {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;

    @ViewInject(R.id.team_selected_caty)
    public static TextView team_selected_caty;
    @ViewInject(R.id.team_edit_text)
    private EditText team_edit_text;
    @ViewInject(R.id.sousuo)
    private TextView sousuo;
String name;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private HealthTeamAdapter healthTeamAdapter;
    public boolean initLoad = true;//初始化加载
    public Main main;
    public static int cityid = -1;

    private Loading mLoading;

    public FragmentHealthTeam setMain(Main main) {
        this.main = main;
        return this;
    }

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_team;
    }

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showTitle(true).setTitle("健康团队").setTitleColor(getResources().getColor(R.color.blue)).showRight(true);

        linearLayoutManager = new LinearLayoutManager(getView().getContext());
        healthTeamAdapter = new HealthTeamAdapter(getView().getContext());
        healthTeamAdapter.setOnItemClickListener(this);
        healthTeamAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getView().getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(healthTeamAdapter);
        recyclerView.setPager(this);
        sousuo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        team_selected_caty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CityListActivity.class));

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
//        if (initLoad) {
//            onInitLayoutAfter();
//        }onRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon);
        onRefresh();
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {//所有团队
        name =  team_edit_text.getText().toString();
        if(!ValidationUtils.isNull(name)){
            try {
                name = URLEncoder.encode(name,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        isLooding = false;
        if (mLoading == null) {
            mLoading = Loading.bulid(getActivity(), null);
            mLoading.show();
        }else
        mLoading.show();
        if (team_selected_caty.getText().equals("全部城市")) {
            RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_ALL, name, "", pageNo, pageSize), getActivity(), new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        totalPage = data.getInt("pages");
                        JSONArray array = data.getJSONArray("results");
                        if (pageNo == 1) datas.clear();
                        for (int i = 0; i < array.length(); i++) {
                            Map<String, Object> item = new HashMap<>();
                            JSONObject jsonObject = array.getJSONObject(i);

                            item.put("id", jsonObject.getInt("id"));
                            item.put("name", jsonObject.getString("name"));
                            item.put("info", jsonObject.getString("info"));
                            item.put("logo", jsonObject.getString("logo"));
                            item.put("isMy", jsonObject.getInt("isMy"));
                            if (jsonObject.getInt("isMy") == 1) {
                                item.put("start", jsonObject.getString("start"));
                                item.put("end", jsonObject.getString("end"));
                            }

                            datas.add(item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (mLoading != null && mLoading.isShowing())
                            mLoading.dismiss();
                    }
                    healthTeamAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    if (mLoading != null && mLoading.isShowing())
                        mLoading.dismiss();
                    if (pageNo <= totalPage) {
                        isLooding = true;
                        pageNo++;
                } else {
                    recyclerView.setRefreshing(false);

                }
                }
            });
        } else {
            RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_ALL, name, cityid, pageNo, pageSize), getActivity(), new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        totalPage = data.getInt("pages");
                        JSONArray array = data.getJSONArray("results");
                        if (pageNo == 1) datas.clear();
                        for (int i = 0; i < array.length(); i++) {
                            Map<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject = array.getJSONObject(i);

                            item.put("id", jsonObject.getInt("id"));
                            item.put("name", jsonObject.getString("name"));
                            item.put("info", jsonObject.getString("info"));
                            item.put("logo", jsonObject.getString("logo"));
                            item.put("isMy", jsonObject.getInt("isMy"));
                            if (jsonObject.getInt("isMy") == 1) {
                                item.put("start", jsonObject.getString("start"));
                                item.put("end", jsonObject.getString("end"));
                            }

                            datas.add(item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    healthTeamAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    if (mLoading != null && mLoading.isShowing())
                        mLoading.dismiss();
                    if (pageNo < totalPage) {
                        isLooding = true;
                        pageNo++;
                    } else {
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
        recyclerView.setRefreshing(false);
        loadNextPage();
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        Intent intent = new Intent(getActivity(), TeamDetailsActivity.class);
        intent.putExtra("id", (int) data.get("id"));
        intent.putExtra("isMy", (int) data.get("isMy"));
        startActivity(intent);
//		startActivityForResult(intent,Constens.START_ACTIVITY_FOR_RESULT);
//		healthTeamAdapter.notifyItemChanged(position);
    }

}
