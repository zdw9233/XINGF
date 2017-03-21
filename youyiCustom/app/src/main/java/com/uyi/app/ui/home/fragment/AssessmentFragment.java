package com.uyi.app.ui.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.health.RiskAssessmentDetailsActivity;
import com.uyi.app.ui.home.fragment.adapter.RiskAssessmentAdapter2_1;
import com.uyi.app.utils.L;
import com.uyi.app.utils.SerializableMap;
import com.uyi.app.utils.T;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.uyi.app.ui.home.fragment.MyReport.chosehosttype;

/**
 * Created by ThinkPad on 2017/2/28.
 */

public class AssessmentFragment extends BaseFragment implements BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>>, EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener {
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_risk_assessment;
   }
    @ViewInject(R.id.nocomtoms)
    private LinearLayout no_assessment;
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private AssessmentFragment.MyLinearLayoutManager linearLayoutManager;
    private RiskAssessmentAdapter2_1 healthDatabaseAdapter;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    @Override
    protected void onInitLayoutAfter() {
        linearLayoutManager = new AssessmentFragment.MyLinearLayoutManager(getActivity());
        healthDatabaseAdapter = new RiskAssessmentAdapter2_1(getActivity());
        healthDatabaseAdapter.setOnItemClickListener(this);
        healthDatabaseAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(healthDatabaseAdapter);
        recyclerView.setPager(this);
//        recyclerView.addOnScrollListener(new android.support.v7.widget.RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (linearLayoutManager.getScrollY() >400){
//                    back_top.setVisibility(View.VISIBLE);
//                }else back_top.setVisibility(View.GONE);
//            }
//        });
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }



    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
    }

    @Override
    public void onItemClick(int position, final Map<String, Object> data) {
        if(data.get("checked").toString().equals("false") ) {
            chosehosttype = 1;
            RequestManager.postObject(String.format(Constens.SETTING_REPORT, 1, data.get("id").toString()), this, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                }
            }, new RequestErrorListener() {
                @Override
                public void requestError(VolleyError e) {
                    if (e.networkResponse != null) {
                        if (e.networkResponse.statusCode == 200) {
                        } else {
                            T.showShort(getActivity(), ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                        }
                    } else {
                    }
                }
            });
        }else{
            chosehosttype = 6;
        }
        Intent intent = new Intent();
        SerializableMap myMap=new SerializableMap();
        myMap.setMap(data);//将map数据添加到封装的myMap中
        Bundle bundle=new Bundle();
        bundle.putSerializable("map", myMap);
        intent.putExtras(bundle);
        intent.setClass(getActivity(),RiskAssessmentDetailsActivity.class);
        startActivityForResult(intent,10003);
    }
    @Override
    public void onRefresh() {
        pageNo = 1;
        isLooding = true;
        recyclerView.setRefreshing(false);
        datas.clear();
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
        System.out.println(UserInfoManager.getLoginUserInfo(getActivity()).userId);
        RequestManager.getObject(String.format(Constens.CUSTOMER_HEALTH_RISK, UserInfoManager.getLoginUserInfo(getActivity()).userId, pageNo, pageSize),
                getActivity(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject data) {
                        System.out.print("_________________________________" + data.toString());
                        try {
                            totalPage = data.getInt("pages");
                            JSONArray array = data.getJSONArray("results");
//                            if (pageNo == 1) datas.clear();
                            for (int i = 0; i < array.length(); i++) {
                                Map<String, Object> item = new HashMap<String, Object>();
                                JSONObject jsonObject = array.getJSONObject(i);
                                item.put("id", jsonObject.getString("id"));
                                item.put("createTime", jsonObject.getString("createTime"));
                                item.put("doc_name", jsonObject.getString("doc_name"));
                                item.put("checked", jsonObject.getString("checked"));
                                if(jsonObject.has("percentageASVCD")){
                                    item.put("percentageASVCD", jsonObject.getString("percentageASVCD"));
                                }else{
                                    item.put("percentageASVCD", "");
                                }
                                if(jsonObject.has("percentageICVD")){
                                    item.put("percentageICVD", jsonObject.getString("percentageICVD"));
                                }else{
                                    item.put("percentageICVD", "");
                                }
                                if(jsonObject.has("bloodPressureConditions")){
                                    item.put("bloodPressureConditions", jsonObject.getString("bloodPressureConditions"));
                                }else{
                                    item.put("bloodPressureConditions", "");
                                }
                                if(jsonObject.has("bloodSugarConditions")){
                                    item.put("bloodSugarConditions", jsonObject.getString("bloodSugarConditions"));
                                }else{
                                    item.put("bloodSugarConditions", "");
                                }
                                if(jsonObject.has("healthIndicator")){
                                    item.put("healthIndicator", jsonObject.getString("healthIndicator"));
                                }else{
                                    item.put("healthIndicator", "");
                                }
                                if(jsonObject.has("advice")){
                                    item.put("advice", jsonObject.getString("advice"));
                                }else{
                                    item.put("advice", "");
                                }
                                if(jsonObject.has("content")){
                                    item.put("content", jsonObject.getString("content"));
                                }else{
                                    item.put("content", "");
                                }
                                if(jsonObject.has("percentage")){
                                    item.put("percentage", jsonObject.getString("percentage"));
                                }else{
                                    item.put("percentage", "");
                                }

//								item.put("isWarning", jsonObject.getBoolean("isWarning"));

                                datas.add(item);
                            }
                            if(datas.size() > 0){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10003){
            onRefresh();
            L.e("111.onRefresh();");
        }
    }
}
