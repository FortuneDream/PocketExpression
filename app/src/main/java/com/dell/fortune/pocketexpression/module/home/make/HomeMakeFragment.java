package com.dell.fortune.pocketexpression.module.home.make;

import android.support.v4.app.FragmentManager;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseFragment;


public class HomeMakeFragment extends BaseFragment<HomeMakePresenter.IView, HomeMakePresenter>
        implements HomeMakePresenter.IView {
    @Override
    public int setContentResource() {
        return R.layout.fragment_home_make;
    }

    @Override
    public void initView() {

    }


    @Override
    protected HomeMakePresenter createPresenter() {
        return new HomeMakePresenter(this);
    }
}
