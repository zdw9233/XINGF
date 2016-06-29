package com.uyi.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.adapter.RecylerViewAdapter;
import com.uyi.app.model.bean.ViewPagerBean;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.DividerGridItemDecoration;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SlideViewPager;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.custom.app.R;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


@ContentView(R.layout.recylerview)
public class RecylerViewActiviry extends BaseActivity implements OnRefreshListener {

	private List<String> lists = new ArrayList<String>();
	@ViewInject(R.id.recyclerView) private RecyclerView recyclerView;
	@ViewInject(R.id.swipeRefreshLayout) private SwipeRefreshLayout swipeRefreshLayout;
	@ViewInject(R.id.headerView) private HeaderView headerView;
	private LinearLayoutManager linearLayoutManager;
	private RecylerViewAdapter recylerViewAdapter;
	private SlideViewPager header ;
	private SlideViewPager footer ;
	private String[] img = new String[]{
			"http://e.hiphotos.baidu.com/image/pic/item/b21c8701a18b87d603b670ba040828381e30fd9b.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/0df431adcbef76091324b6572cdda3cc7dd99ece.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/b64543a98226cffc9b70f24dba014a90f703eaf3.jpg"};
	@Override
	protected void onInitLayoutAfter() {
		for(int i = 1;i<100;i++){
			lists.add(i+"");
		}
		header = new SlideViewPager(activity);
		footer = new SlideViewPager(activity);
		List<ViewPagerBean> images = new ArrayList<ViewPagerBean>();
		for(int i = 0;i<this.img.length;i++){
			images.add(new ViewPagerBean(this.img[i], ""));
		}
		footer.setImages(images);
		footer.startSlide();
		header.setImages(images);
		header.startSlide();
		
//		linearLayoutManager = new LinearLayoutManager(activity);
		StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(staggeredGridLayoutManager);
//		recyclerView.setLayoutManager(linearLayoutManager);
		recylerViewAdapter = new RecylerViewAdapter(this,lists,header,footer);
		recyclerView.setAdapter(recylerViewAdapter);
//		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		 //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	@Override
	public void onRefresh() {
		  headerView.setTitle("正在刷新");
          new Handler().postDelayed(new Runnable() {
              public void run() {
             	 headerView.setTitle("");
             	 lists.add("add item refresh");
                 swipeRefreshLayout.setRefreshing(false);
              }
          }, 3000);
	}
	

	

}
