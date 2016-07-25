package com.uyi.app.ui.personal.messagemanager;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.messagemanager.adapter.QueryCustomerAdapter;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 查询病人名称
 *
 * @author user
 */
@ContentView(R.layout.query_customer)
public class QueryCustomerActivity extends BaseActivity implements OnItemClickListener<Map<String, Object>>, Pager {


    @ViewInject(R.id.query_customer_search)
    private ImageView query_customer_search;

    @ViewInject(R.id.query_customer_names)
    private EditText query_customer_names;
    @ViewInject(R.id.query_customer_cancal)
    private TextView query_customer_cancal;

    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private QueryCustomerAdapter queryCustomerAdapter;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    String name;

    @Override
    protected void onInitLayoutAfter() {
        name = getIntent().getStringExtra("name");
        query_customer_names.setText(name);
        linearLayoutManager = new LinearLayoutManager(activity);
        queryCustomerAdapter = new QueryCustomerAdapter(activity);
        queryCustomerAdapter.setOnItemClickListener(this);
        queryCustomerAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setProgressView(R.layout.item_progress);
        recyclerView.setAdapter(queryCustomerAdapter);
        recyclerView.setPager(this);
        query_customer_search.performClick();
        query_customer_names.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    name = query_customer_names.getText().toString();
                    pageNo = 1;
                    isLooding = true;
                    datas.clear();
                    recyclerView.setRefreshing(false);
                    loadNextPage();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean startImmersiveStatus() {
        return false;
    }


    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {

    }

    @OnClick({R.id.query_customer_search, R.id.query_customer_cancal})
    public void click(View v) {
        if (v.getId() == R.id.query_customer_search) {
            name = query_customer_names.getText().toString();
            pageNo = 1;
            isLooding = true;
            datas.clear();
            recyclerView.setRefreshing(false);
            loadNextPage();
        } else {
            onBackPressed();
        }
    }

    @Override
    public boolean shouldLoad() {
        return isLooding;
    }

    @Override
    public void loadNextPage() {
        isLooding = false;
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.DOCTOR_QUERY_CUSTOMERS, name, pageNo, pageSize), activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                Loading.bulid(activity, null).dismiss();
                try {
                    totalPage = data.getInt("pages");
                    JSONArray array = data.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
                        item.put("id", jsonObject.getInt("id"));
                        item.put("realName", jsonObject.getString("realName"));
                        item.put("icon", jsonObject.getString("icon"));
                        item.put("age", jsonObject.getInt("age"));
                        item.put("gender", jsonObject.getString("gender"));
                        datas.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                queryCustomerAdapter.notifyDataSetChanged();
                if (pageNo <= totalPage) {
                    isLooding = true;
                    pageNo++;
                } else {
                    recyclerView.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        int id = (int) data.get("id");
        String realName = data.get("realName").toString();
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("realName", realName);
        setResult(RESULT_OK, intent);
        finish();
    }

}
