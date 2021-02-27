package com.shanqb.weishouzhuan.tabview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shanqb.weishouzhuan.R;
import com.shanqb.weishouzhuan.inter.MyQueueResponse;
import com.shanqb.weishouzhuan.utils.Global;
import com.shanqb.weishouzhuan.utils.NetworkUtils;
import com.shanqb.weishouzhuan.utils.XToastUtils;

import java.util.Map;
import java.util.Queue;

/**
 * Created by yx on 16/4/3.
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


    protected void requestPostQueue(boolean isShowLoadingDialog, Context context,String requestAction, Map<String, String> requestParams, MyQueueResponse myQueueResponse){
        try {
            if (isShowLoadingDialog) {
                if (!NetworkUtils.checkNetworkConnectionState(context)) {//未连接到网络
                    XToastUtils.toast(getString(R.string.net_close));
                    return;
                }
            }


            String requestUrl = Global.BASE_INTER_URL + requestAction;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(getClass().toString(), response);
                    myQueueResponse.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(getClass().toString(), error.getMessage(), error);
                    myQueueResponse.onErrorResponse(error);
//                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return requestParams;
                }
            };
            Volley.newRequestQueue(context).add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
