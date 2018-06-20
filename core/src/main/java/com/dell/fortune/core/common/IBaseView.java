/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.common;

/**
 * Created by 鹏君 on 2017/1/28.
 */

public interface IBaseView extends IBase {
    void showLoading(boolean isShow);

    void showLoading(boolean isShow, int milliseconds);

    void finish();

    int setContentResource();

    void initView();

    boolean isViewValid();
}
