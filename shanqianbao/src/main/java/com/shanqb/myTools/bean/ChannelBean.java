package com.shanqb.myTools.bean;

public class ChannelBean {

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

    private int id;
    private String businessCode;
    private String channelCode;//渠道
    private String channelName;
    private String channelUser;
    private String channelKey;
    private String channelUrl;
    private String imageUrl;
    private String createTime;
    private String updateTime;
    private String state;//如果为0就是不能点平台锁住了,为1才能点进去
    private String way;//91淘金的打开方式 0:sdk;1:h5
    private String tishi;//如果为0的提示文字

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Object getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getChannelUser() {
        return channelUser;
    }

    public void setChannelUser(String channelUser) {
        this.channelUser = channelUser;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTishi() {
        return tishi;
    }

    public void setTishi(String tishi) {
        this.tishi = tishi;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
}