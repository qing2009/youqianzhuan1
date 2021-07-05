package com.shanqb.demo.utils.sdk;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import com.shanqb.demo.utils.DeviceUtils;
import com.shanqb.demo.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class YuWanUrlUtil {
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
        paramBuilder.appendQueryParameter("appId", appId);
        map.put("appId", appId);
        paramBuilder.appendQueryParameter("platform", "1");
        map.put("platform", "1");
        if(!TextUtils.isEmpty(userId)){
            paramBuilder.appendQueryParameter("mediaUserId",userId);
            map.put("mediaUserId",userId);
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            //android Q 设备
            paramBuilder.appendQueryParameter("oaid",oaid);
            map.put("oaid",oaid);
        }else{
            String imei= DeviceUtils.getIMEI(con,0);
            if(!TextUtils.isEmpty(imei)){
                paramBuilder.appendQueryParameter("deviceIdentity",imei);
                map.put("deviceIdentity",imei);
            }
        }
        StringBuilder data=new StringBuilder();
        for (Map.Entry localEntry : map.entrySet()) {
            String str = (String)localEntry.getValue();
            data.append(str).append("");
        }
        data.append(appKey);
        LogUtils.debug("params:"+data.toString());
        String sign = encryptMD5(data.toString());
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
