package com.uyi.app.ui.healthinfo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 2016/8/24.
 */
public class BasicInfoFragment extends BaseFragment{
    @ViewInject(R.id.register_height)
    private EditText register_height;
    @ViewInject(R.id.register_weight)
    private EditText register_weight;
    @ViewInject(R.id.register_info_one_jiankangzhuangkuang)
    private EditText register_info_one_jiankangzhuangkuang;
    @ViewInject(R.id.register_info_one_submit)
    private Button register_info_one_submit;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;
    int chronicDiseaseType = 0;
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_update_basic_info;
    }

    @Override
    protected void onInitLayoutAfter() {
        RequestManager.getObject(Constens.ACCOUNT_BASIC_INFO, getActivity(), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.e("ONE", data.toString());
                    register_height.setText(JSONObjectUtils.getString(data, "height"));
                    register_weight.setText(JSONObjectUtils.getString(data, "weight"));
                    register_info_one_jiankangzhuangkuang.setText(JSONObjectUtils.getString(data, "healthCondition"));

                    if(!data.getString("chronicDiseaseType").equals("null")){
                        chronicDiseaseType = data.getInt("chronicDiseaseType");
                        int[] ints = {R.id.rb_gxy, R.id.rb_tnb, R.id.rb_gxy_tnb};
                        if(chronicDiseaseType !=0)
                            radioGroup.check(ints[chronicDiseaseType - 1]);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @OnClick(
     R.id.register_info_one_submit
    )
    public void onClick(View view) {
                try{
                    JSONObject params = new JSONObject();
                    params.put("height", register_height.getText().toString());
                    params.put("weight", register_weight.getText().toString());
                    int radioButtonId = radioGroup.getCheckedRadioButtonId();           //有啥子病
                    if (radioButtonId == R.id.rb_gxy) {
                        params.put("chronicDiseaseType",1);
                    } else if (radioButtonId == R.id.rb_tnb) {
                        params.put("chronicDiseaseType",2);
                    } else if (radioButtonId == R.id.rb_gxy_tnb) {
                        params.put("chronicDiseaseType",3);
                    }
                    params.put("healthCondition", register_info_one_jiankangzhuangkuang.getText().toString());
                    Loading.bulid(getActivity(), null).show();
                    RequestManager.postObject(Constens.ACCOUNT_BASIC_INFO_UPDATE, getActivity(), params, null, new RequestErrorListener() {
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
        }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
