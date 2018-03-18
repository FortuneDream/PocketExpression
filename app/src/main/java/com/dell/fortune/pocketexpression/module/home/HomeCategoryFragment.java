package com.dell.fortune.pocketexpression.module.home;

import android.support.v4.app.FragmentManager;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseFragment;

/**
 * Created by 81256 on 2018/3/18.
 */

public class HomeCategoryFragment extends BaseFragment<HomeCategoryPresenter.IView, HomeCategoryPresenter>
        implements HomeCategoryPresenter.IView {
    @Override
    public int setContentResource() {
        return R.layout.fragment_home_category;
    }

    @Override
    public void initView() {

    }


    @Override
    protected HomeCategoryPresenter createPresenter() {
        return new HomeCategoryPresenter(this);
    }
}
