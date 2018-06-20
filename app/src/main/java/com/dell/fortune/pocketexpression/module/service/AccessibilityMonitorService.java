/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.dell.fortune.core.config.IntentConstant;
import com.dell.fortune.tools.LogUtils;

public class AccessibilityMonitorService extends AccessibilityService {
    public final String QQSplashActivity = "com.tencent.mobileqq.activity.SplashActivity";

    public AccessibilityMonitorService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //当前界面为指定包名时，会触发
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String topActivityName = event.getClassName().toString();
            LogUtils.e(topActivityName);
            if (topActivityName.startsWith("android")) {
                return;//排除Android原生控件
            }
            if (topActivityName.equals(QQSplashActivity)) {
                //打开悬浮窗
                sendSuspendBroadcast(true);
            }

            if (topActivityName.endsWith("Launcher")) {
                //关闭悬浮窗
                sendSuspendBroadcast(false);
            }
        }
    }

    public void sendSuspendBroadcast(boolean isShow) {
        Intent intent = new Intent();
        intent.setAction(IntentConstant.SUSPEND_ACTION);
        intent.putExtra(IntentConstant.SUSPEND_PARAM_CONTROL_SUSPEND, isShow);//关闭Suspend
        sendBroadcast(intent);
    }


    @Override
    public void onInterrupt() {

    }

}
