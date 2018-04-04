package com.dell.fortune.pocketexpression.module.service;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.util.common.DpUtil;

/**
 * Created by 81256 on 2018/4/4.
 */

public class IconWindowView {
    private WindowManager.LayoutParams mIconParams;
    private RelativeLayout mIconLayout;
    private Context mContext;


    public IconWindowView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mIconLayout = (RelativeLayout) inflater.inflate(R.layout.view_suspend_window_icon, null);
        mIconLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//主动测量
        mIconParams = new WindowManager.LayoutParams();
        this.mContext = context;
        mIconParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
        mIconParams.format = PixelFormat.RGBA_8888;//透明背景
        mIconParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不操作？？
        //初始位置
        mIconParams.gravity = Gravity.LEFT | Gravity.TOP;//或操作,表示同时拥有，求并集
        mIconParams.x = 0;
        mIconParams.y = 0;
        //长宽数值
        mIconParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mIconParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


    }


    public WindowManager.LayoutParams getIconParams() {
        return mIconParams;
    }


    public RelativeLayout getIconLayout() {
        return mIconLayout;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        onClickListener.onClick(mIconLayout);
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        mIconLayout.setOnTouchListener(onTouchListener);
    }
}
