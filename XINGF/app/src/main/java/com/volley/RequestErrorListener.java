package com.volley;

import com.android.volley.VolleyError;

public interface RequestErrorListener {
	/** 失败 */
	public void requestError(VolleyError e);
}


