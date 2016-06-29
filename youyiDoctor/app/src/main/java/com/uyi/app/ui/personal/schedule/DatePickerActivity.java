package com.uyi.app.ui.personal.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.Constens;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.custom.spiner.AbstractSpinerAdapter.IOnItemSelectListener;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.app.utils.DateUtils;
import com.uyi.app.utils.T;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.doctor.app.R;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;


/**
 * 日期选择
 * @author user
 * 
 * 返回选择的日期字符串
 * 
 *
 */
@ContentView(R.layout.date_picker)
public class DatePickerActivity extends BaseActivity implements OnClickListener, IOnItemSelectListener {

	
	@ViewInject(R.id.headerView) HeaderView  headerView;
	@ViewInject(R.id.datepicker) DatePicker picker;
	
	
	private  SpinerPopWindow spinerPopWindow;
	
	private String sDate ;
	private String eDate ;
	
	private String sDateMessage;
	private String eDateMessage;
	
	Calendar cal = Calendar.getInstance();
	
	List<String> years = new ArrayList<String>();
	
	@Override
	protected void onInitLayoutAfter() {
		
		for(int i = 2050;i>1900;i--){
			years.add(String.valueOf(i));
		}
		
		spinerPopWindow = new SpinerPopWindow(activity);
		spinerPopWindow.setItemListener(this);
		headerView.showLeftReturn(true).showRight(true).showTitle(true).setTitle("选择日期").setTitleColor(getResources().getColor(R.color.blue));
        if(getIntent().hasExtra("sDate")){
        	sDate = getIntent().getStringExtra("sDate");
        }
        if(getIntent().hasExtra("eDate")){
        	eDate = getIntent().getStringExtra("eDate");
        }
        if(getIntent().hasExtra("sDateMessage")){
        	sDateMessage = getIntent().getStringExtra("sDateMessage");
        }
        if(getIntent().hasExtra("eDateMessage")){
        	eDateMessage = getIntent().getStringExtra("eDateMessage");
        }
		
		picker.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
        picker.setFestivalDisplay(false);
        picker.setTodayDisplay(false);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setMode(DPMode.SINGLE);
        
        picker.setOnYearClickListener(this);
        
        
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTL(canvas, rect, paint, data);
                switch (data) {
//                    case "2015-10-5":
//                    case "2015-10-7":
//                    case "2015-10-9":
//                    case "2015-10-11":
//                        paint.setColor(Color.GREEN);
//                        canvas.drawRect(rect, paint);
//                        break;
                    default:
                        paint.setColor(Color.RED);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
                        break;
                }
            }

            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTR(canvas, rect, paint, data);
                switch (data) {
//                    case "2015-10-10":
//                    case "2015-10-11":
//                    case "2015-10-12":
//                        paint.setColor(Color.BLUE);
//                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
//                        break;
                    default:
                        paint.setColor(Color.YELLOW);
                        canvas.drawRect(rect, paint);
                        break;
                }
            }
        });
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
            	
            	try {
	            	if(!ValidationUtils.isNull(sDate)){
	            		long  sTime = DateUtils.toDateByString(sDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
						long  cTime = DateUtils.toDateByString(date, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
						if(cTime < sTime){
							if(sDateMessage == null){
								sDateMessage = "日期不能小于 "+sDate;
							} 
							T.showShort(activity, sDateMessage);
							return;
						}
	            	}
	            	if(!ValidationUtils.isNull(eDate)){
	            		long  eTime = DateUtils.toDateByString(eDate, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
						long  cTime = DateUtils.toDateByString(date, Constens.DATE_FORMAT_YYYY_MM_DD).getTime();
						if(cTime > eTime){
							if(eDateMessage == null){
								eDateMessage =  "日期不能大于 "+eDate;
							} 
							T.showShort(activity,eDateMessage);
							return;
						}
	            	}
            	} catch (ParseException e) {
					e.printStackTrace();
				}
            	Intent intent = new Intent();
            	intent.putExtra("date", date);
            	SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
            	try {
            		intent.putExtra("date", format.format(format.parse(date)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	setResult(RESULT_OK,intent);
            	finish();
            }
        });
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}

	int i = 1;
	
	@Override
	public void onClick(View v) {
		spinerPopWindow.refreshData(years, 0);
		spinerPopWindow.setWidth(200);
		spinerPopWindow.showAsDropDown(v);
	}

	@Override
	public void onItemClick(int pos) {
		picker.setDate(Integer.parseInt(years.get(pos)), cal.get(Calendar.MONTH)+1);
	}

}
