package com.uyi.app.ui.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.HealthInfo;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.HeaderView.OnTabChanage;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter.IOnItemSelectListener;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.FileUtils;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.UYIUtils;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * 修改用户基本信息  当前界面布局及控件命名源自拷贝
 * @author user
 *
 */
@ContentView(R.layout.update_user_info)
public class UpdateUserInfoActivity extends BaseActivity implements OnTabChanage, IOnItemSelectListener {
	 private PopupWindow mSetPhotoPop;
	 private String dizhi,lianxidianhua,youxiangdizhi,zhiye,shenggao,tizhong;
	@ViewInject(R.id.headerView) 						private HeaderView headerView;
	
	//个人资料
	@ViewInject(R.id.updata_user_main) private LinearLayout mMainView; 
	@ViewInject(R.id.register_three_layout) private LinearLayout register_three_layout;
	@ViewInject(R.id.register_header_image) private RoundedImageView register_header_image;
	@ViewInject(R.id.register_shen) private TextView register_shen;
	@ViewInject(R.id.register_city) private TextView register_city;
	@ViewInject(R.id.register_address) private EditText register_address;
	@ViewInject(R.id.register_chushennianyue) private TextView register_chushennianyue;
	@ViewInject(R.id.register_phone) private EditText register_phone;
	@ViewInject(R.id.register_mobile) private EditText register_mobile;
	@ViewInject(R.id.register_email) private EditText register_email;
	@ViewInject(R.id.register_card) private EditText register_card;
	@ViewInject(R.id.register_zhiye) private EditText register_zhiye;
	@ViewInject(R.id.register_height) private EditText register_height;
	@ViewInject(R.id.register_weight) private EditText register_weight;
	@ViewInject(R.id.register_three_submit) private Button register_three_submit;
	
	
	@ViewInject(R.id.register_info_yuejing_layout) private LinearLayout register_info_yuejing_layout;
	@ViewInject(R.id.register_info_liucanshi_layout) private LinearLayout register_info_liucanshi_layout;
	
	private  SpinerPopWindow spinerPopWindow;
	/**
	 * 省
	 */
	private  List<String> provinces = new ArrayList<String>();
	private  JSONArray provincesJSON = new JSONArray();
	
	/**
	 * 市
	 */
	private  List<String> province = new ArrayList<String>();
	private  JSONArray provinceJSON = new JSONArray();
	
	/**
	 * 1 问题
	 * 2 性别
	 * 3省
	 * 4市 
	 * 
	 */
	public int spinerIndex = 1;
	Bitmap photo;
	private File tempFile = new File(Environment.getExternalStorageDirectory(),"header.jpg");
	//健康资料
	@ViewInject(R.id.register_info_one) 				private ScrollView register_info_one;
	@ViewInject(R.id.register_info_gerenjiwangbinshi) 	private EditText register_info_gerenjiwangbinshi;
	@ViewInject(R.id.register_info_chuanrangbingshi) 	private EditText register_info_chuanrangbingshi;
	@ViewInject(R.id.register_info_waishang) 			private EditText register_info_waishang;
	@ViewInject(R.id.register_info_shoushushi) 			private EditText register_info_shoushushi;
	@ViewInject(R.id.register_info_liucanshi) 			private EditText register_info_liucanshi;
	@ViewInject(R.id.register_info_yuejing) 			private EditText register_info_yuejing;
	@ViewInject(R.id.register_info_guomingshi) 			private EditText register_info_guomingshi;
	@ViewInject(R.id.register_info_shuxueshi) 			private EditText register_info_shuxueshi;
	@ViewInject(R.id.register_info_jiazhubinshi) 		private EditText register_info_jiazhubinshi;
	@ViewInject(R.id.register_info_muqianfuyaoqingkuang) private EditText register_info_muqianfuyaoqingkuang;
	@ViewInject(R.id.register_info_qitabuchongqingkuang) private EditText register_info_qitabuchongqingkuang;
	@ViewInject(R.id.register_info_submit) 				private Button register_info_submit;
	
	int type = 1;
	
	Calendar cal = Calendar.getInstance();
	private Integer  shengfen;
	private Integer city;
	
	private HealthInfo healthInfo;//病人ID
	private UserInfo userInfo;
	
	@Override
	protected void onInitLayoutAfter() {
		String[] tag = getResources().getStringArray(R.array.update_user_info);
		headerView.setTitleTabs(tag);
		headerView.showLeftReturn(true).showTab(true).setOnTabChanage(this);
		headerView.selectTabItem(1);
		healthInfo = new HealthInfo();
		spinerPopWindow = new SpinerPopWindow(activity);
		spinerPopWindow.setItemListener(this);
		RequestManager.getArray(Constens.PROVINCDS,activity, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray array) {
				provincesJSON = array;
			
				for (int i = 0; i < array.length(); i++) {
					try {
						provinces.add(array.getJSONObject(i).getString("name"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});  
		
		userInfo = UserInfoManager.getLoginUserInfo(activity);
		if(userInfo != null){
			
			ImageCacheManager.loadImage(userInfo.icon, ImageCacheManager.getImageListener(register_header_image, null, null));
			
			Loading.bulid(activity, null).show();
			RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Listener<JSONObject>() {
				public void onResponse(JSONObject data) {
					try {
						Loading.bulid(activity, null).dismiss();
						
						register_shen.setText(data.getJSONObject("province").getString("name"));
						register_city.setText(data.getJSONObject("city").getString("name"));
						register_address.setText(JSONObjectUtils.getString(data,"address"));
						dizhi = JSONObjectUtils.getString(data,"address");
						register_chushennianyue.setText(JSONObjectUtils.getString(data,"birthday"));
						register_phone.setText(JSONObjectUtils.getString(data,"backupPhoneNumber"));
						lianxidianhua = JSONObjectUtils.getString(data,"backupPhoneNumber");
						register_mobile.setText(JSONObjectUtils.getString(data,"phoneNumber"));
						register_email.setText(JSONObjectUtils.getString(data,"email"));
						youxiangdizhi = JSONObjectUtils.getString(data,"email");
						register_card.setText(JSONObjectUtils.getString(data,"idCardNumber"));
						register_zhiye.setText(JSONObjectUtils.getString(data,"occupation"));
						zhiye = JSONObjectUtils.getString(data,"occupation");
						register_height.setText(JSONObjectUtils.getInt(data,"height")+"");
						shenggao = JSONObjectUtils.getInt(data,"height")+"";
						register_weight.setText(JSONObjectUtils.getInt(data,"weight")+"");
						tizhong = JSONObjectUtils.getInt(data,"weight")+"";
						
						if(data.has("healthInfo")){
							JSONObject healthInfo = data.getJSONObject("healthInfo");
							L.d(TAG, data.toString());
							
							if(ValidationUtils.equlse(UYIUtils.convertGender(data.getString("gender")), "男")){
								register_info_yuejing_layout.setVisibility(View.GONE);
								register_info_liucanshi_layout.setVisibility(View.GONE);
							}
							register_info_gerenjiwangbinshi.setText(JSONObjectUtils.getString(healthInfo, "medical") );
							register_info_chuanrangbingshi.setText(JSONObjectUtils.getString(healthInfo, "infection") );
							register_info_waishang.setText(JSONObjectUtils.getString(healthInfo, "trauma"));
							register_info_shoushushi.setText(JSONObjectUtils.getString(healthInfo, "operation") );
							register_info_liucanshi.setText(JSONObjectUtils.getString(healthInfo, "pregnancy"));
							register_info_yuejing.setText(JSONObjectUtils.getString(healthInfo, "menstruation"));
							register_info_guomingshi.setText(JSONObjectUtils.getString(healthInfo, "allergic"));
							register_info_shuxueshi.setText(JSONObjectUtils.getString(healthInfo, "blood"));
							register_info_jiazhubinshi.setText(JSONObjectUtils.getString(healthInfo, "familyMedical"));
							register_info_muqianfuyaoqingkuang.setText(JSONObjectUtils.getString(healthInfo, "current"));
							register_info_qitabuchongqingkuang.setText(JSONObjectUtils.getString(healthInfo, "others"));
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	
	
	
	
	
	
	@OnClick({R.id.register_wenti,R.id.register_xinbie,R.id.register_shen,R.id.register_city,R.id.register_chushennianyue,R.id.register_header_image})
	public void onClick(View v){
		  if(v.getId() == R.id.register_shen){
			spinerIndex = 3;
			spinerPopWindow.refreshData(provinces, 0);
			spinerPopWindow.setWidth(register_shen.getWidth());
			spinerPopWindow.showAsDropDown(register_shen);
		}else if(v.getId() == R.id.register_city){
			if(shengfen == null){
				return;
			}
			spinerIndex = 4;
			loadCity();
		}else if(v.getId() == R.id.register_chushennianyue){
			Intent intent = new Intent(activity, DatePickerActivity.class);
			intent.putExtra("eDate", DateUtils.toDate(new Date(), Constens.DATE_FORMAT_YYYY_MM_DD));
			intent.putExtra("eDateMessage", getString(R.string.e_date_chushengriqi));
			startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
		}else if(v.getId() == R.id.register_header_image){
			showPop();
		}
	}

	public void loadCity(){
		RequestManager.getArray(String.format(Constens.PROVINCD, shengfen),activity, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray array) {
				provinceJSON = array;
				province.clear();
				for (int i = 0; i < array.length(); i++) {
					try {
						province.add(array.getJSONObject(i).getString("name"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				spinerIndex = 4;
				spinerPopWindow.refreshData(province, 0);
				spinerPopWindow.setWidth(register_city.getWidth());
				spinerPopWindow.showAsDropDown(register_city);
				register_city.setText(province.get(0));
			}
		});
	}
	
	//相册
	private void requestGallery() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constens.PHOTO_REQUEST_GALLERY);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/jpeg");
            startActivityForResult(intent, Constens.PHOTO_REQUEST_GALLERY);
        }
	}
	//照相机
		private void requestTakePhoto() {
			Intent cameraintent = new Intent(   MediaStore.ACTION_IMAGE_CAPTURE);
		    // 指定调用相机拍照后照片的储存路径
		    cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(tempFile));
		    startActivityForResult(cameraintent, Constens.PHOTO_REQUEST_TAKEPHOTO);
		}
	//裁剪
	private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, Constens.PHOTO_REQUEST_CUT);
    }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constens.PHOTO_REQUEST_TAKEPHOTO){
			if(resultCode == RESULT_OK){
				if(tempFile != null){
					startPhotoZoom(Uri.fromFile(tempFile));
				}
			}
		}else
		 if(requestCode == Constens.PHOTO_REQUEST_GALLERY){
			if(resultCode == RESULT_OK){
				if (data != null) {
					startPhotoZoom(Uri.fromFile(new File(FileUtils.getPath(activity, data.getData()))));
				}
			}
		}else if(requestCode == Constens.PHOTO_REQUEST_CUT){ 
			if(resultCode == RESULT_OK){
				if (data != null) {
					 Bundle bundle = data.getExtras();
		             if (bundle != null) {
		            	  photo = (Bitmap) bundle.get("data");
		            	  register_header_image.setImageBitmap(photo);
		             }
				}
			}
		}else if(requestCode == Constens.START_ACTIVITY_FOR_RESULT){
			if(resultCode == RESULT_OK){
				if(data.hasExtra("date")){
					register_chushennianyue.setText(data.getStringExtra("date"));
				}
			}
		}
	}
	
	
	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public void onChanage(int postion) {
		if(type != postion){
			type = postion;
			if(type == 1){
				register_three_layout.setVisibility(View.VISIBLE);
				register_info_one.setVisibility(View.GONE);
			}else{
				register_three_layout.setVisibility(View.GONE);
				register_info_one.setVisibility(View.VISIBLE);
			}
		}
	}
 




	@Override
	public void onItemClick(int pos) {
		 if(spinerIndex == 3){
				JSONObject json;
				try {
					json = provincesJSON.getJSONObject(pos);
					shengfen = json.getInt("id");
					register_shen.setText(json.getString("name"));
					loadCity();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else if(spinerIndex == 4){
				JSONObject json;
				try {
					json = provinceJSON.getJSONObject(pos);
					city = json.getInt("id");
					register_city.setText(json.getString("name"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	}
	
	
	
	@OnClick({R.id.register_three_submit,R.id.register_info_submit})
	public void clickInfo(View view){
		if(view.getId() == R.id.register_three_submit){//个人资料
			try {
				String address  = register_address.getText().toString();
				String birthday = register_chushennianyue.getText().toString();
				String phone   = register_phone.getText().toString();
				String mobile= register_mobile.getText().toString();
				String email  = register_email.getText().toString();
				String idCardNumber  = register_card.getText().toString();
				String occupation = register_zhiye.getText().toString();
				if(ValidationUtils.isNull(address,birthday,phone,mobile,email,idCardNumber,occupation )){
					T.showLong(application, "信息输入不完整!");
					return;
				}
				if(ValidationUtils.isNull(register_height.getText().toString())){ 
					register_height.setText("0");
				}
				if(ValidationUtils.isNull(register_weight.getText().toString())){ 
					register_weight.setText("0");
				}
				int height  = Integer.parseInt(register_height.getText().toString());
				int weight  =  Integer.parseInt(register_weight.getText().toString());
				
				
				if(!ValidationUtils.isMobile(mobile)){
					T.showLong(application, "手机号格式不正确!");
					return;
				}
				if(!ValidationUtils.pattern(Constens.PHONE_REGEX, phone)){
					T.showLong(application, "联系电话格式不正确!");
					return;
				}
				if(!ValidationUtils.pattern(Constens.EMAIL_REGEX, email)){
					T.showLong(application, "邮箱格式不正确!");
					return;
				}
				if(!ValidationUtils.pattern(Constens.ID_CARD_REGEX, idCardNumber)){
					T.showLong(application, "身份证号码格式不正确!");
					return;
				}
				JSONObject params = new JSONObject();
				if(photo != null){
					try {
						params.put("icon", BitmapUtils.encode(photo));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				params.put("birthday", birthday);
				params.put("idCardNumber", idCardNumber);
				params.put("phoneNumber",mobile);
				params.put("backupPhoneNumber",phone);
				params.put("email", email);
				if(city != null){
					params.put("cityId", city);
				}
				params.put("address", address);
				params.put("occupation", occupation);
				params.put("height", height);
				params.put("weight", weight);
				dizhi = address;
				lianxidianhua = phone;
				youxiangdizhi = email;
				zhiye = occupation;
				shenggao = register_height.getText().toString();
				tizhong = register_weight.getText().toString();
				Loading.bulid(activity, null).show();
				RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, null, new RequestErrorListener() {
					@Override
					public void requestError(VolleyError e) {
						Loading.bulid(activity, null).dismiss();
						if(e.networkResponse != null ){
							if(e.networkResponse.statusCode == 204){
								T.showShort(activity, "修改成功!");
								
							}else{
								T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
							}
						}else{
							T.showShort(activity, "修改成功!");
						}
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
		}else if(view.getId() == R.id.register_info_submit){//健康资料
			
			if(userInfo == null){
				T.showLong(activity, "获取注册信息失败!");
				return;
			}
//			if(ValidationUtils.isNull(register_info_gerenjiwangbinshi.getText().toString(),register_info_chuanrangbingshi.getText().toString(),
//					register_info_waishang.getText().toString(),register_info_shoushushi.getText().toString(),register_info_liucanshi.getText().toString(),
//					register_info_yuejing.getText().toString(),register_info_guomingshi.getText().toString(),register_info_shuxueshi.getText().toString(),
//					register_info_jiazhubinshi.getText().toString(),register_info_muqianfuyaoqingkuang.getText().toString(),
//					register_info_qitabuchongqingkuang.getText().toString())){
//				T.showLong(application, "信息输入不完整!");
//				System.out.println(register_info_gerenjiwangbinshi.getText().toString()+"-"+register_info_chuanrangbingshi.getText().toString()+""+
//					register_info_waishang.getText().toString()+""+register_info_shoushushi.getText().toString()+""+register_info_liucanshi.getText().toString()+""+
//					register_info_yuejing.getText().toString()+""+register_info_guomingshi.getText().toString()+""+register_info_shuxueshi.getText().toString()+""+
//					register_info_jiazhubinshi.getText().toString()+""+register_info_muqianfuyaoqingkuang.getText().toString()+""+
//					register_info_qitabuchongqingkuang.getText().toString());
//				return;
//			}
			healthInfo.setMedical(		register_info_gerenjiwangbinshi.getText().toString());
			healthInfo.setInfection(	register_info_chuanrangbingshi.getText().toString());
			healthInfo.setTrauma(		register_info_waishang.getText().toString());
			healthInfo.setOperation(	register_info_shoushushi.getText().toString());
			healthInfo.setPregnancy(	register_info_liucanshi.getText().toString());
			healthInfo.setMenstruation(	register_info_yuejing.getText().toString());
			healthInfo.setAllergic(		register_info_guomingshi.getText().toString());
			healthInfo.setBlood(		register_info_shuxueshi.getText().toString());
			healthInfo.setFamilyMedical(register_info_jiazhubinshi.getText().toString());
			healthInfo.setCurrent(		register_info_muqianfuyaoqingkuang.getText().toString());
			healthInfo.setOthers(		register_info_qitabuchongqingkuang.getText().toString());
			healthInfo.setUserId(userInfo.userId);
			try {
				JSONObject healthInfoObject = new JSONObject();
				if(!ValidationUtils.isNull(healthInfo.getMedical())){
					healthInfoObject.put("medical", healthInfo.getMedical());
				}else{
					healthInfoObject.put("medical", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getInfection())){
					healthInfoObject.put("infection", healthInfo.getInfection());
				}else{
					healthInfoObject.put("infection", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getTrauma())){
					healthInfoObject.put("trauma", healthInfo.getTrauma());
				}else{
					healthInfoObject.put("trauma", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getOperation())){
					healthInfoObject.put("operation", healthInfo.getOperation());
				}else{
					healthInfoObject.put("operation", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getPregnancy())){
					healthInfoObject.put("pregnancy", healthInfo.getPregnancy());
				}else{
					healthInfoObject.put("pregnancy", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getMenstruation())){
					healthInfoObject.put("menstruation", healthInfo.getMenstruation());
				}else{
					healthInfoObject.put("menstruation", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getAllergic())){
					healthInfoObject.put("allergic", healthInfo.getAllergic());
				}else{
					healthInfoObject.put("allergic", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getBlood())){
					healthInfoObject.put("blood", healthInfo.getBlood());
				}else{
					healthInfoObject.put("blood", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getFamilyMedical())){
					healthInfoObject.put("familyMedical", healthInfo.getFamilyMedical());
				}else{
					healthInfoObject.put("familyMedical", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getCurrent())){
					healthInfoObject.put("current", healthInfo.getCurrent());
				}else{
					healthInfoObject.put("current", "");
				}
				
				if(!ValidationUtils.isNull(healthInfo.getOthers())){
					healthInfoObject.put("others", healthInfo.getOthers());
				}else{
					healthInfoObject.put("others", "");
				}
				JSONObject params  = new JSONObject();
				
//				String address  = register_address.getText().toString();
//				String birthday = register_chushennianyue.getText().toString();
//				String phone   = register_phone.getText().toString();
//				String mobile= register_mobile.getText().toString();
//				String email  = register_email.getText().toString();
//				String idCardNumber  = register_card.getText().toString();
//				String occupation = register_zhiye.getText().toString();
//				if(ValidationUtils.isNull(address,birthday,phone,mobile,email,idCardNumber,occupation )){
//					T.showLong(application, "信息输入不完整!");
//					return;
//				}
//				if(ValidationUtils.isNull(register_height.getText().toString())){ 
//					register_height.setText("0");
//				}
//				if(ValidationUtils.isNull(register_weight.getText().toString())){ 
//					register_weight.setText("0");
//				}
				int height  = Integer.parseInt(shenggao);
				int weight  =  Integer.parseInt(tizhong);
				 
//				params.put("birthday", birthday);
//				params.put("idCardNumber", idCardNumber);
				params.put("backupPhoneNumber",lianxidianhua);
				params.put("email", youxiangdizhi); 
				params.put("address", dizhi);
				params.put("occupation", zhiye);
				params.put("height", height);
				params.put("weight", weight);
				params.put("healthInfo", healthInfoObject);
				Loading.bulid(activity, null).show();
				RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, null, new RequestErrorListener() {
					@Override
					public void requestError(VolleyError e) {
						Loading.bulid(activity, null).dismiss();
						if(e.networkResponse != null ){
							if(e.networkResponse.statusCode == 204){
								T.showShort(activity, "修改成功!");
//								UYIApplication.loginOut();
//								UpdateUserInfoActivity.this.finish();
							}else{
								T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
							}
						}else{
							T.showShort(activity, "修改成功!");
//							UYIApplication.loginOut();
//							UpdateUserInfoActivity.this.finish();
						}
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
	 /**
     *  弹出 popupwindow
     */
    public void showPop(){
        View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout, null);
        TextView title =  (TextView) mainView.findViewById(R.id.text_set_title);
        title.setText("设置头像");
        Button btnTakePhoto = (Button) mainView.findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
                // 拍照获取
                requestTakePhoto();
                
            }

        });
        Button btnCheckFromGallery = (Button) mainView.findViewById(R.id.btn_check_from_gallery);
        btnCheckFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
                // 相册获取
                requestGallery();                
            }
        });
        Button btnCancle = (Button) mainView.findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
            }
        });
        mSetPhotoPop = new PopupWindow(this);
        mSetPhotoPop.setBackgroundDrawable(new BitmapDrawable());
        mSetPhotoPop.setFocusable(true);
        mSetPhotoPop.setTouchable(true);
        mSetPhotoPop.setOutsideTouchable(true);
        mSetPhotoPop.setContentView(mainView);
        mSetPhotoPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mSetPhotoPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mSetPhotoPop.setAnimationStyle(R.style.bottomStyle);
        mSetPhotoPop.showAtLocation(mMainView, Gravity.BOTTOM, 0, 0);
        mSetPhotoPop.update();
    }
}
