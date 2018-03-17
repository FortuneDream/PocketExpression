package com.dell.fortune.pocketexpression.module.home;

import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;

/**
 * Created by 81256 on 2018/3/17.
 */

public class HomePresenter extends BasePresenter<HomePresenter.IView> {
    private IView mView;

    public HomePresenter(IView mView){
        attachView(mView);
        this.mView=getIViewRef();
    }


    public interface IView extends IBaseView{

    }
}
