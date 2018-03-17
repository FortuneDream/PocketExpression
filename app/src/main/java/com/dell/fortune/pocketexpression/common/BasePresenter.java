package com.dell.fortune.pocketexpression.common;

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
    protected Reference<T> mViewRef;// View借口类型的弱引用


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




}
