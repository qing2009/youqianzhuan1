package com.shanqb.douquzhuan.utils.sdk;

import android.content.Context;

import com.toomee.mengplus.manager.TooMeeManager;

/**
 *聚享游sdk 工具类
 */
public class JuxiangyouUtils {

    public static final String MID = "55";//聚享游提供
    public static final String TOKEN = "82b76d8e12a337ac91a9fd464a3ac12a";//聚享游提供

    public static void startSDK(Context context,String userId){
        //SDK接入模式
//        String userId = "654321";
        //mid 和 token联系我方提供
        //注意申请动态权限 WRITE_EXTERNAL_STORAGE和READ_PHONE_STATE
        TooMeeManager.init(context, MID, userId, TOKEN, null);
        TooMeeManager.start(context);
    }
}
