package com.shanqb.mayi.inter;

import com.android.volley.VolleyError;

public interface MyQueueResponse {
    void onResponse(String actonString, String response);
    void onErrorResponse(String actonString, VolleyError error);
}
