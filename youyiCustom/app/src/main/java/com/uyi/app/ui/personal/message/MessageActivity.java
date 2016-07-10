package com.uyi.app.ui.personal.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerItemDecoration;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.EndlessRecyclerView.Pager;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.HeaderView.OnTabChanage;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.dialog.MessageConform.MessageType;
import com.uyi.app.ui.dialog.MessageConform.OnMessageClick;
import com.uyi.app.ui.dialog.MessageConform.Result;
import com.uyi.app.ui.personal.message.adapter.MessageAdapter;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;


/**
 * 消息 / 公告
 * @author user
 *
 */
@ContentView(R.layout.message)
public class MessageActivity extends BaseActivity implements OnItemClickListener<Map<String, Object>>, Pager, OnRefreshListener, OnTabChanage, OnClickListener, OnMessageClick {

	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	
	@ViewInject(R.id.recyclerView) private EndlessRecyclerView recyclerView;
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	
	private LinearLayoutManager linearLayoutManager;
	private MessageAdapter messageAdapter;
	private ArrayList<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
	
	/**
	 * 当type为1, 此为消息Id
		当type为2, 此为公告Id
	 */
	private int type = 1;

	private MessageConform conform;
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.getRightLayout().removeAllViews();
		
		TextView child = new TextView(activity);
		child.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		child.setTextColor(getResources().getColor(R.color.red));
		child.setTextSize(12);
		child.setText("清空");
		headerView.getRightLayout().addView(child);
		headerView.getRightLayout().setOnClickListener(this); 
 
		headerView.showLeftReturn(true).showTab(true).showRight(true).setOnTabChanage(this);
 
		String[] str = getResources().getStringArray(R.array.message_notifaction);
		headerView.setTitleTabs(str);
		headerView.selectTabItem(1);
		
		linearLayoutManager = new LinearLayoutManager(activity);
		messageAdapter = new MessageAdapter(activity);
		messageAdapter.setOnItemClickListener(this);
		messageAdapter.setDatas(datas);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.setItemAnimator(new DefaultItemAnimator()); 
		recyclerView.setProgressView(R.layout.item_progress);
		recyclerView.setAdapter(messageAdapter);
		recyclerView.setPager(this);
		
		 //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(this); 
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
		datas.clear();
		recyclerView.setRefreshing(false);
		loadNextPage();
	}

	@Override
	public boolean shouldLoad() {
		return isLooding;
	}

	@Override
	public void loadNextPage() {
		isLooding = false;
		Loading.bulid(activity, null).show();
		
		String url = String.format(Constens.ACCOUNT_MESSAGES,type,"",pageNo,pageSize);
		
		if(type == 3){
			 url = String.format(Constens.COMMON_BULLETINS,pageNo,pageSize); 
		}
		
		RequestManager.getObject(url,activity, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				Loading.bulid(activity, null).dismiss();
				try {
					totalPage = data.getInt("pages");
					JSONArray  array = data.getJSONArray("results");
					for(int i = 0;i<array.length();i++){
						Map<String,Object> item = new HashMap<String,Object>();
						JSONObject jsonObject = array.getJSONObject(i);
						if(type == 3){
							item.put("id", jsonObject.getInt("id"));
							item.put("type", type);
							item.put("updateTime", jsonObject.getString("publishTime"));
							item.put("title", JSONObjectUtils.getString(jsonObject, "title") );
							item.put("isRead", true);
						}else{
							item.put("id", jsonObject.getInt("id"));
							item.put("type", type);
							item.put("updateTime", jsonObject.getString("updateTime"));
							item.put("title", JSONObjectUtils.getString(jsonObject, "title") );
							item.put("isRead", jsonObject.getBoolean("isRead"));
						}
						datas.add(item);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				messageAdapter.notifyDataSetChanged();
				swipeRefreshLayout.setRefreshing(false);
				if(pageNo <= totalPage){
					isLooding = true;
					pageNo ++;
				}else{
					recyclerView.setRefreshing(false);
				}
			}
		});
	}

	@Override
	public void onItemClick(int position, Map<String, Object> data) {
		Intent intent = new Intent(activity,MessageDetailsActivity.class);
		intent.putExtra("id", (int)data.get("id"));
		intent.putExtra("type",type);
		intent.putExtra("isRead",(Boolean)data.get("isRead"));
		data.put("isRead", true);
		startActivityForResult(intent,Constens.START_ACTIVITY_FOR_RESULT);
		messageAdapter.notifyItemChanged(position);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
			if(resultCode == RESULT_OK){
				onRefresh() ;
			}
		}
	}
	

	@Override
	public void onChanage(int postion) {
		if(postion != type){
			type = postion;
			onRefresh();
		}
	}

	@Override
	public void onClick(View v) {//删除
		if(datas.size() == 0){
			return;
		}
		if(conform == null){
			conform = new MessageConform(activity, MessageType.CONFORM);
		}
		conform.setTitle("提示").setContent("确认要清除吗?").setOnMessageClick(this);
		conform.show();
	}

	@Override
	public void onClick(Result result, Object object) {
		if(result == Result.OK){
			try {
				JSONObject  params = new  JSONObject();
				params.put("operate", 2);
				params.put("type", type);
				JSONArray array = new JSONArray();
				for(Map<String,Object> item : datas){
					array.put(item.get("id").toString());
				}
				params.put("ids", array);
				Loading.bulid(activity, null).show();
				RequestManager.postObject(Constens.ACCOUNT_MESSAGE_OPERATE, activity, params, new Listener<JSONObject>() {
					public void onResponse(JSONObject data) {
						Loading.bulid(activity, null).dismiss();
						onRefresh() ;
					}
				}, new RequestErrorListener() {
					public void requestError(VolleyError e) {
						Loading.bulid(activity, null).dismiss();
						onRefresh() ;
					}
				});
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}

}
