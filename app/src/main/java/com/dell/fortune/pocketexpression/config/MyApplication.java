package com.dell.fortune.pocketexpression.config;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.dell.fortune.pocketexpression.model.bean.DaoMaster;
import com.dell.fortune.pocketexpression.model.bean.DaoSession;
import com.dell.fortune.pocketexpression.util.common.GreenDaoUtil;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.greendao.AbstractDaoMaster;

import cn.bmob.v3.Bmob;

/**
 * Created by 81256 on 2018/3/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        initFresco();
        initGreenDao();
        Bmob.initialize(this, "224ac7cab8fe75623e271b06d7495323");
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
