package com.dell.fortune.pocketexpression.module.home.category;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseFragment;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 81256 on 2018/3/18.
 */

public class HomeCategoryFragment extends BaseFragment<HomeCategoryPresenter.IView, HomeCategoryPresenter>
        implements HomeCategoryPresenter.IView, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private HomeCategoryAdapter adapter;


    @Override
    public int setContentResource() {
        return R.layout.fragment_home_category;
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getCurrentContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeCategoryAdapter(R.layout.item_category);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnLoadMoreListener(this, recyclerView);
        presenter.getList(true);
    }


    @Override
    protected HomeCategoryPresenter createPresenter() {
        return new HomeCategoryPresenter(this);
    }

    @Override
    public void setList(boolean isRefreshing, List<ExpressionCategory> list) {
        if (list == null) {
            adapter.loadMoreEnd();
            Log.e("TAG","jjjj");
        } else {
            adapter.addData(list);
            adapter.loadMoreComplete();
            Log.e("TAG",String.valueOf(list.size()));
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        presenter.enterCategoryListActivity(adapter.getItem(position));
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getList(false);
    }
}
