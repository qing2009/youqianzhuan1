package com.shanqb.qianrenzixun.tabview;

/**
 * Created by yx on 16/4/3.
 */
public class TabItem {

    /**
     * icon
     */
    public int imageResId;
    /**
     * 文本
     */
    public int lableResId;
    public int titleResId;


//    public Class<? extends BaseFragment>tagFragmentClz;

    public TabItem(int imageResId, int lableResId, int titleResId) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.titleResId = titleResId;
    }


//    public TabItem(int imageResId, int lableResId, int titleResId) {
//        this.imageResId = imageResId;
//        this.lableResId = lableResId;
//        this.titleResId = titleResId;
//    }

//    public TabItem(int imageResId, int lableResId, int titleResId, Class<? extends BaseFragment> tagFragmentClz) {
//        this.imageResId = imageResId;
//        this.lableResId = lableResId;
//        this.titleResId = titleResId;
//        this.tagFragmentClz = tagFragmentClz;
//    }
}



