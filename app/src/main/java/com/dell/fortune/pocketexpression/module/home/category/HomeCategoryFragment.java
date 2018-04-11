package com.dell.fortune.pocketexpression.module.home.category;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseFragment;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.util.common.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 81256 on 2018/3/18.
 */
//种类界面只使用刷新的方式推荐
public class HomeCategoryFragment extends BaseFragment<HomeCategoryPresenter.IView, HomeCategoryPresenter>
        implements HomeCategoryPresenter.IView, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.refresh_fab)
    FloatingActionButton refreshFab;
    private HomeCategoryAdapter mAdapter;


    @Override
    public int setContentResource() {
        return R.layout.fragment_home_category;
    }

    @Override
    public void initView() {
        mAdapter = new HomeCategoryAdapter(R.layout.item_category);
        initRecycler(recyclerView, mAdapter);
        mAdapter.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    protected HomeCategoryPresenter createPresenter() {
        return new HomeCategoryPresenter(this);
    }

    @Override
    public void setList(List<ExpressionCategory> list) {
        swipeRefreshLayout.setRefreshing(false);
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        } else {
            //已经到头了
            presenter.setPage(-1);
            onRefresh();
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        presenter.enterCategoryListActivity(adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getRefreshList();
    }

    @OnClick(R.id.refresh_fab)
    public void onViewClicked() {
        onRefresh();
    }
}
