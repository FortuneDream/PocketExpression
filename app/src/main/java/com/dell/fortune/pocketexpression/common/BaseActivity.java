package com.dell.fortune.pocketexpression.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;


/**
 * Created by 鹏君 on 2016/10/1.
 */

public abstract class BaseActivity<V extends IBaseView, T extends BasePresenter<V>> extends AppCompatActivity implements IBaseView {
    protected Activity mContext;
    protected T presenter;
    public final String TAG = this.getClass().getName();
    private static final int FLAG_DISMISS_DIALOG = 2001;
    public AlertDialog mLoadingDialog;//这个dialog一般在上传，下载，的时候才会用到
    private boolean mIsViewValid = true;

    @Override
    public boolean isViewValid() {
        return mIsViewValid;
    }

    protected abstract T createPresenter();

    private Handler handler = new UIHandler(this);

    //防止dialog内存泄漏
    private static class UIHandler extends Handler {
        WeakReference<BaseActivity> softActivity;

        UIHandler(BaseActivity baseActivity) {
            softActivity = new WeakReference<>(baseActivity);//使用弱引用+static 一是为了Activity可以在只有Handler引用的情况下立即被清除，而是为了可以调用activity的方法。
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FLAG_DISMISS_DIALOG:
                    BaseActivity activity = softActivity.get();
                    if (activity == null) {
                        return;
                    }
                    if (activity.mLoadingDialog != null && activity.mLoadingDialog.isShowing()) {
                        activity.mLoadingDialog.dismiss();
                    }
                    break;
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentResource());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = createPresenter();
        this.mContext = this;
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
        toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initLoadingView() {
        //初始化一个LoadingDialog
        mLoadingDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .create();
    }

    public void showLoading(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
            handler.sendEmptyMessageDelayed(FLAG_DISMISS_DIALOG, 5 * 1000);
        } else {
            mLoadingDialog.dismiss();
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
        mLoadingDialog.dismiss();
        presenter.detachView();
    }


}
