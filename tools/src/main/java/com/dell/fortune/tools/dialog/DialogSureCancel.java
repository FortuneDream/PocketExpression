package com.dell.fortune.tools.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dell.fortune.tools.R;


public class DialogSureCancel extends BaseDialog {

    private ImageView mIvLogo;
    private TextView mTvContent;
    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvTitle;

    public ImageView getIvLogo() {
        return mIvLogo;
    }

    public void setIvLogo(ImageView mIvLogo) {
        this.mIvLogo = mIvLogo;
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

    public TextView getTvCancel() {
        return mTvCancel;
    }

    public void setTvCancel(TextView mTvCancel) {
        this.mTvCancel = mTvCancel;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public void setTvTitle(TextView mTvTitle) {
        this.mTvTitle = mTvTitle;
    }

    public DialogSureCancel(@NonNull Context context) {
        super(context);
        initView();
    }

    public DialogSureCancel(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DialogSureCancel(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DialogSureCancel(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public void setSureListener(View.OnClickListener sureListener) {
        mTvSure.setOnClickListener(sureListener);
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        mTvCancel.setOnClickListener(cancelListener);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.tools_dialog_sure_false, null);
        mIvLogo = dialogView.findViewById(R.id.iv_logo);
        mTvSure = dialogView.findViewById(R.id.tv_sure);
        mTvCancel = dialogView.findViewById(R.id.tv_cancel);
        mTvContent = dialogView.findViewById(R.id.tv_content);
        mTvContent.setTextIsSelectable(true);
        mTvTitle = dialogView.findViewById(R.id.tv_title);
        setContentView(dialogView);
    }
}
