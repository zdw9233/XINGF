package com.volley;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser; 

public class StringParamsRequest extends Request<String> {
	
	String body = null;
	Map<String, String> headers = new HashMap<String, String>();
	{
		headers.put("Content-Type", "application/json;charset=UTF-8");
		headers.put("User-Agent", "Android");
	}
    private final Listener<String> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringParamsRequest(int method, String url, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }
       
    public void setBody(String body){
    	this.body  = body;
    }
    
    @Override
    public byte[] getBody() throws AuthFailureError {
    	if(body != null){
    		return body.getBytes();
    	}
    	return super.getBody();
    }
    
    @Override
    public byte[] getPostBody() throws AuthFailureError {
    	if(body != null){
    		return body.getBytes();
    	}
    	return super.getPostBody();
    }
    
    public void addHeader(String key,String value){
    	headers.put(key, value);
    }
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
    	return headers;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringParamsRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}