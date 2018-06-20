/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.common;

import android.support.v4.app.FragmentManager;

/**
 * Created by 81256 on 2018/3/18.
 */

public interface IBaseMutiView extends IBaseView {
    int getContentResId();
    FragmentManager getFm();
    void onSelectTabResult(int curIndex, int nextIndex);
}
