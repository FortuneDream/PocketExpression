package com.dell.fortune.pocketexpression.config;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by 81256 on 2018/3/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "224ac7cab8fe75623e271b06d7495323");
    }
}
