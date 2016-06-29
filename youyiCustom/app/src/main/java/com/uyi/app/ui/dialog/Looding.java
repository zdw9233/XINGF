package com.uyi.app.ui.dialog;

import com.uyi.app.UYIApplication;
import com.uyi.custom.app.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
/**
 * 简单dialog 
 * @author chao
 *
 */
public class Looding extends Dialog{
	
	private static Looding dialog = null;
	
	private static String loodingText = "努力加载中...";
	
	private TextView looding;
	
	public static Looding bulid(Context context,String text){
		if(dialog == null){
			if(context == null)  context = UYIApplication.getContext();
			dialog = new Looding(context, loodingText);
		}
		if(text != null){
			dialog.setLoodingText(text);
		}
		return dialog;
	}
	
	
	
	
	private void setLoodingText(String text) {
		looding.setText(text);
	}

	private Looding(Context context,String text) {
		super(context,R.style.dialog);
		setContentView(R.layout.looding);//设置dialog布局
		looding = (TextView)findViewById(R.id.looding_text);
		looding.setText(text);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent); //无背景
		this.setCanceledOnTouchOutside(false);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		HexiApplicationContext.mRequestQueue.cancelAll(getContext());
	}
	
	@Override
	public void dismiss() {
			super.dismiss();
	}
	
	@Override
	public void show() {
		super.show();
	}
}
