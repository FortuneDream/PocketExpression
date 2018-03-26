package com.dell.fortune.pocketexpression.module;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView {
    @BindView(R.id.open_suspend_window_btn)
    Button openSuspendWindowBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_content)
    FrameLayout homeContent;

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

    @OnClick(R.id.open_suspend_window_btn)
    public void onViewClicked() {
        presenter.openSuspendWindows();
    }
}
