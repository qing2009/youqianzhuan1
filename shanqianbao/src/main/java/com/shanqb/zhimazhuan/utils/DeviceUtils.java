package com.shanqb.zhimazhuan.utils;


import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * 设备工具类。
 * 可以获取手机的IMEI、oaid等。
 */
public class DeviceUtils {



    public static String getDeviceId(Context context) {

        String deviceId = "";

        if (Build.VERSION.SDK_INT <= 28) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } else {
            deviceId = SharedPreferencesUtil.getStringValue(context, SharedPreConstants.OAID,"");
        }

        return deviceId;
    }




    /**
     * @param slotId slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    public static String getIMEI(Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, slotId);
            return imei;
        } catch (Exception e) {
            return "";
        }
    }
    public static String getMEID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        try {
            Method getMeid = tm.getClass().getDeclaredMethod("getMeid");
            deviceId = (String) getMeid.invoke(tm);
        } catch (Throwable e) {
            return "";
        }
        if (null == deviceId) {
            deviceId = "";
        }
        return deviceId;
    }


}
