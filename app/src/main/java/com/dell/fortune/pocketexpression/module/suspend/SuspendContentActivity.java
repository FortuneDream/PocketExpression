/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.suspend;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.core.common.BaseActivity;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.tools.IntentUtil;

import java.util.List;


public class SuspendContentActivity extends BaseActivity<SuspendContentPresenter.IView, SuspendContentPresenter>
        implements SuspendContentPresenter.IView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private SuspendContentAdapter mAdapter;
    private ImageView mCloseIv;
    private RecyclerView mSuspendContentRecycler;

    @Override
    protected SuspendContentPresenter createPresenter() {
        return new SuspendContentPresenter(this);
    }

    @Override
    protected void findViewSetListener() {
        mCloseIv = (ImageView) findViewById(R.id.close_iv);
        mCloseIv.setOnClickListener(this);
        mSuspendContentRecycler = (RecyclerView) findViewById(R.id.suspend_content_recycler);

    }

    @Override
    public int setContentResource() {
        return R.layout.app_activity_suspend_content;
    }

    @Override
    public void initView() {
        mAdapter = new SuspendContentAdapter(R.layout.app_item_suspend_content);
        mSuspendContentRecycler.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        mSuspendContentRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        presenter.getList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        IntentUtil.sharePic(mContext, mAdapter.getData().get(position).getPath());
    }

    @Override
    public void setList(List<LocalExpressionItem> localExpressionItems) {
        mAdapter.addData(localExpressionItems);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.close_iv) {
            finish();
        }
    }
}