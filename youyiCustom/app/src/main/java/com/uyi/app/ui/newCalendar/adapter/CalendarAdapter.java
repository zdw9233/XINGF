package com.uyi.app.ui.newCalendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.uyi.app.ui.newCalendar.model.Day;
import com.uyi.app.ui.newCalendar.model.Marker;
import com.uyi.app.utils.L;
import com.uyi.app.widget.recycle.BaseAdapter;
import com.uyi.custom.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ThinkPad on 2016/10/29.
 */
public class CalendarAdapter extends BaseAdapter {
    List<Marker> markers;
    ArrayList<Day> dates;

    private int year;
    private int month;
    String today;

    private int selectedPosition = -1;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CalendarAdapter(Context context, int year, int month) {
        super(context, R.layout.layout_calendar_item);
        dates = new ArrayList<>();
        initData(year, month);
        today = simpleDateFormat.format(System.currentTimeMillis());
    }

    @Override
    protected int getCount() {
        return 42;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        TextView day = holder.holder(R.id.day, TextView.class);
        TextView da1 = holder.holder(R.id.da1, TextView.class);
        TextView da2 = holder.holder(R.id.da2, TextView.class);
        TextView da3 = holder.holder(R.id.da3, TextView.class);
        TextView da4 = holder.holder(R.id.da4, TextView.class);
        Day d = dates.get(position);
        day.setText(d.dayNum);
        //日程标记
        if (markers != null)
            for (Marker m : markers) {
                if (d.dayText.equals(m.day) && m.type != null) {
                    da1.setVisibility(m.type.contains(1) ? View.VISIBLE : View.GONE);
                    da4.setVisibility(m.type.contains(2) ? View.VISIBLE : View.GONE);
                    da2.setVisibility(m.type.contains(3) ? View.VISIBLE : View.GONE);
                    da3.setVisibility(m.type.contains(4) ? View.VISIBLE : View.GONE);
                    da3.setVisibility(m.type.contains(5) ? View.VISIBLE : View.GONE);
                }
            }
        //本月
        if (isCurrentMonth(position)) {
            day.setTextColor(Color.DKGRAY);
        } else day.setTextColor(Color.GRAY);
        //今天
        if (today.equals(d.dayText)) {
            day.setTextColor(Color.RED);
        } else {
            day.setTextColor(Color.DKGRAY);
        }
        //选中
        holder.holder(R.id.item_layout).setBackgroundResource(position == selectedPosition ? R.drawable.shape_calendar_item : R.drawable.shape_calendar_item2);
    }

    private boolean isCurrentMonth(int position) {
        return dates.get(position).month == month;
    }

    public void setSelectedPosition(int selectedPosition) {
        int temp = this.selectedPosition;
        this.selectedPosition = selectedPosition;
        if (temp != -1) {
            notifyItemChanged(temp);
        }
        notifyItemChanged(selectedPosition);
    }

    public String getStartDate() {
        return dates.get(0).dayText;
    }

    public String getEndDate() {
        return dates.get(41).dayText;
    }

    @Override
    public ViewHolder newHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    private void initData(int year, int month) {
        this.year = year;
        this.month = month;
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        int week = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_MONTH, -week + 1);
        dates.clear();
        markers = null;
        selectedPosition = -1;
        for (int i = 0; i < 42; i++) {
            Day day = new Day();
            day.dayNum = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            day.dayText = simpleDateFormat.format(c.getTime());
            day.month = c.get(Calendar.MONTH);
            c.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(day);
        }
    }

    public void notifyMarkerChange(List<Marker> markers) {
        this.markers = markers;
        notifyDataSetChanged();
    }

    public void notifyDataChange(int year, int month) {
        initData(year, month);
        notifyDataSetChanged();
    }

    public void showPreviousMonth() {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        c.add(Calendar.MONTH, -1);
        notifyDataChange(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
    }

    public void showNextMonth() {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        c.add(Calendar.MONTH, 1);
        L.e("month =  " + String.valueOf(c.get(Calendar.MONTH)));
        notifyDataChange(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
    }

    public String getDateText(int position) {
        return dates.get(position).dayText;
    }

    public int getCurrentYear() {
        return year;
    }

    public int getCurrentMonth() {
        return month + 1;
    }
}
