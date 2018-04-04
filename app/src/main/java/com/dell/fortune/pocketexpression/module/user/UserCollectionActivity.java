package com.dell.fortune.pocketexpression.module.user;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.config.StrConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCollectionActivity extends BaseActivity<UserCollectionPresenter.IView, UserCollectionPresenter>
        implements UserCollectionPresenter.IView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private UserCollectionAdapter mAdapter;

    @Override
    protected UserCollectionPresenter createPresenter() {
        return new UserCollectionPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_user_collection;
    }

    @Override
    public void initView() {
        mAdapter = new UserCollectionAdapter(R.layout.item_user_collection);
        initRecycler(recyclerView, mAdapter);
        initToolbar(toolbar, "我的收藏");
        presenter.getList(true);
    }

    @Override
    public void setList(boolean isRefreshing, List<ExpressionItem> list) {
        if (list == null) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.addData(list);
            mAdapter.loadMoreEnd();
        }
    }
}