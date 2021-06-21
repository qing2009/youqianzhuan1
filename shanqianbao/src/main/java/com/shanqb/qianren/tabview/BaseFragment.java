package com.shanqb.qianren.tabview;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.shanqb.qianren.R;
import com.shanqb.qianren.inter.MyQueueResponse;
import com.shanqb.qianren.utils.MyVolleyUtils;
import com.shanqb.qianren.utils.NetworkUtils;
import com.shanqb.qianren.utils.XToastUtils;
import com.shanqb.qianren.view.LoadingProgressDialog;

import java.util.Map;

/**
 * Created by yx on 16/4/3.
 */
public abstract class BaseFragment extends Fragment{

    public MyVolleyUtils myVolley = null;
    private LoadingProgressDialog mLoadingProgressDialog;
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingProgressDialog = new LoadingProgressDialog(getContext());
        myVolley = new MyVolleyUtils(getActivity());
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        mLoadingProgressDialog = null;
    }

    /**
     * 显示dialog
     */
    protected void showLoadingDialog() {
        if (mLoadingProgressDialog != null && !mLoadingProgressDialog.isShowing()) {
            mLoadingProgressDialog.show();
        }
    }

    /**
     * 关闭dialog
     */
    protected void dismissLoadingDialog() {
        if (mLoadingProgressDialog != null && mLoadingProgressDialog.isShowing()) {
            mLoadingProgressDialog.dismiss();
        }
    }


    protected void requestPostQueue(boolean isShowDialog,String requestAction, Map<String, String> requestParams, MyQueueResponse myQueueResponse){
        try {
            if (!NetworkUtils.checkNetworkConnectionState(getContext())) {//未连接到网络
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

    protected void requestGetQueue(boolean isShowDialog,String requestUrl, MyQueueResponse myQueueResponse){
        try {
            if (!NetworkUtils.checkNetworkConnectionState(getContext())) {//未连接到网络
                XToastUtils.toast(getString(R.string.net_close));
                return;
            }
            if (isShowDialog){
                showLoadingDialog();
            }

            myVolley.requestGetQueue(requestUrl,myQueueResponse);

        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }



}
