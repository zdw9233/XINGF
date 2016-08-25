package com.uyi.app.ui.healthinfo;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.ui.common.model.AbnormalEvent;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ThinkPad on 2016/8/24.
 */
public class BloodInfoFragment extends BaseFragment
{
    @ViewInject(R.id.regester_info_xueguanfashengshijian)
    private TextView regester_info_xueguanfashengshijian;
    @ViewInject(R.id.regester_info_xueguanfashengleixing)
    private Spinner regester_info_xueguanfashengleixing;
    @ViewInject(R.id.register_info_qitaleixin)
    private EditText register_info_qitaleixin;
    @ViewInject(R.id.register_info_four_submit)
    private Button register_info_four_submit;
    private List<String> listdata = new ArrayList<>();
    private List<String> listtype = new ArrayList<>();
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_update_blood_info;
    }

    @Override
    protected void onInitLayoutAfter() {
        RequestManager.getArray(Constens.ABNORMAL_EVENT, this, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                com.alibaba.fastjson.JSONArray objects = JSON.parseArray(jsonArray.toString());
                for (int i = 0; i < objects.size(); i++) {
                    AbnormalEvent event = JSON.parseObject(objects.getString(i), AbnormalEvent.class);
                    listdata.add(event.name);
                    listtype.add(event.id+"");
                }
                regester_info_xueguanfashengleixing.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.layout_spinner_item, R.id.textView2, listdata));

                RequestManager.getObject(Constens.ACCOUNT_BLOOD_INFO, getActivity(), new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject data) {
                        try {
                            L.e("ONE", data.toString()+"_____"+listtype.toString());
                            regester_info_xueguanfashengshijian.setText(JSONObjectUtils.getString(data, "time"));
                            register_info_qitaleixin.setText(JSONObjectUtils.getString(data, "description"));
                            String eventId = JSONObjectUtils.getString(data, "eventId");
                            for (int i =0; i<listtype.size();i++){
                                if(eventId.equals(listtype.get(i))){
                                    regester_info_xueguanfashengleixing.setSelection(i);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });








    }
    @OnClick(
            {R.id.register_info_four_submit,R.id.regester_info_xueguanfashengshijian}
    )
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_info_four_submit:
                try {
                    JSONObject params = new JSONObject();
                    params.put("time", regester_info_xueguanfashengshijian.getText().toString());
                    params.put("eventId", listtype.get(regester_info_xueguanfashengleixing.getSelectedItemPosition()));
                    L.e("TWO",  listtype.get(regester_info_xueguanfashengleixing.getSelectedItemPosition()));
                    params.put("description", register_info_qitaleixin.getText().toString());
                    Loading.bulid(getActivity(), null).show();
                    RequestManager.postObject(Constens.ACCOUNT_BLOOD_INFO_UPDATE, getActivity(), params, null, new RequestErrorListener() {
                        @Override
                        public void requestError(VolleyError e) {
                            Loading.bulid(getActivity(), null).dismiss();
                            if (e.networkResponse != null) {
                                if (e.networkResponse.statusCode == 204) {
                                    T.showShort(getActivity(), "修改成功!");
                                } else {
                                    T.showShort(getActivity(), ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                }
                            } else {
                                T.showShort(getActivity(), "修改成功!");
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.regester_info_xueguanfashengshijian:
                Intent intent
                        = new Intent(getActivity(), DatePickerActivity.class);
                intent.putExtra("currentDate", regester_info_xueguanfashengshijian.getText().toString().trim());
                startActivityForResult(intent, 400);

                break;
        }
    }
    private String[] toStringArray(Set<String> strings) {
        String[] strings1 = new String[strings.size()];
        int i = 0;
        for (String s : strings) {
            strings1[i] = s;
            i++;
        }
        return strings1;
    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400 && resultCode == -1) {
                if (data.hasExtra("date")) {
                    regester_info_xueguanfashengshijian.setText(data.getStringExtra("date"));
                }
        }
//
    }
}
