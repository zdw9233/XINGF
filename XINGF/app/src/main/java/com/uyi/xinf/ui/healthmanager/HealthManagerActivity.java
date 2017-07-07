package com.uyi.xinf.ui.healthmanager;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.doctor.xinf.R;
import com.uyi.xinf.Constens;
import com.uyi.xinf.adapter.BaseRecyclerAdapter;
import com.uyi.xinf.ui.custom.BaseActivity;
import com.uyi.xinf.ui.custom.DividerItemDecoration;
import com.uyi.xinf.ui.custom.EndlessRecyclerView;
import com.uyi.xinf.ui.custom.SystemBarTintManager;
import com.uyi.xinf.ui.custom.spiner.SpinerPopWindow;
import com.uyi.xinf.ui.dialog.Loading;
import com.uyi.xinf.ui.healthmanager.adapter.HealthManagerAdapter;
import com.uyi.xinf.utils.L;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.data;
import static com.uyi.doctor.xinf.R.id.et_jdb;
import static com.uyi.doctor.xinf.R.id.et_jdjg;
import static com.uyi.doctor.xinf.R.id.et_jdjgdh;
import static com.uyi.doctor.xinf.R.id.et_zrys;
import static com.uyi.doctor.xinf.R.id.et_zrysdh;
import static com.uyi.doctor.xinf.R.id.tt_cs;
import static com.uyi.doctor.xinf.R.id.tt_rllx;


/**
 * Created by ThinkPad on 2017/6/16.
 */
@ContentView(R.layout.activity_health_manager)
public class HealthManagerActivity extends BaseActivity implements EndlessRecyclerView.Pager,SwipeRefreshLayout.OnRefreshListener {
    @ViewInject( R.id.lay)
    public LinearLayout lay;
    @ViewInject( R.id.back)
    private LinearLayout back;
    @ViewInject( R.id.title)
    private TextView title;
    @ViewInject( R.id.add)
    private TextView add;

    @ViewInject( R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject( R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private HealthManagerAdapter healthManagerAdapter;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    @OnClick({ R.id.back,R.id.add})
    public void widgetClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.back:
                finish();
                break;
            case R.id.add:
                Intent intent = new Intent(activity, AddHealthManagerActivity.class);
                startActivityForResult(intent,10002);
                break;
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
    public boolean shouldLoad() {
        return isLooding;

    }


    @Override
    protected void onInitLayoutAfter() {
        title.setText("健康管理");
        linearLayoutManager = new LinearLayoutManager(this);
        healthManagerAdapter = new HealthManagerAdapter(this);
        healthManagerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>>() {
            @Override
            public void onItemClick(int position, Map<String, Object> data) {
                Intent intent = new Intent(activity, HealthManagerDetailsActivity.class);
                intent.putExtra("id", data.get("id").toString());
                startActivityForResult(intent,10002);
            }
        });
        healthManagerAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(healthManagerAdapter);
        recyclerView.setPager((EndlessRecyclerView.Pager) this);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        String uriAPI = "http://192.168.0.201:8080/app/api/customer/queryAllByUser?name=&page=1&pageSize=10";
//        new Thread(networkTask).start();
        onRefresh();
    }
    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            httpGet("http://192.168.0.201:8080/app/api/customer/queryAllByUser?name=&page=1&pageSize=10");
        }
    };
    public String httpGet( String httpUrl ){
        String result = "" ;
        try {
            BufferedReader reader = null;
            StringBuffer sbf = new StringBuffer() ;

            URL url  = new URL( httpUrl ) ;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection() ;
            //设置超时时间 10s
            connection.setConnectTimeout(10000);
            //设置请求方式
            connection.setRequestMethod( "GET" ) ;
            connection.connect();
            InputStream is = connection.getInputStream() ;
            reader = new BufferedReader(new InputStreamReader( is , "UTF-8" )) ;
            String strRead = null ;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.GET_BASIC_INFORMATION,"" , pageNo, pageSize), this, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                Loading.bulid(activity, null).dismiss();
                try {
                    L.d(TAG, "data == "+data.toString());
                    totalPage = data.getInt("pages");
                    if (pageNo == 1) datas.clear();
                    JSONArray array = data.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
                        item.put("id", jsonObject.getInt("id"));
                        item.put("realName", jsonObject.getString("realName"));
                        item.put("age", jsonObject.getString("age"));
                        item.put("disease", jsonObject.getString("disease"));
                        datas.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                healthManagerAdapter.notifyDataSetChanged();
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

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10002){
            if(resultCode == RESULT_OK){
                onRefresh();
            }
        }
    }
}
