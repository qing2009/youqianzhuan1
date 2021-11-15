package com.shanqb.myTool.utils.sdk;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.shanqb.myTool.utils.DeviceUtils;
import com.shanqb.myTool.utils.LogUtils;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 91淘金CPL 工具类
 */
public class DuoyouUtils {
    private static TreeMap<String,String> map;
    /**
     * 构建请求url
     * @param con
     * @param userId 用户id 如果媒体需要自己给用户发放奖励，则必传该参数
     * @param oaid 通过移动安全联盟sdk获取，网址：http://www.msa-alliance.cn/col.jsp?id=120
     */
    public static String buildUrl(Context con,String userId,String oaid,String url,String appId,String appKey) throws Exception {
        map=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Uri localUri = Uri.parse(url);
        Uri.Builder paramBuilder = localUri.buildUpon();
        paramBuilder.appendQueryParameter("media_id", URLEncoder.encode(appId,"utf-8"));
        map.put("media_id", URLEncoder.encode(appId,"utf-8"));

        if(!TextUtils.isEmpty(userId)){
            paramBuilder.appendQueryParameter("user_id",userId);
            map.put("user_id",userId);
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            //android Q 设备
            if(!TextUtils.isEmpty(oaid)){
                JSONObject jObj = new JSONObject();
                jObj.put("7", oaid);
                String keyWord = jObj.toJSONString();
                paramBuilder.appendQueryParameter("device_ids",keyWord);
                map.put("device_ids",keyWord);
            }
        }else{
            String device_id= DeviceUtils.getDeviceId(con);
            if(!TextUtils.isEmpty(device_id)){
                JSONObject jObj = new JSONObject();
                jObj.put("1", device_id);
                String keyWord = jObj.toJSONString();
                paramBuilder.appendQueryParameter("device_ids",keyWord);
                map.put("device_ids",keyWord);
            }
        }
        paramBuilder.appendQueryParameter("device_type", "2");
        map.put("device_type", "2");

        String sign = generateSignature(map,appKey);
        LogUtils.debug("sign:"+sign);
        paramBuilder.appendQueryParameter("sign",sign);
        return  paramBuilder.toString();
    }
    /**
     *
     * data : 参数map key：appSecret
     *
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            String valueEncode = URLEncoder.encode(data.get(k), "utf-8");
            sb.append(k).append("=").append(valueEncode).append("&");
        }
        sb.append("key=").append(key);
        return encryptMD5(sb.toString()).toLowerCase();
    }
    /**
     * MD5加密
     * @param data 待加密数据
     * @return 加密后的数据
     */
    private static String encryptMD5(String data){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
