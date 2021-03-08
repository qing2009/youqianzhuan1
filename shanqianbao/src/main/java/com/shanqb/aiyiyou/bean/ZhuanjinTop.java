package com.shanqb.aiyiyou.bean;

import java.util.Objects;

public class ZhuanjinTop {
    private int userHeadResId;
    private String gameName;
    private String gameDes;
    private double money;

    public int getUserHeadResId() {
        return userHeadResId;
    }

    public void setUserHeadResId(int userHeadResId) {
        this.userHeadResId = userHeadResId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDes() {
        return gameDes;
    }

    public void setGameDes(String gameDes) {
        this.gameDes = gameDes;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZhuanjinTop that = (ZhuanjinTop) o;
        return Double.compare(that.money, money) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }
}
