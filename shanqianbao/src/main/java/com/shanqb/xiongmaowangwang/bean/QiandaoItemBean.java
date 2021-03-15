package com.shanqb.xiongmaowangwang.bean;

public class QiandaoItemBean {

    public enum benzhou{周一, 周二, 周三,周四, 周五, 周六, 周日};

    private benzhou day;
    private boolean isQiandao;

    public QiandaoItemBean(benzhou day, boolean isQiandao) {
        this.day = day;
        this.isQiandao = isQiandao;
    }

    public benzhou getDay() {
        return day;
    }

    public void setDay(benzhou day) {
        this.day = day;
    }

    public boolean isQiandao() {
        return isQiandao;
    }

    public void setQiandao(boolean qiandao) {
        isQiandao = qiandao;
    }



}
