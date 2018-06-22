/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.main;


import android.database.sqlite.SQLiteDatabase;

import com.dell.fortune.core.common.BaseApplication;
import com.dell.fortune.pocketexpression.model.dao.DaoMaster;
import com.dell.fortune.pocketexpression.util.common.GreenDaoUtil;

public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();//本地数据库
    }
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Local-Expression");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        GreenDaoUtil.setSession(master.newSession());
    }
}
