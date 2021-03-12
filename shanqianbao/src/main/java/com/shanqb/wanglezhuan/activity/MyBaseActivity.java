package com.shanqb.wanglezhuan.activity;

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
import com.shanqb.wanglezhuan.R;
import com.shanqb.wanglezhuan.inter.MyQueueResponse;
import com.shanqb.wanglezhuan.utils.Global;
import com.shanqb.wanglezhuan.utils.MyVolleyUtils;
import com.shanqb.wanglezhuan.utils.NetworkUtils;
import com.shanqb.wanglezhuan.utils.XToastUtils;
import com.shanqb.wanglezhuan.view.LoadingProgressDialog;

import java.util.Map;

import butterknife.ButterKnife;

public abstract class MyBaseActivity extends AppCompatActivity{

    public MyVolleyUtils myVolley = null;
    public RequestQueue mQueue = null;

    private LoadingProgressDialog mLoadingProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingProgressDialog = new LoadingProgressDialog(this);
        myVolley = new MyVolleyUtils(this);
        mQueue  = myVolley.getmQueue();

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


    protected void requestPostQueue(boolean isShowDialog,String requestAction, Map<String, String> requestParams, MyQueueResponse myQueueResponse){
        try {
            if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                XToastUtils.toast(getString(R.string.net_close));
                return;
            }
            if (isShowDialog){
                showLoadingDialog();
            }
            myVolley.requestPostQueue(requestAction,requestParams,myQueueResponse);

        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }

}
