package com.uyi.app.ui.health;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UYIApplication;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.recharge.PayResult;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 阿里支付
 * @author user
 *
 */
public class FollowUpAlipayActivity  extends BaseActivity{
    String orderNo;
    @Override
    protected void onInitLayoutAfter() {
        String money = getIntent().getStringExtra("money");
        String bean = getIntent().getStringExtra("bean");
        String panyId = getIntent().getStringExtra("planId");
        Loading.bulid(activity, null).show();
        JSONObject  param = new JSONObject();
        JSONObject  params = new JSONObject();
        try {


            params.put("money",money);
            params.put("planId",panyId);
            params.put("bean",bean);
            param.put("PayTopThreeJson",params);
        } catch (JSONException e) {
            e.printStackTrace();
        }L.d(TAG, param.toString());
        RequestManager.postObject(Constens.CUSTOMER_BUY_MOBILE_FOLLOW_UP, activity, params,new Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.d(TAG, data.toString());
                    Loading.bulid(activity, null).dismiss();
                    orderNo = data.getString("orderNo");
                    startPay(data.getString("result"));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                }
            }
        },new RequestErrorListener() {
            @Override
            public void requestError(VolleyError e) {
                Loading.bulid(activity, null).dismiss();
                T.show(UYIApplication.getContext(), ErrorCode.getErrorByNetworkResponse(e.networkResponse), -1);
                finish();
            }
        });
    }
    public void startPay(final String orderInfo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(orderInfo);
                mHandler.sendMessage(mHandler.obtainMessage(1, result));
            }
        }) .start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((String) msg.obj);
            String resultInfo = payResult.getResult();
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
            Intent intent = new Intent();
            intent.putExtra("orderNo", orderNo);
            L.d(TAG, resultInfo);
            intent.putExtra("resultStatus", resultStatus);
            setResult(RESULT_OK,intent);
            finish();
//				if (TextUtils.equals(resultStatus, "9000")) {
//					T.showShort(activity,  "支付成功");
//				} else {
////					// 判断resultStatus 为非“9000”则代表可能支付失败
////					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//					if (TextUtils.equals(resultStatus, "8000")) {
//						T.showShort(activity,  "支付结果确认中");
//					} else {
//						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//						T.showShort(activity, "支付失败");
//					}
//				}
        };
    };

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {

    }

}
