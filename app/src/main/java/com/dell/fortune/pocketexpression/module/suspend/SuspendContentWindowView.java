package com.dell.fortune.pocketexpression.module.suspend;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionDaoOpe;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.util.common.RxApi;
import com.dell.fortune.tools.LogUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.functions.Consumer;

/**
 * Created by 81256 on 2018/4/4.
 */

public class SuspendContentWindowView extends LinearLayout implements BaseQuickAdapter.OnItemClickListener {
    ImageView closeContentIv;
    RecyclerView recyclerView;
    private WindowManager.LayoutParams mContentParams;
    private SuspendContentAdapter mAdapter;
    private LocalExpressionDaoOpe localExpressionDaoOpe;
    private OnClickCloseListener onClickCloseListener;

    public void setOnClickCloseListener(OnClickCloseListener onClickCloseListener) {
        this.onClickCloseListener = onClickCloseListener;
    }

    public interface OnClickCloseListener {
        void onClick(View view);
    }

    public SuspendContentWindowView(Context context) {
        super(context);
        initWindowParams();
        init();
    }


    private void initWindowParams() {
        mContentParams = new WindowManager.LayoutParams();
        mContentParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;//不同的window 要不用不同的type,起始位置默认是屏幕的中央
        mContentParams.format = PixelFormat.RGBA_8888;//透明背景
        mContentParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不操作？？
        //初始位置
        //长宽数值
        mContentParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mContentParams.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private void initRecycler() {
        mAdapter = new SuspendContentAdapter(R.layout.item_suspend_content);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        getList();
    }

    private void getList() {
        RxApi.create(new Callable<List<LocalExpressionItem>>() {
            @Override
            public List<LocalExpressionItem> call() throws Exception {
                return localExpressionDaoOpe.findAll();
            }
        }).subscribe(new Consumer<List<LocalExpressionItem>>() {
            @Override
            public void accept(List<LocalExpressionItem> localExpressionItems) throws Exception {
                mAdapter.addData(localExpressionItems);
                LogUtils.e("sdfsdf:" + localExpressionItems.size());
            }
        });
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_suspend_window_content, this);
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        recyclerView = findViewById(R.id.recycler_view);
        closeContentIv = findViewById(R.id.close_content_iv);
        closeContentIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCloseListener.onClick(v);//点击关闭
            }
        });
        localExpressionDaoOpe = new LocalExpressionDaoOpe();
        initRecycler();

    }

    public LinearLayout getContentLayout() {
        return this;
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
