/*
 * Copyright (c) 2018.
 * 版权归于Github.FortuneDream所有
 *
 */

package com.dell.fortune.tools.update;

import android.os.Environment;

import com.dell.fortune.tools.LogUtils;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class UpdateTask {
    private String urlStr; // 下载URL
    private File apkFile; // 文件保存路径
    private boolean isFinished; // 下载是否完成
    private boolean interrupted = false; // 是否强制停止下载线程

    public UpdateTask(String urlStr, File apkFile) {
        this.urlStr = urlStr;
        this.apkFile = apkFile;
    }

    public File getApkFile() {
        if (isFinished)
            return apkFile;
        else
            return null;
    }

    /**
     * 强行终止文件下载
     */
    public void interrupt() {
        interrupted = true;
    }


    public boolean executeDownloadApk() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LogUtils.e("TAG", "外部存储卡不存在，下载失败！");
            return false;
        }
        InputStream iStream = null;
        HttpURLConnection conn = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            java.net.URL url = new java.net.URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(20 * 1000);
            iStream = conn.getInputStream();
            fos = new FileOutputStream(apkFile);
            bis = new BufferedInputStream(iStream);
            byte[] buffer = new byte[1024];
            int len;
            int length = conn.getContentLength(); // 获取文件总长度
            int total = 0;
            while (!interrupted && ((len = bis.read(buffer)) != -1)) {
                fos.write(buffer, 0, len);
                total += len;
            }
            if (total == length) {
                isFinished = true;
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(iStream, fos, bis);
        }
        return false;
    }

    private void close(Closeable... closeable) {
        try {
            for (Closeable item : closeable) {
                if (item != null) {
                    item.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
