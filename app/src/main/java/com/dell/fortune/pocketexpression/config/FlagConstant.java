package com.dell.fortune.pocketexpression.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by 81256 on 2018/3/28.
 */

public class FlagConstant {
    public static final int REQUEST_LOGIN = 1001;
    public static final int REQUEST_REGISTER = 1002;

    //检验邮箱的正则表达式
    public final static String CHECK_EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    public final static String SP_CURRENT_PAGE = "SP_CURRENT_PAGE";

    //本地存储目录
    public final static String COLLECTION_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ExpressionCollection";
    //本地缓存目录（定时清理）
    public final static String TEMP_SHARE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ExpressionTemp";
}
