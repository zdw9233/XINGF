package com.uyi.xinf.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.uyi.doctor.xinf.R;


/**
 * 简单dialog
 *
 * @author chao
 */
public class Loading extends Dialog {

	private static Loading dialog = null;

	private static String loodingText = "努力加载中...";

	private TextView looding;

	private static Context cont;

	public static Loading bulid(Context context, String text) {
		if (dialog == null) {
			dialog = new Loading(context, loodingText);
			cont = context;
		} else if (cont != context) {
			dialog = new Loading(context, loodingText);
			cont = context;
		}
		if (text != null) {
			dialog.setLoodingText(text);
		}
		return dialog;
	}


	private void setLoodingText(String text) {
		looding.setText(text);
	}

	private Loading(Context context, String text) {
		super(context, R.style.dialog);
		setContentView(R.layout.looding);//设置dialog布局
		looding = (TextView) findViewById(R.id.looding_text);
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
