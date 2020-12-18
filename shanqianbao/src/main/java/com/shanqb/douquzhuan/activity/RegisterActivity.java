package com.shanqb.douquzhuan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shanqb.douquzhuan.BaseApplication;
import com.shanqb.douquzhuan.R;
import com.shanqb.douquzhuan.bean.LoginResponse;
import com.shanqb.douquzhuan.tabview.HomeActivity;
import com.shanqb.douquzhuan.utils.AcitonConstants;
import com.shanqb.douquzhuan.utils.Global;
import com.shanqb.douquzhuan.utils.NetworkUtils;
import com.shanqb.douquzhuan.utils.SharedPreConstants;
import com.shanqb.douquzhuan.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class RegisterActivity extends MyBaseActivity {
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
//    private Button mCancelButton;                     //取消按钮

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
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);

        mSureButton = (Button) findViewById(R.id.register_btn_sure);
//        mCancelButton = (Button) findViewById(R.id.register_btn_cancel);

        mSureButton.setOnClickListener(m_register_Listener);      //注册界面两个按钮的监听事件
//        mCancelButton.setOnClickListener(m_register_Listener);
    }

    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_sure:                       //确认按钮的监听事件
                    register_check();
                    break;
//                case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
//                    Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;    //切换User Activity至Login Activity
//                    startActivity(intent_Register_to_Login);
//                    finish();
//                    break;
            }
        }
    };

    public void register_check() {
        try {

            if (isUserNameAndPwdValid()) {
                String userName = mAccount.getText().toString().trim();
                String userPwd = mPwd.getText().toString().trim();
                String userPwdCheck = mPwdCheck.getText().toString().trim();
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
                                        Toast.makeText(RegisterActivity.this, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();  //登录失败提示
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
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
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }


    @OnClick(R.id.register_textView)
    public void onViewClicked() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
