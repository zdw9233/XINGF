package com.uyi.app.ui.consult;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * 咨询详情
 * @author user
 *
 */
@ContentView(R.layout.consult_details)
public class ConsultDetailsActivity extends BaseActivity implements OnClickListener {
	
	@ViewInject(R.id.headerView) private HeaderView headerView; 
	@ViewInject(R.id.consult_details_layout) private ScrollView consult_details_layout; 
	
	@ViewInject(R.id.group_logo) private ImageView group_logo; 
	@ViewInject(R.id.group_name) private TextView group_name; 
	@ViewInject(R.id.group_info) private TextView group_info; 
	
	@ViewInject(R.id.consult_details_status) private TextView consult_details_status; 
	
	//补充资料的layout
	@ViewInject(R.id.consult_info_toas) private LinearLayout consult_info_toas; //提示需要补充资料
	@ViewInject(R.id.consult_details_buchongbaogao_layout) private LinearLayout consult_details_buchongbaogao_layout; //补充资料的视图
	@ViewInject(R.id.consult_details_buchongbaogao_image_layout) private FlowRadioGroup consult_details_buchongbaogao_image_layout; 
	@ViewInject(R.id.consult_add_buchongbaogao_image) private ImageView consult_add_buchongbaogao_image; 
	@ViewInject(R.id.consult_details_bingqing_desc) private EditText consult_details_bingqing_desc; 
	@ViewInject(R.id.consult_ditails_yaowutup_medical_txt) private TextView consult_ditails_yaowutup_medical_txt; 
	
	@ViewInject(R.id.consult_details_addinfos) private LinearLayout consult_details_addinfos; //补充资料的视图
	
	@ViewInject(R.id.consult_ditails_jc_layout) private FlowRadioGroup consult_ditails_jc_layout; 
	@ViewInject(R.id.consult_ditails_yaowutup_layout) private FlowRadioGroup consult_ditails_yaowutup_layout; 
	
	@ViewInject(R.id.consult_details_binqingmaioshu) private TextView consult_details_binqingmaioshu; 
	@ViewInject(R.id.consult_details_yaowutupian_image) private TextView consult_details_yaowutupian_image; 
	
	@ViewInject(R.id.consult_details_yijian) private LinearLayout consult_details_yijian; //意见容器
	//操作视图
	@ViewInject(R.id.consult_detail_suifang) private Button consult_detail_suifang; //随访
	@ViewInject(R.id.consult_detail_pingjia) private Button consult_detail_pingjia; //评价
	int id;
	
	private int status;//状态
	
	List<Bitmap> buchong_tu_pian = new ArrayList<Bitmap>();//补充报告图片
	
	
	int groupId;
	String groupName;
	String groupInfo;
	String groupLogo;
	
	@Override
	protected void onInitLayoutAfter() {
		consult_details_layout.setVisibility(View.INVISIBLE);
		headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("咨询详情").setTitleColor(getResources().getColor(R.color.blue));
		id = getIntent().getIntExtra("id", 0);
		if(id == 0){
			onBackPressed();
		}
		consult_detail_suifang.setOnClickListener(this);
		consult_detail_pingjia.setOnClickListener(this);
		consult_add_buchongbaogao_image.setOnClickListener(this);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		load();
	}
	
	
	public void load(){
		consult_info_toas.setVisibility(View.GONE);
		consult_details_buchongbaogao_layout.setVisibility(View.GONE);
		consult_detail_suifang.setVisibility(View.GONE);
		consult_details_status.setVisibility(View.GONE);
		consult_details_yijian.removeAllViews();
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.HEALTH_CONSULT, id), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					consult_details_layout.setVisibility(View.VISIBLE);
					Loading.bulid(activity, null).dismiss();
					JSONObject group = data.getJSONObject("group");
					groupId = group.getInt("id");
					groupName = group.getString("name");
					groupInfo = group.getString("info");
					groupLogo = group.getString("logo");
					
					group_name.setText(group.getString("name"));
					group_info.setText(group.getString("info"));
					ImageCacheManager.loadImage(group.getString("logo"), ImageCacheManager.getImageListener(group_logo,null, null));
					consult_details_binqingmaioshu.setText(data.getString("desc"));
					if(data.has("help")){
						consult_details_yaowutupian_image.setText(data.getString("help"));
					}
					
					status = data.getInt("status");
					boolean isCommented = false;
					boolean isDiscuss = false;
					if(data.has("isCommented") && data.getBoolean("isCommented")){
						isCommented = data.getBoolean("isCommented");
					} 
					if(data.has("isDiscuss") && data.getBoolean("isDiscuss")){
						isDiscuss = data.getBoolean("isDiscuss");
					}
					
					showViewByStatus(isCommented,isDiscuss);
					
					consult_ditails_jc_layout.removeAllViews();
					if(data.has("checkItems")){
						JSONArray checkItems = data.getJSONArray("checkItems");
						for(int i = 0;i<checkItems.length();i++){
							JSONObject jsonObject = checkItems.getJSONObject(i);
							String url = jsonObject.getString("url");
							final View view = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, consult_ditails_jc_layout,false);
							ImageView imageView = (ImageView)view.findViewById(R.id.new_consult_image_upload);
							ImageView new_consult_upload_delete = (ImageView)view.findViewById(R.id.new_consult_upload_delete);
							new_consult_upload_delete.setVisibility(View.GONE);
							imageView.setTag(url);
							imageView.setOnClickListener(ConsultDetailsActivity.this);
							ImageCacheManager.loadImage(url, ImageCacheManager.getImageListener(imageView, null, null));
							consult_ditails_jc_layout.addView(view);
						}
					}
					if(data.has("medicalPics")){
						consult_ditails_yaowutup_layout.removeAllViews();
						JSONArray medicalPics = data.getJSONArray("medicalPics");
						for(int i = 0;i<medicalPics.length();i++){
							JSONObject jsonObject = medicalPics.getJSONObject(i);
							String url = jsonObject.getString("url");
							final View view = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, consult_ditails_yaowutup_layout,false);
							ImageView imageView = (ImageView)view.findViewById(R.id.new_consult_image_upload);
							ImageView new_consult_upload_delete = (ImageView)view.findViewById(R.id.new_consult_upload_delete);
							new_consult_upload_delete.setVisibility(View.GONE);
							imageView.setTag(url);
							imageView.setOnClickListener(ConsultDetailsActivity.this);
							ImageCacheManager.loadImage(url, ImageCacheManager.getImageListener(imageView, null, null));
							consult_ditails_yaowutup_layout.addView(view);
						}
					}
					if(data.has("medicalTxt")){
						consult_ditails_yaowutup_medical_txt.setText(data.getString("medicalTxt"));
					}
					
					
					
					//患者补充的资料
					if(data.has("addReports")){
						consult_details_addinfos.removeAllViews();
						addInfo(consult_details_addinfos, data.getJSONArray("addReports")); 
					}
					
					String processType = null;
					if(data.has("processType")){
						int pro = data.getInt("processType");
						if(pro == 1){
							processType = "处理结果：直接处理";
								consult_details_status.setVisibility(View.GONE);
						}else if(pro == 2){
							processType = "处理结果：线下预约";
						}else if(pro == 3){
							processType = "处理结果：线上随访";
							if(data.has("isFollowuped") && !data.getBoolean("isFollowuped")){
								consult_detail_suifang.setText("立即线上随访");
								consult_detail_suifang.setVisibility(View.VISIBLE);
							} 
						}
					}
					//助理意见
					if(data.has("assistantAdvice")){
						addYjian(data.getJSONObject("assistantAdvice"),"助理意见",null);
					}
					
					
					boolean isDiscussAdvices = false;
					
					//专家意见
					if(data.has("expertAdvice")){
						isDiscussAdvices = true;
						JSONObject expertAdvice = data.getJSONObject("expertAdvice");
						if( expertAdvice.has("advice") ||  expertAdvice.has("addReportAdvice") ){
							isDiscussAdvices = true;
							addYjian(expertAdvice, "专家意见",processType);
						}
					}
					
					//采用的专家意见
					if(data.has("discussAdvices")){
						JSONArray  array = data.getJSONArray("discussAdvices");
						
						if(array != null){
							for(int i = 0;i<array.length();i++){
								if(i == 0 && !isDiscussAdvices){
									addYjian(array.getJSONObject(i),"专家意见",processType);
								}else{
									addYjian(array.getJSONObject(i),null,processType);
								}
							}
						}
						
					}
					
					
					
					
					boolean isSiscussHeaderAdvices = false;
					//资深专家意见
					if(data.has("headerAdvices")){
						JSONArray  array = data.getJSONArray("headerAdvices");
						if(array != null){
							isSiscussHeaderAdvices = true;
							for(int i = 0;i<array.length();i++){
								if(i == 0 ){
									addYjian(array.getJSONObject(i),"资深专家意见",null);
								}else{
									addYjian(array.getJSONObject(i),null,null);
								}
							}
						}
					}
					
					
					//采用的资深专家意见
					if(data.has("discussHeaderAdvices")){
						JSONArray  array = data.getJSONArray("discussHeaderAdvices");
						if(array != null){ 
							for(int i = 0;i<array.length();i++){
								if(i == 0 && !isSiscussHeaderAdvices){
									addYjian(array.getJSONObject(i),"资深专家意见",null);
								}else{
									addYjian(array.getJSONObject(i),null,null);
								}
							}
						}
					}
					
					if(data.has("processType") && data.has("isFollowup")){
						int pro = data.getInt("processType");
						if(pro == 3 && data.getBoolean("isFollowup") && !data.getBoolean("isFollowuped")){
							consult_detail_suifang.setVisibility(View.VISIBLE);
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 添加患者补充的资料
	 * @param layout
	 * @param jsonArray
	 */
	public void addInfo(LinearLayout layout,JSONArray jsonArray){
		if(jsonArray == null){
			return;
		}
		for(int i = 0;i < jsonArray.length(); i++){
			try {
				final View view = LayoutInflater.from(activity).inflate(R.layout.item_consult_add_info, consult_details_yijian,false);
				FlowRadioGroup right_images = (FlowRadioGroup)view.findViewById(R.id.right_images);
				TextView right_text = (TextView)view.findViewById(R.id.right_text); 
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.has("addReports")){
					JSONArray   imageArray = jsonObject.getJSONArray("addReports");
					for(int j = 0;j<imageArray.length();j++){
						String url = imageArray.getJSONObject(j).getString("url");
						final View imgView = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, consult_ditails_yaowutup_layout,false);
						ImageView imageView = (ImageView)imgView.findViewById(R.id.new_consult_image_upload);
						ImageView new_consult_upload_delete = (ImageView)imgView.findViewById(R.id.new_consult_upload_delete);
						new_consult_upload_delete.setVisibility(View.GONE);
						imageView.setTag(url);
						imageView.setOnClickListener(ConsultDetailsActivity.this);
						ImageCacheManager.loadImage(url, ImageCacheManager.getImageListener(imageView, null, null));
						right_images.addView(imgView);
					}
				}
				right_text.setText(jsonObject.getString("addTxt"));
				layout.addView(view);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	/**
	 * 添加意见
	 * @param jsonObject
	 * @throws JSONException 
	 */
	public void addYjian(JSONObject jsonObject,String title,String info) throws JSONException{
		if(jsonObject == null){
			return;
		}
		final View view = LayoutInflater.from(activity).inflate(R.layout.item_reply, consult_details_yijian,false);
		LinearLayout item_reply_title_layout = (LinearLayout)view.findViewById(R.id.item_reply_title_layout);
		TextView item_reply_info = (TextView)view.findViewById(R.id.item_reply_info);
		TextView item_reply_title = (TextView)view.findViewById(R.id.item_reply_title);
		ImageView item_reply_header = (ImageView)view.findViewById(R.id.item_reply_header);
		TextView item_reply_name = (TextView)view.findViewById(R.id.item_reply_name);
		TextView item_reply_pic = (TextView)view.findViewById(R.id.item_reply_pic);
		if(info != null){
			if(jsonObject.has("checkDate")){
				info += jsonObject.getString("checkDate").split(" ")[0];
			}
			item_reply_info.setText(info);
		}
		if(title == null){
			item_reply_title_layout.setVisibility(View.GONE);
		}
		item_reply_title.setText(title);
		
		item_reply_name.setText(jsonObject.getString("realName") );
		ImageCacheManager.loadImage(jsonObject.getString("icon"), ImageCacheManager.getImageListener(item_reply_header, null, null));
		if(jsonObject.has("advice") && !ValidationUtils.isNull(JSONObjectUtils.getString(jsonObject, "advice"))){
			item_reply_pic.setText(jsonObject.has("advice")?Html.fromHtml(jsonObject.getString("advice")):"" );
			consult_details_yijian.addView(view);
		}else if(jsonObject.has("addReportAdvice") && !ValidationUtils.isNull(JSONObjectUtils.getString(jsonObject, "addReportAdvice"))){
			item_reply_pic.setText(jsonObject.has("addReportAdvice")?Html.fromHtml(jsonObject.getString("addReportAdvice")):"" );
			consult_details_yijian.addView(view);
		}
	}
	
	
	
	
	
	/**
	 * 根据状态显示View
	 * 	1: 已提交
		2: 补充报告
		3: 医生正在处理
		4: 医生已处理
	 * @param isDiscuss 
	 * @param isCommented 
	 * @param status
	 */
	public void showViewByStatus(boolean isCommented, boolean isDiscuss){
		L.d(TAG, status+"");
		if(status == 1){
//			consult_details_status.setVisibility(View.VISIBLE);
//			consult_details_status.setText("已提交");
		}else if(status == 2){
			consult_info_toas.setVisibility(View.VISIBLE);
			consult_details_buchongbaogao_layout.setVisibility(View.VISIBLE);
			consult_detail_suifang.setText("提交");
			consult_detail_suifang.setVisibility(View.VISIBLE);
		}else if(status == 3){
			if(isDiscuss){
				consult_details_status.setVisibility(View.VISIBLE);
				consult_details_status.setText("医生提交到讨论组");
			}else{
				consult_details_status.setVisibility(View.VISIBLE);
				consult_details_status.setText("医生正在处理");
			}
		}else if(status == 4){
			if(isCommented){
				consult_details_status.setVisibility(View.GONE);
				consult_detail_suifang.setVisibility(View.GONE);
				consult_detail_pingjia.setVisibility(View.GONE);
			}else{
//				consult_detail_suifang.setVisibility(View.VISIBLE);
				consult_detail_pingjia.setVisibility(View.VISIBLE);
			}
		} 
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}
	
	//相册
	private void requestGallery() {
		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, Constens.PHOTO_REQUEST_GALLERY);
	}

	
	
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 1){//补充资料
				JSONObject params = (JSONObject) msg.obj;
				RequestManager.postObject(String.format(Constens.ACCOUNT_HEALTH_CONSULT_,id), activity, params, new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject data) {
						Loading.bulid(activity,null).dismiss();
						onResume() ;
					}
				}, new RequestErrorListener() {
					public void requestError(VolleyError e) {
						Loading.bulid(activity,null).dismiss();
						onResume() ;
					}
				});
			}
		}
	};
	
	
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.consult_detail_suifang){
			if(status == 2){//补充资料
					final String addTxt = consult_details_bingqing_desc.getText().toString();
					if(ValidationUtils.isNull(addTxt) && buchong_tu_pian.size() == 0){
						T.showLong(activity, "图片和文字至少要填写一样!");
						return;
					}
					
					Loading.bulid(activity, "正在上传...").show();
					Thread thread = new Thread(new Runnable() {
						public void run() {
							try {
								JSONArray array = new JSONArray();
								for(int i = 0;i<buchong_tu_pian.size();i++){
									JSONObject jsonObject = new JSONObject();
									jsonObject.put("binary", BitmapUtils.encode(buchong_tu_pian.get(i)));
									array.put(jsonObject);
								}
								JSONObject params = new JSONObject();
								if(!ValidationUtils.isNull(addTxt)){
									params.put("addTxt", addTxt);
								}
								if(array.length() >= 1){
									params.put("addReports", array);
								}
								handler.sendMessage(handler.obtainMessage(1, params));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					thread.start();
				
			}else if(status == 4){//立即随访
				Intent intent = new Intent(activity, NewConsultActivity.class);
				intent.putExtra("groupId",groupId);
				intent.putExtra("groupName",groupName);
				intent.putExtra("groupInfo",groupInfo);
				intent.putExtra("groupLogo",groupLogo);
				intent.putExtra("consultId",id);
				startActivity(intent );
				finish();
			}
		}else if(v.getId() == R.id.consult_add_buchongbaogao_image){//点击添加图片
			requestGallery();
		}else if(v.getId() == R.id.consult_detail_pingjia){//立即评价
			Intent intent = new Intent(activity, ConsultEvaluateActivity.class);
			intent.putExtra("consultId", id);
			startActivity(intent);
		}else{//点击图片
			String url = v.getTag().toString();
			if(!ValidationUtils.isNull(url)){
				Intent intent = new Intent(activity, ViewPagerImageActivity.class);
				intent.putExtra("imageUrl", new String[]{url});
				startActivity(intent);
			}
		}
	}
	
	public void showBuChongTuPianView(){
		consult_details_buchongbaogao_image_layout.removeAllViews();
		for(int i = 0;i<buchong_tu_pian.size();i++){
			final View view = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, consult_details_buchongbaogao_image_layout,false);
			ImageView imageView = (ImageView)view.findViewById(R.id.new_consult_image_upload);
			ImageView new_consult_upload_delete = (ImageView)view.findViewById(R.id.new_consult_upload_delete);
			new_consult_upload_delete.setTag(buchong_tu_pian.get(i));
			new_consult_upload_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					buchong_tu_pian.remove((Bitmap)v.getTag());
					consult_details_buchongbaogao_image_layout.removeView(view);
				}
			});
			imageView.setImageBitmap(buchong_tu_pian.get(i));
			consult_details_buchongbaogao_image_layout.addView(view);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 if(requestCode == Constens.PHOTO_REQUEST_GALLERY){
			if(resultCode == RESULT_OK){
				if (data != null) { 
            		try {
						Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
						buchong_tu_pian.add(bitmap);
						showBuChongTuPianView();
            		} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			}
		} 
	}
	
}
