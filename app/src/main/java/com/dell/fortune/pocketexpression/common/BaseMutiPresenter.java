package com.dell.fortune.pocketexpression.common;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by 81256 on 2018/3/18.
 */

public abstract class BaseMutiPresenter<V extends IBaseMutiView> extends BasePresenter<V> {
    private FragmentManager fm;
    private List<Fragment> fragments;

    public abstract List<Fragment> initFragment();

    private Fragment totalFragment;
    private int mCurIndex = -1;//标记当前Fragment

    public BaseMutiPresenter(V mView) {
        super(mView);
        fragments = initFragment();
        fm = mView.getFm();
    }

    public void clickBottomTab(int index) {
        if (mCurIndex != index) {
            showFragment(fragments.get(index));
            mView.onSelectTabResult(mCurIndex, index);
            mCurIndex = index;
        }
    }


    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            if (totalFragment == null) {
                fm.beginTransaction().add(mView.getContentResId(), fragment, fragment.getClass().getName()).commit();
            } else {
                fm.beginTransaction().hide(totalFragment).add(mView.getContentResId(), fragment, fragment.getClass().getName()).commit();
            }
        } else {
            fm.beginTransaction().hide(totalFragment).show(fragment).commit();
        }
        totalFragment = fragment;
    }
}
