/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.auth.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.dell.fortune.auth.register.RegisterActivity;
import com.dell.fortune.core.callback.ToastSaveListener;
import com.dell.fortune.core.common.BaseActivity;
import com.dell.fortune.core.common.BasePresenter;
import com.dell.fortune.core.common.IBaseView;
import com.dell.fortune.core.config.FlagConstant;
import com.dell.fortune.core.model.bean.MyUser;
import com.dell.fortune.tools.toast.ToastUtil;

import cn.bmob.v3.exception.BmobException;


/**
 * Created by 鹏君 on 2016/11/14.
 */

public class LoginPresenter extends BasePresenter<LoginPresenter.IView> {
    public LoginPresenter(IView view) {
        super(view);
    }

    //登录
    public void login(final String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast("完整信息");
            return;
        }
        mView.showLoading(true);
        final MyUser user = new MyUser();
        user.setUsername(account);
        user.setPassword(password);
        user.login(new ToastSaveListener<MyUser>() {

            @Override
            public void onSuccess(MyUser user) {
                ToastUtil.showToast("欢迎尊贵的VIP！ ");
                mView.loginToResult(BaseActivity.RESULT_OK,user);
                mView.finish();//关闭登录界面
            }

            @Override
            public void onFail(MyUser user, BmobException e) {
                super.onFail(user, e);
                mView.loginToResult(BaseActivity.RESULT_CANCELED, null);
            }
        });

    }

    //跳转到RegistererActivity，如果注册成功就不finish到此页
    public void enterRegisterActivity() {
        Intent intent = new Intent(mView.getCurrentContext(), RegisterActivity.class);
        ((Activity) mView.getCurrentContext()).startActivityForResult(intent, FlagConstant.REQUEST_REGISTER);
    }


    public interface IView extends IBaseView {
        void finish();

        void loginToResult(Integer success, MyUser myUser);
    }
}
