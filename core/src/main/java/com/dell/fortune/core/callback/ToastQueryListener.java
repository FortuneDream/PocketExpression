/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.callback;


import com.dell.fortune.core.model.event.LoadingViewEvent;
import com.dell.fortune.core.model.event.RefreshingViewEvent;
import com.dell.fortune.tools.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */
//封装查询，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastQueryListener<T> extends FindListener<T> {

    protected ToastQueryListener() {
    }

    public abstract void onSuccess(List<T> list);

    @Override
    final public void done(List<T> list, BmobException e) {
        if (e == null) {
            onSuccess(list);
        } else {
            onFail(e);
        }
    }

    public void onFail(BmobException e) {
        EventBus.getDefault().post(new LoadingViewEvent(false));
        EventBus.getDefault().post(new RefreshingViewEvent(false));
        ToastUtil.showToast(e.getMessage());
        e.printStackTrace();
    }


}
