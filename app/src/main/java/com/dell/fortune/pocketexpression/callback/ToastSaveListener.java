package com.dell.fortune.pocketexpression.callback;

import com.dell.fortune.pocketexpression.model.event.LoadingViewEvent;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;


import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */
//封装添加，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastSaveListener<T> extends SaveListener<T> {

    public abstract void onSuccess(T t);


    public ToastSaveListener() {
    }

    //不允许重写
    @Override
    final public void done(T t, BmobException e) {
        if (e == null) {
            onSuccess(t);
        } else {
            onFail(t, e);
        }
    }


    public void onFail(T t, BmobException e) {
        EventBus.getDefault().post(new LoadingViewEvent(false));
        ToastUtil.showToast(e.getMessage());
        e.printStackTrace();
    }


}
