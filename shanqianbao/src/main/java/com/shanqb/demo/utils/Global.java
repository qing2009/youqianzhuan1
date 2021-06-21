package com.shanqb.demo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Global
{
	/** 日志打印TAG */
	public static final String DEBUG_TAG = "SQB";
	public static final String BUSINESS_CODE = "20210517173436744";//商户号

	public static final String CHANNEL_CODE_AIBIANXIAN = "20210221021824005";//爱变现
	public static final String CHANNEL_CODE_JUXIANGYOU = "20210221021842938";//聚享游
	public static final String CHANNEL_CODE_TAOJING91 = "20210221021834000";//91淘金
	public static final String CHANNEL_CODE_XGAME = "20210616110840834";//X游戏
	public static final String CHANNEL_CODE_XIANWANG = "20210221021853088";//享玩
	public static final String CHANNEL_CODE_YUWANG = "20210302150858583";//鱼丸盒子
	public static final String CHANNEL_CODE_DUOYOU = "20210302221618125";//多游
	public static final String CHANNEL_CODE_XIQU = "20210224141221832";//嘻趣
	public static final String CHANNEL_CODE_WOWANG = "20210306151335295";//我玩
	public static final String CHANNEL_CODE_XIANWANG2 = "20210224141124709";//闲玩

	/** 日志开启开关 */
	public static final boolean DEBUG = true;

	/** 页面启动地址 */
	public static final String BASE_URL = "http://192.168.1.5:8084/XWGame/front/index";

	/** 版本更新地址 */
//	public static final String UPDATE_URL = "http://119.254.111.198/versions/1";
	/** 版本更新地址 */
	public static final String UPDATE_URL = "http://www.meifenfenqi.com/versions/1";


	public static final String AUTHORITIES = "com.shanqb.wallet.fileprovider";//provider的authorities属性


//	public static final String BASE_INTER_URL = "http://47.106.39.224/front/myInfo/";//
//	public static final String BASE_INTER_URL = "http://wmn.douziok.com/diaobo/front/myInfo/";//
//	public static final String BASE_INTER_URL = "http://1.15.38.129/front/myInfo/";//tt爱游
	public static final String BASE_INTER_URL = "http://8.133.178.205/admin/front/myInfo/";//

	/**
	 * 视频接口
	 */
	public static final String BASE_VIDEO_DOMAIN = "http://wmn.douziok.com/";//视频域名
	public static final String BASE_VIDEO_URL = BASE_VIDEO_DOMAIN+"diaobo/front/video/videoInfo/";//
	public static final String GET_VIDEO_TYPE = BASE_VIDEO_URL+"getVideoTypeJson";//获取视频类型
	public static final String GET_VIDEO_LIST = BASE_VIDEO_URL+"getVideoList?";//获取视频类型




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
