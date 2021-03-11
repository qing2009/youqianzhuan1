package com.shanqb.weizhuanzushou;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.text.TextUtils;

import com.component.dly.xzzq_ywsdk.YwSDK;
import com.pceggs.workwall.util.PceggsWallUtils;
import com.shanqb.weizhuanzushou.utils.MittUtils;
import com.shanqb.weizhuanzushou.utils.SharedPreConstants;
import com.shanqb.weizhuanzushou.utils.SharedPreferencesUtil;
import com.xuexiang.xui.XUI;

import java.util.ArrayList;

public class BaseApplication extends Application {

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
        initOAID();



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
    }

    private void initOAID() {
        if (Build.VERSION.SDK_INT > 28) {
            try {
                //获取oaid
                new MittUtils().getDeviceIds(this, new MittUtils.AppIdsUpdater() {
                    @Override
                    public void OnIdsAvailed(boolean isSupport, String oaid) {
                        if (!TextUtils.isEmpty(oaid)) {

                            SharedPreferencesUtil.setStringValue(BaseApplication.getInstance(), SharedPreConstants.OAID, oaid);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
