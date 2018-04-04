package com.dell.fortune.pocketexpression.util.common;

import com.dell.fortune.pocketexpression.model.dao.DaoSession;

/**
 * Created by 81256 on 2018/3/28.
 */
//包装类
public class GreenDaoUtil {
    private static DaoSession session;

    public static DaoSession getSession() {
        return session;
    }

    public static void setSession(DaoSession session) {
        GreenDaoUtil.session = session;
    }
}
