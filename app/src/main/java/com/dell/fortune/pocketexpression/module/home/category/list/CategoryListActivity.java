package com.dell.fortune.pocketexpression.module.home.category.list;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.config.IntentConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.tools.LogUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CategoryListActivity extends BaseActivity<CategoryListPresenter.IView, CategoryListPresenter>
        implements CategoryListPresenter.IView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemLongClickListener {

    @BindView(R.id.collection_all_btn)
    Button collectionAllBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private CategoryListAdapter mAdapter;
    public ExpressionCategory mCategory;

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
        mCategory = (ExpressionCategory) getIntent().getSerializableExtra(IntentConstant.EXTRA_CATEGORY_LIST_ITEM);
        mAdapter = new CategoryListAdapter(R.layout.item_category_list);
        initToolbar(toolbar, mCategory.getName());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemLongClickListener(this);
        presenter.getList(mCategory);
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

    //长按收藏
    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ExpressionItem item = mAdapter.getItem(position);
        presenter.collectionItem(item);
        return true;
    }


    @OnClick(R.id.collection_all_btn)
    public void onViewClicked() {
        presenter.collectionAllItem(mAdapter.getData());
    }


}