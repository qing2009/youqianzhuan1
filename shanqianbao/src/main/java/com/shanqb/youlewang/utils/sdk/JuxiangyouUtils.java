package com.shanqb.youlewang.utils.sdk;

import android.content.Context;

import com.toomee.mengplus.manager.TooMeeManager;

/**
 *聚享游sdk 工具类
 */
public class JuxiangyouUtils {

//    public static final String MID = "1587";//聚享游提供
//    public static final String TOKEN = "c1c9db60ef940a0b8eee447aedeaaf44";//聚享游提供

    public static void startSDK(Context context,String userId,String channelUser,String channelKey){
        //SDK接入模式
//        String userId = "654321";
        //mid 和 token联系我方提供
        //注意申请动态权限 WRITE_EXTERNAL_STORAGE和READ_PHONE_STATE
//        TooMeeManager.init(context, MID, userId, TOKEN, null);
        TooMeeManager.init(context, channelUser, userId, channelKey, null);
        TooMeeManager.start(context);
    }
}
