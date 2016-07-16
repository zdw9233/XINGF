package com.uyi.app.ui.team.city;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.uyi.app.Constens;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.team.FragmentHealthTeam;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;



public class CityListActivity extends Activity implements TextWatcher
{
	private Context context_ = CityListActivity.this;
	private static LocationClient mLC = null;
	private ContactListViewImpl listview;
	   public static String cityName ;  //城市名    
       
	    private static Geocoder geocoder;   //此对象能通过经纬度来获取相应的城市等信息    
	private EditText searchBox;
	private  TextView cityna,allcity;
	private LinearLayout back;
	private String searchString;

	private Object searchLock = new Object();
	boolean inSearchMode = false;
	public static final String LOCATION_BCR = "location_bcr";
	private final static String TAG = "MainActivity2";

	List<ContactItemInterface>  list = new ArrayList<ContactItemInterface>();
	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;
	CityAdapter adapter;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_chose);

		allcity = (TextView) findViewById(R.id.allcity);
allcity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentHealthTeam.team_selected_caty.setText("全部城市");
				CityListActivity.this.finish();
				
			}
		});
		cityna = (TextView) findViewById(R.id.danqiancity);
		registerBroadCastReceiver();
		cityna.setText("定位中...");
		initLocation();
		requestLocationInfo();
				
		Loading.bulid(CityListActivity.this, "加载城市中...").show();
		RequestManager.getArray(Constens.HEALTH_CITYs,this, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray array) {
				
					System.out.println(array.toString()+"======================");	
				try
				{
					for(int i = 0; i < array.length(); i++)
					{JSONObject jsonObject = array.getJSONObject(i);
						String cityName = jsonObject.getString("name");
						int id = jsonObject.getInt("id");
						list.add(new CityItem(cityName, PinYin.getPinYin(cityName),id));
//						System.out.println(cityName.toString()+"======================");	
					
					}
				} 
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				curSearchTask = new SearchListTask();
				curSearchTask.execute(""); 
							}	
		});
		Loading.bulid(CityListActivity.this, "加载城市中...").dismiss();
		filterList = new ArrayList<ContactItemInterface>();
		adapter = new CityAdapter(this,R.layout.city_item, list);
		back = (LinearLayout) findViewById(R.id.back_city);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	CityListActivity.this.finish();
				
			}
		});
		listview = (ContactListViewImpl) this.findViewById(R.id.city_list);
		listview.setFastScrollEnabled(true);
		listview.setAdapter(adapter);
	


	listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView parent, View v, int position,
				long id)
		{
			List<ContactItemInterface> searchList = inSearchMode ? filterList
					: list;

//			Toast.makeText(context_,
//					searchList.get(position).getDisplayId()+"",
//					Toast.LENGTH_SHORT).show();
			FragmentHealthTeam.cityid = searchList.get(position).getDisplayId();
			FragmentHealthTeam.team_selected_caty.setText(searchList.get(position).getDisplayInfo());
			CityListActivity.this.finish();
		}
	});

	


		searchBox = (EditText) findViewById(R.id.team_city_edit_text);
		searchBox.addTextChangedListener(this);
	
	}
	
 
	@Override
	public void afterTextChanged(Editable s)
	{
		searchString = searchBox.getText().toString().trim().toUpperCase();

		if (curSearchTask != null
				&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED)
		{
			try
			{
				curSearchTask.cancel(true);
			} catch (Exception e)
			{
				Log.i(TAG, "Fail to cancel running search task");
			}

		}
		curSearchTask = new SearchListTask();
		curSearchTask.execute(searchString); 
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// do nothing
	}

	private class SearchListTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode)
			{
				// get all the items matching this
				for (ContactItemInterface item : list)
				{
					CityItem contact = (CityItem) item;

					boolean isPinyin = contact.getFullName().toUpperCase().indexOf(keyword) > -1;
					boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

					if (isPinyin || isChinese)
					{
						filterList.add(item);
					}

				}

			}
			return null;
		}

		protected void onPostExecute(String result)
		{

			synchronized (searchLock)
			{

				if (inSearchMode)
				{

					CityAdapter adapter = new CityAdapter(context_,R.layout.city_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else
				{
					CityAdapter adapter = new CityAdapter(context_,R.layout.city_item, list);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}
	
		/**
		 * 初始化广播，重写onReceive，处理接收回来的结果
		 */
		private BroadcastReceiver mLocationBcr = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String city = intent.getStringExtra("city");
				cityna.setText(city);
			}
		};
		/**
		 * 注册广播
		 */
		private void registerBroadCastReceiver() {
			IntentFilter intentToReceiverFilter = new IntentFilter();
			intentToReceiverFilter.addAction(LOCATION_BCR);
			registerReceiver(mLocationBcr, intentToReceiverFilter);
		}

		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			unregisterReceiver(mLocationBcr);// 注销广播
		}
		
		/**
		 * 初始化定位的内容
		 */
		private void initLocation() {
			mLC = new LocationClient(this);
			// 使用eclipse的SHA1值和该Project的包名去百度地图API那里注册一个key
			mLC.setAK("Aiy12UcER9wwkppqaINQZoA0");
			mLC.registerLocationListener(new MyLocationListener());
		}

		/**
		 * 发起定位
		 */
		public static void requestLocationInfo() {
			setLocationOption();
			if (mLC != null && !mLC.isStarted())
				mLC.start();
			if (mLC != null && mLC.isStarted())
				mLC.requestLocation();
		}

		/**
		 * 停止定位
		 */
		private void stopLocationClient() {
			if (mLC != null && mLC.isStarted())
				mLC.stop();
		}

		/**
		 * 设置定位相关参数
		 */
		private static void setLocationOption() {
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开GPS
			option.setCoorType("bd09ll");// 设置坐标类型
			option.setServiceName("com.baidu.location.service_v2.9");
			option.setPoiExtraInfo(true);
			option.setAddrType("all");// 详细地址
			option.setPoiNumber(10);
			option.disableCache(true);
			option.setPriority(LocationClientOption.NetWorkFirst);
			option.setPriority(LocationClientOption.GpsFirst); // gps
			mLC.setLocOption(option);
		}

		private class MyLocationListener implements BDLocationListener {

			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					sendBroadCast("定位失败");
					return;
				}
				sendBroadCast(location.getCity());
			}

			public void onReceivePoi(BDLocation poilocation) {
				if (poilocation == null) {
					sendBroadCast("定位失败");
					return;
				}
				sendBroadCast(poilocation.getCity());
			}
		}

		/**
		 * 得到结果发送广播
		 * 
		 * @param city
		 */
		private void sendBroadCast(String city) {
			stopLocationClient();// 停止定位
			Intent intent = new Intent(LOCATION_BCR);
			intent.putExtra("city", city);
			sendBroadcast(intent);
		}
}
