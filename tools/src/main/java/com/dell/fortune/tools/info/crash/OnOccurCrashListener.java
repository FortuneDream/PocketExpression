package com.dell.fortune.tools.info.crash;

public interface OnOccurCrashListener {
    void onCrash(Thread thread,Throwable ex);
}
