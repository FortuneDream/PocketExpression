/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.category.list;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.core.common.BaseActivity;
import com.dell.fortune.core.config.IntentConstant;
import com.dell.fortune.pocketexpression.app.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.tools.LogUtils;

import java.util.List;


public class CategoryListActivity extends BaseActivity<CategoryListPresenter.IView, CategoryListPresenter>
        implements CategoryListPresenter.IView, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private CategoryListAdapter mAdapter;
    public ExpressionCategory mCategory;
    /**
     * 收藏所有表情包
     */
    private Button mCollectionAllBtn;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    @Override
    protected CategoryListPresenter createPresenter() {
        return new CategoryListPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.app_activity_category_list;
    }

    @Override
    public void initView() {
        mCategory = (ExpressionCategory) getIntent().getSerializableExtra(IntentConstant.EXTRA_CATEGORY_LIST_ITEM);
        mAdapter = new CategoryListAdapter(R.layout.app_item_category_list);
        initToolbar(mToolbar, mCategory.getName());
        initRecycler(mRecyclerView, mAdapter);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.bindToRecyclerView(mRecyclerView);//绑定
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("position", String.valueOf(position));
                int i = view.getId();
                if (i == R.id.collection_fab) {
                    presenter.collectionItem(mAdapter.getItem(position));

                } else if (i == R.id.share_fab) {
                    presenter.shareItem(mAdapter.getItem(position));

                } else if (i == R.id.pic_sdv) {
                    View fabLl = mAdapter.getViewByPosition(position, R.id.fab_ll);
                    fabLl.setVisibility(View.VISIBLE);

                }
            }
        });
        presenter.getList(mCategory);

    }

    @Override
    protected void findViewSetListener() {
        mCollectionAllBtn = (Button) findViewById(R.id.collection_all_btn);
        mCollectionAllBtn.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    public static Intent buildIntent(Context context, ExpressionCategory category) {
        Intent intent = new Intent(context, CategoryListActivity.class);
        intent.putExtra(IntentConstant.EXTRA_CATEGORY_LIST_ITEM, category);
        return intent;
    }


    @Override
    public void setList(List<ExpressionItem> list) {
        if (list == null || list.size() == 0) {
            LogUtils.e("loading", "继续加载");
            mAdapter.loadMoreEnd();
        } else {
            LogUtils.e("loading", "加载结束");
            mAdapter.addData(list);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getList(mCategory);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.collection_all_btn) {
            presenter.collectionAllItem(mAdapter.getData());

        } else {
        }
    }
}