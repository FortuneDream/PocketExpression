/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.util.common;

public class JNIUtil {
    static {
        System.loadLibrary("appNDK");
    }
    public native String getSignature();
}
