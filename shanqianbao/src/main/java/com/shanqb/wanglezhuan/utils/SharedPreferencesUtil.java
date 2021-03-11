package com.shanqb.wanglezhuan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


public class SharedPreferencesUtil {
	
	public static final String USERINFO_SHARED  = "userInfo";//保存配音相关信息的配置文件


	public static SharedPreferences getInterface(Context context) {
		SharedPreferences mSharedPre = null;
		if (context!=null) {
			mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
		}
		return mSharedPre;
	}


	/**
	 * 将字符串类型的数据保存到配置文件
	 *
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setStringValue(Context context, String key, String value) {
		if (context!=null && !TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
			SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
			mSharedPre.edit().putString(key, value).commit();
		}
	}

	/**
	 * 从配置文件获取字符串值
	 *
	 * @param context
	 * @param key
	 * @param defValue 如果配置文件没有保存值的情况下返回的默认值
	 * @return
	 */
	public static String getStringValue(Context context,String key, String defValue) {
		String valueString = defValue;
		try {
			if (context!=null && !TextUtils.isEmpty(key)) {
				SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
				valueString = mSharedPre.getString(key, defValue);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return valueString;
	}

	public static void setBooleanValue(Context context,String key, boolean value) {
		if (context!=null && !TextUtils.isEmpty(key)) {
			SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
			mSharedPre.edit().putBoolean(key, value).commit();
		}
	}

	public static boolean getBooleanValue(Context context,String key, boolean defValue) {
		boolean valueBoo = defValue;
		try {
			if (context != null && !TextUtils.isEmpty(key)) {
				SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
				valueBoo = mSharedPre.getBoolean(key, defValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueBoo;
	}

	public static void setIntValue(Context context,String key, int value) {
		if (context!=null && !TextUtils.isEmpty(key)) {
			SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
			mSharedPre.edit().putInt(key, value).commit();
		}
	}

	public static int getIntValue(Context context,String key, int defValue) {
		int valueInt = defValue;
		try {
			if (context != null && !TextUtils.isEmpty(key)) {
				SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
				return mSharedPre.getInt(key, defValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueInt;
	}

	public static void remove(Context context,String key) {
		if (context!=null && !TextUtils.isEmpty(key)) {
			SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
			mSharedPre.edit().remove(key).commit();
		}
	}

	public static void clear(Context context) {
		if (context!=null) {
			SharedPreferences mSharedPre = context.getSharedPreferences(USERINFO_SHARED, Context.MODE_PRIVATE);
			mSharedPre.edit().clear().commit();
		}
	}

}
