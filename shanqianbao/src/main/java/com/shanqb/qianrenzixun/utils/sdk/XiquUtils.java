package com.shanqb.qianrenzixun.utils.sdk;


import android.app.Application;
import android.content.Context;

import com.shanqb.qianrenzixun.utils.DeviceUtils;
import com.xiqu.sdklibrary.helper.XQADPage;
import com.xiqu.sdklibrary.helper.XQADPageConfig;
import com.xiqu.sdklibrary.helper.XQAdSdk;


/**
 * 嘻趣sdk工具类
 * 接入文档：https://www.showdoc.com.cn/XiQuChannelV2?page_id=3398243819072419
 */
public class XiquUtils {
//    private static String appid = "4343";
//    private static String appsecret = "n999rtvueaxm1bef";

    public static void startSDK(Context context, String userId){
        //进入列表页
        XQADPage.jumpToAD(new XQADPageConfig.Builder(userId)
                .pageType(XQADPageConfig.PAGE_AD_LIST)
                .msaOAID(DeviceUtils.getDeviceId(context))
                .build());

    }

    public static void init(Application baseApplication, String channelUser, String channelKey) {
//        XQAdSdk.init(baseApplication, appid, appsecret); //初始化 参数
        XQAdSdk.init(baseApplication, channelUser, channelKey); //初始化 参数
        XQAdSdk.showLOG(true); //是否开启日志
    }
}
