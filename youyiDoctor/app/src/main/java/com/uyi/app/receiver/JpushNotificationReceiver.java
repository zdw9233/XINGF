package com.uyi.app.receiver;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.uyi.app.UYIApplication;
import com.uyi.app.ui.consult.ConsultDetailsActivity;
import com.uyi.app.ui.personal.message.MessageDetailsActivity;
import com.uyi.doctor.app.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;


/**
 *  接收推送消息
 * @author user
 *
 */
public class JpushNotificationReceiver  extends BroadcastReceiver {
	private static final String TAG = "JpushNotificationReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
    	if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
        	try {
        		NotificationManager manager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);  
				JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//				Log.d(TAG, jsonObject.getInt("id")+"id");
//				Log.d(TAG, jsonObject.getInt("type")+"type");
				int type = jsonObject.getInt("type");
				if(!jsonObject.has("id")){
					UYIApplication.loginOut();
					return;
				}
				int id = jsonObject.getInt("id");
				/**
				 *  0: 系统公告
				 *	1: 消息
				 *	2: 公告
				 *	3: 咨询
				 */
				Intent intnet = null;
				if(type == 0 || type == 1 || type == 2){
					intnet = new Intent(context, MessageDetailsActivity.class);
					intnet.putExtra("id", id);
					intnet.putExtra("type", type);
				}else{
					intnet = new Intent(context, ConsultDetailsActivity.class);
					intnet.putExtra("id", id);
				}
				PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intnet  , 0);  
	            Notification notify2 = new Notification.Builder(context)  
	                    .setSmallIcon(R.drawable.ic_launcher) 
	                    .setTicker("您有新短消息!") 
	                    .setContentTitle("优医患者") 
	                    .setContentText("您有新短消息!") 
	                    .setContentIntent(pendingIntent2) 
	                    .setNumber(1) 
	                    .getNotification();  
	            notify2.flags |= Notification.FLAG_AUTO_CANCEL;  
	            notify2.defaults=Notification.DEFAULT_SOUND;
	            manager.notify(1, notify2);  
	           
			} catch (Exception e) {
				e.printStackTrace();
			}
//        	processCustomMessage(context, bundle);
        }
		
		
//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.d(TAG, "接收Registration Id : " + regId);
//            //send the Registration Id to your server...
//                        
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//        	Log.d(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
////        	processCustomMessage(context, bundle);
//        
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "接收到推送下来的通知");
//            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//            Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);
//        	
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//        	//打开自定义的Activity
//        	Intent i = new Intent(context, Main.class);
//        	i.putExtras(bundle);
//        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        	context.startActivity(i);
//        	
//        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//            Log.d(TAG, "用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//        	Log.w(TAG, "" + intent.getAction() +" connected state change to "+connected);
//        } else {
//        	Log.d(TAG, "Unhandled intent - " + intent.getAction());
//        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	 
	}