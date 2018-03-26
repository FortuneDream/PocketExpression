package com.dell.fortune.pocketexpression.module.home.recommend;


import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;

public class HomeRecommendPresenter extends BasePresenter<HomeRecommendPresenter.IView> {


    public HomeRecommendPresenter(IView view) {
        super(view);
    }

    interface IView extends IBaseView {

    }
}
