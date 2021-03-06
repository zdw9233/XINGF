package com.uyi.app.ui.personal.schedule;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.service.MessageService;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.dialog.MessageConform.MessageType;
import com.uyi.app.ui.dialog.MessageConform.OnMessageClick;
import com.uyi.app.ui.dialog.MessageConform.Result;
import com.uyi.app.utils.DateUtils;
import com.uyi.doctor.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


/**
 * 日程
 *
 * @author user
 */

@ContentView(R.layout.schedule)
public class ScheduleActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.new_schedule_layout_start)
    private LinearLayout new_schedule_layout_start;
    @ViewInject(R.id.schedule_content_layout)
    private LinearLayout schedule_content_layout;

    @ViewInject(R.id.schedule_layout)
    private LinearLayout schedule_layout;

    @ViewInject(R.id.schedule_left)
    private ImageView schedule_left;
    @ViewInject(R.id.schedule_time)
    private TextView schedule_time;
    @ViewInject(R.id.schedule_right)
    private ImageView schedule_right;

    @ViewInject(R.id.schedule_content)
    private TextView schedule_content;
    @ViewInject(R.id.schedule_type)
    private TextView schedule_type;

    MessageConform conform;
    Calendar cal = Calendar.getInstance();

    /**
     * 1: 专属咨询
     * 2: 咨询(线下检查/随访)
     * 3: 自发布日程
     */
    int type;
    /**
     * Type: 1 专属咨询Id,
     * Type: 2 咨询 Id
     * Type: 3 无此参数
     */
    int entityId;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("日程").setTitleColor(getResources().getColor(R.color.blue));
        pageSize = 1;
        schedule_time.setText(DateUtils.toDate(cal.getTime(), "yyyy年MM月dd日"));
//        schedule_time.setText( "2016年09月07日");
        load();
    }


    /**
     * 加一天 /减一天
     *
     * @param isAdd
     */
    public void selectedDay(boolean isAdd) {
        if (isAdd) {
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
        } else {
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        }
        load();
    }

    public void load() {
        schedule_layout.removeAllViews();
        Loading.bulid(activity, null).show();
        String sdate = DateUtils.toDate(cal.getTime());
        String edate = DateUtils.toDate(new Date((cal.getTimeInMillis() + (1000 * 60 * 60 * 24))));
        RequestManager.getObject(String.format(Constens.ACCOUNT_SCHEDULES, sdate, edate, pageNo, 100), activity, new Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                Loading.bulid(activity, null).dismiss();
                schedule_time.setText(DateUtils.toDate(cal.getTime(), "yyyy年MM月dd日"));
                try {
                    totalPage = data.getInt("pages");
                    JSONArray array = data.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        addSchedule(array.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    public void addSchedule(JSONObject jsonObject) {
        try {
            View view = LayoutInflater.from(activity).inflate(R.layout.schedule_item, schedule_layout, false);
            TextView schedule_type = (TextView) view.findViewById(R.id.schedule_type);
            TextView schedule_content = (TextView) view.findViewById(R.id.schedule_content);
            TextView schedule_delete = (TextView) view.findViewById(R.id.schedule_delete);
            schedule_delete.setTag(jsonObject.getInt("id"));
            schedule_delete.setOnClickListener(this);
            type = jsonObject.getInt("type");
            if (type == 1) {
                entityId = jsonObject.getInt("entityId");
                schedule_type.setText("专属咨询");
            } else if (type == 2) {
                entityId = jsonObject.getInt("entityId");
                schedule_type.setText("咨询");
            } else if (type == 3) {
                schedule_type.setText("自发布日程");
            }
            schedule_content.setText(jsonObject.getString("content"));
            schedule_layout.addView(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.new_schedule_layout_start, R.id.schedule_left, R.id.schedule_right})
    public void click(View v) {
        if (v.getId() == R.id.new_schedule_layout_start) {
            startActivityForResult(new Intent(activity, AddScheduleActivity.class), 0x100);
        } else if (v.getId() == R.id.schedule_left) {
            selectedDay(false);
        } else if (v.getId() == R.id.schedule_right) {
            selectedDay(true);
        }
    }


    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }


    @Override
    public void onClick(View v) {
        final int id = (int) v.getTag();
        conform = new MessageConform(activity, MessageType.CONFORM);
        conform.setContent("确认要删除吗?");
        conform.setOnMessageClick(new OnMessageClick() {
            @Override
            public void onClick(Result result, Object object) {
                if (result == Result.OK) {
                    RequestManager.deleteObject(Constens.ACCOUNT_SCHEDULE + "/" + id, activity, null, new Listener<JSONObject>() {
                        public void onResponse(JSONObject arg0) {
                            load();
                            MessageService.loadMessages(ScheduleActivity.this, 1);
                        }
                    }, new RequestErrorListener() {

                        @Override
                        public void requestError(VolleyError e) {
                            load();

                        }
                    });
                }
            }
        });
        conform.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x100 && resultCode == RESULT_OK) {
            load();
            MessageService.loadMessages(ScheduleActivity.this, 1);
        }
    }
}
