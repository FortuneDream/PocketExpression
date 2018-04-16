package com.dell.fortune.pocketexpression.module.user;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.config.StrConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.util.common.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCollectionActivity extends BaseActivity<UserCollectionPresenter.IView, UserCollectionPresenter>
        implements UserCollectionPresenter.IView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemLongClickListener {
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
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemLongClickListener(this);
        presenter.getList();
    }

    @Override
    public void setList(List<ExpressionItem> list) {
        if (list != null && list.size() > 0) {
            mAdapter.addData(list);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getList();
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        presenter.shareImage(mAdapter.getData().get(position));
        return false;
    }
}