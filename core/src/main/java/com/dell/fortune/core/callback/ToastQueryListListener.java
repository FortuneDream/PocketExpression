/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.callback;

import com.dell.fortune.core.model.event.LoadingViewEvent;
import com.dell.fortune.tools.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */

public abstract class ToastQueryListListener<BatchResult> extends QueryListListener<BatchResult> {

    public ToastQueryListListener() {
    }

    public abstract void onSuccess(List<BatchResult> list);

    @Override
    final public void done(List<BatchResult> list, BmobException e) {
        if (e == null) {
            onSuccess(list);
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
