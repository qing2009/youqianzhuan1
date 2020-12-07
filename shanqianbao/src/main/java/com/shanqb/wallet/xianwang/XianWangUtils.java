package com.shanqb.wallet.xianwang;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.bun.miitmdid.interfaces.IdSupplier;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.shanqb.wallet.BaseApplication;
import com.shanqb.wallet.utils.MittUtils;
import com.shanqb.wallet.utils.SharedPreConstants;
import com.shanqb.wallet.utils.SharedPreferencesUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 接入享玩sdk使用的工具类
 */
public class XianWangUtils {


    /**
     * 接入享玩sdk使用的常量
     */
    public static final String XIANWANG_PID = "11495";//渠道ID(对接渠道享玩提供)
    public static final String XIANWANG_APPKEY = "PCDDXW8_YQZ_11495";//渠道key(对接渠道享玩提供)


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



    public static String getXwdeviceid(Context context) {

        String xwdeviceid = "";

        if (Build.VERSION.SDK_INT <= 28) {
            xwdeviceid = getIMEI(context, 0) + "," + getIMEI(context, 1) + "," + getMEID(context);
        } else {
            xwdeviceid = SharedPreferencesUtil.getStringValue(context, SharedPreConstants.OAID,"");
        }

        return xwdeviceid;
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
