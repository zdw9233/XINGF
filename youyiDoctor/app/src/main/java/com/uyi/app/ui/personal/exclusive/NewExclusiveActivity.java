package com.uyi.app.ui.personal.exclusive;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ErrorCode;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter.IOnItemSelectListener;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新建专属咨询
 *
 * @author user
 */
@ContentView(R.layout.new_exclusive)
public class NewExclusiveActivity extends BaseActivity implements IOnItemSelectListener {

    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.header)
    private RoundedImageView header;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.info)
    private TextView info;

    @ViewInject(R.id.new_exclusive_riqi_layout)
    private LinearLayout new_exclusive_riqi_layout;
    @ViewInject(R.id.new_exclusive_riqi)
    private TextView new_exclusive_riqi;
    @ViewInject(R.id.new_exclusive_time_start)
    private TextView new_exclusive_time_start;
    @ViewInject(R.id.new_exclusive_time_end)
    private TextView new_exclusive_time_end;
    @ViewInject(R.id.new_exclusive_address)
    private EditText new_exclusive_address;
    @ViewInject(R.id.new_exclusive_max)
    private EditText new_exclusive_max;
    @ViewInject(R.id.new_exclusive_beans)
    private EditText new_exclusive_beans;
    @ViewInject(R.id.new_exclusive_desc)
    private EditText new_exclusive_desc;

    @ViewInject(R.id.new_exclusive_submit)
    private Button new_exclusive_submit;
    @ViewInject(R.id.new_exclusive_delete)
    private Button new_exclusive_delete;
    private SpinerPopWindow spinerPopWindow;
    private List<String> lists = new ArrayList<String>();
    private UserInfo userInfo;

    int id;//为0则为添加  否则为修改
    int selectTime = 1;

    @Override
    protected void onInitLayoutAfter() {
        id = getIntent().getIntExtra("id", 0);
        for (int i = 0; i < 24; i++) {
            lists.add((i < 10 ? "0" + i : i) + ":00");
        }
        spinerPopWindow = new SpinerPopWindow(activity);
        spinerPopWindow.setItemListener(this);
        spinerPopWindow.refreshData(lists, 0);
        String title = "新建专属咨询";
        if (id != 0) {
            title = "修改专属咨询";
            new_exclusive_submit.setText("确认修改");
        }
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle(title).setTitleColor(getResources().getColor(R.color.blue));
        userInfo = UserInfoManager.getLoginUserInfo(activity);
        name.setText(userInfo.realName);
        info.setText(userInfo.info);
        ImageCacheManager.loadImage(userInfo.icon, ImageCacheManager.getImageListener(header, null, null));

        if (id != 0) {
            new_exclusive_beans.setEnabled(false);
            Loading.bulid(activity, null).show();
            RequestManager.getObject(String.format(Constens.ACCOUNT_EXCLUSIVE_CONSULT, id), activity, new Listener<JSONObject>() {
                public void onResponse(JSONObject data) {
                    try {
                        Loading.bulid(activity, null).dismiss();
                        new_exclusive_address.setText(data.getString("address"));
                        new_exclusive_beans.setText(data.getString("beans"));
                        new_exclusive_desc.setText(data.getString("desc"));
                        new_exclusive_max.setText(data.getString("people"));

                        new_exclusive_riqi.setText(data.getString("start").split(" ")[0]);
                        new_exclusive_time_start.setText(data.getString("start").split(" ")[1]);
                        new_exclusive_time_end.setText(data.getString("end").split(" ")[1]);


                        if (data.getBoolean("canBeDeleted")) {
                            new_exclusive_delete.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 取消预约
     *
     * @param view
     */
    @OnClick(R.id.new_exclusive_delete)
    public void delete(View view) {
        RequestManager.deleteObject(Constens.ACCOUNT_EXCLUSIVE_CONSULT_ADD + "/" + id, activity, null, new Listener<JSONObject>() {
            public void onResponse(JSONObject arg0) {
                Loading.bulid(activity, null).dismiss();
                setResult(RESULT_OK);
                finish();
            }
        }, new RequestErrorListener() {
            public void requestError(VolleyError e) {
                Loading.bulid(activity, null).dismiss();
                setResult(RESULT_OK);
                finish();
            }
        });
    }


    @OnClick({R.id.new_exclusive_riqi_layout, R.id.new_exclusive_time_start, R.id.new_exclusive_time_end, R.id.new_exclusive_submit})
    public void selectDateTime(View v) {
        if (v.getId() == R.id.new_exclusive_riqi_layout) {
            L.d(TAG, v.toString());
            Intent intent = new Intent(activity, DatePickerActivity.class);
            intent.putExtra("currentDate", new_exclusive_riqi.getText().toString().trim());
            startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
        } else if (v.getId() == R.id.new_exclusive_time_start) {
            selectTime = 1;
            spinerPopWindow.setWidth(v.getWidth());
            spinerPopWindow.showAsDropDown(v);
        } else if (v.getId() == R.id.new_exclusive_time_end) {
            selectTime = 2;
            spinerPopWindow.setWidth(v.getWidth());
            spinerPopWindow.showAsDropDown(v);
        } else if (v.getId() == R.id.new_exclusive_submit) {
            try {
                String date = new_exclusive_riqi.getText().toString();
                String startTime = new_exclusive_time_start.getText().toString();
                String endTime = new_exclusive_time_end.getText().toString();
                String address = new_exclusive_address.getText().toString();
                String beans = new_exclusive_beans.getText().toString();
                String desc = new_exclusive_desc.getText().toString();
                String people = new_exclusive_max.getText().toString();
                if (ValidationUtils.isNull(date, address, beans, desc, endTime, startTime, people)) {
                    T.showShort(activity, "资料未填写完整!");
                    return;
                }
                JSONObject params = new JSONObject();
                params.put("address", address);
                params.put("beans", beans);
                params.put("desc", desc);
                params.put("startTime", date + " " + startTime);
                params.put("endTime", date + " " + endTime);
                params.put("people", people);

                Loading.bulid(activity, null).show();
                String url = Constens.ACCOUNT_EXCLUSIVE_CONSULT_ADD;
                if (id != 0) {
                    url = Constens.ACCOUNT_EXCLUSIVE_CONSULT_ADD + "/" + id;
                }
                RequestManager.postObject(url, activity, params,
                        new Listener<JSONObject>() {
                            public void onResponse(JSONObject data) {
                                Loading.bulid(activity, null).dismiss();
                                setResult(RESULT_OK);
                                finish();
                            }
                        }, new RequestErrorListener() {
                            public void requestError(VolleyError e) {
                                Loading.bulid(activity, null).dismiss();
                                if (e.networkResponse != null) {
                                    T.showShort(activity, ErrorCode.getErrorByNetworkResponse(e.networkResponse));
                                } else {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        });
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.START_ACTIVITY_FOR_RESULT && resultCode == RESULT_OK) {
            if (data.hasExtra("date")) {
                new_exclusive_riqi.setText(data.getStringExtra("date"));
            }
        }
    }


    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }


    @Override
    public void onItemClick(int pos) {
        if (selectTime == 1) {
            new_exclusive_time_start.setText(lists.get(pos));
        } else if (selectTime == 2) {
            new_exclusive_time_end.setText(lists.get(pos));
        }
    }

}
