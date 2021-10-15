package com.shanqb.myTools.utils;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shanqb.myTools.inter.MyQueueResponse;

import java.util.Map;

/**
 * 网络访问工具类
 */
public class MyVolleyUtils {
    private Context mContext;
    private RequestQueue mQueue;
    private MyQueueResponse myQueueResponse;

    public MyVolleyUtils(Context context, MyQueueResponse myQueueResponse) {
        this.mContext = context.getApplicationContext();
        mQueue = Volley.newRequestQueue(mContext);
        this.myQueueResponse = myQueueResponse;
    }

    public MyVolleyUtils(Context context) {
        this(context,null);
    }

    public void requestQueue(int RequestMethod, String requestInterUrl, String requestAction, Map<String, String> requestParams){
        String requestUrl = requestInterUrl + requestAction;
        StringRequest stringRequest = new StringRequest(RequestMethod, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(getClass().toString(), response);
                if (myQueueResponse!=null)
                {
                    myQueueResponse.onResponse(requestAction,response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getClass().toString(), error.getMessage(), error);
                if (myQueueResponse!=null) {
                    myQueueResponse.onErrorResponse(requestAction,error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return requestParams;
            }
        };
        mQueue.add(stringRequest);
    }
    public void requestPostQueue(String requestAction, Map<String, String> requestParams){
        requestQueue(Request.Method.POST,Global.BASE_INTER_URL,requestAction,requestParams);
    }
    public void requestPostQueue(String requestAction, Map<String, String> requestParams, MyQueueResponse myQueueResponse){
        this.myQueueResponse = myQueueResponse;
        requestQueue(Request.Method.POST,Global.BASE_INTER_URL,requestAction,requestParams);
    }

    public void requestGetQueue(String requestUrl, MyQueueResponse myQueueResponse){
        this.myQueueResponse = myQueueResponse;
        requestQueue(Request.Method.GET,requestUrl,"",null);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public RequestQueue getmQueue() {
        return mQueue;
    }

    public void setmQueue(RequestQueue mQueue) {
        this.mQueue = mQueue;
    }

    public MyQueueResponse getMyQueueResponse() {
        return myQueueResponse;
    }

    public void setMyQueueResponse(MyQueueResponse myQueueResponse) {
        this.myQueueResponse = myQueueResponse;
    }
}
