package com.shanqb.myTool.bean;

public class AppsBean {

    /**
     * id : 2
     * channelCode : 20210221021834000
     * channelName : null
     * businessCode : 20210223153134088
     * channelUser : 1
     * channelKey : 1
     * channelUrl : 1
     * imageUrl:
     * createTime : 2021-02-23 09:03:51
     * updateTime : 2021-02-23 09:03:51
     */

    private Integer id;

    private String appName; 			//app名称

    private String appUrl; 			//app下载地址

    private String appDesc; 			//app描述

    private String appIcon; 			//applogo

    private int state;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getAppUrl() {
        return appUrl;
    }
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
    public String getAppDesc() {
        return appDesc;
    }
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }
    public String getAppIcon() {
        return appIcon;
    }
    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
}
