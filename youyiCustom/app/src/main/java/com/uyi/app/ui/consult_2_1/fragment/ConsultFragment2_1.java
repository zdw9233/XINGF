package com.uyi.app.ui.consult_2_1.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.model.bean.Consult;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.Main2_1;
import com.uyi.app.ui.consult.ConsultDetailsActivity;
import com.uyi.app.ui.consult.NewConsultActivity;
import com.uyi.app.ui.consult_2_1.fragment.adapter.ConsultationAdapter2_1;
import com.uyi.app.ui.consult_2_1.fragment.adapter.HealthyQuestionsAdapter2_1;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.ui.personal.questions.HealthyQuestionsAddActivity;
import com.uyi.app.ui.personal.questions.HealthyQuestionsDetailsActivity;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 咨询（新） Created by Leeii on 2016/6/19.
 */
public class ConsultFragment2_1 extends BaseFragment implements EndlessRecyclerView.Pager, SwipeRefreshLayout.OnRefreshListener, BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>> {
    @ViewInject(R.id.fragment_consult_2_1)
    private LinearLayout fragment_consult_2_1;
    @ViewInject(R.id.tab_line_1)
    private TextView tab_line_1;
    @ViewInject(R.id.tab_1)
    private TextView tab_1;
    @ViewInject(R.id.tab_2)
    private TextView tab_2;
    @ViewInject(R.id.tab_line_2)
    private TextView tab_line_2;
    @ViewInject(R.id.consult_text)
    private TextView consult_text;
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private HealthyQuestionsAdapter2_1 healthyQuestionsAdapter;
    int choseType = 1;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private ConsultationAdapter2_1 consultationAdapter;

    private ArrayList<Consult> datas1= new ArrayList<Consult>();
    private UserInfo userInfo;
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_consult_2_1;
    }

    @Override
    protected void onInitLayoutAfter() {
        choseType = 1;
        linearLayoutManager = new LinearLayoutManager(getView().getContext());
        healthyQuestionsAdapter = new HealthyQuestionsAdapter2_1(getActivity());
        healthyQuestionsAdapter.setOnItemClickListener(this);
        healthyQuestionsAdapter.setDatas(datas);
        consultationAdapter = new ConsultationAdapter2_1(getContext());
        consultationAdapter.setDatas(datas1);
        consultationAdapter.setOnBuyServiceCallBack(new ConsultationAdapter2_1.OnBuyServiceCallBack() {
            @Override
            public void buyIt(Consult consult) {
                Intent intent = new Intent(getActivity(), ConsultDetailsActivity.class);
                intent.putExtra("id", consult.id);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getView().getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
//        recyclerView.setAdapter(healthyQuestionsAdapter);
        recyclerView.setAdapter(healthyQuestionsAdapter);
        recyclerView.setPager(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        fragment_consult_2_1.setPadding(0,systemBarConfig.getStatusBarHeight(),0,0);
    }

    @OnClick({
            R.id.tab_1,  //问健管师
            R.id.tab_2,  //问医生
            R.id.submit_question,  //提交问题
            R.id.back_to_tab_1,//返回
            R.id.title_messge//messge
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tab_1:
               chose(1);
                break;
            case R.id.tab_2:
                chose(2);
                break;
            case R.id.submit_question:
                if(choseType == 1){
                    RequestManager.getObject(Constens.HAVE_NUMBER, this, 	new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject data) {
                            try {
                                System.out.println(data.toString());
                                if(data.getInt("healthAdvisory") > 0){
                                    startActivityForResult(new Intent(getActivity(), HealthyQuestionsAddActivity.class), Constens.START_ACTIVITY_FOR_RESULT);
                                }else{
                                    new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("您的本月健康咨询次数已使用完毕，如有疑问请咨询您的慢病管理师！").setPositiveButton("确定", null).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                if(choseType == 2){
                    userInfo = UserInfoManager.getLoginUserInfo(getContext());
                    RequestManager.getObject(Constens.HAVE_NUMBER, getContext(), new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject data) {
                            try {
                                System.out.println(data.toString());
                                if (data.getInt("doctorAdvisory") > 0) {
                                    startActivityForResult(new Intent(getActivity(), NewConsultActivity.class), Constens.START_ACTIVITY_FOR_RESULT);
                                } else {
                                    new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("您的本月医师互动次数已使用完毕，如有疑问请咨询您的慢病管理师！").setPositiveButton("确定", null).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.back_to_tab_1:
                Main2_1.main2_1.changeFragment(0);
                break;
            case R.id.title_messge:
                getContext().startActivity(new Intent(context, MessageActivity.class));
                break;
        }
    }

    private void chose(int i) {
        choseType = i;
        if(i == 1){
            recyclerView.setAdapter(healthyQuestionsAdapter);
            tab_line_1.setVisibility(View.VISIBLE);
            tab_line_2.setVisibility(View.INVISIBLE);
            tab_1.setTextColor(getResources().getColor(R.color.tet_type_3));
            tab_2.setTextColor(getResources().getColor(R.color.tet_type_9));
        }else{
            recyclerView.setAdapter(consultationAdapter);
            tab_line_2.setVisibility(View.VISIBLE);
            tab_line_1.setVisibility(View.INVISIBLE);
            tab_1.setTextColor(getResources().getColor(R.color.tet_type_9));
            tab_2.setTextColor(getResources().getColor(R.color.tet_type_3));
        }
        onRefresh();
    }


    @Override
    public void onRefresh() {
        if(choseType == 1){
        RequestManager.getObject(Constens.HAVE_NUMBER, this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    System.out.println(data.toString());
                    consult_text.setText("免费咨询次数剩余："+data.getInt("healthAdvisory")+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        }else if(choseType == 2){
            RequestManager.getObject(Constens.HAVE_NUMBER, this, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject data) {
                    try {
                        System.out.println(data.toString());
                        consult_text.setText("咨询医生次数剩余："+data.getInt("doctorAdvisory")+"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pageNo = 1;
        isLooding = true;
        recyclerView.setRefreshing(false);
        loadNextPage();
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        String id = data.get("id").toString();
        Intent intent = new Intent(getActivity(), HealthyQuestionsDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        if(choseType == 1){
            Loading.bulid(getView().getContext(), null).show();
        RequestManager.getObject(String.format(Constens.HEALTH_ADVICES,pageNo,pageSize),getActivity(), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {

                try {
                    Loading.bulid(context, null).dismiss();
                    L.e(data.toString());
                    totalPage = data.getInt("pages");
                    JSONArray array = data.getJSONArray("results");
                    if (pageNo == 1) datas.clear();
                    for(int i = 0;i<array.length();i++){
                        Map<String,Object> item = new HashMap<String,Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
                        item.put("id", jsonObject.getInt("id"));
                        item.put("lastUpdateTime", jsonObject.getString("lastUpdateTime"));
                        item.put("content", jsonObject.getString("content"));
                        item.put("realName", jsonObject.getJSONObject("customer").getString("realName"));
                        item.put("iconUrl", jsonObject.getJSONObject("customer").getString("iconUrl"));
                        datas.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                healthyQuestionsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                if(pageNo <totalPage){
                    isLooding = true;
                    pageNo ++;
                }else{
                    recyclerView.setRefreshing(false);
                }

            }
        });

        }else if(choseType == 2){
            Loading.bulid(getView().getContext(), null).show();
            RequestManager.getObject(String.format(Constens.HEALTH_CONSULTS, "1", "1", pageNo, pageSize), getView().getContext(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject data) {
                    try {
                        Loading.bulid(context, null).dismiss();
                        L.e(data.toString());
                        totalPage = data.getInt("pages");
                        List<Consult> results = JSON.parseArray(data.getString("results"), Consult.class);
                        if (pageNo == 1) datas1.clear();
                        datas1.addAll(results);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    consultationAdapter.notifyDataSetChanged();
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
            if(resultCode == RESULT_OK){
                onRefresh();
            }
        }


    }
}
