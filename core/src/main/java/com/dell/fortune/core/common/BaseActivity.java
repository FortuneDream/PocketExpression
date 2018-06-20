/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.core.R;
import com.dell.fortune.tools.dialog.shapeloadingview.DialogShapeLoading;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;



/**
 * Created by 鹏君 on 2016/10/1.
 */

public abstract class BaseActivity<V extends IBaseView, T extends BasePresenter<V>> extends AppCompatActivity implements IBaseView {
    protected Activity mContext;
    protected T presenter;
    public final String TAG = this.getClass().getName();
    private static final int FLAG_SHOW_DIALOG = 2002;
    private static final int FLAG_DISMISS_DIALOG = 2003;
    private boolean mIsViewValid = true;
    private DialogShapeLoading mDialogShapeLoading;

    @Override
    public boolean isViewValid() {
        return mIsViewValid;
    }

    protected abstract T createPresenter();
    protected abstract void findViewSetListener();

    public Handler handler = new UIHandler(this);

    //防止dialog内存泄漏
    private static class UIHandler extends Handler {
        WeakReference<BaseActivity> softActivity;

        UIHandler(BaseActivity baseActivity) {
            softActivity = new WeakReference<>(baseActivity);//使用弱引用+static 一是为了Activity可以在只有Handler引用的情况下立即被清除，而是为了可以调用activity的方法。
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = softActivity.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case FLAG_SHOW_DIALOG:
                    if (activity.mDialogShapeLoading != null && !activity.mDialogShapeLoading.isShowing()) {
                        activity.mDialogShapeLoading.show();
                    }
                case FLAG_DISMISS_DIALOG:
                    if (activity.mDialogShapeLoading != null && activity.mDialogShapeLoading.isShowing()) {
                        activity.mDialogShapeLoading.dismiss();
                    }
                    break;
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentResource());
        EventBus.getDefault().register(this);
        presenter = createPresenter();
        this.mContext = this;
        findViewSetListener();
        initView();
        initLoadingView();
    }


    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }

    public void initRecycler(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void initToolbar(Toolbar toolbar, String titleName) {
        toolbar.setTitle(titleName);
        toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.core_colorAccent));
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.core_colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initLoadingView() {
        //初始化一个LoadingDialog
        mDialogShapeLoading = new DialogShapeLoading(mContext);
    }

    @Override
    public void showLoading(boolean isShow) {
        showLoading(isShow, 0);
    }

    @Override
    public void showLoading(boolean isShow, int milliseconds) {
        if (isShow) {
            handler.sendEmptyMessageDelayed(FLAG_SHOW_DIALOG, milliseconds);
            handler.sendEmptyMessageDelayed(FLAG_DISMISS_DIALOG, 5 * 1000);//5秒后消失
        } else {
            handler.sendEmptyMessageDelayed(FLAG_DISMISS_DIALOG, milliseconds);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //直接重启应用，cleartop
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        if (i != null) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDialog(LoadingDialogEvent event) {
        showLoading(event.isShow());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mIsViewValid = false;
        mDialogShapeLoading.dismiss();
        presenter.detachView();
    }


}
