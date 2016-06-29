package com.uyi.app.ui.dialog;

import com.uyi.custom.app.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 确认取消对话框
 * @author user
 *
 */
public class MessageConform extends AbstrctDialog {

	private TextView conform_dialog_title;
	private TextView conform_dialog_content;
	private Button conform_dialog_cancel;
	private Button conform_dialog_yes;
	private EditText conform_dialog_edittext;
	
	public MessageConform(Context context, MessageType input) {
		super(context,R.style.dialog);
		setContentView(R.layout.conform_dialog);
		conform_dialog_title = (TextView)findViewById(R.id.conform_dialog_title);
		conform_dialog_content = (TextView)findViewById(R.id.conform_dialog_content);
		conform_dialog_yes = (Button)findViewById(R.id.conform_dialog_quereng);
		conform_dialog_cancel = (Button)findViewById(R.id.conform_dialog_cancel);
		conform_dialog_edittext = (EditText)findViewById(R.id.conform_dialog_edittext);
		if(input == MessageType.CONFORM){
			conform_dialog_content.setVisibility(View.VISIBLE);
			conform_dialog_edittext.setVisibility(View.GONE);
		}else if(input == MessageType.INPUT){
			conform_dialog_content.setVisibility(View.GONE);
			conform_dialog_edittext.setVisibility(View.VISIBLE);
		}else if(input == MessageType.ALERT){
			conform_dialog_content.setVisibility(View.VISIBLE);
			conform_dialog_edittext.setVisibility(View.GONE);
			conform_dialog_cancel.setVisibility(View.GONE);
		}
	}
	
	public void setOnMessageClick(final OnMessageClick onMessageClick) {
		conform_dialog_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMessageClick.onClick(Result.CANCEL, "");
				cancel();
			}
		});
		
		conform_dialog_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = conform_dialog_edittext.getText().toString();
				onMessageClick.onClick(Result.OK, content);
				cancel();
			}
		});
	}
	
	
	
	/**
	 * 设置标题显示
	 * @param visibility
	 * @return
	 */
	public MessageConform setTitleVisibility(int visibility){
		conform_dialog_title.setVisibility(visibility);
		return this;
	}
	
	
	/**
	 * 设置标题
	 * @param title
	 * @return
	 */
	public MessageConform setTitle(String title){
		conform_dialog_title.setText(title);
		return this;
	}
	
	/**
	 * 设置提示内容
	 * @param title
	 * @return
	 */
	public MessageConform setContent(String title){
		conform_dialog_content.setText(title);
		return this;
	}
	
	/**
	 * 获取输入框
	 * @return
	 */
	public EditText getconform_dialog_edittext() {
		return conform_dialog_edittext;
	}

	public enum MessageType{
		CONFORM,INPUT,ALERT
	}
	public enum Result{
		OK,CANCEL
	}
	
	public interface OnMessageClick{
		public void onClick(Result result, Object object);
	}

}
