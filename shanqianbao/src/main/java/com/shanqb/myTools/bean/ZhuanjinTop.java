package com.shanqb.myTools.bean;

import java.util.Objects;

public class ZhuanjinTop {
    private int userHeadResId;
    private String merName;
    private String gameDes;
    private double allAmt;

    public int getUserHeadResId() {
        return userHeadResId;
    }

    public void setUserHeadResId(int userHeadResId) {
        this.userHeadResId = userHeadResId;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getGameDes() {
        return gameDes;
    }

    public void setGameDes(String gameDes) {
        this.gameDes = gameDes;
    }

    public double getAllAmt() {
        return allAmt;
    }

    public void setAllAmt(double allAmt) {
        this.allAmt = allAmt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZhuanjinTop that = (ZhuanjinTop) o;
        return Double.compare(that.allAmt, allAmt) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(allAmt);
    }
}
