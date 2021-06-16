package com.shanqb.demo.utils.sdk;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;

import com.shanqb.demo.utils.DeviceUtils;
import com.shanqb.demo.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class XGameUrlUtil {
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
        paramBuilder.appendQueryParameter("mt_id", appId);
        map.put("mt_id", appId);

        if(!TextUtils.isEmpty(userId)){
            paramBuilder.appendQueryParameter("mt_user_id",userId);
            map.put("mt_user_id",userId);
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            //android Q 设备
            paramBuilder.appendQueryParameter("oaid",oaid);
            map.put("oaid",oaid);
        }else{
            String device_id= DeviceUtils.getDeviceId(con);
            if(!TextUtils.isEmpty(device_id)){
                paramBuilder.appendQueryParameter("device_id",device_id);
                map.put("device_id",device_id);
            }
            String imei= DeviceUtils.getIMEI(con,0);
            if(!TextUtils.isEmpty(imei)){
                paramBuilder.appendQueryParameter("imei1",imei);
                map.put("imei1",imei);
            }
            String imei2= DeviceUtils.getIMEI(con,1);
            if(!TextUtils.isEmpty(imei2)){
                paramBuilder.appendQueryParameter("imei2",imei2);
                map.put("imei2",imei2);
            }
//
//            String imsi= DeviceUtil.getIMSI(con);
//            if(!TextUtils.isEmpty(imsi)){
//                paramBuilder.appendQueryParameter("IMSI",imsi);
//                map.put("IMSI",imsi);
//            }
//            String serial= DeviceUtil.getSerialNumber();
//            if(!TextUtils.isEmpty(serial)){
//                paramBuilder.appendQueryParameter("SerialNumber",serial);
//                map.put("SerialNumber",serial);
//            }
        }
//        String androidId= DeviceUtil.getAndroidID(con);
//        if(!TextUtils.isEmpty(androidId)){
//            paramBuilder.appendQueryParameter("AndroidId",androidId);
//            map.put("AndroidId",androidId);
//        }
//        int[] wh= DeviceUtil.getScreenWH(con);
//        paramBuilder.appendQueryParameter("ScreenResolution",wh[0]+"x"+wh[1]);
//        map.put("ScreenResolution",wh[0]+"x"+wh[1]);
//
//        String size= DeviceUtil.getScreenSize(con);
//        if(!TextUtils.isEmpty(size)){
//            paramBuilder.appendQueryParameter("ScreenSize",size);
//            map.put("ScreenSize",size);
//        }

        String model= Build.MODEL;
        if(!TextUtils.isEmpty(model)){
            paramBuilder.appendQueryParameter("mobile_model",model);
            map.put("mobile_model",model);
        }

        String systemVersion= Build.VERSION.RELEASE;
        if(!TextUtils.isEmpty(systemVersion)){
            paramBuilder.appendQueryParameter("sys_ver",systemVersion);
            map.put("sys_ver",systemVersion);
        }
        String round_id= "";
        paramBuilder.appendQueryParameter("round_id",round_id);
        map.put("round_id",round_id);
        String timestamp= (new Date()).getTime()+"";
        if(!TextUtils.isEmpty(timestamp)){
            paramBuilder.appendQueryParameter("timestamp",timestamp);
            map.put("timestamp",systemVersion);
        }
        StringBuilder data=new StringBuilder();
        for (Map.Entry localEntry : map.entrySet()) {
            String str = (String)localEntry.getValue();
            data.append(str).append("#");
        }
        data.append(appKey);
        LogUtils.debug("params:"+data.toString());

        byte[] bytes=Base64.encode(data.toString().getBytes(),Base64.NO_WRAP);
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
