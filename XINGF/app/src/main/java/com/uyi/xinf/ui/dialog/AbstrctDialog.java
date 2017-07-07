package com.uyi.xinf.ui.dialog;

import android.app.Dialog;
import android.content.Context;

public abstract class AbstrctDialog extends Dialog {

	public AbstrctDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public AbstrctDialog(Context context, int theme) {
		super(context, theme);
	}

	public AbstrctDialog(Context context) {
		super(context);
	}

 
	
	public <T> T $(int id){
		return (T)findViewById(id);
	}
	
	
	
	

}
