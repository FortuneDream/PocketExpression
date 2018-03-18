package com.dell.fortune.pocketexpression.module.home;

import android.support.v4.app.Fragment;

import com.dell.fortune.pocketexpression.common.BaseMutiPresenter;
import com.dell.fortune.pocketexpression.common.IBaseMutiView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 81256 on 2018/3/17.
 */

public class HomePresenter extends BaseMutiPresenter<HomePresenter.IView> {
    private HomeCategoryFragment homeCategoryFragment;

    public HomePresenter(IView view) {
        super(view);
    }

    @Override
    public List<Fragment> initFragment() {
        List<Fragment> list = new ArrayList<>();
        homeCategoryFragment = new HomeCategoryFragment();
        list.add(homeCategoryFragment);
        return list;
    }


    public interface IView extends IBaseMutiView {

    }
}
