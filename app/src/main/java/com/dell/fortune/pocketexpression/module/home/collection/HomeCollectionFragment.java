/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.collection;

import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.core.common.BaseFragment;
import com.dell.fortune.pocketexpression.app.R;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.tools.toast.ToastUtil;

import java.util.List;


public class HomeCollectionFragment extends BaseFragment<HomeCollectionPresenter.IView, HomeCollectionPresenter>
        implements HomeCollectionPresenter.IView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    private HomeCollectionAdapter mAdapter;
    private View view;
    /**
     * 还有部分表情包没有同步哦~
     */
    private TextView mSynHeadTv;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mRefreshFab;

    @Override
    protected HomeCollectionPresenter createPresenter() {
        return new HomeCollectionPresenter(this);
    }

    @Override
    protected void findViewSetListener(View root) {
        mSynHeadTv = (TextView) view.findViewById(R.id.syn_head_tv);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRefreshFab = (FloatingActionButton) view.findViewById(R.id.refresh_fab);
        mRefreshFab.setOnClickListener(this);
    }

    @Override
    public int setContentResource() {
        return R.layout.app_fragment_home_user_collection;
    }

    @Override
    public void initView() {
        mAdapter = new HomeCollectionAdapter(R.layout.app_item_user_collection);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
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
            mSynHeadTv.setVisibility(View.VISIBLE);
            mSynHeadTv.setText("还有 " + unSaveSize + " 条未同步");
        } else {
            mSynHeadTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        presenter.shareImage(mAdapter.getData().get(position));
    }

    //选装动画
    private void refreshFabAnim() {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mRefreshFab, "rotation", 0f, 360f);
        rotationAnimator.setDuration(1000);
        rotationAnimator.start();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.refresh_fab) {
            refreshFabAnim();//动画
            presenter.synLocal();
            ToastUtil.showToast("正在同步到本地收藏");
        }
    }
}
