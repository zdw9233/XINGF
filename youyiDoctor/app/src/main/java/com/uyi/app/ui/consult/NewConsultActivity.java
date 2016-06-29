package com.uyi.app.ui.consult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.model.bean.Group;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Looding;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 新建咨询
 * @author user
 *
 */
@ContentView(R.layout.new_consult)
public class NewConsultActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.headerView) private HeaderView headerView; 
	
	/**
	 * 健康团队
	 */
	private ArrayList<Group> groupList = new ArrayList<Group>();
	
	@ViewInject(R.id.new_consult_group_left) private ImageView new_consult_group_left; 
	@ViewInject(R.id.new_consult_group_right) private ImageView new_consult_group_right; 
	@ViewInject(R.id.new_consult_group_logo) private ImageView new_consult_group_logo; 
	@ViewInject(R.id.new_consult_group_name) private TextView new_consult_group_name; 
	@ViewInject(R.id.new_consult_group_desc) private TextView new_consult_group_desc; 
	
	@ViewInject(R.id.new_consult_zs) private EditText new_consult_zs; 
	
	//上传已检查的项目
	@ViewInject(R.id.new_consult_jc_layout) private FlowRadioGroup  new_consult_jc_layout; 
	@ViewInject(R.id.new_consult_add_jc_image) private ImageView new_consult_add_jc_image; //加号
	
	
	//上传药物文本
	@ViewInject(R.id.new_consult_yaowuwenben_layout) private FlowRadioGroup  new_consult_yaowuwenben_layout; 
	@ViewInject(R.id.new_consult_add_ywwb_image) private ImageView new_consult_add_ywwb_image; //加号
	@ViewInject(R.id.new_consult_add_text_desc) private TextView new_consult_add_text_desc; //点击添加文字描述
	
	@ViewInject(R.id.new_consult_yaowu_text_desc_layout) private LinearLayout new_consult_yaowu_text_desc_layout; //点击添加文字描述的布局
	@ViewInject(R.id.new_consult_yaowu_text_desc) private EditText new_consult_yaowu_text_desc; //需要的帮助
	
	 //希望医生提供的帮助
	@ViewInject(R.id.new_consult_bz) private EditText new_consult_bz;
	
	@ViewInject(R.id.imageView1) private ImageView imageView1; //测试
	//提交按钮
	@ViewInject(R.id.new_evaluate_submit) private Button new_evaluate_submit; 
	
	private int groupListIndex = 1;//健康团队下标
	
	
	private boolean wenzi = false;
	private int  addImageIndex = 1;//点击图片的加号
	private File tempFile = new File(Environment.getExternalStorageDirectory(),"header.jpg");
	
	List<Bitmap> jc_images = new ArrayList<Bitmap>();//检查图片
	List<Bitmap> yaowu_images = new ArrayList<Bitmap>();//药物图片 
	
	
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true)
		.setTitle("新建咨询").showTitle(true)
		.setTitleColor(getResources()
		.getColor(R.color.blue))
		.showRight(true);
		new_consult_group_left.setOnClickListener(this);
		new_consult_group_right.setOnClickListener(this);
		 
		new_consult_add_text_desc.setOnClickListener(this);
		
		new_consult_add_jc_image.setOnClickListener(this);
		
		new_consult_add_ywwb_image.setOnClickListener(this);
		new_evaluate_submit.setOnClickListener(this);

		/**
		 * 查询健康团队
		 */
		RequestManager.getArray(Constens.HEALTH_GROUPS, activity, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray array) {
				for(int i = 0;i<array.length();i++){
					try {
						JSONObject jsonObject = array.getJSONObject(i);
						Group group = new Group();
						group.id = jsonObject.getInt("id");
						group.name = jsonObject.getString("name");
						group.info = jsonObject.getString("info");
						group.logo = jsonObject.getString("logo");
						groupList.add(group);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if(groupList.size() > 0){
					selectedGroup(0);
				}
			}
		});
		
	}
	
	/**
	 * 选中健康团队
	 * @param index
	 */
	public void selectedGroup(int index){
		
		if(index >= (groupList.size()-1)){
			index = (groupList.size()-1);
		}
		if(index < 0 ){
			index = 0;
		}
		groupListIndex = index;
		Group group = groupList.get(index);
		new_consult_group_name.setText(group.name);
		new_consult_group_desc.setText(group.name);
		ImageCacheManager.loadImage(group.logo, ImageCacheManager.getImageListener(new_consult_group_logo, null, null));
		new_consult_group_left.setImageResource(R.drawable.left_arrow_selected);
		new_consult_group_right.setImageResource(R.drawable.right_arrow_selected);
		if(groupListIndex ==  0){
			new_consult_group_left.setImageResource(R.drawable.left_arrow_default);
		}
		if(groupListIndex >= (groupList.size()-1)){
			new_consult_group_right.setImageResource(R.drawable.right_arrow_default);
		}
	}
	
	
	
	//相册
	private void requestGallery() {
		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, Constens.PHOTO_REQUEST_GALLERY);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 if(requestCode == Constens.PHOTO_REQUEST_GALLERY){
			if(resultCode == RESULT_OK){
				if (data != null) {
		            	if(addImageIndex == 1){
		            		try {
								Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
								jc_images.add(bitmap);
								showJCView();
		            		} catch ( Exception e) {
								e.printStackTrace();
							}
						}else if(addImageIndex == 2){
							try {
								Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
								yaowu_images.add(bitmap);
								showYaowuWenbenView();
		            		} catch ( Exception e) {
								e.printStackTrace();
							}
						}
				}
			}
		} 
	}
	
	public void showJCView(){
		new_consult_jc_layout.removeAllViews();
		for(int i = 0;i<jc_images.size();i++){
			final View view = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, new_consult_jc_layout,false);
			ImageView imageView = (ImageView)view.findViewById(R.id.new_consult_image_upload);
			ImageView new_consult_upload_delete = (ImageView)view.findViewById(R.id.new_consult_upload_delete);
			new_consult_upload_delete.setTag(jc_images.get(i));
			new_consult_upload_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					jc_images.remove((Bitmap)v.getTag());
					new_consult_jc_layout.removeView(view);
				}
			});
			imageView.setImageBitmap(jc_images.get(i));
			new_consult_jc_layout.addView(view);
		}
	}
	public void showYaowuWenbenView(){
		for(int i = 0;i<yaowu_images.size();i++){
			final View view = LayoutInflater.from(activity).inflate(R.layout.upload_info_image_layout, new_consult_yaowuwenben_layout,false);
			ImageView imageView = (ImageView)view.findViewById(R.id.new_consult_image_upload);
			ImageView new_consult_upload_delete = (ImageView)view.findViewById(R.id.new_consult_upload_delete);
			new_consult_upload_delete.setTag(yaowu_images.get(i));
			new_consult_upload_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					yaowu_images.remove((Bitmap)v.getTag());
					new_consult_yaowuwenben_layout.removeView(view);
				}
			});
			imageView.setImageBitmap(yaowu_images.get(i));
			new_consult_yaowuwenben_layout.addView(view);
		}
	}
	

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}
	

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.new_consult_add_text_desc){//点击  没有图片添加文字描述
			if(wenzi){
				wenzi = false;
				new_consult_yaowu_text_desc_layout.setVisibility(View.VISIBLE);
			}else{
				wenzi = true;
				new_consult_yaowu_text_desc_layout.setVisibility(View.GONE);
			}
		}else if(v.getId() == R.id.new_consult_add_jc_image){//添加 上传检查图片
			addImageIndex = 1;
			requestGallery();
		}else if(v.getId() == R.id.new_consult_add_ywwb_image){//添加 药物图片
			addImageIndex = 2;
			requestGallery();
		}else if(v.getId() == R.id.new_consult_group_left){//Left
			selectedGroup(--groupListIndex);
		}else if(v.getId() == R.id.new_consult_group_right){//Right
			selectedGroup(++groupListIndex);
		}else if(v.getId() == R.id.new_evaluate_submit){//提交
			try {
				String zs = new_consult_zs.getText().toString();
				String medicaltxt = new_consult_add_text_desc.getText().toString();
				String  desc = new_consult_bz.getText().toString();
				
				if(ValidationUtils.isNull(desc,zs)){
					T.showLong(activity, "资料不完整!");
					return;
				}
				
				if(jc_images.size() ==0 ){
					T.showLong(activity, "资料不完整!");
					return;
				}
				if(yaowu_images.size() == 0){
					T.showLong(activity, "资料不完整!");
					return;
				}
				
				Looding.bulid(activity,"正在保存图片..").show();
				JSONArray checkItems = new JSONArray(); 
				for(int i = 0;i < jc_images.size();i++){
					JSONObject medicalPics1 = new JSONObject();
					medicalPics1.put("binary", BitmapUtils.encode(jc_images.get(i)));
					checkItems.put(medicalPics1);
				}
				
				JSONArray medicalPics = new JSONArray(); 
				for(int i = 0;i < yaowu_images.size();i++){
					JSONObject medicalPics1 = new JSONObject();
					medicalPics1.put("binary", BitmapUtils.encode(yaowu_images.get(i)));
					medicalPics.put(medicalPics1);
				}
				
				JSONObject params = new JSONObject();
	//			params.put("consultId", consultId);
				params.put("groupId", groupList.get(groupListIndex).id);
				params.put("desc", zs);
				params.put("help", desc);
				params.put("medicalTxt", medicaltxt);
				params.put("checkItems", checkItems);
				params.put("medicalPics", medicalPics);
				Looding.bulid(activity,"正在上传..").show();
				RequestManager.postObject(Constens.ACCOUNT_HEALTH_CONSULT, activity, params, null, new RequestErrorListener() {
					@Override
					public void requestError(VolleyError e) {
						Looding.bulid(activity,"").dismiss();
						setResult(RESULT_OK);
						finish();
					}
				});
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
