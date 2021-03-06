package com.shanqb.douquzhuan.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shanqb.douquzhuan.R;
import com.shanqb.douquzhuan.bean.BaseJsonResponse;
import com.shanqb.douquzhuan.utils.AcitonConstants;
import com.shanqb.douquzhuan.utils.Global;
import com.shanqb.douquzhuan.utils.NetworkUtils;
import com.shanqb.douquzhuan.utils.SharedPreConstants;
import com.shanqb.douquzhuan.utils.SharedPreferencesUtil;
import com.shanqb.douquzhuan.utils.XToastUtils;
import com.toomee.mengplus.common.utils.ToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.actionbar.TitleUtils;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawalAccountActivity extends MyBaseActivity {
    @BindView(R.id.account_clearEditText)
    ClearEditText accountClearEditText;
    @BindView(R.id.name_clearEditText)
    ClearEditText nameClearEditText;
    @BindView(R.id.withdrawalAccount_titlebar)
    TitleBar withdrawalAccountTitlebar;

    @Override
    public void initLayout() {
        setContentView(R.layout.activity_withdrawalaccount);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initWeight() {
        withdrawalAccountTitlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    @OnClick(R.id.login_btn_login)
    public void onClick() {
        if (isZFBAccountAndNameValid()) {
            bindZFB(accountClearEditText.getText().toString(), nameClearEditText.getText().toString());
        }
    }


    public boolean isZFBAccountAndNameValid() {
        if (accountClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.zfb_account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (nameClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.zfb_name_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void bindZFB(String account, String name) {
        try {
            if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                XToastUtils.toast(getString(R.string.net_close));
                return;
            }

            showLoadingDialog();

            String loginUrl = Global.BASE_INTER_URL + AcitonConstants.BIND_ZFB;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(getClass().toString(), response);
                    dismissLoadingDialog();

                    BaseJsonResponse responseBean = new Gson().fromJson(response, new TypeToken<BaseJsonResponse>() {
                    }.getType());
                    if (responseBean != null) {
                        if (responseBean.isSuccess()) {
                            Toast.makeText(WithdrawalAccountActivity.this, getString(R.string.bind_zfb_success), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissLoadingDialog();
                    Log.e(getClass().toString(), error.getMessage(), error);
//                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(AcitonConstants.MERCODE, SharedPreferencesUtil.getStringValue(WithdrawalAccountActivity.this, SharedPreConstants.merCode, ""));
                    map.put(AcitonConstants.BIND_ZFB_ACCOUNT, account);
                    map.put(AcitonConstants.BIND_ZFB_NAME, name);
                    return map;
                }
            };
            mQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }
}
