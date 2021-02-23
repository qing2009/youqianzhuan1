package com.shanqb.weishouzhuan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shanqb.weishouzhuan.BaseApplication;
import com.shanqb.weishouzhuan.R;
import com.shanqb.weishouzhuan.bean.LoginResponse;
import com.shanqb.weishouzhuan.tabview.HomeActivity;
import com.shanqb.weishouzhuan.utils.AcitonConstants;
import com.shanqb.weishouzhuan.utils.Global;
import com.shanqb.weishouzhuan.utils.NetworkUtils;
import com.shanqb.weishouzhuan.utils.SharedPreConstants;
import com.shanqb.weishouzhuan.utils.SharedPreferencesUtil;
import com.shanqb.weishouzhuan.utils.StringUtils;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends MyBaseActivity {                 //登录界面活动

    public int pwdresetFlag = 0;
    @BindView(R.id.stv_login_account)
    SuperTextView stvLoginAccount;
    @BindView(R.id.stv_login_pwd)
    SuperTextView stvLoginPwd;
//    private EditText mAccount;                        //用户名编辑
//    private EditText mPwd;                            //密码编辑
    //    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮
//    private Button mCancleButton;                     //注销按钮
//    private CheckBox mRememberCheck;

    private SharedPreferences login_sp;
    private String userNameValue, passwordValue;

//    private View loginView;                           //登录
//    private View loginSuccessView;
//    private TextView loginSuccessShow;
    private TextView registText;


    @Override
    public void initLayout() {
        setContentView(R.layout.login);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {

        String userName = SharedPreferencesUtil.getStringValue(this, SharedPreConstants.loginCode, "");
        String userPwd = SharedPreferencesUtil.getStringValue(this, SharedPreConstants.loginPwd, "");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPwd)) {
//            mAccount = (EditText) findViewById(R.id.login_edit_account);
//            mPwd = (EditText) findViewById(R.id.login_edit_pwd);
            stvLoginAccount.setCenterEditString(userName);
            stvLoginPwd.setCenterEditString(userPwd);
            autoLogin(userName, userPwd);
        }

    }

    @Override
    public void initWeight() {

        //通过id找到相应的控件
//        mAccount = (EditText) findViewById(R.id.login_edit_account);
//        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
//        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
//        mCancleButton = (Button) findViewById(R.id.login_btn_cancle);
//        loginView = findViewById(R.id.login_view);
//        loginSuccessView = findViewById(R.id.login_success_view);
//        loginSuccessShow = (TextView) findViewById(R.id.login_success_show);

        registText = (TextView) findViewById(R.id.register_textView);
        registText.setText(StringUtils.setColor4Key(getString(R.string.no_ccount_sign_up),getString(R.string.register),getResources().getColor(R.color.custom_color_main_theme)));
//        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);

        login_sp = getSharedPreferences("userInfo", 0);
        String name = login_sp.getString("USER_NAME", "");
        String pwd = login_sp.getString("PASSWORD", "");
        boolean choseRemember = login_sp.getBoolean("mRememberCheck", false);
        boolean choseAutoLogin = login_sp.getBoolean("mAutologinCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if (choseRemember) {
//            mAccount.setText(name);
//            mPwd.setText(pwd);
            stvLoginAccount.setCenterEditString(name);
            stvLoginAccount.setCenterEditString(pwd);
//            mRememberCheck.setChecked(true);
        }

//        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);
//        mCancleButton.setOnClickListener(mListener);
        registText.setOnClickListener(mListener);
    }


    OnClickListener mListener = new OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.login_btn_register:                            //登录界面的注册按钮
//                    Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //切换Login Activity至User Activity
//                    startActivity(intent_Login_to_Register);
//                    finish();
//                    break;
                case R.id.login_btn_login:                              //登录界面的登录按钮
                    if (isUserNameAndPwdValid()) {
                        String userName = stvLoginAccount.getCenterEditValue().trim();    //获取当前输入的用户名和密码信息
                        String userPwd = stvLoginPwd.getCenterEditValue().trim();
                        login(userName, userPwd);
                    }
                    break;
//                case R.id.login_btn_cancle:                             //登录界面的注销按钮
//                    cancel();
//                    break;
                case R.id.register_textView:                             //登录界面的注销按钮
                    BaseApplication.getInstance().addActivity2List(LoginActivity.this);
                    Intent intent_Login_to_reset = new Intent(LoginActivity.this, RegisterActivity.class);    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_reset);
                    finish();
                    break;
            }
        }
    };

    public void login(String userName, String userPwd) {
        try {
            if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                Toast.makeText(this, getString(R.string.net_close), Toast.LENGTH_SHORT).show();
                return;
            }

            showLoadingDialog();

            String loginUrl = Global.BASE_INTER_URL + AcitonConstants.INTER_LOGIN;
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
                                SharedPreferences preferences = SharedPreferencesUtil.getInterface(LoginActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt(SharedPreConstants.userid, loginResponse.getData().getId());
                                editor.putString(SharedPreConstants.loginCode, loginResponse.getData().getLoginCode());
//                                    editor.putString(SharedPreConstants.loginPwd, loginResponse.getData().getLoginPwd());
                                editor.putString(SharedPreConstants.loginPwd, userPwd);
                                editor.putString(SharedPreConstants.realName, loginResponse.getData().getRealName());
                                editor.putString(SharedPreConstants.merCode, loginResponse.getData().getMerCode());
                                editor.putString(SharedPreConstants.merName, loginResponse.getData().getMerName());
                                editor.putString(SharedPreConstants.merPhoto, loginResponse.getData().getMerPhoto());
                                editor.putString(SharedPreConstants.merPhone, loginResponse.getData().getMerPhone());
                                editor.putString(SharedPreConstants.allAmt, loginResponse.getData().getAllAmt() + "");
                                editor.putString(SharedPreConstants.txAmt, loginResponse.getData().getTxAmt() + "");
                                editor.putString(SharedPreConstants.shareCode, loginResponse.getData().getShareCode() + "");
                                editor.commit();

                                Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();//登录成功提示
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();


                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();  //登录失败提示
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissLoadingDialog();
                    Log.e(getClass().toString(), error.getMessage(), error);
                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(AcitonConstants.LOGIN_USERNAME, userName);
                    map.put(AcitonConstants.LOGIN_PASSWORD, userPwd);
                    return map;
                }
            };
            mQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }


    public void autoLogin(String userName, String userPwd) {
        try {
            if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                return;
            }

            showLoadingDialog();

            String loginUrl = Global.BASE_INTER_URL + AcitonConstants.INTER_LOGIN;
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
                                SharedPreferences preferences = SharedPreferencesUtil.getInterface(LoginActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt(SharedPreConstants.userid, loginResponse.getData().getId());
                                editor.putString(SharedPreConstants.loginCode, loginResponse.getData().getLoginCode());
//                                    editor.putString(SharedPreConstants.loginPwd, loginResponse.getData().getLoginPwd());
                                editor.putString(SharedPreConstants.loginPwd, userPwd);
                                editor.putString(SharedPreConstants.realName, loginResponse.getData().getRealName());
                                editor.putString(SharedPreConstants.merCode, loginResponse.getData().getMerCode());
                                editor.putString(SharedPreConstants.merName, loginResponse.getData().getMerName());
                                editor.putString(SharedPreConstants.merPhoto, loginResponse.getData().getMerPhoto());
                                editor.putString(SharedPreConstants.merPhone, loginResponse.getData().getMerPhone());
                                editor.putString(SharedPreConstants.allAmt, loginResponse.getData().getAllAmt() + "");
                                editor.putString(SharedPreConstants.txAmt, loginResponse.getData().getTxAmt() + "");
                                editor.commit();

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                                startActivity(new Intent(LoginActivity.this, BottomNavigationViewBehaviorActivity.class));
                                finish();


                            }
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
                    map.put(AcitonConstants.LOGIN_USERNAME, userName);
                    map.put(AcitonConstants.LOGIN_PASSWORD, userPwd);
                    return map;
                }
            };
            mQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (stvLoginAccount.getCenterEditValue().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (stvLoginPwd.getCenterEditValue().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
