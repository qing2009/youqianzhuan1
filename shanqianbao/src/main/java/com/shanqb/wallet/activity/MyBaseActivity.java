package com.shanqb.wallet.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.shanqb.wallet.view.LoadingProgressDialog;

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




}
