package com.uyi.app.ui.team.adapter;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;


/**
 * 团队医生
 * @author user
 *
 */
public class HealthTeamDoctorAdapter extends BaseAdapter {

private LayoutInflater mInflater=null;
ArrayList<Map<String,Object>> mdata;
private int mResource;
//默认只传一个数据模型
public HealthTeamDoctorAdapter(Context context,ArrayList<Map<String,Object>> datas ,int resource)
{
//根据context,创建一个布局填充器
mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//传入数据源
mdata=datas;
//传入布局文件的id:数据源放哪
mResource=resource;
}
@Override
public int getCount() {
return mdata.size();
}
@Override
public View getView(int position, View convertView, ViewGroup parent) {
//创建一个视图并返回
View v=null;
if(convertView==null)
{
v=mInflater.inflate(mResource,parent,false);
}else{
v=convertView;
}
//为视图填充相应的数据(可以封装成一个bindView(int position, View view)方法)
TextView tv1=(TextView)v.findViewById(R.id.item_team_docter_name);
TextView tv2=(TextView)v.findViewById(R.id.item_team_docter_details);
TextView tv3=(TextView)v.findViewById(R.id.item_team_docter_islift);
RoundedImageView iv3=(RoundedImageView)v.findViewById(R.id.item_team_docter_header);

tv1.setText(mdata.get(position).get("realName").toString());
tv2.setText(mdata.get(position).get("info").toString());
ImageCacheManager.loadImage((mdata.get(position).get("icon").toString()), ImageCacheManager.getImageListener(iv3, null, null));
if((boolean)mdata.get(position).get("isLeft") == true){
	tv3.setVisibility(View.VISIBLE);
}else{
	tv3.setVisibility(View.GONE);
}
return v;
}
@Override
public long getItemId(int position) {

return position;
}
@Override
public Object getItem(int position) {

return mdata.get(position);
}
}
