package com.shanqb.qianren.utils.sdk;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import com.shanqb.qianren.utils.DeviceUtils;
import com.shanqb.qianren.utils.LogUtils;
import com.toomee.mengplus.manager.TooMeeManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 *聚享游sdk 工具类
 */
public class JuxiangyouUtils {

//    public static final String MID = "1587";//聚享游提供
//    public static final String TOKEN = "c1c9db60ef940a0b8eee447aedeaaf44";//聚享游提供

    public static void startSDK(Context context,String userId,String channelUser,String channelKey,String oaid){
        //SDK接入模式
//        String userId = "654321";
        //mid 和 token联系我方提供
        //注意申请动态权限 WRITE_EXTERNAL_STORAGE和READ_PHONE_STATE
//        TooMeeManager.init(context, MID, userId, TOKEN, null);

        String comstring = "oaid,"+oaid;

        TooMeeManager.init(context, channelUser, userId, channelKey, comstring,null);
        TooMeeManager.start(context);
    }

    private static TreeMap<String,String> map;
    /**
     * 构建请求url
     * @param con
     * @param userId 用户id 如果媒体需要自己给用户发放奖励，则必传该参数
     * @param oaid 通过移动安全联盟sdk获取，网址：http://www.msa-alliance.cn/col.jsp?id=120
     */
    public static String buildUrl(Context con,String userId,String oaid,String url,String appId,String appKey){
        map=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Uri localUri = Uri.parse(url);
        Uri.Builder paramBuilder = localUri.buildUpon();
        paramBuilder.appendQueryParameter("mid", appId);
        map.put("mid", appId);

        if(!TextUtils.isEmpty(userId)){
            paramBuilder.appendQueryParameter("resource_id",userId);
            map.put("resource_id",userId);
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            //android Q 设备
            if(!TextUtils.isEmpty(oaid)){
                paramBuilder.appendQueryParameter("device",oaid);
            }
            paramBuilder.appendQueryParameter("sysver","10");
        }else{
            String device_id= DeviceUtils.getDeviceId(con);
            if(!TextUtils.isEmpty(device_id)){
                paramBuilder.appendQueryParameter("device",device_id);
            }
            paramBuilder.appendQueryParameter("sysver","9");
        }
        StringBuilder data=new StringBuilder();
        for (Map.Entry localEntry : map.entrySet()) {
            String str = (String)localEntry.getValue();
            data.append(str);
        }
        data.append(appKey);
        LogUtils.debug("params:"+data.toString());

        byte[] bytes= Base64.encode(data.toString().getBytes(),Base64.NO_WRAP);
        String str="";
        try {
            str=new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LogUtils.debug("Base64:"+str);
        String sign = encryptMD5(str);
        LogUtils.debug("sign:"+sign);
        paramBuilder.appendQueryParameter("sign",sign);
        return  paramBuilder.toString();
    }

    /**
     * MD5加密
     * @param data 待加密数据
     * @return 加密后的数据
     */
    private static String encryptMD5(String data){
        byte[] hash = null;
        try {
            byte[] bytes=data.getBytes("UTF-8");
            hash = MessageDigest.getInstance("MD5").digest(bytes);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(hash!=null){
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        }
        return null;
    }
}
