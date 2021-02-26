package com.shanqb.zhimazhuan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shanqb.zhimazhuan.bean.LoginResponse;

public class LoginUtils {

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
        String channelListJson = new Gson().toJson(loginResponse.getData().getListbc());
        editor.putString(SharedPreConstants.channelList, channelListJson);
        editor.commit();
    }
}