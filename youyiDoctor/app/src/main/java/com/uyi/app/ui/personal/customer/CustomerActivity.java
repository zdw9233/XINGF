package com.uyi.app.ui.personal.customer;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.customer.adapter.CustomerAdapter2_1;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
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
 * 用户
 * @author user
 *
 */
@ContentView(R.layout.customer)
public class CustomerActivity extends BaseActivity implements OnRefreshListener, Pager,   OnEditorActionListener,BaseRecyclerAdapter.OnItemClickListener{
	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.search_layout_search) private ImageView search_layout_search;
	@ViewInject(R.id.search_layout_edit) private EditText search_layout_edit;
	@ViewInject(R.id.search_layout_search_text) private TextView search_layout_search_text;
	@ViewInject(R.id.peoplenum) private TextView peoplenum;
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	
	private LinearLayoutManager linearLayoutManager;
	private CustomerAdapter2_1 customerAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
	String num = "";
	String name = "";
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("用户").setTitleColor(getResources().getColor(R.color.blue));
		int type = UserInfoManager.getLoginUserInfo(activity).type;
		if(type == 2){
			peoplenum.setVisibility(View.GONE);
		}
		linearLayoutManager = new LinearLayoutManager(activity);
		customerAdapter = new CustomerAdapter2_1(activity);
		customerAdapter.setDatas(datas);
		customerAdapter.setOnItemClickListener(this);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(customerAdapter);
		recyclerView.setPager(this);
		 //设置刷新时动画的颜色，可以设置4个
		
		search_layout_edit.setOnEditorActionListener(this);
		onRefresh();
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
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
	public void loadNextPage() {
		if(!ValidationUtils.isNull(name)){
			try {
				name = URLEncoder.encode(name,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		isLooding = false;
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.DOCTOR_QUERY_CUSTOMERS,name,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Loading.bulid(activity, null).dismiss();
				L.e("data==",data.toString());
				try {
					totalPage = data.getInt("pages");
					if(data.has("total")){
						num = data.getString("total");
						peoplenum.setText("管理人数："+num);
					}

					JSONArray  array = data.getJSONArray("results");
					if (pageNo == 1)
						datas.clear();
					for(int i = 0;i<array.length();i++){
						Map<String,Object> item = new HashMap<String,Object>();
						JSONObject jsonObject = array.getJSONObject(i);
						item.put("id", jsonObject.getInt("id"));
						item.put("realName", jsonObject.getString("realName"));
						item.put("icon", jsonObject.getString("icon"));
						item.put("age", jsonObject.getInt("age"));
						item.put("gender", jsonObject.getString("gender"));
						item.put("status", jsonObject.getString("status"));
						datas.add(item);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				customerAdapter.notifyDataSetChanged();
				if(pageNo < totalPage){
					isLooding = true;
					pageNo ++;
				}else{
					recyclerView.setRefreshing(false);
				}
			}
		});
	}


	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		  if ((actionId == 0 || actionId == 3) && event != null) {
			  name = search_layout_edit.getText().toString();
			  onRefresh();
		  }
		return false;
	}
	
	@OnClick(R.id.search_layout_search_text)
	public void click(View view){
		 name = search_layout_edit.getText().toString();
		  onRefresh();
	}


	@Override
	public void onItemClick(int position, Object data) {
		T.show(activity,"1",0);
	}
}
