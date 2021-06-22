package com.shanqb.shandianli.bean;

import java.util.List;

public class TryPlayListResponse extends BaseJsonResponse2 {


    /**
     * data : [{"id":685,"orderCode":"XW20201130160729989","outCode":"59382020113016072812981C77LG9","memberCode":"20201130105143309","qdName":"闲玩","userFee":0.4,"mtFee":0.8,"memberInfo":null,"noteJson":"{\"adicon\":\"0.4\",\"appsign\":\"20201128100635913\",\"dlevel\":\"1\",\"UserFee\":\"0.8\",\"itime\":\"2020/11/30 16:07:28\",\"deviceid\":\"869665743574584\",\"MtFee\":\"0.4\",\"adid\":\"12981\",\"adname\":\"天天怼三国\",\"atype\":\"2\",\"appid\":\"5938\",\"simid\":\"869665743574584\",\"merid\":\"2236311\",\"event\":\"天天怼三国战力达到100万\",\"pagename\":\"com.junhai.ttdsg.yy\"}","adName":"天天怼三国","taskName":"天天怼三国战力达到100万","appIcon":"https://xianwanimgs.oss-cn-hangzhou.aliyuncs.com/corp-document/202011/24113900038.png","createTime":1606723650000,"updateTime":null}]
     * count : 1
     * state : success
     */

    private int count;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 685
         * orderCode : XW20201130160729989
         * outCode : 59382020113016072812981C77LG9
         * memberCode : 20201130105143309
         * qdName : 闲玩
         * userFee : 0.4
         * mtFee : 0.8
         * memberInfo : null
         * noteJson : {"adicon":"0.4","appsign":"20201128100635913","dlevel":"1","UserFee":"0.8","itime":"2020/11/30 16:07:28","deviceid":"869665743574584","MtFee":"0.4","adid":"12981","adname":"天天怼三国","atype":"2","appid":"5938","simid":"869665743574584","merid":"2236311","event":"天天怼三国战力达到100万","pagename":"com.junhai.ttdsg.yy"}
         * adName : 天天怼三国
         * taskName : 天天怼三国战力达到100万
         * appIcon : https://xianwanimgs.oss-cn-hangzhou.aliyuncs.com/corp-document/202011/24113900038.png
         * createTime : 1606723650000
         * updateTime : null
         */

        private int id;
        private String orderCode;
        private String outCode;
        private String memberCode;
        private String qdName;
        private double userFee;
        private double mtFee;
        private Object memberInfo;
        private String noteJson;
        private String adName;
        private String taskName;
        private String appIcon;
        private long createTime;
        private Object updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOutCode() {
            return outCode;
        }

        public void setOutCode(String outCode) {
            this.outCode = outCode;
        }

        public String getMemberCode() {
            return memberCode;
        }

        public void setMemberCode(String memberCode) {
            this.memberCode = memberCode;
        }

        public String getQdName() {
            return qdName;
        }

        public void setQdName(String qdName) {
            this.qdName = qdName;
        }

        public double getUserFee() {
            return userFee;
        }

        public void setUserFee(double userFee) {
            this.userFee = userFee;
        }

        public double getMtFee() {
            return mtFee;
        }

        public void setMtFee(double mtFee) {
            this.mtFee = mtFee;
        }

        public Object getMemberInfo() {
            return memberInfo;
        }

        public void setMemberInfo(Object memberInfo) {
            this.memberInfo = memberInfo;
        }

        public String getNoteJson() {
            return noteJson;
        }

        public void setNoteJson(String noteJson) {
            this.noteJson = noteJson;
        }

        public String getAdName() {
            return adName;
        }

        public void setAdName(String adName) {
            this.adName = adName;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(String appIcon) {
            this.appIcon = appIcon;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}
