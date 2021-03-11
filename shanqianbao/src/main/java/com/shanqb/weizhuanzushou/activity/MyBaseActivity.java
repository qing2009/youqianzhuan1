package com.shanqb.weizhuanzushou.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shanqb.weizhuanzushou.R;
import com.shanqb.weizhuanzushou.inter.MyQueueResponse;
import com.shanqb.weizhuanzushou.utils.Global;
import com.shanqb.weizhuanzushou.utils.NetworkUtils;
import com.shanqb.weizhuanzushou.utils.XToastUtils;
import com.shanqb.weizhuanzushou.view.LoadingProgressDialog;

import java.util.Map;

import butterknife.ButterKnife;

public abstract class MyBaseActivity extends AppCompatActivity {

    public RequestQueue mQueue = null;

    private LoadingProgressDialog mLoadingProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQueue = Volley.newRequestQueue(this);

        mLoadingProgressDialog = new LoadingProgressDialog(this);

        initLayout();
        ButterKnife.bind(this);

        initBundleData();
        initData();
        initWeight();
    }

    /** 初始化布局 */
    public abstract void initLayout();

    /** 初始化绑定数据 */
    public abstract void initBundleData();

    /** 初始化数据 */
    public abstract void initData();

    /** 初始化控件 */
    public abstract void initWeight();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        mLoadingProgressDialog = null;
    }


    /**
     * 显示dialog
     */
    protected void showLoadingDialog() {
        if (!isFinishing() && mLoadingProgressDialog != null && !mLoadingProgressDialog.isShowing()) {
            mLoadingProgressDialog.show();
        }
    }

    /**
     * 关闭dialog
     */
    protected void dismissLoadingDialog() {
        if (!isFinishing() && mLoadingProgressDialog != null && mLoadingProgressDialog.isShowing()) {
            mLoadingProgressDialog.dismiss();
        }
    }


    protected void requestPostQueue(String requestAction, Map<String, String> requestParams, MyQueueResponse myQueueResponse){
        try {
            if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                XToastUtils.toast(getString(R.string.net_close));
                return;
            }

            showLoadingDialog();


            String requestUrl = Global.BASE_INTER_URL + requestAction;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(getClass().toString(), response);
                    dismissLoadingDialog();
                    myQueueResponse.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissLoadingDialog();
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
            mQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }




}
