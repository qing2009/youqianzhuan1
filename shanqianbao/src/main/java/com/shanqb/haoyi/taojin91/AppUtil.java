package com.shanqb.haoyi.taojin91;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;

public class AppUtil {
    public static void openApp(Activity aty, String packageName){
        try {
            if(aty != null && !TextUtils.isEmpty(packageName)){
                PackageManager pm=aty.getPackageManager();
                Intent intent=pm.getLaunchIntentForPackage(packageName);
                aty.startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean appIsInstalled(Context con,String packageName){
        if (con == null || TextUtils.isEmpty(packageName)){
            return false;
        }
        try {
            PackageInfo info=con.getPackageManager().getPackageInfo(packageName, 0);
            if(info!=null){
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace();
        }
        return false;
    }
    /**
     * 判断apk文件是否完整
     * @param apkFilePath apk文件路径
     * @return true：正常，false：损坏
     */
    public static boolean apkIsValid(Context con,String apkFilePath){
        if(!TextUtils.isEmpty(apkFilePath)){
            File file=new File(apkFilePath);
            if(file.exists()&&file.isFile()){
                PackageInfo info=con.getPackageManager().getPackageArchiveInfo(apkFilePath,PackageManager.GET_ACTIVITIES);
                return info != null;
            }
        }
        return false;
    }
    /**
     * 安装app
     * <p>android 8.0 api>=26 需要声明权限 {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>}
     * <p>android 7.0 api>=24 安装app需要传入清单文件中的 {@code <provider>}的authorities属性
     * @param apkFilePath apk文件路径
     */
    public static void installApp(Context con,String apkFilePath){
        if(!TextUtils.isEmpty(apkFilePath)){
            installApp(con,new File(apkFilePath));
        }
    }

    /**
     * 安装app
     * <p>android 8.0 api>=26 需要声明权限 {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>}
     * <p>android 7.0 api>=24 安装app需要传入清单文件中的 {@code <provider>}的authorities属性
     * @param apkFile apk文件
     */
    public static void installApp(Context con,File apkFile){
        if(isFileExist(apkFile)){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data;
            String type = "application/vnd.android.package-archive";
            if(Build.VERSION.SDK_INT>=24){
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                data = TJH5FileProvider.getUriForFile(con, con.getPackageName()+".tjh5.provider", apkFile);
            }else{
                data = Uri.fromFile(apkFile);
            }
            intent.setDataAndType(data, type);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            con.startActivity(intent);
        }
    }
    private static boolean isFileExist(File file){
        return file!=null&&file.exists();
    }
}
