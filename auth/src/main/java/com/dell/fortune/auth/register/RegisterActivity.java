/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.auth.register;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dell.fortune.core.common.BaseActivity;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.tools.view.TextEdit;


/**
 * Created by 鹏君 on 2016/11/14.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter.IView, RegisterPresenter>
        implements RegisterPresenter.IView, View.OnClickListener {

    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private TextEdit mAccountTet;
    private TextEdit mNickNameTet;
    private TextEdit mPasswordTet;
    private TextEdit mConfirmPasswordTet;
    /**
     * 确定
     */
    private TextView mOkTxt;

    @Override
    public int setContentResource() {
        return R.layout.auth_activity_register;
    }

    @Override
    public void initView() {
        initToolbar(mToolbar, "用户注册");

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ok_txt) {
            String account = mAccountTet.getInputString();
            String password = mPasswordTet.getInputString();
            String confirmPassword = mConfirmPasswordTet.getInputString();
            String nickName = mNickNameTet.getInputString();
            presenter.register(account, password, confirmPassword, nickName);

        }
    }


    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void findViewSetListener() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar);
        mAccountTet = (TextEdit) findViewById(R.id.account_tet);
        mNickNameTet = (TextEdit) findViewById(R.id.nick_name_tet);
        mPasswordTet = (TextEdit) findViewById(R.id.password_tet);
        mConfirmPasswordTet = (TextEdit) findViewById(R.id.confirm_password_tet);
        mOkTxt = (TextView) findViewById(R.id.ok_txt);
        mOkTxt.setOnClickListener(this);
    }
}