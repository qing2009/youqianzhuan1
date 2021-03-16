package com.shanqb.shouzhuanbaohe.bean;

public class BaseJsonResponse {


    /**
     * success : false
     * message : 用户已不存在
     */

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
