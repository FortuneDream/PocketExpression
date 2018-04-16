package com.dell.fortune.pocketexpression.module.suspend;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.util.common.DpUtil;
import com.dell.fortune.pocketexpression.util.common.ScreenUtil;

import java.io.File;

/**
 * Created by 81256 on 2018/4/4.
 */

public class SuspendContentWindowView extends LinearLayout implements BaseQuickAdapter.OnItemClickListener {
    private RelativeLayout mContentLayout;
    private WindowManager.LayoutParams mContentParams;
    private SuspendContentAdapter mAdapter;
    private RecyclerView recyclerView;

    public SuspendContentWindowView(Context context) {
        super(context);
        init();
        initWindowParams(context);
    }

    private void initWindowParams(Context context) {
        mContentParams = new WindowManager.LayoutParams();
        mContentParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统提示性窗口，在普通App图层之上
        mContentParams.format = PixelFormat.RGBA_8888;//透明背景
        mContentParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不操作？？
        //初始位置
        mContentParams.gravity = Gravity.BOTTOM;//或操作,表示同时拥有，求并集
        mContentParams.x = 0;
        mContentParams.y = ScreenUtil.getScreenHeight(getContext());
        //长宽数值
        mContentParams.width = DpUtil.Dp2Px(context, 300);
        mContentParams.height = DpUtil.Dp2Px(context, 300);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private void initRecycler() {
        mAdapter = new SuspendContentAdapter(R.layout.item_suspend_content);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_suspend_window_content, this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    private void init() {
        setOrientation(VERTICAL);
        initRecycler();
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    }

    public RelativeLayout getContentLayout() {
        return mContentLayout;
    }


    public WindowManager.LayoutParams getContentParams() {
        return mContentParams;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(mAdapter.getData().get(position).getPath());
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/*");
        getContext().startActivity(Intent.createChooser(shareIntent, "表情包斗图"));
    }
}
