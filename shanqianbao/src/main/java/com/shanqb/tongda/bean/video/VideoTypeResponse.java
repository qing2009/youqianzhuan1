package com.shanqb.tongda.bean.video;

import com.shanqb.tongda.bean.BaseJsonResponse2;

import java.util.List;

public class VideoTypeResponse extends BaseJsonResponse2 {


    /**
     * data : [{"id":"20200209145534301","name":"推荐"},{"id":"20200209145926452","name":"农业"},{"id":"20200209145926453","name":"搞笑"},{"id":"20200209145846538","name":"影视"},{"id":"20200209145902361","name":"娱乐"},{"id":"20200209145909288","name":"广场舞"},{"id":"20200209145917658","name":"美食"},{"id":"20200209145926450","name":"体育"},{"id":"20200209145926451","name":"生活"}]
     * state : success
     * message : 查询成功
     * tabCur : 20200209145534301
     */

    private List<DataBean> data;
    private String tabCur;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getTabCur() {
        return tabCur;
    }

    public void setTabCur(String tabCur) {
        this.tabCur = tabCur;
    }

    public static class DataBean {
        /**
         * id : 20200209145534301
         * name : 推荐
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
