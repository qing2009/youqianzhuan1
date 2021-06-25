package com.shanqb.qianren.utils;


import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;

import java.lang.reflect.Method;

/**
 * 设备工具类。
 * 可以获取手机的IMEI、oaid等。
 */
public class DeviceUtils {

    private static final String TAG = "DeviceUtils";


    /**
     * 将oaid保存到配置文件
     * @param context
     */
    public static void initOaid(Context context){

        MdidSdkHelper.InitSdk(context, true, new IIdentifierListener() {
            @Override
            public void OnSupport(boolean b, IdSupplier idSupplier) {

                if(idSupplier==null) {
                    return;
                }
                String oaid=idSupplier.getOAID();
                String vaid=idSupplier.getVAID();
                String aaid=idSupplier.getAAID();

                StringBuilder builder=new StringBuilder();
                builder.append("support: ").append(idSupplier.isSupported()?"true":"false").append("\n");
                builder.append("OAID: ").append(oaid).append("\n");
                builder.append("VAID: ").append(vaid).append("\n");
                builder.append("AAID: ").append(aaid).append("\n");
                String idstext=builder.toString();

                LogUtils.debug(idstext);

                SharedPreferencesUtil.setStringValue(context, SharedPreConstants.OAID, oaid);
                SharedPreferencesUtil.setStringValue(context, SharedPreConstants.VAID, oaid);
                SharedPreferencesUtil.setStringValue(context, SharedPreConstants.AAID, oaid);
            }
        });
    }



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

    /**
     * 获取指定卡槽imei
     * @param con Context
     * @param slotIndex 0:卡槽1，1：卡槽2
     * @return imei
     */
    public static  String getImei(Context con,int slotIndex){
        String imei = "" ;
        try{
            TelephonyManager tm = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int count = tm.getPhoneCount();
                if (count > slotIndex) {
                    imei = tm.getImei(slotIndex);
                }
            }else{
                int phoneCount;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    phoneCount = tm.getPhoneCount();
                }else{
                    Method mcount = tm.getClass().getMethod("getPhoneCount");
                    phoneCount = (int) mcount.invoke(tm);
                }
                if(phoneCount>slotIndex){
                    imei=getImeiInvoke(con,slotIndex);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return  imei;
    }
    private static String getImeiInvoke(Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            return (String) method.invoke(manager, slotId);
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
