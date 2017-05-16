package com.uyi.app.ui.consult;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.discuss.DiscussGroupDetailsActivity;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.UYIUtils;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 咨询详情
 * @author user
 *
 */
@ContentView(R.layout.consult_details)
public class ConsultDetailsActivity extends BaseActivity implements OnClickListener {
	@ViewInject(R.id.lay) private LinearLayout lay;
	@ViewInject(R.id.back) private LinearLayout back;
	@ViewInject(R.id.consult_details_layout) private ScrollView consult_details_layout;
	
	@ViewInject(R.id.consult_details_logo) private RoundedImageView consult_details_logo; 
	@ViewInject(R.id.consult_details_name) private TextView consult_details_name; 
	@ViewInject(R.id.consult_details_gender) private TextView consult_details_gender; 
	@ViewInject(R.id.consult_details_chushengriqi) private TextView consult_details_chushengriqi; 
	
	
	@ViewInject(R.id.consult_details_select_info) private TextView consult_details_select_info; //查看详细资料
	@ViewInject(R.id.consult_details_select_distory) private TextView consult_details_select_distory; //查看历史咨询
	
	@ViewInject(R.id.consult_details_binqingmaioshu) private TextView consult_details_binqingmaioshu; 
	@ViewInject(R.id.consult_ditails_jc_layout) private FlowRadioGroup  consult_ditails_jc_layout; 
	@ViewInject(R.id.consult_ditails_yaowutup_layout) private FlowRadioGroup  consult_ditails_yaowutup_layout; 
	@ViewInject(R.id.consult_details_yaowuttext) private TextView  consult_details_yaowuttext; 
	@ViewInject(R.id.consult_ditails_yaowutup_medical_txt) private TextView consult_ditails_yaowutup_medical_txt; 
	@ViewInject(R.id.consult_details_yijian) private LinearLayout  consult_details_yijian; 

	
	@ViewInject(R.id.consult_details_addinfos) private LinearLayout consult_details_addinfos; //补充资料的视图
	
	@ViewInject(R.id.consult_ditails_zhijiechuli_layout) private LinearLayout  consult_ditails_zhijiechuli_layout; //处理layout
	
	//补充资料的layout
	@ViewInject(R.id.consult_ditails_buchong_layout) private FlowRadioGroup  consult_ditails_buchong_layout; 
	@ViewInject(R.id.consult_ditails_buchong_miaoshu) private TextView  consult_ditails_buchong_miaoshu; 
	
	//处理方式
	@ViewInject(R.id.consult_ditails_zhijiechuli) private TextView  consult_ditails_zhijiechuli; 
	@ViewInject(R.id.consult_ditails_shuifang) private TextView  consult_ditails_shuifang; 
	@ViewInject(R.id.consult_ditails_xianxiajiancha) private TextView  consult_ditails_xianxiajiancha; 
	@ViewInject(R.id.consult_ditails_tijiaotaolunzu) private TextView  consult_ditails_tijiaotaolunzu; 
	
	//返回患者
	@ViewInject(R.id.consult_ditails_fanhuihuanzhe) private TextView  consult_ditails_fanhuihuanzhe; 
	
	
	@ViewInject(R.id.consult_ditails_jianchashijian_layout) private LinearLayout  consult_ditails_jianchashijian_layout; 
	@ViewInject(R.id.consult_ditails_jianchashijian) private TextView  consult_ditails_jianchashijian; 
	@ViewInject(R.id.consult_details_jianchaneirong_text) private TextView  consult_details_jianchaneirong_text; 
	@ViewInject(R.id.consult_details_chuliyijian_edit) private EditText  consult_details_chuliyijian_edit; 
	@ViewInject(R.id.consult_details_submit) private Button  consult_details_submit; 
	
	/**
	 * 资深专家补充意见
	 */
	@ViewInject(R.id.consult_ditails_zshengbuchong_layout) private LinearLayout  consult_ditails_zshengbuchong_layout; 
	@ViewInject(R.id.consult_details_buchong_edit) private EditText  consult_details_buchong_edit; 
	@ViewInject(R.id.consult_details_buchong_submit) private Button  consult_details_buchong_submit; 
	
	@ViewInject(R.id.consult_details_discuss) private Button  consult_details_discuss;//资深专家查看讨论组 
	
	/**
	 * 处理方式
	 * 3：直接处理
	 * 4：随访
	 * 5：线下检查
	 * 6：提交讨论组
	 */
	int chuliIndex = 3;
	
	int customerId;//患者ID
 
	int id;
	
	private String checkDate;//随访 线下检查时间
	private String advice;//处理意见
	
	List<Bitmap> buchong_tu_pian = new ArrayList<Bitmap>();//补充报告图片
	
	private UserInfo userinfo;
	
	@Override
	protected void onInitLayoutAfter() {
		consult_details_layout.setVisibility(View.INVISIBLE);
		id = getIntent().getIntExtra("id", 0);
		if(id == 0){
			onBackPressed();
		}
		chuliLayout(3);
		userinfo = UserInfoManager.getLoginUserInfo(activity);
		load();
	}
	
	
	
	public void load(){
		Loading.bulid(activity, null).show();
		RequestManager.getObject(String.format(Constens.HEALTH_CONSULT, id), activity, new Listener<JSONObject>() {
			public void onResponse(JSONObject data) {
				try {
					L.e("CONSULT",data.toString());
					Loading.bulid(activity, null).dismiss();
					consult_details_layout.setVisibility(View.VISIBLE);
					JSONObject customer = data.getJSONObject("customer");
					ImageCacheManager.loadImage(customer.getString("icon"), ImageCacheManager.getImageListener(consult_details_logo, null, null));
					consult_details_name.setText(customer.getString("name"));
					consult_details_gender.setText("性别："+UYIUtils.convertGender(customer.getString("gender")));
					consult_details_chushengriqi.setText("出生日期："+customer.getString("birthday"));
					
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
						}else if(pro == 2){
							processType = "处理结果：线下预约";
						}else if(pro == 3){
							processType = "处理结果：线上随访";
						}
					}
					
					//助理意见
					if(data.has("assistantAdvice")){
						addYijian(data.getJSONObject("assistantAdvice"),"慢病管理师意见",null,"advice");
					}
					
					
					boolean isDiscussAdvices = false;
					
					//专家意见
					if(data.has("expertAdvice")){
						JSONObject expertAdvice = data.getJSONObject("expertAdvice");
						if(expertAdvice.has("addReportAdvice")){
							isDiscussAdvices = true;
							addYijian(expertAdvice, "专家意见",processType,"addReportAdvice");
						}
						if( expertAdvice.has("advice")){
							if(!isDiscussAdvices){
								addYijian(expertAdvice, "专家意见",processType,"advice");
							}else{
								addYijian(expertAdvice, null,processType,"advice");
							}
							isDiscussAdvices = true;
						}
					}
					
					
					
					
					
					//采用的专家意见
					if(data.has("discussAdvices")){
						JSONArray  array = data.getJSONArray("discussAdvices");
						
						if(array != null){
							for(int i = 0;i<array.length();i++){
								if(i == 0 && !isDiscussAdvices){
									addYijian(array.getJSONObject(i),"专家意见",processType,"advice");
								}else{
									addYijian(array.getJSONObject(i),null,processType,"advice");
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
									addYijian(array.getJSONObject(i),"资深专家意见",null,"advice");
								}else{
									addYijian(array.getJSONObject(i),null,null,"advice");
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
									addYijian(array.getJSONObject(i),"资深专家意见",null,"advice");
								}else{
									addYijian(array.getJSONObject(i),null,null,"advice");
								}
							}
						}
					}
					
					
					if(data.has("help")){
						consult_details_yaowuttext.setText(data.getString("help"));
					}
					
					boolean isDiscuss = false;
					if(data.has("isDiscuss")){
						isDiscuss =  data.getBoolean("isDiscuss");//是否提交到讨论组
					}
					boolean isCommented = false;
					if(data.has("isCommented")){
						isCommented =  data.getBoolean("isCommented");//是否已评论
					}
					if(userinfo.type == 2){//资深专家
						consult_ditails_zshengbuchong_layout.setVisibility(View.VISIBLE);
						if(isDiscuss){
							consult_details_discuss.setVisibility(View.VISIBLE);
						}
					}else{//专家
						consult_ditails_zshengbuchong_layout.setVisibility(View.GONE);
						int status = data.getInt("status");
						if(data.has("isMy") && data.getBoolean("isMy")){
//							if(!data.has("processType") && !isCommented && !isDiscuss){
							if(status == 3){
								consult_ditails_zhijiechuli_layout.setVisibility(View.VISIBLE);
								if(ValidationUtils.equlse(UYIUtils.convertConsultationStatus(status, isCommented, isDiscuss), "医生提交到讨论组")){
									consult_ditails_tijiaotaolunzu.setVisibility(View.GONE);
								}
							}
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
	 * 补充的资料
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
	 * @param object
	 */
	private void addYijian(JSONObject object,String title,String info,String advice){
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
			item_reply_pic.setText(object.has(advice)?Html.fromHtml(object.getString(advice)):"" );
			ImageCacheManager.loadImage(object.getString("icon"), ImageCacheManager.getImageListener(item_reply_header, null, null));
			if(object.has(advice)){
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
	
	
	
	/**
	 * 资深专家补充意见
	 * @param view
	 */
	@OnClick(R.id.consult_details_buchong_submit)
	public void buchongClick(View view){
		String content = consult_details_buchong_edit.getText().toString();
		if(ValidationUtils.isNull(content)){
			T.showShort(activity, "请输入意见");
			return;
		}
		advice = content;
		 submit(7);
	}
	
	
	@OnClick({R.id.consult_ditails_zhijiechuli,
		R.id.consult_ditails_shuifang,
		R.id.consult_ditails_xianxiajiancha,
		R.id.consult_ditails_tijiaotaolunzu,
		R.id.consult_details_select_info,
		R.id.consult_details_select_distory,
		R.id.consult_ditails_fanhuihuanzhe,
		R.id.consult_ditails_jianchashijian,
		R.id.consult_details_discuss,
		R.id.consult_details_submit,
	R.id.back})
	public void oclick(View v){
		if(v.getId() == R.id.back){
			finish();
		}
		else if(v.getId() == R.id.consult_ditails_zhijiechuli){
			chuliLayout(3);
		}else if(v.getId() == R.id.consult_ditails_shuifang){
			chuliLayout(5);
		}else if(v.getId() == R.id.consult_ditails_xianxiajiancha){
			chuliLayout(4);
		}else if(v.getId() == R.id.consult_ditails_tijiaotaolunzu){
			chuliLayout(6);
		}else if(v.getId() == R.id.consult_details_select_info){//查看详细资料
			Intent intent = new Intent(activity, CustomInfoActivity.class);
			intent.putExtra("customerId", customerId);
			startActivity(intent);
		}else if(v.getId() == R.id.consult_details_select_distory){//查看历史咨询
			Intent intent = new Intent(activity, HistoryConsultActivity.class);
			intent.putExtra("customerId", customerId);
			startActivityForResult(intent,Constens.START_ACTIVITY_FOR_RESULT);
		}else if(v.getId() == R.id.consult_ditails_jianchashijian){
			Intent intent = new Intent(activity, DatePickerActivity.class);
			intent.putExtra("currentDate", consult_ditails_jianchashijian.getText().toString().trim());
			startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT_TWO);
		}else if(v.getId() == R.id.consult_ditails_fanhuihuanzhe){
//			submit(1);
			chuliLayout(8);
			
		}else if(v.getId() == R.id.consult_details_submit){
			
			checkDate = consult_ditails_jianchashijian.getText().toString();
			
			if(chuliIndex == 4 || chuliIndex == 5){
				if(ValidationUtils.isNull(checkDate)){
					T.showShort(activity, "请选择时间!");
					return;
				}
			}
			
			
			advice = consult_details_chuliyijian_edit.getText().toString();
			if(ValidationUtils.isNull(advice)){
				T.showShort(activity, "请输入意见!");
				return;
			}

			
			
			submit(chuliIndex);
		}else if(v.getId() == R.id.consult_details_discuss){
			Intent intent = new Intent(activity, DiscussGroupDetailsActivity.class);
			intent.putExtra("id", id);
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
	
	
	/**
	 * 处理
	 */
	public void submit(int type){
		try {
			Loading.bulid(activity, null).show();
			JSONObject params = new JSONObject();
			if(type == 8){//返回患者
				type = 1;
			}
			params.put("type", type);
			if(!ValidationUtils.isNull(checkDate)){
				params.put("checkDate", checkDate);
			}
			if(!ValidationUtils.isNull(advice)){
				params.put("advice", advice);
			}
			RequestManager.postObject(String.format(Constens.DOCTOR_PROCESS_HEALTH_CONSULT_, id), activity, params, null, new RequestErrorListener() {
				public void requestError(VolleyError e) {
					Loading.bulid(activity, null).dismiss();
					if(e.networkResponse != null){
						T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
					}else{
						setResult(RESULT_OK);
						finish();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public void chuliLayout(int chuliIndex){
		this.chuliIndex = chuliIndex;
		consult_ditails_zhijiechuli.setTextColor(getResources().getColor(R.color.list_item_content_color));
		consult_ditails_shuifang.setTextColor(getResources().getColor(R.color.list_item_content_color));
		consult_ditails_xianxiajiancha.setTextColor(getResources().getColor(R.color.list_item_content_color));
		consult_ditails_tijiaotaolunzu.setTextColor(getResources().getColor(R.color.list_item_content_color));
		
		consult_ditails_zhijiechuli.setBackgroundResource( R.color.content_background);
		consult_ditails_shuifang.setBackgroundResource( R.color.content_background);
		consult_ditails_xianxiajiancha.setBackgroundResource( R.color.content_background);
		consult_ditails_tijiaotaolunzu.setBackgroundResource( R.color.content_background);
		
		consult_ditails_jianchashijian_layout.setVisibility(View.GONE);
		
		switch (this.chuliIndex) {
		case 3:
			consult_ditails_fanhuihuanzhe.setBackgroundResource(R.color.red);
			consult_ditails_zhijiechuli.setTextColor(getResources().getColor(R.color.white));
			consult_ditails_zhijiechuli.setBackgroundResource(R.color.blue); 
			consult_details_jianchaneirong_text.setText("处理意见");
			break;
		case 5:
			consult_ditails_fanhuihuanzhe.setBackgroundResource(R.color.red);
			consult_ditails_shuifang.setTextColor(getResources().getColor(R.color.white));
			consult_ditails_shuifang.setBackgroundResource(R.color.blue);
			consult_ditails_jianchashijian_layout.setVisibility(View.VISIBLE);
			consult_ditails_jianchashijian.setHint("线上随访时间");
			consult_details_jianchaneirong_text.setText("线上随访建议");
			break;
		case 4:
			consult_ditails_fanhuihuanzhe.setBackgroundResource(R.color.red);
			consult_ditails_xianxiajiancha.setTextColor(getResources().getColor(R.color.white));
			consult_ditails_xianxiajiancha.setBackgroundResource(R.color.blue);
			consult_ditails_jianchashijian_layout.setVisibility(View.VISIBLE);
			consult_ditails_jianchashijian.setHint("线下预约时间");
			consult_details_jianchaneirong_text.setText("线下预约内容");
			break;
		case 6:
			consult_ditails_fanhuihuanzhe.setBackgroundResource(R.color.red);
			consult_ditails_tijiaotaolunzu.setTextColor(getResources().getColor(R.color.white));
			consult_ditails_tijiaotaolunzu.setBackgroundResource(R.color.blue);
			consult_details_jianchaneirong_text.setText("讨论组内容");
			break;
		case 8://返回患者
			consult_ditails_fanhuihuanzhe.setBackgroundResource(R.color.blue);
			break;
		}
	}
 

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
	}
	 
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
			if(resultCode == RESULT_OK){
				if(data.hasExtra("id")){
					id = data.getIntExtra("id", 0);
					load();
				}
			}
		}else if(requestCode == Constens.START_ACTIVITY_FOR_RESULT_TWO){
			if(resultCode == RESULT_OK){
				if(data.hasExtra("date")){
					consult_ditails_jianchashijian.setText(data.getStringExtra("date"));
				}
			}
		}
	}



	@Override
	public void onClick(View v) {
		String url = v.getTag().toString();
		if(!ValidationUtils.isNull(url)){
			Intent intent = new Intent(activity, ViewPagerImageActivity.class);
			intent.putExtra("imageUrl", new String[]{url});
			startActivity(intent);
		}
	}
	 
}
