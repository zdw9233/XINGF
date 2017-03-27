package com.uyi.app.ui.home.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.EndlessRecyclerView;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.home.fragment.adapter.MyServiceAdapter2_1;
import com.uyi.app.ui.home.fragment.model.MyServerce;
import com.uyi.app.ui.home.fragment.model.Serverce;
import com.uyi.app.utils.L;
import com.uyi.app.utils.T;
import com.uyi.app.widget.recycle.RecyclerView;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2017/3/10.
 */
@ContentView(R.layout.activity_my_service)
public class MyService extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.headerView)
    private HeaderView headerView;
    @ViewInject(R.id.recyclerView)
    private EndlessRecyclerView recyclerView;
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyLinearLayoutManager linearLayoutManager;
    private MyServiceAdapter2_1 myServiceAdapter2_1;
    private List<Serverce> datas = new ArrayList<Serverce>();
    private List<MyServerce> myDatas = new ArrayList<MyServerce>();
    boolean isFirst = true;
    @ViewInject(R.id.nocomtoms)
    private LinearLayout no_assessment;

    @Override
    protected void onInitLayoutAfter() {
        headerView.showLeftReturn(true).showTitle(true).showRight(false).setTitle("我的服务").setTitleColor(getResources().getColor(R.color.blue));
        linearLayoutManager = new MyLinearLayoutManager(activity);
        myServiceAdapter2_1 = new MyServiceAdapter2_1(activity, myDatas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myServiceAdapter2_1);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new android.support.v7.widget.RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.getScrollY() > 0){
                    swipeRefreshLayout.setEnabled(false);
                }else{
                    swipeRefreshLayout.setEnabled(true);
                }
            }
        });

        getDataFirst(2);
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }


    @Override
    public void onRefresh() {
        if (isFirst) {
            getData(1);
            isFirst = false;
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private void getData(int i) {
        Loading.bulid(activity, null).show();
        RequestManager.getArray(String.format(Constens.MY_SERVICE, i + ""),
                activity, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray data) {
                        Loading.bulid(activity, null).dismiss();
                        L.e(data.toString());
                        com.alibaba.fastjson.JSONArray object = JSON.parseArray(data.toString());
                        List<Serverce> datanew = new ArrayList<Serverce>();
                        datanew = JSON.parseArray(object.toString(), Serverce.class);
                        if (datanew.size() > 0) {
                            no_assessment.setVisibility(View.GONE);
                            myDatas.clear();
                            datas.add(0, datanew.get(0));
                            List<String> list = new ArrayList<String>();
                            for (int i = 0; i < datas.size(); i++) {
                                list.add(datas.get(i).getExecuteBegin().substring(0, 4));
                            }
                            List<String> newList = new ArrayList<String>();
                            for (String cd : list) {
                                if (!newList.contains(cd)) {
                                    MyServerce myServerce = new MyServerce();
                                    myServerce.setYear(cd);
                                    newList.add(cd);
                                    myDatas.add(myServerce);
                                }
                            }
                            L.e(myDatas.size() + "");
                            for (int i = 0; i < myDatas.size(); i++) {
                                List<MyServerce.ServerceMonth> sv = new ArrayList<MyServerce.ServerceMonth>();
                                ;
                                for (int j = 0; j < datas.size(); j++) {
                                    if (datas.get(j).getExecuteBegin().substring(0, 4).equals(myDatas.get(i).getYear())) {
                                        MyServerce.ServerceMonth serverceMonth = new MyServerce.ServerceMonth();
                                        serverceMonth.setMonth(datas.get(j).getExecuteBegin().substring(5, datas.get(j).getExecuteBegin().length()));
                                        String str = "";
                                        for (int k = 0; k < datas.get(j).getEntryName().size(); k++) {
                                            if (datas.get(j).getEntryName().get(k).getStatus() == 0) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">未完成</font>" + ")" + "<br>";
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 1) {
                                                String url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                                                        + getResources().getResourcePackageName(R.drawable.gou) + "/"
                                                        + getResources().getResourceTypeName(R.drawable.gou) + "/"
                                                        + getResources().getResourceEntryName(R.drawable.gou))+"";
                                                String hmtl = " <img src='"+url+"'/><br>";
                                                str += datas.get(j).getEntryName().get(k).getName()+hmtl;
                                                L.e(hmtl);
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 2) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">待延期</font>" + ")" + "<br>";
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 3) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">已延期</font>" + ")" + "<br>";
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 4) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">已失效</font>" + ")" + "<br>";
                                            } else {
                                                str += datas.get(j).getEntryName().get(k).getName() + "<br>";
                                            }

                                        }
                                        if (!str.equals("")) {
                                            str = str.substring(0, str.length() - 4);
                                        }
                                        serverceMonth.setData(str);
                                        sv.add(serverceMonth);
                                    }
                                }
                                myDatas.get(i).setServerceMonths(sv);
                            }
                            for (int i = 0; i < myDatas.size(); i++) {
                                for (int j = 0; j < myDatas.get(i).getServerceMonths().size(); j++) {
                                    L.e(i + "." + myDatas.get(i).getYear() + "--" + myDatas.get(i).getServerceMonths().get(j).getMonth() + "--" + myDatas.get(i).getServerceMonths().get(j).getData());
                                }

                            }
                            MyServerce myServerce = new MyServerce();
                            myServerce = myDatas.get(0);
                            String mo = "<font color=\"#999999\">" + myServerce.getServerceMonths().get(0).getMonth() + "</font>";
                            String da = "<font color=\"#999999\">" + myServerce.getServerceMonths().get(0).getData() + "</font>";
                            myDatas.get(0).getServerceMonths().get(0).setMonth(mo);
                            myDatas.get(0).getServerceMonths().get(0).setData(da.replace("(<font color=\"#fb7070\">未完成</font>)", "").replace("(<font color=\"#fb7070\">待延期</font>)", "").replace("(<font color=\"#fb7070\">已延期</font>)", "").replace("(<font color=\"#fb7070\">已失效</font>)", ""));
                            myServiceAdapter2_1.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);

                        } else {
                            T.showLong(activity, "没有更多了!");
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                });
    }

    public void getDataFirst(int num) {
        Loading.bulid(activity, null).show();
        RequestManager.getArray(String.format(Constens.MY_SERVICE, num + ""),
                activity, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray data) {
                        Loading.bulid(activity, null).dismiss();
                        L.e(data.toString());
                        com.alibaba.fastjson.JSONArray object = JSON.parseArray(data.toString());
                        datas = JSON.parseArray(object.toString(), Serverce.class);
                        if (datas.size() > 0) {
                            no_assessment.setVisibility(View.GONE);
                            List<String> list = new ArrayList<String>();
                            for (int i = 0; i < datas.size(); i++) {
                                list.add(datas.get(i).getExecuteBegin().substring(0, 4));
                            }
                            List<String> newList = new ArrayList<String>();
                            for (String cd : list) {
                                if (!newList.contains(cd)) {
                                    MyServerce myServerce = new MyServerce();
                                    myServerce.setYear(cd);
                                    newList.add(cd);
                                    myDatas.add(myServerce);
                                }
                            }
                            L.e(myDatas.size() + "");
                            for (int i = 0; i < myDatas.size(); i++) {
                                List<MyServerce.ServerceMonth> sv = new ArrayList<MyServerce.ServerceMonth>();
                                ;
                                for (int j = 0; j < datas.size(); j++) {
                                    if (datas.get(j).getExecuteBegin().substring(0, 4).equals(myDatas.get(i).getYear())) {
                                        MyServerce.ServerceMonth serverceMonth = new MyServerce.ServerceMonth();
                                        serverceMonth.setMonth(datas.get(j).getExecuteBegin().substring(5, datas.get(j).getExecuteBegin().length()));
                                        String str = "";
                                        for (int k = 0; k < datas.get(j).getEntryName().size(); k++) {
                                            if (datas.get(j).getEntryName().get(k).getStatus() == 0) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">未完成</font>" + ")" + "<br>";
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 1) {
                                                String url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                                                        + getResources().getResourcePackageName(R.drawable.gou) + "/"
                                                        + getResources().getResourceTypeName(R.drawable.gou) + "/"
                                                        + getResources().getResourceEntryName(R.drawable.gou))+"";
                                                String hmtl = " <img src='"+url+"'/><br>";
                                                str += datas.get(j).getEntryName().get(k).getName()+hmtl;
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 2) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">待延期</font>" + ")" + "<br>";
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 3) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">已延期</font>" + ")" + "<br>";
                                            } else if (datas.get(j).getEntryName().get(k).getStatus() == 4) {
                                                str += datas.get(j).getEntryName().get(k).getName() + "(" + "<font color=\"#fb7070\">已失效</font>" + ")" + "<br>";
                                            } else {
                                                str += datas.get(j).getEntryName().get(k).getName() + "<br>";
                                            }
                                        }
                                        if (!str.equals("")) {
                                            str = str.substring(0, str.length() - 4);
                                        }
                                        serverceMonth.setData(str);
                                        sv.add(serverceMonth);
                                    }
                                }
                                myDatas.get(i).setServerceMonths(sv);
                            }
                            for (int i = 0; i < myDatas.size(); i++) {
                                for (int j = 0; j < myDatas.get(i).getServerceMonths().size(); j++) {
                                    L.e(i + "." + myDatas.get(i).getYear() + "--" + myDatas.get(i).getServerceMonths().get(j).getMonth() + "--" + myDatas.get(i).getServerceMonths().get(j).getData());
                                }

                            }
                            myServiceAdapter2_1.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            no_assessment.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                });
    }

    public void initData() {
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("aba");
        list.add("aaa");
        List<String> newList = new ArrayList<String>();
        for (String cd : list) {
            if (!newList.contains(cd)) {
                newList.add(cd);
            }
        }
        System.out.println("去重后的集合： " + newList);
    }
    class MyLinearLayoutManager extends LinearLayoutManager {

        private RecyclerView.Recycler mRecycler;

        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
            mRecycler = recycler;
        }

        public int getScrollY() {
            int scrollY = getPaddingTop();
            int firstVisibleItemPosition = findFirstVisibleItemPosition();

            if (firstVisibleItemPosition >= 0 && firstVisibleItemPosition < getItemCount()) {
                for (int i = 0; i < firstVisibleItemPosition; i++) {
                    View view = mRecycler.getViewForPosition(i);
                    if (view == null) {
                        continue;
                    }
                    if (view.getMeasuredHeight() <= 0) {
                        measureChildWithMargins(view, 0, 0);
                    }
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                    scrollY += lp.topMargin;
                    scrollY += getDecoratedMeasuredHeight(view);
                    scrollY += lp.bottomMargin;
                    mRecycler.recycleView(view);
                }

                View firstVisibleItem = findViewByPosition(firstVisibleItemPosition);
                RecyclerView.LayoutParams firstVisibleItemLayoutParams = (RecyclerView.LayoutParams) firstVisibleItem.getLayoutParams();
                scrollY += firstVisibleItemLayoutParams.topMargin;
                scrollY -= getDecoratedTop(firstVisibleItem);
            }

            return scrollY;
        }
    }
}
