package com.dell.fortune.pocketexpression.module.suspend;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.util.common.DpUtil;
import com.dell.fortune.pocketexpression.util.common.ScreenUtil;

/**
 * Created by 81256 on 2018/4/4.
 */
//Layout，以及Param都交给WindowManage进行管理,只能监听子View的点击或者触摸进行操作。
public class SuspendIconWindowView extends RelativeLayout {
    private WindowManager.LayoutParams mParentParams;
    private RelativeLayout mIconLayout;


    public SuspendIconWindowView(Context context) {
        super(context);
        init();
        initWindowParam();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_suspend_window_icon, this, true);
        mIconLayout = findViewById(R.id.content);
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//主动测量
    }

    private void init() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    private void initWindowParam() {
        mParentParams = new WindowManager.LayoutParams();
        mParentParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
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


    public WindowManager.LayoutParams getParentParams() {
        return mParentParams;
    }


    public RelativeLayout getParentLayout() {
        return this;
    }

    //让父View接受事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public RelativeLayout getIconLayout() {
        return mIconLayout;
    }


//    public void setOnClickListener(View.OnClickListener onClickListener) {
//        mIconLayout.setOnClickListener(onClickListener);
//    }

    //    /**
//     * 设置触摸事件监听应该是子View而不是父View
//     *
//     * @param onTouchListener
//     */
//    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
//        mIconLayout.setOnTouchListener(onTouchListener);
//    }
}
