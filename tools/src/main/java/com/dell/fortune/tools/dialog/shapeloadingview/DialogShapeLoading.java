/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.tools.dialog.shapeloadingview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.dell.fortune.tools.R;
import com.dell.fortune.tools.dialog.BaseDialog;

public class DialogShapeLoading extends BaseDialog{

        private ShapeLoadingView mLoadingView;
        private View mDialogContentView;

        public DialogShapeLoading(Context context, int themeResId) {
            super(context, themeResId);
            initView(context);
        }

        public DialogShapeLoading(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
            initView(context);
        }

        public DialogShapeLoading(Context context) {
            super(context);
            initView(context);
        }

        public DialogShapeLoading(Activity context) {
            super(context);
            initView(context);
        }

        public DialogShapeLoading(Context context, float alpha, int gravity) {
            super(context, alpha, gravity);
            initView(context);
        }

        private void initView(Context context) {
            mDialogContentView = LayoutInflater.from(context).inflate(R.layout.tools_dialog_shape_loading_view, null);
            mLoadingView = mDialogContentView.findViewById(R.id.loadView);
            setContentView(mDialogContentView);
        }

        public void cancel(RxCancelType code, String str) {
            cancel();
        }

        public void setLoadingText(CharSequence charSequence) {
            mLoadingView.setLoadingText(charSequence);
        }

        public ShapeLoadingView getLoadingView() {
            return mLoadingView;
        }

        public View getDialogContentView() {
            return mDialogContentView;
        }

        public enum RxCancelType {normal, error, success, info}

}
