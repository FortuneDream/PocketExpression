package com.dell.fortune.pocketexpression.module.auth.login;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.config.FlagConstant;
import com.dell.fortune.pocketexpression.model.bean.MyUser;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.pocketexpression.util.common.UserUtil;
import com.dell.fortune.pocketexpression.util.common.view.TextEdit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class LoginActivity extends BaseActivity<LoginPresenter.IView, LoginPresenter>
        implements LoginPresenter.IView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.account_tet)
    TextEdit accountTet;
    @BindView(R.id.password_tet)
    TextEdit passwordTet;
    @BindView(R.id.forget_password_iv)
    ImageView forgetPasswordIv;
    @BindView(R.id.ok_txt)
    TextView okTxt;
    @BindView(R.id.register_txt)
    TextView registerTxt;
    @BindView(R.id.login_by_pocket_tv)
    TextView loginByPocketTv;

    @Override
    public int setContentResource() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        initToolbar(toolbar, "用户登录");
    }


    //通过基类AuthActivity跳转来，登录成功后返回user，没有则返回null
    @Override
    public void loginToResult(Integer result, MyUser myUser) {
        Intent intent = new Intent();
        intent.putExtra(UserUtil.RESULT_USER, myUser);
        setResult(result, intent);
    }


    @OnClick({R.id.ok_txt, R.id.register_txt, R.id.forget_password_iv, R.id.login_by_pocket_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_txt://点击确定
                String account = accountTet.getInputString();
                String password = passwordTet.getInputString();
                presenter.login(account, password);
                break;
            case R.id.register_txt://跳转到注册
                presenter.enterRegisterActivity();//请求码REQUEST_REGISTER
                break;
            case R.id.forget_password_iv:
                ToastUtil.showToast("忘记密码-->请在粉丝群内@鹏君");
                break;
            case R.id.login_by_pocket_tv:
                ToastUtil.showToast("应用间信息共享ContentProvider");
                break;
        }
    }

    //跳转到RegistererActivity，如果注册成功就不finish到此页
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
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

}