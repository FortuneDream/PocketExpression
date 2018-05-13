package com.dell.fortune.pocketexpression.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dell.fortune.pocketexpression.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 鹏君 on 2017/1/16.
 */

public abstract class BaseFragment<V extends IBaseView, T extends BasePresenter<V>> extends Fragment implements IBaseView {
    protected T presenter;
    public static AlertDialog mLoadingDialog;
    public Context mContext;
    public final String TAG = this.getClass().getName();
    Unbinder unbinder;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
        presenter = createPresenter();
        presenter.attachView((V)this);
        if (mLoadingDialog == null) {
            mLoadingDialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .create();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setContentResource(), container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
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

    public void showLoading(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    public void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    public void initRecycler(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mContext,LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDialog(LoadingDialogEvent event){
        showLoading(event.isShow());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        isViewValid = false;
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewValid = false;
        presenter.detachView();
    }

}
