package com.dell.fortune.pocketexpression.util.common;

import android.database.sqlite.SQLiteDatabase;

import com.dell.fortune.pocketexpression.model.bean.DaoMaster;
import com.dell.fortune.pocketexpression.model.bean.DaoSession;

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
