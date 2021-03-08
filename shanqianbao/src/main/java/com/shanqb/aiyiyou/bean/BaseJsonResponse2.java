package com.shanqb.aiyiyou.bean;

public class BaseJsonResponse2 {


    public static final String STATE_SUCCESS = "success";

    /**
     *     "state": "success",
     *     "message": "查询成功"
     */
    public String state;
    public String message;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
