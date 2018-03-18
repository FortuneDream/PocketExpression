package com.dell.fortune.pocketexpression.common;

import android.support.v4.app.FragmentManager;

/**
 * Created by 81256 on 2018/3/18.
 */

public interface IBaseMutiView extends IBaseView {
    int getContentResId();
    FragmentManager getFm();
    void onSelectTabResult(int curIndex,int nextIndex);
}
