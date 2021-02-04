package com.shanqb.ttaiyou.utils.sdk;

import android.app.Activity;
import android.app.Application;

import com.aiyingli.ibxmodule.IBXSdk;


/**
 * 爱变现 工具类
 */
public class AibianxianUtils {

    public static final String appKey = "142793143";
    public static final String secret = "720bbfe6e6c97a58";

    public static void startSDK(Application application, String userId,Activity activity){
//        IBXSdk.getInstance().showLog(true);
        IBXSdk.getInstance().init(application, appKey,secret,userId).showLog(true).start(activity);
    }
}
