package com.shanqb.wanglezhuan.bean;

import com.shanqb.wanglezhuan.bean.BaseJsonResponse2;
import com.shanqb.wanglezhuan.bean.ZhuanjinTop;

import java.util.List;
import java.util.Objects;

public class ZhuanjinTopResponse extends BaseJsonResponse2 {
    private List<ZhuanjinTop> data;

    public List<ZhuanjinTop> getData() {
        return data;
    }

    public void setData(List<ZhuanjinTop> data) {
        this.data = data;
    }
}
