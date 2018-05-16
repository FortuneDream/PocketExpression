package com.dell.fortune.pocketexpression.module.user;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCollectionActivity extends BaseActivity<UserCollectionPresenter.IView, UserCollectionPresenter>
        implements UserCollectionPresenter.IView, BaseQuickAdapter.OnItemLongClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_fab)
    FloatingActionButton refreshFab;
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
        initToolbar(toolbar, "本地收藏");
        mAdapter.setOnItemLongClickListener(this);
        presenter.getList();
    }

    @Override
    public void setList(List<LocalExpressionItem> list) {
        if (list != null && list.size() > 0) {
            mAdapter.addData(list);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        presenter.shareImage(mAdapter.getData().get(position));
        return false;
    }


    @OnClick(R.id.refresh_fab)
    public void onViewClicked() {
        presenter.synLocal();
    }
}