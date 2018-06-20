/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.collection;


import com.dell.fortune.core.common.BasePresenter;
import com.dell.fortune.core.common.IBaseView;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.model.callback.OnCheckCollectionListener;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.tools.ConnectionStatusUtil;
import com.dell.fortune.tools.IntentUtil;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomeCollectionPresenter extends BasePresenter<HomeCollectionPresenter.IView> {
    private CollectionModel collectionModel;

    public interface OnSynSuccessListener {
        void onSuccess();
    }

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

    //同步本地
    public void synLocal() {
        mView.showLoading(true, 3 * 1000);
        collectionModel.synLocal(new OnSynSuccessListener() {
            @Override
            public void onSuccess() {
                mView.showSynHead(false, 0);
            }
        });
    }

    public void checkLocalSynchronize() {
        //wifi下自动同步;
        if (ConnectionStatusUtil.checkWifeStatus(mContext)) {
            collectionModel.synLocal(new OnSynSuccessListener() {
                @Override
                public void onSuccess() {
                    mView.showSynHead(false, 0);
                }
            });
            return;
        }
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
