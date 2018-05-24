/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.suspend;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.tools.IntentUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SuspendContentActivity extends BaseActivity<SuspendContentPresenter.IView, SuspendContentPresenter>
        implements SuspendContentPresenter.IView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.suspend_content_recycler)
    RecyclerView suspendContentRecycler;
    private SuspendContentAdapter mAdapter;

    @Override
    protected SuspendContentPresenter createPresenter() {
        return new SuspendContentPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_suspend_content;
    }

    @Override
    public void initView() {
        mAdapter = new SuspendContentAdapter(R.layout.item_suspend_content);
        suspendContentRecycler.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        suspendContentRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        presenter.getList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        IntentUtil.sharePic(mAdapter.getData().get(position).getPath(), this);
    }


    @OnClick(R.id.close_iv)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void setList(List<LocalExpressionItem> localExpressionItems) {
        mAdapter.addData(localExpressionItems);
    }
}