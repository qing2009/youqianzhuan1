package com.shanqb.shouzhuanbaohe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 缓存数据管理器
 * 
 * @author liu_kf
 * 
 */
public class DataKeeper
{
	private static final String PREFERENCES_NAME = "_version";

	public static int getVersionCode(Context context) {
		SharedPreferences pref = context
				.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		return pref.getInt("versionCode", 0);
	}

	public static void saveVersionCode(Context context, int versionCode) {
		SharedPreferences pref = context
				.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();

		editor.putInt("versionCode", versionCode);

		editor.commit();
	}

	public static void clear(Context context) {
		SharedPreferences pref = context
				.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();

		editor.clear();

		editor.commit();
	}

}
