package com.dell.fortune.pocketexpression.module.home.category.list;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.config.IntentConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import java.util.List;

import butterknife.BindView;

public class CategoryListActivity extends BaseActivity<CategoryListPresenter.IView, CategoryListPresenter>
        implements CategoryListPresenter.IView, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private CategoryListAdapter adapter;

    @Override
    protected CategoryListPresenter createPresenter() {
        return new CategoryListPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_category_list;
    }

    @Override
    public void initView() {
        ExpressionCategory category = (ExpressionCategory) getIntent().getSerializableExtra(IntentConstant.EXTRA_CATEGORY_LIST_ITEM);
        initToolbar(toolbar, category.getName());
        initRecycler(recyclerView, adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);

    }


    public static Intent buildIntent(Context context, ExpressionCategory category) {
        Intent intent = new Intent(context, CategoryListActivity.class);
        intent.putExtra(IntentConstant.EXTRA_CATEGORY_LIST_ITEM, category);
        return intent;
    }


    @Override
    public void setList(boolean isRefreshing, List<ExpressionItem> list) {
        if (list == null) {
            adapter.loadMoreEnd();
        } else {
            adapter.addData(list);
            adapter.loadMoreComplete();
        }

    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getList(false);
    }
}