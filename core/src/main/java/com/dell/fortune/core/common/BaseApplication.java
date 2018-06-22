/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.common;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dell.fortune.core.config.FlagConstant;


import com.dell.fortune.tools.SharedPrefsUtil;
import com.dell.fortune.tools.crash.CrashCatch;
import com.dell.fortune.tools.toast.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import cn.bmob.v3.Bmob;

/**
 * Created by 81256 on 2018/3/17.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsUtil.init(this);
        ToastUtil.init(this);
        // Fresco
        initFresco();
        // Bmob
        Bmob.initialize(this, BmobConstant.APP_ID);
        //异常捕获
        CrashCatch crashCatch = CrashCatch.getInstance();
        crashCatch.setListener(new CrashDefaultHandler(this));
        crashCatch.init();
        //建立文件夹
        createDir();
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
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


    private void initFresco() {
        Fresco.initialize(this);
    }
}
