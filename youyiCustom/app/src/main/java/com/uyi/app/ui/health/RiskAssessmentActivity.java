package com.uyi.app.ui.health;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.adapter.RiskAssessmentAdapter;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/7/1.
 */
@ContentView(R.layout.risk_assessment)
public class RiskAssessmentActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
//    @ViewInject(R.id.new_assessment_time)
//    private TextView new_assessment_time;
//
//    @ViewInject(R.id.doc_name)
//    private TextView doc_name;
//    @ViewInject(R.id.risk_index)
//    private TextView risk_index;
//    @ViewInject(R.id.deils)
//    private TextView deils;
    @ViewInject(R.id.no_assessment)
    private TextView no_assessment;
    @ViewInject(R.id.back_top)
    private Button back_top;
    int isgone = 0;
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyLinearLayoutManager linearLayoutManager;
    private RiskAssessmentAdapter healthDatabaseAdapter;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    int scorllW;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("风险评估").setTitleColor(getResources().getColor(R.color.blue));
        linearLayoutManager = new MyLinearLayoutManager(this);
        healthDatabaseAdapter = new RiskAssessmentAdapter(this);
        healthDatabaseAdapter.setOnItemClickListener(this);
        healthDatabaseAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(healthDatabaseAdapter);
        recyclerView.setPager(this);
        recyclerView.addOnScrollListener(new android.support.v7.widget.RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.getScrollY() >400){
                    back_top.setVisibility(View.VISIBLE);
                }else back_top.setVisibility(View.GONE);
            }
        });
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }



    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        isLooding = true;
        recyclerView.setRefreshing(false);
        loadNextPage();
    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @OnClick({
        R.id.back_top
    })
    public void onClick(View v){
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        Loading.bulid(activity, null).show();
        System.out.println(UserInfoManager.getLoginUserInfo(activity).userId);
        RequestManager.getObject(String.format(Constens.CUSTOMER_HEALTH_RISK, UserInfoManager.getLoginUserInfo(this).userId, pageNo, pageSize),
                activity, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        System.out.print("_________________________________" + data.toString());
                        try {
                            Loading.bulid(activity, null).dismiss();
                            totalPage = data.getInt("pages");
                            JSONArray array = data.getJSONArray("results");
                            if (pageNo == 1) datas.clear();
                            for (int i = 0; i < array.length(); i++) {
                                Map<String, Object> item = new HashMap<String, Object>();
                                JSONObject jsonObject = array.getJSONObject(i);
                                item.put("id", jsonObject.getString("id"));
                                if(jsonObject.has("content")){
                                    item.put("content", jsonObject.getString("content"));
                                }else{
                                    item.put("content", "");
                                }
                                item.put("createTime", jsonObject.getString("createTime"));
                                if(jsonObject.has("percentage")){
                                    item.put("percentage", jsonObject.getString("percentage"));
                                }else{
                                    item.put("percentage", "");
                                }

                                item.put("checked", jsonObject.getString("checked"));
                                item.put("doc_name", jsonObject.getString("doc_name"));
//								item.put("isWarning", jsonObject.getBoolean("isWarning"));

                                datas.add(item);
                            }
                            if(datas.size() > 0){
//                                doc_name.setText("填写医生:"+datas.get(0).get("doc_name").toString());
//                                new_assessment_time.setText(datas.get(0).get("createTime").toString());
//                                risk_index.setText("风险指数："+datas.get(0).get("percentage").toString());
//                                deils.setText(datas.get(0).get("content").toString());
//                            }else{
//                                risk_index.setText("无");

                                no_assessment.setVisibility(View.GONE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                            }else{
                                no_assessment.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.GONE);
                        }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        healthDatabaseAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                        if (pageNo < totalPage) {
                            isLooding = true;
                            pageNo++;
                        } else {
                            recyclerView.setRefreshing(false);

                        }
                    }
                });
    }
    class MyLinearLayoutManager extends LinearLayoutManager {

        private RecyclerView.Recycler mRecycler;

        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
            mRecycler = recycler;
        }

        public int getScrollY() {
            int scrollY = getPaddingTop();
            int firstVisibleItemPosition = findFirstVisibleItemPosition();

            if (firstVisibleItemPosition >= 0 && firstVisibleItemPosition < getItemCount()) {
                for (int i = 0; i < firstVisibleItemPosition; i++) {
                    View view = mRecycler.getViewForPosition(i);
                    if (view == null) {
                        continue;
                    }
                    if (view.getMeasuredHeight() <= 0) {
                        measureChildWithMargins(view, 0, 0);
                    }
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                    scrollY += lp.topMargin;
                    scrollY += getDecoratedMeasuredHeight(view);
                    scrollY += lp.bottomMargin;
                    mRecycler.recycleView(view);
                }

                View firstVisibleItem = findViewByPosition(firstVisibleItemPosition);
                RecyclerView.LayoutParams firstVisibleItemLayoutParams = (RecyclerView.LayoutParams) firstVisibleItem.getLayoutParams();
                scrollY += firstVisibleItemLayoutParams.topMargin;
                scrollY -= getDecoratedTop(firstVisibleItem);
            }

            return scrollY;
        }
    }
}
