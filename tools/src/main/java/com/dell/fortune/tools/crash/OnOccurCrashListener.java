package com.dell.fortune.tools.crash;

public interface OnOccurCrashListener {
    void onCrash(Thread thread,Throwable ex);
}
