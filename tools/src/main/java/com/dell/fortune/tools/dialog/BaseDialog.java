package com.dell.fortune.tools.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dell.fortune.tools.R;


public class BaseDialog extends Dialog {
    protected WindowManager.LayoutParams mLayoutParams;

    public BaseDialog(@NonNull Context context) {
        super(context);
        initView();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        mLayoutParams = getWindow().getAttributes();
        mLayoutParams.alpha = 1f;
        getWindow().setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
    }

    public BaseDialog(Context context, float alpha, int gravity) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        mLayoutParams = getWindow().getAttributes();
        mLayoutParams.alpha = alpha;
        getWindow().setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = gravity;
        }
    }

    //隐藏头部导航栏状态栏
    public void skipTools() {
        if (Build.VERSION.SDK_INT<19){
            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void setFullScreen(){
        getWindow().getDecorView().setPadding(0,0,0,0);
        mLayoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height=WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(mLayoutParams);
    }

    public void setFullScreenWidth(){
        getWindow().getDecorView().setPadding(0,0,0,0);
        mLayoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(mLayoutParams);
    }

    public void setFullScreenHeight(){
        getWindow().getDecorView().setPadding(0,0,0,0);
        mLayoutParams.width=WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height=WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(mLayoutParams);
    }

    //最顶层Window
    public void setOnWhole(){
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }
}
