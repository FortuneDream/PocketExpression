package com.dell.fortune.pocketexpression.module.home.recommend;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseFragment;

/**
 * Created by 81256 on 2018/3/18.
 */

public class HomeRecommendFragment extends BaseFragment<HomeRecommendPresenter.IView, HomeRecommendPresenter>
        implements HomeRecommendPresenter.IView {
    @Override
    public int setContentResource() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    public void initView() {

    }


    @Override
    protected HomeRecommendPresenter createPresenter() {
        return new HomeRecommendPresenter(this);
    }
}
