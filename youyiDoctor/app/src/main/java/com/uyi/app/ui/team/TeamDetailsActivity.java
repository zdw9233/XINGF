package com.uyi.app.ui.team;


import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.team.adapter.DocterDtailsAdapter;
import com.uyi.app.ui.team.adapter.HealthTeamDoctorAdapter;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@ContentView(R.layout.team_details)
public class TeamDetailsActivity extends BaseActivity implements android.widget.AdapterView.OnItemClickListener, AbstractSpinerAdapter.IOnItemSelectListener {
    private ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private HealthTeamDoctorAdapter adapter;
    Map<String, Object> mydoctor;
    private ArrayList<Pinjia> datapingjia = new ArrayList<Pinjia>();

    @ViewInject(R.id.lay)
    private LinearLayout lay;
    @ViewInject(R.id.team)
    private TextView team;
    @ViewInject(R.id.team_group_logo)
    private ImageView team_group_logo;
    @ViewInject(R.id.team_group_info)
    private TextView team_group_info;
    @ViewInject(R.id.team_group_name)
    private TextView team_group_name;
    @ViewInject(R.id.team_zl_group_logo)
    private RoundedImageView team_zl_group_logo;
    @ViewInject(R.id.team_zl_name)
    private TextView team_zl_name;
    @ViewInject(R.id.team_zl_phone)
    private TextView team_zl_phone;
    @ViewInject(R.id.team_zl_group_info)
    private TextView team_zl_group_info;
    @ViewInject(R.id.tezm_money)
    private TextView tezm_money;
    @ViewInject(R.id.team_zixunshu)
    private TextView team_zixunshu;
    @ViewInject(R.id.team_zonzixunshu)
    private TextView team_zonzixunshu;
    @ViewInject(R.id.team_group_docter)
    private ListView team_group_docter;
    @ViewInject(R.id.team_goup_jiaru)
    private Button team_goup_jiaru;
    @ViewInject(R.id.team_details_rating)
    private RatingBar team_details_rating;
    String id;//健康团队id
    String detail;//团队详情
    int doctorId;
    DocterDtailsAdapter docterDtailsAdapter;
    private SpinerPopWindow spinerPopWindow;
    ArrayList<String> myTeam = new ArrayList<>();
    ArrayList<String> myTeamname = new ArrayList<>();

    @OnClick({R.id.team, R.id.back})
    private void click(View view) {
        if (view.getId() == R.id.team) {
            spinerPopWindow.setWidth(team.getWidth());
            spinerPopWindow.refreshData(myTeamname, 0);
            spinerPopWindow.showAsDropDown(team);
        }
        if (view.getId() == R.id.back) {
            finish();
        }
    }

    @Override
    protected void onInitLayoutAfter() {
        id = getIntent().getStringExtra("id");
        myTeam = getIntent().getStringArrayListExtra("ids");
        myTeamname = getIntent().getStringArrayListExtra("names");
        System.out.println(id + "_____________");
        spinerPopWindow = new SpinerPopWindow(activity);
        spinerPopWindow.setItemListener(this);
        spinerPopWindow.refreshData(myTeamname, 1);
        adapter = new HealthTeamDoctorAdapter(activity, datas, R.layout.item_team_docter_list);
        team_group_docter.setAdapter(adapter);
        team_group_docter.setOnItemClickListener(this);
        getdatas();


    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
        lay.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        mydoctor = new HashMap<String, Object>();

        mydoctor.clear();
        Loading.bulid(activity, null).show();
        doctorId = (int) datas.get(arg2).get("id");
        System.out.println(doctorId + "++++++++++++++++++++++++++");
        RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_DOCTER_DETAILS, doctorId), activity, new Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                System.out.println(data.toString());
                Loading.bulid(activity, null).dismiss();

                try {
                    mydoctor.put("id", data.getInt("id"));
                    mydoctor.put("type", data.getInt("type"));
                    mydoctor.put("realName", data.getString("realName"));
                    mydoctor.put("icon", data.getString("icon"));
                    mydoctor.put("info", data.getString("info"));
                    mydoctor.put("isLeft", data.getBoolean("isLeft"));
                    mydoctor.put("currentNum", data.getInt("currentNum"));
                    mydoctor.put("totalNum", data.getInt("totalNum"));
                    mydoctor.put("star", (float) data.getDouble("star"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((int) mydoctor.get("type") == 1) {

//						ZjDialog dialog = new ZjDialog(activity, mydoctor);
//						dialog.show();
                    LayoutInflater inflaterDl = LayoutInflater.from(activity);

                    RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.dialog_item_zhuanjia, null);

                    //对话框
                    final Dialog dialog = new AlertDialog.Builder(activity).create();
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    dialogWindow.setGravity(Gravity.TOP);

					         /*
                              * lp.x与lp.y表示相对于原始位置的偏移.
					          * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
					          * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
					          * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
					          * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
					          * 当参数值包含Gravity.CENTER_HORIZONTAL时
					          * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
					          * 当参数值包含Gravity.CENTER_VERTICAL时
					          * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
					          * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
					          * Gravity.CENTER_VERTICAL.
					          * 
					          * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
					          * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
					          * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
					          */

                    lp.y = 100; // 新位置Y坐标


                    // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
                    // dialog.onWindowAttributesChanged(lp);
                    dialogWindow.setAttributes(lp);
                    dialog.show();
                    dialog.getWindow().setContentView(layout);
                    final EndlessRecyclerView recyclerView = (EndlessRecyclerView) layout.findViewById(R.id.doctor_recyclerView);
                    RoundedImageView icon = (RoundedImageView) layout.findViewById(R.id.item_zhuanjia_icon);

                    TextView name = (TextView) layout.findViewById(R.id.item_zhuanjia_name);
                    RatingBar rat = (RatingBar) layout.findViewById(R.id.docter_details_rating);
                    TextView info = (TextView) layout.findViewById(R.id.item_zhuanjia_info);
                    TextView byzx = (TextView) layout.findViewById(R.id.team_zixunshu);
                    TextView zzx = (TextView) layout.findViewById(R.id.team_zonzixunshu);
                    ImageCacheManager.loadImage((mydoctor.get("icon").toString()), ImageCacheManager.getImageListener(icon, null, null));
                    name.setText(mydoctor.get("realName").toString());
                    info.setText(mydoctor.get("info").toString());
                    byzx.setText(mydoctor.get("currentNum") + "");
                    zzx.setText(mydoctor.get("totalNum") + "");
                    rat.setRating((float) mydoctor.get("star"));

                    docterDtailsAdapter = new DocterDtailsAdapter(activity);
                    docterDtailsAdapter.setDatas(datapingjia);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    recyclerView.setAdapter(docterDtailsAdapter);

                    RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_DOCTER_DETAILS_PINJIA, mydoctor.get("id"), pageNo, pageSize), activity, new Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject data) {
                            try {
                                datapingjia.clear();
                                totalPage = data.getInt("pages");
                                JSONArray array = data.getJSONArray("results");
                                for (int i = 0; i < array.length(); i++) {
                                    Pinjia consult = new Pinjia();
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    consult.star = jsonObject.getInt("star");
                                    consult.icon = jsonObject.getString("icon");
                                    consult.date = jsonObject.getString("date");
                                    consult.comment = jsonObject.getString("comment");

//								
                                    datapingjia.add(consult);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            docterDtailsAdapter.notifyDataSetChanged();
//								

//										if(pageNo <= totalPage){
//											isLooding = true;
//											pageNo ++;
//										}else{
                            recyclerView.setRefreshing(false);

//										}
                        }
                    });

                    //取消按钮
                    Button exit = (Button) layout.findViewById(R.id.doctor_exit);
                    exit.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                }
                if ((int) mydoctor.get("type") == 2) {
                    LayoutInflater inflaterDl1 = LayoutInflater.from(activity);

                    RelativeLayout layout1 = (RelativeLayout) inflaterDl1.inflate(R.layout.dialog_item_zishenzhuanjia, null);

                    //对话框
                    final Dialog dialog1 = new AlertDialog.Builder(activity).create();
                    Window dialogWindow = dialog1.getWindow();
                    WindowManager.LayoutParams lp1 = dialogWindow.getAttributes();
                    dialogWindow.setGravity(Gravity.TOP);
                    lp1.y = 100; // 新位置Y坐标
                    dialogWindow.setAttributes(lp1);
                    dialog1.show();
                    dialog1.getWindow().setContentView(layout1);

                    RoundedImageView icon = (RoundedImageView) layout1.findViewById(R.id.item_zishenzhuanjia_icon);
                    TextView name = (TextView) layout1.findViewById(R.id.item_zishenzhuanjia_name);
                    TextView info = (TextView) layout1.findViewById(R.id.item_zishenzhuanjia_info);
                    ImageCacheManager.loadImage((mydoctor.get("icon").toString()), ImageCacheManager.getImageListener(icon, null, null));
                    name.setText(mydoctor.get("realName").toString());
                    info.setText(mydoctor.get("info").toString());

                    //取消按钮
                    Button exit = (Button) layout1.findViewById(R.id.zsdoctor_exit);
                    exit.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });
                }
                System.out.println(mydoctor.toString() + "---------------------------");
            }
        });


    }

    @Override
    public void onItemClick(int pos) {

        id = myTeam.get(pos);
        getdatas();
    }

    public void getdatas() {
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.HEALTH_GROUPS_DETAILS, id), activity, new Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                System.out.println(data.toString());
                try {
                    Loading.bulid(activity, null).dismiss();
                    team_group_name.setText(data.getString("name"));
                    team_group_info.setText(data.getString("info"));
//					tezm_money.setText(data.getString("beans"));
                    team_zixunshu.setText(data.getInt("currentNum") + "");
                    team_zonzixunshu.setText(data.getInt("totalNum") + "");
                    team_details_rating.setRating((float) data.getDouble("star"));
                    ImageCacheManager.loadImage(data.get("logo").toString(), ImageCacheManager.getImageListener(team_group_logo, null, null));
//					detail = data.getString("detail");
                    JSONObject assistant = data.getJSONObject("assistant");
                    team_zl_name.setText(assistant.getString("name"));
                    team_zl_phone.setText(assistant.getString("phoneNumber"));
                    team_zl_group_info.setText(assistant.getString("info"));

                    ImageCacheManager.loadImage(assistant.get("icon").toString(), ImageCacheManager.getImageListener(team_zl_group_logo, null, null));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        RequestManager.getArray(String.format(Constens.HEALTH_GROUPS_DOCTER, id), activity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                System.out.println(array.toString());
                datas.clear();
                for (int i = 0; i < array.length(); i++) {
                    try {

                        Map<String, Object> myGroup = new HashMap<String, Object>();
                        JSONObject jsonObject = array.getJSONObject(i);
//						myGroup.put("type",1);//判断是否加入的团队
                        myGroup.put("id", jsonObject.getInt("id"));
                        myGroup.put("type", jsonObject.getInt("type"));
                        myGroup.put("realName", jsonObject.getString("realName"));
                        myGroup.put("icon", jsonObject.getString("icon"));
                        myGroup.put("info", jsonObject.getString("info"));
                        myGroup.put("isLeft", jsonObject.getBoolean("isLeft"));

                        datas.add(myGroup);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(datas.size() + "---------------------------");
                adapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(team_group_docter);
            }
        });
    }
}
