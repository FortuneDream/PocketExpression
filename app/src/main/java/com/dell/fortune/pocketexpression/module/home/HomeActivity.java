package com.dell.fortune.pocketexpression.module.home;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;

public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView {
    @Override
    public int setContentResource() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public int getContentResId() {
        return R.id.home_content;
    }

    @Override
    public FragmentManager getFm() {
        return getSupportFragmentManager();
    }

    @Override
    public void onSelectTabResult(int curIndex, int nextIndex) {

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }
}
