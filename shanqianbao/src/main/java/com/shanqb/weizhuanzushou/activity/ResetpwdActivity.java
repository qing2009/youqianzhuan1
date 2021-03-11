package com.shanqb.weizhuanzushou.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shanqb.weizhuanzushou.R;
import com.shanqb.weizhuanzushou.bean.BaseJsonResponse;
import com.shanqb.weizhuanzushou.utils.AcitonConstants;
import com.shanqb.weizhuanzushou.utils.Global;
import com.shanqb.weizhuanzushou.utils.NetworkUtils;
import com.shanqb.weizhuanzushou.utils.SharedPreConstants;
import com.shanqb.weizhuanzushou.utils.SharedPreferencesUtil;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.PasswordEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetpwdActivity extends MyBaseActivity {


    @BindView(R.id.resetPwd_titlebar)
    TitleBar resetPwdTitlebar;
    @BindView(R.id.resetPwd_btn_sure)
    Button resetPwdBtnSure;
    @BindView(R.id.resetPwd_oldPwd_clearEditText)
    PasswordEditText resetPwdOldPwdClearEditText;
    @BindView(R.id.resetPwd_newPwd_clearEditText)
    PasswordEditText resetPwdNewPwdClearEditText;
    @BindView(R.id.resetPwd_confirmNewPwd_clearEditText)
    PasswordEditText resetPwdConfirmNewPwdClearEditText;

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
        resetPwdTitlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public void resetpwd_check() {                                //确认按钮的监听事件
        try {
            if (isUserNameAndPwdValid()) {
                String userPwd_old = resetPwdOldPwdClearEditText.getText().toString().trim();
                String userPwd_new = resetPwdNewPwdClearEditText.getText().toString().trim();
                String userPwdCheck = resetPwdConfirmNewPwdClearEditText.getText().toString().trim();

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

        String userPwd_old = resetPwdOldPwdClearEditText.getText().toString().trim();
        String userPwd_new = resetPwdNewPwdClearEditText.getText().toString().trim();
        String userPwdCheck = resetPwdConfirmNewPwdClearEditText.getText().toString().trim();


        if (resetPwdOldPwdClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (resetPwdNewPwdClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_new_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (resetPwdConfirmNewPwdClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (userPwd_new.equals(userPwdCheck) == false) {           //两次密码输入不一样
            Toast.makeText(this, getString(R.string.pwd_not_the_same), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @OnClick(R.id.resetPwd_btn_sure)
    public void onClick() {
        resetpwd_check();
    }
}

