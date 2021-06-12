package com.shanqb.mayi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.component.dly.xzzq_ywsdk.YwSDK;
import com.google.gson.Gson;
import com.shanqb.mayi.R;
import com.shanqb.mayi.bean.LoginResponse;
import com.shanqb.mayi.tabview.HomeActivity;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

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
        editor.putString(SharedPreConstants.ISSLOGIN, loginResponse.getData().getIsSlogin());

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


    /**
     * 是否需要模拟器检测
     *  如果不需要：打开主页
     *  如果需要：判断是否为模拟器：
     *      非模拟器：打开主页
     *      模拟器：弹出提示
     *          点击确定退出应用。
     */
    public static void emulatorCheck(Activity activity, String toastString){

        String isSlogin = SharedPreferencesUtil.getStringValue(activity.getApplicationContext(),SharedPreConstants.ISSLOGIN,"");
        if (SharedPreConstants.ISSLOGIN_OPNE.equals(isSlogin)){//需要检测
            boolean isEmulator = SharedPreferencesUtil.getBooleanValue(activity.getApplicationContext(),SharedPreConstants.ISEMULATOR,false);
            if (isEmulator){//当前设备为模拟器
                new MaterialDialog.Builder(activity)
                        .title(R.string.notice)
                        .content(R.string.emulator_tips)
                        .positiveText(R.string.ok)
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                System.exit(0);
                            }
                        })
                        .cancelable(false)
                        .show();
                return;
            }
        }

        if (!TextUtils.isEmpty(toastString)){
            Toast.makeText(activity, toastString, Toast.LENGTH_SHORT).show();//登录成功提示
        }
        activity.startActivity(new Intent(activity, HomeActivity.class));
        activity.finish();
    }

}