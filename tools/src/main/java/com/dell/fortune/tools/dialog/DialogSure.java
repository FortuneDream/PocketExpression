package com.dell.fortune.tools.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dell.fortune.tools.R;

//确认弹出框
public class DialogSure extends BaseDialog {
    private ImageView mIvLogo;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvSure;


    public DialogSure(@NonNull Context context) {
        super(context);
        initView();
    }

    public DialogSure(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DialogSure(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DialogSure(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public void setSureListener(View.OnClickListener onClickListener) {
        mTvSure.setOnClickListener(onClickListener);
    }

    public void setContent(String content){
        mTvContent.setText(content);
    }

    public ImageView getIvLogo() {
        return mIvLogo;
    }

    public void setIvLogo(ImageView mIvLogo) {
        this.mIvLogo = mIvLogo;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public void setTvTitle(TextView mTvTitle) {
        this.mTvTitle = mTvTitle;
    }

    public TextView getTvContent() {
        return mTvContent;
    }

    public void setTvContent(TextView mTvContent) {
        this.mTvContent = mTvContent;
    }

    public TextView getTvSure() {
        return mTvSure;
    }

    public void setTvSure(TextView mTvSure) {
        this.mTvSure = mTvSure;
    }

    //点击链接
    public void setContentUrl(String url){
        mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
        mTvContent.setText(url);
    }

    private void initView() {
        View dialogView = View.inflate(getContext(), R.layout.dialog_sure, null);
        mTvSure = dialogView.findViewById(R.id.tv_sure);
        mTvTitle = dialogView.findViewById(R.id.tv_title);
        mTvTitle.setTextIsSelectable(true);
        mTvContent = dialogView.findViewById(R.id.tv_content);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());//也可以支持对TextView的内容滑动，但对Textview内容不支持长按文字可以复制，搜索
        mTvContent.setTextIsSelectable(true);//可以支持长按文字可以复制，搜索等，而且支持对TextView的内容滑动
        mIvLogo = dialogView.findViewById(R.id.iv_logo);
        setContentView(dialogView);
    }
}
