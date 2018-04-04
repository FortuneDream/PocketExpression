package com.dell.fortune.pocketexpression.module.service;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.util.common.DpUtil;

/**
 * Created by 81256 on 2018/4/4.
 */

public class ContentWindowView {
    private RelativeLayout mContentLayout;
    private WindowManager.LayoutParams mContentParams;
    private WindowManager mWindowManager;

    public ContentWindowView(Context context, WindowManager windowManager) {
        this.mWindowManager = windowManager;
        mContentParams = new WindowManager.LayoutParams();
        mContentParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
        mContentParams.format = PixelFormat.RGBA_8888;//透明背景
        mContentParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不操作？？
        //初始位置
        mContentParams.gravity = Gravity.BOTTOM;//或操作,表示同时拥有，求并集
        mContentParams.x = 0;
        mContentParams.y = 0;
        //长宽数值
        mContentParams.width = DpUtil.Dp2Px(context, 300);
        mContentParams.height = DpUtil.Dp2Px(context, 300);

        LayoutInflater inflater = LayoutInflater.from(context);
        mContentLayout = (RelativeLayout) inflater.inflate(R.layout.view_suspend_window_content, null);
        //主动计算当前View的宽高信息
        mContentLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
