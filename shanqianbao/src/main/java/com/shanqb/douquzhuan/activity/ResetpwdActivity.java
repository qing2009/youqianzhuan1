package com.shanqb.douquzhuan.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class ResetpwdActivity extends MyBaseActivity {

    //    private EditText mAccount;                        //用户名编辑
    private EditText mPwd_old;                            //密码编辑
    private EditText mPwd_new;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
//    private Button mCancelButton;                     //取消按钮

    @Override
    public void initLayout() {
        setContentView(R.layout.activity_resetpwd);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initWeight() {
        //标题栏
        findViewById(R.id.back_imgView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView titleTextView = (TextView) findViewById(R.id.title_textView);
        titleTextView.setText(getText(R.string.resetpwd));
//        layout.setOrientation(RelativeLayout.VERTICAL).
//        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd_old = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwd_new = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_check);

        mSureButton = (Button) findViewById(R.id.resetpwd_btn_sure);
//        mCancelButton = (Button) findViewById(R.id.resetpwd_btn_cancel);

        mSureButton.setOnClickListener(m_resetpwd_Listener);      //注册界面两个按钮的监听事件
//        mCancelButton.setOnClickListener(m_resetpwd_Listener);
        //mCancelButton.setOnClickListener(m_resetpwd_Listener);
    }

    View.OnClickListener m_resetpwd_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.resetpwd_btn_sure:                       //确认按钮的监听事件
                    resetpwd_check();
                    break;
//                case R.id.resetpwd_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
//                    Intent intent_Resetpwd_to_Login = new Intent(Resetpwd.this,Login.class) ;    //切换Resetpwd Activity至Login Activity
//                    startActivity(intent_Resetpwd_to_Login);
//                    finish();
//                    break;
            }
        }
    };

    public void resetpwd_check() {                                //确认按钮的监听事件
        try {
            if (isUserNameAndPwdValid()) {
                String userPwd_old = mPwd_old.getText().toString().trim();
                String userPwd_new = mPwd_new.getText().toString().trim();
                String userPwdCheck = mPwdCheck.getText().toString().trim();

                if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                    Toast.makeText(this, getString(R.string.net_close), Toast.LENGTH_SHORT).show();
                    return;
                }

                showLoadingDialog();

                String loginUrl = Global.BASE_INTER_URL + AcitonConstants.INTER_UPDATEPSW;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(getClass().toString(), response);
                        dismissLoadingDialog();

                        BaseJsonResponse jsonResponse = new Gson().fromJson(response, new TypeToken<BaseJsonResponse>() {
                        }.getType());
                        if (jsonResponse != null) {
                            if (jsonResponse.isSuccess()) {
                                Toast.makeText(ResetpwdActivity.this, getString(R.string.resetpwd_success), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ResetpwdActivity.this, jsonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ResetpwdActivity.this, getString(R.string.resetpwd_fail), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissLoadingDialog();
                        Log.e(getClass().toString(), error.getMessage(), error);
                        Toast.makeText(ResetpwdActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(AcitonConstants.MERCODE, SharedPreferencesUtil.getStringValue(ResetpwdActivity.this, SharedPreConstants.merCode, ""));
                        map.put(AcitonConstants.UPDATEPSW_OLDPSW, userPwd_old);
                        map.put(AcitonConstants.UPDATEPSW_NEWPSW, userPwd_new);
                        return map;
                    }
                };
                mQueue.add(stringRequest);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isUserNameAndPwdValid() {

        String userPwd_old = mPwd_old.getText().toString().trim();
        String userPwd_new = mPwd_new.getText().toString().trim();
        String userPwdCheck = mPwdCheck.getText().toString().trim();


        if (mPwd_old.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd_new.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_new_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (userPwd_new.equals(userPwdCheck) == false) {           //两次密码输入不一样
            Toast.makeText(this, getString(R.string.pwd_not_the_same), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}

