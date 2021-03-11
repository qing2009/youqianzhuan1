package com.shanqb.weizhuanzushou.inter;

import com.android.volley.VolleyError;

public interface MyQueueResponse {
    void onResponse(String response);
    void onErrorResponse(VolleyError error);
}
