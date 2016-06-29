package com.uyi.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uyi.app.Constens;
import com.uyi.app.ui.dialog.CustomMultiChoiceDialog;
import com.uyi.app.utils.StorageUtils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

/**
 * 负责更新APP
 */
public class UpdateManager {
	
	private  Context context;
	private  String version;//版本号
	private  String update_text;//更新说明
	private  Boolean canceled = false;
	private  int progress;
	CustomMultiChoiceDialog customMultiChoiceDialog;
	CheckUpdateCallbackListenner checkUpdateCallbackListenner;
	// 存放更新APK文件的路径
	public static final String UPDATE_DOWNURL = Constens.SERVER_URL+"/android/youyipatient.apk";
	// 存放更新APK文件相应的版本说明路径
	public static final String UPDATE_CHECKURL =  Constens.SERVER_URL+"/android/youyipatient.txt";
	public static final String UPDATE_APKNAME = "youyipatient.apk";
	public static final String UPDATE_SAVENAME = "youyipatient.apk";
	
	private  final int UPDATE_CHECKCOMPLETED = 1;
	private  final int UPDATE_DOWNLOADING = 2;
	private  final int UPDATE_DOWNLOAD_ERROR = 3;
	private  final int UPDATE_DOWNLOAD_COMPLETED = 4;
	
	// 从服务器上下载apk存放文件夹
	private  String savefolder = Environment.getExternalStorageDirectory().getPath();
	/**
	 * 检查是否有更新
	 */
	public  void isUpdate(final Context context ,CheckUpdateCallbackListenner checkUpdateCallbackListenner){
		this.checkUpdateCallbackListenner  = checkUpdateCallbackListenner;
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean isupdate = false;
				try{
					PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
					int curVersionCode = pInfo.versionCode;
					HttpClient client = new DefaultHttpClient();
					HttpGet post = new HttpGet(UPDATE_CHECKURL);
					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() == 200) {
						String content = EntityUtils.toString(response .getEntity(),"UTF-8");
						JSONArray array = new JSONArray(content);
						if (array.length() > 0) {
							JSONObject obj = array.getJSONObject(0);
							int newVersionCode = Integer.parseInt(obj.getString("verCode"));
							if (newVersionCode > curVersionCode) {
								isupdate = true;
								version     = obj.getString("verName");
								update_text = obj.isNull("updateText")?"": obj.getString("updateText");
							}
						}
					}
				} catch ( Exception e) {
				}
				if(isupdate){
					handler.sendMessage(handler.obtainMessage(UPDATE_CHECKCOMPLETED, "true"));
				}else{
					handler.sendMessage(handler.obtainMessage(UPDATE_CHECKCOMPLETED, "false"));
				}
			}
		}).start();
	}

	
	public  void update(Context context){
		this.context = context;
		customMultiChoiceDialog = new  CustomMultiChoiceDialog(context,"发现新版本",version,update_text)
  		.setButton_cancel_text("取消", null) 
  		.setButton_ok_text("更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				new Thread() {
					@Override
					public void run() {
						try {
							URL url = new URL(UPDATE_DOWNURL);
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.connect();
							int length = conn.getContentLength();
							InputStream is = conn.getInputStream();
							File ApkFile = new File(savefolder, UPDATE_SAVENAME);
							if (ApkFile.exists()) {
								ApkFile.delete();
							}
							FileOutputStream fos = new FileOutputStream(ApkFile);
							int count = 0;
							byte buf[] = new byte[512];
							do {
								int numread = is.read(buf);
								count += numread;
								progress = (int) (((float) count / length) * 100);
								handler.sendMessage(handler.obtainMessage(UPDATE_DOWNLOADING));
								if (numread <= 0) {
									canceled = true;
									break;
								}
								fos.write(buf, 0, numread);
							} while (!canceled);
							if (canceled) {
								handler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
							}
							fos.close();
							is.close();
						} catch ( Exception e) {
							e.printStackTrace();
							handler.sendMessage(handler.obtainMessage(UPDATE_DOWNLOAD_ERROR, e.getMessage()));
						}
					}
				}.start();
			}
		});
  		customMultiChoiceDialog.show();
	}
	
	 Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_CHECKCOMPLETED:
				checkUpdateCallbackListenner.success(msg.obj.equals("true"));
				break;
			case UPDATE_DOWNLOADING:
				Log.i("UPDATE_PROGRESS", progress+"");
				customMultiChoiceDialog.setProgress(progress,progress+"%");
				break;
			case UPDATE_DOWNLOAD_ERROR:
				break;
			case UPDATE_DOWNLOAD_COMPLETED:
				customMultiChoiceDialog.dismiss();
				CustomMultiChoiceDialog.IS_BACKBUTTON = true;
				StorageUtils.installAPK(context, savefolder+"/"+UPDATE_SAVENAME);
				break;
			}
		};
	};
	
	
	public interface CheckUpdateCallbackListenner{
		void success(boolean isUpdate);
	}

}
