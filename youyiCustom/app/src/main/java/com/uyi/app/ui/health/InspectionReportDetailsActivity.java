package com.uyi.app.ui.health;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.common.ViewPagerImageActivity;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.Loading;
import com.uyi.app.ui.health.adapter.PictruePagerAdapter;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2016/9/1.
 *
 */

@ContentView(R.layout.activity_inspection_report_details)
public class InspectionReportDetailsActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {


    @ViewInject(R.id.headerView) private HeaderView headerView;
    @ViewInject(R.id.viewPager)
    private ViewPager mViewPager;
    PictruePagerAdapter mPagerAdapter;
    LayoutInflater inflater  ;
    @ViewInject(R.id.check_item) private TextView check_item;
    @ViewInject(R.id.number) private TextView number;
    List<String> strings;
    @Override
    protected void onInitLayoutAfter() {
        inflater  = LayoutInflater.from(activity);
        mViewPager.addOnPageChangeListener(this);
        headerView.showTitle(true).showLeftReturn(true).showRight(false).setTitle("检查报告详情").setTitleColor(getResources().getColor(R.color.blue));

        String id = getIntent().getStringExtra("id");
        if(id == null){
            finish();
        }
        Loading.bulid(activity, null).show();
        RequestManager.getObject(String.format(Constens.HEALTH_CHECK_INFO, id), activity, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject data) {
                L.d(TAG, data.toString());
                try {
                    strings = new ArrayList<String>();
                    if(data.has("checkItem")){
                        check_item.setText(JSONObjectUtils.getString(data,"checkItem"));
                    }
                    //检查报告
                    if(data.has("images")){
                        JSONArray array = data.getJSONArray("images");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String imageUrl = jsonObject.getString("url").replace("http://localhost:8080", Constens.SERVER_URL);
                            strings.add(imageUrl);
                        }
                    }
                   if(strings.size()>0){
                       number.setText("1/"+strings.size()+"");
                   }else{
                       number.setVisibility(View.GONE);
                   }
                    mPagerAdapter = new PictruePagerAdapter(InspectionReportDetailsActivity.this, strings);
                    mViewPager.setAdapter(mPagerAdapter);
                    mPagerAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Loading.bulid(activity, null).dismiss();
            }
        });
    }
    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        headerView.setKitkat(systemBarConfig);
    }



    @Override
    public void onClick(View v) {
        String imageUrl = v.getTag().toString();
        Intent intent = new Intent(activity, ViewPagerImageActivity.class);
        intent.putExtra("imageUrl", new String[]{imageUrl});
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        number.setText(position+1+"/"+strings.size()+"");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}

