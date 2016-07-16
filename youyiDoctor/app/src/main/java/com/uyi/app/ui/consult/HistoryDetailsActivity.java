package com.uyi.app.ui.consult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.UYIUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestManager;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 历史咨询详情
 * @author user
 *
 */
@ContentView(R.layout.history_details)
public class HistoryDetailsActivity extends BaseActivity implements OnClickListener {
	
	@ViewInject(R.id.headerView) private HeaderView headerView; 
	@ViewInject(R.id.constom_info_logo) private RoundedImageView constom_info_logo; 
	@ViewInject(R.id.constom_info_name) private TextView constom_info_name; 
	@ViewInject(R.id.constom_info_gender) private TextView constom_info_gender; 
	@ViewInject(R.id.constom_info_chushengriqi) private TextView constom_info_chushengriqi; 
	
	@ViewInject(R.id.constom_info_chak) private TextView constom_info_chak; 
	@ViewInject(R.id.history_details_close) private ImageView history_details_close; 
	
	@ViewInject(R.id.consult_details_binqingmaioshu) private TextView consult_details_binqingmaioshu; 
	@ViewInject(R.id.consult_ditails_jc_layout) private FlowRadioGroup  consult_ditails_jc_layout; 
	@ViewInject(R.id.consult_ditails_yaowutup_layout) private FlowRadioGroup  consult_ditails_yaowutup_layout; 
	@ViewInject(R.id.consult_details_yaowuttext) private TextView  consult_details_yaowuttext; 
	
	@ViewInject(R.id.consult_details_yijian) private LinearLayout  consult_details_yijian; 
	
	
	int customerId;//患者ID
	int id;
	
	@Override
	protected void onInitLayoutAfter() {
		id = getIntent().getIntExtra("id", 0);
		load();
	}
	
	
	
	@OnClick({R.id.constom_info_chak,R.id.history_details_close})
	public void click(View v){
		if(v.getId() == R.id.history_details_close){
			onBackPressed();
		}else{
			Intent intent = new Intent(activity, CustomInfoActivity.class);
			intent.putExtra("customerId", customerId);
			startActivity(intent);
		}
	}

	public void load(){
		RequestManager.getObject(String.format(Constens.HEALTH_CONSULT, id), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					Loading.bulid(activity, null).dismiss();
					JSONObject customer = data.getJSONObject("customer");
					ImageCacheManager.loadImage(customer.getString("icon"), ImageCacheManager.getImageListener(constom_info_logo, null, null));
					constom_info_name.setText(customer.getString("name"));
					constom_info_gender.setText("性别："+UYIUtils.convertGender(customer.getString("gender")));
					constom_info_chushengriqi.setText("出生日期："+customer.getString("birthday"));
					
					customerId = customer.getInt("id");
					
					consult_details_binqingmaioshu.setText(data.getString("desc"));
					
					
					if(data.has("checkItems")){//已做检查项目
						JSONArray array = data.getJSONArray("checkItems");
						for(int i = 0;i<array.length();i++){
							addXiangmu(consult_ditails_jc_layout, array.getJSONObject(i));
						}
					}
					if(data.has("medicalPics")){//药物图片
						JSONArray array = data.getJSONArray("medicalPics");
						for(int i = 0;i<array.length();i++){
							addXiangmu(consult_ditails_yaowutup_layout, array.getJSONObject(i));
						}
					}
					
					if(data.has("addReports")){//补充的资料
						JSONArray array = data.getJSONArray("addReports");
						for(int i = 0;i<array.length();i++){
							addBuChongYijian(consult_details_yijian, array.getJSONObject(i));
						}
					}
				 
					
					
					if(data.has("medicalTxt")){
						consult_details_yaowuttext.setText(data.getString("medicalTxt"));
					}
					
					
					
//					//患者补充的资料
//					if(data.has("addReports")){
//						consult_details_addinfos.removeAllViews();
//						addInfo(consult_details_addinfos, data.getJSONArray("addReports")); 
//					}
					
					String processType = null;
					if(data.has("processType")){
						int pro = data.getInt("processType");
						if(pro == 1){
							processType = "处理结果：直接处理";
						}else if(pro == 2){
							processType = "处理结果：线下预约";
						}else if(pro == 3){
							processType = "处理结果：线上随访";
						}
					}
					
					//助理意见
					if(data.has("assistantAdvice")){
						addYijian(data.getJSONObject("assistantAdvice"),"助理意见",null);
					}
					
					
					boolean isDiscussAdvices = false;
					
					//专家意见
					if(data.has("expertAdvice")){
						isDiscussAdvices = true;
						JSONObject expertAdvice = data.getJSONObject("expertAdvice");
						if( expertAdvice.has("advice") ||  expertAdvice.has("addReportAdvice") ){
							isDiscussAdvices = true;
							addYijian(expertAdvice, "专家意见",processType);
						}
					}
					
					//采用的专家意见
					if(data.has("discussAdvices")){
						JSONArray  array = data.getJSONArray("discussAdvices");
						
						if(array != null){
							for(int i = 0;i<array.length();i++){
								if(i == 0 && !isDiscussAdvices){
									addYijian(array.getJSONObject(i),"专家意见",processType);
								}else{
									addYijian(array.getJSONObject(i),null,processType);
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
									addYijian(array.getJSONObject(i),"资深专家意见",null);
								}else{
									addYijian(array.getJSONObject(i),null,null);
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
									addYijian(array.getJSONObject(i),"资深专家意见",null);
								}else{
									addYijian(array.getJSONObject(i),null,null);
								}
							}
						}
					}
					
					
					if(data.has("help")){
						consult_details_yaowuttext.setText(data.getString("help"));
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
						imageView.setOnClickListener(HistoryDetailsActivity.this);
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
	 * 补充的资料
	 * @param consult_ditails_yaowutup_layout
	 * @param jsonObject
	 */
	private void addBuChongYijian(LinearLayout rootLayout, JSONObject jsonObject) {
		View view = LayoutInflater.from(activity).inflate(R.layout.item_buchongbaogao, rootLayout, false);
		FlowRadioGroup consult_ditails_buchong_layout = (FlowRadioGroup)view.findViewById(R.id.consult_ditails_buchong_layout);
		TextView consult_ditails_buchong_miaoshu = (TextView)view.findViewById(R.id.consult_ditails_buchong_miaoshu);
		try {
			consult_ditails_buchong_miaoshu.setText(jsonObject.getString("addTxt"));
			
			JSONArray addReports = jsonObject.getJSONArray("addReports");
			for(int i = 0;i<addReports.length();i++){
				addXiangmu(consult_ditails_buchong_layout, addReports.getJSONObject(i));
			}
			rootLayout.addView(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加助理意见
	 * @param rootLayout
	 * @param object
	 */
	private void addYijian(JSONObject object,String title,String info){
		if(object == null){
			return;
		}
		try {
			final View view = LayoutInflater.from(activity).inflate(R.layout.item_reply, consult_details_yijian,false);
			LinearLayout item_reply_title_layout = (LinearLayout)view.findViewById(R.id.item_reply_title_layout);
			TextView item_reply_info = (TextView)view.findViewById(R.id.item_reply_info);
			TextView item_reply_title = (TextView)view.findViewById(R.id.item_reply_title);
			ImageView item_reply_header = (ImageView)view.findViewById(R.id.item_reply_header);
			TextView item_reply_name = (TextView)view.findViewById(R.id.item_reply_name);
			TextView item_reply_pic = (TextView)view.findViewById(R.id.item_reply_pic);
			if(info != null){
				if(object.has("checkDate")){
					
						info += object.getString("checkDate").split(" ")[0];
				
				}
				item_reply_info.setText(info);
			}
			if(title == null){
				item_reply_title_layout.setVisibility(View.GONE);
			}
			item_reply_title.setText(title);
			
			item_reply_name.setText(object.getString("realName") );
			item_reply_pic.setText(object.has("advice")?Html.fromHtml(object.getString("advice")):"" );
			ImageCacheManager.loadImage(object.getString("icon"), ImageCacheManager.getImageListener(item_reply_header, null, null));
			if(object.has("advice")){
				consult_details_yijian.addView(view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加图片
	 * @param rootLayout
	 * @param object
	 */
	public void addXiangmu(FlowRadioGroup rootLayout,JSONObject object){
		try {
			String url = object.getString("url");
			View view = LayoutInflater.from(activity).inflate(R.layout.item_imageview, rootLayout, false);
			ImageView img = (ImageView)view.findViewById(R.id.item_imageview);
			ImageCacheManager.loadImage(url, ImageCacheManager.getImageListener(img, null, null));
			img.setTag(object.getString("originalUrl"));
			img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity, ViewPagerImageActivity.class);
					intent.putExtra("imageUrl", new String[]{v.getTag().toString()});
					startActivity(intent);
				}
			});
			rootLayout.addView(view);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {

	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
