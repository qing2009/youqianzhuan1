package com.shanqb.wallet.utils;

public class AcitonConstants {


    //登录接口
    public static final String INTER_LOGIN = "login";
    /**
     * 登录接口参数
     */
    public static final String LOGIN_USERNAME = "loginCode";
    public static final String LOGIN_PASSWORD = "loginPwd";


    //注册接口
    public static final String INTER_REGISTER = "register";


    //修改密码接口
    public static final String INTER_UPDATEPSW = "updatePsw";
    /**
     * 修改密码接口参数
     */
    public static final String UPDATEPSW_OLDPSW = "oldPsw";
    public static final String UPDATEPSW_NEWPSW = "newPsw";


    //试玩记录
    public static final String INTER_ORDERLIST = "getOrderList";
    /**
     * 试玩记录接口参数
     */
    public static final String ORDERLIST_MEMBERCODE = "memberCode";
    public static final String ORDERLIST_PAGE = "page";
    public static final String ORDERLIST_SIZE = "size";


    //获取用户信息
    public static final String INTER_MYINFO = "myInfo";

    public static final String MERCODE = "merCode";
}
