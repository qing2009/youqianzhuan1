package com.shanqb.weishouzhuan.utils.sdk;

import android.app.Activity;

import com.fc.tjcpl.sdk.TJSDK;

/**
 * 91淘金CPL 工具类
 */
public class Taojing91Utils {

    public static final String appId  = "1957";
    public static final String appkey = "359b2780e4f0d05db9241c179b8fd3cf";

    public static void startSDK(Activity context, String userId){
        //初始化sdk,TJSDK.init(appId,appSecret,userId)
        //appId，appKey,创建媒体后获取
        //userId：媒体app中的用户id,媒体采用自己的提现系统时，必传，支持数字、字母混合，最长64位
        TJSDK.init(appId,appkey,userId);
        //展示sdk,进入列表界面
        TJSDK.show(context);
    }
}
