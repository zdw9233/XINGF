package com.uyi.app.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CreatSpinnerAdapter {
	  public static ArrayAdapter<CharSequence> createFromResource(Context context,
			  CharSequence[] strings, int textViewResId) {
	       // CharSequence[] strings = context.getResources().getTextArray(textArrayResId);
	        return new ArrayAdapter<CharSequence>(context, textViewResId, strings);
	    }
}
