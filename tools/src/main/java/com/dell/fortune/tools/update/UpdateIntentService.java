/*
 * Copyright (c) 2018.
 * 版权归于Github.FortuneDream所有
 *
 */

package com.dell.fortune.tools.update;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import com.dell.fortune.tools.FileUtil;
import com.dell.fortune.tools.IntentUtil;
import com.dell.fortune.tools.R;

import java.io.File;

public class UpdateIntentService extends IntentService {
    public final static String PARAM_URL = "url";
    public final static String PARAM_DIR = "dir_path";
    public final static String PARAM_APP_LOGO = "app_logo";
    private Notification mNotification;
    private int mNotificationId = 1;
    private NotificationManager nm;
    private UpdateTask mTask;


    public UpdateIntentService() {
        super("UpdateIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //不能再构造函数从初始化，Context=null
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (intent == null) {
            return;
        }
        Toast.makeText(this, "正在下载更新包...", Toast.LENGTH_SHORT).show();
        String url = intent.getStringExtra(PARAM_URL);
        String dirPath = intent.getStringExtra(PARAM_DIR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int appLogoResourceId = intent.getIntExtra(PARAM_APP_LOGO, 0);
            openOreoUpdateNotification(appLogoResourceId);//开启下载通知栏
        } else {
            openUpdateNotification();
        }
        File fileByNet = FileUtil.createFileByNet(url, dirPath);
        mTask = new UpdateTask(url, fileByNet);
        boolean isFinish = mTask.executeDownloadApk();
        if (isFinish) {
            IntentUtil.installApk(this, mTask.getApkFile());
            nm.cancel(mNotificationId);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTask != null)
            mTask.interrupt();
        if (mNotification != null) {
            nm.cancel(mNotificationId);
        }
    }

    //低版本下载通知栏
    private void openUpdateNotification() {
        mNotification = new NotificationCompat.Builder(this)
                .setContentTitle(getAppName())
                .setContentText("正在下载更新包...")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .build();
        if (nm != null) {
            nm.notify(mNotificationId, mNotification);
        }

    }

    //Oreo下载通知栏
    //加入了标记后就不会有代码提示API不够了
    @TargetApi(Build.VERSION_CODES.O)
    private void openOreoUpdateNotification(int appLogoResourceId) {
        String mChannelId = "update";
        String mChannelName = "更新软件";
        NotificationChannel channel = new NotificationChannel(mChannelId, mChannelName, NotificationManager.IMPORTANCE_DEFAULT);
        if (nm != null) {
            nm.createNotificationChannel(channel);
            mNotification = new NotificationCompat.Builder(this, mChannelId)
                    .setContentTitle(getAppName())
                    .setContentText("正在下载更新包...")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(appLogoResourceId)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), appLogoResourceId))
                    .setAutoCancel(true)
                    .build();
            nm.notify(mNotificationId, mNotification);
        }
    }

    private CharSequence getAppName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "应用名字";
        }
    }

}
