package com.shanqb.aiyiyou.inter;

import com.android.volley.VolleyError;

public interface MyQueueResponse {
    void onResponse(String response);
    void onErrorResponse(VolleyError error);
}
