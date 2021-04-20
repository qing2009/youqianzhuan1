package diff.strazzere.anti;


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
import android.util.Log;

import diff.strazzere.anti.debugger.FindDebugger;
import diff.strazzere.anti.emulator.FindEmulator;
import diff.strazzere.anti.monkey.FindMonkey;
import diff.strazzere.anti.taint.FindTaint;

/**
 * 工具类
 */
public class AntiEmulatorUtils {


    /**
     * 检测是否为模拟器
     * @param context
     * @return
     */
    public boolean isEmulator(Context context) {
        log("isEmulator...");

        boolean checkResult =
                        hasWendu(context) ||
                        checkTelephoneStatus(context) ||
                        notHasLightSensorManager(context) ||
                        notHasBlueTooth() ||
                        isQEmuEnvDetected(context) ||
                        isTaintTrackingDetected(context) ||
                        isMonkeyDetected() ||
                        isDebugged();

        log("检测结果:"+checkResult);
        return checkResult;
    }


    public boolean isQEmuEnvDetected(Context context) {
        log("Checking for QEmu env...");
        log("hasKnownDeviceId : " + FindEmulator.hasKnownDeviceId(context.getApplicationContext()));
        log("hasKnownPhoneNumber : " + FindEmulator.hasKnownPhoneNumber(context.getApplicationContext()));
        log("isOperatorNameAndroid : " + FindEmulator.isOperatorNameAndroid(context.getApplicationContext()));
        log("hasKnownImsi : " + FindEmulator.hasKnownImsi(context.getApplicationContext()));
        log("hasEmulatorBuild : " + FindEmulator.hasEmulatorBuild(context.getApplicationContext()));
        log("hasPipes : " + FindEmulator.hasPipes());
        log("hasQEmuDriver : " + FindEmulator.hasQEmuDrivers());
        log("hasQEmuFiles : " + FindEmulator.hasQEmuFiles());
        log("hasGenyFiles : " + FindEmulator.hasGenyFiles());
        log("hasEmulatorAdb :" + FindEmulator.hasEmulatorAdb());
        for (String abi : Build.SUPPORTED_ABIS) {
            if (abi.equalsIgnoreCase("armeabi-v7a")) {
                log("hitsQemuBreakpoint : " + FindEmulator.checkQemuBreakpoint());
            }
        }
        if (FindEmulator.hasKnownDeviceId(context.getApplicationContext())
                || FindEmulator.hasKnownImsi(context.getApplicationContext())
                || FindEmulator.hasEmulatorBuild(context.getApplicationContext())
                || FindEmulator.hasKnownPhoneNumber(context.getApplicationContext()) || FindEmulator.hasPipes()
                || FindEmulator.hasQEmuDrivers() || FindEmulator.hasEmulatorAdb()
                || FindEmulator.hasQEmuFiles()
                || FindEmulator.hasGenyFiles()) {
            log("QEmu environment detected.");
            log("\n");
            return true;
        } else {
            log("QEmu environment not detected.");
            log("\n");
            return false;
        }
    }

    public boolean isTaintTrackingDetected(Context context) {
        log("Checking for Taint tracking...");
        log("hasAppAnalysisPackage : " + FindTaint.hasAppAnalysisPackage(context.getApplicationContext()));
        log("hasTaintClass : " + FindTaint.hasTaintClass());
        log("hasTaintMemberVariables : " + FindTaint.hasTaintMemberVariables());
        if (FindTaint.hasAppAnalysisPackage(context.getApplicationContext()) || FindTaint.hasTaintClass()
                || FindTaint.hasTaintMemberVariables()) {
            log("Taint tracking was detected.");
            log("\n");
            return true;
        } else {
            log("Taint tracking was not detected.");
            log("\n");
            return false;
        }
    }

    public boolean isMonkeyDetected() {
        log("Checking for Monkey user...");
        log("isUserAMonkey : " + FindMonkey.isUserAMonkey());

        if (FindMonkey.isUserAMonkey()) {
            log("Monkey user was detected.");
            log("\n");
            return true;
        } else {
            log("Monkey user was not detected.");
            log("\n");
            return false;
        }
    }

    public boolean isDebugged() {
        log("Checking for debuggers...");

        boolean tracer = false;
        try {
            tracer = FindDebugger.hasTracerPid();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (FindDebugger.isBeingDebugged() || tracer) {
            log("Debugger was detected");
            log("\n");
            return true;
        } else {
            log("No debugger was detected.");
            log("\n");
            return false;
        }
    }


    /**
     * 通过电池的伏数和温度来判断是真机还是模拟器
     *
     * @return
     */
    public boolean hasWendu(Context context) {
        log("通过电池的伏数和温度来判断是真机还是模拟器.");
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = context.registerReceiver(null, intentFilter);
        int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);
        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
        log("voltage : " + voltage);
        log("temperature : " + temperature);
        if (((voltage == 0) && (temperature == 0))
                || ((voltage == 10000) && (temperature == 0))) {
            //这是通过电池的伏数和温度来判断是真机还是模拟器
            log("检测结果 : true");
            log("\n");
            return true;
        } else {
            log("检测结果 : false");
            log("\n");
            return false;
        }
    }

    /**
     * 拨打电话
     *
     * @return
     */
    public boolean checkTelephoneStatus(Context context) {
        log("拨打电话检测");
        String url = "tel:" + "15512340001";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canResolveIntent = intent.resolveActivity(context.getPackageManager()) != null;
        log("canResolveIntent : " + canResolveIntent);

        boolean result = Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.SERIAL.equalsIgnoreCase("unknown")
                || Build.SERIAL.equalsIgnoreCase("android")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .getNetworkOperatorName().toLowerCase().equals("android")
                || !canResolveIntent;
//        return result;

        log("result : " + result);
        log("检测结果 : " + !canResolveIntent);
        log("\n");
        return !canResolveIntent;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     *
     * @return true 为模拟器
     */
    public Boolean notHasLightSensorManager(Context context) {
        log("判断是否存在光传感器来判断是否为模拟器");
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        log("sensor8 : " + sensor8);
        if (null == sensor8) {
            log("检测结果 : true");
            log("\n");
            return true;
        } else {
            log("检测结果 : false");
            log("\n");
            return false;
        }
    }

    /*
     *判断蓝牙是否有效来判断是否为模拟器
     *返回:true 为模拟器
     */
    public boolean notHasBlueTooth() {
        log("判断蓝牙是否有效来判断是否为模拟器");
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            log("ba : " + ba);
            log("检测结果 : true");
            log("\n");
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            log("BlueToothName : " + name);
            if (TextUtils.isEmpty(name)) {
                log("检测结果 : true");
                log("\n");
                return true;
            } else {
                log("检测结果 : false");
                log("\n");
                return false;
            }
        }
    }

    public void log(String msg) {
        Log.v("AntiEmulator", msg);
    }


}
