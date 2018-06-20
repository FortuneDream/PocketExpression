/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.common;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by 鹏君 on 2017/1/31.
 */

public abstract class BasePresenter<T extends IBaseView> {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
    public final String TAG = this.getClass().getName();
    protected Context mContext;
    protected Reference<T> mViewRef;// View借口类型的弱引用
    protected T mView;

    public BasePresenter(T view) {
        attachView(view);
        mView = getIViewRef();
        mContext = mView.getCurrentContext();
    }

    protected T getIViewRef() {
        return mViewRef.get();
    }

    public void attachView(T activity) {
        mViewRef = new WeakReference<>(activity);//建立关联
    }


    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public void startActivity(Class targetActivity){
        mView.getCurrentContext().startActivity(new Intent(mContext,targetActivity));
    }


}
