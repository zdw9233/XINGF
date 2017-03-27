package com.uyi.app.ui.home.fragment.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyi.app.adapter.BaseRecyclerAdapter;
import com.uyi.custom.app.R;

import java.util.Map;


/**
 * 健康数据库列表
 *
 * @author user
 */
public class BloodSugarAdapter2_1 extends BaseRecyclerAdapter<Map<String, Object>> {
    public Context context;

    public BloodSugarAdapter2_1(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_blood_sugar2_1, parent, false);
        return new Holder(this, view);
    }

    @Override
    public void onBind(ViewHolder viewHolder, int RealPosition, Map<String, Object> data) {
        if (viewHolder instanceof Holder) {
            Holder hodler = (Holder) viewHolder;
            hodler.title1.setText(data.get("uploadTime").toString());
            if (data.get("fastBloodSugar") != null) {
                if (!data.get("fastBloodSugar").toString().equals("0")) {
                    hodler.title2.setText(data.get("fastBloodSugar").toString() + "");
                    hodler.title2.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                    if (data.get("fbsState") != null) {
                        if (data.get("fbsState").toString().equals("0")) {
                            hodler.title2.setCompoundDrawables(null, null, null, null);
                            hodler.title2.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                        } else if (data.get("fbsState").toString().equals("1")) {
                            //获取资源图片
                            Drawable leftDrawable = context.getResources().getDrawable(R.drawable.high2x);

                            //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
                            leftDrawable.setBounds(0, 0, 20, 20);
                            //设置图片在TextView中的位置
                            hodler.title2.setCompoundDrawables(null, null, leftDrawable, null);
                            hodler.title2.setTextColor(ContextCompat.getColor(context, R.color.rend));
                        } else {
                            //获取资源图片
                            Drawable leftDrawable = context.getResources().getDrawable(R.drawable.low2x);
                            //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
                            leftDrawable.setBounds(0, 0, 20, 20);
                            //设置图片在TextView中的位置
                            hodler.title2.setCompoundDrawables(null, null, leftDrawable, null);
                            hodler.title2.setTextColor(ContextCompat.getColor(context, R.color.green));
                        }
                    } else {
                        hodler.title2.setCompoundDrawables(null, null, null, null);
                        hodler.title2.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                    }

                } else {
                    hodler.title2.setCompoundDrawables(null, null, null, null);
                    hodler.title2.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                }
            } else {
                hodler.title2.setText("--");
                hodler.title2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                hodler.title2.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
            }

            if (data.get("postPrandilaSugar") != null) {
                if (!data.get("postPrandilaSugar").toString().equals("0")) {
                    hodler.title3.setText(data.get("postPrandilaSugar").toString());
                    hodler.title3.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                    if (data.get("ppsState") != null) {
                        if (data.get("ppsState").toString().equals("0")) {
                            hodler.title3.setCompoundDrawables(null, null, null, null);
                            hodler.title3.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                        } else if (data.get("ppsState").toString().equals("1")) {
                            //获取资源图片
                            Drawable leftDrawable = context.getResources().getDrawable(R.drawable.high2x);
                            //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
                            leftDrawable.setBounds(0, 0, 20, 20);
                            //设置图片在TextView中的位置
                            hodler.title3.setCompoundDrawables(null, null, leftDrawable, null);
                            hodler.title3.setTextColor(ContextCompat.getColor(context, R.color.rend));
                        } else {
                            //获取资源图片
                            Drawable leftDrawable = context.getResources().getDrawable(R.drawable.low2x);
                            //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
                            leftDrawable.setBounds(0, 0, 20, 20);
                            //设置图片在TextView中的位置
                            hodler.title3.setCompoundDrawables(null, null, leftDrawable, null);
                            hodler.title3.setTextColor(ContextCompat.getColor(context, R.color.green));
                        }

                    } else {
                        hodler.title3.setCompoundDrawables(null, null, null, null);
                        hodler.title3.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                    }
                } else {
                    hodler.title3.setText("--");
                    hodler.title3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    hodler.title3.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                }
            } else {
                hodler.title3.setText("--");
                hodler.title3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                hodler.title3.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
            }
            if (data.get("randomBloodSugar") != null) {
                if (!data.get("randomBloodSugar").toString().equals("0")) {
                    hodler.title4.setText(data.get("randomBloodSugar").toString() + "");
                    hodler.title4.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                    if (data.get("rbsState") != null) {
                        if (data.get("rbsState").toString().equals("0")) {
                            hodler.title4.setCompoundDrawables(null, null, null, null);
                            hodler.title4.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                        } else if (data.get("rbsState").toString().equals("1")) {
                            //获取资源图片
                            Drawable leftDrawable = context.getResources().getDrawable(R.drawable.high2x);
                            //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
                            leftDrawable.setBounds(0, 0, 20, 20);
                            //设置图片在TextView中的位置
                            hodler.title4.setCompoundDrawables(null, null, leftDrawable, null);
                            hodler.title4.setTextColor(ContextCompat.getColor(context, R.color.rend));
                        } else {
                            //获取资源图片
                            Drawable leftDrawable = context.getResources().getDrawable(R.drawable.low2x);
                            //设置图片的尺寸，奇数位置后减前得到宽度，偶数位置后减前得到高度。
                            leftDrawable.setBounds(0, 0, 20, 20);
                            //设置图片在TextView中的位置
                            hodler.title4.setCompoundDrawables(null, null, leftDrawable, null);
                            hodler.title4.setTextColor(ContextCompat.getColor(context, R.color.green));
                        }

                    } else {
                        hodler.title4.setCompoundDrawables(null, null, null, null);
                        hodler.title4.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                    }
                } else {
                    hodler.title4.setText("--");
                    hodler.title4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    hodler.title4.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
                }
            } else {
                hodler.title4.setText("--");
                hodler.title4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                hodler.title4.setTextColor(ContextCompat.getColor(context, R.color.tet_type_6));
            }
        }
    }

    public static class Holder extends BaseRecyclerAdapter<Map<String, Object>>.Holder {
        public Holder(BaseRecyclerAdapter<Map<String, Object>> base, View v) {
            base.super(v);
            title1 = (TextView) v.findViewById(R.id.title1);
            title2 = (TextView) v.findViewById(R.id.title2);
            title3 = (TextView) v.findViewById(R.id.title3);
            title4 = (TextView) v.findViewById(R.id.title4);

        }

        private TextView title1;
        private TextView title2;
        private TextView title3;
        private TextView title4;
    }

}
