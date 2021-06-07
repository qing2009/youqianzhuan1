package com.shanqb.dingyaolianmeng.utils;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


/**
 * 工具类
 */
public class AntiEmulatorUtils {

    /**
     * 检测是否为模拟器
     *
     * @param context
     * @return
     */
    public boolean isEmulator(Context context) {
        LogUtils.debug("isEmulator...");

        boolean checkResult =
                checkProperty() ||
                        operatorNameCheck(context) ||
                        checkTelephoneStatus(context) ||
                        hasWendu(context) ||
                        notHasLightSensorManager(context) ||
                        notHasBlueTooth();

        LogUtils.debug("是否为模拟器:" + checkResult);
        return checkResult;
    }


    /**
     * 根据运营商名称检测
     *
     * @param context
     * @return
     */
    private static boolean operatorNameCheck(Context context) {
        LogUtils.debug("operatorNameCheck() called with: context = [" + context + "]");

        String operatorName = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            String name = tm.getNetworkOperatorName();
            if (name != null) {
                operatorName = name;
            }
        }
        LogUtils.debug("operatorName:"+operatorName);
        boolean checkOperatorName = operatorName.toLowerCase().equals("android");
        LogUtils.debug("checkOperatorName:"+checkOperatorName);
        LogUtils.debug("\n");
        return checkOperatorName;
    }


    /**
     * 根据参数检测
     *
     * @return
     */
    private static boolean checkProperty() {
        LogUtils.debug( "checkProperty() called");
        boolean result =  Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
        LogUtils.debug("result:"+result);
        LogUtils.debug("\r\n");
        return result;
    }


    /**
     * 通过电池的伏数和温度来判断是真机还是模拟器
     *
     * @return
     */
    public boolean hasWendu(Context context) {
        LogUtils.debug("通过电池的伏数和温度来判断是真机还是模拟器.");
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = context.registerReceiver(null, intentFilter);
        int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);
        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
        LogUtils.debug("voltage : " + voltage);
        LogUtils.debug("temperature : " + temperature);
        if (((voltage == 0) && (temperature == 0))
                || ((voltage == 10000) && (temperature == 0))) {
            //这是通过电池的伏数和温度来判断是真机还是模拟器
            LogUtils.debug("检测结果 : true");
            LogUtils.debug("\n");
            return true;
        } else {
            LogUtils.debug("检测结果 : false");
            LogUtils.debug("\n");
            return false;
        }
    }

    /**
     * 拨打电话
     *
     * @return
     */
    public boolean checkTelephoneStatus(Context context) {
        LogUtils.debug("拨打电话检测");
        String url = "tel:" + "15512340001";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canResolveIntent = intent.resolveActivity(context.getPackageManager()) != null;
        LogUtils.debug("canResolveIntent : " + canResolveIntent);

        LogUtils.debug("检测结果 : " + !canResolveIntent);
        LogUtils.debug("\n");
        return !canResolveIntent;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     *
     * @return true 为模拟器
     */
    public Boolean notHasLightSensorManager(Context context) {
        LogUtils.debug("判断是否存在光传感器来判断是否为模拟器");
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        LogUtils.debug("sensor8 : " + sensor8);
        if (null == sensor8) {
            LogUtils.debug("检测结果 : true");
            LogUtils.debug("\n");
            return true;
        } else {
            LogUtils.debug("检测结果 : false");
            LogUtils.debug("\n");
            return false;
        }
    }

    /*
     *判断蓝牙是否有效来判断是否为模拟器
     *返回:true 为模拟器
     */
    public boolean notHasBlueTooth() {
        LogUtils.debug("判断蓝牙是否有效来判断是否为模拟器");
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            LogUtils.debug("ba : " + ba);
            LogUtils.debug("检测结果 : true");
            LogUtils.debug("\n");
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            LogUtils.debug("BlueToothName : " + name);
            if (TextUtils.isEmpty(name)) {
                LogUtils.debug("检测结果 : true");
                LogUtils.debug("\n");
                return true;
            } else {
                LogUtils.debug("检测结果 : false");
                LogUtils.debug("\n");
                return false;
            }
        }
    }

}
