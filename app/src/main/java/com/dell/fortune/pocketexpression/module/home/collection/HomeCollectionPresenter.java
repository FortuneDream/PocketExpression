/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.collection;


import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.model.callback.OnCheckCollectionListener;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.tools.IntentUtil;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomeCollectionPresenter extends BasePresenter<HomeCollectionPresenter.IView> {
    private CollectionModel collectionModel;

    public HomeCollectionPresenter(HomeCollectionPresenter.IView view) {
        super(view);
        collectionModel = new CollectionModel();
    }

    public void getList() {
        collectionModel.getLocalList(new Consumer<List<LocalExpressionItem>>() {
            @Override
            public void accept(List<LocalExpressionItem> localExpressionItems) throws Exception {
                mView.setList(localExpressionItems);
            }
        });
    }


    public void shareImage(LocalExpressionItem item) {
        IntentUtil.sharePic(mContext, item.getPath());
    }

    public void synLocal() {
        mView.showLoading(true);
        collectionModel.synLocal();
    }

    public void checkLocalSynchronize() {
        collectionModel.checkSyn(new OnCheckCollectionListener() {
            @Override
            public void onCheckResult(boolean isSynSuccess, List<ExpressionItem> unSaveItems) {
                mView.showSynHead(!isSynSuccess, unSaveItems.size());
            }
        });
    }

    interface IView extends IBaseView {

        void setList(List<LocalExpressionItem> list);

        void showSynHead(boolean isShow, int unSaveSize);
    }
}
