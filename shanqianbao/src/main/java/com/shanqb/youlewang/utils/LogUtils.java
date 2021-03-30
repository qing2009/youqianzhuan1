package com.shanqb.youlewang.utils;

import android.util.Log;

/**
 * 信息打印工具，通过全局控制来完全关闭或者打开信息打印
 *
 * @author liu_kf
 */
public class LogUtils {
    public static void debug(Object obj) {
        // 全局信息打印标志位关闭，则不输入任何信息
        if (!Global.DEBUG) {
            return;
        }
        if (obj == null) {
            Log.d(Global.DEBUG_TAG, "null object");
            return;
        }

        Log.d(Global.DEBUG_TAG, obj.toString());
    }
}
