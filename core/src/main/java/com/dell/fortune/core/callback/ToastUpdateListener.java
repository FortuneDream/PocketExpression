/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.callback;

import com.dell.fortune.core.model.event.LoadingViewEvent;
import com.dell.fortune.tools.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */
//封装更新，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastUpdateListener extends UpdateListener {
    public abstract void onSuccess();

    public ToastUpdateListener() {

    }

    @Override
    final public void done(BmobException e) {
        if (e == null) {
            onSuccess();
        } else {
            onFail(e);
        }
    }

    public void onFail(BmobException e) {
        EventBus.getDefault().post(new LoadingViewEvent(false));
        ToastUtil.showToast(e.getMessage());
        e.printStackTrace();
    }
}
