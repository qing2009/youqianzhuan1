package com.shanqb.xiongmaowangwang.utils.sdk;


import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.pceggs.workwall.util.PceggsWallUtils;
import com.shanqb.xiongmaowangwang.utils.DeviceUtils;
import com.shanqb.xiongmaowangwang.utils.SharedPreConstants;
import com.shanqb.xiongmaowangwang.utils.SharedPreferencesUtil;

/**
 * 接入享玩sdk使用的工具类
 */
public class XianWangUtils {

    /**
     * 接入享玩sdk使用的常量
     */
//    public static final String XIANWANG_PID = "11495";//渠道ID(对接渠道享玩提供)
//    public static final String XIANWANG_APPKEY = "PCDDXW8_YQZ_11495";//渠道key(对接渠道享玩提供)



    public static void startSDK(Activity context,String channelUser,String channelKey){
        String deviceId = DeviceUtils.getDeviceId(context);
        String xwdeviceid = getXwdeviceid(context);
        String merCode = SharedPreferencesUtil.getStringValue(context, SharedPreConstants.merCode, "");

//        PceggsWallUtils.loadAd(context, XIANWANG_PID, XIANWANG_APPKEY, merCode, deviceId, xwdeviceid);
        PceggsWallUtils.loadAd(context, channelUser, channelKey, merCode, deviceId, xwdeviceid);
//      PceggsWallUtils.loadAd(context, "10001", "PCDDXW_CS_10001", userid + "", deviceId, xwdeviceid);
    }



    public static String getXwdeviceid(Context context) {
        String xwdeviceid = "";

        if (Build.VERSION.SDK_INT <= 28) {
            xwdeviceid =  DeviceUtils.getIMEI(context, 0) + "," +  DeviceUtils.getIMEI(context, 1) + "," +  DeviceUtils.getMEID(context);
        } else {
            xwdeviceid = SharedPreferencesUtil.getStringValue(context, SharedPreConstants.OAID,"");
        }

        return xwdeviceid;
    }

}
