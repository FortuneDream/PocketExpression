package com.dell.fortune.tools.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dell.fortune.tools.R;


//编辑框
public class DialogEditSureCancel extends BaseDialog {
    private ImageView mIvLogo;
    private TextView mTvSure;
    private TextView mTvCancel;
    private EditText editText;
    private TextView mTvTitle;

    public ImageView getIvLogo() {
        return mIvLogo;
    }

    public void setIvLogo(ImageView mIvLogo) {
        this.mIvLogo = mIvLogo;
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

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public void setTvTitle(TextView mTvTitle) {
        this.mTvTitle = mTvTitle;
    }

    public DialogEditSureCancel(@NonNull Context context) {
        super(context);
        initView();
    }

    public DialogEditSureCancel(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DialogEditSureCancel(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DialogEditSureCancel(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.tools_dialog_edittext_sure_false, null);
        mIvLogo = dialogView.findViewById(R.id.iv_logo);
        mTvTitle = dialogView.findViewById(R.id.tv_title);
        mTvSure = dialogView.findViewById(R.id.tv_sure);
        mTvCancel = dialogView.findViewById(R.id.tv_cancle);
        editText = dialogView.findViewById(R.id.editText);
        setContentView(dialogView);
    }
}
