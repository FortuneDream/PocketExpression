/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.suspend;


import com.dell.fortune.core.common.BasePresenter;
import com.dell.fortune.core.common.IBaseView;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionDaoOpe;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.util.common.RxApi;
import com.dell.fortune.tools.LogUtils;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.functions.Consumer;

public class SuspendContentPresenter extends BasePresenter<SuspendContentPresenter.IView> {
    private LocalExpressionDaoOpe localExpressionDaoOpe;

    public SuspendContentPresenter(IView view) {
        super(view);
        localExpressionDaoOpe = new LocalExpressionDaoOpe();
    }

    public void getList() {
        RxApi.create(new Callable<List<LocalExpressionItem>>() {
            @Override
            public List<LocalExpressionItem> call() throws Exception {
                return localExpressionDaoOpe.findAll();
            }
        }).subscribe(new Consumer<List<LocalExpressionItem>>() {
            @Override
            public void accept(List<LocalExpressionItem> localExpressionItems) throws Exception {
                if (localExpressionItems != null) {
                    LogUtils.e("item.size : ", String.valueOf(localExpressionItems.size()));
                }
                mView.setList(localExpressionItems);
            }
        });
    }

    interface IView extends IBaseView {

        void setList(List<LocalExpressionItem> localExpressionItems);
    }
}
