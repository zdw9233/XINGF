package com.uyi.app.ui.home.fragment.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.ui.home.fragment.model.MyServerce;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.lang.reflect.Field;
import java.util.List;


public class MyServiceMonthAdapter2_1 extends BaseAdapter {
    private final List<MyServerce.ServerceMonth> mReportItems;
    Context contest;

    public MyServiceMonthAdapter2_1(Context context, List<MyServerce.ServerceMonth> mReportItems) {
        super(context, R.layout.item_myservece_month);
        this.mReportItems = mReportItems;
        this.contest = context;
    }

    @Override
    protected int getCount() {
        return mReportItems == null ? 0 : mReportItems.size();
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView time_month = holder.holder(R.id.time_month, TextView.class);
		TextView context = holder.holder(R.id.context, TextView.class);
		MyServerce.ServerceMonth reportItem = mReportItems.get(position);
		time_month.setText(Html.fromHtml(reportItem.getMonth()));
        CharSequence charSequence = Html.fromHtml(reportItem.getData(), new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                // 获得资源系统的信息，比如图片信息//根据名字，获取图片id，再根据id获取当前工程下的drawable文件中的图片
                Drawable drawable = ContextCompat.getDrawable(contest,R.drawable.gou);
                // 当名字等于pic3的时候，使图片变小
                if (source.equals("pic3")) {
                    // IntrinsicWidth内部固有的宽IntrinsicHeight内部固有的高
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2,
                            drawable.getIntrinsicHeight() / 2);
                } else {
                    // IntrinsicWidth内部固有的宽IntrinsicHeight内部固有的高
//                    drawable.setBounds(0, 0,20,
//                           20);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth() ,
                            drawable.getIntrinsicHeight() );
                }
                return drawable;
            }
        }, null);
        context.setText(charSequence);
//		context.setText(Html.fromHtml(reportItem.getData()));
//        String s = reportItem.getData();
//        SpannableString spannableString = new SpannableString(s);
//        Bitmap bitmap = BitmapFactory.decodeResource(contest.getResources(), R.drawable.gou);
//        ImageSpan imageSpan = new ImageSpan(contest, bitmap);
//        // 用ImageSpan对象替换字符
//        spannableString.setSpan(imageSpan, s.length()-2, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        str += spannableString+ "\n";
    }

    @Override
    public ViewHolder newHolder(final View itemView) {
        final ViewHolder viewHolder = new ViewHolder(itemView, R.color.gra_bg);
        return viewHolder;
    }

    com.uyi.app.ui.report.adapter.ReportListAdapter.CallBack callBack;

    public void setCallBack(com.uyi.app.ui.report.adapter.ReportListAdapter.CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onclick(int position);
    }
    //使用反射机制来获取资源的id的字段（静态变量）的值
    public int getResourceId(String name) {
        try {
            // 根据资源的id名的变量取得field的对象，使用反射机制来实现
            Field field = R.drawable.class.getField(name);
            // 取得并返回资源的id的字段（静态变量）的值，使用反射机制
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
        }
        return 0;
    }
}
