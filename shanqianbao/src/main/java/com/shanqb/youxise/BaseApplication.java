package com.shanqb.youxise;

import android.app.Activity;
import android.app.Application;

import com.component.dly.xzzq_ywsdk.YwSDK;
import com.pceggs.workwall.util.PceggsWallUtils;
import com.shanqb.youxise.utils.AntiEmulatorUtils;
import com.shanqb.youxise.utils.DeviceUtils;
import com.shanqb.youxise.utils.SharedPreConstants;
import com.shanqb.youxise.utils.SharedPreferencesUtil;
import com.xuexiang.xui.XUI;

import java.util.ArrayList;

import jfq.wowan.com.myapplication.PlayMeUtil;

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static BaseApplication baseApplication = null;
    private ArrayList<Activity> activityArrayList = null;

    /**
     * 单例获取
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;


        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志


        //安全联盟
        DeviceUtils.initOaid(this);


        //享玩sdk初始化
        PceggsWallUtils.init(this);

        //聚享游sdk初始化
//        TooMeeUtils.init(this);

//        XiquUtils.init(this);


        //鱼丸盒子
        //java代码调用
        YwSDK.Companion.init(this,"","","",SharedPreferencesUtil.getStringValue(BaseApplication.getInstance(), SharedPreConstants.OAID, ""));
        YwSDK.Companion.setDebugMode();
//        YwSDK.Companion.refreshMediaUserId("mediaUserId");
//        YwSDK.Companion.refreshAppSecret("appSecret","appId");
//        YwSDK.Companion.refreshOaid("oaid");


        //我玩
        PlayMeUtil.init(this, getPackageName()+".fileprovider");

        emulatorCheck();
    }


    /**
     * 模拟器检测
     */
    private void emulatorCheck() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                boolean result = new AntiEmulatorUtils().isEmulator(getApplicationContext());
                SharedPreferencesUtil.setBooleanValue(getApplicationContext(),SharedPreConstants.ISEMULATOR,result);
            }
        }.start();
    }


    public void addActivity2List(Activity activity) {
        try {
            if (activityArrayList == null) {
                activityArrayList = new ArrayList<>();
            }

            activityArrayList.add(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearActivityList() {
        try {
            if (activityArrayList != null) {
                for (Activity activity : activityArrayList) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
