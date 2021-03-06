package com.uyi.app.ui.newCalendar;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.FragmentHealthListManager;
import com.uyi.app.ui.health.NewFollowUpDetailsActivity;
import com.uyi.app.ui.personal.schedule.DatePickerActivity;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.T;
import com.uyi.doctor.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/10/12.
 */
@ContentView(R.layout.activity_updata_follow_up)
public class CalendarAddActivity extends BaseActivity{
    @ViewInject(R.id.layout1) private RelativeLayout layout1;
    @ViewInject(R.id.fanganxz) private TextView fanganxz;
    @ViewInject(R.id.layout2) private RelativeLayout layout2;
    @ViewInject(R.id.duoxuanlayout) private LinearLayout duoxuanlayout;
    @ViewInject(R.id.fangansj) private TextView fangansj;
    @ViewInject(R.id.new_report) private FrameLayout new_report;
    @ViewInject(R.id.shijianbiaoji) private ImageView shijianbiaoji;
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private List<CheckBox> checkboxs = new ArrayList<>();


    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(true).setTitle("三甲随访计划").setTitleColor(getResources().getColor(R.color.blue));
        fangansj.setText(DateUtils.toDate(Calendar.getInstance().getTime(), "yyyy-MM-dd"));
        getData();
//        initData();

    }

    private void initData() {
        for (int z = 0; z < checkboxs.size(); z++) {
            checkboxs.get(z).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = "";
                    for (int j = 0; j < checkboxs.size(); j++) {
                            if(checkboxs.get(j).isChecked()){
                                name += checkboxs.get(j)+",";
                            }
                    }
                    fanganxz.setText(name);
                }
            });
        }
    }

    private void getData() {
        Loading.bulid(activity, null).show();
        RequestManager.getArray(String.format(Constens.GET_SERVER_XIANMU,FragmentHealthListManager.customer), activity,new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray json) {
                System.out.println(json.toString());
                Loading.bulid(activity, null).dismiss();
                try {

                    if(json.length()>0){
                        for (int i = 0; i < json.length(); i++) {
                            Map<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject = json.getJSONObject(i);
                            CheckBox box = new CheckBox(CalendarAddActivity.this);
                            item.put("id", jsonObject.getInt("id"));
                            item.put("name", jsonObject.getString("name"));
                            item.put("price", jsonObject.getString("price"));
                            box.setText(jsonObject.getString("name"));
                            duoxuanlayout.addView(box);
                            checkboxs.add(box);
                            datas.add(item);
                        }
                        for (int z = 0; z < checkboxs.size(); z++) {
                            checkboxs.get(z).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String name = "";
                                    for (int j = 0; j < checkboxs.size(); j++) {
                                        if(checkboxs.get(j).isChecked()){
                                            name += checkboxs.get(j).getText().toString()+",";
                                        }
                                    }
                                    fanganxz.setText(name);
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }
    @OnClick({
            R.id.layout1, //选择日期
            R.id.layout2,       //选择方案
            R.id.new_report      //确定
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout1:
                Intent intent = new Intent(this, DatePickerActivity.class);
                intent.putExtra("currentDate", fangansj.getText().toString().trim());
                startActivityForResult(intent, Constens.START_ACTIVITY_FOR_RESULT);
                break;
            case R.id.layout2:
              if(duoxuanlayout.getVisibility() == View.GONE){
                  duoxuanlayout.setVisibility(View.VISIBLE);
                  shijianbiaoji.setBackgroundResource(R.drawable.xiala);
              }else{
                  duoxuanlayout.setVisibility(View.GONE);
                  shijianbiaoji.setBackgroundResource(R.drawable.shangshou);
              }
                break;
            case R.id.new_report:
             int num = 0;
                for (int j = 0; j <checkboxs.size(); j++) {
                    if (checkboxs.get(j).isChecked()) {
                       num = 1;
                    }
                }
            if(num == 0){
                T.showShort(CalendarAddActivity.this,"至少选择一个事件！");
            }else{
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("time",fangansj.getText().toString());
                item.put("week",getWeekStr(fangansj.getText().toString()));

                ArrayList<Map<String, Object>> datas1 = new ArrayList<Map<String, Object>>();
                for (int j = 0; j <checkboxs.size(); j++) {
                    if (checkboxs.get(j).isChecked()) {
                        Map<String, Object> item1 = new HashMap<String, Object>();
                        item1.put("id", datas.get(j).get("id").toString());
                        item1.put("name", datas.get(j).get("name").toString());
                        item1.put("price", datas.get(j).get("price").toString());
                        datas1.add(item1);
                    }
                }
                item.put("itemListJsons",datas1);
                System.out.println(item.toString());
                NewFollowUpDetailsActivity.datas.add(item);
                setResult(RESULT_OK);
                finish();
            }
                break;

        }
    }
    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
   private String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }
    private Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    private String getWeekStr(String sdate){
        String str = "";
        str = getWeek(sdate);
        if("1".equals(str)){
            str = "星期日";
        }else if("2".equals(str)){
            str = "星期一";
        }else if("3".equals(str)){
            str = "星期二";
        }else if("4".equals(str)){
            str = "星期三";
        }else if("5".equals(str)){
            str = "星期四";
        }else if("6".equals(str)){
            str = "星期五";
        }else if("7".equals(str)){
            str = "星期六";
        }
        return str;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constens.START_ACTIVITY_FOR_RESULT && resultCode == -1) {
            if (data.hasExtra("date")) {
                try {
                    long stime = DateUtils.toDateByString(data.getStringExtra("date"), Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
//                        if (stime >= System.currentTimeMillis()) {
//                            T.showShort(this, "起始时间不能大于当前日期");
//                            return;
//                        }
                    fangansj.setText(data.getStringExtra("date"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
