package com.dell.fortune.pocketexpression.module.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.util.common.ConvertUtil;
import com.dell.fortune.pocketexpression.util.common.LogUtils;

public class SuspendService extends Service {
    private WindowManager.LayoutParams mIconParams;
    private WindowManager.LayoutParams mContentParams;
    private WindowManager windowManager;
    private RelativeLayout mIconLayout;
    private RelativeLayout mContentLayout;
    private ImageButton textView;
    private int statusBarHeight = -1;

    public SuspendService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
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
        mIconParams = new WindowManager.LayoutParams();
        mIconParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
        mIconParams.format = PixelFormat.RGBA_8888;//透明背景
        mIconParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不操作？？
        //初始位置
        mIconParams.gravity = Gravity.LEFT | Gravity.TOP;//或操作,表示同时拥有，求并集
        mIconParams.x = 0;
        mIconParams.y = 0;
        //长宽数值
        mIconParams.width = 300;
        mIconParams.height = 300;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mIconLayout = (RelativeLayout) inflater.inflate(R.layout.view_suspend_window_icon, null);
        windowManager.addView(mIconLayout, mIconParams);
        //主动计算当前View的宽高信息
        mIconLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //表情包列表
                alertSuspendContent();
            }
        });
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");//这是什么意思？
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        textView = mIconLayout.findViewById(R.id.image);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mIconParams.x = (int) (motionEvent.getRawX() - 150);
                mIconParams.y = (int) (motionEvent.getRawY()) - 150 - statusBarHeight;
                windowManager.updateViewLayout(mIconLayout, mIconParams);
                return false;
            }
        });
    }

    //表情包列表
    private void alertSuspendContent() {
        mContentParams = new WindowManager.LayoutParams();
        mContentParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
        mContentParams.format = PixelFormat.RGBA_8888;//透明背景
        mContentParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不操作？？
        //初始位置
        mContentParams.gravity = Gravity.BOTTOM;//或操作,表示同时拥有，求并集
        mContentParams.x = 0;
        mContentParams.y = 0;
        //长宽数值
        mContentParams.width = ConvertUtil.Dp2Px(this, 300);
        mContentParams.height = ConvertUtil.Dp2Px(this, 300);

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mContentLayout = (RelativeLayout) inflater.inflate(R.layout.view_suspend_window_content, null);
        windowManager.addView(mContentLayout, mIconParams);
        //主动计算当前View的宽高信息
        mContentLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");//这是什么意思？
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        textView = mIconLayout.findViewById(R.id.image);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mIconParams.x = (int) (motionEvent.getRawX() - 150);
                mIconParams.y = (int) (motionEvent.getRawY()) - 150 - statusBarHeight;
                windowManager.updateViewLayout(mIconLayout, mIconParams);
                return false;
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textView != null) {
            windowManager.removeView(mIconLayout);
        }
    }
}
