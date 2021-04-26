package com.shanqb.yibowangluo.bean;

public class RegisterResponse extends BaseJsonResponse {

    /**
     * data : {"id":570,"loginCode":"a2","loginPwd":"08b96cdc7dc85dc9e3511a991b6716f4","merCode":"20201130105241775","appId":"APP","description":null,"merName":"鼻潞站","merPhoto":null,"merSex":"1","merPhone":null,"realName":null,"idCard":null,"zfb":null,"zfbName":null,"allAmt":0,"txAmt":0,"state":0,"createTime":"2020-11-30 02:52:41","updateTime":1606704761775,"lastLook":null,"imei":"20201130105241775","mobileModel":null}
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
         * id : 570
         * loginCode : a2
         * loginPwd : 08b96cdc7dc85dc9e3511a991b6716f4
         * merCode : 20201130105241775
         * appId : APP
         * description : null
         * merName : 鼻潞站
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
         * createTime : 2020-11-30 02:52:41
         * updateTime : 1606704761775
         * lastLook : null
         * imei : 20201130105241775
         * mobileModel : null
         */

        private int id;
        private String loginCode;
        private String loginPwd;
        private String merCode;
        private String appId;
        private Object description;
        private String merName;
        private Object merPhoto;
        private String merSex;
        private Object merPhone;
        private Object realName;
        private Object idCard;
        private Object zfb;
        private Object zfbName;
        private int allAmt;
        private int txAmt;
        private int state;
        private String createTime;
        private long updateTime;
        private Object lastLook;
        private String imei;
        private Object mobileModel;

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

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public Object getMerPhoto() {
            return merPhoto;
        }

        public void setMerPhoto(Object merPhoto) {
            this.merPhoto = merPhoto;
        }

        public String getMerSex() {
            return merSex;
        }

        public void setMerSex(String merSex) {
            this.merSex = merSex;
        }

        public Object getMerPhone() {
            return merPhone;
        }

        public void setMerPhone(Object merPhone) {
            this.merPhone = merPhone;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
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

        public int getAllAmt() {
            return allAmt;
        }

        public void setAllAmt(int allAmt) {
            this.allAmt = allAmt;
        }

        public int getTxAmt() {
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
