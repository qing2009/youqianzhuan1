package com.shanqb.douquzhuan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Global
{
	/** 日志打印TAG */
	public static final String DEBUG_TAG = "SQB";

	/** 日志开启开关 */
	public static final boolean DEBUG = false;

	/** 页面启动地址 */
	public static final String BASE_URL = "http://192.168.1.5:8084/XWGame/front/index";

	/** 版本更新地址 */
//	public static final String UPDATE_URL = "http://119.254.111.198/versions/1";
	/** 版本更新地址 */
	public static final String UPDATE_URL = "http://www.meifenfenqi.com/versions/1";


	public static final String AUTHORITIES = "com.shanqb.wallet.fileprovider";//provider的authorities属性


//	public static final String BASE_INTER_URL = "http://47.106.39.224/front/myInfo/";//
//	public static final String BASE_INTER_URL = "http://wmn.douziok.com/diaobo/front/myInfo/";//
	public static final String BASE_INTER_URL = "http://8.133.178.205/front/myInfo/";//


	/**
	 * 获取应用程序的当前版本号,即AndroidManifest.xml中的versionCode
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			return 0;
		}
	}

}
