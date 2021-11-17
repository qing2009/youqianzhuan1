package com.shanqb.lwan.aibianxian;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 权限组列表：
 Android6.0只用申请权限组中一个权限及获得全部权限
 Android8.0需要全部申请权限组权限，但是只会申请第一个权限时提示，后面不会提示

 // 读写日历。
 Manifest.permission.READ_CALENDAR,
 Manifest.permission.WRITE_CALENDAR
 // 相机。
 Manifest.permission.CAMERA
 // 读写联系人。
 Manifest.permission.READ_CONTACTS,
 Manifest.permission.WRITE_CONTACTS,
 Manifest.permission.GET_ACCOUNTS
 // 读位置信息。
 Manifest.permission.ACCESS_FINE_LOCATION,
 Manifest.permission.ACCESS_COARSE_LOCATION
 // 使用麦克风。
 Manifest.permission.RECORD_AUDIO
 // 读电话状态、打电话、读写电话记录。
 Manifest.permission.READ_PHONE_STATE,
 Manifest.permission.CALL_PHONE,
 Manifest.permission.READ_CALL_LOG,
 Manifest.permission.WRITE_CALL_LOG,
 Manifest.permission.ADD_VOICEMAIL,
 Manifest.permission.USE_SIP,
 Manifest.permission.PROCESS_OUTGOING_CALLS
 // 传感器。
 Manifest.permission.BODY_SENSORS
 // 读写短信、收发短信。
 Manifest.permission.SEND_SMS,
 Manifest.permission.RECEIVE_SMS,
 Manifest.permission.READ_SMS,
 Manifest.permission.RECEIVE_WAP_PUSH,
 Manifest.permission.RECEIVE_MMS,
 Manifest.permission.READ_CELL_BROADCASTS
 // 读写存储卡。
 Manifest.permission.READ_EXTERNAL_STORAGE,
 Manifest.permission.WRITE_EXTERNAL_STORAGE
 */
public class PermissionUtil {

    private static int mRequestCode = -1;

    private static OnPermissionListener mOnPermissionListener;

    public interface OnPermissionListener {

        void onPermissionGranted();

        void onPermissionDenied();
    }

    public static void requestPermissions(Activity aty, int requestCode
            , String[] permissions, OnPermissionListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mOnPermissionListener = listener;
            List<String> deniedPermissions = getDeniedPermissions(aty, permissions);
            if (deniedPermissions.size() > 0) {
                mRequestCode = requestCode;
                aty.requestPermissions(deniedPermissions
                        .toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                if (mOnPermissionListener != null)
                    mOnPermissionListener.onPermissionGranted();
            }
        }else{
            if (listener!=null){
                listener.onPermissionGranted();
            }
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    private static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (mOnPermissionListener != null) {
                if (verifyPermissions(grantResults)) {
                    mOnPermissionListener.onPermissionGranted();
                } else {
                    mOnPermissionListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 验证所有权限是否都已经授权
     */
    private static boolean verifyPermissions(int[] grantResults) {
        if (grantResults != null && grantResults.length>0){
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    /**
     * 显示提示对话框
     */
    public static void showTipsDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage("我们需要获取手机信息权限，请单击【确定】按钮前往设置进行授权。")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                })
                .setPositiveButton("取消", null).show();
    }
    /**
     * 启动当前应用设置页面
     */
    private static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}