package com.dell.fortune.pocketexpression.module.home;


import android.support.v4.app.Fragment;

import com.dell.fortune.pocketexpression.common.BaseMutiPresenter;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseMutiView;
import com.dell.fortune.pocketexpression.common.IBaseView;

import java.util.List;

/**
 * Created by 81256 on 2018/3/18.
 */

public class HomeCategoryPresenter extends BasePresenter<HomeCategoryPresenter.IView> {
    public HomeCategoryPresenter(IView view) {
        super(view);
    }



    public interface IView extends IBaseView{

    }
}
