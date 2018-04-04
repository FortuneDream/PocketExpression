package com.dell.fortune.pocketexpression.module.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.dell.fortune.pocketexpression.util.common.LogUtils;

public class SuspendService extends Service {
    private IconWindowView mIconWindowView;
    private WindowManager mWindowManager;
    private int mStatusBarHeight;

    public SuspendService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        //状态栏
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");//这是什么意思？
        if (resourceId > 0) {
            mStatusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        //Icon
        if (isTargetActivity()) {
            createSuspendWindow();
        }

    }

    private boolean isTargetActivity() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
            LogUtils.e(runningActivity);
        }
        return true;
    }

    //悬浮窗口Window
    private void createSuspendWindow() {
        mIconWindowView = new IconWindowView(this);
        mWindowManager.addView(mIconWindowView.getIconLayout(), mIconWindowView.getIconParams());
        mIconWindowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //表情包列表
                alertSuspendContent();
            }
        });
        //拖动
        mIconWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mIconWindowView.getIconParams().x = (int) (motionEvent.getRawX() - 150);
                mIconWindowView.getIconParams().y = (int) (motionEvent.getRawY()) - 150 - mStatusBarHeight;
                mWindowManager.updateViewLayout(mIconWindowView.getIconLayout(), mIconWindowView.getIconParams());
                return false;
            }
        });
    }

    //表情包列表
    private void alertSuspendContent() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIconWindowView != null) {
            mWindowManager.removeView(mIconWindowView.getIconLayout());
            mIconWindowView = null;
        }
    }
}
