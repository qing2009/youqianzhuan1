package com.shanqb.muzhiyule.utils.sdk;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fc.tjcpl.sdk.TJSDK;
import com.shanqb.muzhiyule.utils.Utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 91淘金CPL 工具类
 */
public class Taojing91Utils {

    public static final String appId  = "2018";
    public static final String appkey = "511c8436a7095df4c25969f8bbd54a51";

    public static void startSDK(Activity context, String userId, String channelUser, String channelKey) {
        //初始化sdk,TJSDK.init(appId,appSecret,userId)
        //appId，appKey,创建媒体后获取
        //userId：媒体app中的用户id,媒体采用自己的提现系统时，必传，支持数字、字母混合，最长64位
//        TJSDK.init(appId,appkey,userId);
        TJSDK.init(channelUser, channelKey, userId);
        //展示sdk,进入列表界面
        TJSDK.show(context);
    }

    public static void startWeb(Context context, String userId,String oaid) {

        //TODO Demo使用AndroidID作为userID，开发者对接时请使用自己应用的用户ID。
        // 在媒体开发者接入时，请传入自己应用的用户账号，用户完成任务时，会在回调接口中返回
        String baseUrl = "https://app.91taojin.com.cn?";
        String url = UrlUtil.buildUrl(context.getApplicationContext(),userId,oaid,baseUrl,appId,appkey);
        Log.e("tag","url=="+url);

        Utils.goWeb(context, url);
    }

    public static String getURL(Context context, String userId,String oaid) {

        //TODO Demo使用AndroidID作为userID，开发者对接时请使用自己应用的用户ID。
        // 在媒体开发者接入时，请传入自己应用的用户账号，用户完成任务时，会在回调接口中返回
        String baseUrl = "https://app.91taojin.com.cn?";
        String url = UrlUtil.buildUrl(context.getApplicationContext(),userId,oaid,baseUrl,appId,appkey);
        Log.e("tag","url=="+url);

        return url;
    }

    /**
     * 把数组所有元素，按照“参数=参数值”的模式用“#”字符拼接成字符串
     */
    public static String getParamStr91(Map<String, String> paramsMap) {
        StringBuffer paramstr = new StringBuffer();
        for (String pkey : paramsMap.keySet()) {
            String pvalue = paramsMap.get(pkey);
            paramstr.append(pvalue + "#");
        }
        // 去掉最后一个&
        String result = paramstr.substring(0, paramstr.length() - 1);
        System.out.println("签名原串：" + result);
        return result;
    }


    /**
     * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
     */
    public static String getParamSrc(TreeMap<String, String> paramsMap) {
        StringBuffer paramstr = new StringBuffer();
        for (String pkey : paramsMap.keySet()) {
            String pvalue = paramsMap.get(pkey);
            if (null != pvalue && "" != pvalue) {// 空值不传递，不签名
                paramstr.append(pkey + "=" + pvalue + "&"); // 签名原串，不url编码
            }
        }
        // 去掉最后一个&
        String result = paramstr.substring(0, paramstr.length() - 1);
        System.out.println("签名原串：" + result);
        return result;
    }
}
