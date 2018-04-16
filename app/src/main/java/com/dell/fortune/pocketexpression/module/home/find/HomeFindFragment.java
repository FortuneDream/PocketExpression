package com.dell.fortune.pocketexpression.module.home.find;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFindFragment extends BaseFragment<HomeFindPresenter.IView, HomeFindPresenter>
        implements HomeFindPresenter.IView, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.find_recycler)
    RecyclerView findRecycler;
    @BindView(R.id.upload_fab)
    FloatingActionButton uploadFab;
    private HomeFindAdapter mAdapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_find;
    }

    @Override
    public void initView() {
        mAdapter = new HomeFindAdapter(R.layout.item_home_find);
        initRecycler(findRecycler,mAdapter);
        mAdapter.setOnLoadMoreListener(this);
    }


    @Override
    protected HomeFindPresenter createPresenter() {
        return new HomeFindPresenter(this);
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
