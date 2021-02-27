package com.shanqb.weishouzhuan.bean;

import java.util.List;
import java.util.Objects;

public class ZhuanjinTopResponse extends BaseJsonResponse{
    private List<ZhuanjinTop> data;

    public List<ZhuanjinTop> getData() {
        return data;
    }

    public void setData(List<ZhuanjinTop> data) {
        this.data = data;
    }
}
