package com.dell.fortune.pocketexpression.module.joy;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BarrageActivity extends BaseActivity<BarragePresenter.IView, BarragePresenter>
        implements BarragePresenter.IView {

    @BindView(R.id.add_text_fab)
    FloatingActionButton addTextFab;

    @Override
    protected BarragePresenter createPresenter() {
        return new BarragePresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_barrage;
    }

    @Override
    public void initView() {

    }


    @OnClick(R.id.add_text_fab)
    public void onViewClicked() {

    }
}