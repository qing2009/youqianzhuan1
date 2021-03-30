package com.shanqb.youzhuanwang51.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.component.dly.xzzq_ywsdk.YwSDK;
import com.google.gson.Gson;
import com.shanqb.youzhuanwang51.bean.LoginResponse;

public class LoginUtils {
    private static final String TAG = "LoginUtils";

    public static void saveUserInfo(Context context,LoginResponse loginResponse,String userPwd){
        SharedPreferences preferences = SharedPreferencesUtil.getInterface(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SharedPreConstants.userid, loginResponse.getData().getId());
        editor.putString(SharedPreConstants.loginCode, loginResponse.getData().getLoginCode());
//        editor.putString(SharedPreConstants.loginPwd, loginResponse.getData().getLoginPwd());
        editor.putString(SharedPreConstants.loginPwd, userPwd);
        editor.putString(SharedPreConstants.realName, loginResponse.getData().getRealName());
        editor.putString(SharedPreConstants.merCode, loginResponse.getData().getMerCode());
        editor.putString(SharedPreConstants.merName, loginResponse.getData().getMerName());
        editor.putString(SharedPreConstants.merPhoto, loginResponse.getData().getMerPhoto());
        editor.putString(SharedPreConstants.merPhone, loginResponse.getData().getMerPhone());
        editor.putString(SharedPreConstants.zfb, loginResponse.getData().getZfb());
        editor.putString(SharedPreConstants.zfbName, loginResponse.getData().getZfbName());
        editor.putString(SharedPreConstants.allAmt, loginResponse.getData().getAllAmt() + "");
        editor.putString(SharedPreConstants.txAmt, loginResponse.getData().getTxAmt() + "");
        editor.putString(SharedPreConstants.shareCode, loginResponse.getData().getShareCode() + "");
        editor.putString(SharedPreConstants.txMinAmt, loginResponse.getData().getTxMinAmt());

        editor.putString(SharedPreConstants.gonggao, loginResponse.getGonggao());
        editor.putString(SharedPreConstants.qdbg, loginResponse.getQdbg());

        String channelListJson = new Gson().toJson(loginResponse.getData().getListbc());
        editor.putString(SharedPreConstants.channelList, channelListJson);
        editor.commit();

        initYwSDk(loginResponse);
    }

    private static void initYwSDk(LoginResponse loginResponse) {
        try {
            YwSDK.Companion.refreshMediaUserId(loginResponse.getData().getMerCode());
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "initYwSDk: Exception:", e);
        }
    }
}