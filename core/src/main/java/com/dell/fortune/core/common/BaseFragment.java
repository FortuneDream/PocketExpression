/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.core.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 鹏君 on 2017/1/16.
 */

public abstract class BaseFragment<V extends IBaseView, T extends BasePresenter<V>> extends Fragment implements IBaseView {
    protected T presenter;
    public Context mContext;
    public final String TAG = this.getClass().getName();
    private boolean isViewValid;

    @Override
    public boolean isViewValid() {
        return isViewValid;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewValid = true;
    }


    protected abstract T createPresenter();
    protected abstract void findViewSetListener(View root);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
        presenter = createPresenter();
        presenter.attachView((V) this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setContentResource(), container, false);
        EventBus.getDefault().register(this);
        findViewSetListener(view);
        initView();
        return view;
    }


    @Override
    public Context getAppContext() {
        return getContext() != null ? getContext().getApplicationContext() : null;
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public void showLoading(boolean isShow) {
        showLoading(isShow, 0);
    }

    @Override
    public void showLoading(boolean isShow, int milliseconds) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.showLoading(isShow, milliseconds);
        }
    }

    public void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.core_colorAccent));
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.core_colorPrimary));
    }

    public void initRecycler(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDialog(LoadingDialogEvent event) {
        showLoading(event.isShow());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        isViewValid = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewValid = false;
        presenter.detachView();
    }

}
