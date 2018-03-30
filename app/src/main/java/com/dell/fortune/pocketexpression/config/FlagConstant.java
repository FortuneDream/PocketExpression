package com.dell.fortune.pocketexpression.config;

/**
 * Created by 81256 on 2018/3/28.
 */

public class FlagConstant {
    public static final int REQUEST_LOGIN = 1001;
    public static final int REQUEST_REGISTER = 1002;

    public static final int SUCCESS = 1;
    public static final int FAIL = 2;

    //检验邮箱的正则表达式
    public final static String CHECK_EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
}
