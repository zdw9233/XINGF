package com.uyi.app.ui.health;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UYIApplication;
import com.uyi.app.UserInfoManager;
import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.app.service.UserService;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.health.adapter.FollowUpPayAdapter;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/10/20.
 */
@ContentView(R.layout.activity_follow_up_pay)
public class FollowUpPayActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener<Map<String,Object>> {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.zhifubaozhifu)
    private RelativeLayout zhifubaozhifu;
    @ViewInject(R.id.zfb_xuanzhonzhuangtai)
    private ImageView zfb_xuanzhonzhuangtai;
    @ViewInject(R.id.jiankandouzhifu)
    private RelativeLayout jiankandouzhifu;
    @ViewInject(R.id.jkd_xuanzhonzhuangtai)
    private ImageView jkd_xuanzhonzhuangtai;
    @ViewInject(R.id.shurujiankandou)
    private EditText shurujiankandou;
    @ViewInject(R.id.jiankandou)
    private TextView jiankandou;
    @ViewInject(R.id.jiandoushurukuan)
    private LinearLayout jiandoushurukuan;
    @ViewInject(R.id.heji)
    private TextView heji;
    @ViewInject(R.id.new_report)
    private FrameLayout new_report;
    private ArrayList<Map<String, Object>> datas;
    private LinearLayoutManager linearLayoutManager;
    private FollowUpPayAdapter followUpPayAdapter;
    int zfbnum = 0;
    int jkdnum = 0;
    String yanqifei = "";
    int id = 0;
    MessageConform messageCoonform;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).setTitle("支付").setTitleColor(getResources().getColor(R.color.blue)).showRight(false);
        messageCoonform = new MessageConform(activity, MessageConform.MessageType.ALERT);
        datas = new ArrayList<Map<String, Object>>();

        shurujiankandou.setText("");
        jiankandou.setText(UserInfoManager.getLoginUserInfo(FollowUpPayActivity.this).beans+"");
        linearLayoutManager = new LinearLayoutManager(this);
        followUpPayAdapter = new FollowUpPayAdapter(this);
        followUpPayAdapter.setOnItemClickListener(this);
        followUpPayAdapter.setDatas(datas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(followUpPayAdapter);
        getData();
    }
    @OnClick({
            R.id.zhifubaozhifu,
            R.id.jiankandouzhifu,
            R.id.new_report    //支付
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhifubaozhifu:
                if(zfb_xuanzhonzhuangtai.getVisibility() == View.GONE){
                    zfb_xuanzhonzhuangtai.setVisibility(View.VISIBLE);
                    zfbnum = 1;
                }else{
                    zfb_xuanzhonzhuangtai.setVisibility(View.GONE);
                    zfbnum = 0;
                }

                break;
            case R.id.jiankandouzhifu:
                if(jkd_xuanzhonzhuangtai.getVisibility() == View.GONE){
                    jkd_xuanzhonzhuangtai.setVisibility(View.VISIBLE);
                    jiandoushurukuan.setVisibility(View.VISIBLE);
                    jkdnum = 1;
                }else{
                    jkd_xuanzhonzhuangtai.setVisibility(View.GONE);
                    shurujiankandou.setText("");
                    jiandoushurukuan.setVisibility(View.GONE);
                    jkdnum = 0;
                }
                break;
            case R.id.new_report:
                if(zfbnum ==1 && jkdnum == 1){
                    if(!shurujiankandou.getText().toString().equals("")){

                    if(Double.parseDouble(shurujiankandou.getText().toString()) - Double.parseDouble(heji.getText().toString()) > 0){
                        T.showShort(FollowUpPayActivity.this,"请输入正确的健康豆数目！");
                    }else{
                        if(Double.parseDouble(shurujiankandou.getText().toString()) - UserInfoManager.getLoginUserInfo(FollowUpPayActivity.this).beans > 0){
                            T.showShort(FollowUpPayActivity.this,"健康豆数目不足！");
                        }else {
                            if (Double.parseDouble(shurujiankandou.getText().toString()) - Double.parseDouble(heji.getText().toString()) == 0) {
                                JSONObject  param = new JSONObject();


                                try {
                                    param.put("bean",shurujiankandou.getText().toString());
                                    param.put("planId",id);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(param.toString());
                                RequestManager.postObject(String.format(Constens.CUSTOMER_BUY_MOBILE_FOLLOW_UP_BEAN), activity, param,new Response.Listener<JSONObject>() {
                                    public void onResponse(JSONObject data) {
                                        try {
                                            L.d(TAG, data.toString());
                                            Loading.bulid(activity, null).dismiss();
                                            T.showShort(FollowUpPayActivity.this,"支付成功！");
                                            UserService.loadUserInfo(application);
                                            finish();
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
                                    }
                                });
                            } else {
                                Intent intent = new Intent(activity, FollowUpAlipayActivity.class);
                                intent.putExtra("money", Integer.parseInt(heji.getText().toString().substring(0,heji.getText().toString().length()-2))-Integer.parseInt(shurujiankandou.getText().toString())+"");
                                intent.putExtra("planId",id+"");
                                intent.putExtra("bean",shurujiankandou.getText().toString());
                                System.out.println(intent.toString());
                                startActivityForResult(intent, Constens.START_ALIPAY_FOR_RESULT);
                            }
                        }
                    }

                    }else{
                        T.showShort(FollowUpPayActivity.this,"请输入正确的健康豆数目！");
                    }
                }else if(zfbnum ==1 && jkdnum ==0){
                    Intent intent = new Intent(activity, FollowUpAlipayActivity.class);
                    intent.putExtra("money", Integer.parseInt(heji.getText().toString().substring(0,heji.getText().toString().length()-2))+"");
                    intent.putExtra("planId",id+"");
                    intent.putExtra("bean",0+"");
                    System.out.println(intent.toString());

                    startActivityForResult(intent, Constens.START_ALIPAY_FOR_RESULT);
                }else if(zfbnum ==0 && jkdnum ==1){
                    if(!shurujiankandou.getText().toString().equals("")){
                    if(Double.parseDouble(shurujiankandou.getText().toString()) - Double.parseDouble(heji.getText().toString()) != 0){
                        T.showShort(FollowUpPayActivity.this,"请输入正确的健康豆数目！");
                    }else{
                        if(Double.parseDouble(shurujiankandou.getText().toString()) - UserInfoManager.getLoginUserInfo(FollowUpPayActivity.this).beans > 0){
                            T.showShort(FollowUpPayActivity.this,"健康豆数目不足！");
                        }else{
                            JSONObject  param = new JSONObject();
                            try {
                                param.put("bean",shurujiankandou.getText().toString());
                                param.put("planId",id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println(param.toString());
                            RequestManager.postObject(String.format(Constens.CUSTOMER_BUY_MOBILE_FOLLOW_UP_BEAN), activity, param,new Response.Listener<JSONObject>() {
                                public void onResponse(JSONObject data) {
                                    try {
                                        L.d(TAG, data.toString());
                                        Loading.bulid(activity, null).dismiss();
                                        T.showShort(FollowUpPayActivity.this,"支付成功！");
                                        UserService.loadUserInfo(application);
                                        finish();
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
                                }
                            });
                        }
                    }
                }else{
                    T.showShort(FollowUpPayActivity.this,"请输入正确的健康豆数目！");
                }
                }else if(zfbnum ==0 && jkdnum ==0){
                    T.showShort(FollowUpPayActivity.this,"请选择支付方式！");
                }
                break;
        }
    }
    public void getData() {
        Loading.bulid(activity, null).show();
        RequestManager.getObject(Constens.GET_SERVER_THREE_PAY, activity, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {

                Loading.bulid(activity, null).dismiss();
                try {
                    JSONArray array = json.getJSONArray("unpaidTopThreeServiceJsonList");
                    if (json.has("deferredPrice")) {
                        yanqifei = json.getString("deferredPrice");
                    }
                    for (int i = 0; i < array.length(); i++) {
                        Map<String, Object> item = new HashMap<String, Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
                        item.put("id", jsonObject.getInt("id"));
                        item.put("planName", jsonObject.getString("planName"));
                        item.put("price", jsonObject.getString("price"));
                        if (i == 0) {
                            item.put("one", "Y");
                            id = jsonObject.getInt("id");
                        } else {
                            item.put("one", "N");
                        }
                        datas.add(item);
                    }
                    System.out.println(datas.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                followUpPayAdapter.notifyDataSetChanged();
                heji.setText(Double.parseDouble(datas.get(0).get("price").toString())-Double.parseDouble(yanqifei)+"");
            }
        });

    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

    @Override
    public void onItemClick(int position, Map<String, Object> data) {
        id = (int) data.get("id");
      ArrayList<Map<String, Object>> datas1 =new ArrayList<>();
        datas1 = datas;
        System.out.println(datas1.toString());
        datas = new ArrayList<>() ;
        System.out.println(datas.toString());
        for (int i = 0; i < datas1.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", datas1.get(i).get("id"));
            item.put("planName",datas1.get(i).get("planName"));
            item.put("price", datas1.get(i).get("price"));
            if(i ==position){
                item.put("one", "Y");
            }else{
                item.put("one", "N");
            }
            datas.add(item);
            System.out.println(datas.toString());
        }
        followUpPayAdapter.setDatas(datas);
        followUpPayAdapter.notifyItemChanged(position);
        heji.setText(Double.parseDouble(datas.get(position).get("price").toString())-Double.parseDouble(yanqifei)+"");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constens.START_ALIPAY_FOR_RESULT){
            if(resultCode == RESULT_OK){
                String orderNo = data.getStringExtra("orderNo");
                String resultStatus = data.getStringExtra("resultStatus");
                if(ValidationUtils.equlse(resultStatus, "9000")){//充值成功
                    T.showShort(activity, "付款成功!");
//					getRechargeBeans(orderNo);
//                    UserService.loadUserInfo(application);
                    UserService.loadUserInfo(application);
                }else if(ValidationUtils.equlse(resultStatus, "8000")){
                    messageCoonform.setContent("支付系统延迟,购买成功以系统为准!");
                    messageCoonform.show();
                }else{
                    T.showShort(activity, "付款失败!");
                }
            }
        }
    }
}