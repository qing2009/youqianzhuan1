package com.shanqb.douquzhuan.utils.sdk;

import android.app.Activity;
import android.app.Application;

import com.aiyingli.ibxmodule.IBXSdk;
import com.fc.tjcpl.sdk.TJSDK;

/**
 * 爱变现 工具类
 */
public class AibianxianUtils {

    public static final String appKey = "142793066";
    public static final String secret = "d1ea984016d3e97c";

    public static void startSDK(Application application, String userId,Activity activity){
//        IBXSdk.getInstance().showLog(true);
        IBXSdk.getInstance().init(application, appKey,secret,userId).showLog(true).start(activity);
    }
}
