package com.shanqb.douquzhuan.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.shanqb.douquzhuan.BaseApplication;
import com.shanqb.douquzhuan.R;
import com.shanqb.douquzhuan.bean.LoginResponse;
import com.shanqb.douquzhuan.tabview.HomeActivity;
import com.shanqb.douquzhuan.utils.AcitonConstants;
import com.shanqb.douquzhuan.utils.DeviceUtils;
import com.shanqb.douquzhuan.utils.Global;
import com.shanqb.douquzhuan.utils.NetworkUtils;
import com.shanqb.douquzhuan.utils.SharedPreConstants;
import com.shanqb.douquzhuan.utils.SharedPreferencesUtil;
import com.shanqb.douquzhuan.utils.sdk.XianWangUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.PasswordEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends MyBaseActivity {
    @BindView(R.id.register_account_clearEditText)
    ClearEditText registerAccountClearEditText;
    @BindView(R.id.register_pwd_clearEditText)
    PasswordEditText registerPwdClearEditText;
    @BindView(R.id.register_confirmPwd_clearEditText)
    PasswordEditText registerConfirmPwdClearEditText;


    @Override
    public void initLayout() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initWeight() {
    }


    public void register_check() {
        try {

            if (isUserNameAndPwdValid()) {
                String userName = registerAccountClearEditText.getText().toString().trim();
                String userPwd = registerPwdClearEditText.getText().toString().trim();
                String userPwdCheck = registerConfirmPwdClearEditText.getText().toString().trim();
                String imei = DeviceUtils.getDeviceId(this);

                if (userPwd.equals(userPwdCheck) == false) {     //两次密码输入不一样
                    Toast.makeText(this, getString(R.string.pwd_not_the_same), Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                        Toast.makeText(this, getString(R.string.net_close), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    showLoadingDialog();

                    String loginUrl = Global.BASE_INTER_URL + AcitonConstants.INTER_REGISTER;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(getClass().toString(), response);
                            dismissLoadingDialog();

                            LoginResponse loginResponse = new Gson().fromJson(response, new TypeToken<LoginResponse>() {
                            }.getType());
                            if (loginResponse != null) {
                                if (loginResponse.isSuccess()) {

                                    if (loginResponse.getData() != null) {
                                        SharedPreferences preferences = SharedPreferencesUtil.getInterface(RegisterActivity.this);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putInt(SharedPreConstants.userid, loginResponse.getData().getId());
                                        editor.putString(SharedPreConstants.loginCode, loginResponse.getData().getLoginCode());
//                                        editor.putString(SharedPreConstants.loginPwd, loginResponse.getData().getLoginPwd());
                                        editor.putString(SharedPreConstants.loginPwd, userPwd);
                                        editor.putString(SharedPreConstants.realName, loginResponse.getData().getRealName());
                                        editor.putString(SharedPreConstants.merCode, loginResponse.getData().getMerCode());
                                        editor.putString(SharedPreConstants.merName, loginResponse.getData().getMerName());
                                        editor.putString(SharedPreConstants.merPhoto, loginResponse.getData().getMerPhoto());
                                        editor.putString(SharedPreConstants.merPhone, loginResponse.getData().getMerPhone());
                                        editor.putString(SharedPreConstants.allAmt, loginResponse.getData().getAllAmt() + "");
                                        editor.putString(SharedPreConstants.txAmt, loginResponse.getData().getTxAmt() + "");
                                        editor.commit();

                                        BaseApplication.getInstance().clearActivityList();

                                        Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();//登录成功提示
                                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                        finish();


                                    } else {
//                                        Toast.makeText(RegisterActivity.this, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                                        showSimpleTipDialog(getString(R.string.register_fail));
                                    }
                                } else {
//                                    Toast.makeText(RegisterActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();  //登录失败提示
                                    showSimpleTipDialog(loginResponse.getMessage());

                                }
                            } else {
//                                Toast.makeText(RegisterActivity.this, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                                showSimpleTipDialog(getString(R.string.register_fail));
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dismissLoadingDialog();
                            Log.e(getClass().toString(), error.getMessage(), error);
                            Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();  //登录失败提示
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put(AcitonConstants.LOGIN_USERNAME, userName);
                            map.put(AcitonConstants.LOGIN_PASSWORD, userPwd);
                            map.put(AcitonConstants.INTER_REGISTER_IMEI, imei);
                            return map;
                        }
                    };
                    mQueue.add(stringRequest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (registerAccountClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (registerPwdClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (registerConfirmPwdClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }



    @OnClick({R.id.register_btn_sure, R.id.toLogin_textView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_sure:

                PermissionX.init(this)
                        .permissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE)
                        .onExplainRequestReason(new ExplainReasonCallback() {
                            @Override
                            public void onExplainReason(ExplainScope scope, List<String> deniedList) {
                                scope.showRequestReasonDialog(deniedList, getString(R.string.need_agree_permissions), getString(R.string.agree), getString(R.string.cancel));
                            }
                        })
                        .onForwardToSettings(new ForwardToSettingsCallback() {
                            @Override
                            public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                                scope.showForwardToSettingsDialog(deniedList, getString(R.string.to_set_open_permissions), getString(R.string.openSet), getString(R.string.cancel));
                            }
                        })
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                                if (allGranted) {
                                    register_check();
                                } else {
//                                    Toast.makeText(getActivity(), "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                break;
            case R.id.toLogin_textView:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    /**
     * 简单的提示性对话框
     */
    private void showSimpleTipDialog(String contentString) {
        new MaterialDialog.Builder(this)
                .title(R.string.notice)
                .content(contentString)
                .positiveText(R.string.ok)
                .show();
    }
}
