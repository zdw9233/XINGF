package com.uyi.app.ui.personal.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.consult.CustomInfoActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.dialog.MessageConform.MessageType;
import com.uyi.app.ui.dialog.MessageConform.OnMessageClick;
import com.uyi.app.ui.dialog.MessageConform.Result;
import com.uyi.app.ui.health.HealthDatabaseDetails;
import com.uyi.app.ui.personal.exclusive.ExclusiveDetailsActivity;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.TextView;


/**
 * 消息/公告 详情
 * @author user
 *
 */
@ContentView(R.layout.message_details)
public class MessageDetailsActivity extends BaseActivity implements OnClickListener, OnMessageClick {
	
	
	@ViewInject(R.id.headerView) private HeaderView headerView;
	@ViewInject(R.id.message_details_title) private TextView message_details_title;
	@ViewInject(R.id.message_details_time) private TextView message_details_time;
	@ViewInject(R.id.message_details_name) private TextView message_details_name;
	@ViewInject(R.id.message_details_content) private WebView message_details_content;
	@ViewInject(R.id.message_details_click) private TextView message_details_click;
	
	int id;//消息ID  公告ID
	int type;//类型   1为消息   2为公告 3为系统公告
	private MessageConform conform;
	
	
	/**
	 *  1: 用户加入健康团队
		2: 日常健康检查资料报警
		4: 专属咨询
		5: 消息
		6: 未设置收费标准
		7: 未设置报警标准
		8: 医生
	 */
	private int messageType;
	
	/**
	 *  messageType: 3,咨询/随访id
		messageType: 4 专属咨询id
	 */
	private int entityId;
	
	private Boolean isRead;
	

	@Override
	protected void onInitLayoutAfter() {
		id = getIntent().getIntExtra("id", 0);
		type = getIntent().getIntExtra("type",0);
		isRead = getIntent().getBooleanExtra("isRead", true);
		headerView.getRightLayout().removeAllViews();
		if(type != 3){
			TextView child = new TextView(activity);
			child.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			child.setTextColor(getResources().getColor(R.color.red));
			child.setTextSize(12);
			child.setText("删除");
			headerView.getRightLayout().addView(child);
		}
		headerView.getRightLayout().setOnClickListener(this);
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("详情内容").setTitleColor(getResources().getColor(R.color.blue)).showRight(true) ;
		Loading.bulid(activity, null).show();
		
		String url = String.format(Constens.ACCOUNT_MESSAGE, id,type);
		if(type == 3){
			url = String.format(Constens.COMMON_BULLETINS_DETAILS, id);
		}
		RequestManager.getObject(url, activity,new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					Loading.bulid(activity, null).dismiss();
					L.d(TAG, data.toString());
					if(type == 3){
						message_details_title.setText(data.getString("title"));
						message_details_time.setText(data.getString("publishTime"));
						message_details_name.setText("");
						message_details_content.loadDataWithBaseURL(null, "<html><head><style>body{width:100%};img{width:95%}</style></head></body>"+data.getString("content")+"</body></html>",  "text/html", "UTF-8", null);
					}else{
						message_details_title.setText(data.getString("title"));
						message_details_time.setText(data.getString("updateTime"));
						message_details_name.setText("发布者："+JSONObjectUtils.getString(data, "publisher"));
						message_details_content.loadDataWithBaseURL(null, "<html><head><style>body{width:100%};img{width:95%}</style></head></body>"+data.getString("content")+"</body></html>",  "text/html", "UTF-8", null);
						if(type == 1){
							messageType = 	data.getInt("messageType");
							if(messageType == 1  || messageType == 2 || messageType == 4 ){
								entityId = 	data.getInt("entityId");
								message_details_click.setVisibility(View.VISIBLE);
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		if(!isRead){//如果次消息未读 就标记为已读
			deleteOrRead(1);
		}
	}
	
	
	@OnClick(R.id.message_details_click)
	public void click(View view){
		if(messageType == 1  ){
			Intent intent = new Intent(activity, CustomInfoActivity.class);
			intent.putExtra("customerId", entityId);
			startActivity(intent);
		}else if( messageType == 2){
			Intent intent = new Intent(activity, HealthDatabaseDetails.class);
			intent.putExtra("isWarning", true);
			intent.putExtra("id", entityId+"");
			startActivity(intent);
			
		}else if(messageType == 3 ){
			Intent intent = new Intent(activity, ExclusiveDetailsActivity.class);
			intent.putExtra("id", entityId);
			startActivity(intent);
			
		}
	}
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public void onClick(View v) {
		if(conform == null){
			conform = new MessageConform(activity, MessageType.CONFORM);
		}
		conform.setTitle("提示").setContent("确认要删除吗?").setOnMessageClick(this);
		conform.show();
	}

	
	public void  deleteOrRead(final int operate){
		try {
			JSONObject  params = new  JSONObject();
			params.put("operate", operate);
			params.put("type", type);
			JSONArray array = new JSONArray();
			array.put(id);
			params.put("ids", array);
			if(operate == 2){
				Loading.bulid(activity, null).show();
			}
			RequestManager.postObject(Constens.ACCOUNT_MESSAGE_OPERATE, activity, params, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					if(operate == 2){
						Loading.bulid(activity, null).dismiss();
						setResult(RESULT_OK);
						finish();
					}
				}
			}, new RequestErrorListener() {
				public void requestError(VolleyError e) {
					if(operate == 2){
						Loading.bulid(activity, null).dismiss();
						setResult(RESULT_OK);
						finish();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void onClick(Result result, Object object) {
		if(result == Result.OK){
			deleteOrRead(2);
		}
	}

}
