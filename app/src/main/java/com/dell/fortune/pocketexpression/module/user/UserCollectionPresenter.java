package com.dell.fortune.pocketexpression.module.user;


import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import java.util.List;

public class UserCollectionPresenter extends BasePresenter<UserCollectionPresenter.IView> {
    private CollectionModel collectionModel;
    private int mPage;

    public UserCollectionPresenter(IView view) {
        super(view);
        collectionModel = new CollectionModel();
    }

    public void getList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        collectionModel.getList(mPage, new ToastQueryListener<ExpressionItem>() {
            @Override
            public void onSuccess(List<ExpressionItem> list) {
                mView.setList(isRefreshing,list);
            }
        });
    }

    interface IView extends IBaseView {

        void setList(boolean isRefreshing, List<ExpressionItem> list);
    }
}
