package com.dell.fortune.pocketexpression;

import android.app.Service;
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
import android.widget.TextView;

public class MyService extends Service {
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private RelativeLayout contentLayout;
    private ImageButton textView;
    private int statusBarHeight = -1;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createSuspendWindow();
    }

    //悬浮窗口Window
    private void createSuspendWindow() {
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
        params.format = PixelFormat.RGBA_8888;//透明背景
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不操作？？
        //初始位置
        params.gravity = Gravity.LEFT | Gravity.TOP;//或操作,表示同时拥有，求并集
        params.x = 0;
        params.y = 0;
        //长宽数值
        params.width = 300;
        params.height = 300;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        contentLayout = (RelativeLayout) inflater.inflate(R.layout.view_suspend_window, null);
        windowManager.addView(contentLayout, params);

        //主动计算当前View的宽高信息
        contentLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");//这是什么意思？
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        textView = contentLayout.findViewById(R.id.image);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                params.x = (int) (motionEvent.getRawX() - 150);
                params.y = (int) (motionEvent.getRawY()) - 150 - statusBarHeight;
                windowManager.updateViewLayout(contentLayout, params);
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
            windowManager.removeView(contentLayout);
        }
    }
}
