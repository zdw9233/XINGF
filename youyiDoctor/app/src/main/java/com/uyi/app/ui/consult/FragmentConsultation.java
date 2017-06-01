package com.uyi.app.ui.consult;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.model.bean.Consult;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.Main;
import com.uyi.app.ui.consult.adapter.ConsultationAdapter;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.HeaderView.OnTabChanage;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.L;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 所有咨询
 *
 * @author user
 */
public class FragmentConsultation extends BaseFragment implements Pager, OnRefreshListener, OnTabChanage, OnItemClickListener<Consult> {

    @ViewInject(R.id.headerView)
    private HeaderView headerView;

    private ArrayList<Consult> datas = new ArrayList<Consult>();
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;

    @ViewInject(R.id.search_layout_edit)
    private EditText search_layout_edit;
    @ViewInject(R.id.search_layout_search_text)
    private TextView search_layout_search_text;
    private String name = "";

    public Main main;

    private LinearLayoutManager linearLayoutManager;
    private ConsultationAdapter consultationAdapter;


    public boolean initLoad = true;//初始化加载


    private UserInfo userInfo;

    public FragmentConsultation() {
    }

    public FragmentConsultation setMain(Main main) {
        this.main = main;
        return this;
    }

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_consultation;
    }

    @Override
    protected void onInitLayoutAfter() {
        userInfo = UserInfoManager.getLoginUserInfo(context);
        if (userInfo == null) {
            return;
        }
        if (userInfo.type == 1) {
            String[] str = getResources().getStringArray(R.array.consultation);
            headerView.setTitleTabs(str);
            headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showTab(true).showRight(true).setOnTabChanage(this);
            ;
            headerView.selectTabItem(1);
        } else if (userInfo.type == 2) {
            headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon).showRight(true).showTitle(true).setTitle("所有互动").setTitleColor(getResources().getColor(R.color.blue));
        }
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        consultationAdapter = new ConsultationAdapter(getView().getContext());
        consultationAdapter.setOnItemClickListener(this);
        consultationAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getView().getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(consultationAdapter);
        recyclerView.setPager(this);

        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
//        onRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (initLoad) {
//            onInitLayoutAfter();
//        }
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onResume() {
        L.e("       super.onResume();;");
        super.onResume();
        headerView.showLeftHeader(true, UserInfoManager.getLoginUserInfo(context).icon);
        onRefresh();
    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        final Loading loading = Loading.bulid(main, null);
        loading.show();
        RequestManager.getObject(String.format(Constens.HEALTH_CONSULTS + "&name=" + name, "1", "1", pageNo, pageSize), getActivity(), new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject data) {
                try {
                    loading.dismiss();
                    initLoad = false;
                    totalPage = data.getInt("pages");
                    if (pageNo == 1)
                        datas.clear();
                    JSONArray array = data.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        Consult consult = new Consult();
                        JSONObject jsonObject = array.getJSONObject(i);
                        consult.id = jsonObject.getInt("id");
                        consult.icon = jsonObject.getString("icon");
                        consult.desc = jsonObject.getString("desc");
                        consult.updateTime = jsonObject.getString("updateTime");
                        consult.customerName = jsonObject.getString("customerName");
                        consult.status = jsonObject.getInt("status");
                        if (jsonObject.has("isCommented") && jsonObject.getBoolean("isCommented")) {
                            consult.isCommented = jsonObject.getBoolean("isCommented");
                        }
                        if (jsonObject.has("isDiscuss") && jsonObject.getBoolean("isDiscuss")) {
                            consult.isDiscuss = jsonObject.getBoolean("isDiscuss");
                        }
//						map.put("isDiscuss", jsonObject.getBoolean("isCommented"));
                        datas.add(consult);
                    }
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
//					T.showLong(UYIApplication.getContext(),R.string.looding_all);
                }
            }
        });
    }


    @OnClick(R.id.search_layout_search_text)
    public void search(View view) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        name = search_layout_edit.getText().toString();
        pageNo = 1;
        isLooding = true;
        recyclerView.setRefreshing(false);
        loadNextPage();
    }

    @Override
    public void onChanage(int postion) {
        if (postion == 2) {
            main.replaceView(4);
        } else if (postion == 3) {
            main.replaceView(6);
        }

    }

    @Override
    public void onItemClick(int position, Consult data) {
        Intent intent = new Intent(context, ConsultDetailsActivity.class);
        intent.putExtra("id", data.id);
        startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.START_ACTIVITY_FOR_RESULT) {
            if (resultCode == -1) {
                onRefresh();
            }
        }
    }
}
