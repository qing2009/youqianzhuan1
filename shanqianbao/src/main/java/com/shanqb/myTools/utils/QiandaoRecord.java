package com.shanqb.myTools.utils;

import com.shanqb.myTools.bean.QiandaoItemBean;

import java.util.List;

/**
 * 签到数据保存实现分析
 * 需求：
 *  1.总签到天数
 *  2.本周签到记录
 *
 * 实现：
 *  1.定义变量qiandaoSum记录总签到天数。
 *  2.从配置文件获取本周数据，
 *      如果没有数据，则初始化本周数据。本周数据用一个类表示，有记录本周时今年的第几周，以及这周的数据。
 *      如果有数据，检查本周与保存的周是否为同一周，如果非同一周，则更新数据。
 *      签到页初始化：
 *          初始化本周记录：根据map展示页面签到记录。
 *          初始化签到按钮：获取今日日期，查询今天是否已经签到。
 *                  已签到：签到按钮不可点击。
 *                  未签到：签到按钮可点击。
 *                      点击后更新map，刷新数据，并保存到配置文件。
 */
public class QiandaoRecord {
    private int weekofYear;
    private List<QiandaoItemBean> qiandaoItemBeanList;

    public int getWeekofYear() {
        return weekofYear;
    }

    public void setWeekofYear(int weekofYear) {
        this.weekofYear = weekofYear;
    }

    public List<QiandaoItemBean> getQiandaoItemBeanList() {
        return qiandaoItemBeanList;
    }

    public void setQiandaoItemBeanList(List<QiandaoItemBean> qiandaoItemBeanList) {
        this.qiandaoItemBeanList = qiandaoItemBeanList;
    }

}

