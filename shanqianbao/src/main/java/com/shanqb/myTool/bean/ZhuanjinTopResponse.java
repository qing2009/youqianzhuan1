package com.shanqb.myTool.bean;

import java.util.List;

public class ZhuanjinTopResponse extends BaseJsonResponse2 {
    private List<ZhuanjinTop> data;

    public List<ZhuanjinTop> getData() {
        return data;
    }

    public void setData(List<ZhuanjinTop> data) {
        this.data = data;
    }
}
