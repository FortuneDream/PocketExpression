package com.dell.fortune.pocketexpression.util.common.view;

import android.view.LayoutInflater;
import android.view.View;

//暴露接口自定义布局
public interface ViewFlipperRollAdapter {

    int getCount();

    View getView(int i, ViewFlipperRollView flipperRollView, LayoutInflater inflater);
}
