package com.dell.fortune.tools.info.crash;

import android.os.Environment;
import android.os.Process;

import java.io.File;

/**
 * Created by 鹏君 on 2018/4/22.
 */
public class CrashCatch implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashCatch";
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ExpressionCrash" + File.separator + "log" + File.separator;
    private static CrashCatch instance = new CrashCatch();
    private static Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private static final boolean DEBUG = true;
    //Crash监听
    private OnOccurCrashListener listener;

    public void setListener(OnOccurCrashListener listener) {
        this.listener = listener;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //默认上传
        if (listener != null) {
            listener.onCrash(thread,ex);
        }
        ex.printStackTrace();
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private CrashCatch() {

    }

    public static CrashCatch getInstance() {
        return instance;
    }

    public void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
