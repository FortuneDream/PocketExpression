package com.dell.fortune.pocketexpression.config;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.dell.fortune.pocketexpression.common.BmobConstant;
import com.dell.fortune.pocketexpression.model.dao.DaoMaster;
import com.dell.fortune.pocketexpression.util.common.GreenDaoUtil;

import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.tools.SharedPrefsUtil;
import com.dell.fortune.tools.crash.CrashCatch;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.statistics.AppStat;

/**
 * Created by 81256 on 2018/3/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsUtil.init(this);
        ToastUtil.init(this);
        initFresco();
        initGreenDao();
        Bmob.initialize(this, BmobConstant.APP_ID);
        CrashCatch crashCatch = CrashCatch.getInstance();
        crashCatch.setListener(new CrashDefaultHandler(this));
        crashCatch.init();
        createDir();//建立文件夹
    }

    private void createDir() {
        File collectionDir = new File(FlagConstant.COLLECTION_DIR);
        if (!collectionDir.exists()) {
            collectionDir.mkdirs();
        }
        File tempShareDir = new File(FlagConstant.TEMP_SHARE_DIR);
        if (tempShareDir.exists()) {
            tempShareDir.mkdirs();
        }

    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Local-Expression");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        GreenDaoUtil.setSession(master.newSession());
    }


    private void initFresco() {
        Fresco.initialize(this);
    }
}
