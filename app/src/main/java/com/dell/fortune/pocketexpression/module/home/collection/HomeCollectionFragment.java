/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.collection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseFragment;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.util.common.UserUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeCollectionFragment extends BaseFragment<HomeCollectionPresenter.IView, HomeCollectionPresenter>
        implements HomeCollectionPresenter.IView, BaseQuickAdapter.OnItemLongClickListener {

    @BindView(R.id.syn_head_tv)
    TextView synHeadTv;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_fab)
    FloatingActionButton refreshFab;
    private HomeCollectionAdapter mAdapter;

    @Override
    protected HomeCollectionPresenter createPresenter() {
        return new HomeCollectionPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.fragment_home_user_collection;
    }

    @Override
    public void initView() {
        if (!UserUtil.checkLocalUser(true, this)) {
            return;
        }
        mAdapter = new HomeCollectionAdapter(R.layout.item_user_collection);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 5, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener(this);
        presenter.getList();
        presenter.checkLocalSynchronize();//是否与本地同步
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
    public void showSynHead(boolean isShow, int unSaveSize) {
        if (isShow) {
            synHeadTv.setVisibility(View.VISIBLE);
            synHeadTv.setText("还有 " + unSaveSize + " 条未同步");
        } else {
            synHeadTv.setVisibility(View.GONE);
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
