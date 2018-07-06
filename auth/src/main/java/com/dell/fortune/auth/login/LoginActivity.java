/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.auth.login;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.dell.fortune.core.common.BaseActivity;
import com.dell.fortune.core.config.FlagConstant;
import com.dell.fortune.core.model.bean.MyUser;
import com.dell.fortune.core.util.UserUtil;
import com.dell.fortune.pocketexpression.auth.R;
import com.dell.fortune.tools.toast.ToastUtil;
import com.dell.fortune.tools.view.TextEdit;


/**
 * Created by 鹏君 on 2016/11/14.
 */
@Route(path = "/auth/login")
public class LoginActivity extends BaseActivity<LoginPresenter.IView, LoginPresenter>
        implements LoginPresenter.IView, View.OnClickListener {
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private TextEdit mAccountTet;
    private TextEdit mPasswordTet;
    private ImageView mForgetPasswordIv;
    private TextView mOkTxt;
    private TextView mRegisterTxt;


    @Override
    public int setContentResource() {
        return R.layout.auth_activity_login;
    }

    @Override
    public void initView() {
        initToolbar(mToolbar, "用户登录");
    }



    //通过基类AuthActivity跳转来，登录成功后返回user，没有则返回null
    @Override
    public void loginToResult(Integer result, MyUser myUser) {
        Intent intent = new Intent();
        intent.putExtra(UserUtil.RESULT_USER, myUser);
        setResult(result, intent);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ok_txt) {
            String account = mAccountTet.getInputString();
            String password = mPasswordTet.getInputString();
            presenter.login(account, password);

        } else if (i == R.id.register_txt) {
            presenter.enterRegisterActivity();//请求码REQUEST_REGISTER

        } else if (i == R.id.forget_password_iv) {
            ToastUtil.showToast("忘记密码-->请在粉丝群内@鹏君");
        }
    }

    //跳转到RegistererActivity，如果注册成功就不finish到此页
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FlagConstant.REQUEST_REGISTER:
                    finish();
                    break;
            }
        }
    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void findViewSetListener() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar);
        mAccountTet = (TextEdit) findViewById(R.id.account_tet);
        mPasswordTet = (TextEdit) findViewById(R.id.password_tet);
        mForgetPasswordIv = (ImageView) findViewById(R.id.forget_password_iv);
        mOkTxt = (TextView) findViewById(R.id.ok_txt);
        mRegisterTxt = (TextView) findViewById(R.id.register_txt);
        mForgetPasswordIv.setOnClickListener(this);
        mOkTxt.setOnClickListener(this);
        mRegisterTxt.setOnClickListener(this);
    }
}