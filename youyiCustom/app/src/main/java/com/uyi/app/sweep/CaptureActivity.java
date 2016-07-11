package com.uyi.app.sweep;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import zxing.standopen.Bangdingchenggong;
import zxing.standopen.Bangdingshibai;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;


/**
 * 作者: zdw
 * 描述: 扫描界面  待修改
 */
public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private boolean hasSurface;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.50f;
	private boolean vibrate;
	private int x = 0;
	private int y = 0;
	private int cropWidth = 0;
	private int cropHeight = 0;
	private RelativeLayout mContainer = null;
	private RelativeLayout mCropLayout = null;
	private boolean isNeedCapture = false;
	
	public boolean isNeedCapture() {
		return isNeedCapture;
	}

	public void setNeedCapture(boolean isNeedCapture) {
		this.isNeedCapture = isNeedCapture;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCropWidth() {
		return cropWidth;
	}

	public void setCropWidth(int cropWidth) {
		this.cropWidth = cropWidth;
	}

	public int getCropHeight() {
		return cropHeight;
	}

	public void setCropHeight(int cropHeight) {
		this.cropHeight = cropHeight;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_qr_scan);
		// 初始化 CameraManager
		
		CameraManager.init(getApplication());
		PackageManager pm = getPackageManager();  
		  boolean flag = (PackageManager.PERMISSION_GRANTED ==
				  pm.checkPermission("android.permission.RECORD_AUDIO", "youyiCustom"));  
//		  System.out.println(""+flag);
		        if (flag) {        
		      //有这个权限，做相应处理   
//		       	 Toast.makeText(CaptureActivity.this, "flag="+flag, 1).show();
		     }else {   
		    	 //没有权限  
//		    	 Toast.makeText(CaptureActivity.this, "请在应用程序管理开启相机权限！", 1).show();
//		    	 Toast.makeText(CaptureActivity.this, "flag="+flag, 1).show();
		      }
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

		mContainer = (RelativeLayout) findViewById(R.id.capture_containter);
		mCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);

		ImageView mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
		TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
		mAnimation.setDuration(1500);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.REVERSE);
		mAnimation.setInterpolator(new LinearInterpolator());
		mQrLineView.setAnimation(mAnimation);
	}

	boolean flag = true;

	protected void light() {
		if (flag == true) {
			flag = false;
			// 开闪光灯
//			CameraManager.get().openLight();
		} else {
			flag = true;
			// 关闪光灯
//			CameraManager.get().offLight();
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}
	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	public String handleDecode(final String result) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
//		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
	
		//跳转到主界面:HomeActivity
//		Intent intent = new Intent();
//		intent.setClass(CaptureActivity.this,Test.class);
//		//bundle传值到HomeActivity
//		Bundle bundle = new Bundle();
//		bundle.putString("QWE", result);
//		intent.putExtras(bundle);
//		startActivity(intent);
		Loading.bulid(CaptureActivity.this,null).show();
		JSONObject params = new JSONObject();
		
		try {
			
				params.put("timeCompany",4);
			params.put("customerId",UserInfoManager.getLoginUserInfo(CaptureActivity.this).userId );
			params.put("ccid",result);
//			params.put("useTime",endTime.getText().toString());
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	RequestManager.postObject(String.format(Constens.SWEEP_UP), CaptureActivity.this, params , new Response.Listener<JSONObject>() {
		public void onResponse(JSONObject data) {
			Loading.bulid(CaptureActivity.this,null).dismiss();
			System.out.println("成功");
			System.out.println(data.toString());
//			team_goup_jiaru.setVisibility(View.GONE);
//			Toast.makeText(result.this, "绑定成功！", 0).show();
//			Loading.bulid(CaptureActivity.this, null).dismiss();
			 Intent intent=new Intent();
				intent.putExtra("info",result);
				intent.setClass(CaptureActivity.this, Bangdingchenggong.class);
				startActivity(intent);
			CaptureActivity.this.finish();
			
		}
	}, new RequestErrorListener() {
		public void requestError(VolleyError e) {
			Loading.bulid(CaptureActivity.this,null).dismiss();
			System.out.println(e.toString());
//			Toast.makeText(result.this, "绑定失败", 0).show();
//			startActivity(new Intent(CaptureActivity.this, Bangdingshibai.class));
			 Intent intent=new Intent();
				intent.putExtra("info",result);
				intent.setClass(CaptureActivity.this, Bangdingshibai.class);
				startActivity(intent);
			CaptureActivity.this.finish();
		}
	});
		return result;
	   

		// 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
		// handler.sendEmptyMessage(R.id.restart_preview);
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);

			Point point = CameraManager.get().getCameraResolution();
			int width = point.y;
			int height = point.x;

			int x = mCropLayout.getLeft() * width / mContainer.getWidth();
			int y = mCropLayout.getTop() * height / mContainer.getHeight();

			int cropWidth = mCropLayout.getWidth() * width / mContainer.getWidth();
			int cropHeight = mCropLayout.getHeight() * height / mContainer.getHeight();

			setX(x);
			setY(y);
			setCropWidth(cropWidth);
			setCropHeight(cropHeight);
			// 设置是否需要截图
			setNeedCapture(true);
			

		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(CaptureActivity.this);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public Handler getHandler() {
		return handler;
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
}