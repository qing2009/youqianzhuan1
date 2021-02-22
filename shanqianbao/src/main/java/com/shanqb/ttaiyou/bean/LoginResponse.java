package com.shanqb.ttaiyou.bean;

public class LoginResponse extends BaseJsonResponse {

    /**
     * data : {"id":569,"loginCode":"a","loginPwd":"08b96cdc7dc85dc9e3511a991b6716f4","merCode":"20201130105143309","appId":"APP","description":null,"merName":"抚姚疤","merPhoto":null,"merSex":"1","merPhone":null,"realName":null,"idCard":null,"zfb":null,"zfbName":null,"allAmt":0,"txAmt":0,"state":0,"createTime":"2020-11-30 02:51:43","updateTime":1606704703000,"lastLook":null,"imei":"20201130105143309","mobileModel":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 569
         * loginCode : a
         * loginPwd : 08b96cdc7dc85dc9e3511a991b6716f4
         * merCode : 20201130105143309
         * appId : APP
         * description : null
         * merName : 抚姚疤
         * merPhoto : null
         * merSex : 1
         * merPhone : null
         * realName : null
         * idCard : null
         * zfb : null
         * zfbName : null
         * allAmt : 0
         * txAmt : 0
         * state : 0
         * createTime : 2020-11-30 02:51:43
         * updateTime : 1606704703000
         * lastLook : null
         * imei : 20201130105143309
         * mobileModel : null
         */

        private int id;
        private String loginCode;
        private String loginPwd;
        private String merCode;
        private String appId;
        private String description;
        private String merName;
        private String merPhoto;
        private String merSex;
        private String merPhone;
        private String realName;
        private Object idCard;
        private Object zfb;
        private Object zfbName;
        private double allAmt;
        private double txAmt;
        private int state;
        private String createTime;
        private long updateTime;
        private Object lastLook;
        private String imei;
        private Object mobileModel;
        private String shareCode;

        public String getShareCode() {
            return shareCode;
        }

        public void setShareCode(String shareCode) {
            this.shareCode = shareCode;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoginCode() {
            return loginCode;
        }

        public void setLoginCode(String loginCode) {
            this.loginCode = loginCode;
        }

        public String getLoginPwd() {
            return loginPwd;
        }

        public void setLoginPwd(String loginPwd) {
            this.loginPwd = loginPwd;
        }

        public String getMerCode() {
            return merCode;
        }

        public void setMerCode(String merCode) {
            this.merCode = merCode;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public String getMerPhoto() {
            return merPhoto;
        }

        public void setMerPhoto(String merPhoto) {
            this.merPhoto = merPhoto;
        }

        public String getMerSex() {
            return merSex;
        }

        public void setMerSex(String merSex) {
            this.merSex = merSex;
        }

        public String getMerPhone() {
            return merPhone;
        }

        public void setMerPhone(String merPhone) {
            this.merPhone = merPhone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public Object getIdCard() {
            return idCard;
        }

        public void setIdCard(Object idCard) {
            this.idCard = idCard;
        }

        public Object getZfb() {
            return zfb;
        }

        public void setZfb(Object zfb) {
            this.zfb = zfb;
        }

        public Object getZfbName() {
            return zfbName;
        }

        public void setZfbName(Object zfbName) {
            this.zfbName = zfbName;
        }

        public double getAllAmt() {
            return allAmt;
        }

        public void setAllAmt(int allAmt) {
            this.allAmt = allAmt;
        }

        public double getTxAmt() {
            return txAmt;
        }

        public void setTxAmt(int txAmt) {
            this.txAmt = txAmt;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getLastLook() {
            return lastLook;
        }

        public void setLastLook(Object lastLook) {
            this.lastLook = lastLook;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public Object getMobileModel() {
            return mobileModel;
        }

        public void setMobileModel(Object mobileModel) {
            this.mobileModel = mobileModel;
        }
    }
}
