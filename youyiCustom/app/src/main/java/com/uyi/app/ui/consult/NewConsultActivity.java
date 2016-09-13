package com.uyi.app.ui.consult;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.util.ImageTools;
import com.lidroid.xutils.util.PhotoUtil;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.model.bean.Group;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.FlowRadioGroup;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.BitmapUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 新建咨询
 * @author user
 *
 */
@ContentView(R.layout.new_consult)
public class NewConsultActivity extends BaseActivity implements OnClickListener {
	   private PopupWindow mSetPhotoPop;
	    private File mCurrentPhotoFile;
	    private static final int CAMERA_WITH_DATA = 1882;
	    private static final int CAMERA_CROP_RESULT = 1883;
	    private static final int ICON_SIZE = 96;
	@ViewInject(R.id.headerView) private HeaderView headerView; 
	@ViewInject(R.id.new_consult_layout) private ScrollView new_consult_layout; 
	
	/**
	 * 健康团队
	 */
	private ArrayList<Group> groupList = new ArrayList<Group>();
	@ViewInject(R.id.new_consult_main) private LinearLayout mMainView; 
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
	
	int consultId = 0;
	
	private boolean wenzi = false;
	private int  addImageIndex = 1;//点击图片的加号
	private File tempFile = new File(Environment.getExternalStorageDirectory(),"header.jpg");
	
	List<Bitmap> jc_images = new ArrayList<Bitmap>();//检查图片
	List<Bitmap> yaowu_images = new ArrayList<Bitmap>();//药物图片 
	
	boolean isSubmit = true;//是否可以提交  控制提交按钮被多次点击
	
	@Override
	protected void onInitLayoutAfter() {
		headerView.showLeftReturn(true)
		.setTitle("新建互动").showTitle(true)
		.setTitleColor(getResources()
		.getColor(R.color.blue))
		.showRight(true);
		new_consult_group_left.setOnClickListener(this);
		new_consult_group_right.setOnClickListener(this);
		 
		new_consult_add_text_desc.setOnClickListener(this);
		
		new_consult_add_jc_image.setOnClickListener(this);
		
		new_consult_add_ywwb_image.setOnClickListener(this);
		new_evaluate_submit.setOnClickListener(this);
		
		if(getIntent().hasExtra("groupId")){//随访
			groupListIndex = 0;
			Group group = new Group();
			group.id = getIntent().getIntExtra("groupId", 0);
			group.name = getIntent().getStringExtra("groupName");
			group.info = getIntent().getStringExtra("groupInfo");
			group.logo =  getIntent().getStringExtra("groupLogo");
			consultId = getIntent().getIntExtra("consultId", 0);
			L.d(TAG, consultId+"");
			groupList.add(group);
			if(groupList.size() > 0){
				selectedGroup(0);
			}
			new_consult_group_left.setVisibility(View.GONE);
			new_consult_group_right.setVisibility(View.GONE);
			new_consult_layout.setVisibility(View.VISIBLE);
		}else{
			Loading.bulid(activity, null).show();
			/**
			 * 查询健康团队
			 */
			RequestManager.getArray(Constens.HEALTH_GROUPS, activity, new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray array) {
					Loading.bulid(activity, null).dismiss();
					if(array.length() == 0 ){
						T.showShort(application, "请先加入健康团队");
						finish();
					}
					new_consult_layout.setVisibility(View.VISIBLE);
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
								Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
								Bitmap bitmap = ImageTools.zoomBitmap(bitmap1, bitmap1.getWidth() / 5, bitmap1.getHeight() / 5);
								jc_images.add(bitmap);
								showJCView();
		            		} catch ( Exception e) {
								e.printStackTrace();
							}
						}else if(addImageIndex == 2){
							try {
								Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
								Bitmap bitmap = ImageTools.zoomBitmap(bitmap1, bitmap1.getWidth() / 5, bitmap1.getHeight() / 5);
								yaowu_images.add(bitmap);
								showYaowuWenbenView();
		            		} catch ( Exception e) {
								e.printStackTrace();
							}
						}
				}
				
			}
		} 
		 if(requestCode == CAMERA_WITH_DATA) {
			 try {
//					Bitmap bitmap = data.getParcelableExtra("data");
				 Bitmap bitmap1 = BitmapFactory.decodeFile(mCurrentPhotoFile.getAbsolutePath());
					Bitmap bitmap = ImageTools.zoomBitmap(bitmap1, bitmap1.getWidth() / 5, bitmap1.getHeight() / 5);
					//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
					bitmap1.recycle();
					if(addImageIndex == 1){
	            		try {
							
							jc_images.add(bitmap);
							showJCView();
	            		} catch ( Exception e) {
							e.printStackTrace();
						}
					}else if(addImageIndex == 2){
						try {
						
							yaowu_images.add(bitmap);
							showYaowuWenbenView();
	            		} catch ( Exception e) {
							e.printStackTrace();
						}
					}
				
  		} catch ( Exception e) {
					e.printStackTrace();
				}
         
			 }
//		 if(requestCode == CAMERA_CROP_RESULT) {
//       	  try {
//					Bitmap bitmap = data.getParcelableExtra("data");
//					if(addImageIndex == 1){
//	            		try {
//							
//							jc_images.add(bitmap);
//							showJCView();
//	            		} catch ( Exception e) {
//							e.printStackTrace();
//						}
//					}else if(addImageIndex == 2){
//						try {
//						
//							yaowu_images.add(bitmap);
//							showYaowuWenbenView();
//	            		} catch ( Exception e) {
//							e.printStackTrace();
//						}
//					}
//				
//     		} catch ( Exception e) {
//					e.printStackTrace();
//				}
//            
//             // imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            
//            
//			}
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
		new_consult_yaowuwenben_layout.removeAllViews();
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
	
	
	
	
	
	
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1){
				JSONObject params = (JSONObject) msg.obj;
				RequestManager.postObject(Constens.ACCOUNT_HEALTH_CONSULT, activity, params, new Listener<JSONObject>() {
					public void onResponse(JSONObject arg0) {
						Loading.bulid(activity,"").dismiss();
						setResult(RESULT_OK);
						finish();
					}
				}, new RequestErrorListener() {
					public void requestError(VolleyError e) {
						Loading.bulid(activity,"").dismiss();
						setResult(RESULT_OK);
						finish();
					}
				});
			}
		};
		
		
	};
	
	/**
	 * 设置是否可以提交
	 * @param isSubmit
	 */
	public void setButtonSubmit(boolean isSubmit){
		if(isSubmit){
			new_evaluate_submit.setText("提交咨询");
			new_evaluate_submit.setBackgroundResource(R.drawable.blue_bg);
			new_evaluate_submit.setTextColor(getResources().getColor(R.color.white));
		}else{
			new_evaluate_submit.setText("正在处理");
			new_evaluate_submit.setBackgroundResource(R.drawable.hui_bg);
			new_evaluate_submit.setTextColor(getResources().getColor(R.color.black));
		}
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
//			requestGallery();
			showPop();
		}else if(v.getId() == R.id.new_consult_add_ywwb_image){//添加 药物图片
			addImageIndex = 2;
//			requestGallery();
			showPop();
		}else if(v.getId() == R.id.new_consult_group_left){//Left
			selectedGroup(--groupListIndex);
		}else if(v.getId() == R.id.new_consult_group_right){//Right
			selectedGroup(++groupListIndex);
		}else if(v.getId() == R.id.new_evaluate_submit){//提交
				
				final String zs = new_consult_zs.getText().toString();
				final String medicaltxt = new_consult_yaowu_text_desc.getText().toString();
				final String  desc = new_consult_bz.getText().toString();
				
				if(ValidationUtils.isNull(desc,zs)){
					T.showLong(activity, "资料不完整!");
					return;
				}
				L.d(TAG, isSubmit+"1");
				if(!isSubmit){
					return;
				}
				isSubmit = false;
				setButtonSubmit(isSubmit);
				L.d(TAG, isSubmit+"2");
				
//				if(jc_images.size() ==0 ){
//					T.showLong(activity, "资料不完整!");
//					return;
//				}
//				if(yaowu_images.size() == 0){
//					T.showLong(activity, "资料不完整!");
//					return;
//				}
				Loading.bulid(activity,"正在保存..").show();
				L.d(TAG, isSubmit+"3");
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try { 
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
							if(consultId != 0){
								params.put("consultId", consultId);
							}
							params.put("medicalTxt", medicaltxt);
							L.d(TAG, params.toString());
							params.put("checkItems", checkItems);
							params.put("medicalPics", medicalPics);
							handler.sendMessage(handler.obtainMessage(1, params));
						} catch (Exception e1) {
							e1.printStackTrace();
							isSubmit = true;
							setButtonSubmit(isSubmit);
						}
					}
				});
				thread.start();
		}
	}
	 /**
     *  弹出 popupwindow
     */
    public void showPop(){
        View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout, null);
        Button btnTakePhoto = (Button) mainView.findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
				// 拍照获取
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
					requestPermissions(new String[]{Manifest.permission.CAMERA}, 0x100);
				} else
					doTakePhoto();

                
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
    /**
     * 调用系统相机拍照
     */
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Photo");
            if (!file.exists()) {
                file.mkdirs();
            }
            
            mCurrentPhotoFile = new File(file, PhotoUtil.getRandomFileName());
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "手机中无可用的图片", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Constructs an intent for capturing a photo and storing it in a temporary
     * file.
     */
    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 相机剪切图片
     */
    protected void doCropPhoto(File f) {
        try {
            // Add the image to the media store
            MediaScannerConnection.scanFile(this, new String[]{
                    f.getAbsolutePath()
            }, new String[]{
                    null
            }, null);

            // Launch gallery to crop the photo
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, CAMERA_CROP_RESULT);
        } catch (Exception e) {
            Toast.makeText(this,"手机中无可用的图片", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取系统剪裁图片的Intent.
     */
    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra("return-data", true);
        return intent;
    }
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 0x100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			doTakePhoto();
		}

	}
}
