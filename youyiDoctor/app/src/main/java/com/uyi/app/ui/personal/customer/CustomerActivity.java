package com.uyi.app.ui.personal.customer;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.customer.adapter.CustomerAdapter2_1;
import com.uyi.app.utils.L;
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
import java.util.List;
import java.util.Map;


/**
 * 用户
 * @author user
 *
 */
@ContentView(R.layout.customer)
public class CustomerActivity extends BaseActivity implements OnRefreshListener, Pager,   OnEditorActionListener,BaseRecyclerAdapter.OnItemClickListener<Map<String, Object>> ,AbstractSpinerAdapter.IOnItemSelectListener {

	@ViewInject(R.id.lay) private LinearLayout lay;
	@ViewInject(R.id.search_layout_search) private ImageView search_layout_search;
	@ViewInject(R.id.search_layout_edit) private EditText search_layout_edit;
	@ViewInject(R.id.search_layout_search_text) private TextView search_layout_search_text;
	@ViewInject(R.id.peoplenum) private TextView peoplenum;
	@ViewInject(R.id.paixu) private TextView paixu;
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	public static int customer;
	private LinearLayoutManager linearLayoutManager;
	private CustomerAdapter2_1 customerAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
	String num = "";
	String name = "";
	int pxnum1 = 0;
	int pxnum2 = 3;
	private SpinerPopWindow spinerPopWindow;

	
	@Override
	protected void onInitLayoutAfter() {
		int type = UserInfoManager.getLoginUserInfo(activity).type;
		if(type == 2){
			peoplenum.setVisibility(View.GONE);
		}
		spinerPopWindow = new SpinerPopWindow(activity);
		spinerPopWindow.setItemListener(this);
		spinerPopWindow.refreshData(zbString, 1);
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
		lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
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
		RequestManager.getObject(String.format(Constens.DOCTOR_HEALTH_MANAGER,name,pxnum1,pxnum2,pageNo,pageSize),activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Loading.bulid(activity, null).dismiss();
				L.e("data==",data.toString());
				try {
					totalPage = data.getInt("pages");
					if(data.has("total")){
						num = data.getString("total");
						peoplenum.setText(""+num);
					}

					JSONArray  array = data.getJSONArray("results");
					if (pageNo == 1)
						datas.clear();
					for(int i = 0;i<array.length();i++){
						Map<String,Object> item = new HashMap<String,Object>();
						JSONObject jsonObject = array.getJSONObject(i);
						item.put("id", jsonObject.getInt("id"));
						item.put("name", jsonObject.getString("name"));
						item.put("icon", jsonObject.getString("icon"));
						item.put("gender", jsonObject.getString("gender"));
						item.put("servicePackageName", jsonObject.getString("servicePackageName"));//服务包
						item.put("age", jsonObject.getInt("age"));
						item.put("consultationCount", jsonObject.getInt("consultationCount"));//咨询数
//						item.put("status", jsonObject.getString("status"));
						item.put("followUpTeam", jsonObject.getString("followUpTeam"));//三甲
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
	
	@OnClick({R.id.search_layout_search_text,R.id.back,R.id.paixu})
	public void click(View view){
		if(view.getId() == R.id.search_layout_search_text){
			name = search_layout_edit.getText().toString();
			onRefresh();
		}else if(view.getId() == R.id.back){
			finish();
		}else if(view.getId() == R.id.paixu) {
			spinerPopWindow.setWidth(paixu.getWidth());
			spinerPopWindow.refreshData(zbString, 0);
			spinerPopWindow.showAsDropDown(paixu);
		}

	}




	@Override
	public void onItemClick(int position, Map<String, Object> data) {
		customer = (int) data.get("id");
		Intent intent = new Intent(this, CustomerDetailsActivity.class);
		intent.putExtra("name", data.get("name").toString());
		intent.putExtra("icon", data.get("icon").toString());
		intent.putExtra("gender", data.get("gender").toString());
		intent.putExtra("servicePackageName", data.get("servicePackageName").toString());
		intent.putExtra("age", data.get("age").toString());
		intent.putExtra("consultationCount", data.get("consultationCount").toString());
		startActivity(intent);
	}

	@Override
	public void onItemClick(int pos) {
		switch (pos){
			case 0:
				pxnum1 = 0;
				pxnum2 = 3;
				paixu.setText(zbString.get(pos));
				onRefresh();
				break;
			case 1:
				pxnum1 = 3;
				pxnum2 = 0;
				paixu.setText(zbString.get(pos));
				onRefresh();
				break;
			case 2:
				pxnum1 = 0;
				pxnum2 = 1;
				paixu.setText(zbString.get(pos));
				onRefresh();
				break;
			case 3:
				pxnum1 = 0;
				pxnum2 = 2;
				paixu.setText(zbString.get(pos));
				onRefresh();
				break;

		}


	}
	List<String> zbString = new ArrayList<String>() {
		public List<String> $() {
			add("用户姓名倒序");
			add("用户姓名升序");
			add("更新时间倒序");
			add("更新时间升序");
			return this;
		}
	}.$();
}
