/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.model;

import android.content.Context;

import com.dell.fortune.core.config.FlagConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.tools.IntentUtil;

import java.io.File;
import java.net.URLConnection;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class ShareModel {

    public void shareNetPic(final Context context, final ExpressionItem item) {
        String urlType = URLConnection.guessContentTypeFromName(item.getUrl());
        String type = urlType.replaceAll("image/", "");
        BmobFile file = new BmobFile(item.getMd5() + "." + type, "", item.getUrl());
        File targetDir = new File(FlagConstant.TEMP_SHARE_DIR, file.getFilename());//目标文件
        file.download(targetDir, new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    IntentUtil.sharePic(context, s);
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }
}
