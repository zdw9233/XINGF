package com.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uyi.xinf.ErrorCode;
import com.uyi.xinf.UYIApplication;
import com.uyi.xinf.utils.T;

/**
 * 
 * @author Doots
 *
 */
public class RequestManager {
	
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(UYIApplication.getContext());
    private RequestManager() {
        // no instances
    }

    /**
     * 
     * @param <T>
     * @param url
     * @param tag
     * @param listener
     */
    public static void getObject(String url, Object tag, Response.Listener<JSONObject> successListener) {
    	getObject(url, tag, null, successListener,null);
    }

    /**
     * 
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public static void getObject(String url, Object tag,JSONObject params,Response.Listener<JSONObject> successListener,RequestErrorListener errorListener) {
    	JsonObjectRequest  request = new JsonObjectRequest(Method.GET,url, params, successListener, responseError(errorListener)){
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("authToken","");
				headers.put("Content-Type", "application/json;charset=UTF-8");
				headers.put("User-Agent", "Android");
				return headers;
			}
		};
    	addRequest(request , tag);
    }

    /**
     * 
     * @param url
     * @param tag
     * @param listener
     */
    public static void postObject(String url, Object tag, Response.Listener<JSONObject> successListener) {
    	postObject(url, tag, null, successListener,null);
    }
    
    /**
     * 
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public static void postObject(String url, Object tag, JSONObject params, Response.Listener<JSONObject> litenner ,RequestErrorListener errorListener) {
    	JsonObjectRequest  request = new JsonObjectRequest(Method.POST, url, params, litenner, responseError(errorListener)){
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json;charset=UTF-8");
				headers.put("User-Agent", "Android");
				return headers;
			}
		};
    	addRequest(request , tag);
    }
    
    
    
    public static void getArray(String url, Object tag, Response.Listener<JSONArray> successListener) {
    	getArray(url, tag, null, successListener,null);
    }
    
    /**
     * post获取数组
     * @param url
     * @param tag
     * @param params
     * @param litenner
     * @param errorListener
     */
    private static void getArray(String url, Object tag, JSONObject params, Response.Listener<JSONArray> litenner,RequestErrorListener errorListener) {
    	JsonArrayRequest  request = new JsonArrayRequest(url, litenner, responseError(errorListener)){;
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json;charset=UTF-8");
				headers.put("User-Agent", "Android");
				return headers;
			}
			@Override
			public int getMethod() {
				return Method.GET;
			}
		};
    	addRequest(request , tag);
    }
    
    public static void postArray(String url, Object tag, Response.Listener<JSONArray> successListener) {
    	postArray(url, tag, null, successListener,null);
    }
    
    /**
     * post获取数组
     * @param url
     * @param tag
     * @param params
     * @param litenner
     * @param errorListener
     */
    public static void postArray(String url, Object tag, final JSONObject params, Response.Listener<JSONArray> litenner,RequestErrorListener errorListener) {
    	JsonArrayRequest  request = new JsonArrayRequest(url, litenner,responseError(errorListener)){;
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json;charset=UTF-8");
				headers.put("User-Agent", "Android");
				return headers;
			}
			@Override
			public int getMethod() {
				return Method.POST;
			}
		};
    	addRequest(request , tag);
    }
    
    
    /**
     * 
     * @param method
     * @param url
     * @param tag
     * @param body
     * @param litenner
     * @param errorListener
     */
    public static void requestString(int method,String url,Object tag ,String body,  Response.Listener<String> litenner,RequestErrorListener errorListener){
    	StringParamsRequest request = new StringParamsRequest(method,url,litenner , responseError(errorListener) );
    	request.setBody(body.toString());
		addRequest(request , tag);
    }
    
    
    
    public static void deleteObject(String url, Object tag, Response.Listener<JSONObject> successListener) {
    	deleteObject(url, tag, null, successListener,null);
    }
    
    
    /**
     * delete请求
     * @param url
     * @param tag
     * @param params
     * @param litenner
     * @param errorListener
     */
    public static void deleteObject(String url, Object tag, JSONObject params, Response.Listener<JSONObject> litenner,RequestErrorListener errorListener) {
    	JsonObjectRequest  request = new JsonObjectRequest(Method.DELETE, url, params, litenner, responseError(errorListener)){
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json;charset=UTF-8");
				headers.put("User-Agent", "Android");
				return headers;
			}
		};
    	addRequest(request , tag);
    }
    
    
    
    
    
    
    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    /**
     * 取消当前界面的所有请求
     * @param tag
     */
    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
    
    /**
     * 返回错误监听
     * @param l
     * @return
     */
    protected static Response.ErrorListener responseError(final RequestErrorListener l) {
    	return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError e) {
				if(l == null){
					T.show(UYIApplication.getContext(), ErrorCode.getErrorByNetworkResponse(e.networkResponse), -1);
				}else{
					l.requestError(e);
				}
			}
		};
    }
}
