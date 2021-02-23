package com.shanqb.weishouzhuan.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class StringUtils {

    /**
     * 改变字符串中字段的颜色
     * @param content	字符串
     * @param key	需要改变颜色的关键字
     * @param color	颜色
     * @return
     */
    public static SpannableStringBuilder setColor4Key(String content, String key, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        if (!android.text.TextUtils.isEmpty(content) && !android.text.TextUtils.isEmpty(key)) {

            int keyStartIndex = content.indexOf(key);
            int keyEndIndex = keyStartIndex+key.length();
            if (keyStartIndex == -1) {
                return builder.append(content);
            }

            builder.append(content);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
            builder.setSpan(redSpan, keyStartIndex, keyEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }
}
