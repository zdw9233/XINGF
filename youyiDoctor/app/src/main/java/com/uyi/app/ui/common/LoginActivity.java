package com.uyi.app.ui.common;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


@ContentView(R.layout.login)
public class LoginActivity extends BaseActivity {


    @ViewInject(R.id.headerView)
    private HeaderView headerView;

    @ViewInject(R.id.login_username)
    private EditText login_username;
    @ViewInject(R.id.login_password)
    private EditText login_password;

    @ViewInject(R.id.login_submit)
    private Button login_submit;
    @ViewInject(R.id.login_register)
    private TextView login_register;
    @ViewInject(R.id.login_get_password)
    private TextView login_get_password;


    @Override
    protected void onInitLayoutAfter() {
        headerView.showTitle(true);
        headerView.setTitle(getString(R.string.app_name));
        headerView.setHeaderBackgroundColor(getResources().getColor(R.color.blue));
        if (UserInfoManager.getLoginUserInfo(activity) != null) {
            finish();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (UserInfoManager.getLoginUserInfo(activity) != null) {
            finish();
        }
    }

    @OnClick({R.id.login_submit, R.id.login_get_password})
    public void login(View v) throws JSONException {
        if (v.getId() == R.id.login_get_password) {
            startActivity(new Intent(activity, GetPasswordActivity.class));
        } else if (v.getId() == R.id.login_submit) {
            String account = login_username.getText().toString();
            final String password = login_password.getText().toString();
            if (ValidationUtils.isNull(account) || ValidationUtils.isNull(password)) {
                T.showLong(activity, "账号密码不能为空!");
                return;
            }
            JSONObject params = new JSONObject();
            params.put("account", account);
            params.put("password", password);
            RequestManager.postObject(Constens.LOGIN_URL, activity, params, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject data) {
                    UserInfo userInfo = new UserInfo();
                    try {
                        userInfo.authToken = data.getString("authToken");
                        userInfo.type = data.getInt("type");
                        userInfo.userId = data.getInt("id");
                        userInfo.account = data.getString("account");
                        userInfo.realName = data.getString("realName");
                        userInfo.password = password;
                        userInfo.icon = data.getString("icon");
                        userInfo.address = data.has("address") ? data.getString("address") : null;
                        userInfo.city = data.has("city") ? data.getString("city") : null;
                        userInfo.beans = data.has("beans") ? data.getInt("beans") : null;
                        userInfo.consumedBeans = data.has("consumedBeans") ? data.getInt("consumedBeans") : null;
                        userInfo.lastLoginTime = data.getString("lastLoginTime");

                        userInfo.info = JSONObjectUtils.getString(data, "info");
                        userInfo.joinedGroup = JSONObjectUtils.getBoolean(data, "joinedGroup");

                        Set<String> tags = new HashSet<String>();
                        tags.add("bulletin");
                        tags.add("message_doctor_" + userInfo.userId);
                        JPushInterface.setTags(activity, tags, null);
                        if (userInfo.type != 1 && userInfo.type != 2) {
                            T.showLong(activity, "只能登陆专家和资深专家账户");
                            return;
                        }
                        UserInfoManager.setLoginUserInfo(activity, userInfo);
                        setResult(RESULT_OK);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected int getColorResourceId() {
        return R.color.blue;
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }

}
