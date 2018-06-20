/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.category;

import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.core.common.BaseFragment;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.tools.toast.ToastUtil;

import java.util.List;

/**
 * Created by 81256 on 2018/3/18.
 */
//种类界面只使用刷新的方式推荐
public class HomeCategoryFragment extends BaseFragment<HomeCategoryPresenter.IView, HomeCategoryPresenter>
        implements HomeCategoryPresenter.IView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private HomeCategoryAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mRefreshFab;


    @Override
    public int setContentResource() {
        return R.layout.app_fragment_home_category;
    }

    @Override
    public void initView() {
        mAdapter = new HomeCategoryAdapter(R.layout.app_item_category);
        initRecycler(mRecyclerView, mAdapter);
        mAdapter.setOnItemClickListener(this);
        presenter.getRefreshList();
    }

    @Override
    protected HomeCategoryPresenter createPresenter() {
        return new HomeCategoryPresenter(this);
    }

    @Override
    protected void findViewSetListener(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRefreshFab = (FloatingActionButton) view.findViewById(R.id.refresh_fab);
        mRefreshFab.setOnClickListener(this);
    }

    @Override
    public void setList(List<ExpressionCategory> list) {
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        } else {
            //已经到头了
            presenter.setPage(-1);
            presenter.getRefreshList();
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        presenter.enterCategoryListActivity(adapter.getItem(position));
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
            ToastUtil.showToast("正在加载下一波表情包");
            presenter.getRefreshList();
        }
    }
}
