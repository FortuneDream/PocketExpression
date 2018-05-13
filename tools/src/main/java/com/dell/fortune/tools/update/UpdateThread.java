package com.dell.fortune.tools.update;

/**
 * Created by 鹏君 on 2017/7/1.
 * （￣m￣）
 */

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.dell.fortune.tools.info.LogUtils;

public class UpdateThread implements Runnable {
    public final static int DOWNLOAD_COMPLETE = -2;
    public final static int DOWNLOAD_FAIL = -1;
    public final static String TAG = "UpdateThread";
    private Handler mHandler; // 传入的Handler,用于像Activity或service通知下载进度
    private String urlStr; // 下载URL
    private File apkFile; // 文件保存路径
    private boolean isFinished; // 下载是否完成
    private boolean interrupted = false; // 是否强制停止下载线程

    public UpdateThread(Handler handler, String urlStr, File file) {
        LogUtils.i(TAG, urlStr);
        this.mHandler = handler;
        this.urlStr = urlStr;
        this.apkFile = file;
        this.isFinished = false;
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
    public void interruptThread() {
        interrupted = true;
    }

    @SuppressLint("NewApi")
    @Override
    public void run() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LogUtils.e(TAG, "外部存储卡不存在，下载失败！");
            mHandler.sendEmptyMessage(DOWNLOAD_FAIL);
        }
        InputStream iStream = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            java.net.URL url = new java.net.URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(20 * 1000);
            iStream = conn.getInputStream();
            fos = new FileOutputStream(apkFile);
            bis = new BufferedInputStream(iStream);
            byte[] buffer = new byte[1024];
            int len;
            int length = conn.getContentLength(); // 获取文件总长度
            int total = 0;
            int times = 0;// 设置更新频率，频繁操作ＵＩ线程会导致系统奔溃
            double rate = (double) 100 / length; // 最大进度转化为100
            int timeLoad = length / 100 / 1024;
            while (!interrupted && ((len = bis.read(buffer)) != -1)) {
                fos.write(buffer, 0, len);
                // 获取已经读取长度
                total += len;
                int p = (int) (total * rate);
                if (times >= timeLoad || p == 100) {
                    /*
                     * 这是防止频繁地更新通知，而导致系统变慢甚至崩溃。 非常重要。。。。。
                     */
                    LogUtils.e("time", String.valueOf(times));
                    times = 0;
                    Message msg = Message.obtain();
                    msg.what = p;
                    mHandler.sendMessage(msg);
                }
                times++;
            }
            if (total == length) {
                isFinished = true;
                mHandler.sendEmptyMessage(DOWNLOAD_COMPLETE);
                LogUtils.e(TAG, "下载完成结束");
                return;
            }
            LogUtils.e(TAG, "强制中途结束");
        } catch (Exception e) {
            mHandler.sendEmptyMessage(DOWNLOAD_FAIL);
            LogUtils.e(TAG, "异常中途结束");
            e.printStackTrace();
        } finally {
            close(fos);
            close(bis);
            close(iStream);
        }
    }

    //关闭流
    private void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}