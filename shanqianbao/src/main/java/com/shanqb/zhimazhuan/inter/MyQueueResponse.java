package com.shanqb.zhimazhuan.inter;

import com.android.volley.VolleyError;

public interface MyQueueResponse {
    void onResponse(String response);
    void onErrorResponse(VolleyError error);
}
