package diff.strazzere.anti;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import diff.strazzere.anti.debugger.FindDebugger;
import diff.strazzere.anti.emulator.FindEmulator;
import diff.strazzere.anti.monkey.FindMonkey;
import diff.strazzere.anti.taint.FindTaint;

public class MainActivity extends Activity {
    private TextView textView;
    private StringBuilder stringBuilder = new StringBuilder();
    private boolean checkResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview01);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());


        new Thread() {
            @Override
            public void run() {
                super.run();

                checkResult = isTaintTrackingDetected() ||
                        isMonkeyDetected() ||
                        isDebugged() ||
                        isQEmuEnvDetected() ||
                        hasWendu() ||
                        isEmulator() ||
                        notHasLightSensorManager() ||
                        notHasBlueTooth();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("模拟器检测："+checkResult+"\n\n"+stringBuilder.toString());
                    }
                });
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean isQEmuEnvDetected() {
        myStringAppend("Checking for QEmu env...");
        myStringAppend("hasKnownDeviceId : " + FindEmulator.hasKnownDeviceId(getApplicationContext()));
        myStringAppend("hasKnownPhoneNumber : " + FindEmulator.hasKnownPhoneNumber(getApplicationContext()));
        myStringAppend("isOperatorNameAndroid : " + FindEmulator.isOperatorNameAndroid(getApplicationContext()));
        myStringAppend("hasKnownImsi : " + FindEmulator.hasKnownImsi(getApplicationContext()));
        myStringAppend("hasEmulatorBuild : " + FindEmulator.hasEmulatorBuild(getApplicationContext()));
        myStringAppend("hasPipes : " + FindEmulator.hasPipes());
        myStringAppend("hasQEmuDriver : " + FindEmulator.hasQEmuDrivers());
        myStringAppend("hasQEmuFiles : " + FindEmulator.hasQEmuFiles());
        myStringAppend("hasGenyFiles : " + FindEmulator.hasGenyFiles());
        myStringAppend("hasEmulatorAdb :" + FindEmulator.hasEmulatorAdb());
        for (String abi : Build.SUPPORTED_ABIS) {
            if (abi.equalsIgnoreCase("armeabi-v7a")) {
                myStringAppend("hitsQemuBreakpoint : " + FindEmulator.checkQemuBreakpoint());
            }
        }
        if (FindEmulator.hasKnownDeviceId(getApplicationContext())
                || FindEmulator.hasKnownImsi(getApplicationContext())
                || FindEmulator.hasEmulatorBuild(getApplicationContext())
                || FindEmulator.hasKnownPhoneNumber(getApplicationContext()) || FindEmulator.hasPipes()
                || FindEmulator.hasQEmuDrivers() || FindEmulator.hasEmulatorAdb()
                || FindEmulator.hasQEmuFiles()
                || FindEmulator.hasGenyFiles()) {
            myStringAppend("QEmu environment detected.");
            myStringAppend("\n");
            return true;
        } else {
            myStringAppend("QEmu environment not detected.");
            myStringAppend("\n");
            return false;
        }
    }

    public boolean isTaintTrackingDetected() {
        myStringAppend("Checking for Taint tracking...");
        myStringAppend("hasAppAnalysisPackage : " + FindTaint.hasAppAnalysisPackage(getApplicationContext()));
        myStringAppend("hasTaintClass : " + FindTaint.hasTaintClass());
        myStringAppend("hasTaintMemberVariables : " + FindTaint.hasTaintMemberVariables());
        if (FindTaint.hasAppAnalysisPackage(getApplicationContext()) || FindTaint.hasTaintClass()
                || FindTaint.hasTaintMemberVariables()) {
            myStringAppend("Taint tracking was detected.");
            myStringAppend("\n");
            return true;
        } else {
            myStringAppend("Taint tracking was not detected.");
            myStringAppend("\n");
            return false;
        }
    }

    public boolean isMonkeyDetected() {
        myStringAppend("Checking for Monkey user...");
        myStringAppend("isUserAMonkey : " + FindMonkey.isUserAMonkey());

        if (FindMonkey.isUserAMonkey()) {
            myStringAppend("Monkey user was detected.");
            myStringAppend("\n");
            return true;
        } else {
            myStringAppend("Monkey user was not detected.");
            myStringAppend("\n");
            return false;
        }
    }

    public boolean isDebugged() {
        myStringAppend("Checking for debuggers...");

        boolean tracer = false;
        try {
            tracer = FindDebugger.hasTracerPid();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (FindDebugger.isBeingDebugged() || tracer) {
            myStringAppend("Debugger was detected");
            myStringAppend("\n");
            return true;
        } else {
            myStringAppend("No debugger was detected.");
            myStringAppend("\n");
            return false;
        }
    }


    /**
     * 通过电池的伏数和温度来判断是真机还是模拟器
     * @return
     */
    public boolean hasWendu() {
        myStringAppend("通过电池的伏数和温度来判断是真机还是模拟器.");
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = registerReceiver(null, intentFilter);
        int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);
        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
        myStringAppend("voltage : "+voltage);
        myStringAppend("temperature : "+temperature);
        if (((voltage == 0) && (temperature == 0))
                || ((voltage == 10000) && (temperature == 0))) {
            //这是通过电池的伏数和温度来判断是真机还是模拟器
            myStringAppend("检测结果 : true");
            myStringAppend("\n");
            return true;
        }else{
            myStringAppend("检测结果 : false");
            myStringAppend("\n");
            return false;
        }
    }

    /**
     * 拨打电话
     * @return
     */
    public boolean isEmulator() {
        myStringAppend("拨打电话检测");
        String url = "tel:" + "15512340001";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canResolveIntent = intent.resolveActivity(getPackageManager()) != null;
        myStringAppend("canResolveIntent : "+canResolveIntent);

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
                || ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE))
                .getNetworkOperatorName().toLowerCase().equals("android")
                || !canResolveIntent;
//        return result;

        myStringAppend("result : "+result);
        myStringAppend("检测结果 : "+!canResolveIntent);
        myStringAppend("\n");
        return !canResolveIntent;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     * @return true 为模拟器
     */
    public Boolean notHasLightSensorManager() {
        myStringAppend("判断是否存在光传感器来判断是否为模拟器");
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        myStringAppend("sensor8 : "+sensor8);
        if (null == sensor8) {
            myStringAppend("检测结果 : true");
            myStringAppend("\n");
            return true;
        } else {
            myStringAppend("检测结果 : false");
            myStringAppend("\n");
            return false;
        }
    }

    /*
     *判断蓝牙是否有效来判断是否为模拟器
     *返回:true 为模拟器
     */
    public boolean notHasBlueTooth() {
        myStringAppend("判断蓝牙是否有效来判断是否为模拟器");
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            myStringAppend("ba : "+ba);
            myStringAppend("检测结果 : true");
            myStringAppend("\n");
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            myStringAppend("BlueToothName : "+name);
            if (TextUtils.isEmpty(name)) {
                myStringAppend("检测结果 : true");
                myStringAppend("\n");
                return true;
            } else {
                myStringAppend("检测结果 : false");
                myStringAppend("\n");
                return false;
            }
        }
    }

    public void log(String msg) {
        Log.v("AntiEmulator", msg);
    }

    public void myStringAppend(String msg) {
        stringBuilder.append(msg + "\n");
    }
}
