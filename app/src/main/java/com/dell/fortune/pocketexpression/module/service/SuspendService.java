package com.dell.fortune.pocketexpression.module.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.dell.fortune.pocketexpression.util.common.LogUtils;

public class SuspendService extends Service {
    private SuspendIconWindowView mSuspendIconWindowView;
    private SuspendContentWindowView mSuspendContentWindowView;
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
//            LogUtils.e(runningActivity);
        }
        return true;
    }

    //悬浮窗口Window
    private void createSuspendWindow() {
        mSuspendIconWindowView = new SuspendIconWindowView(this);
        mWindowManager.addView(mSuspendIconWindowView.getIconLayout(), mSuspendIconWindowView.getIconParams());
        mSuspendIconWindowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //表情包列表
                alertSuspendContent();
            }
        });
        //拖动
        mSuspendIconWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                LogUtils.e("悬浮窗","正在移动");
                mSuspendIconWindowView.getIconParams().x = (int) (motionEvent.getRawX() - 150);
                mSuspendIconWindowView.getIconParams().y = (int) (motionEvent.getRawY()) - 150 - mStatusBarHeight;
                mWindowManager.updateViewLayout(mSuspendIconWindowView.getIconLayout(), mSuspendIconWindowView.getIconParams());
                return false;
            }
        });
    }

    //表情包列表
    private void alertSuspendContent() {
        mSuspendContentWindowView = new SuspendContentWindowView(this);
        mWindowManager.addView(mSuspendContentWindowView.getContentLayout(), mSuspendContentWindowView.getContentParams());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSuspendIconWindowView != null) {
            mWindowManager.removeView(mSuspendIconWindowView.getIconLayout());
            mSuspendIconWindowView = null;
        }
        if (mSuspendContentWindowView != null) {
            mWindowManager.removeView(mSuspendContentWindowView.getContentLayout());
            mSuspendContentWindowView = null;
        }
    }
}
