package com.uyi.xinf.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uyi.doctor.xinf.R;


/**
 * 重写更新框 
 *
 */
public class CustomMultiChoiceDialog extends Dialog {
	private Context context;
	private String title;
	private String version;
	private String content;
	private String button_ok_text;
	private String button_cancel_text;
	private DialogInterface.OnClickListener button_ok_button_click_listener;
	private DialogInterface.OnClickListener button_cancel_button_click_listener;
	private LinearLayout dialog_layout_buttons;
	private LinearLayout dialog_progress;
	
	private TextView proerss;//进度文本
	private ProgressBar pro;//进度条
	
	public  static Boolean IS_BACKBUTTON = true;//返回键是否可以使用
	
	@Override
	public void show() {
		create();
		super.show();
	}
	@Override
	public void onBackPressed() {
		if(IS_BACKBUTTON){
			super.onBackPressed();
		}
	}
		
	/**
	 * 更新进度
	 * @param progress
	 * @param proText
	 */
	public void setProgress(int progress,String proText){
		if(pro != null){
			proerss.setText(proText);
			pro.setProgress(progress);
		}
	}
	
	public CustomMultiChoiceDialog(Context context) {
		super(context, R.style.update_dialog);
	}
	
	public CustomMultiChoiceDialog(Context context,String title,String version,String content){
		super(context, R.style.update_dialog);
		this.title = title;
		this.context = context;
		this.version = version;
		this.content = content;
	}
	
	public CustomMultiChoiceDialog setButton_ok_text(String button_ok, DialogInterface.OnClickListener listener) {
		this.button_ok_text =  button_ok;
		this.button_ok_button_click_listener = listener;
		return this;
	}
	public CustomMultiChoiceDialog setButton_cancel_text(String button_cancel, DialogInterface.OnClickListener listener) {
		this.button_cancel_text =   button_cancel;
		this.button_cancel_button_click_listener = listener;
		return this;
	}

	
	public void create() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setCanceledOnTouchOutside(false);
		View layout = inflater.inflate(R.layout.dialog_multichoice_layout, null,false);
		TextView multichoicTitle = (TextView) layout.findViewById(R.id.multichoic_title);
		multichoicTitle.setText(title);
		dialog_layout_buttons = (LinearLayout)layout.findViewById(R.id.dialog_layout_buttons);
		TextView multichoic_version = (TextView) layout.findViewById(R.id.multichoic_version);
		multichoic_version.setText("版本号:" +this.version);
		TextView multichoic_content = (TextView) layout.findViewById(R.id.multichoic_content);
		dialog_progress = (LinearLayout)layout.findViewById(R.id.dialog_progress);
		
//		content = "1.更新了再说.<br />1.更新了再说.<br />1.更新了再说.<br />1.更新了再说.<br />1.更新了再说.<br />1.更新了再说.<br />";
		multichoic_content.setText("更新说明:"+Html.fromHtml(this.content));
		
		proerss = (TextView)layout.findViewById(R.id.app_download_pro_text);
		pro = (ProgressBar)layout.findViewById(R.id.app_download_item_pro);
		
		final Button button_cancel = (Button) layout.findViewById(R.id.button_cancel);
		final Button button_ok = (Button) layout.findViewById(R.id.button_ok);
		
		this.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		// set the confirm button
		if (button_cancel_text != null) {
			button_cancel.setText(button_cancel_text);
			if (button_cancel_button_click_listener != null) {
				button_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						button_cancel_button_click_listener.onClick(CustomMultiChoiceDialog.this, DialogInterface.BUTTON_POSITIVE);
						dismiss();
					}
				});
			} else {
				button_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dismiss();
					}
				});
			}
		} else {
			button_cancel.setVisibility(View.GONE);
		}
		if (button_ok_text != null) {
			button_ok.setText(button_ok_text);
			if (button_ok_button_click_listener != null) {
				button_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dialog_progress.setVisibility(View.VISIBLE);
						dialog_layout_buttons.setVisibility(View.GONE);
						IS_BACKBUTTON = false;
						button_ok_button_click_listener.onClick(CustomMultiChoiceDialog.this, DialogInterface.BUTTON_NEGATIVE);
					}
				});
			} else {
				button_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dismiss();
					}
				});
			}
		} else {
			button_ok.setVisibility(View.GONE);
		}
		/*
		Window dialogWindow = getWindow();
	    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = 300; // 宽度
        dialogWindow.setAttributes(lp);*/
		this.setContentView(layout);
		
	}
	
}
