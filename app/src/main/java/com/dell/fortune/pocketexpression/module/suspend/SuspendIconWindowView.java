package com.dell.fortune.pocketexpression.module.suspend;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.util.common.ScreenUtil;
import com.dell.fortune.tools.DpUtil;
import com.dell.fortune.tools.LogUtils;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by 81256 on 2018/4/4.
 */
//Layout，以及Param都交给WindowManage进行管理,只能监听子View的点击或者触摸进行操作。
public class SuspendIconWindowView extends RelativeLayout implements View.OnTouchListener {
    private WindowManager.LayoutParams mParentParams;
    private WindowManager wm;
    private boolean isShow = false;//是否已经展示


    public SuspendIconWindowView(Context context) {
        super(context);
        wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        init();
        initWindowParam();
        //状态栏
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_suspend_window_icon, this, true);
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//主动测量
        setOnTouchListener(this);
    }

    private void init() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    private void initWindowParam() {
        mParentParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParentParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParentParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
        }

        mParentParams.format = PixelFormat.RGBA_8888;//透明背景
        mParentParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//弹出的View收不到Back键的事件
        //初始位置
        mParentParams.gravity = Gravity.LEFT | Gravity.TOP;//或操作,表示同时拥有，求并集
        mParentParams.x = ScreenUtil.getScreenWidth(getContext()) / 4 / 3;
        mParentParams.y = ScreenUtil.getScreenHeight(getContext()) / 3 / 2;
        //长宽数值
        mParentParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParentParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }


    //让父View接受事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void show() {
        if (wm != null && !isShow) {
            wm.addView(this, mParentParams);
            isShow = true;
        }
    }

    public void dismiss() {
        if (wm != null && isShow) {
            wm.removeView(this);
            isShow = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mParentParams.x = (int) (event.getRawX()) - DpUtil.Dp2Px(getContext(), 28);
                mParentParams.y = (int) (event.getRawY()) - DpUtil.Dp2Px(getContext(), 28) - DpUtil.getStatusHeight(getContext());
                LogUtils.e("Suspend", "正在移动");
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        if (wm != null && isShow) {
            wm.updateViewLayout(this, mParentParams);
        }
        return false;
    }
}
