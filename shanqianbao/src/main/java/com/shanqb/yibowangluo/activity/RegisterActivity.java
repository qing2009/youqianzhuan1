package com.shanqb.yibowangluo.activity;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.shanqb.yibowangluo.BaseApplication;
import com.shanqb.yibowangluo.R;
import com.shanqb.yibowangluo.bean.BaseJsonResponse;
import com.shanqb.yibowangluo.bean.LoginResponse;
import com.shanqb.yibowangluo.utils.ActionConstants;
import com.shanqb.yibowangluo.utils.DeviceUtils;
import com.shanqb.yibowangluo.utils.Global;
import com.shanqb.yibowangluo.utils.NetworkUtils;
import com.shanqb.yibowangluo.utils.StringUtils;
import com.shanqb.yibowangluo.utils.XToastUtils;
import com.shanqb.yibowangluo.utils.LoginUtils;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends MyBaseActivity {
    @BindView(R.id.stv_register)
    SuperTextView stvRegister;
    @BindView(R.id.stv_register_pwd)
    SuperTextView stvRegisterPwd;
    @BindView(R.id.stv_register_pwd_confirmPwd)
    SuperTextView stvRegisterPwdConfirmPwd;
    @BindView(R.id.register_btn_sure)
    RoundButton registerBtnSure;
    @BindView(R.id.toLogin_textView)
    TextView toLoginTextView;
    @BindView(R.id.stv_verify_code)
    SuperTextView stvVerifyCode;
    @BindView(R.id.btn_get_verify_code)
    RoundButton btnGetVerifyCode;

    private CountDownButtonHelper mCountDownHelper;


    @Override
    public void initLayout() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {
        mCountDownHelper = new CountDownButtonHelper(btnGetVerifyCode, 60);
    }

    @Override
    public void initWeight() {
        toLoginTextView.setText(StringUtils.setColor4Key(getString(R.string.goto_login), getString(R.string.login), getResources().getColor(R.color.custom_color_main_theme)));
    }


    public void register_check() {
        try {

            if (isUserNameAndPwdValid()) {
                String userName = stvRegister.getCenterEditValue().trim();
                String userPwd = stvRegisterPwd.getCenterEditValue().trim();
                String userPwdCheck = stvRegisterPwdConfirmPwd.getCenterEditValue().trim();
                String imei = DeviceUtils.getDeviceId(getApplicationContext());

                if (userPwd.equals(userPwdCheck) == false) {     //两次密码输入不一样
                    Toast.makeText(this, getString(R.string.pwd_not_the_same), Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                        Toast.makeText(this, getString(R.string.net_close), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    showLoadingDialog();

                    String loginUrl = Global.BASE_INTER_URL + ActionConstants.INTER_REGISTER;
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
                                        LoginUtils.saveUserInfo(getApplicationContext(),loginResponse,userPwd);

                                        BaseApplication.getInstance().clearActivityList();

                                        LoginUtils.emulatorCheck(RegisterActivity.this, getString(R.string.register_success));
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
                            map.put(ActionConstants.LOGIN_USERNAME, userName);
                            map.put(ActionConstants.LOGIN_PASSWORD, userPwd);
                            map.put(ActionConstants.INTER_REGISTER_IMEI, imei);
                            map.put(ActionConstants.INTER_REGISTER_VCODE, stvVerifyCode.getCenterEditValue().trim());
                            map.put(ActionConstants.INTER_REGISTER_BUSINESSCODE, Global.BUSINESS_CODE);
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
        if (stvRegister.getCenterEditValue().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(stvVerifyCode.getCenterEditValue().trim())) {
            Toast.makeText(this, getString(R.string.please_input_code),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if (stvRegisterPwd.getCenterEditValue().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (stvRegisterPwdConfirmPwd.getCenterEditValue().trim().equals("")) {
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

    @OnClick(R.id.btn_get_verify_code)
    public void onClick() {
        if (!stvRegister.getCenterEditValue().trim().equals("")) {stvRegister.invalidate();
            getVerifyCode(stvRegister.getCenterEditValue().trim());
        }else {
            XToastUtils.toast(getString(R.string.hint_username));
        }
    }


    /**
     * 获取验证码
     */
    private void getVerifyCode(String phoneNumber) {
        if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
            Toast.makeText(this, getString(R.string.net_close), Toast.LENGTH_SHORT).show();
            return;
        }

        mCountDownHelper.start();

        showLoadingDialog();

        String loginUrl = Global.BASE_INTER_URL + ActionConstants.INTER_GETPHONEMSG;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(getClass().toString(), response);
                    dismissLoadingDialog();

                    BaseJsonResponse jsonResponse = new Gson().fromJson(response, new TypeToken<BaseJsonResponse>() {
                    }.getType());
                    if (jsonResponse != null) {
                        if (jsonResponse.isSuccess()) {
//                        Toast.makeText(RegisterActivity.this, getString(R.string.send_success), Toast.LENGTH_SHORT).show();//
                            Toast.makeText(RegisterActivity.this, jsonResponse.getMessage(), Toast.LENGTH_SHORT).show();//

                        } else {
                            showSimpleTipDialog(jsonResponse.getMessage());

                        }
                    } else {
                        showSimpleTipDialog(getString(R.string.getMsg_fail));
                        mCountDownHelper.cancel();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    showSimpleTipDialog(getString(R.string.getMsg_fail));
                    mCountDownHelper.cancel();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissLoadingDialog();
                mCountDownHelper.cancel();
                Log.e(getClass().toString(), error.getMessage(), error);
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(ActionConstants.INTER_GETPHONEMSG_PHONENUM, phoneNumber);
                map.put(ActionConstants.LOGIN_businessCode, Global.BUSINESS_CODE);
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
