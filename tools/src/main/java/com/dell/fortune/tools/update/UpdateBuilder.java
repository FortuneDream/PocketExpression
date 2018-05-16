/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.tools.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

/**
 * Created by 鹏君 on 2017/7/1.
 * （￣m￣）
 */
public class UpdateBuilder {
    private Context mContext;
    private UpdateConfiguration mConfiguration;

    public UpdateBuilder(Context context) {
        this.mContext = context;

    }

    public UpdateBuilder setConfiguration(UpdateConfiguration configuration) {
        this.mConfiguration = configuration;
        return this;
    }


    public void update() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("新版本号：" + mConfiguration.getVersionCodeStr())
                .setMessage(mConfiguration.getVersionContent())
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startDownloadApkService();
                    }
                });
        if (mConfiguration.isForce()) {
            builder.setCancelable(false);
        } else {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }

    //开始下载
    private void startDownloadApkService() {
        Intent intent = new Intent(mContext, UpdateIntentService.class);
        intent.putExtra(UpdateIntentService.PARAM_URL, mConfiguration.getUrl());
        intent.putExtra(UpdateIntentService.PARAM_DIR, mConfiguration.getDir());
        intent.putExtra(UpdateIntentService.PARAM_APP_LOGO, mConfiguration.getAppLogoResourceId());
        mContext.startService(intent);
    }
}